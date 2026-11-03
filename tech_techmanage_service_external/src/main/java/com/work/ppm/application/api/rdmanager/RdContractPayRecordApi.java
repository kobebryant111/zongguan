package com.work.ppm.application.api.rdmanager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.ppm.application.model.rdmanager.RdContractPayRecord;
import com.work.ppm.application.services.rdmanager.IRdContractPayRecordService;
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
 * 合同支付记录
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"RdProjectPayRecordApi"},description="合同支付记录")
@RestController
@RequestMapping("rdContractPayRecord")
@Slf4j
@Validated
public class RdContractPayRecordApi {
	/**
	 * 合同支付记录
	 */
    @Autowired
    private IRdContractPayRecordService rdContractPayRecordService;


	/**
	 * 新建项目
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/save")
	@ApiOperation(value = "保存项目员工", notes = "合同支付记录")
    public ApiResult  save(@RequestBody  @Validated RdContractPayRecord req) {
		this.rdContractPayRecordService.saveRdContractPayRecord(req);

    	return ApiResult.newSuccess();
	}



	/**
	 * 新建项目
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除项目员工", notes = "合同支付记录")
	public ApiResult  delete(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		this.rdContractPayRecordService.deleteById(pkId);//removeById(pkId);
		return ApiResult.newSuccess();
	}

    /**
     * 查询所有支付记录
     * @param rdProjectContractId 合同id
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryByRdProjectContractId")
    @ApiOperation(value ="查询项目员工", notes = "合同支付记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "合同", value = "合同id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryByRdProjectContractId(@NotBlank(message = "合同id不能为空")
										  @Param("rdProjectContractId") String rdProjectContractId ) {
		LambdaQueryWrapper<RdContractPayRecord> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RdContractPayRecord::getRdProjectContractId,rdProjectContractId);
		List<RdContractPayRecord> list = this.rdContractPayRecordService.list(queryWrapper);
        return ApiResult.newSuccessResults(list);
    }


}
