package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.projectmanager.DemandBackDto;
import com.work.ppm.application.dto.projectmanager.DemandInfoDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandnewinfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.projectmanager.IPpmDemandnewinfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
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
import java.io.IOException;
import java.util.List;

/**
 * 奋进号业务意向
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDemandnewinfoApi"},description="奋进号业务意向")
@RestController
@RequestMapping("ppmDemandnewinfo")
@Slf4j
public class PpmDemandnewinfoApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDemandnewinfoService ppmDemandnewinfoService;

	/**
		 * 创建业务意向流程-奋进号
		 * @param demandInfoDto 对象
		 * @return   ApiResult
		 */
		@PostMapping("create")
		@ApiOperation(value = "创建业务意向流程-奋进号",notes = "传输格式为application/json;charset=UTF-8")
		public ApiResult createDemandneFlow(@RequestBody DemandInfoDto demandInfoDto) {
			ApiResult result = new ApiResult();
			ppmDemandnewinfoService.createDemandneFlow(demandInfoDto);
			result.setResults(demandInfoDto.getDemandno());
			result.success("创建流程成功");
			return result;
	}
	/**
	 * 研发二级部经理意向初审-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("preliminaryReview")
	@ApiOperation(value = "研发二级部经理意向初审-奋进号",notes = "业务总经理意向审批-奋进号")
	public ApiResult preliminaryReviewDemandne(@RequestBody PpmDemandnewinfoDto demandInfoDto,
											   HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.preliminaryReviewDemandne(demandInfoDto);
		return result;
	}
	/**
	 * 业务总经理意向审批-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("busGeneral")
	@ApiOperation(value = "业务总经理意向审批-奋进号",notes = "业务总经理意向审批-奋进号")
	public ApiResult busGeneralMsgApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto, HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.busGeneralMsgApprove(demandInfoDto);
		return result;
	}
	/**
	 * 分行合规总经理审批-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("complianceGeneral")
	@ApiOperation(value = "分行合规总经理审批--奋进号",notes = "分行合规总经理审批--奋进号")
	public ApiResult complianceGeneralMsgApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
											HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.complianceGeneralMsgApprove(demandInfoDto);
		return result;
	}
	/**
	 * 分行人力资源部总经理审批-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("humanResources")
	@ApiOperation(value = "分行人力资源部总经理审批--奋进号",notes = "分行人力资源部总经理审批--奋进号")
	public ApiResult humanResourcesApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
												 HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.humanResourcesApprove(demandInfoDto);
		return result;
	}
	/**
	 * 科技总经理审批-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("techGMApprove")
	@ApiOperation(value = "科技总经理审批--奋进号",notes = "科技总经理审批--奋进号")
	public ApiResult techGMApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
										   HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.techGMApprove(demandInfoDto);
		return result;
	}

	/**
	 * 分行分管行长审批-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("branchBank")
	@ApiOperation(value = "分行分管行长审批--奋进号",notes = "分行分管行长审批--奋进号")
	public ApiResult branchBankApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
								   HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.branchBankApprove(demandInfoDto);
		return result;
	}
	/**
	 * 分行管理部审核-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("branchMgt")
	@ApiOperation(value = "分行管理部审核--奋进号",notes = "分行管理部审核--奋进号")
	public ApiResult branchMgtApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
									   HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.branchMgtApprove(demandInfoDto);
		return result;
	}

	/**
	 * 架构部三新审核-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("frameworkDept")
	@ApiOperation(value = "架构部三新审核--奋进号",notes = "架构部三新审核--奋进号")
	public ApiResult frameworkDeptApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
									  HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.frameworkDeptApprove(demandInfoDto);
		return result;
	}
	/**
	 * 架构部分管副总审批-奋进号
	 * @param demandInfoDto 对象
	 * @param request 对象
	 * @return   ApiResult
	 */
	@PostMapping("frameworkbrandeputy")
	@ApiOperation(value = "架构部分管副总审批--奋进号",notes = "架构部分管副总审批--奋进号")
	public ApiResult frameworkbrandeputyApprove(@RequestBody PpmDemandnewinfoDto demandInfoDto,
										  HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		demandInfoDto.getBps().setUserId(user.getUserid());
		result = ppmDemandnewinfoService.frameworkbrandeputyApprove(demandInfoDto);
		return result;
	}
	/**
	 * 删除流程
	 * @param demandBackDto 对象
	 * @return   ApiResult
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除流程-奋进号",notes = "传输格式为application/json;charset=UTF-8")
	public ApiResult removeDemand(@RequestBody DemandBackDto demandBackDto) {
		ApiResult result = ppmDemandnewinfoService.removeDemand(demandBackDto);
		return result;
	}

	/**
	 * 文件上传
	 * @param request  request
	 * @param objectNo 项目编号
	 * @param file 文件
	 * @return    ApiResult
	 */
	@PostMapping("upload")
	@ApiOperation(value = "文件上传", notes = "预审材料文件上传")
	public ApiResult upload(MultipartFile file, @RequestParam("objectno") String objectNo,
					   HttpServletRequest request) throws IOException {
		ApiResult result = new ApiResult();
		super.setFile(file);
		super.setIp(IpUtil.getIpAddress(request));
		String fileName = this.uploadImage(objectNo,"A11", "CATALOG2014091100026",request);
		result.success("上传成功");
		result.setResults(fileName);
		return result;
	}

    /**
     * 分页
     * @param ppmDemandnewinfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cgtype", value = "外包采购类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isxmzcg", value = "是否涉及外包采购需求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tbudget", value = "科技人力预算", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "bbudget", value = "业务人力预算", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "sumworkload", value = "总工作量", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "sumworkunit", value = "总工作量单位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "proscale", value = "项目规模 GLOBAL_PROJECT_SCALE", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islarge", value = "是否大中型 02是  01否", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issjwbcgxq", value = "是否涉及外包采购需求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issjbhdzyz", value = "是否涉及包含电子印章", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "suggest", value = "审批处理意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfgzjl", value = "业务 分管总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfghz", value = "业务 分管行长", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "删除标志", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isywkjfzjl", value = "合并是否业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isftywjl", value = "合并是否业务分管总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywzjl", value = "业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sumbudget", value = "总预算", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "issueid", value = "奋进号业务意向ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywejbjl", value = "业务二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kjejbjl", value = "科技二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isywfazjl", value = "是否业务分管总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfzzjl", value = "业务分摊总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isjxkh", value = "是否绩效考核项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "researchuserid", value = "可行性分析执行人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isresearch", value = "是否可行性分析", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "demandno", value = "意向编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demandname", value = "意向名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demanduserid", value = "需求发起人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bringupdate", value = "提出时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "busiuserid", value = "业务项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "prodeuctmanageruserid", value = "产品经理",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "prodeuct", value = "所属产品", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "scenes", value = "所属场景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "apptypes", value = "应用类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "apps", value = "级联-具体应用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "types", value = "意向分类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demandsource", value = "需求来源", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demanddeptno", value = "牵头业务部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "priority", value = "优先类型  GLOBAL_PROJECT_3PRIORITY",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isnew", value = "是否新系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "forusers", value = "适用用户", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "incometype", value = "收益类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainincomes", value = "级联-具体收益", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "majorproject", value = "是否重点项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "majorprojectreason", value = "级联-紧急/重点原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demanddesc", value = "意向描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busiexpect", value = "系统期望上线日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "mSysno", value = "主系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecmanageruserid", value = "科技二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecuserid", value = "主系统研发负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busideptno", value = "系统业务主管部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tectdeptno", value = "系统科技主管部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cbudget", value = "采购预算", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sworkunit", value = "业务团队工作量单位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tworkunit", value = " 科技团队工作量单位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecworkload", value = "科技团队工作量", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "busiworkload", value = "业务团队工作量", dataType = "double", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmDemandnewinfoDto ppmDemandnewinfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDemandnewinfoService.queryPager(ppmDemandnewinfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmDemandnewinfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "cgtype", value = "外包采购类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isxmzcg", value = "是否涉及外包采购需求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tbudget", value = "科技人力预算", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "bbudget", value = "业务人力预算", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "sumworkload", value = "总工作量", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "sumworkunit", value = "总工作量单位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "proscale", value = "项目规模 GLOBAL_PROJECT_SCALE", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islarge", value = "是否大中型 02是  01否", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issjwbcgxq", value = "是否涉及外包采购需求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issjbhdzyz", value = "是否涉及包含电子印章", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "suggest", value = "审批处理意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfgzjl", value = "业务 分管总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfghz", value = "业务 分管行长", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "删除标志", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isywkjfzjl", value = "合并是否业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isftywjl", value = "合并是否业务分管总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywzjl", value = "业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sumbudget", value = "总预算", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "issueid", value = "奋进号业务意向ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywejbjl", value = "业务二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kjejbjl", value = "科技二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isywfazjl", value = "是否业务分管总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfzzjl", value = "业务分摊总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isjxkh", value = "是否绩效考核项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "researchuserid", value = "可行性分析执行人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isresearch", value = "是否可行性分析", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "demandno", value = "意向编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demandname", value = "意向名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demanduserid", value = "需求发起人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bringupdate", value = "提出时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "busiuserid", value = "业务项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "prodeuctmanageruserid", value = "产品经理",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "prodeuct", value = "所属产品", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "scenes", value = "所属场景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "apptypes", value = "应用类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "apps", value = "级联-具体应用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "types", value = "意向分类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demandsource", value = "需求来源", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demanddeptno", value = "牵头业务部门",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "priority", value = "优先类型  GLOBAL_PROJECT_3PRIORITY",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isnew", value = "是否新系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "forusers", value = "适用用户", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "incometype", value = "收益类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainincomes", value = "级联-具体收益", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "majorproject", value = "是否重点项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "majorprojectreason", value = "级联-紧急/重点原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demanddesc", value = "意向描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busiexpect", value = "系统期望上线日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "mSysno", value = "主系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecmanageruserid", value = "科技二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecuserid", value = "主系统研发负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busideptno", value = "系统业务主管部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tectdeptno", value = "系统科技主管部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cbudget", value = "采购预算", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sworkunit", value = "业务团队工作量单位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tworkunit", value = " 科技团队工作量单位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecworkload", value = "科技团队工作量", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "busiworkload", value = "业务团队工作量", dataType = "double", paramType = "query"),
			
    })
    public ApiResult<PpmDemandnewinfoDto> queryDetail(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
        ApiResult<PpmDemandnewinfoDto> result =new ApiResult<>();
        PpmDemandnewinfoDto item = ppmDemandnewinfoService.queryDetail(ppmDemandnewinfoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

	/**
	 * 手机端查询详情
	 * @param ppmDemandnewinfoDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "queryMobileDetail")
	@ApiOperation(value = "手机端查询详情", notes = "查询详情")
	public ApiResult<PpmDemandnewinfoDto> queryMobileDetail(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
		ApiResult<PpmDemandnewinfoDto> result =new ApiResult<>();
		PpmDemandnewinfoDto item = ppmDemandnewinfoService.queryMobileDetail(ppmDemandnewinfoDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 计算预算信息的工作量
	 * @param demandInfoDto 对象
	 * @return   ApiResult
	 */
	@GetMapping("budgetWorkload")
	@ApiOperation(value = "计算预算信息的工作量", notes = "计算预算信息的工作量")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sworkunit", value = "业务团队工作量单位",
					dataType = "string", paramType = "query",
					required = true),
			@ApiImplicitParam(name = "tworkunit", value = " 科技团队工作量单位",
					dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "tecworkload", value = "科技团队工作量",
					dataType = "double", paramType = "query",required = true),
			@ApiImplicitParam(name = "busiworkload", value = "业务团队工作量",
					dataType = "double", paramType = "query",required = true),
	})
    public ApiResult<PpmDemandnewinfoDto> budgetWorkload(DemandInfoDto demandInfoDto) {
    	ApiResult<PpmDemandnewinfoDto> result = new ApiResult();
    	PpmDemandnewinfoDto item = ppmDemandnewinfoService.budgetWorkload(demandInfoDto, null);
    	result.setResults(item);
    	result.success("查询成功");
    	return result;
	}

	/**
	 *查询分行主持部经理
	 * @param depno 部门编号
	 * @return   ApiResult
	 */
	@GetMapping("getBranchGeneralManager")
	@ApiOperation(value = "查询分行主持部经理", notes = "查询分行主持部经理")
	@ApiImplicitParam(name = "depno", value = "部门编号", dataType = "string", paramType = "query",required = true)
	public ApiResult<CommEmployeeDto> getBranchGeneralManager(@RequestParam String depno) {
		ApiResult<CommEmployeeDto> result = new ApiResult();
		CommEmployeeDto item = ppmDemandnewinfoService.getBranchGeneralManager(depno);
		result.setResults(item);
		result.success("查询成功");
		return result;
	}

	/**
	 ** 根据奋进号id查询流程id
	 * @param issueid 奋进号id
	 * @return  ApiResult
	 */
	@GetMapping("queryByIssueId")
    @ApiOperation(value = "根据奋进号id查询流程id")
	@ApiImplicitParam(name = "issueid", value = "奋进号业务意向ID", dataType = "string", paramType = "query")
	public ApiResult<PpmDemandnewinfoDto> queryInstanceidByIssueId(String issueid) {
		ApiResult<PpmDemandnewinfoDto> result = new ApiResult();
		PpmDemandnewinfoDto item = ppmDemandnewinfoService.queryInstanceidByIssueId(issueid);
		result.setResults(item);
		result.success("查询成功");
		return result;
	}
}
