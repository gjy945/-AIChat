package com.example.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.model.User;
import com.example.bean.request.UserRegisterRequest;
import com.example.bean.response.UserInfoResponse;

public interface IUserService extends IService<User> {

    void register(UserRegisterRequest userRegisterRequest);

}
