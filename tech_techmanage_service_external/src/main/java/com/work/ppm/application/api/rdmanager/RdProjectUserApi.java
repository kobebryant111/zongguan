package com.work.ppm.application.api.rdmanager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.work.ppm.application.model.rdmanager.RdProjectUser;
import com.work.ppm.application.services.rdmanager.IRdProjectUserService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
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
import java.util.Date;
import java.util.List;

/**
 * 研发项目人员
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"RdProjectUserApi"},description="研发项目管理")
@RestController
@RequestMapping("rdProjectUser")
@Slf4j
@Validated
public class RdProjectUserApi {
	/**
	 * 系统管理 接口
	 */
    @Autowired
    private IRdProjectUserService rdProjectUserService;


	/**
	 * 新建项目
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/save")
	@ApiOperation(value = "保存项目员工", notes = "研发项目员工")
    public ApiResult  save(@RequestBody  @Validated RdProjectUser req) {
		if(StringUtil.isStringNotEmpty(req.getPkId())){
			LambdaUpdateWrapper<RdProjectUser> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(RdProjectUser::getPkId,req.getPkId());
			updateWrapper.set(RdProjectUser::getUserId,req.getUserId());
			updateWrapper.set(RdProjectUser::getUserName,req.getUserName());
			updateWrapper.set(RdProjectUser::getDeptName,req.getDeptName());
			this.rdProjectUserService.update(updateWrapper);
		}else{
			req.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
			req.setCreateDate(new Date());
			this.rdProjectUserService.save(req);
		}
    	return ApiResult.newSuccess();
	}



	/**
	 * 新建项目
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除项目员工", notes = "研发项目员工")
	public ApiResult  delete(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		this.rdProjectUserService.removeById(pkId);
		return ApiResult.newSuccess();
	}

    /**
     * 分页
     * @param rdProjectId 项目id
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryByRdProjectId")
    @ApiOperation(value ="查询项目员工", notes = "研发项目员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rdProjectId", value = "项目id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryByRdProjectId(@NotBlank(message = "研发项目id不能为空")
										  @Param("rdProjectId") String rdProjectId ) {
		LambdaQueryWrapper<RdProjectUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RdProjectUser::getRdProjectId,rdProjectId);
		List<RdProjectUser> list = this.rdProjectUserService.list(queryWrapper);
        return ApiResult.newSuccessResults(list);
    }


}
