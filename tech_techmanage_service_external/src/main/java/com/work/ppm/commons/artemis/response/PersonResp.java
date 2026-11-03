package com.work.ppm.commons.artemis.response;

import lombok.Data;

import java.util.Date;

/**
 * 人员列表对象V2
 */
@Data
public class PersonResp {
    // 人员ID
    private String personId;
    // 姓名
    private String personName;
    // 拼音
    private String pinyin;
    // 性别，1：男，2：女，0：未知
    private Integer gender;
    // 所属组织唯一标识码
    private String orgIndexCode;
    // 所属组织名称，@进行分割，如@默认部门@
    private String orgPath;
    // 证件类型
    private Integer certificateType;
    // 证件号码
    private String certificateNo;
    // 工号
    private String jobNo;
    // 年龄
    private Integer age;
    // 面部识别
    private Integer faceNum;
    // 指纹编号
    private Integer fingerprintNum;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
