package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeScoreinfoDto;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeScoreinfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 外包面试官
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmIntervieweeScoreinfo"}, description = "外包面试官")
@RestController
@RequestMapping("ppmIntervieweeScoreinfo")
@Slf4j
public class PpmIntervieweeScoreinfoApi extends BaseUploadApi {
    /**
     * 面试官接口
     */
    @Autowired
    private IPpmIntervieweeScoreinfoService ppmIntervieweeScoreinfoService;

    /**
     * 获取面试官成绩信息
     *
     * @param instanceId 流程id
     * @return ApiResult
     */
    @PostMapping("queryInterviewer")
    @ApiOperation(value = "获取面试官成绩信息")
    @ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "string", paramType = "query", required = true)
    public ApiResult<List<PpmIntervieweeScoreinfoDto>> queryInterviewer(@PathVariable String instanceId) {
        ApiResult<List<PpmIntervieweeScoreinfoDto>> result = new ApiResult();
        List<PpmIntervieweeScoreinfoDto> list = ppmIntervieweeScoreinfoService.getInterviewer(instanceId);
        result.setStatus(ApiCode.SUCCESS);
        result.success("获取成功");
        result.setResults(list);
        return result;
    }

}
