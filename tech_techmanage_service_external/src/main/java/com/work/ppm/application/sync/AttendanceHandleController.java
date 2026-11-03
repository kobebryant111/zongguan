package com.work.ppm.application.sync;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.services.workflowmanager.IAttendanceHandleService;
import com.work.ppm.commons.sdk.OpenSdk;
import com.work.ppm.commons.utils.JsonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 考勤相关处理 外部系统访问接口
 */
@RestController
@RequestMapping("/attendanceHandle")
@Slf4j
@Api(value = "data-attendanceHandle", tags = "考勤对接手机工作平台")
public class AttendanceHandleController {

    @Autowired
    private IAttendanceHandleService attendanceHandleService;

    /*
     * 测试接口
     * @param upBody 请求报文 密文
     * @return ret

    @PostMapping("/common/kqTest")
    public String workflowTest(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("考勤对接手机工作平台通用处理接口请求报文明文:{}",body);

        Map<String,Object> inMap = JsonUtil.jsonToMap(body);
        String type = inMap.get("type").toString();
        inMap.remove("type");
        Map<String,Object> map;
        String ret = "";
        if("detail".equals(type)){
            map = attendanceHandleService.queryAbnormalAttendanceDetail(inMap.get("pmId").toString());
            ret = JSONObject.toJSONString(map,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteMapNullValue);
        }

        if("handle".equals(type)){
            PpmKqRecordDto kqRecordDto = JsonUtil.jsonToClass(inMap, PpmKqRecordDto.class);
            map = attendanceHandleService.abnormalAttendanceHandle(kqRecordDto);
            ret = JsonUtil.toJSONString(map);
        }

        //返回报文
        log.info("考勤对接手机工作平台通用处理接口返回报文明文:{}",ret);
        return ret;
    }*/


    /**
     * 异常考勤处理
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/abnormal/handle")
    public String abnormalHandle(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("考勤对接手机工作平台异常考勤处理接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("考勤对接手机工作平台异常考勤处理接口请求报文明文:{}",bodyDe);

        //处理流程
        PpmKqRecordDto kqRecordDto = JsonUtil.jsonToClass(bodyDe, PpmKqRecordDto.class);
        Map<String,Object> map = attendanceHandleService.abnormalAttendanceHandle(kqRecordDto);
        //加密返回报文
        String ret = JsonUtil.toJSONString(map);
        log.info("考勤对接手机工作平台异常考勤处理接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("考勤对接手机工作平台异常考勤处理接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 异常考勤详情
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/abnormal/detail")
    public String abnormalDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("考勤对接手机工作平台异常考勤详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("考勤对接手机工作平台异常考勤详情接口请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = attendanceHandleService.queryAbnormalAttendanceDetail(inMap.get("pmId").toString());

        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
                );
        log.info("考勤对接手机工作平台异常考勤详情接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("考勤对接手机工作平台异常考勤详情接口返回报文密文:{}",retEn);
        return retEn;
    }





}
