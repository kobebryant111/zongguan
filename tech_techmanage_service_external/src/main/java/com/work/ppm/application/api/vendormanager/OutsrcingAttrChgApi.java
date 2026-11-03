package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendormanager.OutsrcingAttrChgDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.OutsrcingAttrChg;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IOutsrcingAttrChgService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 人员属性变更 前端控制器
 * </p>
 *
 * @since 2022-12-08
 */

@Api(produces="application/json;charset=UTF-8", tags= {"OutsrcingAttrChgApi"})
@RestController
@RequestMapping("/outsrcingAttrChg")
public class OutsrcingAttrChgApi extends BaseUploadApi {

    @Autowired
    private IOutsrcingAttrChgService outsrcingAttrChgService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 分页
     * @param  dto 对象
     * @param  request 对象
     * @param  pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "id",value = "ID",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "instanceId",value = "流程实例id",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "flowId",value = "流程键值",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "userCode",value = "用户编号",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "userName",value = "用户姓名",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "deptId",value = "所属部门id",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "position",value = "人员类型",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "serviceYear",value = "工作年限",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "price",value = "预计单价(元/人月)",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "vendorCode",value = "所属供应商",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "prjMgrCode",value = "项目经理code",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "prjMgrName",value = "项目经理name",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "chgDate",value = "变更时间",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "contractId",value = "合同id",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "attachId",value = "总办议案id",dataType = "big_decimal",paramType = "query"),
       @ApiImplicitParam(name = "remarks",value = "",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "isDel",value = "逻辑删除",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "createDate",value = "创建时间",dataType = "local_date_time",paramType = "query"),
       @ApiImplicitParam(name = "createUser",value = "创建人",dataType = "string",paramType = "query"),
       @ApiImplicitParam(name = "updateDate",value = "修改时间",dataType = "local_date_time",paramType = "query"),
       @ApiImplicitParam(name = "updateUser",value = "修改人",dataType = "string",paramType = "query"),
       // 分页公共参数
       @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
       @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody OutsrcingAttrChgDto dto,
                                      @RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String perm = ppmOutsourcingBasicService.getPermissionByUserid(user);
        dto.setPerm(perm);
        dto.setPermMgr(user.getUserid());
        dto.setIsDel(LogicEnums.VALID.getCode());
        List<OutsrcingAttrChgDto> list = outsrcingAttrChgService.queryPager(dto,pager);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 依据id查询详情
     * @param  id  Long
     * @return  ApiResult
     */
    @ApiOperation(value = "依据id查询单条记录", notes = "依据id查询单条记录")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query", required = true),
    })
    @GetMapping("/queryDetails/{id}")
    public ApiResult queryDetailsById(@PathVariable("id") Long id) {
        ApiResult<OutsrcingAttrChgDto> result =new ApiResult<>();
        OutsrcingAttrChg item = outsrcingAttrChgService.queryDetailsById(id);
        OutsrcingAttrChgDto dto = JsonUtil.jsonToClass(item, OutsrcingAttrChgDto.class);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}