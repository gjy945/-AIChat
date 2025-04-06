package com.example.security.admin.model;

import lombok.Data;

import java.util.Set;

/**
 * 用户详细信息
 */
@Data
public class SysUser {

    /**
     * 用户ID
     */
    private String userId;

    private boolean enabled;

    private Set<String> authorities;

    private String username;

}
