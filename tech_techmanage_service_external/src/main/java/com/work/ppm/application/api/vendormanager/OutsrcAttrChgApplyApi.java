package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttatchManagementDto;
import com.work.ppm.application.dto.vendorcontract.VendorContractDto;
import com.work.ppm.application.dto.vendormanager.OutsrcAttrChgApplyDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.model.vendormanager.OutsrcAttrChgApply;
import com.work.ppm.application.model.vendormanager.VendorContract;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeOverinfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.vendormanager.IOutsrcAttrChgApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcingAttrChgService;
import com.work.ppm.application.services.vendormanager.IVendorContractService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员属性变更 前端控制器
 * </p>
 *
 * @since 2022-12-08
 */

@Api(produces="application/json;charset=UTF-8", tags= {"OutsrcAttrChgApplyApi"})
@RestController
@RequestMapping("/outsrcAttrChgApply")
public class OutsrcAttrChgApplyApi extends BaseUploadApi {

    @Autowired
    private IOutsrcAttrChgApplyService outsrcAttrChgApplyService;

    @Autowired
    private IOutsrcingAttrChgService outsrcingAttrChgService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IAttatchManagementService attatchManagementService;

    @Autowired
    private IVendorContractService vendorContractService;

    @Autowired
    private IPpmIntervieweeOverinfoService ppmIntervieweeOverinfoService;

    /**
     * 依照流程实例id查询
     * @desc 依照流程实例id查询
     * @param instanceId 流程实例id
     * @return ApiResult<List>
     */
    @PostMapping("/queryDetailsByInsId")
    @ApiOperation(value = "依照流程实例id查询记录", notes = "依照流程实例id查询记录")
    public ApiResult queryDetailsByInsId(@Param("instanceId") String instanceId) {
        ApiResult<OutsrcAttrChgApply> result =new ApiResult<>();
        OutsrcAttrChgApplyDto entity = outsrcAttrChgApplyService.queryDetailsByInsId(instanceId);
        result.setResults(entity);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 依照流程实例id查询
     * @desc 依照流程实例id查询
     * @param info /
     * @return ApiResult<List>
     */
    @PostMapping(value = "getOutsBasicInfo")
    @ApiOperation(value = "获得外包基本信息", notes = "获得外包基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<PpmOutsourcingBasicInfo>> getOutsBasicInfo(@RequestBody PpmOutsourcingBasicInfo info) {
        ApiResult<List<PpmOutsourcingBasicInfo>> result = new ApiResult();
        HashMap map= new HashMap();
        map.put("userName", info.getUserName());
        map.put("jobId", info.getJobId());
        map.put("deptId", info.getDeptId());
        map.put("personInCharge", info.getPersonInCharge());
        map.put("presenceState","1");
        List<PpmOutsourcingBasic> list = ppmOutsourcingBasicService.queryOutsList(map);
        List infoList = new ArrayList<>();
        list.stream().forEach(x -> {
            PpmOutsourcingBasicInfo obj = JsonUtil.jsonToClass(x,PpmOutsourcingBasicInfo.class);
            String orgName = outsrcAttrChgApplyService.getOrgName(obj.getOutsourceNo());
            obj.setOutsourcname(orgName);
            Long contractId = x.getNewContractId();
            if (!Objects.isNull(contractId)) {
                VendorContract vc = vendorContractService.queryDetailsById(contractId);
                if (!Objects.isNull(vc)){
                   obj.setContractName(vc.getContractName());
                }
            }
            Long zbya = x.getCeneralOfficeMotion();
            if (!Objects.isNull(zbya)) {
                AttatchManagement am = attatchManagementService.queryDetailsById(zbya);
                if (!Objects.isNull(am)){
                    obj.setAttachName(am.getFileName());
                }
            }
            infoList.add(obj);
        });
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(infoList);
        return result;
    }

    /**
     * 查询有效状态的总办议案
     * @param attatchManagementDto 对象
     * @return ApiResult<List<AttatchManagement>>
     */
    @GetMapping(value = "queryZBYAValid")
    @ApiOperation(value = "查询有效总办议案列表", notes = "查询有效总办议案列表")
    public ApiResult<List<AttatchManagement>> queryZBYAValid(AttatchManagementDto attatchManagementDto) {
        ApiResult<List<AttatchManagement>> result = new ApiResult<>();
        HashMap map = new HashMap();
        map.put("fileType",attatchManagementDto.getFileType());
        map.put("isDel","0");
        List<AttatchManagement> list = attatchManagementService.selectByMapSmall(map);
        if(list == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("无有效总办议案信息");
            return result;
        }

        List<AttatchManagement> newList = list.stream().filter(x -> "生效中".equals(x.getStatus()))
                .filter(x-> "0".equals(x.getFileType())).collect(Collectors.toList());
        result.setResults(newList);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询有效合同
     * @param  vendorContractDto 对象
     * @return ApiResult<List<VendorContract>>
     */
    @GetMapping(value = "queryContractValid")
    @ApiOperation(value = "queryContractValid", notes = "有效合同列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attachInfo", value = "总办议案编号",
                    paramType = "query", dataType = "string", required = true)
    })
    public ApiResult<List<VendorContract>> queryContractValid(VendorContractDto vendorContractDto) {
        ApiResult<List<VendorContract>> result = new ApiResult<>();
        HashMap map = new HashMap();
        map.put("attachInfo",vendorContractDto.getAttachInfo());
        map.put("outsourceType",vendorContractDto.getOutsourceType());
        map.put("isDel","0");
        List<VendorContract> list = vendorContractService.selectByMapSmall(map);

        if(list == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("无有效合同信息");
            return result;
        }
        List<VendorContract> newList = list.stream()
                .filter(x -> "生效中".equals(x.getStatus())).collect(Collectors.toList());
        result.setResults(newList);
        result.setStatus(ApiCode.SUCCESS);
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
                                               @RequestBody OutsrcAttrChgApplyDto dto) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        dto.setBps(bps);
        String msg = outsrcAttrChgApplyService.approvalCirculate(dto,bps);
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
    public ApiResult<String> approvedByMgrL2(@RequestBody OutsrcAttrChgApplyDto dto,
                                             HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = outsrcAttrChgApplyService.approvedByMgrL2(dto, bps);
        result.success(msg);
        return result;
    }

    /**
     * 一级部门经理审批
     *
     * @param dto /
     * @param request /
     * @return ApiResult<String>
     */
    @PostMapping("approvedByMgrL1")
    @ApiOperation("一级部门经理审批")
    public ApiResult<String> approvedByMgrL1(@RequestBody OutsrcAttrChgApplyDto dto,
                                             HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = outsrcAttrChgApplyService.approvedByMgrL1(dto, bps);
        result.success(msg);
        return result;
    }

    /**
     * 流程删除
     *
     * @param apply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteFlow")
    @ApiOperation(value = "流程删除", notes = "流程删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String",
                    paramType = "query", required = true)
    })
    public ApiResult deleteFlow(@RequestBody OutsrcAttrChgApplyDto apply) {
        ApiResult result = new ApiResult<>();
        int id = outsrcAttrChgApplyService.removeFlow(apply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

}