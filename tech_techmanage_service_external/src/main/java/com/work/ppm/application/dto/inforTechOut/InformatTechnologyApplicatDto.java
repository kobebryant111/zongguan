package com.work.ppm.application.dto.inforTechOut;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.work.ppm.application.model.inforTechOut.InforOutsourcFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InformatTechnologyApplicatDto {
    /**
     * id
     */
    @TableId(value = "ID",type = IdType.UUID)
    private String id;
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
    private String outReqName;
    /**
     * 申请部门负责人
     */
    private String alctionDirector;
    /**
     * 外包用工部门
     */
    private String outDepid;
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

}
