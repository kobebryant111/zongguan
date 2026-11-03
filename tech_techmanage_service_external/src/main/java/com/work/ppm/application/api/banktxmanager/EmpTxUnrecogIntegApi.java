package com.work.ppm.application.api.banktxmanager;

import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.banktxmanager.IntegInfo;
import com.work.ppm.application.services.banktxmanager.IEmpTxUnrecogIntegService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 行员调休积分API接口
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"EmpTxUnrecogIntegApi"})
@RestController
@RequestMapping("/empTxUnrecogInteg")
@Slf4j
public class EmpTxUnrecogIntegApi extends BaseApi {

    /**
     * 未确认积分信息服务接口
     */
    @Autowired
    private IEmpTxUnrecogIntegService iEmpTxUnrecogIntegService;

    /**
     * 分页查询
     * @param integInfoDto 对象
     * @param pager        分页对象
     * @param request      request
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryPager(IntegInfoDto integInfoDto, Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        iEmpTxUnrecogIntegService.queryPager(integInfoDto , pager, user);

        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 积分信息表数据删除
     * @param integInfo
     * @return ApiResult
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "积分信息删除", notes = "积分信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "detailId", value = "积分信息主键", dataType = "String", paramType = "query"),
    })
    public ApiResult  delete(@RequestBody IntegInfo integInfo) {
        ApiResult<Object> result = new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(integInfo.getDetailId())) {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("信息ID不能为空!");
            return result;
        }
        int i = iEmpTxUnrecogIntegService.delete(integInfo.getDetailId());
        if(i > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("数据删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("数据删除失败");
        }
        return result;
    }


    /**
     * 保存积分信息
     * @param integInfoDto
     * @return ApiResult
     */
    @PostMapping(value = "/saveInfo")
    @ApiOperation(value = "保存积分登记信息", notes = "保存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empName", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nameBadge", value = "姓名工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "registerData", value = "登记日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "workType", value = "工作分类", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "integReason", value = "原因", dataType = "String", paramType = "query"),
    })
    public ApiResult  saveInfo(IntegInfoDto integInfoDto) {
        ApiResult<Object> result = new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(integInfoDto.getDetailId())) {
          int i = iEmpTxUnrecogIntegService.insertInfo(integInfoDto);
            if (i == 2) {
                result.setStatus(ApiCode.DELETE_FAIL);
                result.setMessage("您今年的积分累计已达10分上限");
            } else if(i > 0){
                result.setStatus(ApiCode.SUCCESS);
                result.setMessage("信息添加成功");
            } else if (i < 0) {
                result.setStatus(ApiCode.DELETE_FAIL);
                result.setMessage("结束时间不正确,不可超过凌晨5点!");
            } else {
                result.setStatus(ApiCode.DELETE_FAIL);
                result.setMessage("信息添加失败,可能已经添加过该职员信息或可能出现异常!");
            }
        }else{
            int i = iEmpTxUnrecogIntegService.saveInfo(integInfoDto);
            if (i == 2) {
                result.setStatus(ApiCode.DELETE_FAIL);
                result.setMessage("信息保存失败, 您今年的积分累计已达10分上限!");
            } else if(i > 0){
                result.setStatus(ApiCode.SUCCESS);
                result.setMessage("信息保存成功!");
            } else if (i < 0) {
                result.setStatus(ApiCode.DELETE_FAIL);
                result.setMessage("信息保存失败,结束时间不正确,不可超过凌晨5点!");
            } else {
                result.setStatus(ApiCode.DELETE_FAIL);
                result.setMessage("信息保存失败!");
            }
        }
        return result;
    }

    /**
     * 批量提交
     * @return ApiResult
     */
    @PostMapping(value = "/submitInfo")
    @ApiOperation(value = "提交积分信息", notes = "提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "积分表主键集合", dataType = "string", paramType = "update"),
    })
    public ApiResult submitInfo (String ids) {
        ApiResult result =new ApiResult<>();
        int i = iEmpTxUnrecogIntegService.submitInfo(ids);
        if(i > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("信息保存成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("信息保存失败");
        }
        return result;
    }




    /**
     * 积分信息表数据提交
     * @param integInfo
     * @return ApiResult
     */
    @PostMapping(value = "/submit")
    @ApiOperation(value = "积分信息提交", notes = "积分信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "detailId", value = "积分信息主键", dataType = "String", paramType = "query"),
    })
    public ApiResult  submit(@RequestBody IntegInfo integInfo) {
        ApiResult<Object> result = new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(integInfo.getDetailId())) {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("信息ID不能为空!");
            return result;
        }
        int i = iEmpTxUnrecogIntegService.submit(integInfo.getDetailId());
        if(i > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 批量提交
     * @return ApiResult
     */
    @PostMapping(value = "/judgIsHoliday")
    @ApiOperation(value = "判断是否是节假日", notes = "提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "string", paramType = "query"),
    })
    public ApiResult judgIsHoliday (String startTime) {
        ApiResult result =new ApiResult<>();
        String isHoliday = iEmpTxUnrecogIntegService.judgIsHoliday(startTime);

        result.setResults(isHoliday);
        result.setStatus(ApiCode.SUCCESS);
        result.setMessage("信息保存成功");
        return result;
    }

}
