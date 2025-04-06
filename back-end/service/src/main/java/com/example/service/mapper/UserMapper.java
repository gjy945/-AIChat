package com.example.service.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
