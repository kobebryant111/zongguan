package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

import java.util.Date;

/**
 * 岗位
 */
@Data
public class CommJobDto {
    /**
     *	中文名称
     **/
    private String location;
    /**
     *	  所属部门
     **/
    private Integer depid;
    /**
     *	更新标示 0.hr同步数据  1. 本地新增 2.本地更新
     **/
    private String updateflg;
    /**
     *	JobID  岗位代码
     **/
    private Integer jobid;
    /**
     *	是否失效
     **/
    private String isdisabled;
    /**
     *	所属机构
     **/
    private Integer compid;
    /**
     *	最后更新时间
     **/
    private Date lastupdatedate;
    /**
     *	所属条线
     **/
    private Integer boardline;
    /**
     *	JobID  岗位代码
     (HR)
     **/
    private Integer jobidhr;
    /**
     *	  是否主持工作
     **/
    private String isprework;
    /**
     *	岗位编码
     **/
    private String jobcode;
    /**
     *	  备注
     **/
    private String remark;
    /**
     *	失效日期
     **/
    private Date disableddate;
    /**
     *	生效日期
     **/
    private Date effectdate;

}
