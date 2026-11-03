package com.work.ppm.commons.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.work.ppm.commons.annotaion.FillDefVal;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 设置默认值
 *
 */
@Slf4j
@Component
public class DefaultValueHandler implements MetaObjectHandler {


    /**
     * 插入处理 创建人 创建时间
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if(UserSessionUtil.getCurrentUser()!=null){
            this.setFieldValByName("createdBy",UserSessionUtil.getCurrentUser().getUserid(), metaObject);
        }else{
            try {
                Field field1 = metaObject.getOriginalObject().getClass().getDeclaredField("createdBy");
                FillDefVal fillDefVal = field1.getAnnotation(FillDefVal.class);
                if(fillDefVal!=null){
                    this.setFieldValByName("createdBy", fillDefVal.value(),metaObject);
                }
            } catch ( NoSuchFieldException e) {
                log.info("没有createdTs属性");
            }
        }
//        this.setFieldValByName("createdTs", new Date(),metaObject);
        Class createdTsCls = Date.class;
        try {
            Field field1 = metaObject.getOriginalObject().getClass().getDeclaredField("createdDate");
            createdTsCls = field1.getType();
        } catch ( NoSuchFieldException e) {
            log.info("没有createdTs属性");
        }
        if(createdTsCls.isAssignableFrom(Date.class)){
            this.setFieldValByName("createdDate", new Date(),metaObject);
        }else if(createdTsCls.isAssignableFrom(Timestamp.class)){
            this.setFieldValByName("createdDate",new Timestamp( new Date().getTime()),metaObject);
        }

    }

    /**
     * 处理更新 更新人 更新时间
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if(UserSessionUtil.getCurrentUser()!=null){
            this.setFieldValByName("updatedBy", UserSessionUtil.getCurrentUser().getUserid(),metaObject);
            this.setFieldValByName("modifyBy", UserSessionUtil.getCurrentUser().getUserid(),metaObject);
        }else{
            try {
                Field field1 = metaObject.getOriginalObject().getClass().getDeclaredField("updatedBy");
                FillDefVal fillDefVal = field1.getAnnotation(FillDefVal.class);
                if(fillDefVal!=null){
                    this.setFieldValByName("updatedBy", fillDefVal.value(),metaObject);
                }
            } catch ( NoSuchFieldException e) {
                log.info("没有updatedBy属性");
            }
            try {
                Field field1 = metaObject.getOriginalObject().getClass().getDeclaredField("modifyBy");
                FillDefVal fillDefVal = field1.getAnnotation(FillDefVal.class);
                if(fillDefVal!=null){
                    this.setFieldValByName("modifyBy", fillDefVal.value(),metaObject);
                }
            } catch ( NoSuchFieldException e) {
                log.info("没有updatedBy属性");
            }
        }


        Class updatedTsCls = Date.class;
        Class modifyTsCls = Date.class;
        try {
            Field field1 = metaObject.getOriginalObject().getClass().getDeclaredField("updatedDate");
            updatedTsCls = field1.getType();
        } catch ( NoSuchFieldException e) {
            log.info("没有updateTs属性");
        }
        Field field2 = null;
        try {
            field2 = metaObject.getOriginalObject().getClass().getDeclaredField("modifyDate");
            modifyTsCls = field2.getType();
        } catch (NoSuchFieldException e) {
            log.info("没有modifyTs属性");
        }

        if(modifyTsCls.isAssignableFrom(Date.class)){
            this.setFieldValByName("modifyDate", new Date(),metaObject);
        }else if(modifyTsCls.isAssignableFrom(Date.class)){
            this.setFieldValByName("modifyDate", new Timestamp(new Date().getTime()),metaObject);
        }
        if(updatedTsCls.isAssignableFrom(Date.class)){
            this.setFieldValByName("updatedDate", new Date(),metaObject);
        }else if(modifyTsCls.isAssignableFrom(Timestamp.class)){
            this.setFieldValByName("updatedDate",new Timestamp( new Date().getTime()),metaObject);
        }




    }

    /**
     * 设置值
     * @param metaObject
     * @param fieldName
     */
    private  void setDefValue(MetaObject metaObject,String fieldName){
        if(UserSessionUtil.getCurrentUser()!=null){
            this.setFieldValByName(fieldName, UserSessionUtil.getCurrentUser().getUserid(),metaObject);
        }else{
            try {
                Field field1 = metaObject.getOriginalObject().getClass().getDeclaredField(fieldName);
                FillDefVal fillDefVal = field1.getAnnotation(FillDefVal.class);
                if(fillDefVal!=null){
                    this.setFieldValByName(fieldName, fillDefVal.value(),metaObject);
                }
            } catch ( NoSuchFieldException e) {
                log.info("没有{}属性",fieldName);
            }
        }

    }
}
