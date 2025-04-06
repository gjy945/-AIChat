package com.example.security.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.bean.model.User;
import com.example.common.exception.BaseException;
import com.example.common.response.ServerResponseEntity;
import com.example.common.utils.PrincipalUtil;
import com.example.security.admin.model.SysUser;
import com.example.security.admin.util.SecurityUtils;
import com.example.security.common.bo.UserInfoInTokenBO;
import com.example.security.common.dto.AuthenticationDTO;
import com.example.security.common.enums.SysTypeEnum;
import com.example.security.common.manager.PasswordCheckManager;
import com.example.security.common.manager.PasswordManager;
import com.example.security.common.manager.TokenStore;
import com.example.security.common.vo.TokenInfoVO;
import com.example.service.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "登录")
public class LoginController {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordCheckManager passwordCheckManager;


    @PostMapping("/login")
    @Operation(summary = "账号密码(用于前端登录)" , description = "通过账号/手机号/用户名密码登录，还要携带用户的类型，也就是用户所在的系统")
    public ServerResponseEntity<TokenInfoVO> login(
            @Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String mobileOrUserName = authenticationDTO.getUserName();
        User user = getUser(mobileOrUserName);


        // 半小时内密码输入错误十次，已限制登录30分钟
        passwordCheckManager.checkPassword(SysTypeEnum.ORDINARY,authenticationDTO.getUserName(), authenticationDTO.getPassWord(), user.getPassword());

        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(user.getId());
        userInfoInToken.setSysType(SysTypeEnum.ADMIN.value());
        userInfoInToken.setEnabled(true);
        userInfoInToken.setNickName(user.getUsername());
        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return ServerResponseEntity.success(tokenInfoVO);
    }

    @GetMapping("/hi")
    public SysUser hi() {
        return SecurityUtils.getSysUser();
    }

    private User getUser(String mobileOrUserName) {
        User user = null;
        // 如果不是手机验证码登陆， 找不到手机号就找用户名
        if  (user == null) {
            user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, mobileOrUserName));
        }
        if (user == null) {
            throw new BaseException("账号或密码不正确");
        }
        return user;
    }
}
