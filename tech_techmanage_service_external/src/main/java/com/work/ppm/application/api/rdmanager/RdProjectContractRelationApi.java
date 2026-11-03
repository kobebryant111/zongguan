package com.work.ppm.application.api.rdmanager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationQueryDto;
import com.work.ppm.application.model.rdmanager.RdProjectContractRelation;
import com.work.ppm.application.services.rdmanager.IRdProjectContractRelationService;
import com.work.ppm.commons.ApiResult;
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

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 项目合同关联
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"RdProjectPayRecordApi"},description="项目合同关联")
@RestController
@RequestMapping("rdProjectContractRelation")
@Slf4j
@Validated
public class RdProjectContractRelationApi {
	/**
	 * 项目合同关联
	 */
    @Autowired
    private IRdProjectContractRelationService rdProjectContractRelationService;


	/**
	 * 保存项目合同关系
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/save")
	@ApiOperation(value = "保存项目合同关系", notes = "项目合同关联")
    public ApiResult  save(@RequestBody  @Validated RdProjectContractRelation req) {
		this.rdProjectContractRelationService.saveRdProjectContractRelation(req);
//		if(StringUtil.isStringNotEmpty(req.getPkId())){
//			LambdaUpdateWrapper<RdProjectContractRelation> updateWrapper = new LambdaUpdateWrapper<>();
//			updateWrapper.eq(RdProjectContractRelation::getPkId,req.getPkId());
//			updateWrapper.set(RdProjectContractRelation::getRatio,req.getRatio());
//			updateWrapper.set(RdProjectContractRelation::getUpdateBy,UserSessionUtil.getCurrentUser().getUserid());
//			updateWrapper.set(RdProjectContractRelation::getUpdateDate,new Date());
//			this.rdProjectContractRelationService.update(updateWrapper);
//		}else{
//			LambdaQueryWrapper<RdProjectContractRelation> queryWrapper = new LambdaQueryWrapper<>();
//			queryWrapper.eq(RdProjectContractRelation::getRdProjectContractId,req.getRdProjectContractId());
//			queryWrapper.eq(RdProjectContractRelation::getRdProjectId,req.getRdProjectId());
//			List<RdProjectContractRelation> list = this.rdProjectContractRelationService.list(queryWrapper);
//			if(list!=null&&list.size()>0){
//				throw new RuntimeException("该项目已经被关联");
//			}
//			req.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
//			req.setCreateDate(new Date());
//			this.rdProjectContractRelationService.save(req);
//		}
    	return ApiResult.newSuccess();
	}



	/**
	 * 删除项目合同关联
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除项目员工", notes = "项目合同关联")
	public ApiResult  delete(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		this.rdProjectContractRelationService.removeById(pkId);
		return ApiResult.newSuccess();
	}

    /**
     * 查询所有支付记录
     * @param req 合同id
     * @return ApiResult<List>
     */
    @GetMapping(value = "query")
    @ApiOperation(value ="查询项目员工", notes = "项目合同关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "合同", value = "合同id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> query( RdProjectContractRelationQueryDto req ) {
		List<RdProjectContractRelationDto> list =
				this.rdProjectContractRelationService.query(req);
        return ApiResult.newSuccessResults(list);
    }


	/**
	 * 查询关联关系
	 * @param rdProjectId 项目id
	 * @return ApiResult<List>
	 */
	@GetMapping(value = "queryByRdProjectId")
	@ApiOperation(value ="查询关联关系", notes = "项目合同关联")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "项目id", value = "项目id", dataType = "string", paramType = "query"),
	})
	public ApiResult<List> queryByRdProjectId(@NotBlank(message = "合同id不能为空")
													  @Param("rdProjectContractId") String rdProjectId ) {
		LambdaQueryWrapper<RdProjectContractRelation> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RdProjectContractRelation::getRdProjectId,rdProjectId);
		List<RdProjectContractRelation> list = this.rdProjectContractRelationService.list(queryWrapper);
		return ApiResult.newSuccessResults(list);
	}


}
