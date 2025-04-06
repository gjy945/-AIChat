package com.example.security.api.model;

import lombok.Data;

/**
 * 用户详细信息
 */
@Data
public class NormalUser {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 业务用户ID
     */
    private String bizUserId;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 用户名
     */
    private String username;
}
