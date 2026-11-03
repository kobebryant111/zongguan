package com.work.ppm.application.api.rdmanager;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.work.ppm.application.dto.rdmanager.FileUploadDto;
import com.work.ppm.application.model.rdmanager.RdProjectFile;
import com.work.ppm.application.services.rdmanager.IRdProjectFileService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.RequestUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 研发项目文件
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"RdProjectFileApi"},description="研发项目管理")
@RestController
@RequestMapping("rdProjectFile")
@Slf4j
@Validated
public class RdProjectFileApi {
	/**
	 * 系统管理 接口
	 */
    @Autowired
    private IRdProjectFileService rdProjectFileService;


	/**
	 * 更新文件名称
	 * @param req
	 * @return ApiResult
	 */
	@PostMapping(value = "/update")
	@ApiOperation(value = "更新项目文件", notes = "更新项目文件")
    public ApiResult  update(@RequestBody  @Validated RdProjectFile req) {
		LambdaUpdateWrapper<RdProjectFile> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(RdProjectFile::getPkId,req.getPkId());
		updateWrapper.set(RdProjectFile::getFileName,req.getFileName());
		this.rdProjectFileService.update(updateWrapper);
    	return ApiResult.newSuccess();
	}



	/**
	 * 新建项目
	 * @param pkId
	 * @return ApiResult
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除项目文件", notes = "研发项目文件")
	public ApiResult  delete(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId) {
		LambdaUpdateWrapper<RdProjectFile> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(RdProjectFile::getPkId,pkId);
		updateWrapper.set(RdProjectFile::getDeleted,"1");
		this.rdProjectFileService.update(updateWrapper);
		return ApiResult.newSuccess();
	}

    /**
     * 分页
     * @param rdProjectId 项目id
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryByRdProjectId")
    @ApiOperation(value ="查询项目文件", notes = "研发项目文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rdProjectId", value = "项目id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryByRdProjectId(@NotBlank(message = "研发项目id不能为空")
										  @Param("rdProjectId") String rdProjectId ) {
		LambdaQueryWrapper<RdProjectFile> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RdProjectFile::getRdProjectId,rdProjectId);
		queryWrapper.eq(RdProjectFile::getDeleted,"0");
		List<RdProjectFile> list = this.rdProjectFileService.list(queryWrapper);
		list.forEach(dto -> {
			//研发项目管理-项目详情-研发项目立项资料模块 合同管理-合同详情-合同预算信息/合同及支付信息模块 文件上传删除上传时间
			if("1".equals(dto.getType()) || "2".equals(dto.getType()) || "10".equals(dto.getType()) || "11".equals(dto.getType())) {
				dto.setCreateDate(null);
			}
		});
        return ApiResult.newSuccessResults(list);
    }



	/**
	 * 查询文件
	 * @param rdProjectId 项目id
	 * @param type 类型
	 * @return ApiResult<List>
	 */
	@PostMapping(value = "query")
	@ApiOperation(value ="分页查询", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "rdProjectId", value = "项目id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "业务类型", dataType = "string", paramType = "query")
	})
	public ApiResult<List<RdProjectFile>> query(
			@NotBlank(message = "研发项目id不能为空") @Param("rdProjectId") String rdProjectId, String type) {
		LambdaQueryWrapper<RdProjectFile> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RdProjectFile::getRdProjectId,rdProjectId);
		queryWrapper.eq(StringUtil.isStringNotEmpty(type),RdProjectFile::getType,type);
		List<RdProjectFile> files = this.rdProjectFileService.list(queryWrapper);
		return ApiResult.newSuccessResults(files);
	}

	/**
	 * 上传文件
	 * @param req
	 * @param file
	 * @return Result
	 */
	@PostMapping("/upload")
	@ApiOperation(value = "上传文件",notes = "上传文件")
	public ApiResult upload(@Validated FileUploadDto req,
							@NotNull(message = "文件不能为空") MultipartFile file){
		this.rdProjectFileService.upload(req,file);
		return ApiResult.newSuccess();
	}


	/**
	 * 下载文件
	 * @param rdProjectFileId
	 *
	 */
	@PostMapping("/download")
	@ApiOperation(value = "下载文件",notes = "下载文件")
	public void download(@NotBlank(message = "研发项目id不能为空") @Param("rdProjectFileId") String rdProjectFileId,HttpServletResponse resp) throws IOException {
		RdProjectFile file = this.rdProjectFileService.getById(rdProjectFileId);
		AssertUtil.notNull(file,"文件不存在");
//		resp.setContentType("");
		resp.setHeader("Content-Disposition", "attachment;filename="+
				URLEncoder.encode("UTF-8"));
		resp.setContentType("application/octet-stream");
		IoUtil.copy(new FileInputStream(new File(file.getFilePath())),
				resp.getOutputStream());
	}


}
