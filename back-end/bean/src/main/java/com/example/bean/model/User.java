package com.example.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bean.entity.BaseEntity;
import com.example.common.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2090714647038636896L;
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
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
     * 登录密码
     */

    private String password;
    /**
     * 头像图片路径
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String avatar;
}
