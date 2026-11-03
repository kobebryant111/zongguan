package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineOrgareaDto;
import com.work.ppm.application.services.onlinemanager.IPpmOnlineOrgareaService;
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

import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOnlineOrgareaApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmOnlineOrgarea")
@Slf4j
public class PpmOnlineOrgareaApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOnlineOrgareaService ppmOnlineOrgareaService;

    /**
     * 分页
     * @param ppmOnlineOrgareaDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupledsys", value = "是否配合类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "safetytesttype", value = "互联网变更场景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "子项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "上线日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "更新者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "linkman", value = "业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "linkname", value = "业务总经理名称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOnlineOrgareaDto ppmOnlineOrgareaDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOnlineOrgareaService.queryPager(ppmOnlineOrgareaDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOnlineOrgareaDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "coupledsys", value = "是否配合类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "safetytesttype", value = "互联网变更场景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "子项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "上线日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "更新者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "linkman", value = "业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "linkname", value = "业务总经理名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOnlineOrgareaDto> queryDetail(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        ApiResult<PpmOnlineOrgareaDto> result =new ApiResult<>();
        PpmOnlineOrgareaDto item = ppmOnlineOrgareaService.queryDetail(ppmOnlineOrgareaDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOnlineOrgareaDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupledsys", value = "是否配合类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "safetytesttype", value = "互联网变更场景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "子项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "上线日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "更新者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "linkman", value = "业务总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "linkname", value = "业务总经理名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOnlineOrgareaService.updatePpmOnlineOrgarea(ppmOnlineOrgareaDto);
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
     * @param ppmOnlineOrgareaDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOnlineOrgarea (PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmOnlineOrgareaService.deletePpmOnlineOrgarea(ppmOnlineOrgareaDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

     /**
     * 根据上线编号查询系统编号
     * @param onlineno 上线编号
     * @return ApiResult
     */
    @GetMapping("querySysNoForOnlineno")
    @ApiOperation(value = "根据上线编号查询系统编号", notes = "根据上线编号查询系统编号")
    @ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query",
            required = true)
    public ApiResult<List<PpmOnlineOrgareaDto>> querySysNoForOnlineno(String onlineno) {
        ApiResult<List<PpmOnlineOrgareaDto>> result = new ApiResult<>();
        List<PpmOnlineOrgareaDto> list = ppmOnlineOrgareaService.querySysNoForOnlineno(onlineno);
        result.setResults(list);
        result.success("查询成功");
        return result;
    }

}
