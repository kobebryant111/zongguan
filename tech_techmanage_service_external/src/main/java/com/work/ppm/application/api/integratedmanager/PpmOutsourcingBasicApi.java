package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.CommDepAndOutsTreeDto;
import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicCardExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingExtInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.integratedmanager.OutFlowData;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingExtInfo;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 外包人员信息表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingBasicApi"},description="外包人员信息表")
@RestController
@RequestMapping("ppmOutsourcingBasic")
@Slf4j
public class PpmOutsourcingBasicApi extends BaseApi {
	/**
	 * 外包人员信息表结论
	 */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
	/**
	 * 外包人员拓展信息表
	 */
	@Autowired
	private IPpmOutsourcingExtService ppmOutsourcingExtService;

	/**
     * 分页
     * @param ppmOutsourcingBasic 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outsourceNo", value = "外包公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userName", value = "名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certificateType", value = "证件类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certificateNumber", value = "证件号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mobile", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门编号（对应COMM_DEPARMENT的DEPID字段）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "outsourceType", value = "外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userType", value = "结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "外包人员状态（0：在岗，1离岗）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "age", value = "年龄", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "maritalStatus", value = "婚姻状况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "education", value = "学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "生日", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "profession", value = "专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "groupId", value = "所在小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "postId", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workContent", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "college", value = "毕业学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hometown", value = "籍贯", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectNumber", value = "项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectmanager", value = "项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isOtherProject", value = "是否为其他项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sdprojectName", value = "其他项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sdmanageruserId", value = " 其他项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "personInCharge", value = "行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemNo", value = "系统名称ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemPrincipal", value = "系统负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "purchaseItem", value = "采购项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faceimage", value = "人脸照片路径", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "leavingTime", value = "离岗时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "entryTime", value = "入职时间", dataType = "date", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingBasic ppmOutsourcingBasic, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingBasicService.queryPager(ppmOutsourcingBasic,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingBasic 对象
     * @return ApiResult<PpmOutsourcingBasic>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "outsourceNo", value = "外包公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userName", value = "名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certificateType", value = "证件类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certificateNumber", value = "证件号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mobile", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门编号（对应COMM_DEPARMENT的DEPID字段）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "outsourceType", value = "外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userType", value = "结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "外包人员状态（0：在岗，1离岗）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "age", value = "年龄", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "maritalStatus", value = "婚姻状况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "education", value = "学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "生日", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "profession", value = "专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "groupId", value = "所在小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "postId", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workContent", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "college", value = "毕业学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hometown", value = "籍贯", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectNumber", value = "项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectmanager", value = "项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isOtherProject", value = "是否为其他项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sdprojectName", value = "其他项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sdmanageruserId", value = " 其他项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "personInCharge", value = "行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemNo", value = "系统名称ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemPrincipal", value = "系统负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "purchaseItem", value = "采购项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faceimage", value = "人脸照片路径", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "leavingTime", value = "离岗时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "entryTime", value = "入职时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingBasic> queryDetail(PpmOutsourcingBasic ppmOutsourcingBasic) {
        ApiResult<PpmOutsourcingBasic> result =new ApiResult<>();
        PpmOutsourcingBasic item = ppmOutsourcingBasicService.queryDetail(ppmOutsourcingBasic);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingBasic 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outsourceNo", value = "外包公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userName", value = "名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certificateType", value = "证件类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certificateNumber", value = "证件号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mobile", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门编号（对应COMM_DEPARMENT的DEPID字段）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "outsourceType", value = "外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userType", value = "结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "外包人员状态（0：在岗，1离岗）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "age", value = "年龄", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "maritalStatus", value = "婚姻状况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "education", value = "学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "生日", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "profession", value = "专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "groupId", value = "所在小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "postId", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workContent", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "college", value = "毕业学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hometown", value = "籍贯", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectNumber", value = "项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectmanager", value = "项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isOtherProject", value = "是否为其他项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sdprojectName", value = "其他项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sdmanageruserId", value = " 其他项目经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "personInCharge", value = "行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemNo", value = "系统名称ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemPrincipal", value = "系统负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "purchaseItem", value = "采购项目", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faceimage", value = "人脸照片路径", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "leavingTime", value = "离岗时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "entryTime", value = "入职时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingBasicService.updatePpmOutsourcingBasic(ppmOutsourcingBasic);
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
     * @param ppmOutsourcingBasic 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingBasic (PpmOutsourcingBasic ppmOutsourcingBasic) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingBasicService.deletePpmOutsourcingBasic(ppmOutsourcingBasic);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

	/**
	 * 组织外包树
	 * @return ApiResult<List<CommDepAndOutsTreeDto>>
	 */
	@GetMapping(value = "getDepOutsTree")
	public ApiResult<List<CommDepAndOutsTreeDto>> getDepOutsTree() {
		ApiResult<List<CommDepAndOutsTreeDto>> result = new ApiResult();
		List<CommDepAndOutsTreeDto> item = ppmOutsourcingBasicService.getDepOutsTree();
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(item);
		return result;
	}

	/**
	 * 组织外包树
	 * @return ApiResult<List<CommDepAndOutsTreeDto>>
	 */
	@GetMapping(value = "getOutsBaseInfo")
	public ApiResult<List<CommDepAndOutsTreeDto>> getOutsBaseInfo() {
		ApiResult<List<CommDepAndOutsTreeDto>> result = new ApiResult();
		List<CommDepAndOutsTreeDto> item = ppmOutsourcingBasicService.getDepOutsTree();
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(item);
		return result;
	}


	/**
	 * 获得外包基本信息
	 * @param info
	 * @return PpmOutsourcingBasicInfo
	 */
	@GetMapping(value = "getOutsBasicInfo")
	@ApiOperation(value = "获得外包基本信息", notes = "获得外包基本信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
	})
	public ApiResult<PpmOutsourcingBasicInfo> getOutsBasicInfo(PpmOutsourcingBasicInfo info) {
		ApiResult<PpmOutsourcingBasicInfo> result = new ApiResult();
		String jobid=info.getJobId();
		PpmOutsourcingBasicInfo data = ppmOutsourcingBasicService.getBaseInfo(jobid);
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(data);
		return result;
	}

//	/**
//	 * 获得外包基本信息
//	 * @param info 对象
//	 * @return PpmOutsourcingBasicInfo
//	 */
//	@PostMapping(value = "getOutsBasicInfoByTerms")
//	@ApiOperation(value = "获得外包基本信息", notes = "获得外包基本信息")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "userName", value = "名称", dataType = "string", paramType = "query"),
//	})
//	public ApiResult<List<PpmOutsourcingBasicInfo>> getOutsBasicInfoByTerms(@RequestBody  PpmOutsourcingBasic info) {
//		ApiResult<List<PpmOutsourcingBasicInfo>> result = new ApiResult();
//		List<PpmOutsourcingBasicInfo> data = ppmOutsourcingBasicService.getBaseInfoByTerms(info);
//		result.setStatus(ApiCode.SUCCESS);
//		result.setResults(data);
//		return result;
//	}

	/**
	 * 获得外包拓展信息
	 * @param info
	 * @return PpmOutsourcingBasicInfo
	 */
	@GetMapping(value = "getExtDetailAll")
	@ApiOperation(value = "获得外包拓展信息", notes = "获得外包拓展信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "工号", dataType = "string", paramType = "query"),
	})
	public ApiResult<PpmOutsourcingExtInfo> getExtDetailAll(PpmOutsourcingExtInfo info) {
		ApiResult<PpmOutsourcingExtInfo> result = new ApiResult();
		PpmOutsourcingExtInfo data = ppmOutsourcingExtService.queryDetailAll(info);
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(data);
		return result;
	}
	/**
	 * 更新
	 * @param ppmOutsourcingExtInfoDto 对象
	 * @return ApiResult<Object>
	 */
	@PostMapping(value = "updatePpmOutsourcingExt")
	@ApiOperation(value = "更新拓展信息", notes = "更新拓展信息")
	public ApiResult<Object> updatePpmOutsourcingExt(PpmOutsourcingExtInfoDto ppmOutsourcingExtInfoDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmOutsourcingExtService.updatePpmOutsourcingExt(ppmOutsourcingExtInfoDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}


	/**
	 * 获得相关流程数据
	 * @param outDataDto
	 * @return List<OutFlowData>
	 */
	@GetMapping(value = "getFlowData")
	@ApiOperation(value = "获得相关流程数据", notes = "获得相关流程数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<OutFlowData>> getFlowData(OutDataDto outDataDto) {
		ApiResult<List<OutFlowData>> result = new ApiResult();
		List<OutFlowData> item = ppmOutsourcingBasicService.getFlowData(outDataDto);
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(item);
		return result;
	}

	/**
	 * 外包花名册 - 分页
	 * @param commEmployeeDto 对象
	 * @param pager 分页对象
	 * @param request
	 * @return ApiResult
	 */
	@PostMapping("queryCrewPage")
	@ApiOperation(value = "外包花名册 - 分页")
	@ApiImplicitParams({
			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
	})
	public ApiResult queryCrewPageList(CommEmployeeDto commEmployeeDto, Pager pager, HttpServletRequest request) {
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ApiResult result = new ApiResult();
		ppmOutsourcingBasicService.queryCrewPageList(commEmployeeDto, pager,user);
		result.success("查询成功");
		result.setResults(pager.getList());
		result.setTotal(pager.getTotal());
		return result;
	}

	/**
	 * 外包花名册 - 分页
	 * @return ApiResult
	 */
	@PostMapping("queryRole")
	@ApiOperation(value = "当前登陆人角色")
	public ApiResult< List<ImRoleDoLoginDto> > queryRole(HttpServletRequest request) {
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ApiResult< List<ImRoleDoLoginDto> > result = new ApiResult();
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(user.getRoles());
		return result;

	}

	/**
	 * 外包花名册 - 分页
	 * @return ApiResult
	 */
	@PostMapping("queryPermission")
	@ApiOperation(value = "当前人花名册数据权限设置")
	public ApiResult<String> queryPermission(HttpServletRequest request) {
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ApiResult<String> result = new ApiResult();
		String item = ppmOutsourcingBasicService.getPermissionByUserid(user);
		result.setStatus(ApiCode.SUCCESS);
		result.setResults(item);
		return result;

	}


	/**
	 * 管理员外包花名册导出
	 * @param ppmOutsourcingBasicInfodto  对象
	 * @param request    request
	 * @param response  对象
	 */
	@PostMapping("exportAdminOutsourcing")
	@ApiOperation(value = "管理员外包花名册导出")
	public void exportAdminOutsourcing(PpmOutsourcingBasicExcelDto ppmOutsourcingBasicInfodto,
									   HttpServletRequest request, HttpServletResponse response) {
		ApiResult result = new ApiResult();
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		List<PpmOutsourcingBasicExcelDto> list =
				ppmOutsourcingBasicService.exportOutsourcing(ppmOutsourcingBasicInfodto, user);
		ExcelUtil.exportExcel(list, PpmOutsourcingBasicExcelDto.class,"outInfos.xls",response);
		result.success("数据导出成功");
	}

	/**
	 * 导入虚拟机账户
	 *
	 * @param file    对象
	 * @param request request
	 * @return ApiResult
	 */
	@PostMapping("importAccessCard")
	@ApiOperation(value = "导入虚拟机账户")
	public ApiResult<Object> importAccessCard(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ApiResult<Object> result = new ApiResult<>();
		CommOperatorDoLoginDto user = super.getCurrentUser(request);
		log.info("{}开始上传虚拟机账户！", "测试开始");
		List<PpmOutsourcingBasicCardExcelDto> list =
				ExcelUtil.importExcel(file,0,1,PpmOutsourcingBasicCardExcelDto.class);

		if(list == null){
			result.setStatus(ApiCode.FAIL);
			result.setMessage("导入无数据或模板有误");
			return result;
		}

		long listSize = list.stream().filter(x -> x.getJobId() == null && x.getUserName() == null &&
				x.getVmAccount() == null ).count();
		if(listSize == list.size()){
			result.setStatus(ApiCode.FAIL);
			result.setMessage("导入无数据或模板有误");
			return result;
		}

		list = list.stream().filter(x -> x.getJobId() != null
				|| x.getUserName() != null || x.getVmAccount() != null).collect(Collectors.toList());

		for (int i=0;i<list.size();i++){
			PpmOutsourcingBasicCardExcelDto cardExcelDto = list.get(i);
			if (StringUtils.isEmpty(cardExcelDto.getJobId()) ||
					StringUtils.isEmpty(cardExcelDto.getUserName()) ||
					StringUtils.isEmpty(cardExcelDto.getVmAccount())){
				result.setStatus(ApiCode.FAIL);
				result.setMessage("第"+(i+2) +"行存在数值为空。");
				return result;
			}
			PpmOutsourcingBasicInfo basicInfo
					= ppmOutsourcingBasicService.getBaseInfo(cardExcelDto.getJobId());
			if (basicInfo == null || !Objects.equals(basicInfo.getUserName(),cardExcelDto.getUserName())){
				result.setStatus(ApiCode.FAIL);
				result.setMessage("第"+(i+2) +"行无此系统编号或信息不匹配");
				return result;
			}
		}

		ppmOutsourcingBasicService.importOutsourcingCard(list,user);

		result.setMessage("true");
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 外包花名册导出
	 * @param commEmployeeDto  对象
	 * @param request    request
	 * @param response  对象
	 */
	@PostMapping("exportOutsourcing")
	@ApiOperation(value = "外包花名册导出")
	public void exportOutsourcing(CommEmployeeDto commEmployeeDto,
									   HttpServletRequest request, HttpServletResponse response) {
		ApiResult result = new ApiResult();
		CommOperatorDoLoginDto user = this.getCurrentUser(request);
		List<CommEmployeeInfo> list =
				ppmOutsourcingBasicService.exportBasicOutsourcing(commEmployeeDto, user);
		ExcelUtil.exportExcel(list, CommEmployeeInfo.class, "basicOutInfos.xlsx", response);
		result.success("数据导出成功");
	}

}
