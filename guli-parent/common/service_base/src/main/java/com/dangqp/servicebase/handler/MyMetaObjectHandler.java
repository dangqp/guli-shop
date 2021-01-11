package com.dangqp.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Title:com.dangqp.servicebase.handler.MyMetaObjectHandler
 * Description: 描述【mybatis_plus数据库字段默认依据操作时间修改】
 * Copyright: Copyright (c) 2018
 * Company:
 * @author dangqp
 * @version 1.0
 * @created  2021/1/11 18:47
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     *     @TableField(fill = FieldFill.INSERT)
     *     private Date gmtCreate;
     *
     *     @TableField(fill = FieldFill.INSERT_UPDATE)
     *     private Date gmtModified;
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称，不是字段名称
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
