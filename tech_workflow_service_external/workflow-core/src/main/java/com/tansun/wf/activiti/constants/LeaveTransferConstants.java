package com.tansun.wf.activiti.constants;

/**
 * 离岗移交相关常量类
 */
public interface LeaveTransferConstants {

    /**
     * 离岗移交执行状态：未执行
     */
    String START_STATUS_UN_BEGIN = "0";

    /**
     * 离岗移交执行状态：已执行
     */
    String START_STATUS_IS_BEGIN = "1";

    /**
     * 离岗移交状态：生效
     */
    String STATUS_VALID = "0";

    /**
     * 离岗移交状态：失效
     */
    String STATUS_INVALID = "1";

    /**
     * 移交人是否还有权限：无权限
     */
    String IS_POWER_NO = "0";

    /**
     * 移交人是否还有权限：有权限
     */
    String IS_POWER_YES = "1";

    /**
     * 是否移交已办任务：否
     */
    String IS_TRANSFER_HISTORICAL_TASKS_NO = "0";

    /**
     * 是否移交已办任务：是
     */
    String IS_TRANSFER_HISTORICAL_TASKS_YES = "1";

    /**
     * 是否永久移交：否
     */
    String IS_TRANSFER_NO = "0";

    /**
     * 是否永久移交：是
     */
    String IS_TRANSFER_YES = "1";

    /**
     * 新类型：移交人
     */
    String CANDIDATE_USER = "candidate_user";

    /**
     * 新类型：交接人
     */
    String CANDIDATE_TRANSFER = "candidate_transfer";


}
