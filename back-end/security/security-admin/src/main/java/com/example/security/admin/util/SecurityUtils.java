package com.example.security.admin.util;

import com.example.security.admin.model.SysUser;
import com.example.security.common.bo.UserInfoInTokenBO;
import com.example.security.common.util.AuthUserContext;
import lombok.experimental.UtilityClass;

@UtilityClass // 添加UtilityClass注解用来避免实例化
public class SecurityUtils {
    /**
     * 获取用户
     */
    public SysUser getSysUser() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();

        SysUser details = new SysUser();
        details.setUserId(userInfoInTokenBO.getUserId());
        details.setEnabled(userInfoInTokenBO.getEnabled());
        details.setUsername(userInfoInTokenBO.getNickName());
        details.setAuthorities(userInfoInTokenBO.getPerms());
        return details;
    }
}

