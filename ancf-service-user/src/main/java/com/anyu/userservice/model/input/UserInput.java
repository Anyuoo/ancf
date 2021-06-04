package com.anyu.userservice.model.input;


import com.anyu.common.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserInput {

    @NotBlank(message = "账号不能为空")
    private String account;
    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    /**
     *生日
     */
    @NotNull(message = "生日不能为空")
    private LocalDate birthday;
    /**
     * 用户性别
     */
    @NotNull(message = "性别不能为空")
    private Gender gender;
    /**
     * 用户真实名
     */
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

}
