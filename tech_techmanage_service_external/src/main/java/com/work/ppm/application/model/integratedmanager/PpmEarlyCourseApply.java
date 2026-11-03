package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 提前入场申请
 */
@Data
public class PpmEarlyCourseApply {

    /**
     *	id
     **/
    @Id
    private Long id;
    /**
     *	部门编号 DEPTNO
     **/
    private String deptNo;
    /**
     *	流程编号 INSTANCEID
     **/
    private String instanceid;
    /**
     *	申请人 APPLYER
     **/
    private String applyer;
    /**
     *	流程状态码 -1 已关闭 0 未发起 1已发起 STATE
     **/
    private String state;
    /**
     *	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示 STATE_NAME
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
     *	创建时间
     **/
    private Date createdTime;
    /**
     *	创建人
     **/
    private String createdBy;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 修改人
     */
    private String modifiedBy;

}
