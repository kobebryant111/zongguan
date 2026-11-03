package com.work.ppm.application.dto.workflowmanager.attendance;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AbnormalAttendanceDetailDto {

    /**
     * 编号
     */
    private String id;
    /**
     * 人员编号
     */
    private String jobId;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 部门编号
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 考勤日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date attendanceDate;
    /**
     * 打卡时间
     */
    @JSONField(format = "HH:mm:ss")
    private Date punchTime;
    /**
     * 异常类型
     */
    private String dataType;
    /**
     * 系统判断异常原因
     */
    private String autoAbnormalReason;
    /**
     * 异常原因
     */
    private String abnormalReason;
    /**
     * 是否处理
     */
    private String whetherHandle;
    /**
     *  异常原来可选列表
     */
    private List abnormalReasonList;

    /**
     *  异常原来可选列表
     */
    private List<Map> reasonSelect;
    /**
     * 备注
     */
    private String remark;


}
