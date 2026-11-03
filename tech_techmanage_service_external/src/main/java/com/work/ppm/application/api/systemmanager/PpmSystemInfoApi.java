package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmSystemInfo;
import com.work.ppm.application.services.systemmanager.IPpmSystemInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 系统详情表 API接口
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmSystemInfo")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmSystemInfoApi"})
public class PpmSystemInfoApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSystemInfoService ppmSystemInfoService;

    /**
     * 分页
     *
     * @param ppmSystemInfoDto 对象
     * @param pager            分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(HttpServletRequest request, PpmSystemInfoDto ppmSystemInfoDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<ImRoleDoLoginDto> roles = user.getRoles();
        boolean admin = roles.stream().anyMatch(i -> "00000001".equals(i.getRoleId()));
        boolean yfglb = "80000122".equals(user.getDeptId());
//        if (!admin && !yfglb) {
//            ppmSystemInfoDto.setDepId(Long.parseLong(user.getDeptId()));
//        }
        ppmSystemInfoService.queryPager(ppmSystemInfoDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询系统信息
     *
     * @param ppmSystemInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value = "查询系统信息", notes = "查询系统信息")
    public ApiResult<List<PpmSystemInfo>> queryList(PpmSystemInfoDto ppmSystemInfoDto) {
        ApiResult<List<PpmSystemInfo>> result = new ApiResult<>();
        result.setResults(ppmSystemInfoService.querySystem(ppmSystemInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询系统负责人
     *
     * @param ppmSystemInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryEmp")
    @ApiOperation(value = "查询系统负责人", notes = "查询系统负责人")
    public ApiResult<List<CommEmployee>> queryEmp(PpmSystemInfoDto ppmSystemInfoDto) {
        ApiResult<List<CommEmployee>> result = new ApiResult<>();
        result.setResults(ppmSystemInfoService.findByPpmSystemInfo(ppmSystemInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmSystemInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmSystemInfoDto> queryDetail(PpmSystemInfoDto ppmSystemInfoDto) {
        ApiResult<PpmSystemInfoDto> result = new ApiResult<>();
        PpmSystemInfoDto item = ppmSystemInfoService.queryDetail(ppmSystemInfoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 保存
     *
     * @param ppmSystemInfoDto 对象
     * @return ApiResult
     */
    @PostMapping
    @ApiOperation(value = "保存", notes = "保存")
    public ApiResult<Object> save(HttpServletRequest request, @RequestBody PpmSystemInfoDto ppmSystemInfoDto) {
        ApiResult<Object> result = new ApiResult<>();
        int count;
        CommOperatorDoLoginDto loginDto = getCurrentUser(request);
        if (ObjectUtils.isEmpty(ppmSystemInfoDto.getId())) {
            ppmSystemInfoDto.setCreateBy(loginDto.getOperatorname());
            ppmSystemInfoDto.setUpdateBy(loginDto.getOperatorname());
            ppmSystemInfoDto.setCreateDate(new Date());
            ppmSystemInfoDto.setUpdateDate(new Date());
            count = ppmSystemInfoService.addPpmSystemInfo(ppmSystemInfoDto);
        } else {
            ppmSystemInfoDto.setUpdateBy(loginDto.getOperatorname());
            ppmSystemInfoDto.setUpdateDate(new Date());
            count = ppmSystemInfoService.updatePpmSystemInfo(ppmSystemInfoDto);
        }
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
     * @param ppmSystemInfoDto 对象
     * @return ApiResult
     */
    @DeleteMapping
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult delete(PpmSystemInfoDto ppmSystemInfoDto) {
        ApiResult result = new ApiResult<>();
        int id = ppmSystemInfoService.deletePpmSystemInfo(ppmSystemInfoDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
