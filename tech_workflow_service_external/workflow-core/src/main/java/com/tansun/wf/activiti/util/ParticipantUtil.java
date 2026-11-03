package com.tansun.wf.activiti.util;

import org.camunda.commons.utils.StringUtil;

import java.util.Map;

public class ParticipantUtil {
    /**
     * 根据处理人表达式获取流程变量中的值，比如${startOrgId}
     * @param variables 流程变量
     * @param id
     * @return
     */
    public static String getVariableValue(Map<String,Object> variables, String id) {
        String text = "";
        if(StringUtil.isExpression(id)) {
            // 支持以${}形式的流程变量
            text = id.substring(id.indexOf("${") + 2, id.indexOf("}", id.indexOf("}")));

        }else if(id.startsWith("@")){
            // 支持@开头的流程变量
            text = id.substring(1);

        }else{
            return id;
        }

        Object variable = variables.get(text);
        if(variable==null){
            throw new RuntimeException("找不到流程变量："+text);
        }
        if(!(variable instanceof String)){
            throw new RuntimeException(text+"流程变量不是字符串");
        }
        return variable.toString();
    }
}
