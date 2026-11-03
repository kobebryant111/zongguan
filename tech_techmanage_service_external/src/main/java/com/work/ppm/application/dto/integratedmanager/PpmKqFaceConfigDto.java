package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  考勤人脸配置表 传输对象
 */
@Data
public class PpmKqFaceConfigDto {

    /**
     * 唯一编号 ID
     **/
    @ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
    private Long id;
    /**
     * 部门编号 DEPT_ID
     **/
    @ApiModelProperty(name = "deptId", value = "部门编号", dataType = "String")
    private String deptId;
    /**
     * 工号 JOB_ID
     **/
    @ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
    private String jobId;
    /**
     *	姓名 USER_NAME
     **/
    @ApiModelProperty(name = "userName", value = "姓名", dataType = "String")
    private String userName;
    /**
     *	图片 PICTURE
     **/
    @ApiModelProperty(name = "picture", value = "图片", dataType = "String")
    private String picture;
    /**
     *	下发状态 0未下发 1已下发 ISSUE_STATE
     **/
    @ApiModelProperty(name = "issueState", value = "下发状态 0未下发 1已下发", dataType = "String")
    private String issueState;
    /**
     *	下发时间 ISSUE_TIME
     **/
    @ApiModelProperty(name = "issueTime", value = "下发时间", dataType = "String")
    private Date issueTime;
    /**
     *	类型 TYPE
     **/
    @ApiModelProperty(name = "type", value = "类型", dataType = "String")
    private String type;

    /**
     * 已下发考勤机
     */
    private String issueMachine;

    /**
     * 已下发考勤机 数组
     */
    private String[] issueMachineArray;

    /**
     *	预览照片路径 PICTURE_URL
     **/
    private String pictureUrl;

    /**
     * 实际路径
     */
    private String stockpath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     *	部门名称 DEPT_NAME
     **/
    private String deptName;

    /**
     * 是否成功
     */
    private Boolean retCode;
    /**
     * 返回msg
     */
    private String retMsg;
    /**
     * 在场情况
     */
    private String presenceState;
    /**
     * 在岗状态
     */
    private String state;

    /**
     * 父部门编号
     */
    private Integer adminId;

}
