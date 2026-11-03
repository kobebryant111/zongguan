package com.work.ppm.application.api.outsourceledger;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.outsourceledger.OutsourceLedger;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.outsourceledger.IOutsourceLedgerService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 提前入场申请信息表 前端控制器
 * </p>
 *
 * @since 2022-11-22
 */

@Api(produces="application/json;charset=UTF-8", tags= {"OutsourceLedgerApi"})
@RestController
@RequestMapping("/outsourceLedger")
public class OutsourceLedgerApi extends BaseUploadApi {

    @Autowired
    private IOutsourceLedgerService outsourceLedgerService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;
    /**
     * 分页
     * @param  outsourceLedger 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一编号", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "外包项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "descriptions", value = "具体外包事项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptNo", value = "外包业务用工部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vendor", value = "外包服务提供商", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "registerPlace", value = "服务商注册地（境外、境内）",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "合同起始时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "合同到期日", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "amount", value = "外包项目金额", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "nums", value = "外包项目人数", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "isStation", value = "服务方式（是否驻场）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isSubcontract", value = "是否分包或转包", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateDate", value = "修改时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody OutsourceLedger outsourceLedger, @RequestBody Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        outsourceLedger.setIsDel("0");
        outsourceLedgerService.queryPager(outsourceLedger,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

//    /**
//     * 不分页查询
//     * @param  outsourceLedger 对象
//     * @return ApiResult<List>
//     */
//    @PostMapping(value = "queryList")
//    @ApiOperation(value ="List", notes = "不分页查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "唯一编号", dataType = "big_decimal", paramType = "query"),
//            @ApiImplicitParam(name = "projectName", value = "外包项目名称", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "descriptions", value = "具体外包事项", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "deptNo", value = "外包业务用工部门", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "vendor", value = "外包服务提供商", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "registerPlace", value = "服务商注册地（境外、境内）",
//                    dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "startDate", value = "合同起始时间", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "endDate", value = "合同到期日", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "amount", value = "外包项目金额", dataType = "big_decimal", paramType = "query"),
//            @ApiImplicitParam(name = "nums", value = "外包项目人数", dataType = "big_decimal", paramType = "query"),
//            @ApiImplicitParam(name = "isStation", value = "服务方式（是否驻场）", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "isSubcontract", value = "是否分包或转包", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
//            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "updateDate", value = "修改时间", dataType = "local_date_time", paramType = "query"),
//            @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
//    })
//    public ApiResult<List> queryList(@RequestBody OutsourceLedger outsourceLedger) {
//        ApiResult<List> result = new ApiResult<>();
//        List<OutsourceLedger> items= outsourceLedgerService.queryList(outsourceLedger);
//        result.setResults(items);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

//    /**
//     * 不分页查询
//     * @param  map 对象
//     * @return ApiResult<List>
//     */
//    @PostMapping(value = "queryListByMapSmall")
//    @ApiOperation(value ="List", notes = "不分页查询,下拉字段")
//    public ApiResult<List> queryListByMapSmall(@RequestBody HashMap map) {
//        ApiResult<List> result = new ApiResult<>();
//        List<OutsourceLedger> items= outsourceLedgerService.selectByMapSmall(map);
//        result.setResults(items);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

//    /**
//     * 不分页查询
//     * @param  map 对象
//     * @return ApiResult<List>
//     */
//    @PostMapping(value = "queryListByMapBasic")
//    @ApiOperation(value ="List", notes = "不分页查询,基础字段")
//    public ApiResult<List> queryListByMapBasic(@RequestBody HashMap map) {
//        ApiResult<List> result = new ApiResult<>();
//        List<OutsourceLedger> items= outsourceLedgerService.selectByMapBasic(map);
//        result.setResults(items);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

//    /**
//     * 查询详情
//     * @param outsourceLedger 对象
//     * @return  ApiResult<OutsourceLedger>
//     */
//    @PostMapping(value = "queryDetails")
//    @ApiOperation(value = "查询详情", notes = "查询详情")
//    @ApiImplicitParams({
//         @ApiImplicitParam(name = "id", value = "唯一编号", dataType = "big_decimal", paramType = "query"),
//         @ApiImplicitParam(name = "projectName", value = "外包项目名称", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "descriptions", value = "具体外包事项", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "deptNo", value = "外包业务用工部门", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "vendor", value = "外包服务提供商", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "registerPlace", value = "服务商注册地（境外、境内）", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "startDate", value = "合同起始时间", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "endDate", value = "合同到期日", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "amount", value = "外包项目金额", dataType = "big_decimal", paramType = "query"),
//         @ApiImplicitParam(name = "nums", value = "外包项目人数", dataType = "big_decimal", paramType = "query"),
//         @ApiImplicitParam(name = "isStation", value = "服务方式（是否驻场）", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "isSubcontract", value = "是否分包或转包", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
//         @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
//         @ApiImplicitParam(name = "updateDate", value = "修改时间", dataType = "local_date_time", paramType = "query"),
//         @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
//    })
//    public ApiResult<OutsourceLedger> queryDetails(@RequestBody OutsourceLedger outsourceLedger) {
//        ApiResult<OutsourceLedger> result =new ApiResult<>();
//        OutsourceLedger item = outsourceLedgerService.queryDetails(outsourceLedger);
//        result.setResults(item);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

    /**
     * 依据id查询详情
     * @param  id  Long
     * @return  ApiResult<OutsourceLedger>
     */
    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query", required = true),
    })
    @GetMapping("/queryDetails/{id}")
    public ApiResult queryDetailsById(@PathVariable("id") Long id) {

        ApiResult<OutsourceLedger> result =new ApiResult<>();
        OutsourceLedger item = outsourceLedgerService.queryDetailsById(id);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
//    /**
//     * 依据ids查询详情列表
//     * @param  ids  String
//     * @return ApiResult<List<OutsourceLedger>>
//     */
//    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "ids", value = "", dataType = "String", paramType = "query", required = true),
//    })
//    @GetMapping("/queryListByIds/{ids}")
//    public ApiResult<List<OutsourceLedger>> queryListByIds(@PathVariable("ids") String ids) {
//        ApiResult<List<OutsourceLedger>> result =new ApiResult<>();
//        if (!StringUtil.isStringNotEmpty(ids)) {
//            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
//        }
//        try {
//            List list = Arrays.stream(ids.split(",")).map(Long::parseLong)
//                    .collect(Collectors.toList());
//            List<OutsourceLedger> item = outsourceLedgerService.queryListByIds(list);
//            result.setResults(item);
//            result.setStatus(ApiCode.SUCCESS);
//        } catch (NumberFormatException e) {
//            result.setStatus(ApiCode.FAIL);
//            result.setMessage("For input < abnormal `digit` String > ");
//        }
//        return result;
//    }
//    /**
//     * 计数查询
//     * @param  map 对象
//     * @return ApiResult<Long>
//     */
//    @PostMapping(value = "count")
//    @ApiOperation(value ="Long", notes = "计数")
//    public ApiResult<Long> counts(@RequestBody HashMap map) {
//        ApiResult<Long> result = new ApiResult<>();
//        Long c = outsourceLedgerService.count(map);
//        result.setResults(c);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

    /**
     *  添加记录
     *
     *  @param outsourceLedger 实体对象
     *  @param request /
     *  @return ApiResult<Object>
     */
     @PostMapping(value = "add")
     @ApiOperation(value = "添加", notes = "添加")
     public ApiResult<Object> addOutsourceLedger(@RequestBody OutsourceLedger outsourceLedger,
                                                 HttpServletRequest request) {
         ApiResult<Object> result = new ApiResult<>();
         CommOperatorDoLoginDto user = super.getCurrentUser(request);
         outsourceLedger.setCreateUser(user.getUserid());
         outsourceLedger.setIsDel(LogicEnums.VALID.getCode());
         int id = outsourceLedgerService.addOutsourceLedger(outsourceLedger);
         if (id > 0) {
             result.setStatus(ApiCode.SUCCESS);
         } else {
             result.setStatus(ApiCode.UPATE_FAIL);
             result.setMessage("添加失败");
         }
         return result;
     }

    /**
     * 更新记录
     *
     * @param entity /
     * @return ApiResult
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updateOutsourceLedger(@RequestBody OutsourceLedger entity) {
        ApiResult<Object> result = new ApiResult<>();
        int id = outsourceLedgerService.updateOutsourceLedger(entity);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除记录
     *
     * @param ids 记录id集合
     * @return ApiResult
     */
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deleteOutsourceLedger(@PathVariable("ids") Long ids) {
        ApiResult result = new ApiResult<>();
        int id = outsourceLedgerService.deleteByIds(ids);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导入
     * @param file 文件
     * @param request   对象
     * @return   ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "导出的数据导入")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = outsourceLedgerService.importExcel(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }
}