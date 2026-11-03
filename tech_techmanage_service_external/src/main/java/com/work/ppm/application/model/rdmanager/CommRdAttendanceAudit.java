package com.work.ppm.application.model.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommRdAttendanceAudit {

    /**
     * 主键
     */
    private String id;
    /**
     * 员工代码
     */
    private Integer eid;
    /**
     * 工号
     */
    private String badge;
    /**
     * 姓名
     */
    private String username;
    /**
     * 考勤状态
     */
    private String attendanceStatus;
    /**
     * 审核人
     */
    private String auditEmployee;
    /**
     * 日期
     */
    private String attendanceDate;
    /**
     * 日期
     */
    private Data calendarDate;
    /**
     * 创建时间
     */
    private Data createDate;
    /**
     * 修改时间
     */
    private Data updateDate;
}
