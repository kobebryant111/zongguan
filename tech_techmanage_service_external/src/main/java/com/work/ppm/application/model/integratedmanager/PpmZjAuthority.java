package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 闸机权限管理 实体对象
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PpmZjAuthority {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 流程实例ID
     **/
    private String instanceid;
    /**
     * 闸机ID
     **/
    private String ppmZjId;
    /**
     * 闸机
     **/
    private String zjname;
    /**
     * 工号
     **/
    private String badge;
    /**
     * 姓名
     **/
    private String empname;
    /**
     * 证件类型
     **/
    private String certificateType;
    /**
     * 证件号码
     **/
    private String certificateNumber;
    /**
     * 所属部门
     **/
    private Long depid;
    /**
     * 所属部门
     **/
    private String depname;
    /**
     * 员工状态
     **/
    private String empstatus;
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
     * 状态（-1:已失效,0:待生效，1:已生效）
     **/
    private Integer flag;
}
