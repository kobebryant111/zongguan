package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 外包面试人员信息
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmIntervieweeInfoApi"},description="外包面试人员信息")
@RestController
@RequestMapping("ppmIntervieweeInfo")
@Slf4j
public class PpmIntervieweeInfoApi {
    /**
     * 外包面试人员信息 接口
     */
    @Autowired
    private IPpmIntervieweeInfoService ppmIntervieweeInfoService;

    /**
     * 查询应聘者信息
     * @param ppmIntervieweeInfo 对象
     * @return ApiResult<List<PpmIntervieweeInfoDto>>
     */
    @GetMapping(value = "getApplicantsLisr")
    @ApiOperation(value = "查询应聘者信息", notes = "查询应聘者信息")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "msname", value = "面试人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "面试人性别 0:女1:男", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workyears", value = "工作年限", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "suggestlevel", value = "建议等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbsid", value = "外包商ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jl", value = "简历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query", required = true),
			@ApiImplicitParam(name = "mspy", value = "面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "score", value = "成绩", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "interviewresult", value = "面试结果 0：通过1：不通过", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "suggestprice", value = "建议单价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reason", value = "修改理由", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<List<PpmIntervieweeInfoDto>> getApplicantsLisr(PpmIntervieweeInfo ppmIntervieweeInfo) {
        ApiResult<List<PpmIntervieweeInfoDto>> result =new ApiResult<>();
        AssertUtil.notNull(ppmIntervieweeInfo.getInstanceid(), "【instanceid】参数不能为空");
        List<PpmIntervieweeInfoDto> list = ppmIntervieweeInfoService.queryListForInstanceid(ppmIntervieweeInfo.getInstanceid());
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 面试进度
     * @param instanceid  流程实例id
     * @return ApiResult<List<PpmIntervieweeInfoDto>>
     */
    @GetMapping("getInterviewProgress/{instanceid}")
    @ApiOperation(value = "面试进度", notes = "根据流程编号查询面试进度")
    @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "string", paramType = "path", required = true)
    public ApiResult<List<PpmIntervieweeInfoDto>> getInterviewProgress(@PathVariable String instanceid) {
        ApiResult<List<PpmIntervieweeInfoDto>> result = new ApiResult<>();
        List<PpmIntervieweeInfoDto> list = ppmIntervieweeInfoService.getInterviewProgress(instanceid);
        result.setResults(list);
        result.success("查询成功");
        return result;
    }

    /**
     * 删除
     * @param ppmIntervieweeInfo 对象
     * @return ApiResult
     */
    @DeleteMapping("del")
    @ApiOperation(value = "删除", notes = "根据流程id和自增编号删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "自增编号",dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "instanceid",value = "流程id",dataType = "string", paramType = "query",required = true),
    })
    public ApiResult deleteInterviewProgress(PpmIntervieweeInfo ppmIntervieweeInfo) {
        ApiResult result = new ApiResult();
        AssertUtil.notNull(ppmIntervieweeInfo.getId(),"[id]不能为空");
        AssertUtil.notNull(ppmIntervieweeInfo.getInstanceid(),"[Instanceid]不能为空");
        int id = ppmIntervieweeInfoService.deletePpmIntervieweeInfo(ppmIntervieweeInfo);
        if(id > 0) {
            result.success("删除成功");
        } else {
            result.failure("删除失败");
        }
        return result;
    }
}
