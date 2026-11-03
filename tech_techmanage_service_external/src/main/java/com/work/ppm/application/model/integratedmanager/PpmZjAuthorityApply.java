package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 闸机权限申请表 实体对象
 *
 */
@Data
public class PpmZjAuthorityApply {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 闸机ID
     **/
    private String ppmZjId;
    /**
     * 流程实例ID
     **/
    private String instanceid;
    /**
     *	业务类型
     **/
    private Integer bizType;
    /**
     * 标题
     **/
    private String title;
    /**
     * 流程ID
     **/
    private String flowid;
    /**
     * 所属部门
     **/
    private Integer depid;
    /**
     * 申请类型
     **/
    private Integer applyType;
    /**
     * 开始时间
     **/
    private Date beginDate;
    /**
     * 结束时间
     **/
    private Date endDate;
    /**
     * 创建人
     **/
    private String createid;
    /**
     * 创建时间
     **/
    private Date createdate;
    /**
     * 完成时间
     **/
    private Date completedate;
    /**
     * 状态（-1:已取消,0:申请中，1:已完成）
     **/
    private Integer flowflag;
}
