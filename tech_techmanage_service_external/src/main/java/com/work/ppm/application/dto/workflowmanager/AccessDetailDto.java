package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.CommonDetailDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude
public class AccessDetailDto extends CommonDetailDto {

    /**
     *  当前处理人编号
     */
    String currentUserId;
    /**
     *  当前处理人名称
     */
    String currentUserName;
    /**
     *  系统编号
     */
    String jobId;
    /**
     *  姓名
     */
    String userName;
    /**
     *  外包商编码
     */
    String outsourceNo;
    /**
     *  外包商名称
     */
    String outsourceName;
    /**
     *  入职日期
     */
    Date entryTime;
    /**
     *  岗位
     */
    String positionName;
    /**
     *  项目经理
     */
    String pmId;
    /**
     *  项目经理名称
     */
    String pmName;
    /**
     *  项目名称
     */
    String projectName;
    /**
     *  系统名称
     */
    String systemName;
    /**
     *  外包方式名称
     */
    String outsourceTypeName;
    /**
     *  外包方式编码
     */
    String outsourceType;
    /**
     *  照片相关
     */
    String faceimage;
    /**
     *  流程日志
     */
    List<DetailFlowLogDto> flowLog;

}
