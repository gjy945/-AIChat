package com.example.common.constant;

public interface OauthCacheNames {

    /**
     * oauth 授权相关key
     */
    String OAUTH_PREFIX = "oauth:";

    /**
     * token 授权相关key
     */
    String OAUTH_TOKEN_PREFIX = OAUTH_PREFIX + "token:";

    /**
     * 保存token 缓存使用key
     */
    String ACCESS = OAUTH_TOKEN_PREFIX + "access:";

    /**
     * 刷新token 缓存使用key
     */
    String REFRESH_TO_ACCESS = OAUTH_TOKEN_PREFIX + "refresh_to_access:";

    /**
     * 根据uid获取保存的token key缓存使用的key
     */
    String UID_TO_ACCESS = OAUTH_TOKEN_PREFIX + "uid_to_access:";

    /**
     * 保存token的用户信息使用的key
     */
    String USER_INFO = OAUTH_TOKEN_PREFIX + "user_info:";
}
