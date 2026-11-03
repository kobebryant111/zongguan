package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessApplyDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssess;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssessApply;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IOutsrcAttrChgApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcEmpAssessApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcEmpAssessService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 人员考核 前端控制器
 * </p>
 *
 * @since 2023-01-03
 */

@Api(produces="application/json;charset=UTF-8", tags= {"OutsrcAssessApplyApi"})
@RestController
@RequestMapping("/outsrcEmpAssessApply")
public class OutsrcEmpAssessApplyApi extends BaseUploadApi {

    @Autowired
    private IOutsrcEmpAssessApplyService outsrcAssessApplyService;

    @Autowired
    private IOutsrcEmpAssessService outsrcEmpAssessService;

    @Autowired
    private IOutsrcAttrChgApplyService outsrcAttrChgApplyService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;


    /**
     * 分页
     * @param  outsrcAssessApply 对象
     * @param pager 分页对象
     * @param request /
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "id",value = "ID",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "instanceId",value = "流程实例id",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "flowId",value = "流程键值",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "deptId",value = "部门id",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "deptName",value = "部门名称",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "applier",value = "申请人",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "isDel",value = "逻辑删除",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "createDate",value = "创建时间",dataType = "local_date_time",paramType = "query"),
       @ApiImplicitParam(name = "createUser",value = "创建人",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "updateDate",value = "修改时间",dataType = "local_date_time",paramType = "query"),
       @ApiImplicitParam(name = "updateUser",value = "修改人",dataType = "string",paramType = "query"),
       // 分页公共参数
       @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
       @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody OutsrcEmpAssessApplyDto outsrcAssessApply,
                                      @RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        outsrcAssessApply.setIsDel(LogicEnums.VALID.getCode());
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String perm = ppmOutsourcingBasicService.getPermissionByUserid(user);
        outsrcAssessApply.setPerm(perm);
        outsrcAssessApply.setPermMgr(user.getUserid());
        List<OutsrcEmpAssessApplyDto> dtoList = outsrcAssessApplyService.queryPager(outsrcAssessApply,pager);
        result.setResults(dtoList);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /*
     * 不分页查询
     * @param  outsrcAssessApply 对象
     * @return ApiResult<List>

    @PostMapping(value = "queryList")
    @ApiOperation(value ="List", notes = "不分页查询")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "id", value = "ID",dataType = "big_decimal", paramType = "query"),
       @ApiImplicitParam(name = "instanceId", value = "流程实例id",dataType = "string", paramType = "query"),
       @ApiImplicitParam(name = "flowId", value = "流程键值",dataType = "string", paramType = "query"),
       @ApiImplicitParam(name = "deptId", value = "部门id",dataType = "big_decimal", paramType = "query"),
       @ApiImplicitParam(name = "deptName", value = "部门名称",dataType = "string", paramType = "query"),
       @ApiImplicitParam(name = "applier", value = "申请人",dataType = "string", paramType = "query"),
       @ApiImplicitParam(name = "isDel", value = "逻辑删除",dataType = "string", paramType = "query"),
       @ApiImplicitParam(name = "createDate", value = "创建时间",dataType = "local_date_time", paramType = "query"),
       @ApiImplicitParam(name = "createUser", value = "创建人",dataType = "string", paramType = "query"),
       @ApiImplicitParam(name = "updateDate", value = "修改时间",dataType = "local_date_time", paramType = "query"),
       @ApiImplicitParam(name = "updateUser", value = "修改人",dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryList(@RequestBody OutsrcEmpAssessApply outsrcAssessApply) {
        ApiResult<List> result = new ApiResult<>();
        List<OutsrcEmpAssessApply> items= outsrcAssessApplyService.queryList(outsrcAssessApply);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }*/


    /**
     * 依照流程实例id查询
     * @param instanceId 流程实例id
     * @return ApiResult<List>
     */
    @PostMapping("/queryDetailsByInsId")
    @ApiOperation(value = "依照流程实例id查询记录", notes = "依照流程实例id查询记录")
    public ApiResult queryDetailsByInsId(@Param("instanceId") String instanceId) {
        ApiResult<OutsrcEmpAssessApply> result =new ApiResult<>();
        OutsrcEmpAssessApplyDto entity = outsrcAssessApplyService.queryDetailsByInsId(instanceId);
        if(entity != null){
            entity.setDeptName(BusinessUtil.getDeptNameValue(entity.getDeptName(),1));
        }
        result.setResults(entity);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 依照id查询详情
     * @param  id  Long
     * @return  ApiResult
     */
    @ApiOperation(value = "依照id查询单条记录", notes = "依照id查询单条记录")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query", required = true),
    })
    @GetMapping("/queryDetails/{id}")
    public ApiResult queryDetailsById(@PathVariable("id") Long id) {
        ApiResult<OutsrcEmpAssessApplyDto> result =new ApiResult<>();
        OutsrcEmpAssessApplyDto item = outsrcAssessApplyService.queryDetailsById(id);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 依照流程实例id查询
     * @param dto 对象
     * @return ApiResult /
     */
    @PostMapping(value = "getOutsBasicInfo")
    @ApiOperation(value = "获得外包基本信息", notes = "获得外包基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<PpmOutsourcingBasicInfo>> getOutsBasicInfo(@RequestBody OutsrcEmpAssessDto dto) {
        ApiResult<List<PpmOutsourcingBasicInfo>> result = new ApiResult();
        PpmOutsourcingBasicDto basicDto = new PpmOutsourcingBasicDto();
        basicDto.setJobId(dto.getUserCode());
        basicDto.setDeptId(dto.getDeptId());
        basicDto.setPersonInCharge(dto.getPrjMgrCode());
        basicDto.setOutsourceType("1");

        List annualList = new ArrayList<>();
        List yearList = new ArrayList<>();
        List allList = new ArrayList<>();

        // 为季度时, 取外包人员花名册baisc表信息
        if ("0".equals(dto.getAssessType())) { // 季度:'0', 年度：'1'
            List<PpmOutsourcingBasicDto> list = ppmOutsourcingBasicService.queryOutList(basicDto);
            // 所选季度的最后一天
            String lastDay = DateUtil.getYearOfQuarter(dto.getAssessYear(),dto.getAssessAnnual(),"-1");
            String startDay = DateUtil.getYearOfQuarter(dto.getAssessYear(),dto.getAssessAnnual(),"0");
            Date lastDate = DateUtil.strToDate(lastDay,"yyyy-MM-dd");
            Date startDate = DateUtil.strToDate(startDay,"yyyy-MM-dd");
            list.forEach(x -> {
                // 必须是入场日期在最后一天之前，且离场日期为空或离场日期在最后一天之后的
                // 相反的在入场日期至
                if (x.getEntryTime().after(lastDate) ||
                        (!Objects.isNull(x.getLeavingTime()) && x.getLeavingTime().before(startDate))){
                    return;
                }
                PpmOutsourcingBasicInfo obj = JsonUtil.jsonToClass(x,PpmOutsourcingBasicInfo.class);
                String orgName = outsrcAttrChgApplyService.getOrgName(obj.getOutsourceNo());
                obj.setOutsourcname(orgName);
                annualList.add(obj);
            });
        } else {
            OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
            outsrcEmpAssess.setAssessYear(dto.getAssessYear());
            outsrcEmpAssess.setPrjMgrCode(dto.getPrjMgrCode());
            yearList = outsrcEmpAssessService.getYearAssessWithAvgScore(outsrcEmpAssess);
        }
        allList.addAll(annualList);
        allList.addAll(yearList);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(allList);
        return result;
    }

    /**
     *  添加记录
     *  @param outsrcAssessApply /
     *  @param request /
     *  @return ApiResult<Object>
     */
     @PostMapping(value = "add")
     @ApiOperation(value = "添加", notes = "添加")
     public ApiResult<Object> addOutsrcAssessApply(@RequestBody OutsrcEmpAssessApplyDto outsrcAssessApply,
                                            HttpServletRequest request) {
         ApiResult<Object> result = new ApiResult<>();
         CommOperatorDoLoginDto user = super.getCurrentUser(request);
         outsrcAssessApply.setCreateUser(user.getUserid());
         outsrcAssessApply.setIsDel(LogicEnums.VALID.getCode());
         int id = outsrcAssessApplyService.addOutsrcAssessApply(outsrcAssessApply);
         if (id > 0) {
             result.setStatus(ApiCode.SUCCESS);
         } else {
             result.setStatus(ApiCode.UPATE_FAIL);
             result.setMessage("添加失败");
         }
         return result;
     }

    /**
     * 流程删除
     * @param apply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteFlow")
    @ApiOperation(value = "流程删除", notes = "流程删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号",
                              dataType = "String", paramType = "query")
    })
    public ApiResult deleteFlow(@RequestBody OutsrcEmpAssessApply apply) {
        ApiResult result = new ApiResult<>();
        int id = outsrcAssessApplyService.removeFlow(apply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 发起申请
     * @param dto /
     * @param request /
     * @return ApiResult
     */
    @PostMapping("approvalCirculate")
    @ApiOperation(value = "发起申请")
    public ApiResult<String> approvalCirculate(HttpServletRequest request,
                                               @RequestBody OutsrcEmpAssessApplyDto dto) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        String msg = outsrcAssessApplyService.approvalCirculate(dto,bps);
        result.success(msg);
        return result;
    }

    /**
     * 二级部门经理审批
     *
     * @param dto /
     * @param request /
     * @return ApiResult<String>
     */
    @PostMapping("approvedByMgrL2")
    @ApiOperation("二级部门经理审批")
    public ApiResult<String> approvedByMgrL2(@RequestBody OutsrcEmpAssessApplyDto dto,
                                             HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = outsrcAssessApplyService.approvedByMgrL2(dto, bps);
        result.success(msg);
        return result;
    }

    /**
     * 草稿
     * @param dto /
     * @param request   request
     * @return ApiResult
     */
    @PostMapping("draft")
    @ApiOperation(value = "草稿")
    public ApiResult<OutsrcEmpAssessApplyDto> draft(@RequestBody OutsrcEmpAssessApplyDto dto,
                                                       HttpServletRequest request) {
        ApiResult<OutsrcEmpAssessApplyDto> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        OutsrcEmpAssessApplyDto apply = outsrcAssessApplyService.draft(dto, bps);
        result.success("msg ... ");
        result.setResults(apply);
        return result;
    }

}