package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.application.dto.onlinemanager.PpmPostGovernorDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.onlinemanager.ext.PpmPostGovernorInfo;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.application.services.onlinemanager.IPpmPostGovernorService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.workflow.client.soentity.BPSWorkItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据中心后督管理流程需求  api接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmPostGovernorApi"})
@RestController
@RequestMapping("ppmPostGovernor")
@Slf4j
public class PpmPostGovernorApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmPostGovernorService ppmPostGovernorService;

    /**
     *公共流程接口Service
     */
    @Autowired
    private ICommonFlowService commonFlowService;

    /**
     * 后督反馈记录分页
     * @param ppmPostGovernorDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="后督反馈记录分页查询", notes = "后督反馈记录分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmPostGovernorDto ppmPostGovernorDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        //查询反馈记录
        ppmPostGovernorDto.setStatus("1");
        ppmPostGovernorService.queryPager(ppmPostGovernorDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 后督录入记录分页
     * @param ppmPostGovernorDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerInput")
    @ApiOperation(value ="后督录入记录分页查询", notes = "后督录入记录分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerInput(PpmPostGovernorDto ppmPostGovernorDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        //查询录入记录
        ppmPostGovernorDto.setStatus("2");
        ppmPostGovernorService.queryPager(ppmPostGovernorDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmPostGovernorDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmPostGovernorInfo> queryDetail(PpmPostGovernorDto ppmPostGovernorDto, HttpServletRequest request) {
        ApiResult<PpmPostGovernorInfo> result =new ApiResult<>();
        this.getCurrentUser(request);
        PpmPostGovernorInfo item = ppmPostGovernorService.queryDetail(ppmPostGovernorDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 新增
     * @param ppmPostGovernorDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "新增", notes = "新增")
    public ApiResult<Object> savePpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user= this.getCurrentUser(request);
        int id = ppmPostGovernorService.addPpmPostGovernor(ppmPostGovernorDto,  user);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("新增失败");
        }
        return result;
    }



    /**
     * 更新
     * @param ppmPostGovernorDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
       this.getCurrentUser(request);
        int id = ppmPostGovernorService.updatePpmPostGovernor(ppmPostGovernorDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }



    /**
     * 点击触发反馈流程
     * @param ppmPostGovernorDtos 对象
     * @param request    request
     * @return ApiResult
     */
    @PostMapping("flowStartAndRun")
    @ApiOperation(value = "触发反馈流程", notes = "触发反馈流程")
    public ApiResult flowStartAndRun(@RequestBody List<PpmPostGovernorDto> ppmPostGovernorDtos,
                                     HttpServletRequest request) {
        log.info("数据中心后督管理流程需求触发反馈流程请求参数{}",ppmPostGovernorDtos.toString());
        ApiResult result ;
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        result=ppmPostGovernorService.flowStartAndRun(ppmPostGovernorDtos,user);
        return result;
    }

    /**
     * 关联人员审批接口 relationUserRunFlowInfo
     * @param ppmPostGovernorDto 对象
     * @param request 对象
     * @return   ApiResult
     */
    @PostMapping("relationUserRunFlowInfo")
    @ApiOperation(value = "关联人员审批接口",notes = "参数传入为流程对象 bps 对象流程id 和 审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conclusion", value = "审核结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstId", value = "流程Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "审批意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feedbackContent", value = "反馈内容", dataType = "string", paramType = "query"),
    })
    public ApiResult relationUserRunFlowInfo(@RequestBody PpmPostGovernorDto ppmPostGovernorDto,
                                 HttpServletRequest request) {
        ApiResult result =new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        //通过流程Id 获取当前流程的 活动Id  用来判断 当前是具体哪个审批角色
        BPSWorkItem item = commonFlowService.queryWorkItem(ppmPostGovernorDto.getBps());
        if(item==null || ppmPostGovernorDto.getBps()==null ) {
            result.failure("待关联人员审批审批节点不正确或者流程不存在");
        }else {
            if("manualActivity1".equals(item.getActivityDefID())){
                ppmPostGovernorDto.getBps().setWorkItemId(String.valueOf(item.getWorkItemID()));
                ppmPostGovernorDto.getBps().setUserId(user.getUserid());
                result = ppmPostGovernorService.relationUserRunFlowInfo(ppmPostGovernorDto);
            }else {
                result.failure("关联人员审批节点和审批人不相符，请确认！");
            }
        }
        return result;
    }

    /**
     * 二级部经理审批接口 managerRunFlowInfo
     * @param ppmPostGovernorDto 对象
     * @param request 对象
     * @return   ApiResult
     */
    @PostMapping("managerRunFlowInfo")
    @ApiOperation(value = "二级部经理审批接口",notes = "参数传入为流程对象 bps 对象流程id 和 审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conclusion", value = "审核结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstId", value = "流程Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "审批意见", dataType = "string", paramType = "query"),
    })
    public ApiResult managerRunFlowInfo(@RequestBody PpmPostGovernorDto ppmPostGovernorDto,
                                             HttpServletRequest request) {
        ApiResult result =new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        //通过流程Id 获取当前流程的 活动Id  用来判断 当前是具体哪个审批角色
        BPSWorkItem item = commonFlowService.queryWorkItem(ppmPostGovernorDto.getBps());
        if(item==null || ppmPostGovernorDto.getBps()==null ) {
            result.failure("待审批节点不正确或者流程不存在");
        }else {
            if("manualActivity2".equals(item.getActivityDefID())){
                ppmPostGovernorDto.getBps().setWorkItemId(String.valueOf(item.getWorkItemID()));
                ppmPostGovernorDto.getBps().setUserId(user.getUserid());
                ppmPostGovernorDto.setProcessInstId(ppmPostGovernorDto.getBps().getProcessInstId());
                result = ppmPostGovernorService.managerRunFlowInfo(ppmPostGovernorDto);
            }else {
                result.failure("当前审批节点和审批人不相符，请确认！");
            }
        }
        return result;
    }

    /**
     * 后督管理人员审核审批接口 relationUserRunFlowInfo
     * @param ppmPostGovernorDto 对象
     * @param request 对象
     * @return   ApiResult
     */
    @PostMapping("endRunFlowInfo")
    @ApiOperation(value = "后督管理人员审核审批接口",notes = "参数传入为流程对象 bps 对象流程id 和 审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conclusion", value = "审核结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstId", value = "流程Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "审批意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isCompliance", value = "是否合规", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "note", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult endRunFlowInfo(@RequestBody PpmPostGovernorDto ppmPostGovernorDto,
                                        HttpServletRequest request) {
        ApiResult result =new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        //通过流程Id 获取当前流程的 活动Id  用来判断 当前是具体哪个审批角色
        BPSWorkItem item = commonFlowService.queryWorkItem(ppmPostGovernorDto.getBps());
        if(item==null || ppmPostGovernorDto.getBps()==null ) {
            result.failure("待审批节点不正确或者流程不存在");
        }else {
            if("manualActivity3".equals(item.getActivityDefID())){
                ppmPostGovernorDto.getBps().setWorkItemId(String.valueOf(item.getWorkItemID()));
                ppmPostGovernorDto.getBps().setUserId(user.getUserid());
                result = ppmPostGovernorService.endRunFlowInfo(ppmPostGovernorDto);
            }else {
                result.failure("当前审批节点和审批人不相符，请确认！");
            }
        }
        return result;
    }


    /**
     * 批量删除
     * @param ppmPostGovernorDtos 对象
     * @return ApiResult
     */
    @PostMapping(value = "batchDelete")
    @ApiOperation(value = "批量删除", notes = "批量删除")
    public ApiResult batchDeletePpmPostGovernor (@RequestBody List<PpmPostGovernorDto> ppmPostGovernorDtos, HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        this.getCurrentUser(request);
        int id = ppmPostGovernorService.batchDeletePpmPostGovernor(ppmPostGovernorDtos);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 后督管理流程模板导入
     * @param file 文件
     * @return   ApiResult
     */
    @PostMapping("importExcelInfo")
    @ApiOperation(value = "后督管理流程模板导入", notes = "后督管理流程模板导入")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        boolean falg = ppmPostGovernorService.importExcelInfo(file,user);
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

    /**
     * 后督反馈记录导出
     * @param ppmPostGovernorDto  对象
     * @param response  对象
     */
    @PostMapping("exportPostGovernorDatas")
    @ApiOperation(value = "后督反馈记录导出")
    public void exportPostGovernorDatas(PpmPostGovernorDto ppmPostGovernorDto , HttpServletResponse response) {
        ppmPostGovernorDto.setStatus("1");
        List<PpmPostGovernorInfo> list = ppmPostGovernorService.selectList(ppmPostGovernorDto);
        ExcelUtil.exportExcel(list,PpmPostGovernorInfo.class,"后督反馈记录导出.xls",response);
    }

    /**
     * 后督信息录入导出
     * @param ppmPostGovernorDto  对象
     * @param response  对象
     */
    @PostMapping("exportPostGovernorInputDatas")
    @ApiOperation(value = "后督信息录入导出")
    public void exportPostGovernorInputDatas(PpmPostGovernorDto ppmPostGovernorDto , HttpServletResponse response) {
        ppmPostGovernorDto.setStatus("2");
        List<PpmPostGovernorDto> list = ppmPostGovernorService.exportPostGovernorInputDatas(ppmPostGovernorDto);
        ExcelUtil.exportExcel(list,PpmPostGovernorDto.class,"后督信息录入导出.xls",response);
    }

}
