package com.work.ppm.application.api.rdmanager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.work.ppm.application.dto.rdmanager.ContractBatchCreationDto;
import com.work.ppm.application.dto.rdmanager.ContractResearchProjectDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractFinancialInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractPayInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.application.model.rdmanager.RdProjectContractExcel;
import com.work.ppm.application.services.rdmanager.IRdProjectContractService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 研发项目人员
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"RdProjectContractApi"},description="研发项目管理")
@RestController
@RequestMapping("rdProjectContract")
@Slf4j
@Validated
public class RdProjectContractApi {
	/**
	 * 系统管理 接口
	 */
    @Autowired
    private IRdProjectContractService rdProjectContractService;


	/**
	 * 新建项目合同
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/save")
	@ApiOperation(value = "保存项目合同", notes = "研发项目合同")
    public ApiResult  save(@RequestBody  @Validated RdProjectContract req) {
		this.rdProjectContractService.saveRdProjectContract(req);
    	return ApiResult.newSuccess();
	}


	/**
	 * 修改合同预算信息
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/updateContractInfo")
	@ApiOperation(value = "修改合同预算信息", notes = "合同")
	public ApiResult  updateContractInfo(@RequestBody  @Validated RdProjectContractInfoDto req) {
		return ApiResult.newSuccessResults(this.rdProjectContractService.updateContractInfo(req));
	}



	/**
	 * 研发项目关联
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/updateContractResearchProject")
	@ApiOperation(value = "研发项目关联", notes = "合同")
	public ApiResult  updateContractResearchProject(@RequestBody  @Validated ContractResearchProjectDto req) {
		this.rdProjectContractService.updateContractResearchProject(req);
		return ApiResult.newSuccess();
	}


	/**
	 * 合同及支付信息
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/updateContractPayInfo")
	@ApiOperation(value = "合同及支付信息", notes = "合同")
	public ApiResult  updateContractPayInfo(@RequestBody  @Validated RdProjectContractPayInfoDto req) {
		this.rdProjectContractService.updateContractPayInfo(req);
		return ApiResult.newSuccess();
	}

	/**
	 * 财务核算信息
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/updateContractFinancialInfo")
	@ApiOperation(value = "财务核算信息", notes = "合同")
	public ApiResult  updateContractFinancialInfo(@RequestBody  @Validated RdProjectContractFinancialInfoDto req) {
		this.rdProjectContractService.updateContractFinancialInfo(req);
		return ApiResult.newSuccess();
	}




	/**
	 * 新建项目
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除项目合同", notes = "研发项目合同")
	public ApiResult  delete(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(RdProjectContract::getPkId,pkId);
		updateWrapper.set(RdProjectContract::getDeleted,"1");
		this.rdProjectContractService.update(updateWrapper);
		return ApiResult.newSuccess();
	}

    /**
     * 分页
     * @param rdProjectId 项目id
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryByRdProjectId")
    @ApiOperation(value ="查询项目合同", notes = "研发项目合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rdProjectId", value = "项目id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryByRdProjectId(@NotBlank(message = "研发项目id不能为空")
										  @Param("rdProjectId") String rdProjectId ) {
		List<RdProjectContract> list = this.rdProjectContractService.queryByRdProjectId(rdProjectId);
        return ApiResult.newSuccessResults(list);
    }


	/**
	 * 分页
	 * @param rdProjectContractId 合同id
	 * @return ApiResult<RdProjectContract>
	 */
	@GetMapping(value = "rdProjectContractId")
	@ApiOperation(value ="查询项目合同", notes = "研发项目合同")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "rdProjectContractId", value = "合同id", dataType = "string", paramType = "query"),
	})
	public ApiResult<RdProjectContract> queryByRdProjectContractId(@NotBlank(message = "研发项目id不能为空")
											  @Param("rdProjectContractId") String rdProjectContractId ) {
		RdProjectContract result = this.rdProjectContractService.getById(rdProjectContractId);
		return ApiResult.newSuccessResults(result);
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
	public ApiResult<List> queryPager(RdProjectContractQueryDto req, Pager pager) {
		this.rdProjectContractService.queryPager(req,pager);
		return ApiResult.pageResult(pager);
	}

	/**
	 * 分页
	 * @param req 对象
	 * @param pager 分页对象
	 * @return ApiResult<List>
	 */
	@PostMapping(value = "queryPagerInfo")
	@ApiOperation(value ="导出合同表", notes = "导出合同表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectCode", value = "项目编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptName", value = "所属部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysName", value = "系统名称", dataType = "string", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
	})
	public void queryPagerInfo(RdProjectContractQueryDto req,@RequestBody Pager pager, HttpServletResponse response) {
		List<RdProjectContractExcel> reqs=this.rdProjectContractService.queryPagerInfo(req,pager);
		if (!reqs.isEmpty()){
			ExcelUtil.exportExcel(reqs, RdProjectContractExcel.class, "合同表.xls", response);
			log.info("导出成功。");
		}else {
			log.info("导出失败。");
		}

	}

	/**
	 * 批量新建
	 *
	 * @param file 文件
	 * @return 结果
	 */
	@PostMapping("importContract")
	@ApiOperation(value = "批量新建", notes = "批量新建合同")
	public ApiResult importAchievement(@RequestParam("file") MultipartFile file) {
		try {
			List<ContractBatchCreationDto> contractBatchCreationDtoList = ExcelUtil.importExcel(file, ContractBatchCreationDto.class);
			if (contractBatchCreationDtoList == null || contractBatchCreationDtoList.isEmpty()) {
				return ApiResult.newFailure("批量新建失败，Excel为空");
			}
			rdProjectContractService.importAchievement(contractBatchCreationDtoList);
		} catch (Exception e){
			return ApiResult.newFailure(e.getMessage());
		}
		return ApiResult.newSuccess("批量新建成功");
	}

}
