package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 闸机人员信息 实体对象
 *
 */
@Data
public class PpmZjPerson {
    /**
     * 人员ID
     **/
    @Id
    private String personId;
    /**
     * 姓名
     **/
    private String personName;
    /**
     * 拼音
     **/
    private String pinyin;
    /**
     * 性别，1：男，2：女，0：未知
     **/
    private Integer gender;
    /**
     * 所属组织唯一标识码
     **/
    private String orgIndexCode;
    /**
     * 所属组织名称
     **/
    private String orgPath;
    /**
     * 证件类型
     **/
    private Integer certificateType;
    /**
     * 证件号码
     **/
    private String certificateNo;
    /**
     * 工号
     **/
    private String jobNo;
    /**
     * 年龄
     **/
    private Integer age;
    /**
     * 面部识别
     **/
    private Integer faceNum;
    /**
     * 指纹编号
     **/
    private Integer fingerPrintNum;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 更新时间
     **/
    private Date updateTime;
}
