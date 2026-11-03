package com.work.ppm.application.dto.inforTechOut;

import java.util.Date;
import java.util.List;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.inforTechOut.InforOutsourcFile;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;


@Data
public class PpmOutsourcingXklx {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 流程实例ID
     */
    private String instanceid;

    /**
     * 创建人
     */
    private String createid;
    /**
     * 创建人
     */
    private String userId;

    /**
     * 创建时间
     */
    private String createdate;

    /**
     * 标题
     */
    private String title;

    /**
     * 流程ID
     */
    private String flowid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 状态
     */
    private Integer flag;

    /**
     * 意见
     */
    private String remark;

    /**
     * 申请人名称
     */
    private String empname;

    /**
     * 申请人工号
     */
    private String badge;

    /**
     * 申请时间
     */
    private Date alctionTime;

    /**
     * 申请部门ID
     */
    private String alctionDepid;

    /**
     * 申请部门名称
     */
    private String alctionLocation;

    /**
     * 外包需求编号
     */
    private String outReqNum;

    /**
     * 外包类别
     */
    private String outType;

    /**
     * 外包需求名称
     */
    private String outreqName;

    /**
     * 申请部门负责人
     */
    private String alctionDirector;

    /**
     * 外包用工部门
     */
    private String outDepid;

    /**
     * 外包用工部门名称
     */
    private String outLocation;

    /**
     * 用工部门负责人名称
     */
    private String outDirector;

    /**
     * 用工部门负责人工号
     */
    private String outDirectorBadge;

    /**
     * 外包需求简要说明
     */
    private String outreqExplan;
    /**
     * 外包业务需求审批报告
     */
    private List<InforOutsourcFile> fileList;
    /**
     * 外包业务需求审批报告
     */
    private List<OssObjectPo> objectPosList;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;
}
