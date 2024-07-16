package com.bosch.web.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.bosch.common.utils.DateUtils;
import com.bosch.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", DateUtils.getNowDate(), metaObject);
        this.setFieldValByName("createBy", SecurityUtils.getLoginUser().getUsername(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime", DateUtils.getNowDate(), metaObject);
        System.out.println(SecurityUtils.getUsername());
        this.setFieldValByName("updateBy", SecurityUtils.getLoginUser().getUsername(), metaObject);
    }
}


