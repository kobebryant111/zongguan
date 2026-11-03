package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;

import java.util.List;

/**
 * 部门外包树
 */
@Data
public class CommDepAndOutsTreeDto {
    /**
     * 上级部门
     */
    private Integer adminid;
    /**
     * 部门编号
     */
    private Integer depid;
    /**
     * 部门编码
     */
    private String depcode;
    /**
     * 部门名称
     */
    private String location;
    /**
     * 机构id
     */
    private Integer compid;
    /**
     * 编码
     */
    private String objectcode;
    /**
     * 等级
     */
    private String objectgrade;
    /**
     * 节点
     */
    private List<PpmOutsourcingBasicDto> children;
}
