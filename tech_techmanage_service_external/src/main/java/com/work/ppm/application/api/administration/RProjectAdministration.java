package com.work.ppm.application.api.administration;

import cn.hutool.core.io.IoUtil;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportAllDto;
import com.work.ppm.application.dto.rdmanager.FileUploadDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.administration.ExportResearchFile;
import com.work.ppm.application.model.administration.MessUploadDto;
import com.work.ppm.application.model.administration.WorkResearchFile;
import com.work.ppm.application.model.commonmanager.CommRdDepartment;
import com.work.ppm.application.model.rdmanager.*;
import com.work.ppm.application.services.administration.IRProjectAdministrationService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 研发项目人员
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"RProjectAdministration"}, description = "研发人员工作管理")
@RestController
@RequestMapping("researchProAllocation")
@Slf4j
@Validated
public class RProjectAdministration extends BaseApi {

    @Autowired
    private IRProjectAdministrationService rProjectAdministrationService;

    /**
     * 用户项目信息展示
     *
     * @param commRdEmployeeDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "userProjectMess")
    @ApiOperation(value = "查询研发人员项目", notes = "查询研发人员项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eid", value = "员工代码", dataType = "Integer", paramType = "query"),
    })
    public ApiResult<Object> userProjectMess(@RequestBody CommRdEmployeeDto commRdEmployeeDto) {

        ApiResult<Object> result = new ApiResult<>();
        List<HrProject> list = rProjectAdministrationService.userProjectMess(commRdEmployeeDto);
        //研发工作管理-各研发人员已完结项目及未完结项目 文件上传删除上传时间
        list.forEach(dto -> dto.getChildren().forEach(file -> file.setCreateDate(null)));
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    /**
     * 研发文档上传记录导出
     *
     * @param commExportFile 对象
     * @param request        对象
     * @param response       对象
     */
    @PostMapping(value = "exportFileMsg")
    @ApiOperation(value = "导出上传文件信息", notes = "导出上传文件信息")
    public void exportFileMsg(CommExportFile commExportFile, HttpServletRequest request, HttpServletResponse response) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<ExportResearchFile> list = rProjectAdministrationService.exportFileMsg(commExportFile, user);
        if (!list.isEmpty()) {
            ExcelUtil.exportExcel(list, ExportResearchFile.class, "研发文档上传记录.xls", response);
        }
    }

    /**
     * 查询部门信息
     *
     * @param commEmployeeDto 对象
     * @return ApiResult<CommTreeDto>
     */
    @PostMapping(value = "queryDepartment")
    @ApiOperation(value = "查询部门组织树", notes = "查询部门组织树")
    @ApiImplicitParams({
    })
    public ApiResult<CommTreeDto> queryDepartment(@RequestBody CommEmployeeDto commEmployeeDto, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<CommRdDepartment> list = rProjectAdministrationService.queryDepartment(commEmployeeDto, user);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    /**
     * 查询部门信息
     *
     * @return ApiResult<RdProject>
     */
    @PostMapping(value = "queryProject")
    @ApiOperation(value = "查询研发项目", notes = "查询研发项目")
    @ApiImplicitParams({
    })
    public ApiResult<RdProject> queryProject() {
        ApiResult result = new ApiResult<>();
        List<RdProject> list = rProjectAdministrationService.queryProject();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    /**
     * 上传文件
     *
     * @param req  id
     * @param file 二进制
     * @return Result
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ApiResult upload(@Validated MessUploadDto req,
                            @NotNull(message = "文件不能为空") MultipartFile file) {
        this.rProjectAdministrationService.upload(req, file);
        return ApiResult.newSuccess();
    }

    /**
     * 文件保存
     *
     * @param workResearchFile 文件对象
     * @return ApiResult<Object>
     */
    @PostMapping("documentsChanged")
    @ApiOperation(value = "文件保存", notes = "文件保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rdProjectId", value = "项目主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sourceFileName", value = "文件名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件路径", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "deleted", value = "是否删除", dataType = "String", paramType = "query"),
    })
    public ApiResult<Object> documentsChanged(@RequestBody WorkResearchFile workResearchFile) {
        ApiResult<Object> result = new ApiResult<>();
        this.rProjectAdministrationService.documentsChanged(workResearchFile);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 文件删除
     *
     * @param workResearchFile 删除对象
     * @return ApiResult<Object>
     */
    @PostMapping("documentsDelete")
    @ApiOperation(value = "文件删除", notes = "文件删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rdProjectId", value = "项目主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sourceFileName", value = "文件名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件路径", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "deleted", value = "是否删除", dataType = "String", paramType = "query"),
    })
    public ApiResult<Object> documentsDelete(@RequestBody WorkResearchFile workResearchFile) {
        ApiResult<Object> result = new ApiResult<>();
        this.rProjectAdministrationService.documentsDelete(workResearchFile);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 文件下载
     *
     * @param pkId 文件主键Id
     * @param resp 用户
     * @throws IOException 异常
     */
    @PostMapping("downloadFile")
    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "String", paramType = "query"),
    })
    public void download(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId,
                         HttpServletResponse resp) throws IOException {
        WorkResearchFile file = this.rProjectAdministrationService.download(pkId);
        AssertUtil.notNull(file, "文件不存在");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode("UTF-8"));
        resp.setContentType("application/octet-stream");
        IoUtil.copy(new FileInputStream(new File(file.getFilePath())),
                resp.getOutputStream());
    }

    /**
     * 查询研发人员项目
     *
     * @param rdQueryDeptType 对象
     * @param request         对象
     * @return ApiResult
     */
    @PostMapping(value = "rdQueryDeptTree")
    @ApiOperation(value = "查询研发人员项目", notes = "查询研发人员项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "", value = "", dataType = "", paramType = ""),
    })
    public ApiResult<Object> rdQueryDeptTree(@RequestBody RdQueryDeptType rdQueryDeptType, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<CommRdEmployeeDto> queryPagerList = rProjectAdministrationService.rdQueryDeptTree(rdQueryDeptType, user);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(queryPagerList);
        return result;
    }

    @PostMapping("downloadFiles")
    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "String", paramType = "query")
    })
    public void downloadFiles(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId,
                              @Param("belongUser") String belongUser, HttpServletResponse resp) throws IOException {
        WorkResearchFile file = this.rProjectAdministrationService.downloadFiles(pkId,belongUser);
        AssertUtil.notNull(file, "文件不存在");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode("UTF-8"));
        resp.setContentType("application/octet-stream");
        IoUtil.copy(new FileInputStream(new File(file.getFilePath())),
                resp.getOutputStream());
        new File(file.getFilePath()).delete();
    }


    /**
     * 更新children值
     *
     * @param pkId 主键
     * @return ApiResult<Object>
     */
    @PostMapping(value = "renewChildren")
    @ApiOperation(value = "更新children值", notes = "更新children值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query")
    })
    public ApiResult<Object> renewChildren(@NotBlank(message = "研发项目id不能为空") @Param("pkId") String pkId ,
                                           @Param("badge")String badge) {
        ApiResult result = new ApiResult<>();
        List<WorkResearchFile> workResearchFiles = rProjectAdministrationService.renewChildren(pkId, badge);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(workResearchFiles);
        return result;
    }
}