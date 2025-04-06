package com.example.bean.response;

import com.example.common.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class UserInfoResponse {

    /**
     * ID
     */
    private String id;

    /**
     * 用户昵称
     */
    private String username;


    /**
     * 用户邮箱
     */

    private String email;


    /**
     * 头像图片路径
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String avatar;
}
