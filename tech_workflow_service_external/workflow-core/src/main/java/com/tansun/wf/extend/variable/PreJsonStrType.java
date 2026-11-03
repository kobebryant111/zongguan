//package com.tansun.wf.extend.variable;
//
//import com.alibaba.fastjson.JSON;
//import org.camunda.bpm.engine.impl.variable.ValueFields;
//import org.camunda.bpm.engine.impl.variable.VariableType;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 前置数组JSON处理器
// * 如果是变量对象是List,Map,Set 则用json字符串存储
// */
// camunda-TODO
//public class PreJsonStrType implements VariableType {
//
//
//    @Override
//    public String getTypeName() {
//        return "jsonStr";
//    }
//
//    @Override
//    public boolean isCachable() {
//        return true;
//    }
//
//    @Override
//    public boolean isAbleToStore(Object value) {
//        if (value == null) {
//            return false;
//        }
//        if (value instanceof List) {
//            return true;
//        }else if(value instanceof String[]){
//            return true;
//        }else if(value instanceof Map){
//            return true;
//        }else if(value instanceof Set){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    @Override
//    public void setValue(Object value, ValueFields valueFields) {
//        //如果字符串超出长度限制，建议建act_ru_variable 和act_hi_variable 的text_字段类型改成text
//        if (value == null){
//            valueFields.setTextValue(null);
//            valueFields.setTextValue2(null);
//            return;
//        }
//        String list = JSON.toJSONString(value);
//        valueFields.setTextValue(list);
//
//        if (value instanceof List) {
//            valueFields.setTextValue2(List.class.getName());
//        }else if(value instanceof String[]){
//            valueFields.setTextValue2(String[].class.getName());
//        }else if(value instanceof Map){
//            valueFields.setTextValue2(Map.class.getName());
//        }else if(value instanceof Set){
//            valueFields.setTextValue2(Set.class.getName());
//        }
//
//    }
//
//    @Override
//    public Object getValue(ValueFields valueFields) {
//        String textValue = valueFields.getTextValue();
//
//        //value2 是类名称
//        String textValue2 = valueFields.getTextValue2();
//        if (StringUtils.isNotBlank(textValue)  && StringUtils.isNotBlank(textValue2)) {
//
//            Class testTypeForName = null;
//            try {
//                testTypeForName = Class.forName(textValue2);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            return JSON.parseObject(textValue, testTypeForName);
//        }else {
//            return null;
//        }
//
//
//    }
//}
