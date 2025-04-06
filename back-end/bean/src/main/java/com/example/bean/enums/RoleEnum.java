package com.example.bean.enums;


import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
public enum RoleEnum {

    ADMIN(1),

    USER(2);


    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }

}
