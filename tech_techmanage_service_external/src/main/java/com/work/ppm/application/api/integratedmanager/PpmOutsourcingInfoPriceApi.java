package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingInfoPriceDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingInfoPrice;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingInfoPriceService;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 外包商单价
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingInfoPriceApi"},description="外包商单价")
@RestController
@RequestMapping("ppmOutsourcingInfoPrice")
@Slf4j
public class PpmOutsourcingInfoPriceApi extends BaseUploadApi {
    /**
     * 外包商单价接口
     */
    @Autowired
    private IPpmOutsourcingInfoPriceService ppmOutsourcingInfoPriceService;

    /**
     * 查询外包商单价
     * @param ppmOutsourcingInfoPriceDto 对象
     * @return ApiResult<PpmOutsourcingInfoPriceDto>
     */
    @GetMapping(value = "getOutsourcPrices")
    @ApiOperation(value ="查询外包商单价", notes = "根据外包商编号供应商编号和人员类型编号查询外包商单价，外包人员信息变更使用供应商编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键||外包商编号", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "orgcode", value = "供应商编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firststartprie", value = "1档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "firstendprie", value = "1档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "secondstartprie", value = "2档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "secondendprie", value = "2档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "thirdstartprie", value = "3档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "thirdendprie", value = "3档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "fourthstartprie", value = "4档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "fourthendprie", value = "4档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "createusrid", value = "创建人", dataType = "string", paramType = "query"),
    })
    public ApiResult<PpmOutsourcingInfoPriceDto> getOutsourcPrices(PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPriceDto) {
        ApiResult<PpmOutsourcingInfoPriceDto> result =new ApiResult<>();
        PpmOutsourcingInfoPriceDto item = ppmOutsourcingInfoPriceService.getOutsourcPrices(ppmOutsourcingInfoPriceDto.getId(), ppmOutsourcingInfoPriceDto.getPosition());
        result.setResults(item);
        result.success("获取成功");
        return result;
    }

    /**
     * 分页
     * @param ppmOutsourcingInfoPrice 对象
     * @param pager 分页对象
     * @return  ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "供应商编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firststartprie", value = "1档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "firstendprie", value = "1档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "secondstartprie", value = "2档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "secondendprie", value = "2档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "thirdstartprie", value = "3档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "thirdendprie", value = "3档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "fourthstartprie", value = "4档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "fourthendprie", value = "4档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createusrid", value = "创建人", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingInfoPriceService.queryPager(ppmOutsourcingInfoPrice,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingInfoPrice 对象
     * @return ApiResult<PpmOutsourcingInfoPrice>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "供应商编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firststartprie", value = "1档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "firstendprie", value = "1档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "secondstartprie", value = "2档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "secondendprie", value = "2档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "thirdstartprie", value = "3档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "thirdendprie", value = "3档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "fourthstartprie", value = "4档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "fourthendprie", value = "4档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createusrid", value = "创建人", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingInfoPrice> queryDetail(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        ApiResult<PpmOutsourcingInfoPrice> result =new ApiResult<>();
        PpmOutsourcingInfoPrice item = ppmOutsourcingInfoPriceService.queryDetail(ppmOutsourcingInfoPrice);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingInfoPrice 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "供应商编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firststartprie", value = "1档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "firstendprie", value = "1档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "secondstartprie", value = "2档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "secondendprie", value = "2档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "thirdstartprie", value = "3档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "thirdendprie", value = "3档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "fourthstartprie", value = "4档开始价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "fourthendprie", value = "4档结束价格", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createusrid", value = "创建人", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingInfoPriceService.updatePpmOutsourcingInfoPrice(ppmOutsourcingInfoPrice);
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
     * @param ppmOutsourcingInfoPrice 对象
     * @return  ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingInfoPrice (PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingInfoPriceService.deletePpmOutsourcingInfoPrice(ppmOutsourcingInfoPrice);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
    /**
     * 根据条件查询
     * @param ppmOutsourcingInfoPrice 对象
     * @return  ApiResult<List>
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value ="根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "orgcode", value = "供应商编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firststartprie", value = "1档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "firstendprie", value = "1档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "secondstartprie", value = "2档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "secondendprie", value = "2档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "thirdstartprie", value = "3档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "thirdendprie", value = "3档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "fourthstartprie", value = "4档开始价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "fourthendprie", value = "4档结束价格", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "createusrid", value = "创建人", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryList(PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPrice) {
        ApiResult<List> result =new ApiResult<>();
        List<PpmOutsourcingInfoPriceDto> list=ppmOutsourcingInfoPriceService.queryList(ppmOutsourcingInfoPrice);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    /**
     * 单价表下载
     *
     * @param infoPriceDto 对象
     * @param response             response
     */
    @PostMapping("priceDownload")
    @ApiOperation(value = "单价表导出")
    public void priceDownload(PpmOutsourcingInfoPriceDto infoPriceDto,HttpServletResponse response) {
        ApiResult<Object> result = new ApiResult<>();

        List<PpmOutsourcingInfoPriceDto> list = ppmOutsourcingInfoPriceService.exportExcel(infoPriceDto);
        ExcelUtil.exportExcel(list, PpmOutsourcingInfoPriceDto.class, "jtk.xls", response);
        result.success("数据导出成功");
    }
}
