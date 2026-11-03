package com.work.ppm.application.dto.projectmanager;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 项目管理树
 */
@Data
public class PpmProjectBuildSoftTreeDto {
    /**
     * id
     */
    private String pid;
    /**
     *	字段名
     **/
    private String busiuserid;
    /**
     * 项目编号
     */
    private String projectno;
    /**
     * 项目名称
     */
    private String projectname;
    /**
     * 负责人id
     */
    private String manauserid;
    /**
     * 负责人名称
     */
    private String manausername;
    /**
     * 名称
     */
    private String empname;
    /**
     * 时间
     */
    private Date expectdate;
    /**
     * 系统编号
     */
    private String sysno;
    /**
     * 系统名称
     */
    private String sysname;
    /**
     * 节点
     */
    private List<PpmProjectBuildSoftTreeDto> children;

    /**
     * 项目属性 01.正常，02.提前入场，03.挂起，04.离场，05.留场旧项目运维，06.留场新项目开发
     */
    private String proattrbutes;
}
