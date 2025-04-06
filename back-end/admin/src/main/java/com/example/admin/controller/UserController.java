package com.example.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.bean.model.User;
import com.example.bean.request.UserRegisterRequest;
import com.example.bean.response.UserInfoResponse;
import com.example.common.response.ServerResponseEntity;
import com.example.security.admin.model.SysUser;
import com.example.security.admin.util.SecurityUtils;
import com.example.service.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Tag(name = "管理员用户")
public class UserController {
    private final IUserService iUserService;

    @PostMapping("/register")
    @Operation(summary = "添加管理员用户")
    public ServerResponseEntity<Void> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        iUserService.register(userRegisterRequest);
        return ServerResponseEntity.success();
    }

    @GetMapping("/getInfo")
    @Operation(summary = "获取管理员用户信息")
    public ServerResponseEntity<UserInfoResponse> getInfo() {
        SysUser sysUser = SecurityUtils.getSysUser();
        User byId = iUserService.getById(sysUser.getUserId());
        return ServerResponseEntity.success(BeanUtil.copyProperties(byId, UserInfoResponse.class));
    }
}
