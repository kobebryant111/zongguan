package com.work.ppm.application.api.otherdeptmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.otherdeptmanager.PpmOutsourcePersonledgeDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmOutsourcePersonledgeService;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  非科技外包人员台账表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcePersonledgeApi"})
@RestController
@RequestMapping("outsourcePersonledge")
@Slf4j
public class PpmOutsourcePersonledgeApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcePersonledgeService ppmOutsourcePersonledgeService;

    /**
     * 分页
     * @param ledgeDto 对象
     * @param pager 分页对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcePersonledgeDto ledgeDto,Pager pager,HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        ppmOutsourcePersonledgeService.queryPager(ledgeDto,pager,user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcePersonledgeDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmOutsourcePersonledgeDto> queryDetail(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        ApiResult<PpmOutsourcePersonledgeDto> result =new ApiResult<>();
        PpmOutsourcePersonledgeDto item = ppmOutsourcePersonledgeService.queryDetail(ppmOutsourcePersonledgeDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     * @param ppmOutsourcePersonledgeDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    public ApiResult<Object> addPpmOutsourcePersonledge(
            @RequestBody PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcePersonledgeService.addPpmOutsourcePersonledge(ppmOutsourcePersonledgeDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcePersonledgeDto 对象
     * @return ApiResult
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmOutsourcePersonledge(
            @RequestBody PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcePersonledgeService.updatePpmOutsourcePersonledge(ppmOutsourcePersonledgeDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
            if (ppmOutsourcePersonledgeDto.getIsChangeState()){
                ppmOutsourcePersonledgeService.pushEmptyPhoto(ppmOutsourcePersonledgeDto);
            }
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 导出
     *
     * @param outsourcePersonledgeDto 对象
     * @param response             response
     * @param request              request
     */
    @PostMapping("export")
    @ApiOperation(value = "导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query")
    })
    public void exportExcel(PpmOutsourcePersonledgeDto outsourcePersonledgeDto, HttpServletRequest request,
                            HttpServletResponse response) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmOutsourcePersonledgeDto> list =
                ppmOutsourcePersonledgeService.exportExcel(outsourcePersonledgeDto, user);
        ExcelUtil.exportExcel(list, PpmOutsourcePersonledgeDto.class, "temp.xls", response);
        result.success("数据导出成功");
    }

}
