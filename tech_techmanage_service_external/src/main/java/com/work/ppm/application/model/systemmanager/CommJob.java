package com.work.ppm.application.model.systemmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 描述
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommJob {
    /**
     * JobID  岗位代码
     **/
    @Id
    private Long jobid;
    /**
     * 岗位编码
     **/
    private String jobcode;
    /**
     * 中文名称
     **/
    private String location;
    /**
     * 所属机构
     **/
    private Long compid;
    /**
     * 所属部门
     **/
    private Long depid;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 生效日期
     **/
    private Date effectdate;
    /**
     * 是否失效
     **/
    private String isdisabled;
    /**
     * 失效日期
     **/
    private Date disableddate;
    /**
     * 更新标示 0.hr同步数据  1. 本地新增 2.本地更新
     **/
    private String updateflg;
    /**
     * 最后更新时间
     **/
    private Date lastupdatedate;
    /**
     * JobID  岗位代码
     * (HR)
     **/
    private Long jobidhr;
    /**
     * 是否主持工作
     **/
    private String isprework;
    /**
     * 所属条线
     **/
    private String boardline;
}
