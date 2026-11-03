package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 闸机人员信息 传输对象
 *
 */
@Data
public class PpmZjPersonDto {
    /**
     * 人员ID
     **/
    @ApiModelProperty(name = "personId", value = "人员ID", dataType = "String")
    private String personId;
    /**
     * 姓名
     **/
    @ApiModelProperty(name = "personName", value = "姓名", dataType = "String")
    private String personName;
    /**
     * 拼音
     **/
    @ApiModelProperty(name = "pinyin", value = "拼音", dataType = "String")
    private String pinyin;
    /**
     * 性别，1：男，2：女，0：未知
     **/
    @ApiModelProperty(name = "gender", value = " 性别，1：男，2：女，0：未知", dataType = "Integer")
    private Integer gender;
    /**
     * 所属组织唯一标识码
     **/
    @ApiModelProperty(name = "orgIndexCode", value = "所属组织唯一标识码", dataType = "String")
    private String orgIndexCode;
    /**
     * 所属组织名称
     **/
    @ApiModelProperty(name = "orgPath", value = "所属组织名称", dataType = "String")
    private String orgPath;
    /**
     * 证件类型
     **/
    @ApiModelProperty(name = "certificateType", value = "证件类型", dataType = "Integer")
    private Integer certificateType;
    /**
     * 证件号码
     **/
    @ApiModelProperty(name = "certificateNo", value = "证件号码", dataType = "String")
    private String certificateNo;
    /**
     * 工号
     **/
    @ApiModelProperty(name = "jobNo", value = "工号", dataType = "String")
    private String jobNo;
    /**
     * 年龄
     **/
    @ApiModelProperty(name = "age", value = "年龄", dataType = "Integer")
    private Integer age;
    /**
     * 面部识别
     **/
    @ApiModelProperty(name = "faceNum", value = "面部识别", dataType = "Integer")
    private Integer faceNum;
    /**
     * 指纹编号
     **/
    @ApiModelProperty(name = "fingerPrintNum", value = "指纹编号", dataType = "Integer")
    private Integer fingerPrintNum;
    /**
     * 创建时间
     **/
    @ApiModelProperty(name = "createTime", value = "创建时间", dataType = "Date")
    private Date createTime;
    /**
     * 更新时间
     **/
    @ApiModelProperty(name = "updateTime", value = "更新时间", dataType = "Date")
    private Date updateTime;
}
