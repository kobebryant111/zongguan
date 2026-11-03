package com.work.ppm.application.model.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemListDto {
    /**
     * id
     */
    private String id;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统编号
     */
    private String systemNo;
    /**
     * 运维负责人A岗姓名
     */
    private String nameResponsible;
    /**
     * 运维负责人A岗工号
     */
    private String jobidResponsible;
//    /**
//     * 运维负责人B岗姓名
//     */
//    private String omRpbBName;
//    /**
//     * 运维负责人B岗工号
//     */
//    private String omRpbBJobid;
//    /**
//     * 研发负责人A岗姓名
//     */
//    private String rdRpbAName;
//    /**
//     * 研发负责人A岗工号
//     */
//    private String rdRpbAJobid;
//    /**
//     * 研发负责人B岗姓名
//     */
//    private String rdRpbBName;
//    /**
//     * 研发负责人B岗工号
//     */
//    private String rdRpbBJobid;

}
