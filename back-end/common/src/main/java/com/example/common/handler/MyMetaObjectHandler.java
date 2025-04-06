package com.example.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.common.constant.AutoFillConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入操作自动填充
        this.setFieldValByName(AutoFillConstant.CREATE_TIME, LocalDateTime.now(), metaObject);
        this.setFieldValByName(AutoFillConstant.UPDATE_TIME, LocalDateTime.now(), metaObject);
//        this.setFieldValByName(AutoFillConstant.CREATE_USER, BaseContext.getCurrentId(), metaObject);
//        this.setFieldValByName(AutoFillConstant.UPDATE_USER, BaseContext.getCurrentId(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新操作自动填充
        this.setFieldValByName(AutoFillConstant.UPDATE_TIME, LocalDateTime.now(), metaObject);
//        this.setFieldValByName(AutoFillConstant.UPDATE_USER, BaseContext.getCurrentId(), metaObject);
    }
}