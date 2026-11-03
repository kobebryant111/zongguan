package com.work.ppm.application.api.resourceplat;

import com.work.ppm.application.dto.resourceplat.ResNoticeLogDto;
import com.work.ppm.application.services.resourceplat.IResNoticeLogService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通知日志
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResNoticeLogApi"})
@RestController
@RequestMapping("resNoticeLog")
@Slf4j
public class ResNoticeLogApi extends BaseApi {

    /**
     * 通知业务层
     */
    @Autowired
    private IResNoticeLogService resNoticeLogService;

    /**
     * 分页
     *
     * @param modelData 对象
     * @param pager       分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryPager(ResNoticeLogDto modelData, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        resNoticeLogService.queryPager(modelData, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

}
