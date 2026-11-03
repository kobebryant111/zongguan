package com.tansun.wf.activiti.form;

import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Arrays;

/**
 * 用户表单字段类型
 *
 * @author henryyan
 */
public class UsersFormType extends AbstractFormFieldType  {

    
    public String getName() {
        return "users";
    }

    @Override
    public TypedValue convertToFormValue(TypedValue propertyValue) {
        return null;
    }

    @Override
    public TypedValue convertToModelValue(TypedValue propertyValue) {
        return null;
    }

    @Override
    public Object convertFormValueToModelValue(Object propertyValue) {
        // camunda-TODO
//        String[] split = StringUtils.split(propertyValue, ",");
//        return Arrays.asList(split);
        return propertyValue;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        // camunda-TODO
        return ObjectUtils.toString(modelValue);
    }

}
