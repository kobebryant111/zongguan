package com.work.ppm.application.api.markmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.ExcelWaterRemarkUtils;
import com.work.ppm.commons.utils.ImageRemarkUtil;
import com.work.ppm.commons.utils.WaterMarkForWordPdf;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件增加水印
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmMarkApi"},description="文件增加水印")
@RestController
@RequestMapping("ppmMarkPm")
@Slf4j
public class PpmMarkApi extends BaseApi {

    /**
     * 水印角度
     */
    private static final int DEGREE = -15;

    /**
     * 文件增水印
     @param absolutePath  文件路劲
      * @param originName  文件名
     * @param userCode   工号
     * @return 返回值
     * @throws IOException IOException
     * @throws InvalidFormatException InvalidFormatException
     */
    @PostMapping(value = "addTextMark")
    @ApiOperation(value ="文件增水印", notes = "文件增水印")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "absolutePath", value = "路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "originName", value = "文件名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userCode", value = "工号", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> addTextMark(String absolutePath,
                                         String originName, String userCode) throws IOException,
            InvalidFormatException {
        log.info("参数absolutePath："+absolutePath);
        log.info("参数aoriginName："+originName);
        log.info("参数userCode："+userCode);

        ApiResult<Object> result =new ApiResult<>();
        String originPath = absolutePath + "/" + originName;
        String waterMark=userCode;
        String successMsg = "添加水印成功";

        String[] split = originPath.split("\\.");
        String suffix = split[split.length - 1];

        if (("png").equals(suffix.toLowerCase()) || ("jpg").equals(suffix.toLowerCase())) {
            ImageRemarkUtil.markImageByTextAb(waterMark, originPath, originPath, DEGREE);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage(successMsg);
            return result;
        }

        if (("xlsx").equals(suffix.toLowerCase())) {
            ExcelWaterRemarkUtils.addWaterMarkToXlsx(originPath, waterMark);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage(successMsg);
            return result;
        }
        if (("xls").equals(suffix.toLowerCase())) {
           ExcelWaterRemarkUtils.printExcelXlsAb(originPath, waterMark, originPath);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage(successMsg);
            return result;
        }
        if (("docx").equals(suffix.toLowerCase())||("doc").equals(suffix.toLowerCase())) {
            WaterMarkForWordPdf.addWordWaterMark(originPath, originPath, waterMark);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage(successMsg);
            return result;
        } else {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("添加水印失败");
            return result;
        }
    }
}
