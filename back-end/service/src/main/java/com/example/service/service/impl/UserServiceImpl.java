package com.example.service.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.model.User;
import com.example.bean.request.UserRegisterRequest;
import com.example.bean.response.UserInfoResponse;
import com.example.common.exception.BaseException;
import com.example.common.utils.PasswordUtils;
import com.example.service.mapper.UserMapper;
import com.example.service.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.common.constant.MessageConstant.REGISTER_FAILED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
       User user =  BeanUtil.copyProperties(userRegisterRequest,User.class);
      user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        boolean save = save(user);
        if (!save){
            throw new BaseException(REGISTER_FAILED);
        }
    }
}
