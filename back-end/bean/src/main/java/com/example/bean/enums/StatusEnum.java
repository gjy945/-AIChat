package com.example.bean.enums;

import lombok.Getter;

/**
 * 状态枚举，启用或者禁用
 */
@Getter
public enum StatusEnum {
    ACTIVATE(1), // 启用
    FREEZE(0); // 禁用, 冻结

    private final int value;

    StatusEnum(int value) {
        this.value = value;
    }

}

