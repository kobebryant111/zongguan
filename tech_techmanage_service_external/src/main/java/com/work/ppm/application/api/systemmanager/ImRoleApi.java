package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.ImRoleDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleTreeDto;
import com.work.ppm.application.model.systemmanager.ImRole;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.IImRoleService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 新水星-角色
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"ImRoleApi"}, description = "新水星-角色")
@RestController
@RequestMapping("imRole")
@Slf4j
public class ImRoleApi extends BaseApi {
    /**
     * 新水星-角色
     */
    @Autowired
    private IImRoleService imRoleService;
    /**
     * 操作接口
     */
    @Autowired
    private ICommOperatorService commOperatorService;
    /**
     * 用户角色树
     * @param imRole 对象
     * @return ApiResult<List<ImRoleTreeDto>>
     */
    @GetMapping(value = "roleTree")
    @ApiOperation(value ="roleTree", notes = "用户角色树")
    public ApiResult<List<ImRoleTreeDto>> queryRoleTree(ImRole imRole) {
        ApiResult<List<ImRoleTreeDto>> result =new ApiResult<>();
        List<ImRoleTreeDto> list = imRoleService.queryRoleTree();
        result.setResults(list);
       result.success("获取数据成功");
        return result;
    }

    /**
     * 查询详情
     * @param imRole 对象
     * @return ApiResult<ImRole>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "rolId", value = "角色编号，主键ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolName", value = "角色名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolDesc", value = "角色描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolLevel", value = "角色层级", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "rolGroupId", value = "角色分组ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolCreateTime", value = "角色创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rolParentId", value = "直接父角色编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolCreateUser", value = "角色创建用户", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<ImRole> queryDetail(ImRole imRole) {
        ApiResult<ImRole> result =new ApiResult<>();
        ImRole item = imRoleService.queryDetail(imRole);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加或者更新
     * @param imRole 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "endit")
    @ApiOperation(value = "endit", notes = "添加或者更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolId", value = "角色编号，主键ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolName", value = "角色名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolDesc", value = "角色描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolLevel", value = "角色层级", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "rolGroupId", value = "角色分组ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolCreateTime", value = "角色创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rolParentId", value = "直接父角色编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rolCreateUser", value = "角色创建用户", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sqlStatus", value = "sql执行状态 add 添加，update 修改", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updateImRole(HttpServletRequest request, ImRoleDto imRole) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto imUser  = getCurrentUser(request);
        imRole.setRolCreateUser(imUser.getUserid());
        int id = imRoleService.updateImRole(imRole);

        if(id == 1){
            result.success("添加成功");
        } else if(id == 2) {
            result.success("更新成功");
        }
        return result;
    }

    /**
     * 删除
     * @param imRole 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolId", value = "角色编号，主键ID", dataType = "string", paramType = "query"),

    })
    public ApiResult deleteImRole (ImRole imRole) {
        ApiResult result =new ApiResult<>();
        int id = imRoleService.deleteImRole(imRole);
        if(id > 0){
            result.success("已删除");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 用户角色树
     * @param imRole 对象
     * @return ApiResult<List<ImRoleTreeDto>>
     */
    @GetMapping(value = "operaRoleTree")
    @ApiOperation(value ="operaRoleTree", notes = "操作权限角色树")
    public ApiResult<List<ImRoleTreeDto>> queryOperaRoleTree(ImRole imRole) {
        ApiResult<List<ImRoleTreeDto>> result =new ApiResult<>();
        List<ImRoleTreeDto> list = imRoleService.queryOperaRoleTree();
        result.setResults(list);
        result.success("获取数据成功");
        return result;
    }
}
