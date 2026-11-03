package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 提前入场申请
 */
@Data
public class PpmEarlyCourseApplyDto {

    /**
     *	id
     **/
    private Long id;
    /**
     *	部门编号 DEPTNO
     **/
    private String deptNo;
    /**
     *	部门编号 DEPT_NAME
     **/
    private String deptName;
    /**
     *	流程编号 INSTANCEID
     **/
    private String instanceid;
    /**
     *	申请人 APPLYER
     **/
    private String applyer;
    /**
     *	申请人名称 APPLYER
     **/
    private String applyerName;
    /**
     *	流程状态码 -1 已关闭 0 未发起 1已发起 REMARK
     **/
    private String state;
    /**
     *	流程状态码名称 -1 已关闭 0 未发起 >1 根据实际流程节点展示 REMARK
     **/
    private String stateName;
    /**
     *	当前处理人编号 CURRENT_ID
     **/
    private String currentId;
    /**
     *	当前处理人名称 CURRENT_NAME
     **/
    private String currentName;
    /**
     *	创建时间 CREATED_TIME
     **/
    private Date createdTime;
    /**
     *	创建人 CREATED_BY
     **/
    private String createdBy;
    /**
     * 修改时间 MODIFIED_TIME
     */
    private Date modifiedTime;
    /**
     * 修改人 MODIFIED_BY
     */
    private String modifiedBy;


    /**
     * bps
     */
    private BpsWorkFlowDto bps;
    /**
     * bps
     */
    private List<PpmEarlyCourseApplyInfoDto> applyInfoDtos;

    /**
     * 流程定义名称
     **/
    private String flowId;

}
