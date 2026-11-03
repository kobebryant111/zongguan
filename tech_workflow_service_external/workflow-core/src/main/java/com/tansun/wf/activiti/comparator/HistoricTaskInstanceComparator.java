package com.tansun.wf.activiti.comparator;

import org.camunda.bpm.engine.history.HistoricTaskInstance;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * 加上子流程后的任务后，流程跟踪管理需要实现根据startTime重新排序
 * @author lijiang
 * @create 2019-10-23 18:04
 */
public class HistoricTaskInstanceComparator implements Comparator<Map> {
    @Override
    public int compare(Map o1, Map o2) {
        return ((String)o1.get("beginTime")).compareTo((String)o2.get("beginTime"));
    }
}
