package com.work.ppm.application.model.onlinemanager.ext;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.model.onlinemanager.PpmDacenterDemand;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述 科管数据中心需求管理线上化需求 Vo 对象
 */
@Data
@ExcelTarget("ppmDacenterDemandInfo")
public class PpmDacenterDemandInfo extends PpmDacenterDemand {


    /**
     *	审批状态
     **/
    @Excel(name = "流程状态", orderNum = "3")
    private String flowStatus;
    /**
     *	需求分类名称
     **/
    @Excel(name = "需求分类", orderNum = "6")
    private String demandTypeName;
    /**
     *	关联主系统名称
     **/
    @Excel(name = "关联主系统", orderNum = "4")
    private String sysName;
    /**
     *	需求部门名称
     **/
    @Excel(name = "需求部门", orderNum = "8")
    private String deptName;
    /**
     *	需求状态中文名
     **/
    @Excel(name = "需求状态", orderNum = "11")
    private String relationDemandStatusName;
    /**
     * 文档集合
     */
    private List<PpmDocumentDto> docList;
    /**
     *提出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提出时间", orderNum = "12",exportFormat = "yyyy-MM-dd")
    private Date bringUpdate;
    /**
     *立项时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "立项时间", orderNum = "14",exportFormat = "yyyy-MM-dd")
    private Date buildDate;
    /**
     *项目状态
     */
    @Excel(name = "项目状态", orderNum = "13")
    private String projectStatus;
    /**
     *上线时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上线时间", orderNum = "15",exportFormat = "yyyy-MM-dd")
    private Date actualDate;

}
