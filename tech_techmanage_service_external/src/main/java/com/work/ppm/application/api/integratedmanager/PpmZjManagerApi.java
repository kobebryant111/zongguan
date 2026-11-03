package com.work.ppm.application.api.integratedmanager;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.integratedmanager.PpmZjManagerDto;
import com.work.ppm.application.services.integratedmanager.IPpmZjManagerService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.artemis.enums.ControlTypeEnums;
import com.work.ppm.commons.artemis.request.ControlReqt;
import com.work.ppm.commons.artemis.service.ArtemisService;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 闸机管理 API接口
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("ppmZjManager")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmZjManagerApi"})
public class PpmZjManagerApi {
    // 闸机管理
    private final IPpmZjManagerService ppmZjManagerService;
    // 海康卫视闸机服务
    private final ArtemisService artemisService;

    /**
     * 列表查询
     *
     * @param ppmZjManagerDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryList")
    @ApiOperation(value = "列表查询", notes = "列表查询")
    public ApiResult queryList(PpmZjManagerDto ppmZjManagerDto) {
        ApiResult<List> result = new ApiResult<>();
        result.setResults(ppmZjManagerService.queryList(ppmZjManagerDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 分页
     *
     * @param ppmZjManagerDto 对象
     * @param pager           分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmZjManagerDto ppmZjManagerDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmZjManagerService.queryPager(ppmZjManagerDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 门禁点反控
     *
     * @param ppmZjManagerDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "doControl")
    @ApiOperation(value = "门禁点反控", notes = "门禁点反控")
    public ApiResult<Boolean> doControl(PpmZjManagerDto ppmZjManagerDto) {
        ApiResult<Boolean> result = new ApiResult<>();
        PpmZjManagerDto item = ppmZjManagerService.queryDetail(ppmZjManagerDto);
        if (!ObjectUtils.isEmpty(item)) {
            ControlReqt controlReqt = ControlReqt.builder()
                    .doorIndexCodes(Lists.newArrayList(item.getIndexCode()))
                    .controlType(ControlTypeEnums.fromCode(ppmZjManagerDto
                            .getControlType())).build();
            artemisService.doControl(controlReqt);
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("开门失败");
        }
        return result;
    }
}
