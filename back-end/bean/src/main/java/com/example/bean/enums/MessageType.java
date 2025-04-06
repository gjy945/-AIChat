package com.example.bean.enums;

import lombok.Getter;

@Getter
public enum MessageType {
    TEXT("text"), //文本消息
    FILE("file"),  //文件消息
    IMAGE("image"),  //图片消息
    VIDEO("video"),  //视频消息
    AUDIO("audio"),  //音频消息
    LOCATION("location"),  //位置消息
    LINK("link"),  //链接消息
    CARD("card"),  //卡片消息
    NOTICE("notice"),  //通知消息
    SYSTEM("system"),  //系统消息
    ;

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

}
