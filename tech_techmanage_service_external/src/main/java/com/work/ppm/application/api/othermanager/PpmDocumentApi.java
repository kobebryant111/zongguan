package com.work.ppm.application.api.othermanager;

import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 资源文档接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDocumentApi"},description = "资源文档接口")
@RestController
@RequestMapping("ppmDocument")
@Slf4j
public class PpmDocumentApi extends BaseUploadApi {
    /**
     * 资源文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;

    /**
     * 分页
     * @param ppmDocument 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmDocument ppmDocument,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDocumentService.queryPager(ppmDocument,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 获取图片地址
     * @param ppmDocument 对象
     * @return ApiResult<PpmDocument>
     */
    @GetMapping(value = "getImageUrl")
    @ApiOperation(value = "获取图片地址", notes = "获取图片地址")
    @ApiImplicitParam(name = "documentno",value = "文档编号", dataType = "string", paramType = "query",required = true)
    public ApiResult<PpmDocument> getImageUrl(PpmDocument ppmDocument) {
        ApiResult<PpmDocument> result =new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(ppmDocument.getDocumentno())) {
        	result.setStatus(ApiCode.PARAMETER_ERROR);
        	result.setMessage("请求参数有误");
        	return result;
		}
        PpmDocument item = ppmDocumentService.getImageUrl(ppmDocument.getDocumentno());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 预览
     *
     * @param ppmDocument  对象
     * @return ApiResult<PpmDocument>
     */
    @GetMapping(value = {"preview","preview/{documentno}"})
    @ApiOperation(value = "预览地址", notes = "预览地址")
    @ApiImplicitParam(name = "documentno",value = "文档编号", dataType = "string", paramType = "query",required = true, defaultValue = "")
    public ApiResult<PpmDocument> getPreview(PpmDocument ppmDocument) {
        ApiResult<PpmDocument> result =new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(ppmDocument.getDocumentno())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("请求参数有误");
            return result;
        }
        PpmDocument item = ppmDocumentService.getPreview(ppmDocument.getDocumentno());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmDocument 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({

			
    })
    public ApiResult<Object> updatePpmDocument(PpmDocumentDto ppmDocument) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDocumentService.updatePpmDocument(ppmDocument);
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
     * @param ppmDocument 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmDocument (PpmDocumentDto ppmDocument) {
        ApiResult result =new ApiResult<>();
        int id = ppmDocumentService.deletePpmDocument(ppmDocument);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 获取下载地址
     * @param dto 对象
     * @return ApiResult
     */
    @GetMapping("getDownloadUrl")
    @ApiOperation(value = "获取下载地址" , notes = "根据通用参数配置的key值，下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "通用参数配置key", paramType = "query", dataType = "string",required = true,  defaultValue = "PPM_NEW_DOCUMENT")
    })
    public ApiResult getDownloadUrl(PpmDocumentDto dto, HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        PpmDocumentDto item = ppmDocumentService.getDownloadUrl(dto);
        result.setResults(item);
        result.success("获取成功");
        return result;
    }

    /**
     * 下载文件
     * @param docmentno  文档编号
     * @param response response
     * @param request  request
     * @return ApiResult
     */
    @GetMapping("download/{docmentno}")
    @ApiOperation(value = "文档下载", notes = "面试文档下载")
    @ApiImplicitParam(name = "docmentno", value = "文档编号", dataType = "string", paramType = "path",defaultValue = "DOCUMENT202009236012")
    public ApiResult download(@PathVariable String docmentno, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiResult result = new ApiResult();
        this.setIp(IpUtil.getIpAddress(request));
        super.download(docmentno,response);
        result.success("下载成功");
        return null;
    }

    /**
     *  excel 上传
     * @param objectNo  唯一编号(这里一般指流程id
     * @param type  类型
     * @param catalogNo 文档表编号
     * @param request request
     * @return ApiResult
     */
    @PostMapping("uploadExcel")
    @ApiOperation(value = "excel 上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectNo", value = "唯一编号(这里一般指流程id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query", defaultValue = "MS"),
            @ApiImplicitParam(name = "catalogNo", value = "文档表编号", dataType = "string", paramType = "query", defaultValue = "CATALOG2019043000001"),
    })
    public ApiResult uploadExcel(String objectNo, String type, String catalogNo,
                                 @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        ApiResult result = new ApiResult();
        super.setFile(file);
        super.setIp(IpUtil.getIpAddress(request));
        String imageId = super.uploadImage(objectNo,type,catalogNo,request);
        result.setResults(imageId);
        result.success("上传成功");
        return result;
    }


    /**
     * 获取下载地址
     * @param dto 对象
     * @param request request
     * @return ApiResult
     */
    @GetMapping("getFileDetail")
    @ApiOperation(value = "获取下载地址" , notes = "根据documentno获取文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docmentno", value = "文档编号", dataType = "string", paramType = "path",defaultValue = "DOCUMENT202009236012")
    })
    public ApiResult getFileDetail(PpmDocumentDto dto, HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        String pathRealName = ppmDocumentService.getFileDetail(dto);
        result.setResults(pathRealName);
        result.success("获取成功");
        return result;
    }

    /**
     * 是否授权文档可以下载
     * @param documentno 文档编号
     * @param type 类型
     * @param versionName 版本
     * @param request 对象
     * @return ApiResult 对象
     */
    @GetMapping("checkedDownloadAuth")
    @ApiOperation(value = "是否授权文档可以下载")
    public ApiResult<PpmDocumentDto> checkedDownloadAuth(String documentno, String type, String versionName,
                                                         HttpServletRequest request) {
        ApiResult<PpmDocumentDto> result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        super.setIp(IpUtil.getIpAddress(request));
        PpmDocumentDto dot = super.getDocumentAuth(documentno, type, versionName, user);
        result.setResults(dot);
        result.success("授权下载");
        return result;
    }
}
