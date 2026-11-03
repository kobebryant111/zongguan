package com.work.ppm.application.api.rdmanager;

import com.work.ppm.application.dto.integratedmanager.PpmRdEmployeeInfoExportDto;
import com.work.ppm.application.dto.rdmanager.RdProjectDetailsDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnerDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnovaDto;
import com.work.ppm.application.dto.rdmanager.RdProjectNewDto;
import com.work.ppm.application.dto.rdmanager.RdProjectQueryDto;
import com.work.ppm.application.model.rdmanager.CommRdEmployeeDto;
import com.work.ppm.application.model.rdmanager.RdProject;
import com.work.ppm.application.model.rdmanager.RdProjectExcel;
import com.work.ppm.application.services.rdmanager.IRdProjectFileService;
import com.work.ppm.application.services.rdmanager.IRdProjectService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 研发项目
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"RdProjectApi"},description="研发项目管理")
@RestController
@RequestMapping("rdProject")
@Slf4j
@Validated
public class RdProjectApi {
	/**
	 * 系统管理 接口
	 */
    @Autowired
    private IRdProjectService rdProjectService;

	/**
	 * 系统管理 接口
	 */
	@Autowired
	private IRdProjectFileService rdProjectFileService;

	/**
	 * 新建项目
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/getRdProjectDetails")
	@ApiOperation(value = "新建研发项目", notes = "研发项目")
	public ApiResult<RdProjectDetailsDto>  getRdProjectDetails
	(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		RdProjectDetailsDto result = this.rdProjectService.getRdProjectDetails(pkId);
		return ApiResult.newSuccessResults(result);
	}

	/**
	 * 新建项目
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/saveBaseInfo")
	@ApiOperation(value = "新建研发项目", notes = "研发项目")
    public ApiResult<RdProject>  saveBaseInfo(@RequestBody  @Validated RdProjectNewDto req) {
		RdProject info = this.rdProjectService.saveBaseInfo(req);
    	return ApiResult.newSuccessResults(info);
	}


	/**
	 * 修改项目内部信息
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/updateInnerInfo")
	@ApiOperation(value = "修改项目内部信息", notes = "研发项目")
	public ApiResult  updateInnerInfo(@RequestBody  @Validated RdProjectInnerDto req) {
		this.rdProjectService.updateInnerInfo(req);
		return ApiResult.newSuccess();
	}

	/**
	 * 修改项目技术信息
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/updateInnova")
	@ApiOperation(value = "修改项目技术信息", notes = "研发项目")
	public ApiResult  updateInnova(@RequestBody  @Validated RdProjectInnovaDto req) {
		this.rdProjectService.updateInnova(req);
		return ApiResult.newSuccess();
	}



	/**
	 * 新建项目
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "新建研发项目", notes = "研发项目")
	public ApiResult  delete(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		this.rdProjectService.delete(pkId);
		return ApiResult.newSuccess();
	}
	/**
	 * 项目内部资源查询
	 */
	@GetMapping(value = "queryByRdProjectCode")
	@ApiOperation(value = "项目内部资源查询", notes = "项目内部资源查询")
	public ApiResult  queryByRdProjectCode(@NotBlank(message = "研发项目id不能为空") @Param("projectCode") String projectCode) {
		List<CommRdEmployeeDto> list =this.rdProjectService.queryByRdProjectCode(projectCode);
		return ApiResult.newSuccessResults(list);
	}
    /**
     * 分页
     * @param req 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectCode", value = "项目编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptName", value = "所属部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysName", value = "系统名称", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryPager(RdProjectQueryDto req, Pager pager) {
        this.rdProjectService.queryPager(req,pager);
        return ApiResult.pageResult(pager);
    }

	/**
	 * 分页
	 * @param req 对象
	 * @param pager 分页对象
	 * @return ApiResult<List>
	 */
	@PostMapping(value = "queryPagerExcel")
	@ApiOperation(value ="项目表导出", notes = "项目表导出")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectCode", value = "项目编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptName", value = "所属部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysName", value = "系统名称", dataType = "string", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
	})
	public void queryPagerExcel(RdProjectQueryDto req,@RequestBody Pager pager, HttpServletResponse response) {
		List<RdProjectExcel> reqs= this.rdProjectService.queryPagerExcel(req,pager);
		if (!reqs.isEmpty()){
			ExcelUtil.exportExcel(reqs, RdProjectExcel.class, "项目表.xls", response);
		}
		log.info("导出成功。");
	}
}
