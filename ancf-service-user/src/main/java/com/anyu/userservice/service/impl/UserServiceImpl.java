package com.anyu.userservice.service.impl;


import com.anyu.authservice.entity.enums.Role;
import com.anyu.authservice.service.AuthService;
import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.ActiveStatus;
import com.anyu.common.result.type.UserResultType;
import com.anyu.common.util.CommonUtils;
import com.anyu.common.util.MailClient;
import com.anyu.userservice.entity.condition.UserPageCondition;
import com.anyu.userservice.entity.input.UserInput;
import com.anyu.userservice.mapper.UserMapper;
import com.anyu.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
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
        var user = this.lambdaQuery().eq(User::getId, id).one();
        return Optional.ofNullable(user);
    }

    /**
     * 用户注册 邮箱或手机号
     * {@link #sendActivationEmail(String)} 邮箱发送激活信息
     * {@link #verifyUserInput(UserInput)} 验证用户输入，清理过期用户
     *
     * @param input 注册参数
     * @return 结果
     */
    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public boolean register(UserInput input) {
        verifyUserInput(input);
        final var user = User.build();
        BeanUtils.copyProperties(input, user);
        //密码加密,加盐加密
        final var salt = CommonUtils.randomString().substring(0, 5);
        final var password = CommonUtils.md5(user.getPassword() + salt);

        user.setSalt(salt);
        user.setPassword(password);
        user.setActivation(ActiveStatus.UNACTIVED);
        if (user.getAge() == null) {
            user.setAge(0);
        }
        if (!this.save(user)) {
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
    public boolean updateUserById(@NonNull Integer id, UserInput input) {
        verifyUserInput(input);
        final var original = this.getById(id);
        if (original == null) {
            //该用户不存在
            throw GlobalException.causeBy(UserResultType.NOT_EXIST);
        }
        BeanUtils.copyProperties(input, original);
        return this.updateById(original);
    }

    /**
     * 分页查询
     * {@link UserPageCondition#initWrapperByCondition(LambdaQueryChainWrapper, UserPageCondition)} 初始化
     *
     * @param first     数量
     * @param id        起始id
     * @param condition 分页条件构造
     * @return 分页列表
     */
    @Override
    public List<User> listUserAfter(int first, Integer id, UserPageCondition condition) {
        final var chainWrapper = this.lambdaQuery();
        if (id != null) chainWrapper.ge(User::getId, id);
        UserPageCondition.initWrapperByCondition(chainWrapper, condition);
        if (first == 0) first = PAGE_FIRST;
        chainWrapper.last(PAGE_SQL_LIMIT + first);
        return chainWrapper.list();
    }

    @Override
    public Optional<User> getUserByAccount(@NotBlank String account) {
        final var user = this.lambdaQuery().eq(User::getAccount, account).one();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(@NotBlank String email) {
        final var user = this.lambdaQuery().eq(User::getEmail, email).one();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByMobile(@NotBlank String mobile) {
        final var user = this.lambdaQuery().eq(User::getMobile, mobile).one();
        return Optional.ofNullable(user);
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
        Optional<String> code = cacheService.getActivationCode(isEmail, activationKey);
        //缓存不存在
        if (code.isEmpty()) return false;
        //activationKey does not equal original activationKey
        if (!StringUtils.equals(code.get(), activationCode.trim())) {
            return false;
        }
        if (isEmail) {
            Optional<User> userByEmail = getUserByEmail(activationKey);
            if (userByEmail.isPresent() && doActivate(userByEmail.get())) {
                log.info("activateUser[email:{}激活成功]", activationKey);
                return true;
            }
        } else {
            Optional<User> userByMobile = getUserByMobile(activationKey);
            if (userByMobile.isPresent() && doActivate(userByMobile.get())) {
                log.info("activateUser[mobile:{}激活成功]", activationKey);
                return true;
            }
        }
        return false;
    }

    /**
     * 用户激活状态改为激活
     *
     * @param user 用户
     * @return 结果
     */
    @Transactional
    public boolean doActivate(User user) {
        user.setActivation(ActiveStatus.ACTIVED);
        return this.updateById(user);
    }

    @Override
    public boolean updateAvatar(int userId, String url) {
        final var original = getUserById(userId);
        if (original.isEmpty()) {
            return false;
        }
        final var user = original.get();
        user.setAvatar(url);
        return updateById(user);
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
        principal = principal.trim();
        password = password.trim();
        //邮箱
        if (StringUtils.containsAny(principal, "@")) {
            Optional<User> userByEmail = getUserByEmail(principal);
            if (checkLogin(userByEmail, password)) {
                final var user = userByEmail.get();
                return authService.createJwt(user.getId().toString(), user.getNickname(), Role.ROLE);
            }
        }
        //mobil
        //TODO verify
        if (principal.length() == 11) {
            Optional<User> userByMobile = getUserByMobile(principal);
            //verify login
            if (checkLogin(userByMobile, password)) {
                final var user = userByMobile.get();
                return authService.createJwt(user.getId().toString(), user.getNickname(), Role.ROLE);
            }
        }
        //account
        Optional<User> userByAccount = getUserByAccount(principal);
        if (checkLogin(userByAccount, password)) {
            final var user = userByAccount.get();
            return authService.createJwt(user.getId().toString(), user.getNickname(), Role.ROLE);
        }
        return Optional.empty();
    }

    /**
     * @param user     current user
     * @param password input password
     * @return login result
     */
    private boolean checkLogin(Optional<User> user, String password) {
        if (user.isPresent()) {
            final var original = user.get();
            if (original.getActivation() == ActiveStatus.UNACTIVED) {
                throw GlobalException.causeBy(UserResultType.NOT_ACTIVE);
            }
            final var salt = original.getSalt();
            password = CommonUtils.md5(password + salt);
            if (StringUtils.equals(password, original.getPassword())) {
                return true;
            }
            //密码错误
            throw GlobalException.causeBy(UserResultType.PASSWORD_ERROR);
        }
        return false;
    }


    /**
     * 验证用户输入
     * {@link #verifyAndCleanUserWith(String, boolean)} 验证并清理过期邮箱或手机号
     * {@link #verifyAndCleanUserWith(String)} 验证并清理过期账号
     *
     * @param input 输入参数
     */
    private void verifyUserInput(UserInput input) {
        //验证邮箱
        if (StringUtils.isNotBlank(input.getEmail())) {
            input.setEmail(input.getEmail().trim());
            if (verifyAndCleanUserWith(input.getEmail(), true)) {
                log.debug("register[email:{} 已经存在]", input.getEmail());
                throw GlobalException.causeBy(UserResultType.EMAIL_EXISTED);
            }
        }
        //验证手机号
        if (StringUtils.isNotBlank(input.getMobile())) {
            input.setMobile(input.getMobile());
            if (verifyAndCleanUserWith(input.getMobile(), false)) {
                log.debug("register[mobile:{} 已经存在]", input.getMobile());
                throw GlobalException.causeBy(UserResultType.MOBILE_EXISTED);
            }
        }
        //验证账号
        if (StringUtils.isNotBlank(input.getAccount())) {
            input.setAccount(input.getAccount().trim());
            if (verifyAndCleanUserWith(input.getAccount())) {
                log.debug("register[account:{} 已被注冊]", input.getAccount());
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
        //缓存为空，先将激活码失效的邮箱或手机号账户注销
        if (code.isEmpty()) {
            var chainWrapper = lambdaQuery();
            if (isEmail) {
                chainWrapper.eq(User::getEmail, emailOrMobile);
            } else {
                chainWrapper.eq(User::getMobile, emailOrMobile);
            }
            var users = chainWrapper.list();
            users.forEach(user -> {
                if (user.getActivation() == ActiveStatus.UNACTIVED) {
                    this.removeById(user.getId());
                }
            });
        }
        //缓存中有该手机号或邮箱
        if (code.isPresent()) return true;
        //已注册并激活的用户
        if (isEmail) {
            return getUserByEmail(emailOrMobile).isPresent();
        } else {
            return getUserByMobile(emailOrMobile).isPresent();
        }
    }

    /**
     * 验证并清理过期账户
     *
     * @param account 账户名
     * @return 是否存在
     */
    private boolean verifyAndCleanUserWith(@NotBlank String account) {
        final var users = this.lambdaQuery().eq(User::getAccount, account).list();
        users.forEach(user -> {
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