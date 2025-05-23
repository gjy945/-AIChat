package com.example.security.common.bo;

import lombok.Data;

import java.util.Set;

/**
 * 保存在token信息里面的用户信息
 */
@Data
public class UserInfoInTokenBO {

    /**
     * 用户在自己系统的用户id
     */
    private String userId;


    /**
     * 昵称
     */
    private String nickName;

    /**
     * 系统类型
     * @see com.example.security.common.enums.SysTypeEnum
     */
    private Integer sysType;

    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String bizUserId;

    /**
     * 权限列表
     */
    private Set<String> perms;

    /**
     * 状态 1 正常 0 无效
     */
    private Boolean enabled;

    /**
     * 其他Id
     */
    private Long otherId;

}
