package com.work.ppm.application.dto.projectmanager;

import lombok.Data;


/**
 * 分摊
 *
 */
@Data
public class PpmDemandnewcostsharesDto {
    /**
     * 字段名
     **/
    private Long id;
    /**
     * 需求编号
     **/
    private String demandno;
    /**
     * 部门编号
     **/
    private String deptno;
    /**
     * 分摊比例
     **/
    private Integer proportion;
    /**
     * 机构 org,部门 dep,空的情况默认为部门
     **/
    private String orgdep;
    /**
     * 分摊确认人
     **/
    private String manageruser;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 分摊确认人名称
     */
    private String managerUserName;

}
