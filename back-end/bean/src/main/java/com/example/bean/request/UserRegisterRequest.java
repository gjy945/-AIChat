package com.example.bean.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    /**
     * 用户昵称
     */
    private String username;


    /**
     * 用户邮箱
     */

    private String email;

    /**
     * 登录密码
     */

    private String password;
    /**
     * 头像图片路径
     */
    private String avatar;
}
