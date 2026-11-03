package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmEmpBaseInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingChangeApplyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeApply;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos;
import com.work.ppm.application.model.integratedmanager.ext.PpmEmpBaseInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingChangeApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 外包基本信息变更
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingChangeApplyApi"},description="外包基本信息变更")
@RestController
@RequestMapping("ppmOutsourcingChangeApply")
@Slf4j
public class PpmOutsourcingChangeApplyApi extends BaseApi {
    /**
     * 外包基本信息变更
     */
    @Autowired
    private IPpmOutsourcingChangeApplyService ppmOutsourcingChangeApplyService;

    /**
     * 分页
     * @param ppmOutsourcingChangeAppply 对象
     * @param pager  对象
     * @return ApiResult
     * @throws Exception
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "applyer", value = "申请人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "流程FLOWID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "applyid", value = "申请人ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "actChangetime", value = "实际变更时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changereason", value = "变更原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfit", value = "是否符合", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isinterview", value = "是否需要走面试流程", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingChangeApply ppmOutsourcingChangeAppply, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingChangeApplyService.queryPager(ppmOutsourcingChangeAppply,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingChangeAppply 对象
     * @return ApiResult<PpmOutsourcingChangeApply>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "applyer", value = "申请人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "流程FLOWID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "applyid", value = "申请人ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "actChangetime", value = "实际变更时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changereason", value = "变更原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfit", value = "是否符合", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isinterview", value = "是否需要走面试流程", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingChangeApply> queryDetail(PpmOutsourcingChangeApply ppmOutsourcingChangeAppply) {
        ApiResult<PpmOutsourcingChangeApply> result =new ApiResult<>();
        PpmOutsourcingChangeApply item = ppmOutsourcingChangeApplyService.queryDetail(ppmOutsourcingChangeAppply);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingChangeAppply 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "applyer", value = "申请人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "流程FLOWID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "applyid", value = "申请人ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "actChangetime", value = "实际变更时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changereason", value = "变更原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfit", value = "是否符合", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isinterview", value = "是否需要走面试流程", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingChangeAppply(PpmOutsourcingChangeApply ppmOutsourcingChangeAppply) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingChangeApplyService.updatePpmOutsourcingChangeApply(ppmOutsourcingChangeAppply);
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
     * @param ppmOutsourcingChangeAppply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingChangeAppply (PpmOutsourcingChangeApply ppmOutsourcingChangeAppply) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingChangeApplyService.deletePpmOutsourcingChangeApply(ppmOutsourcingChangeAppply);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
    /**
     * 查询当前人基本信息
     * @param ppmEmpBaseInfo 对象
     * @param request request
     * @return ApiResult<PpmEmpBaseInfo>
     */
    @PostMapping("getEmpBaseInfo")
    @ApiOperation("获得当前登录人员的基本信息")
    public ApiResult<PpmEmpBaseInfo> getEmpBaseInfo( PpmEmpBaseInfo ppmEmpBaseInfo, HttpServletRequest request) {
        ApiResult<PpmEmpBaseInfo> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        if(!StringUtil.isNotBlank(ppmEmpBaseInfo.getBadge())){
            ppmEmpBaseInfo.setBadge(user.getUserid());
        }
        PpmEmpBaseInfo info = ppmOutsourcingChangeApplyService.getEmpBaseInfo(ppmEmpBaseInfo);
        if(null == info) {
            result.failure("获取基本信息失败");
        }else {
            result.success("获取基本信息成功");
            result.setResults(info);
        }
        return result;
    }

    /**
     * 查询当前人基本信息
     * @param ppmOutsourcingBasicInfo 对象
     * @param request request
     * @return ApiResult<PpmOutsourcingChangeInfos>
     */
    @PostMapping("getOutBaseInfo")
    @ApiOperation("获得人员的基本信息")
    public ApiResult<PpmOutsourcingChangeInfos> getOutBaseInfo(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo, HttpServletRequest request) {
        ApiResult<PpmOutsourcingChangeInfos> result =new ApiResult<>();
        PpmOutsourcingChangeInfos info = ppmOutsourcingChangeApplyService.getOutBaseInfo(ppmOutsourcingBasicInfo.getJobId());
        if(null == info) {
            result.failure("获取外包人员信息失败");
        }else {
            result.success("获取外包人员信息成功");
            result.setResults(info);
        }
        return result;
    }
    /**
     * 保存流程数据
     * @param ppmOutsourcingChangeApply 对象
     * @param request  request
     * @return ApiResult<PpmOutsourcingChangeApplyDto>
     */
    @PostMapping("save")
    @ApiOperation("保存流程数据")
    public ApiResult<PpmOutsourcingChangeApplyDto> save(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, HttpServletRequest request) {
        ApiResult<PpmOutsourcingChangeApplyDto> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        PpmOutsourcingChangeApplyDto dto = ppmOutsourcingChangeApplyService.save(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),user);
        if(null == dto) {
            result.failure("保存失败");
        }else {
            result.success("保存成功");
            result.setResults(dto);
        }
        return result;
    }
    /**
     * 发起申请
     * @param ppmOutsourcingChangeApply 对象
     * @param request  request
     * @return ApiResult<String>
     * @throws Exception
     */
    @PostMapping("liuzhuan")
    @ApiOperation("发起申请")
    @ApiImplicitParam(value = "依照页面元素进行填充")
    public ApiResult<String> liuzhuan(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        BpsWorkFlowDto bps =null;
        if(null == ppmOutsourcingChangeApply.getBps()) {
            bps=new BpsWorkFlowDto();
        }else{
            bps=ppmOutsourcingChangeApply.getBps();
        }
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.launch(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(), bps);
        result.success(msg);
        return result;
    }
    /**
     * 面试打分
     * @param ppmOutsourcingChangeApply 对象
     * @param request  request
     * @return  ApiResult<String>
     */
    @PostMapping("interviewscore")
    @ApiOperation("面试打分")
    public ApiResult<String> interviewscore(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply,  HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        BpsWorkFlowDto bps=ppmOutsourcingChangeApply.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.interviewscore(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),bps);
        result.success(msg);
        return  result;
    }

    /**
     * 录入结果
     * @param ppmOutsourcingChangeApply  对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("resultinput")
    @ApiOperation("录入结果")
    public ApiResult<String> resultinput(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply,  HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        BpsWorkFlowDto bps=ppmOutsourcingChangeApply.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.resultinput(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),bps);
        result.success(msg);
        return  result;
    }

    /**
     * 录入结果
     * @param ppmOutsourcingChangeApply 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("twoManager")
    @ApiOperation("二级部经理审批")
    public ApiResult<String> twoManager(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply,  HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        BpsWorkFlowDto bps=ppmOutsourcingChangeApply.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.twoManager(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),bps);
        result.success(msg);
        return  result;
    }

    /**
     * 研发管理部确认
     * @param ppmOutsourcingChangeApply 对象
     * @param request request
     * @return ApiResult<String>
     */
    @PostMapping("confirm")
    @ApiOperation("研发管理部确认")
    public ApiResult<String> confirm(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply,  HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        BpsWorkFlowDto bps=ppmOutsourcingChangeApply.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.confirm(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),bps);
        result.success(msg);
        return  result;
    }

    /**
     * 分管总经理
     * @param ppmOutsourcingChangeApply 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("deputyDirectorGeneral")
    @ApiOperation("分管总经理")
    public ApiResult<String> deputyDirectorGeneral(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply,  HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        BpsWorkFlowDto bps=ppmOutsourcingChangeApply.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.deputyDirectorGeneral(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),bps);
        result.success(msg);
        return  result;
    }

    /**
     * 总经理审批
     * @param ppmOutsourcingChangeApply  对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("manager")
    @ApiOperation("总经理审批")
    public ApiResult<String> manager(@RequestBody PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply,  HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        BpsWorkFlowDto bps=ppmOutsourcingChangeApply.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingChangeApplyService.manager(ppmOutsourcingChangeApply,ppmOutsourcingChangeApply.getInfos(),bps);
        result.success(msg);
        return  result;
    }

    /**
     * 根据流程instanceid查询数据
     * @param ppmOutsourcingChangeApply 对象
     * @return ApiResult<PpmOutsourcingChangeApplyDto>
     */
    @GetMapping("getFLowInfo")
    @ApiOperation("根据流程instanceid查询数据")
    @ApiImplicitParam(name = "instanceid", value = "流程编号", required = true,dataType = "string", paramType = "query")
    public ApiResult<PpmOutsourcingChangeApplyDto> getFLowInfo(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply) {
        ApiResult<PpmOutsourcingChangeApplyDto> result =new ApiResult<>();
        PpmOutsourcingChangeApplyDto dto = ppmOutsourcingChangeApplyService.getFLowInfo(ppmOutsourcingChangeApply);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询中心部门树数据
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("queryCenterDepTree")
    @ApiOperation("查询中心部门树数据")
    public ApiResult<List<CommTreeDto>> queryCenterDepTree(PpmEmpBaseInfoDto info) {
        ApiResult<List<CommTreeDto>> result =new ApiResult<>();
        List<CommTreeDto> dto = ppmOutsourcingChangeApplyService.queryCenterDepTree(info);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 根据部门编号查询数据
     * @param info 对象
     * @return ApiResult<List<PpmOutsourcingBasicExcelDto>>
     */
    @PostMapping ("queryOutBaseInfos")
    @ApiOperation("根据部门编号查询数据")
    @ApiImplicitParam(name = "deptId", value = "部门编号", required = true,dataType = "string", paramType = "query")
    public ApiResult<List<PpmOutsourcingBasicExcelDto>> queryOutBaseInfos(PpmOutsourcingBasicExcelDto info) {
        ApiResult<List<PpmOutsourcingBasicExcelDto>> result =new ApiResult<>();
        List<PpmOutsourcingBasicExcelDto> dto = ppmOutsourcingChangeApplyService.queryOutBaseInfos(info);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 根据工号查询或者姓名模糊查询
     * @param info 对象
     * @return ApiResult<List<PpmOutsourcingBasic>>
     */
    @PostMapping ("queryDetailByIdAndName")
    @ApiOperation("根据工号查询或者姓名模糊查询")
    public ApiResult<List<PpmOutsourcingBasic>> queryDetailByIdAndName(PpmOutsourcingBasic info) {
        ApiResult<List<PpmOutsourcingBasic>> result =new ApiResult<>();
        List<PpmOutsourcingBasic> dto = ppmOutsourcingChangeApplyService.queryDetailByIdAndName(info);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
