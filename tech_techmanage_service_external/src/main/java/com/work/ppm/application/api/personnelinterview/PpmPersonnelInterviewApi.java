package com.work.ppm.application.api.personnelinterview;

import com.work.ppm.application.dto.personnelinterview.PersonnelInterviewDeleteDto;
import com.work.ppm.application.dto.personnelinterview.PersonnelInterviewSaveDto;
import com.work.ppm.application.services.personnelinterview.IPpmPersonnelInterviewService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import java.io.IOException;
import java.util.List;


/**
 * 人员约谈
 *
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmPersonnelInterviewApi"}, description="人员约谈")
@RestController
@RequestMapping("personnelInterview")
@Slf4j
@Validated
public class PpmPersonnelInterviewApi {

    /**
     * 系统管理 接口
     */
    @Autowired
    private IPpmPersonnelInterviewService ppmPersonnelInterviewService;

    /**
     * 人员约谈保存
     *
     * @return 结果
     */
    @PostMapping("/save")
    @ApiOperation(value = "人员约谈保存",notes = "人员约谈保存")
    public ApiResult save(@Validated PersonnelInterviewSaveDto req,
                          MultipartFile picFile,
                          MultipartFile wordFile){
        ppmPersonnelInterviewService.save(req, picFile, wordFile);
        return ApiResult.newSuccess();
    }

    /**
     * 人员约谈删除
     *
     * @return 结果
     */
    @PostMapping("/delete")
    @ApiOperation(value = "人员约谈删除",notes = "人员约谈删除")
    public ApiResult delete(@Validated @RequestBody PersonnelInterviewDeleteDto req){
        ppmPersonnelInterviewService.delete(req.getPersonnelInterviewIdList());
        return ApiResult.newSuccess();
    }

    /**
     * 人员约谈分页查询
     *
     * @return 结果
     */
    @PostMapping("/page")
    @ApiOperation(value = "人员约谈分页查询",notes = "人员约谈分页查询")
    public ApiResult page(Pager pager){
        ApiResult<List> result =new ApiResult<>();
        ppmPersonnelInterviewService.pageList(pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 人员约谈文件下载
     *
     * @return 结果
     */
    @GetMapping("/download")
    @ApiOperation(value = "人员约谈文件下载",notes = "人员约谈文件下载")
    public ApiResult download(@RequestParam String fileId, @RequestParam String type, HttpServletResponse response) throws IOException {
        ppmPersonnelInterviewService.download(fileId, type, response);
        return ApiResult.newSuccess();
    }

}
