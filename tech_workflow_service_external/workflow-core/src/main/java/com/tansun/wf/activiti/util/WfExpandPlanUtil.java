package com.tansun.wf.activiti.util;

import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jali
 * @create 2019-08-23 11:14
 */
public class WfExpandPlanUtil {
    /**
     * 生成扩展字段映射
     * @param plans
     * @return
     */
    public static List<String> generateFieldMapping(List<WfTaskExpandPlanEntity> plans) {
        //生成扩展字段条件
        List<String> fieldMapping = new ArrayList<String>();
        for (WfTaskExpandPlanEntity plan: plans) {
            String fieldColumn = plan.getFieldValue();
            String fieldKey = plan.getFieldKey();
            fieldMapping.add("W." + fieldColumn + " \"" + fieldKey + "\"");
        }
        return fieldMapping;
    }
}
