package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.model.systemmanager.CommOrganization;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 机构
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"CommOrganizationApi"}, description = "机构")
@RestController("commOrganizationApi")
@RequestMapping("commOrganization")
@Slf4j
public class CommOrganizationApi {
    /**
     * 机构
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;

    /**
     * 分页
     * @param commOrganization 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
			@ApiImplicitParam(name = "compcode", value = "机构编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommOrganization commOrganization,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commOrganizationService.queryPager(commOrganization,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param commOrganization 对象
     * @return ApiResult<CommOrganization>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "compid", value = "机构ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "disableddate", value = "失效日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "displaycode", value = "四位码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcate", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否失效", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastupdatedate", value = "最新更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "compcode", value = "机构编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "abbreviation", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcharge", value = "机构负责人", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "updateflg", value = "0.HR数据  1.本地新增  2.本地编辑", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adminid", value = "所属上级机构", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "compidhr", value = "机构ID(HR)", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "effectdate", value = "生效日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "compgrade", value = "机构级别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "director", value = "机构分管领导", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "comorgid", value = "机构序列(排序用)", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<CommOrganization> queryDetail(CommOrganization commOrganization) {
        ApiResult<CommOrganization> result =new ApiResult<>();
        CommOrganization item = commOrganizationService.queryDetail(commOrganization);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param commOrganization 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "机构ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "disableddate", value = "失效日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "displaycode", value = "四位码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcate", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否失效", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastupdatedate", value = "最新更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "compcode", value = "机构编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "abbreviation", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcharge", value = "机构负责人", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "updateflg", value = "0.HR数据  1.本地新增  2.本地编辑", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adminid", value = "所属上级机构", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "compidhr", value = "机构ID(HR)", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "effectdate", value = "生效日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "compgrade", value = "机构级别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "director", value = "机构分管领导", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "comorgid", value = "机构序列(排序用)", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult updateCommOrganization(CommOrganization commOrganization) {
        ApiResult result =new ApiResult<>();
        int id = commOrganizationService.updateCommOrganization(commOrganization);
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
     * @param commOrganization 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "deletePojo", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteCommOrganization (CommOrganization commOrganization) {
        ApiResult result =new ApiResult<>();
        int id = commOrganizationService.deleteCommOrganization(commOrganization);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 机构树
     * @return ApiResult<List<CommOrganizationDto>>
     */
    @GetMapping(value = "queryOrgTree")
    public ApiResult<List<CommOrganizationDto>> queryOrgTree() {
        ApiResult<List<CommOrganizationDto>> result = new ApiResult();
        List<CommOrganizationDto> item = commOrganizationService.getTree();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(item);
        return result;
    }

    /**
     * 机构树组件.只查询机构树不包括其他
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryOrgTreeComponents")
    @ApiOperation(value = "机构树组件", notes = "只查询机构树不包括其他")
    public ApiResult<List<CommTreeDto>> queryOrgTreeComponents() {
        ApiResult<List<CommTreeDto>> result = new ApiResult();
        List<CommTreeDto> item = commOrganizationService.queryOrgTreeComponents();
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 只查询分行的机构树
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryBranchThree")
    @ApiOperation(value = "只查询分行的机构树", notes = "只查询分行的机构树")
    public ApiResult<List<CommTreeDto>> queryBranchThree() {
        ApiResult<List<CommTreeDto>> result = new ApiResult();
        List<CommTreeDto> item = commOrganizationService.queryBranchThree();
        result.success("获取成功");
        result.setResults(item);
        return result;
    }
}
