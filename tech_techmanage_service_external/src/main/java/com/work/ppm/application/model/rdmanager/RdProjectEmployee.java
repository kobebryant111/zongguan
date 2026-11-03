package com.work.ppm.application.model.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RdProjectEmployee {
    /**
     *项目编号
     */
    private String projectCode;
    /**
     * 代码编号
     */
    private String eidCode;
    /**
     * 员工代码
     */
    private Integer eid;
    /**
     * 姓名
     */
    private String empname;
    /**
     * 到岗日期
     */
    private String arrivalTime;

    /**
     * 离岗日期
     */
    private String leaveTime;

}
