package com.tansun.wf.activiti.constants;

/**
 * camunda实例相关常量
 */
public interface CamundaInstConstants {

    /** 执行中实例 == */
    String INST_STAT_ACTIVE = "ACTIVE";

    /** 已完成实例 == */
    String INST_STAT_COMPLETED = "COMPLETED";

    /** 已终止实例 like */
    String INST_STAT_DELETE = "%TERMINATED%";
}
