package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgApplyDto;
import com.work.ppm.application.dto.projectmanager.SyncPatchObjectDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.projectmanager.IPpmFhyyxtbgApplyService;
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
import java.util.Date;
import java.util.List;

/**
 * 分管项目变更
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFhyyxtbgApplyApi"},description="分管项目变更")
@RestController
@RequestMapping("ppmFhyyxtbgApply")
@Slf4j
public class PpmFhyyxtbgApplyApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmFhyyxtbgApplyService ppmFhyyxtbgApplyService;

	/**
	 * 获取项目变更基本信息
	 * @param request  request
	 * @return  ApiResult
	 */
	@GetMapping("getProjectChangeInfo")
	@ApiOperation(value = "获取项目变更基本信息", notes = "根据当前人查询分行IT支持部经理，分行运营部总经理")
    public ApiResult getProjectChangeInfo(HttpServletRequest request) {
		ApiResult result = new ApiResult();
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		PpmFhyyxtbgApplyDto item = ppmFhyyxtbgApplyService.getProjectChangeInfo(user.getUserid(),
				user.getOperatorname(),user.getDeptId());
		result.setResults(item);
		result.success("查询成功");
		return result;
	}
	/**
	 * 文件上传
	 * @param request  request
	 * @param objecttype 项目类型
	 * @param file 文件
	 * @return    ApiResult
	 */
	@PostMapping("upload")
	@ApiOperation(value = "文件上传", notes = "文件上传")
	@ApiImplicitParam(name = "objecttype", value = "项目类型", required = true, paramType = "string")
	public ApiResult upload(MultipartFile file, @RequestParam("objecttype") String objecttype,
							HttpServletRequest request) throws IOException {
		ApiResult result = new ApiResult();
		super.setFile(file);
		super.setIp(IpUtil.getIpAddress(request));
		String docmentNo = this.uploadImage("",objecttype, "CATALOG2014091100026",request);
		result.success("上传成功");
		result.setResults(docmentNo);
		return result;
	}

	/**
	 * 保存草稿
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @param request    request
	 * @return ApiResult
	 */
	@PostMapping("save")
    @ApiOperation(value = "保存草稿", notes = "保存草稿")
	@ApiImplicitParam(name = "ppmFhyyxtbgApplyDto", value = "json格式", required = true, paramType = "body")
    public ApiResult save(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto,
						  HttpServletRequest request) {
    	ApiResult result ;
    	CommOperatorDoLoginDto user = this.getCurrentUser(request);
    	ppmFhyyxtbgApplyDto.setApplyuser(user.getUserid());
		result = ppmFhyyxtbgApplyService.save(ppmFhyyxtbgApplyDto,user);
    	return result;
	}

	/**
	 * 分行系统负责人应用变更申请
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @param request request
	 * @return   ApiResult
	 * */
	@PostMapping("liuzhuan")
	@ApiOperation(value = "分行系统负责人应用变更申请", notes = "发起并流转")
	@ApiImplicitParam(name = "ppmFhyyxtbgApplyDto", value = "json格式", required = true, paramType = "body")
	public ApiResult liuzhuan(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto,
							  HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		ppmFhyyxtbgApplyDto.setApplyuser(user.getUserid());
		result = ppmFhyyxtbgApplyService.liuzhuan(ppmFhyyxtbgApplyDto,user);
		return result;
	}

	/**
	 * 分行研发部/IT支持部经理审批
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @param request    request
	 * @return  ApiResult
	 */
	@PostMapping("banchDeve")
	@ApiOperation(value = "分行研发部/IT支持部经理审批", notes = "分行研发部/IT支持部经理审批")
	@ApiImplicitParam(name = "ppmFhyyxtbgApplyDto", value = "json格式", required = true, paramType = "body")
	public ApiResult liuzhuan2(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto,
							   HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		ppmFhyyxtbgApplyDto.setApplyuser(user.getUserid());
		result = ppmFhyyxtbgApplyService.liuzhuan2(ppmFhyyxtbgApplyDto,user);
		return result;
	}

	/**
	 * 分行科技部/运营部总经理审批
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @param request  request
	 * @return  ApiResult
	 */
	@PostMapping("banchTech")
	@ApiOperation(value = "分行科技部/运营部总经理审批", notes = "分行科技部/运营部总经理审批")
	@ApiImplicitParam(name = "ppmFhyyxtbgApplyDto", value = "json格式", required = true, paramType = "body")
	public ApiResult liuzhuan3(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		ppmFhyyxtbgApplyDto.setApplyuser(user.getUserid());
		result = ppmFhyyxtbgApplyService.liuzhuan3(ppmFhyyxtbgApplyDto);
		return result;
	}

	/**
	 * 主办人员办理
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @param request 对象
	 * @return  ApiResult
	 */
	@PostMapping("hostHandle")
	@ApiOperation(value = "主办人员办理", notes = "主办人员办理")
	@ApiImplicitParam(name = "ppmFhyyxtbgApplyDto", value = "json格式",
			required = true, paramType = "body")
	public ApiResult liuzhuan4(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto,
							   HttpServletRequest request) {
		ApiResult result ;
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		ppmFhyyxtbgApplyDto.setApplyuser(user.getUserid());
		result = ppmFhyyxtbgApplyService.liuzhuan4(ppmFhyyxtbgApplyDto);
		return result;
	}

    /**
     * 分页
     * @param ppmFhyyxtbgApplyDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "applyuser", value = "申请人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcode", value = "分行编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "applyChangeTitle", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mSysno", value = "系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changedate", value = "变更日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "fhitmanager", value = "分行IT支持部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fhyybmanager", value = "分行运营部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updatedate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "updateuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changetype", value = "变更类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "变更项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "patchtitle", value = "缺陷标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pdefectid", value = "缺陷ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changehour", value = "预计变更时长(小时)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "istopwd", value = "是否需要申请用户密码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwdnote", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usestarttime", value = "使用开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "useendtime", value = "使用结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "usehour", value = "使用时长（小时）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applycode", value = "流程编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planstarttime", value = "计划使用开始时间", dataType = "date", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmFhyyxtbgApplyService.queryPager(ppmFhyyxtbgApplyDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmFhyyxtbgApplyDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "applyuser", value = "申请人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcode", value = "分行编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "applyChangeTitle", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mSysno", value = "系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changedate", value = "变更日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "fhitmanager", value = "分行IT支持部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fhyybmanager", value = "分行运营部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updatedate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "updateuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changetype", value = "变更类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "变更项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "patchtitle", value = "缺陷标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pdefectid", value = "缺陷ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changehour", value = "预计变更时长(小时)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "istopwd", value = "是否需要申请用户密码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwdnote", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usestarttime", value = "使用开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "useendtime", value = "使用结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "usehour", value = "使用时长（小时）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applycode", value = "流程编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planstarttime", value = "计划使用开始时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<PpmFhyyxtbgApplyDto> queryDetail(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        ApiResult<PpmFhyyxtbgApplyDto> result =new ApiResult<>();
        PpmFhyyxtbgApplyDto item = ppmFhyyxtbgApplyService.queryDetail(ppmFhyyxtbgApplyDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 获取生产缺陷
     * @param request request
     * @return ApiResult
     */
    @GetMapping(value = "getProBus")
    @ApiOperation(value = "获取生产缺陷", notes = "获取生产缺陷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysId", value = "系统编号",
					dataType = "string", paramType = "query"),

    })
    public ApiResult<List<SyncPatchObjectDto>> getProBus (HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
		List<SyncPatchObjectDto> list = ppmFhyyxtbgApplyService.queryBugsList(user.getUserid());
		result.setResults(list);
		result.success("查询成功");
        return result;
    }

	/**
	 * 是否白天
	 * @param date 时间
	 * @return ApiResult
	 */
	@GetMapping("getIsDayTime")
	@ApiOperation(value = "是否白天", notes = "是否白天")
	@ApiImplicitParam(name = "date", value = "时间",
			dataType = "string", paramType = "query", required = true)
    public ApiResult<Integer> getIsDayTime(@RequestParam Date date) {
    	ApiResult<Integer> result = new ApiResult<>();
    	int type = ppmFhyyxtbgApplyService.getIsDayTime(date);
    	result.setResults(type);
    	result.success("查询成功");
    	return result;
	}

	/**
	 * 判断是否可以直接获取密码
	 * @param instanceid 流程id
	 * @return   ApiResult
	 */
	@GetMapping("queryPasswordIs")
	@ApiOperation(value = "获取密管密码", notes = "根据流程id 判断是否可以直接获取密码")
	@ApiImplicitParam(name = "instanceid", value = "流程id",
			dataType = "string", paramType = "query", required = true)
	public ApiResult queryPasswordIs(String instanceid) {
		ApiResult result = ppmFhyyxtbgApplyService.queryPasswordIs(instanceid);
		return result;
	}

	/**
	 * 流程密码获取模式
	 * （1：可直接获取密码，2：审批模式但未发起申请3：已发起申请但未开始，4：已发起申请且已开始）
	 * @param projectno 项目编号
	 * @return ApiResult
	 * */
	@GetMapping("queryPwdModel")
	@ApiOperation(value = "流程密码获取模式", notes = "（1：可直接获取密码，" +
			"2：审批模式但未发起申请3：已发起申请但未开始，4：已发起申请且已开始）" )
	@ApiImplicitParam(name = "projectno", value = "对象编号",
			dataType = "string", paramType = "query", required = true)
	public ApiResult queryPwdModel(String projectno) {
		ApiResult result = ppmFhyyxtbgApplyService.queryPwdModel(projectno);
		return result;
	}

	/**
	 * 保存并获取密管密码
	 * 没发起通用密码获取的前提下，获取密码并保存加密后的密码
	 * 如第一次点击时，维护时间 -1h 到   维护时间+预计变更时长+1小时 时间范围内,直接获取密码
	 * @param ppmFhyyxtbgApplyDto  对象
	 * @param request request
	 * @return   ApiResult
	 */
	@PostMapping("querySavePasswd")
	@ApiOperation(value = "保存并获取密管密码", notes = "没发起通用密码获取的前提下，获取密码并保存加密后的密码" )
	public ApiResult querySavePasswd(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, HttpServletRequest request) {
		CommOperatorDoLoginDto user = getCurrentUser(request);
		ApiResult result = ppmFhyyxtbgApplyService.querySavePasswd(ppmFhyyxtbgApplyDto, user);
		return result;
	}

	/**
	 * 生成运维审计工单
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @param request   request
	 * @return    ApiResult
	 */
	@PostMapping("scywsjgd")
	@ApiOperation(value = "生成运维审计工单", notes = "生成运维审计工单")
	public ApiResult<PpmFhyyxtbgApplyDto> scywsjgd(@RequestBody PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto,
												   HttpServletRequest request) {
		CommOperatorDoLoginDto user = getCurrentUser(request);
		ApiResult item = ppmFhyyxtbgApplyService.getFlowinfoForPwd(ppmFhyyxtbgApplyDto,user);
		return  item;
	}

	/**
	 * 是否隐藏按钮
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @return    ApiResult
	 */
	@GetMapping(value = "ifHiddenButton")
	@ApiOperation(value = "是否隐藏按钮", notes = "是否隐藏按钮")
	public ApiResult ifHiddenButton(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
		ApiResult result = ppmFhyyxtbgApplyService.ifHiddenButton(ppmFhyyxtbgApplyDto);
		return result;
	}

	/**
	 *	获取通用密码密钥
	 * @param request request
	 * @return   ApiResult
	 */
	@GetMapping("getGeneralPassworkKey")
	@ApiOperation(value = "获取通用密码密钥", notes = "获取通用密码密钥")
	public ApiResult<PpmFhyyxtbgApplyDto> getGeneralPassworkKey(HttpServletRequest request) {
		ApiResult<PpmFhyyxtbgApplyDto> result = new ApiResult<>();
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		String ip = IpUtil.getIpAddress(request);
		PpmFhyyxtbgApplyDto item = ppmFhyyxtbgApplyService.getGeneralPassworkKey(user, ip);
		result.setResults(item);
		result.success("查询成功");
		return result;
	}

	/**
	 * 查询详情
	 * @param ppmFhyyxtbgApplyDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "queryMobileDetail")
	@ApiOperation(value = "查询详情(手机端)", notes = "查询详情(手机端)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "applyuser", value = "申请人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compcode", value = "分行编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "applyChangeTitle", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mSysno", value = "系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changedate", value = "变更日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "fhitmanager", value = "分行IT支持部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fhyybmanager", value = "分行运营部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updatedate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "updateuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changetype", value = "变更类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "变更项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "patchtitle", value = "缺陷标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pdefectid", value = "缺陷ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changehour", value = "预计变更时长(小时)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "istopwd", value = "是否需要申请用户密码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwdnote", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usestarttime", value = "使用开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "useendtime", value = "使用结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "usehour", value = "使用时长（小时）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applycode", value = "流程编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planstarttime", value = "计划使用开始时间", dataType = "date", paramType = "query"),

	})
	public ApiResult<PpmFhyyxtbgApplyDto> queryMobileDetail(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
		ApiResult<PpmFhyyxtbgApplyDto> result =new ApiResult<>();
		PpmFhyyxtbgApplyDto item = ppmFhyyxtbgApplyService.queryMobileDetail(ppmFhyyxtbgApplyDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
}
