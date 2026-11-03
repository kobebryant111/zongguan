package com.work.ppm.application.sync;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.esb.req.PpmKqFaceDataEsbReqDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceDataDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceDataService;
import com.work.ppm.commons.utils.JsonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/face")
@Slf4j
@Api(value = "data-faceSync", tags = "人脸信息同步接口")
public class FaceDataSyncController {

    @Autowired
    private IPpmKqFaceDataService ppmKqFaceDataService;

    /**
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/dataSync")
    public Map dataSync(@RequestBody JSONObject upBody) {
        log.info("--------------------------hr推送考勤信息同步开始-----------------------------");
        String body = JsonUtil.toJSONString(upBody.get("BODY"));
        log.info("员工考勤信息同步入参:{}",body);
        PpmKqFaceDataEsbReqDto ppmKqFaceDataEsbDto = JsonUtil.jsonToClass(body, PpmKqFaceDataEsbReqDto.class);
        PpmKqFaceDataDto ppmKqFaceDataDto= new PpmKqFaceDataDto();
        ppmKqFaceDataDto.setIpAddress(ppmKqFaceDataEsbDto.getIPAdr());
        ppmKqFaceDataDto.setCardNO(ppmKqFaceDataEsbDto.getEmpNo());
        ppmKqFaceDataDto.setPunchTime(ppmKqFaceDataEsbDto.getChkInTm());
        Map<String,Object> map = ppmKqFaceDataService.syncKqFaceData(ppmKqFaceDataDto);
        log.info("人脸考勤同步接口返回报文:{}",map);
        log.info("--------------------------hr推送考勤信息同步结束-----------------------------");
        return map;
    }

    /**
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/dataSyncTest")
    public String dataSyncTest(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("人脸考勤同步接口请求报文明文:{}",body);
        PpmKqFaceDataDto ppmKqFaceDataDto = JsonUtil.jsonToClass(body,PpmKqFaceDataDto.class);
        Map<String,Object> map = ppmKqFaceDataService.syncKqFaceData(ppmKqFaceDataDto);

        String ret = JsonUtil.toJSONString(map);
        log.info("人脸考勤同步接口返回报文:{}",ret);
        return ret;
    }

}
