package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.services.integratedmanager.IPpmFacemachineConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 外包准入流程图片处理类
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFacemachineConfigApi"},description="外包准入流程图片处理类")
@RestController
@RequestMapping("ppmFacemachineConfig")
@Slf4j
public class PpmFacemachineConfigApi extends BaseApi {
    /**
     * 外包准入流程图片处理类
     */
    @Autowired
    private IPpmFacemachineConfigService ppmFacemachineConfigService;
    /**
     *  外包准入流程发送图片
     * @param badge  工号
     * @param empName  姓名
     * @param faceimage  图片编号
     * @return ApiResult<String>
     */
    @PostMapping("sendPhotoToDevice")
    @ApiOperation("外包准入流程发送图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "empName", value = "姓名", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "faceimage", value = "图片编号", dataType = "string", paramType = "query",required = true),
    })
    public ApiResult<String> sendPhotoToDevice( String badge, String empName, String faceimage) {
        ApiResult<String> result =new ApiResult<>();
        String msg=ppmFacemachineConfigService.sendPhotoToDevice(badge, empName, faceimage);
        result.success(msg);
        return  result;
    }

}
