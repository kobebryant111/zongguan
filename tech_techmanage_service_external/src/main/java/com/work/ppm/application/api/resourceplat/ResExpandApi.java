package com.work.ppm.application.api.resourceplat;
import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.resourceplat.ResExpandDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.services.resourceplat.IResExpandFlowService;
import com.work.ppm.application.services.resourceplat.IResExpandReportService;
import com.work.ppm.application.services.resourceplat.IResExpandService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.BpsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 资源扩容
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResExpandApi"})
@RestController
@RequestMapping("resExpand")
@Slf4j
public class ResExpandApi extends BaseApi {

    /**
     * 资源扩容
     */
    @Autowired
    private IResExpandService resExpandService;

    /**
     * 资源扩容
     */
    @Autowired
    private IResExpandReportService resExpandReportService;

    /**
     * 资源扩容
     */
    @Autowired
    private IResExpandFlowService resExpandFlowService;

    /**
     * 资源扩容申请保存
     *
     * @param resExpandDto 对象
     * @param request 请求
     * @return String
     */
    @PostMapping(value = "insertResourceExpand")
    @ApiOperation(value = "资源扩容申请保存", notes = "资源扩容申请保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete"),
    })
    public ApiResult<String> insertResourceExpand(@RequestBody ResExpandDto resExpandDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        if(null==user){
            result.setStatus(ApiCode.FAIL);
            return result;
        }
        try {
            resExpandDto.setCreator(user.getUserid());
            resExpandDto.setCreatorName(user.getOperatorname());
            resExpandDto.setStatus("1");
            boolean flag =  resExpandService.insertResourceExpand(resExpandDto);
            if(flag){
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
     * 删除资源扩容申请
     *
     * @param id 资源扩容申请主键
     * @return String
     */
    @PostMapping(value = "deleteResourceExpand")
    @ApiOperation(value = "删除资源扩容申请", notes = "删除资源扩容申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete"),
    })
    public ApiResult<String> deleteResourceExpand(String id) {
        ApiResult<String> result = new ApiResult<>();
        try {
            resExpandService.deleteResourceExpand(id);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("操作成功");
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("操作成功");
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 资源扩容申请提交
     * @param resExpandDto 对象
     * @param request 请求
     * @return String 字符串
     */
    @PostMapping(value = "submitResourceExpand")
    @ApiOperation(value = "资源扩容申请提交", notes = "资源扩容申请提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete"),
    })

    public ApiResult<String> submitResourceExpand(@RequestBody ResExpandDto resExpandDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user =  super.getCurrentUser(request);
            if(null==user){
                result.setStatus(ApiCode.FAIL);
                return result;
            }
            resExpandDto.setCreator(user.getUserid());
            resExpandDto.setCreatorName(user.getOperatorname());
            String processInfo =  resExpandService.submitResourceExpand(resExpandDto);
            if(!"false".equals(processInfo)){
                result.setResults(processInfo);
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
     * 资源扩容申请编辑初始化
     *
     * @param id 资源扩容申请主键
     * @return ResExpandDto
     */
    @PostMapping(value = "queryResourceExpand")
    @ApiOperation(value = "资源扩容申请编辑初始化", notes = "资源扩容申请编辑初始化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete"),
    })
    public ApiResult<ResExpandDto> queryResourceExpand(String id) {
        ApiResult<ResExpandDto> result = new ApiResult<>();
        try {
            ResExpandDto resExpandDto =  resExpandService.queryResourceExpand(id);
            result.setResults(resExpandDto);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 配置资源查询
     * @param selectType 1物理机资源2虚拟机资源
     * @param sysOperLevel 系统运维等级
     * @param isFundTrans 是否涉及资金交易
     *  @param ids 已选配件
     * @return List
     */
    @PostMapping(value = "queryConfgiItemList")
    @ApiOperation(value = "配置资源查询", notes = "配置资源查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "selectType", value = "配置资源查询", dataType = "string", paramType = "delete"),
    })
    public ApiResult<List> queryConfgiItemList(String selectType,String sysOperLevel,String isFundTrans,String ids) {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String,Object>> list = resExpandService.queryConfgiItemList
                    (selectType,sysOperLevel,isFundTrans,ids);
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 根据流程实例ID，获取业务信息“ID，sourceType”
     *
     * @param processInstId 流程实例ID
     * @return ResAdded
     */
    @PostMapping(value = "queryBusInfoByProcessInstId")
    @ApiOperation(value = "根据流程实例ID，获取业务信息", notes = "根据流程实例ID，获取业务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例ID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<ResAdded> queryBusInfoByProcessInstId(String processInstId) {
        ApiResult<ResAdded> result = new ApiResult<>();
        try {
            ResAdded resAdded =  resExpandFlowService.queryBusInfoByProcessInstId(processInstId);
            result.setResults(resAdded);
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
     * @param request 请求
     * @return ResAdded 对象
     */
    @PostMapping(value = "submitWorkItem")
    @ApiOperation(value = "提交工作项", notes = "提交工作项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例ID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<ResAdded> submitWorkItem(@RequestBody FlowDto flowDto, HttpServletRequest request) {
        ApiResult<ResAdded> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user =  super.getCurrentUser(request);
            if(null==user){
                result.setStatus(ApiCode.FAIL);
                return result;
            }
            flowDto.setCreator(user.getUserid());
            boolean flag =  resExpandFlowService.submitWorkItem(flowDto);
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
     * @param flowDto 审批项管信息
     * @param resExpandDto 资源申请信息
     * @param request 请求
     * @return ResAdded 对象
     */
    @PostMapping(value = "reSubmitWorkFlow")
    @ApiOperation(value = "重新提交发起工作项", notes = "重新提交发起工作项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例ID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<ResAdded> reSubmitWorkFlow(@RequestBody FlowDto flowDto,
                                                @RequestBody ResExpandDto resExpandDto,
                                                HttpServletRequest request) {
        ApiResult<ResAdded> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user =  super.getCurrentUser(request);
            if(null==user){
                result.setStatus(ApiCode.FAIL);
                return result;
            }
            flowDto.setCreator(user.getUserid());
            boolean flag =  resExpandFlowService.reSubmitWorkFlow(flowDto,resExpandDto);
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
     * 配置应用系统ID，获取资源列表
     * @param ciid 应用系统标识
     * @return List
     */
    @PostMapping(value = "queryCmdbResourceList")
    @ApiOperation(value = "配置应用系统ID，获取资源列表", notes = "配置应用系统ID，获取资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ciid", value = "应用系统标识", dataType = "string", paramType = "delete"),
    })
    public ApiResult<List> queryCmdbResourceList(String ciid) {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String,Object>> list = resExpandService.queryCmdbResourceList(ciid);
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }
    /**
     * 根据虚拟机的CIID，获取对应物理机信息
     * @param ciid 虚拟机的CIID
     * @return  Map<String,Object>
     */
    @PostMapping(value = "queryCmdbRelateResourceList")
    @ApiOperation(value = "根据虚拟机的CIID，获取对应物理机信息", notes = "根据虚拟机的CIID，获取对应物理机信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ciid", value = "虚拟机的CIID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<Map<String,Object>> queryCmdbRelateResourceList(String ciid) {
        ApiResult<Map<String,Object>> result = new ApiResult<>();
        try {
            Map<String,Object> results = resExpandService.queryCmdbRelateResourceList(ciid);
            result.setResults(results);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }
    /**
     * 根据SAN资源，获取总的价格信息
     * @param id SAN资源ID
     * @param  space 总的空间GB
     * @return Map<String,Object>
     */
    @PostMapping(value = "querySanInfo")
    @ApiOperation(value = "根据SAN资源，获取总的价格信息", notes = "根据虚拟机的CIID，获取对应物理机信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "SAN资源ID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<Map<String,Object>> querySanInfo(String id,String space) {
        ApiResult<Map<String,Object>> result = new ApiResult<>();
        try {
            Map<String,Object> results = resExpandService.querySanInfo(id,space);
            result.setResults(results);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 导出计评报表
     * @param id 申请主键
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    @PostMapping(value = "/exportResAuditReport")
    @ApiOperation(value = "资产报表下载", notes = "资产报表下载", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "申请主键", dataType = "string", paramType = "query"),
    })
    public void exportResAuditReport(String id, HttpServletRequest request, HttpServletResponse response) {
        resExpandReportService.exportResAuditReport(id,response);
    }

    /**
     * 根据ID校验作废数据
     * @param resExpandDto 主表D
     * @return ApiResult<String> 返回值
     */
    @PostMapping(value = "validOutofDateMain")
    @ApiOperation(value = "根据ID校验作废数据",notes = "根据ID校验作废数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "query"),
    })
    public ApiResult<String> validOutofDateMain(@RequestBody ResExpandDto resExpandDto){
        return resExpandService.validOutOfDateMain(resExpandDto);
    }
}
