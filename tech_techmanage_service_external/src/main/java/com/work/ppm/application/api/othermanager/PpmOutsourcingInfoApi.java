package com.work.ppm.application.api.othermanager;

import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 外包商公司
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingInfoApi"},description = "外包商公司")
@RestController
@RequestMapping("ppmOutsourcingInfo")
@Slf4j
public class PpmOutsourcingInfoApi {
	/**
	 *	外包商公司 接口
	 */
    @Autowired
    private IPpmOutsourcingInfoService ppmOutsourcingInfoService;

    /**
     * 分页
     * @param ppmOutsourcingInfo 对象
     * @param pager 分页对象
     * @return ApiResult<List<PpmOutsourcingInfo>>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PPM_OUTSOURCING_INFO_ID ??EOS??????", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "outsourcname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regcapital", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "establishtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "companyaddress", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainbusiness", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "service", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactsname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactspost", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactsemail", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "caseinfo", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "qualificationinfo", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companystaser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "telephone", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companynum", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "abbreviation", value = "简称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<PpmOutsourcingInfo>> queryPager(PpmOutsourcingInfo ppmOutsourcingInfo,Pager pager) {

        ApiResult<List<PpmOutsourcingInfo>> result =new ApiResult<>();
		ppmOutsourcingInfo.setIsdel(0L);
        ppmOutsourcingInfoService.queryPager(ppmOutsourcingInfo,pager);
        result.setResults((List<PpmOutsourcingInfo>)pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

	/**
	 * 获取所有外包商
	 * @return ApiResult<List<PpmOutsourcingInfo>>
	 */
	@GetMapping("getListAll")
	@ApiOperation(value = "获取所有外包商")
    public ApiResult<List<PpmOutsourcingInfo>> getListAll() {
    	ApiResult<List<PpmOutsourcingInfo>> result = new ApiResult<>();
		List<PpmOutsourcingInfo> list = ppmOutsourcingInfoService.getListAll();
		result.setResults(list);
		result.success("获取成功");
		return result;
	}

    /**
     * 查询详情
     * @param ppmOutsourcingInfo 对象
     * @return ApiResult<PpmOutsourcingInfo>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "PPM_OUTSOURCING_INFO_ID ??EOS??????", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "outsourcname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regcapital", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "establishtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "companyaddress", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainbusiness", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "service", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactsname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactspost", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactsemail", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "caseinfo", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "qualificationinfo", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companystaser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "telephone", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companynum", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "abbreviation", value = "简称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingInfo> queryDetail(PpmOutsourcingInfo ppmOutsourcingInfo) {
        ApiResult<PpmOutsourcingInfo> result =new ApiResult<>();
        PpmOutsourcingInfo item = ppmOutsourcingInfoService.queryDetail(ppmOutsourcingInfo);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingInfo d对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PPM_OUTSOURCING_INFO_ID ??EOS??????", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "outsourcname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regcapital", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "establishtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "companyaddress", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainbusiness", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "service", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactsname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactspost", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contactsemail", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "caseinfo", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "qualificationinfo", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companystaser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "telephone", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companynum", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "abbreviation", value = "简称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingInfoService.updatePpmOutsourcingInfo(ppmOutsourcingInfo);
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
     * @param ppmOutsourcingInfo 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "deletePojo", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingInfo (PpmOutsourcingInfo ppmOutsourcingInfo) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingInfoService.deletePpmOutsourcingInfo(ppmOutsourcingInfo);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

	/**
	 * 获取有采供供应商编号的所有外包商
	 * @param ppmOutsourcingInfoDto 对象
	 * @return ApiResult<List<PpmOutsourcingInfoDto>>
	 */
	@GetMapping("getListForNotCg")
	@ApiOperation(value = "获取有采供供应商编号的所有外包商")
	public ApiResult<List<PpmOutsourcingInfoDto>> getListForNotCg(PpmOutsourcingInfoDto ppmOutsourcingInfoDto) {
		ApiResult<List<PpmOutsourcingInfoDto>> result = new ApiResult<>();
		List<PpmOutsourcingInfoDto> list =
				ppmOutsourcingInfoService.queryDetailList(ppmOutsourcingInfoDto,"getListForNotCg");
		result.setResults(list);
		result.success("获取成功");
		return result;
	}
}
