package com.work.ppm.application.services.external;

import com.work.ppm.application.dto.esb.req.PpmKqFaceEsbReqDto;
import com.work.ppm.application.dto.esb.req.PpmKqOutsourcingDto;
import com.work.ppm.commons.annotaion.esb.EsbBody;
import com.work.ppm.commons.annotaion.esb.EsbConfig;
import com.work.ppm.commons.annotaion.esb.EsbService;

/**
 * 回访相关接口
 */
@EsbService
public interface CallEsbService {

    /**
     * 考勤机信息下发
     * @param request
     * @return String
     */
    @EsbBody
    @EsbConfig(serviceCode = "12002010068",serviceScene = "31",tranCode = "operateFaceSetInfo2")
    public String datasyncIn(PpmKqFaceEsbReqDto request);

    /**
     * 工作平台入场审批通知
     * @param request
     * @return
     */
    @EsbBody
    @EsbConfig(serviceCode = "12002010024",serviceScene = "08",tranCode = "sendMessage")
    public String sendMessage(PpmKqOutsourcingDto request);

}
