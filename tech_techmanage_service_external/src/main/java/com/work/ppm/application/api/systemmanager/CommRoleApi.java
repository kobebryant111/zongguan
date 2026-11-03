package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.CommRole;
import com.work.ppm.application.model.systemmanager.ext.CommRoleInfo;
import com.work.ppm.application.services.systemmanager.ICommRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"CommRoleApi"}, description = "角色")
@RestController
@RequestMapping("commRole")
@Slf4j
public class CommRoleApi {
    /**
     * 角色
     */
    @Autowired
    private ICommRoleService commRoleService;

    /**
     * 分页
     * @param commRole 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operatorname", value = "角色名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "员工号", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommRoleInfo commRole, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commRoleService.queryPager(commRole,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param commRole 对象
     * @return ApiResult<CommRole>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "rolename", value = "角色名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roletype", value = "角色类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "有效标志", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "父ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roleid", value = "角色ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roledesc", value = "角色描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "selfid", value = "ID", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<CommRole> queryDetail(CommRole commRole) {
        ApiResult<CommRole> result =new ApiResult<>();
        CommRole item = commRoleService.queryDetail(commRole);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param commRole 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolename", value = "角色名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roletype", value = "角色类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "有效标志", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "父ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roleid", value = "角色ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roledesc", value = "角色描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "selfid", value = "ID", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<Object> updateCommRole(CommRole commRole) {
        ApiResult<Object> result =new ApiResult<>();
        int id = commRoleService.updateCommRole(commRole);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

}
