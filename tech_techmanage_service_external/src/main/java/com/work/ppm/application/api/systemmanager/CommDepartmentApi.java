package com.work.ppm.application.api.systemmanager;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 部门
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"CommDepartmentApi"}, description = "部门")
@RestController
@RequestMapping("commDepartment")
@Slf4j
public class CommDepartmentApi extends BaseApi {
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Autowired
    private ICommEmployeeService commEmployeeService;

    /**
     * 分页
     *
     * @param commDepartment 对象
     * @param pager          分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "部门编码 (十位)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommDepartment commDepartment, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        commDepartmentService.queryPager(commDepartment, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param commDepartment 对象
     * @return ApiResult<CommDepartment>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "disableddate", value = "失效日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "updateflg", value = "0.HR同步 1.本地新增 2.本地删除", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastupdatedate", value = "最后更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "effectdate", value = "生效日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "adminid", value = "所属上级部门", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否失效", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "部门负责人", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depgrade", value = "部门级别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depidhr", value = "部门代码（HR）", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "displaycode", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "部门编码 (十位)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构", dataType = "integer", paramType = "query"),

    })
    public ApiResult<CommDepartment> queryDetail(CommDepartment commDepartment) {
        ApiResult<CommDepartment> result = new ApiResult<>();
        CommDepartment item = commDepartmentService.queryDetail(commDepartment);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     *
     * @param commDepartment 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "disableddate", value = "失效日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "updateflg", value = "0.HR同步 1.本地新增 2.本地删除", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastupdatedate", value = "最后更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "effectdate", value = "生效日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "adminid", value = "所属上级部门", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否失效", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "部门负责人", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depgrade", value = "部门级别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depidhr", value = "部门代码（HR）", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门代码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "displaycode", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "部门编码 (十位)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构", dataType = "integer", paramType = "query"),

    })
    public ApiResult<Object> updateCommDepartment(CommDepartment commDepartment) {
        ApiResult<Object> result = new ApiResult<>();
        int id = commDepartmentService.updateCommDepartment(commDepartment);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param commDepartment 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteCommDepartment(CommDepartment commDepartment) {
        ApiResult result = new ApiResult<>();
        int id = commDepartmentService.deleteCommDepartment(commDepartment);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 组织树
     * @param request 对象
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryDeptTree")
    @ApiOperation(value = "查询组织树", notes = "查询组织树")
    public ApiResult<CommTreeDto> queryDeptTree(HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        CommDepartment commDepartment = commDepartmentService.queryFirstDept(user.getUserid());
        List<CommTreeDto> list = commDepartmentService.queryDeptTreeQt(
                commDepartment.getDepcode(),commDepartment.getDepid().toString(), "");
        /*if("6000000300".equals(commDepartment.getDepcode())){
            list = commDepartmentService.queryDeptTree(commDepartment.getDepcode(), "");
        }else {
            list = commDepartmentService.queryDeptTreeQt(
                    commDepartment.getDepcode(),commDepartment.getDepid().toString(), "");
        }*/
        result.success("获取成功");
        result.setResults(list);
        return result;
    }

    /**
     * 查询部门组织树-2级部
     *
     * @param request http请求参数
     * @param commDepartmentDto 部门对象
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryResourceDept")
    @ApiOperation(value = "查询2级部门", notes = "查询部门组织树-2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectcode", value = "编号",
                    dataType = "String", paramType = "query", required = true, defaultValue = "9000190000"),
            @ApiImplicitParam(name = "objectgrade", value = "标记",
                    dataType = "String", paramType = "query", required = true, defaultValue = "2"),
    })
    public ApiResult<CommTreeDto> queryResourceDept(HttpServletRequest request, CommDepartmentDto commDepartmentDto) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<CommTreeDto> list = commDepartmentService.queryResourceDept(user, commDepartmentDto);
        result.success("获取成功");
        result.setResults(list);
        return result;
    }


    /**
     * 查询部门组织树
     *
     * @param empId 部门ID
     * @return ApiResult<CommDepartment>
     */
    @GetMapping("queryDep")
    @ApiOperation(value = "查询2级部门", notes = "查询部门组织树-2")
    public ApiResult<CommDepartment> queryDep(@RequestParam("empId") String empId) {
        ApiResult result = new ApiResult<>();
        List<CommDepartment> list = commDepartmentService.queryDep(empId);
        result.success("获取成功");
        result.setResults(list);
        return result;
    }

    /**
     * 根据机构编号查询行长室部门id
     *
     * @param compId 机构编号
     * @return ApiResult
     */
    @GetMapping("queryDeptIdForCompId")
    @ApiOperation(value = "根据机构编号查询行长室部门id", notes = "根据机构编号查询行长室部门id")
    @ApiImplicitParam(name = "compId", value = "机构编号", dataType = "String", paramType = "query", required = true,
            defaultValue = "2")
    public ApiResult queryDeptIdForCompId(@RequestParam("compId") String compId) {
        ApiResult result = new ApiResult();
        CommDepartmentDto item = commDepartmentService.queryDeptIdForCompId(compId);
        result.setResults(item);
        result.success("查询成功");
        return result;
    }

    /**
     * 获取分行IT支持部或者软件研发部
     *
     * @param compcode 机构编号
     * @return ApiResult
     */
    @GetMapping("getFhITManager")
    @ApiOperation(value = "获取分行IT支持部或者软件研发部", notes = "根据compcode查询")
    public ApiResult getFhITManager(String compcode) {
        ApiResult result = new ApiResult();
        String deptid = commDepartmentService.getFhITManager(compcode);
        result.setResults(deptid);
        result.success("查询成功");
        return result;
    }

    /**
     * 获取分行运营部总经理
     *
     * @param compcode 机构编号
     * @return ApiResult
     */
    @GetMapping("getFhYybManager")
    @ApiOperation(value = "获取分行运营部总经理", notes = "根据compcode查询")
    public ApiResult getFhYybManager(String compcode) {
        ApiResult result = new ApiResult();
        String deptid = commDepartmentService.getFhYybManager(compcode);
        result.setResults(deptid);
        result.success("查询成功");
        return result;
    }

    /**
     * 获取分行运营部总经理
     *
     * @param deptId 机构编号
     * @return ApiResult
     */
    @GetMapping("get2ndDeptList/{deptId}")
    @ApiOperation(value = "get2ndDeptList", notes = "依据二级部门查询1级部门下所有二级部门")
    public ApiResult get2ndDeptList(@PathVariable("deptId") String deptId) {
        ApiResult result = new ApiResult();
        if (!StringUtil.isNumber(deptId)) {
           throw new BizException(ApiCode.PARAMETER_ERROR, "For input illegal format digit");
        }
        List<CommDepartment> dto = commDepartmentService.query2ndBy1stDept(deptId);
        result.setResults(dto);
        result.success("查询成功");
        return result;
    }

    /**
     * 查询部门信息
     *
     * @param commDepartmentDto 机构对象
     * @return ApiResult
     */
    @GetMapping("getDeptList")
    @ApiOperation(value = "getDeptList", notes = "查询部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depgrade", value = "级别",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否有效",dataType = "String",paramType = "query"),
    })
    public ApiResult getDeptList(CommDepartment commDepartmentDto) {
        ApiResult result = new ApiResult();
        List<CommDepartment> dto = commDepartmentService.queryList(commDepartmentDto);
        result.setResults(dto);
        result.success("查询成功");
        return result;
    }

    /**
     * 查询当前登录人和对应部门
     *
     * @param level 状态
     * @param request request
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryUserDept")
    @ApiOperation(value = "查询当前登录人和对应部门", notes = "查询当前登录人和对应部门")
    public ApiResult<Object> queryUserDept(@RequestParam("level") Integer level ,HttpServletRequest request) {
        //查询
        ApiResult<Object> result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        JSONObject json = new JSONObject();
        json.put("userId",user.getUserid());
        json.put("userName",user.getOperatorname());
        String deptNo = commEmployeeService.getDeptNo(user.getUserid());
        json.put("deptNo",deptNo);
        String deptName = commDepartmentService.getDetpIdForDeptName(deptNo);
        if(StringUtil.isNotEmpty(deptName)){
            if("-1".equals(level.toString())){
                json.put("deptName",deptName);
            }else {
                String [] depts =  deptName.split("-");
                if(depts.length > 1){
                    json.put("deptName",depts[level]);
                }
            }
        }else {
            json.put("deptName","");
        }

        if(StringUtils.isNotEmpty(deptNo)){
            CommEmployeeDto commEmployee = commEmployeeService.getTwoDeptWorkManager(deptNo);
            json.put("leaderPmId",commEmployee.getBadge());
            json.put("leaderPmName",commEmployee.getEmpname());
        }

        result.setStatus(ApiCode.SUCCESS);
        result.setResults(json);
        return result;
    }

}
