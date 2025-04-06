package com.example.security.api.util;

import com.example.common.utils.HttpContextUtils;
import com.example.security.api.model.NormalUser;
import com.example.security.common.bo.UserInfoInTokenBO;
import com.example.security.common.util.AuthUserContext;
import lombok.experimental.UtilityClass;

@UtilityClass // 添加UtilityClass注解用来避免实例化
public class SecurityUtils {

    private static final String USER_REQUEST = "/p/";

    /**
     * 获取用户
     */
    public NormalUser getUser() {
        if (!HttpContextUtils.getHttpServletRequest().getRequestURI().startsWith(USER_REQUEST)) {
            // 用户相关的请求，应该以/p开头！！！
            throw new RuntimeException("user.request.error");
        }
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();

        NormalUser normalUser = new NormalUser();
        normalUser.setUserId(userInfoInTokenBO.getUserId());
        normalUser.setBizUserId(userInfoInTokenBO.getBizUserId());
        normalUser.setEnabled(userInfoInTokenBO.getEnabled());
        normalUser.setUsername(userInfoInTokenBO.getNickName());
        return normalUser;
    }
}
