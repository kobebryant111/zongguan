package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmAttendanceModify;
import lombok.Data;

/**
 * 外包考勤修改扩展
 */
@Data
public class PpmAttendanceModifyInfo extends PpmAttendanceModify {
    /**
     * 部门名称
     */
    private String deptName;
}
