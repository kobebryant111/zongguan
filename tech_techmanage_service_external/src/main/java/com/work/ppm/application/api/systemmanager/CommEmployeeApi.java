package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 员工
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"CommEmployeeApi"}, description = "员工")
@RestController
@RequestMapping("commEmployee")
@Slf4j
public class CommEmployeeApi extends BaseUploadApi {
    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 分页
     * @param commEmployee 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommEmployeeDto commEmployee, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commEmployeeService.queryPager(commEmployee,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }
    /**
     * 分页,,去除离职人员
     * @param commEmployee 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "pageList")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> pageList(CommEmployee commEmployee,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commEmployeeService.pageList(commEmployee,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param commEmployee 对象
     * @return  ApiResult<CommEmployee>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updateflg", value = "0.HR数据  1.本地新增  2.本地编辑", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastupdatedate", value = "最新更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "reportto", value = "上级领导", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "删除标识", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "shortphone", value = "短号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empauth", value = "员工编制", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "authproperty", value = "编制属性", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fullname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "所属岗位代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "员工代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "empstatus", value = "员工状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "officephone", value = "办公电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eidhr", value = "员工代码HR", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "boardline", value = "所属条线", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "spellname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职务", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mobiletelphone", value = "移动电话", dataType = "string", paramType = "query"),

    })
    public ApiResult<CommEmployee> queryDetail(CommEmployee commEmployee) {
        ApiResult<CommEmployee> result =new ApiResult<>();
        CommEmployee item = commEmployeeService.getCommEmployeeInfo(commEmployee.getBadge());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param commEmployee 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updateflg", value = "0.HR数据  1.本地新增  2.本地编辑", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastupdatedate", value = "最新更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "reportto", value = "上级领导", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "删除标识", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "shortphone", value = "短号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empauth", value = "员工编制", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "authproperty", value = "编制属性", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fullname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "所属岗位代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "员工代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "empstatus", value = "员工状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "officephone", value = "办公电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eidhr", value = "员工代码HR", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "boardline", value = "所属条线", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "spellname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职务", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mobiletelphone", value = "移动电话", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updateCommEmployee(CommEmployee commEmployee) {
        ApiResult<Object> result =new ApiResult<>();
        int id = commEmployeeService.updateCommEmployee(commEmployee);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 角色用户关联分页查询
     * @param commEmployee 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping("queryRoleUserRel")
    @ApiOperation(value ="角色用户关联分页查询", notes = "角色用户关联分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryRoleUserRel(CommEmployeeDto commEmployee, Pager pager) {
        ApiResult result = new ApiResult();
        commEmployeeService.queryRoleUserRelByPage(commEmployee,pager);
        result.success("获取数据成功");
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 行员花名册 - 分页
     * @param commEmployeeDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping("queryCrewPage")
    @ApiOperation(value = "行员花名册 - 分页")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryCrewPageList(CommEmployeeDto commEmployeeDto, Pager pager) {
        ApiResult result = new ApiResult();
        commEmployeeService.queryCrewPageList(commEmployeeDto, pager);
        result.success("查询成功");
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 部门行员树
     * @param deptNo 部门编号
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryEmployeeTree/{deptno}")
    @ApiOperation(value = "部门行员树|行内负责人", notes = "部门和行员关联树，行内负责人")
    @ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "path",required = true)
    public ApiResult<List<CommTreeDto>> queryEmployeeTree(@PathVariable("deptno")  String deptNo) {
        ApiResult<List<CommTreeDto>> result = new ApiResult();
        List<CommTreeDto> list  = commDepartmentService.queryAuth(deptNo,null,null);
        result.setResults(list);
        result.success("查询成功");
        return  result;
    }

    /**
     * 部门行员树
     * @param orgid 机构编号
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryEmployeeByOrgidTree/{orgid}")
    @ApiOperation(value = "部门行员树|行内负责人", notes = "根据机构编号查询，显示部门和行员关联树，行内负责人")
    @ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path",required = true)
    public ApiResult<List<CommTreeDto>> queryEmployeeByOrgidTree(@PathVariable("orgid")  String orgid) {
        ApiResult<List<CommTreeDto>> result = new ApiResult();
        List<CommTreeDto> list  = commDepartmentService.queryAuth(orgid);
        result.setResults(list);
        result.success("查询成功");
        return  result;
    }

    /**
     * 获取行员列表-组件
     * @param commEmployeeDto 对象
     * @return  ApiResult<List<CommEmployeeDto>>
     */
    @GetMapping("getEmployList")
    @ApiOperation(value = "获取行员列表-组件", notes = "包括预加载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "行员名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "spellname", value = "名称简写", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fullname", value = "名称拼音", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "proLoadNumber", value = "预加载数", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<CommEmployeeDto>> getEmployList(CommEmployeeDto commEmployeeDto) {
        ApiResult<List<CommEmployeeDto>> result = new ApiResult<>();
        List<CommEmployeeDto> list = commEmployeeService.getEmployeeList(commEmployeeDto);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 获取用户树
     * 总机构->机构-> 部门-> 行员
     * @param commTreeDto
     *  id  父类编号
     *  next 子节点类型
     * current 当前节点类型
     * searchUser 搜索条件
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryEmployees")
    @ApiOperation(value = "获取用户树", notes = "总机构->机构-> 部门-> 行员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "父类编号",dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "next", value = "子节点类型",dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "current", value = "当前节点类型",dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "searchUser", value = "搜索条件",dataType = "string", paramType = "query"),
    })
    public ApiResult<CommTreeDto> queryEmployeeTree(CommTreeDto commTreeDto) {
        ApiResult result = new ApiResult();
        List<CommTreeDto> list = commEmployeeService.queryEmployeeTree(commTreeDto);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 根据用户名称、拼音、工号 查询员工信息
     * @param username 名称、工号、拼音
     * @return ApiResult<CommEmployeeDto>
     */
    @GetMapping("queryEmployUser/{username}")
    @ApiOperation(value = "根据用户名称、拼音、工号 查询员工信息")
    @ApiImplicitParam(name = "username", value = "名称、工号、拼音", dataType = "string", paramType = "path", required=true)
    public ApiResult<CommEmployeeDto> queryEmployUser(@PathVariable String username) {
        ApiResult result = new ApiResult();
        List<CommEmployeeDto> list = commEmployeeService.queryEmployUser(username);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 外包花名册导出
     * @param commEmployeeDto  对象
     * @param request  对象
     * @param response response
     */
    @PostMapping("exportOutsourcing")
    @ApiOperation(value = "外包花名册导出")
    public void exportOutsourcing(PpmOutsourcingBasicExcelDto commEmployeeDto,
                                  HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        List<PpmOutsourcingBasicExcelDto> list = commEmployeeService.exportOutsourcing(commEmployeeDto, user);
        ExcelUtil.exportExcel(list,PpmOutsourcingBasicExcelDto.class,"jtk.xls",response);
        result.success("数据导出成功");
    }

    /**
     * 查询部门全部行员
     * @param deptNo 部门编号
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryEmployeeList/{deptno}")
    @ApiOperation(value = "行内负责人", notes = "行内负责人")
    @ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "path",required = true)
    public ApiResult<List<CommTreeDto>> queryEmployeeList(@PathVariable("deptno")  String deptNo) {
        ApiResult<List<CommTreeDto>> result = new ApiResult();
        List<CommTreeDto> list  = commDepartmentService.queryAuthList(deptNo);
        result.setResults(list);
        result.success("查询成功");
        return  result;
    }

    /**
     * 根据角色进行查询面试官信息
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryMsgList")
    @ApiOperation(value = "行内负责人", notes = "行内负责人")
    public ApiResult<List<CommTreeDto>> queryMsgList() {
        ApiResult<List<CommTreeDto>> result = new ApiResult();
        List<CommTreeDto> list  = commDepartmentService.queryMsgList();
        result.setResults(list);
        result.success("查询成功");
        return  result;
    }


    /**
     * 操作权限角色用户关联分页查询
     * @param commEmployee 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping("queryOperaRoleUserRel")
    @ApiOperation(value ="角色用户关联分页查询", notes = "角色用户关联分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryOperaRoleUserRel(CommEmployeeDto commEmployee, Pager pager) {
        ApiResult result = new ApiResult();
        commEmployeeService.queryOperaRoleUserRelByPage(commEmployee,pager);
        result.success("获取数据成功");
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 操作权限角色用户查询
     * @param userid 登录用户的用户ID
     * @return ApiResult
     */
    @GetMapping("queryUserOperaAuth/{userid}")
    @ApiOperation(value = "queryUserOperaAuth", notes = "操作权限角色用户查询")
    @ApiImplicitParam(name = "userid", value = "用户ID", dataType = "string", paramType = "path", required=true)
    public ApiResult queryUserOperaAuth(@PathVariable("userid")String userid) {
        ApiResult result = new ApiResult();
        String enCoding = commEmployeeService.getUserOperaAuthEncoding(userid);
        result.success("获取数据成功");
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(enCoding);
        return result;
    }

    /**
     * 分页
     * @param commEmployee 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryResearchManagerEmployee")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryResearchManagerEmployee(CommEmployeeDto commEmployee, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commEmployeeService.queryResearchManagerEmployee(commEmployee,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

}
