package com.anyu.userservice.service.impl;


import com.anyu.authservice.model.enums.Role;
import com.anyu.authservice.service.AuthService;
import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.ActiveStatus;
import com.anyu.common.result.type.UserResultType;
import com.anyu.common.util.CommonUtils;
import com.anyu.common.util.MailClient;
import com.anyu.userservice.model.condition.UserPageCondition;
import com.anyu.userservice.model.input.UserInput;
import com.anyu.userservice.mapper.UserMapper;
import com.anyu.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * (User)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-07 09:56:09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private MailClient mailClient;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private AuthService authService;
    @Resource
    private CacheService cacheService;

    @Override
    public Optional<User> getUserById(int id) {
        return lambdaQuery().eq(User::getId, id).oneOpt();
    }

    /**
     * 用户注册 邮箱或手机号
     * {@link #sendActivationEmail(String)} 邮箱发送激活信息
     * {@link #verifyUserInput(User)} 验证用户输入，清理过期用户
     *
     * @param user 注册参数
     * @return 结果
     */
    @Override
    public boolean register(User user) {
        verifyUserInput(user);
        //密码加密,加盐加密
        final var salt = CommonUtils.randomString().substring(0, 5);
        final var password = CommonUtils.md5(user.getPassword() + salt);

        user.setSalt(salt)
                .setPassword(password)
                .setActivation(ActiveStatus.UNACTIVED);
        if (user.getAge() == null) {
            user.setAge(0);
        }
        if (!save(user)) {
            log.info("UserServiceImpl: register[saveUser:{},注册失败]", user.getEmail());
            //注册失败！
            throw GlobalException.causeBy(UserResultType.REGISTER_ERROR);
        }
        //激活用户，邮箱注册,if user email is blank or send activation email failed,then throw exception
        if (StringUtils.isBlank(user.getEmail()) || !sendActivationEmail(user.getEmail())) {
            throw GlobalException.causeBy(UserResultType.SEND_ACTIVATION_EMAIL_ERROR);
        }
        //TODO 激活用户，手机注册验证

        //if everything is completed ,return true;
        return true;
    }


    @Override
    public boolean updateUserById(@NonNull Integer id, User user) {
        verifyUserInput(user);
        final var original = this.getById(id);
        if (original == null) {
            //该用户不存在
            throw GlobalException.causeBy(UserResultType.NOT_EXIST);
        }
        BeanUtils.copyProperties(user, original);
        return this.updateById(original);
    }

    /**
     * 分页查询
     * {@link UserPageCondition# initWrapperByCondition(LambdaQueryChainWrapper, UserPageCondition)} 初始化
     *
     * @param first     数量
     * @param id        起始id
     * @param condition 分页条件构造
     * @return 分页列表
     */
    @Override
    public List<User> listUserAfter(int first, Integer id, UserPageCondition condition) {
        if (first == 0)
            first = PAGE_FIRST;
        return condition.initWrapperByCondition(lambdaQuery())
                .ge(id != null,User::getId, id)
                .last(PAGE_SQL_LIMIT + first)
                .list();
    }

    @Override
    public Optional<User> getUserByAccount(@NotBlank String account) {
        return lambdaQuery().eq(User::getAccount, account).oneOpt();
    }

    @Override
    public Optional<User> getUserByEmail(@NotBlank String email) {
        return lambdaQuery().eq(User::getEmail, email).oneOpt();
    }

    @Override
    public Optional<User> getUserByMobile(@NotBlank String mobile) {
        return lambdaQuery().eq(User::getMobile, mobile).oneOpt();
    }

    @Override
    public boolean removeUserById(@NonNull Integer id) {
        return this.removeById(id);
    }

    /**
     * 激活用户
     * {@link #doActivate(User)} 激活
     *
     * @param activationKey  激活码的key，邮箱地址或手机号
     * @param activationCode 激活码
     * @param isEmail        是否是邮箱
     * @return 结果
     */
    @Override
    public boolean activateUser(@NotBlank String activationKey, @NotBlank String activationCode, boolean isEmail) {
        //缓存不存在
        var code = cacheService.getActivationCode(isEmail, activationKey).orElseThrow();
        if (!StringUtils.equals(code, activationCode.trim())) {
            return false;
        }
        return isEmail
                ? getUserByEmail(activationKey).map(this::doActivate).orElse(false)
                : getUserByMobile(activationKey).map(this::doActivate).orElse(false);
    }

    /**
     * 用户激活状态改为激活
     *
     * @param user 用户
     * @return 结果
     */
    public boolean doActivate(User user) {
        user.setActivation(ActiveStatus.ACTIVED);
        return this.updateById(user);
    }

    @Override
    public boolean updateAvatar(int userId, String url) {
        var success = new AtomicReference<>(false);
        getUserById(userId).ifPresent(user -> {
            user.setAvatar(url);
            success.set(updateById(user));
        });
        return success.get();
    }

    /**
     * 用户登录 (账号、邮箱、手机号) + 密码
     *
     * @param principal 账号、邮箱、手机号
     * @param password  密码
     * @return jwt
     */
    @Override
    public Optional<String> login(@NotBlank String principal, @NotBlank String password) {
        //邮箱
        if (StringUtils.containsAny(principal, "@")) {
            return getUserByEmail(principal).flatMap(user -> checkLogin(user, password)
                    ? authService.createJwt(user.getId().toString(), user.getNickname(), Role.USER)
                    : Optional.empty());
        }
        //mobil
        //TODO verify
        if (principal.length() == 11) {
            return getUserByMobile(principal).flatMap(user -> checkLogin(user, password)
                    ? authService.createJwt(user.getId().toString(), user.getNickname(), Role.USER)
                    : Optional.empty());
        }
        //account
        return getUserByAccount(principal).flatMap(user -> checkLogin(user, password)
                ? authService.createJwt(user.getId().toString(), user.getNickname(), Role.USER)
                : Optional.empty());
    }

    /**
     * @param user     current user
     * @param password input password
     * @return login result
     */
    private boolean checkLogin(User user, String password) {
        if (user ==null)
            return false;
        //not active
        if (user.getActivation() == ActiveStatus.UNACTIVED) {
            throw GlobalException.causeBy(UserResultType.NOT_ACTIVE);
        }
        var hashPassword = CommonUtils.md5(password + user.getSalt());
        if (StringUtils.equals(hashPassword, user.getPassword())) {
            return true;
        }
        //密码错误
        throw GlobalException.causeBy(UserResultType.PASSWORD_ERROR);
    }


    /**
     * 验证用户输入
     * {@link #verifyAndCleanUserWith(String, boolean)} 验证并清理过期邮箱或手机号
     * {@link #verifyAndCleanUserWith(String)} 验证并清理过期账号
     *
     * @param user 输入参数
     */
    private void verifyUserInput(User user) {
        //验证邮箱
        if (StringUtils.isNotBlank(user.getEmail())) {
            user.setEmail(user.getEmail().trim());
            if (verifyAndCleanUserWith(user.getEmail(), true)) {
                log.debug("register[email:{} 已经存在]", user.getEmail());
                throw GlobalException.causeBy(UserResultType.EMAIL_EXISTED);
            }
        }
        //验证手机号
        if (StringUtils.isNotBlank(user.getMobile())) {
            user.setMobile(user.getMobile());
            if (verifyAndCleanUserWith(user.getMobile(), false)) {
                log.debug("register[mobile:{} 已经存在]", user.getMobile());
                throw GlobalException.causeBy(UserResultType.MOBILE_EXISTED);
            }
        }
        //验证账号
        if (StringUtils.isNotBlank(user.getAccount())) {
            user.setAccount(user.getAccount().trim());
            if (verifyAndCleanUserWith(user.getAccount())) {
                log.debug("register[account:{} 已被注冊]", user.getAccount());
                throw GlobalException.causeBy(UserResultType.ACCOUNT_EXISTED);
            }
        }
    }

    /**
     * 验证并清理过期邮箱或手机号
     *
     * @param emailOrMobile 需要验证的邮箱或手机号
     * @return 是否存在
     */
    private boolean verifyAndCleanUserWith(@NotBlank String emailOrMobile, boolean isEmail) {
        //获取缓存key
        Optional<String> code = cacheService.getActivationCode(isEmail, emailOrMobile);
        //缓存中有该手机号或邮箱
        if (code.isPresent()) return true;
        //缓存为空，先将激活码失效的邮箱或手机号账户注销
        lambdaQuery()
                .eq(isEmail, User::getEmail, emailOrMobile)
                .eq(!isEmail, User::getMobile, emailOrMobile)
                .list()
                .forEach(user -> {
                    if (user.getActivation() == ActiveStatus.UNACTIVED) {
                        this.removeById(user.getId());
                    }
                });

        //已注册并激活的用户
        return isEmail
                ? getUserByEmail(emailOrMobile).isPresent()
                : getUserByMobile(emailOrMobile).isPresent();

    }

    /**
     * 验证并清理过期账户
     *
     * @param account 账户名
     * @return 是否存在
     */
    private boolean verifyAndCleanUserWith(@NotBlank String account) {
        lambdaQuery()
                .eq(User::getAccount, account)
                .list()
                .forEach(user -> {
                    //未激活的账户
                    if (user.getActivation() == ActiveStatus.UNACTIVED) {
                        if (StringUtils.isNotBlank(user.getEmail())) {
                            verifyAndCleanUserWith(user.getEmail(), true);
                        }
                        if (StringUtils.isNotBlank(user.getMobile())) {
                            verifyAndCleanUserWith(user.getMobile(), false);
                        }
                    }
                });
        return getUserByAccount(account).isPresent();
    }

    /**
     * 发送激活邮件
     *
     * @param email 注册的邮件地址
     * @return 结果
     */
    private boolean sendActivationEmail(@NotBlank String email) {
        var original = getUserByEmail(email);
        original.ifPresent(user -> {
            final var context = new Context();
            final var variables = new HashMap<String, Object>(4);
            variables.put("nickName", user.getNickname());
            //邮箱
            variables.put("email", user.getEmail());
            //激活码
            final var activeCode = CommonUtils.randomNumberString().substring(0, 5);
            variables.put("activeCode", activeCode);
            //添加置缓存
            cacheService.setActivationCode(true, email, activeCode);
            //注册时间
            final var createTime = user.getCreateTime().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
            variables.put("createTime", createTime);
            context.setVariables(variables);
            final var content = templateEngine.process("activation", context);
            mailClient.sendMail(null, Collections.singletonList(user.getEmail()), "激活邮件", content);
        });
        return original.isPresent();
    }
}