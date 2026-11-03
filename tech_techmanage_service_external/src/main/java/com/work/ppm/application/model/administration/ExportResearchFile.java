package com.work.ppm.application.model.administration;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * WorkResearchFile
 *
 */
@Data
@ExcelTarget("ExportResearchFile")
public class ExportResearchFile {
    /**
     *	部门
     **/
    @Excel(name = "部门", orderNum = "1", width = 20)
    private String deptName;
    /**
     *	员工 USER_NAME
     **/
    @Excel(name = "员工", orderNum = "2")
    private String userName;

    /**
     *	工号 BADGE
     **/
    @Excel(name = "工号", orderNum = "3")
    private String badge;

    /**
     *	参与项目 PROJECT_NAME
     **/
    @Excel(name = "参与项目", orderNum = "4", width = 40)
    private String projectName;

    /**
     *	参与项目状态 PROJECT_STATE
     **/
    @Excel(name = "参与项目状态", orderNum = "5", replace = {"立项中_0","进行中_1","收尾中_2","暂停_3","废止_4","已完结_9"})
    private String projectState;

    /**
     *	文档上传状态 UPLOAD_STATE
     **/
    @Excel(name = "文档上传状态",orderNum = "6")
    private String uploadState;

    /**
     *	文件名称 FILE_NAME
     **/
    @Excel(name = "文件名称", orderNum = "7", width = 30)
    private String fileName;

    /**
     *	文件类型 TYPE
     **/
    @Excel(name = "文件类型", orderNum = "8", replace = {"需求文档_01","设计文档_02","测试报告_03","验收报告_04","其他_05","_null"})
    private String  type;

    /**
     *	上传时间 CREATE_DATE
     **/
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "上传时间", orderNum = "9", exportFormat = "yyyy/MM/dd")
    private Date  createDate;
}
