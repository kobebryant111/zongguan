package com.work.ppm.application.api.projectmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftTreeDto;
import com.work.ppm.application.model.projectmanager.PpmProjectBuildSoft;
import com.work.ppm.application.services.projectmanager.IPpmProjectBuildSoftService;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 项目管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProjectBuildSoftApi"},description="项目管理")
@RestController
@RequestMapping("ppmProjectBuildSoft")
@Slf4j
public class PpmProjectBuildSoftApi {
    /**
     * 项目管理 接口
     */
    @Autowired
    private IPpmProjectBuildSoftService ppmProjectBuildSoftService;

	/**
	 * 查询项目系统树
	 * @return ApiResult<List<PpmProjectBuildSoftTreeDto>>
	 */
	@GetMapping("queryProjectTree")
	@ApiOperation(value = "查询所有上线项目系统树", notes ="查询所有上线项目系统树" )
    public ApiResult<List<PpmProjectBuildSoftTreeDto>> queryProjectTree() {
    	ApiResult<List<PpmProjectBuildSoftTreeDto>> result = new ApiResult();
    	List<PpmProjectBuildSoftTreeDto> list = ppmProjectBuildSoftService.queryProjectTree();
    	result.success("查询成功");
    	result.setResults(list);
    	return result;
	}

    /**
     * 系统负责人
     * @param ppmProjectBuildSoftDto 对象
     * @return ApiResult<List<PpmProjectBuildSoftDto>>
     */
	@GetMapping("queryMAS")
    @ApiOperation("系统负责人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectno", value = "项目编号",dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sysno", value = "系统编号",dataType = "string", paramType = "query"),
    })

	public ApiResult<List<PpmProjectBuildSoftDto>> queryMAS(PpmProjectBuildSoftDto ppmProjectBuildSoftDto) {
        ApiResult<List<PpmProjectBuildSoftDto>> result = new ApiResult();
        List<PpmProjectBuildSoftDto> list = null;
        if(StringUtil.isStringNotEmpty(ppmProjectBuildSoftDto.getProjectno())) {
            list = ppmProjectBuildSoftService.queryMAS(ppmProjectBuildSoftDto.getProjectno());
        } else {
            list = ppmProjectBuildSoftService.querySystemPrincipal(ppmProjectBuildSoftDto.getSysno());
        }

        result.success("查询成功");
        result.setResults(list);
        return result;
    }


    /**
     * 分页
     * @param ppmProjectBuildSoft 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
    })
    public ApiResult<List> queryPager(PpmProjectBuildSoft ppmProjectBuildSoft,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmProjectBuildSoftService.queryPager(ppmProjectBuildSoft,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmProjectBuildSoft 对象
     * @return  ApiResult<PpmProjectBuildSoft>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
    })
    public ApiResult<PpmProjectBuildSoft> queryDetail(PpmProjectBuildSoft ppmProjectBuildSoft) {
        ApiResult<PpmProjectBuildSoft> result =new ApiResult<>();
        PpmProjectBuildSoft item = ppmProjectBuildSoftService.queryDetail(ppmProjectBuildSoft);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmProjectBuildSoft 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
    })
    public ApiResult<Object> updatePpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmProjectBuildSoftService.updatePpmProjectBuildSoft(ppmProjectBuildSoft);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmProjectBuildSoft 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmProjectBuildSoft (PpmProjectBuildSoft ppmProjectBuildSoft) {
        ApiResult result =new ApiResult<>();
        int id = ppmProjectBuildSoftService.deletePpmProjectBuildSoft(ppmProjectBuildSoft);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
