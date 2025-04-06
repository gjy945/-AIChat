package com.example.bean.enums;

import lombok.Getter;

/**
 * websocket消息类型枚举
 */
@Getter
public enum ChatType {
    SINGLE_SEND(0),       //单聊
    GROUP_SEND(1),        //群聊
    BROADCAST_SEND(2);    //广播

    private final int value;

    ChatType(int value) {
        this.value = value;
    }
}