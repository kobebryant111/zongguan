package com.work.ppm.application.model.rdmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName com.work.ppm.application.model.rdmanager
 *
 * @version JDK 8
 * @className PersonAttendance
 * @date 2025/3/5
 * @description TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonAttendance {
    /**
     * 员工代码
     **/
    private Integer eid;
    /**
     * 工号
     **/
    private String badge;
    /**
     * 员工状态
     **/
    private String empstatus;
    /**
     * 0.全职 1.兼职1（85%以上)、2兼职研发2(80%以上)、3兼职研发3 (50%-70%)
     */
    private String nature;
    /**
     * 姓名
     */
    private String username;
    /**
     * 项目考勤
     */
    private List<HrProject> projects;

}
