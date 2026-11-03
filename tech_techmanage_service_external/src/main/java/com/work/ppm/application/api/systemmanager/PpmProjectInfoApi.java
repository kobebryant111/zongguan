package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmProjectInfo;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StreamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目详情表 API接口
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmProjectInfo")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmProjectInfoApi"})
public class PpmProjectInfoApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    /**
     * 分页
     *
     * @param ppmProjectInfoDto 对象
     * @param pager             分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(HttpServletRequest request, PpmProjectInfoDto ppmProjectInfoDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<ImRoleDoLoginDto> roles = user.getRoles();
        boolean admin = roles.stream().anyMatch(i -> "00000001".equals(i.getRoleId()));
        boolean yfglb = "80000122".equals(user.getDeptId());
        if (!admin && !yfglb) {
            ppmProjectInfoDto.setDepId(Long.parseLong(user.getDeptId()));
        }
        ppmProjectInfoService.queryPager(ppmProjectInfoDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询项目经理
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value = "查询项目", notes = "查询项目")
    public ApiResult<List<PpmProjectInfo>> queryList(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<List<PpmProjectInfo>> result = new ApiResult<>();
        result.setResults(ppmProjectInfoService.queryProject(ppmProjectInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询项目经理
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryApplyList")
    @ApiOperation(value = "查询项目", notes = "查询项目")
    public ApiResult<List<PpmProjectInfo>> queryApplyList(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<List<PpmProjectInfo>> result = new ApiResult<>();
        result.setResults(ppmProjectInfoService.queryApplyProject(ppmProjectInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询项目编号不为空的项目信息
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryListForNotXmid")
    @ApiOperation(value = "查询项目编号不为空的项目信息", notes = "查询项目编号不为空的项目信息")
    public ApiResult<List<PpmProjectInfoDto>> queryListForNotXmid(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<List<PpmProjectInfoDto>> result = new ApiResult<>();
        List<PpmProjectInfoDto> list =
                ppmProjectInfoService.queryDetailList(ppmProjectInfoDto,"getListForNotXmid");
        list = list.stream().filter(
                StreamUtils.distinctByKey(PpmProjectInfoDto::getProjectNumber)).collect(Collectors.toList());
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询项目经理
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryEmp")
    @ApiOperation(value = "查询项目经理", notes = "查询项目经理")
    public ApiResult<List<CommEmployee>> queryEmp(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<List<CommEmployee>> result = new ApiResult<>();
        result.setResults(ppmProjectInfoService.findByPpmProjectInfo(ppmProjectInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询项目经理
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryNewList")
    @ApiOperation(value = "查询项目", notes = "查询项目")
    public ApiResult<List<PpmProjectInfo>> queryNewList(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<List<PpmProjectInfo>> result = new ApiResult<>();
        result.setResults(ppmProjectInfoService.queryNewProject(ppmProjectInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmProjectInfoDto> queryDetail(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<PpmProjectInfoDto> result = new ApiResult<>();
        PpmProjectInfoDto item = ppmProjectInfoService.queryDetail(ppmProjectInfoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 保存
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult
     */
    @PostMapping
    @ApiOperation(value = "保存", notes = "保存")
    public ApiResult<Object> save(HttpServletRequest request, @RequestBody PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<Object> result = new ApiResult<>();
        int count;
        CommOperatorDoLoginDto loginDto = getCurrentUser(request);
        count =  ppmProjectInfoService.savePpmProjectInfo(ppmProjectInfoDto,loginDto);
        if (count > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("保存失败");
        }
        return result;
    }


    /**
     * 删除
     *
     * @param ppmProjectInfoDto 对象
     * @return ApiResult
     */
    @DeleteMapping
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult delete(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult result = new ApiResult<>();
        int id = ppmProjectInfoService.deletePpmProjectInfo(ppmProjectInfoDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
