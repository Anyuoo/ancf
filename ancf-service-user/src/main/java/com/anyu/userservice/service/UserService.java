package com.anyu.userservice.service;


import com.anyu.authservice.model.enums.Role;
import com.anyu.authservice.service.AuthService;
import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.Gender;
import com.anyu.common.result.type.ResultType;
import com.anyu.common.result.type.UserResultType;
import com.anyu.common.util.CommonUtils;
import com.anyu.common.util.GlobalConstant;
import com.anyu.common.util.MailClient;
import com.anyu.userservice.mapper.UserMapper;
import com.anyu.userservice.model.input.UserInput;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

/**
 * (User)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-07 09:56:09
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {
    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    @Resource
    private MailClient mailClient;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private AuthService authService;
    @Resource
    private CacheService cacheService;


    public Optional<User> getUserById(long id) {
        return lambdaQuery().eq(User::getId, id).oneOpt();
    }

    /**
     * 修改用户基本信息
     */
    public boolean updateUserInfoById(@NonNull long id, UserInput input) {
        final var account = input.getAccount() + GlobalConstant.ACCOUNT_SUFFIX;
        validAccountStr(account);
        final var original = getById(id);
        if (original == null) {
            //该用户不存在
            throw GlobalException.causeBy(UserResultType.NOT_EXIST);
        }
        //是否修改账号
        if (!account.equals(original.getAccount())) {
            Integer count = lambdaQuery().eq(User::getAccount, account).count();
            if (count != null && count > 0) {
                throw GlobalException.causeBy(ResultType.PARAM_ERROR, "账号已存在");
            }
        }
        original.setAccount(account)
                .setGender(input.getGender())
                .setNickname(input.getNickname())
                .setRealName(input.getRealName())
                .setBirthday(input.getBirthday());
        return updateById(original);
    }

    /**
     * 修改密码
     *
     * @param id       用户id
     * @param password 新密码
     */
    public boolean updatePassword(long id, String password) {
        final var user = getUserById(id).orElse(null);
        if (StringUtils.isBlank(password) || user == null) {
            throw GlobalException.causeBy(ResultType.FAILED);
        }
        user.setPassword(password);
        hashPassword(user);
        return updateById(user);
    }

    /**
     * 修改邮箱
     *
     * @param id    用户id
     * @param email 邮箱
     * @param code  验证码
     */
    public boolean updateEmail(long id, String email, String code) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(code)) {
            return false;
        }
        final var user = getUserById(id)
                .orElseThrow(() -> GlobalException.causeBy(ResultType.PARAM_ERROR));
        Optional<String> verifyCode = cacheService.getVerifyCode(email);
        if (verifyCode.isPresent() && code.equals(verifyCode.get())) {
            user.setEmail(email);
            return updateById(user);
        }
        return false;
    }

    /**
     * 邮箱修改验证
     *
     * @param id    用户id
     * @param email 新邮箱
     */
    public boolean sendEmailUpdateCode(long id, String email) {
        if (getUserByEmail(email).isPresent()) {
            throw GlobalException.causeBy(ResultType.FAILED, "邮箱已注册");
        }
        User user = getUserById(id).orElseThrow(() -> GlobalException.causeBy(ResultType.FAILED, "用户不存在"));
        final var context = new Context();
        final var variables = new HashMap<String, Object>(4);
        //邮箱
        String oldEmail = user.getEmail();
        variables.put("oldEmail", oldEmail);
        variables.put("newEmail", email);
        //验证码
        final var code = CommonUtils.randomNumberString().substring(0, 5);
        variables.put("code", code);
        //注册时间
        final var createTime = LocalDateTime.now();
        variables.put("createTime", createTime);
        context.setVariables(variables);
        final var content = templateEngine.process("EmailNoticeOfUpdateEmail", context);
        mailClient.sendMail(null, Collections.singletonList(oldEmail), "AnCF验证邮件", content);
        //添加置缓存
        return cacheService.setVerifyCode(email, code);
    }

    //
//    /**
//     * 分页查询
//     * {@link UserPageCondition# initWrapperByCondition(LambdaQueryChainWrapper, UserPageCondition)} 初始化
//     *
//     * @param first     数量
//     * @param id        起始id
//     * @param condition 分页条件构造
//     * @return 分页列表
//     */
//
//    public List<User> listUserAfter(int first, Integer id, UserPageCondition condition) {
//        if (first == 0)
//            first = PAGE_FIRST;
//        return condition.initWrapperByCondition(lambdaQuery())
//                .ge(id != null,User::getId, id)
//                .last(PAGE_SQL_LIMIT + first)
//                .list();
//    }
//
//
    public Optional<User> getUserByAccount(String account) {
        return lambdaQuery().eq(StringUtils.isNotBlank(account), User::getAccount, account).oneOpt();
    }


    public Optional<User> getUserByEmail(String email) {
        return lambdaQuery().eq(StringUtils.isNotBlank(email), User::getEmail, email).oneOpt();
    }

    /**
     * 发送注册验证邮件
     */
    public boolean sendVerifyCodeByEmail(@NotBlank String email) {
        if (getUserByEmail(email).isPresent()) {
            throw GlobalException.causeBy(ResultType.FAILED, "邮箱已注册");
        }
        final var context = new Context();
        final var variables = new HashMap<String, Object>(4);
        //邮箱
        variables.put("email", email);
        //验证码
        final var verifyCode = CommonUtils.randomNumberString().substring(0, 5);
        variables.put("verifyCode", verifyCode);
        //注册时间
        final var createTime = LocalDateTime.now();
        variables.put("createTime", createTime);
        context.setVariables(variables);
        final var content = templateEngine.process("EmailNoticeOfEmailRegister", context);
        mailClient.sendMail(null, Collections.singletonList(email), "AnCF注册邮件", content);
        //添加置缓存
        return cacheService.setVerifyCode(email, verifyCode);
    }

    /**
     * 注册
     *
     * @param email    邮箱
     * @param password 密码
     * @param code     验证码
     */
    public boolean register(String email, String password, String code) {
        //验证验证码
        final var verifyCode = cacheService.getVerifyCode(email).orElse(null);
        if (StringUtils.isBlank(verifyCode) || !verifyCode.equals(code)) {
            throw GlobalException.causeBy(ResultType.FAILED, "验证码错误");
        }
        //验证邮箱
        if (getUserByEmail(email).isPresent()) {
            throw GlobalException.causeBy(ResultType.FAILED, "邮箱已注册");
        }
        //账号
        final var account = getNewAccountStr(email.split("@")[0]);
        final var user = new User()
                .setEmail(email)
                .setAccount(account)
                .setPassword(password)
                .setAge(0)
                .setGender(Gender.NUKNOWN);
        //密码hash
        hashPassword(user);
        return save(user);
    }

    //    登录
    public Optional<String> login(String principal, String password) {
        //账号
        User loginUser;
        if (StringUtils.endsWith(principal, "@ancf")) {
            loginUser = getUserByAccount(principal).orElse(null);
        } else {
            loginUser = getUserByEmail(principal).orElse(null);
        }
        if (loginUser == null) {
            throw GlobalException.causeBy(ResultType.FAILED, "用户不存在");
        }
        var hashPass = CommonUtils.md5(password + loginUser.getSalt());
        if (StringUtils.isBlank(hashPass) || !hashPass.equals(loginUser.getPassword())) {
            throw GlobalException.causeBy(ResultType.FAILED, "密码错误");
        }
        return authService.createJwt(loginUser.getId().toString(), loginUser.getAccount(), Role.USER);
    }

    //    更新头像
    public boolean updateAvatar(long userId, String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        var user = getUserById(userId).orElseThrow(() -> GlobalException.causeBy(ResultType.SYS_ERROR));
        user.setAvatar(url);
        return updateById(user);
    }

    /**
     * 密码hash
     */
    private void hashPassword(User user) {
        if (user == null)
            throw GlobalException.causeBy(ResultType.PARAM_ERROR);
        //密码加密,加盐加密
        final var salt = CommonUtils.randomString().substring(0, 5);
        final var hashPass = CommonUtils.md5(user.getPassword() + salt);
        if (StringUtils.isBlank(salt) || StringUtils.isBlank(hashPass))
            throw GlobalException.causeBy(ResultType.SYS_ERROR);
        user.setPassword(hashPass)
                .setSalt(salt);
    }

    /**
     * 生成随机新账号
     */
    private String getNewAccountStr(String key) {
        final var suffix = "@ancf";
        final var randStr = CommonUtils.randomString().substring(0, 4);
        final var account = key + randStr + suffix;
        final var count = lambdaQuery().eq(User::getAccount, account).count();
        if (count != null && count > 0) {
            return key + randStr + count + suffix;
        }
        return account;
    }

    /**
     * 验证账号格式
     */
    private void validAccountStr(String account) {
        if (StringUtils.isBlank(account)
                || !account.endsWith(GlobalConstant.ACCOUNT_SUFFIX)
                || account.split(GlobalConstant.ACCOUNT_SUFFIX).length != 1) {
            throw GlobalException.causeBy(ResultType.PARAM_ERROR, "账号格式错误");
        }
    }


}