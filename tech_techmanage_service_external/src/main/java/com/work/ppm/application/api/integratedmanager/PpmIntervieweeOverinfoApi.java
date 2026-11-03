package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.IntervieweeInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeOverinfoDto;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeOverinfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 外包面试归档
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ppmIntervieweeOverinfo"}, description = "外包面试归档")
@RestController
@RequestMapping("ppmIntervieweeOverinfo")
@Slf4j
public class PpmIntervieweeOverinfoApi extends BaseUploadApi {
    /**
     * 归档接口
     */
    @Autowired
    private IPpmIntervieweeOverinfoService ppmIntervieweeOverinfoService;


    /**
     * 检查人力导入excel是否存在
     * @param number 身份证
     * @return   ApiResult
     */
    @GetMapping("checkedExistEntry/{number}")
    @ApiOperation(value = "检查人力导入excel是否存在", notes = "对象存在并且instanceid存在：则说明该用户存在，反之不存在")
    @ApiImplicitParam(name = "number", value = "身份证", required = true, dataType = "string", paramType = "path")
    public ApiResult<PpmIntervieweeOverinfoDto> checkedExistEntry(@PathVariable String number) {
        ApiResult<PpmIntervieweeOverinfoDto> result = new ApiResult<>();
        PpmIntervieweeOverinfoDto item = ppmIntervieweeOverinfoService.getCheckedExistEntry(number);
        result.setStatus(ApiCode.SUCCESS);
        result.setMessage("获取成功");
        result.setResults(item);
        return result;
    }
    /**
     * 检查人力导入excel是否通过
     * @param number 身份证
     * @return   ApiResult
     */
    @GetMapping("checkedPassEntry/{number}")
    @ApiOperation(value = "检查人力导入excel是否存在", notes = "对象存在并且instanceid存在：则说明该用户存在，反之不存在")
    @ApiImplicitParam(name = "number", value = "身份证", required = true, dataType = "string", paramType = "path")
    public ApiResult<PpmIntervieweeOverinfoDto> checkedPassEntry(@PathVariable String number) {
        ApiResult<PpmIntervieweeOverinfoDto> result = new ApiResult<>();
        PpmIntervieweeOverinfoDto item = ppmIntervieweeOverinfoService.getCheckedPassEntry(number);
        result.setStatus(ApiCode.SUCCESS);
        result.setMessage("获取成功");
        result.setResults(item);
        return result;
    }


    /**
     * 根据身份证查询面试信息
     * @param idcard
     * @return IntervieweeInfoDto
     */
    @GetMapping("getIntervieweeInfo/{idcard}")
    @ApiOperation("根据身份证查询面试信息")
    @ApiImplicitParam(name = "idcard", value = "身份证", required = true, dataType = "string", paramType = "path")
    public ApiResult<IntervieweeInfoDto> getIntervieweeInfo(@PathVariable String idcard) {
        ApiResult<IntervieweeInfoDto> result =new ApiResult<>();
        IntervieweeInfoDto dto = ppmIntervieweeOverinfoService.getIntervieweeInfo(idcard);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据身份证查询面试信息
     * @param idcard
     * @return IntervieweeInfoDto
     */
    @GetMapping("getIntervieweeInfos/{idcard}")
    @ApiOperation("根据身份证查询面试信息")
    @ApiImplicitParam(name = "idcard", value = "身份证", required = true, dataType = "string", paramType = "path")
    public ApiResult<List<IntervieweeInfoDto>> getIntervieweeInfos(@PathVariable String idcard) {
        ApiResult<List<IntervieweeInfoDto>> result =new ApiResult<>();
        result.setResults(ppmIntervieweeOverinfoService.getIntervieweeInfos(idcard));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
