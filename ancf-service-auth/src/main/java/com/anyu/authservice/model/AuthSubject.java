package com.anyu.authservice.model;

import com.anyu.authservice.model.enums.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthSubject {
    private Integer userId;
    private String account;
    private Role role;
    private String nickname;
}
