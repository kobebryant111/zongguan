package com.work.ppm.application.api.usesysresourcealter;

import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.usesysresourcealter.ResUseSysDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.usesysresourcealter.ResUseSys;
import com.work.ppm.application.model.usesysresourcealter.ResUseSysOther;
import com.work.ppm.application.services.usesysresourcealter.IResUseSysService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 应用系统资源变更
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"resConfigApi"})
@RestController
@RequestMapping("resUseSys")
@Slf4j
public class ResUseSysFlowApi extends BaseApi {
    /**
     * 应用系统接口
     */
    @Autowired
    private IResUseSysService resUseSysService;

    /**
     * 分页
     *
     * @param resUseSysDto 对象
     * @param pager       分页对象
     * @param request 请求
     * @return ApiResult<List> 列表
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flowno", value = "流程编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appsysname", value = "应用名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rangeDate", value = "申请时间", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")

    })
    public ApiResult<List> queryPager(ResUseSysDto resUseSysDto, Pager pager, HttpServletRequest request) {
        //对时间做特殊处理
        String rangeDate = resUseSysDto.getRangeDate();

        if (StringUtils.isNotEmpty(rangeDate)) {
            String startTime =rangeDate +" 00:00:00";
            String endTime =rangeDate +" 23:59:59";
            resUseSysDto.setStartTime(startTime);
            resUseSysDto.setEndTime(endTime);
        }
        ApiResult<List> result = new ApiResult<>();
        resUseSysService.queryPager(resUseSysDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 获取应用系统变更数据
     *
     * @param resUseSysDto 对象
     * @return ApiResult 列表
     */
    @PostMapping(value = "queryUseSysList")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appNo", value = "应用系统id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ids", value = "id集合", dataType = "String", paramType = "query"),
            // 分页公共参数
    })
    public ApiResult<List> queryUseSysList(ResUseSysDto resUseSysDto) {
        ApiResult<List> result = new ApiResult<>();
        String appNo =resUseSysDto.getAppsysno();// 应用系统id
        String ids =resUseSysDto.getIds();// 应用系统id
        // 调用cmdb接口
        List<Map<String, Object>> list =resUseSysService.queryUseSysList(appNo,ids);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 获取系统管理员A岗人员
     * @param resUseSysDto
     * @return String
     */
    @PostMapping(value = "querySystemUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appNo", value = "应用系统id", dataType = "String", paramType = "query"),
            // 分页公共参数
    })
    public ApiResult<String> querySystemUser(ResUseSysDto resUseSysDto) {
        ApiResult<String> result = new ApiResult<>();
        String appNo =resUseSysDto.getAppsysno();// 应用系统id
        String ids =resUseSysDto.getIds();// 应用系统id
        // 调用cmdb接口
        String sysUser =resUseSysService.querySystemUser(appNo);
        result.setResults(sysUser);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 获取应用系统变更数据
     *
     * @param resUseSysDto 对象
     * @param pager        分页对象
     * @return ApiResult 列表
     */
    @PostMapping(value = "queryUseSysPageList")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resclassify", value = "应用系统id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "ip", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "serialno", value = "序列号", dataType = "String", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryUseSysPageList(ResUseSysDto resUseSysDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        // 调用cmdb接口
        List<Map<String, Object>> list =resUseSysService.queryUseSysPageList(resUseSysDto,pager);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);

        if(!list.isEmpty()){
            result.setTotal(Integer.valueOf(list.get(0).get("totalPage").toString()));
        }
        return result;
    }


    /**
     * 发起应用系统资源变更流程
     *
     * @param resUseSysDto 对象
     * @param request 请求
     * @return string 字符串
     */
    @PostMapping(value = "submitResUseSysData")
    @ApiOperation(value = "发起应用系统资源变更流程", notes = "发起应用系统资源变更流程")
    public ApiResult<String> submitResourceAdded(@RequestBody ResUseSysDto resUseSysDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto sys = super.getCurrentUser(request);
        try {
            ResUseSys list = resUseSysDto.getList();
            list.setCreator(sys.getUserid());
            list.setCreatorname(sys.getOperatorname());
            String processInfo = resUseSysService.submitResUseSysData(resUseSysDto);
            result.setResults(processInfo);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 提交工作项
     *
     * @param flowDto 审批项管信息
     * @return ResAdded 对象
     */
    @PostMapping(value = "submitWorkItem")
    @ApiOperation(value = "提交工作项", notes = "提交工作项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例ID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<String> submitWorkItem(@RequestBody FlowDto flowDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user =  super.getCurrentUser(request);
            if(null==user){
                result.setStatus(ApiCode.FAIL);
                return result;
            }
            flowDto.setCreator(user.getUserid());
            boolean flag =  resUseSysService.submitWorkItem(flowDto);
            if(flag){
                String processInfo = BpsUtil.queryProcessInfo(flowDto.getProcessInstId());
                result.setMessage(processInfo);
                result.setStatus(ApiCode.SUCCESS);
            }else{
                result.setStatus(ApiCode.FAIL);
            }

        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 重新提交发起工作项
     *
     * @param flowDto     审批项管信息
     * @param resUseSysDto 应用系统资源变更申请信息
     * @return ResAdded 对象
     */
    @PostMapping(value = "reSubmitWorkFlow")
    @ApiOperation(value = "重新提交发起工作项", notes = "重新提交发起工作项")
    public ApiResult<ResAdded> reSubmitWorkFlow(@RequestBody FlowDto flowDto, @RequestBody ResUseSysDto resUseSysDto, HttpServletRequest request) {
        ApiResult<ResAdded> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            if (null == user) {
                result.setStatus(ApiCode.FAIL);
                return result;
            }
            flowDto.setCreator(user.getUserid());
            boolean flag = resUseSysService.reSubmitWorkFlow(flowDto,resUseSysDto);
            if (flag) {
                String processInfo = BpsUtil.queryProcessInfo(flowDto.getProcessInstId());
                result.setMessage(processInfo);
                result.setStatus(ApiCode.SUCCESS);
            } else {
                result.setStatus(ApiCode.FAIL);
            }

        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 保存应用系统业务数据
     *
     * @param resUseSysDto 对象
     * @param request     对象
     * @return ApiResult 字符串
     */
    @PostMapping(value = "saveUseSysData")
    @ApiOperation(value = "保存数据", notes = "保存数据")
    public ApiResult<String> saveUseSysData(@RequestBody ResUseSysDto resUseSysDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();

        CommOperatorDoLoginDto sys = super.getCurrentUser(request);
        ResUseSys list = resUseSysDto.getList();
        list.setCreator(sys.getUserid());
        list.setCreatorname(sys.getOperatorname());
        resUseSysService.saveUseSysData(resUseSysDto,"1");
        result.success("保存成功");
        return result;
    }

    /**
     * 获取应用系统资源变更数据
     *
     * @param resUseSys 流程实例id
     * @return ResUseSysDto 对象
     */
    @PostMapping(value = "queryResUseSysAlterData")
    @ApiOperation(value = "获取应用系统资源变更数据", notes = "获取应用系统资源变更数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstID", value = "流程实例id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "流程实例id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<ResUseSysOther>> queryResUseSysAlterData(ResUseSys resUseSys) {
        ApiResult<List<ResUseSysOther>> result = new ApiResult<>();
        try {
            List<ResUseSysOther> resExpandDto =  resUseSysService.queryResUseSysAlterData(resUseSys);
            result.setResults(resExpandDto);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 删除应用系统资源变更数据
     *
     * @param resUseSysOther 对象
     * @return result 字符串
     */
    @PostMapping(value = "delUseSysAlterData")
    @ApiOperation(value = "删除应用系统资源变更数据", notes = "删除应用系统资源变更数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "string", paramType = "delete"),
            @ApiImplicitParam(name = "activitydefid", value = "当前节点", dataType = "string", paramType = "delete"),
    })
    public ApiResult<String> delUseSysAlterData(ResUseSysOther resUseSysOther) {
        ApiResult<String> result = new ApiResult<>();
        try {
            resUseSysService.delUseSysAlterData(resUseSysOther);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("操作成功");
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("操作失败");
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 更新
     *
     * @param resUseSysOther 对象
     * @return ApiResult<Object> 对象
     */
    @PostMapping(value = "updateUseSysFlowData")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activitydefid", value = "当前节点id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "resusesysid", value = "关联主表id", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updateUseSysFlowData(ResUseSysOther resUseSysOther) {
        ApiResult<Object> result = new ApiResult<>();
        int id = resUseSysService.updateUseSysFlowData(resUseSysOther);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("撤回成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("撤回失败");

        }
        return result;
    }

    /**
     * 生成流水号
     * @return ApiResult<String> 字符串
     */
    @PostMapping("getFlowId")
    @ApiOperation("生成流水号")
    public ApiResult<String> getFlowId() {
        ApiResult<String> result =new ApiResult<>();

        String flowid = resUseSysService.getFlowId();

        if(flowid.isEmpty()) {
            result.failure("生成流水号失败");
        }else {
            result.success("生成流水号成功");
            result.setResults(flowid);
        }
        return result;
    }


}
