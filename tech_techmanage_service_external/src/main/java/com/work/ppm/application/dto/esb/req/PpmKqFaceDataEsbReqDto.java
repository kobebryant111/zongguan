package com.work.ppm.application.dto.esb.req;


import lombok.Data;

/**
 *  人脸考勤数据对象 传输对象-esb
 */
@Data
public class PpmKqFaceDataEsbReqDto {
    /**
     * 考勤机ip
     */
    private String IPAdr;
    /**
     * 工号
     */
    private String EmpNo;
    /**
     * 打卡时间
     */
    private String ChkInTm;
}
