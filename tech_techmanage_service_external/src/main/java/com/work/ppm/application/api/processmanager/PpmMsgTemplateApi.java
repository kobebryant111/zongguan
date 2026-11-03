package com.work.ppm.application.api.processmanager;

import com.work.ppm.application.dto.processmanager.PpmMsgTemplateDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.processmanager.PpmMsgTemplate;
import com.work.ppm.application.services.processmanager.IPpmMsgTemplateService;
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
 * 通知模板管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmMsgTemplateApi"},description="通知模板管理")
@RestController
@RequestMapping("ppmMsgTemplate")
@Slf4j
public class PpmMsgTemplateApi {
    /**
     * 通知模板管理 接口
     */
    @Autowired
    private IPpmMsgTemplateService ppmMsgTemplateService;

    /**
     * 分页
     * @param ppmMsgTemplate 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "templateno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "templatename", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "templatetype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmMsgTemplate ppmMsgTemplate,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmMsgTemplateService.queryPager(ppmMsgTemplate,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmMsgTemplate 对象
     * @return ApiResult<PpmMsgTemplate>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "编号", dataType = "long", paramType = "query", required = true),
    })
    public ApiResult<PpmMsgTemplate> queryDetail(PpmMsgTemplate ppmMsgTemplate) {
        ApiResult<PpmMsgTemplate> result =new ApiResult<>();
        PpmMsgTemplate item = ppmMsgTemplateService.queryDetail(ppmMsgTemplate);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmMsgTemplate 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "templateno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "templatename", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "templatetype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmMsgTemplate(PpmMsgTemplateDto ppmMsgTemplate) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmMsgTemplateService.updatePpmMsgTemplate(ppmMsgTemplate);
        if("add".equals(ppmMsgTemplate.getSqlStatus())){
            result.success("添加成功");
        } else {
            result.success("更新成功");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmMsgTemplate 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query", required = true),

    })
    public ApiResult deletePpmMsgTemplate (PpmMsgTemplateDto ppmMsgTemplate) {
        ApiResult result =new ApiResult<>();
        ppmMsgTemplate.setIsdel(1L);
        int id = ppmMsgTemplateService.updatePpmMsgTemplate(ppmMsgTemplate);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 查询可用的模板
     * @param ppmMsgTemplate 对象
     * @return ApiResult<List<PpmMsgTemplate>>
     */
    @ApiOperation(value = "查询可用的模板", notes = "默认情况下不许要条件")
    @GetMapping("getAvailableTemplate")
    public ApiResult<List<PpmMsgTemplate>> getAvailableTemplate(PpmMsgTemplate ppmMsgTemplate) {
        ApiResult<List<PpmMsgTemplate>> result =new ApiResult<>();
        List<PpmMsgTemplate> list = ppmMsgTemplateService.getList(ppmMsgTemplate);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }
}
