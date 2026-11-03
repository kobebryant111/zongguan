package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 新水星-用户角色关联
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"ImUserRoleRelApi"}, description = "新水星-用户角色关联")
@RestController
@RequestMapping("imUserRoleRel")
@Slf4j
public class ImUserRoleRelApi {
    /**
     * 新水星-用户角色关联
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;
    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;


    /**
     * 添加
     * @param imUserRoleRel 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"add"})
    @ApiOperation(value = "add", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "urrUserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "urrRoleid", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updateImUserRoleRel(ImUserRoleRel imUserRoleRel) {
        ApiResult<Object> result =new ApiResult<>();
        int id = imUserRoleRelService.addImUserRoleRel(imUserRoleRel);
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        imUserRoleRelService.insertResearchManagerEmployee(imUserRoleRel);
        return result;
    }

    /**
     * 删除
     * @param imUserRoleRel 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteImUserRoleRel (ImUserRoleRel imUserRoleRel) {
        ApiResult result =new ApiResult<>();
        int id = imUserRoleRelService.deleteImUserRoleRel(imUserRoleRel.getUrrUserid(), imUserRoleRel.getUrrRoleid());
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 添加
     * @param imUserRoleRel 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"addOperaUserRole"})
    @ApiOperation(value = "addOperaUserRole", notes = "添加操作用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "urrUserid", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "urrRoleid", value = "", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updateOperaUserRoleRel(ImUserRoleRel imUserRoleRel) {
        ApiResult<Object> result =new ApiResult<>();
        int id = imUserRoleRelService.addOperaUserRoleRel(imUserRoleRel);
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败，请确认该用户是否已添加过！");
        }
        return result;
    }

    /**
     * 批量添加
     * @param ids 用户ID集合
     * @param roleId 角色ID
     * @return ApiResult<Object>
     */
    @PostMapping(value = "addOperaUserRoleBatch")
    @ApiOperation(value = "addOperaUserRoleBatch", notes = "批量添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户ID集合", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Long", paramType = "query"),
    })
    public ApiResult addOperaUserRoleBatch (String ids , String roleId) {
        ApiResult result =new ApiResult<>();
        int i = 0 ;
        if (StringUtil.isStringNotEmpty(ids)) {
            i = imUserRoleRelService.addOperaUserRoleBatch(ids, roleId);
        }
        if(i > 0){
            result.success("已经添加" + i + "名用户。");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("您选中的成员可能已将添加过，不要重复添加！");
        }
        return result;
    }

    /**
     * 分页
     * @param empname 姓名
     * @param badge 工号
     * @param compid 所属机构代码
     * @param depid 所属部门代码
     * @param roleId 角色ID
     * @return ApiResult<List>
     */
    @PostMapping(value = "addOperaUserRoleAll")
    @ApiOperation(value ="添加所有角色用户", notes = "添加所有角色用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> addOperaUserRoleAll(String empname , String badge , String compid , String depid , String roleId ) {
        ApiResult<List> result =new ApiResult<>();
        int i = imUserRoleRelService.addOperaUserRoleAll(empname,badge,compid,depid,roleId);
        if(i > 0){
            result.success("已经添加" + i + "名用户。");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("没有需要添加的数据！");
        }
        return result;
    }



    /**
     * 删除
     * @param imUserRoleRel 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteOperaUserRoleRel")
    @ApiOperation(value = "deleteOperaUserRoleRel", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),
    })
    public ApiResult deleteOperaUserRoleRel (ImUserRoleRel imUserRoleRel) {
        ApiResult result =new ApiResult<>();
        int id = imUserRoleRelService.deleteOperaUserRoleRel(imUserRoleRel.getUrrUserid(), imUserRoleRel.getUrrRoleid());
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 批量删除
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteOperaUserRoleRelByIds")
    @ApiOperation(value = "deleteOperaUserRoleRelByIds", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户ID集合", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Long", paramType = "query"),
    })
    public ApiResult deleteOperaUserRoleRelByIds (String ids , String roleId) {
        ApiResult result =new ApiResult<>();
        int id = imUserRoleRelService.deleteOperaUserRoleRelByIds(ids,roleId);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 批量删除
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteImUserRoleRelByIds")
    @ApiOperation(value = "deleteImUserRoleRelByIds", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户ID集合", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Long", paramType = "query"),
    })
    public ApiResult deleteImUserRoleRelByIds (String ids , String roleId) {
        ApiResult result =new ApiResult<>();
        int id = imUserRoleRelService.deleteImUserRoleRelByIds(ids,roleId);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
