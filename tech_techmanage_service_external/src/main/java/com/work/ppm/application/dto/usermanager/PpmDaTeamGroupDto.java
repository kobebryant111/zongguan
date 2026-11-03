package com.work.ppm.application.dto.usermanager;

import lombok.Data;

/**
 * dto
 */
@Data
public class PpmDaTeamGroupDto {
    /**
     *	小组编号
     **/
    private String id;
    /**
     *	小组名称
     **/
    private String groupname;
    /**
     *	小组职责描述
     **/
    private String detail;
    /**
     *	小组长
     **/
    private String groupuserid;
    /**
     *	小组类型
     **/
    private String type;
    /**
     *	是否跨部门
     **/
    private String isotherdep;
    /**
     *	团队编号
     **/
    private String teamid;
    /**
     *	全部团队编号
     **/
    private String teamids;
    /**
     *	全部团队编号名称
     **/
    private String teamidnames;
    /**
     *	删除标志0正常 1删除
     **/
    private String isdel;
    /**
     *	 管理项目，需求，概念申报编号
     **/
    private String projectno;
    /**
     *	 管理项目，需求，概念申报名称
     **/
    private String projectname;
    /**
     *	是否为大中型项目研发小组
     **/
    private String isbigproject;
    /**
     *	所属部门
     **/
    private String depid;
    /**
     *	小组分管经理
     **/
    private String teammanager;
    /**
     *	小组QA
     **/
    private String groupqauserid;
}
