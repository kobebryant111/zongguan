package com.work.ppm.application.sync;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncDto;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierAssessService;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncService;
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

@RestController
@RequestMapping("/suppliers")
@Slf4j
@Api(value = "data-suppliersSync", tags = "供应商信息同步接口")
public class SupplierSyncController {

    @Autowired
    private IPpmSuppliersSyncService ppmSuppliersSyncService;
    @Autowired
    private IPpmSupplierAssessService ppmSupplierAssessService;

    /**
     * @param upBody 请求报文 密文接口
     * @return String
     */
    @PostMapping("/suppliersSync")
    public String suppliersSync(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("供应商信息同步接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("供应商信息同步接口请求报文明文:{}",bodyDe);
        PpmSuppliersSyncDto ppmSuppliersSyncDto = JsonUtil.jsonToClass(bodyDe,PpmSuppliersSyncDto.class);
        Map<String,Object> map = ppmSuppliersSyncService.syncSupplierData(ppmSuppliersSyncDto);

        String ret = JsonUtil.toJSONString(map);
        log.info("供应商信息同步接口返回报文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("供应商信息同步接口报文密文:{}",retEn);
        return retEn;
    }

    /*
     * @param upBody 请求报文 明文接口
     * @return String

    @PostMapping("/suppliersSyncTest")
    public String suppliersSyncTest(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.debug("供应商信息同步接口请求报文明文:{}",body);
        PpmSuppliersSyncDto ppmSuppliersSyncDto = JsonUtil.jsonToClass(body,PpmSuppliersSyncDto.class);
        Map<String,Object> map = ppmSuppliersSyncService.syncSupplierData(ppmSuppliersSyncDto);

        String ret = JsonUtil.toJSONString(map);
        log.debug("供应商信息同步接口返回报文:{}",ret);
        return ret;
    }*/


    /*
     * @param upBody 测试接口
     * @return ApiResult
    @PostMapping("/pjxxTest")
    public ApiResult pjxxTest(@RequestBody JSONObject upBody){
        ApiResult result = new ApiResult();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(ppmSupplierAssessService.handSupplierAssess(upBody));
        return result;
    }*/

}
