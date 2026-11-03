package com.work.ppm.application.api.otherdeptmanager;

import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerImportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardLedgerService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  外包人员监保部台账 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmAccessCardLedgerApi"})
@RestController
@RequestMapping("accessCardLedger")
@Slf4j
public class PpmAccessCardLedgerApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmAccessCardLedgerService ppmAccessCardLedgerService;

    /**
     * 分页
     * @param accessCardLedgerDto 对象
     * @param pager 分页对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmAccessCardLedgerDto accessCardLedgerDto, Pager pager, HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        ppmAccessCardLedgerService.queryPager(accessCardLedgerDto,pager,user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /*
     * 查询详情
     * @param ppmAccessCardLedgerDto 对象
     * @return ApiResult 对象

    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmAccessCardLedgerDto> queryDetail(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
        ApiResult<PpmAccessCardLedgerDto> result =new ApiResult<>();
        PpmAccessCardLedgerDto item = ppmAccessCardLedgerService.queryDetail(ppmAccessCardLedgerDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }*/

    /*
     * 添加
     * @param ppmAccessCardLedgerDto 对象
     * @return ApiResult

    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    public ApiResult<Object> addPpmAccessCardLedger(@RequestBody PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
        ApiResult<Object> result =new ApiResult<>();
        ppmAccessCardLedgerDto.setId(DateUtil.getDateRandom());

        int id = ppmAccessCardLedgerService.addPpmAccessCardLedger(ppmAccessCardLedgerDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }*/

    /**
     * 更新
     * @param accessCardLedgerDto 对象
     * @return ApiResult
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmAccessCardLedger(@RequestBody PpmAccessCardLedgerDto accessCardLedgerDto) {
        ApiResult<Object> result =new ApiResult<>();
        if ("Y".equals(accessCardLedgerDto.getIfReceiveCard()) && !"Y".equals(accessCardLedgerDto.getIfMakeCard())){
            accessCardLedgerDto.setIfMakeCard("Y");
        }
        int id = ppmAccessCardLedgerService.updatePpmAccessCardLedger(accessCardLedgerDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /*
     * 更新
     * @param accessCardLedgerDto 对象
     * @return ApiResult
     *
    @PutMapping(value = "updates")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmAccessCardLedgers(@RequestBody PpmAccessCardLedgerDto accessCardLedgerDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmAccessCardLedgerService.updatePpmAccessCardLedgers(accessCardLedgerDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }*/

    /**
     * 导入考勤信息
     *
     * @param file    对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("importAccessCard")
    @ApiOperation(value = "导入监保部台账")
    public ApiResult<Object> importAccessCard(
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        log.info("{}开始上传监保部台账！", "测试开始");
        List<PpmAccessCardLedgerImportDto> list =
                ExcelUtil.importExcel(file,0,1, PpmAccessCardLedgerImportDto.class);

        if(list == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("导入无数据或模板有误");
            return result;
        }
        long listSize = list.stream().filter(x -> x.getVirtualMachineId() == null).count();
        if(listSize == list.size()){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("导入无数据或模板有误");
            return result;
        }

        list = list.stream().filter(x -> x.getVirtualMachineId() != null).collect(Collectors.toList());
        ppmAccessCardLedgerService.importExcel(list,user);

        result.setMessage("true");
        result.setStatus(ApiCode.SUCCESS);
        log.info("{}上传监保部台账结束！", "测试结束");
        return result;
    }

}
