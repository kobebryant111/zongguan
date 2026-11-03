package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.EosDictEntryDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 业务字典
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"EosDictEntryApi"}, description = "业务字典")
@RestController
@RequestMapping("eosDictEntry")
@Slf4j
public class EosDictEntryApi {
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 根据类型di查询集合
     * @param entry 对象
     * @return ApiResult<List<EosDictEntry>>
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value ="根据类型id查询集合", notes = "根据类型di查询集合")
    @ApiImplicitParam(name = "dicttypeid", value = "类型编号", dataType = "string", paramType = "query", required = true)
    public ApiResult<List<EosDictEntry>> queryList(EosDictEntry entry) {
        ApiResult<List<EosDictEntry>> result = new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(entry.getDicttypeid())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            return result;
        }
        List<EosDictEntry> list = eosDictEntryService.getTypeidForList(entry.getDicttypeid().split(","));
        result.setResults(list);
        result.success("获取信息成功");
        return result;
    }

    /**
     * 根据类型di查询集合
     * @param entry 对象
     * @return ApiResult<List<EosDictEntry>>
     */
    @GetMapping(value = "queryListForStatus")
    @ApiOperation(value ="根据类型id查询集合", notes = "根据类型id查询集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query")
    })
    public ApiResult<List<EosDictEntryDto>> queryListForStatus(EosDictEntry entry) {
        ApiResult<List<EosDictEntryDto>> result = new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(entry.getDicttypeid())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            return result;
        }
        List<EosDictEntryDto> list = eosDictEntryService.getList(entry);
        result.setResults(list);
        result.success("获取信息成功");
        return result;
    }


    /**
     * 分页
     * @param eosDictEntry 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "sortno", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "rank", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "seqno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "filter1", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "filter2", value = "", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(EosDictEntry eosDictEntry,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        eosDictEntryService.queryPager(eosDictEntry,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param eosDictEntry 对象
     * @return ApiResult<EosDictEntry>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "dicttypeid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "sortno", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "rank", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "seqno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "filter1", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "filter2", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<EosDictEntry> queryDetail(EosDictEntry eosDictEntry) {
        ApiResult<EosDictEntry> result =new ApiResult<>();
        EosDictEntry item = eosDictEntryService.queryDetail(eosDictEntry);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param eosDictEntry      * @throws Exception
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "sortno", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "rank", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "seqno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "filter1", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "filter2", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updateEosDictEntry(EosDictEntry eosDictEntry) {
        ApiResult<Object> result =new ApiResult<>();
        int id = eosDictEntryService.updateEosDictEntry(eosDictEntry);
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
     * @param eosDictEntry      对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "deletePojo", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteEosDictEntry (EosDictEntry eosDictEntry) {
        ApiResult result =new ApiResult<>();
        int id = eosDictEntryService.deleteEosDictEntry(eosDictEntry);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 获取系统来源 OA VD
     * @param request request
     * @return ApiResult
     */
    @GetMapping("getDomainName")
    @ApiOperation(value = " 获取系统来源 OA VD")
    @ApiImplicitParam(name = "ip", value = "ip地址", dataType = "string", paramType = "query",required = true)
    public ApiResult getDomainName(HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        String ip = IpUtil.getIpAddress(request);
        String domainName = eosDictEntryService.getDomainName(ip);
        result.success("获取成功");
        result.setResults(domainName);
        return result;
    }


    /**
     * 根据类型di查询集合
     * @return ApiResult<List<EosDictEntry>>
     */
    @GetMapping(value = "queryAll")
    @ApiOperation(value ="查询所有字典", notes = "查询所有字典")
    public ApiResult<List<EosDictEntry>> queryAll() {
        List<EosDictEntry> list = eosDictEntryService.queryAll();
        return ApiResult.newSuccessResults(list);
    }



}
