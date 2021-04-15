package com.anyu.userservice.model.condition;


import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.Gender;
import com.anyu.common.model.enums.UserOrderType;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户查询条件
 * {@link UserOrderType 用户排序规则枚举类}
 *
 * @author Anyu
 * @since 2020/10/31
 */
public class UserPageCondition {
    /**
     * 用户昵称
     */
    private String nickname;
    /*
     *年龄
     */
    private Integer minAge;
    /*
     *年龄
     */
    private Integer maxAge;
    /**
     * 用户性别
     */
    private Gender gender;
    /**
     * 用户昵称
     */
    private String realName;

    private UserOrderType orderType;

    /**
     * 使用lambda 根據条件构造chainWrapper
     *
     * @param chainWrapper 需要构造的wrapper
     * @param condition    条件
     */
    public static void initWrapperByCondition(LambdaQueryChainWrapper<User> chainWrapper, UserPageCondition condition) {

        if (condition != null) {
            if (condition.getMinAge() != null) {
                chainWrapper.ge(User::getAge, condition.getMinAge());
            }
            if (condition.getMaxAge() != null) {
                chainWrapper.le(User::getAge, condition.getMaxAge());
            }
            if (StringUtils.isNotBlank(condition.getNickname())) {
                chainWrapper.eq(User::getNickname, condition.getNickname());
            }
            if (StringUtils.isNotBlank(condition.getRealName())) {
                chainWrapper.eq(User::getRealName, condition.getRealName());
            }
            if (condition.getOrderType() != null) {
                switch (condition.getOrderType()) {
                    case ASC_AGE:
                        chainWrapper.orderByAsc(User::getAge);
                        break;
                    case ASC_CREATE_TIME:
                        chainWrapper.orderByAsc(User::getCreateTime);
                        break;
                    case ASC_MODIFIED_TIME:
                        chainWrapper.orderByAsc(User::getModifiedTime);
                        break;
                    case DESC_AGE:
                        chainWrapper.orderByDesc(User::getAge);
                        break;
                    case DESC_CREATE_TIME:
                        chainWrapper.orderByDesc(User::getBirthday);
                        break;
                    case DESC_MODIFIED_TIME:
                    case DEFAULT:
                        chainWrapper.orderByDesc(User::getModifiedTime);
                        break;
                }
            }
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public UserOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(UserOrderType orderType) {
        this.orderType = orderType;
    }
}
