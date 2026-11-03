package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.application.dto.onlinemanager.PpmDacenterDemandDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.onlinemanager.ext.PpmDacenterDemandInfo;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.application.services.onlinemanager.IPpmDacenterDemandService;
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
import org.apache.commons.lang3.StringUtils;
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
import java.io.IOException;
import java.util.List;

/**
 * 类描述  科管数据中心需求管理线上化需求 api接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDacenterDemandApi"})
@RestController
@RequestMapping("ppmDacenterDemand")
@Slf4j
public class PpmDacenterDemandApi extends BaseUploadApi {
    /**
     * 科管数据中心需求管理线上化Service
     */
    @Autowired
    private IPpmDacenterDemandService ppmDacenterDemandService;
    /**
     *公共流程接口Service
     */
    @Autowired
    private ICommonFlowService commonFlowService;

    /**
     * 分页
     * @param ppmDacenterDemandDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmDacenterDemandDto ppmDacenterDemandDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDacenterDemandService.queryPager(ppmDacenterDemandDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 数据中心需求管理编辑修改
     * @param ppmDacenterDemandDto 对象
     * @param request    request
     * @return ApiResult
     */
    @PostMapping("modifyDacenterDemand")
    @ApiOperation(value = "数据中心需求管理编辑修改", notes = "数据中心需求管理编辑修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandNo", value = "流程编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandType", value = "需求分类", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandOnlineDate", value = "需求期望上线时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "mainSystemId", value = "关联主系统id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandRemakr", value = "需求分析概要", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandStatus", value = "关联需求状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandProgress", value = "需求进度", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandNo", value = "关联需求编号", dataType = "string", paramType = "query"),
    })
    public ApiResult modifyDacenterDemand(@RequestBody PpmDacenterDemandDto ppmDacenterDemandDto,
                               HttpServletRequest request) {
        log.info("数据中心需求管理流程保存草稿请求参数{}",ppmDacenterDemandDto.toString());
        ApiResult result ;
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        result=ppmDacenterDemandService.modifyDacenterDemand(ppmDacenterDemandDto,user);
        return result;
    }



    /**
     * 保存草稿
     * @param ppmDacenterDemandDto 对象
     * @param request    request
     * @return ApiResult
     */
    @PostMapping("saveDraft")
    @ApiOperation(value = "保存草稿", notes = "保存草稿")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandNo", value = "流程编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createBy", value = "申请人工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandDept", value = "需求负责人所在部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandBy", value = "需求负责人工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandType", value = "需求分类", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandOnlineDate", value = "需求期望上线时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "mainSystemId", value = "关联主系统id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandRemakr", value = "需求分析概要", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandStatus", value = "关联需求状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandProgress", value = "需求进度", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandNo", value = "关联需求编号", dataType = "string", paramType = "query"),
    })
    public ApiResult saveDraft(@RequestBody PpmDacenterDemandDto ppmDacenterDemandDto,
                          HttpServletRequest request) {
        log.info("数据中心需求管理流程保存草稿请求参数{}",ppmDacenterDemandDto.toString());
        ApiResult result ;
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        result=ppmDacenterDemandService.saveDraft(ppmDacenterDemandDto,user);
        return result;
    }



    /**
     * 提交流程并且直接流转
     * @param ppmDacenterDemandDto 对象
     * @param request    request
     * @return ApiResult
     */
    @PostMapping("flowStartAndRun")
    @ApiOperation(value = "提交流转流程", notes = "提交流转流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandNo", value = "流程编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createBy", value = "申请人工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandDept", value = "需求负责人所在部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandBy", value = "需求负责人工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandType", value = "需求分类", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandOnlineDate", value = "需求期望上线时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "mainSystemId", value = "关联主系统id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandRemakr", value = "需求分析概要", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandStatus", value = "关联需求状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandProgress", value = "需求进度", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandNo", value = "关联需求编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relationDemandRemakr", value = "补充说明", dataType = "string", paramType = "query"),
    })
    public ApiResult flowStartAndRun(@RequestBody PpmDacenterDemandDto ppmDacenterDemandDto,
                               HttpServletRequest request) {
        log.info("数据中心需求管理流程提交流转流程请求参数{}",ppmDacenterDemandDto.toString());
        ApiResult result ;
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        result=ppmDacenterDemandService.flowStartAndRun(ppmDacenterDemandDto,user);
        return result;
    }


    /**
     * 查询详情
     * @param  ppmDacenterDemandDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一主键", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstId", value = "流程Id", dataType = "string", paramType = "query"),
    })
    public ApiResult<PpmDacenterDemandInfo> queryDetail(PpmDacenterDemandDto ppmDacenterDemandDto) {
        ApiResult<PpmDacenterDemandInfo> result =new ApiResult<>();
        if(StringUtils.isBlank(ppmDacenterDemandDto.getProcessInstId()) && ppmDacenterDemandDto.getId() ==null){
            result.failure("详情查询，查询条件不容许为空！");
        }else {
            PpmDacenterDemandInfo item = ppmDacenterDemandService.queryDetail(ppmDacenterDemandDto);
            result.setResults(item);
            result.setStatus(ApiCode.SUCCESS);
        }
        return result;
    }



    /**
     * 手机端查询详情
     * @param  ppmDacenterDemandDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryMobileDetail")
    @ApiOperation(value = "手机端查询详情", notes = "手机端查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程Id", dataType = "string", paramType = "query"),
    })
        public ApiResult<PpmDacenterDemandInfo> queryMobileDetail(PpmDacenterDemandDto ppmDacenterDemandDto) {
        ApiResult<PpmDacenterDemandInfo> result =new ApiResult<>();
        if(StringUtils.isBlank(ppmDacenterDemandDto.getProcessInstId())){
            result.failure("详情查询，查询条件不容许为空！");
        }else {
            PpmDacenterDemandInfo item = ppmDacenterDemandService.queryMobileDetail(ppmDacenterDemandDto);
            result.setResults(item);
            result.setStatus(ApiCode.SUCCESS);
        }
        return result;
    }


    /**
     *  flowRunInfo
     * @param ppmDacenterDemandDto 对象
     * @param request 对象
     * @return   ApiResult
     */
    @PostMapping("runFlowInfo")
    @ApiOperation(value = "流程审批接口共用",notes = "参数传入为流程对象 bps 对象流程id 和 审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conclusion", value = "审核结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstId", value = "流程Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "审批意见", dataType = "string", paramType = "query"),
    })
    public ApiResult runFlowInfo(@RequestBody PpmDacenterDemandDto ppmDacenterDemandDto,
                                          HttpServletRequest request) {
        ApiResult result =new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        //通过流程Id 获取当前流程的 活动Id  用来判断 当前是具体哪个审批角色
        BPSWorkItem item = commonFlowService.queryWorkItem(ppmDacenterDemandDto.getBps());
        if(item==null || ppmDacenterDemandDto.getBps()==null ) {
            result.failure("待审批节点不正确或者流程不存在");
        }else {
            ppmDacenterDemandDto.getBps().setUserId(user.getUserid());
            result = ppmDacenterDemandService.runFlowInfo(ppmDacenterDemandDto,item);
        }
        return result;
    }


    /**
     * 需求文件上传
     * @param request  request
     * @param file 文件
     * @return    ApiResult
     */
    @PostMapping("upload")
    @ApiOperation(value = "文件上传", notes = "文件上传")
    public ApiResult upload(MultipartFile file,@RequestParam("objectNo") String objectNo,
                            HttpServletRequest request) throws IOException {
        ApiResult result = new ApiResult();
        super.setFile(file);
        String docmentNo = this.uploadImage(objectNo,"A3", "CATALOG2021121300002",request);
        result.success("上传成功");
        result.setResults(docmentNo);
        return result;
    }



    /**
     * 需求管理线上化数据导出
     * @param ppmDacenterDemandDto  对象
     * @param response  对象
     */
    @PostMapping("exportDacenterDemandDatas")
    @ApiOperation(value = "需求管理线上化数据导出")
    public void exportDacenterDemandDatas(PpmDacenterDemandDto ppmDacenterDemandDto , HttpServletResponse response) {
        List<PpmDacenterDemandInfo> list = ppmDacenterDemandService.selectList(ppmDacenterDemandDto);
        ExcelUtil.exportExcel(list,PpmDacenterDemandInfo.class,"需求管理线上化数据导出.xls",response);
    }
}
