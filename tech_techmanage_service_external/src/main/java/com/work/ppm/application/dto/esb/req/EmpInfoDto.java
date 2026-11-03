package com.work.ppm.application.dto.esb.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 员工信息数组
 */
@Data
@XStreamAlias("EmpInfArray")
public class EmpInfoDto {
    /**
     * 员工工号
     */
    @XStreamAlias("EmpNo")
    private String EmpNo;
    /**
     * 员工名称
     */
    @XStreamAlias("EmpNm")
    private String EmpNm;
    /**
     * 人脸照片
     */
    @XStreamAlias("FcPhoto")
    private byte[] FcPhoto;
}
