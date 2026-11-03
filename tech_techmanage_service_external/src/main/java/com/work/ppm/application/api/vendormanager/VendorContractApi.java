package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.VendorContractDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.Doc;
import com.work.ppm.application.model.vendormanager.VendorContract;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IVendorContractService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 合同基本信息 前端控制器
 * </p>
 *
 * @since 2022-10-26
 */

@Api(produces="application/json;charset=UTF-8", tags= {"vendorContractApi"})
@RestController
@RequestMapping("/vendorContract")
public class VendorContractApi extends BaseUploadApi {

    @Autowired
    private IVendorContractService vendorContractService;
    @Autowired
    private IPpmDocumentService ppmDocumentService;
    /**
     * 分页
     * @param  vendorContract 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "contractNo", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractName", value = "合同名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vendor", value = "供应商", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "合同生效日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "合同失效日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "合同状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taxRate", value = "税率", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "isDel", value = "逻辑删除", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "proContent", value = "项目内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "settlementFrequency", value = "结算频率", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractDoc", value = "合同文档", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "payee", value = "收款人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bankDeposit", value = "开户行", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "account", value = "账号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "transactionType", value = "交易类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bankCode", value = "行号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建日期", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateDate", value = "更新日期", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "更新人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody VendorContract vendorContract, @RequestBody Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        vendorContract.setIsDel(LogicEnums.VALID.getCode());
        vendorContractService.queryPager(vendorContract, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 不分页查询
     * @param  map 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryListByMapSmall")
    @ApiOperation(value ="List", notes = "不分页查询,下拉字段")
    public ApiResult<List> queryListByMapSmall(@RequestBody HashMap map) {
        ApiResult<List> result = new ApiResult<>();
        List<VendorContract> items= vendorContractService.selectByMapSmall(map);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 依据id查询详情
     * @param  id  Long
     * @return  ApiResult<VendorContract>
     */
    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query", required = true),
    })
    @GetMapping("/queryDetails/{id}")
    public ApiResult queryDetailsById(@PathVariable("id") Long id) {
        ApiResult<VendorContractDto> result =new ApiResult<>();
        VendorContract item = vendorContractService.queryDetailsById(id);
        VendorContractDto dto = JsonUtil.jsonToClass(item, VendorContractDto.class);
        String var01 = item.getContractDoc();
        if (StringUtil.isStringNotEmpty(var01)) {
            List<String> docList = Arrays.asList(var01.split(",")) ;
            List setList = new ArrayList();
            docList.forEach(x->{
                Doc doc = new Doc();
                doc.setCode(x);
                doc.setName(ppmDocumentService.getDetail(x).getDocname());
                setList.add(doc);
            });
            dto.setDoc(setList);
        }
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     *  添加记录
     *  @param request /
     *  @param vendorContract /
     *  @return ApiResult<Object>
     */
     @PostMapping(value = "add")
     @ApiOperation(value = "添加", notes = "添加")
     public ApiResult<Object> addVendorContract(@Valid @RequestBody VendorContract vendorContract,
                                                HttpServletRequest request) {
         ApiResult<Object> result = new ApiResult<>();
         CommOperatorDoLoginDto user = super.getCurrentUser(request);
         vendorContract.setCreateUser(user.getUserid());
         vendorContract.setIsDel(LogicEnums.VALID.getCode());
         int id = vendorContractService.addVendorContract(vendorContract);
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
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updateVendorContract(@RequestBody VendorContract entity) {
        ApiResult<Object> result = new ApiResult<>();
        int id = vendorContractService.updateVendorContract(entity);
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
     * @param id 记录id集合
     * @return ApiResult
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deleteByIdsWithTerms(@PathVariable("id") Long id) {
        ApiResult result = new ApiResult<>();
        int c = vendorContractService.deleteByIdsWithTerms(id);
        if (c > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * excel 上传
     * @param objectNo 唯一编号
     * @param type 类型
     * @param catalogNo 文档表编号
     * @param file multipartFile
     * @param request /
     * @return ApiResult
     */
    @PostMapping("uploadExcel")
    @ApiOperation(value = "excel 上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectNo", value = "唯一编号(这里一般指流程id", dataType = "string",
                    paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query",
                    defaultValue = "RLWBXQ"),
            @ApiImplicitParam(name = "catalogNo", value = "文档表编号", dataType = "string", paramType = "query",
                    defaultValue = "CATALOG2019043000001"),
    })
    public ApiResult uploadExcel(String objectNo, String type, String catalogNo,
                                 @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        super.setFile(file);
        super.setIp(IpUtil.getIpAddress(request));
        String imageId = null;
        try {
            imageId = super.uploadImage(objectNo, type, catalogNo, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setResults(imageId);
        result.success("上传成功");
        return result;
    }

    /**
     * 计数查询
     * @param  map 对象
     * @return ApiResult<Long>
     */
    @PostMapping(value = "count")
    @ApiOperation(value ="Long", notes = "计数")
    public ApiResult<Long> counts(@RequestBody HashMap map) {
        ApiResult<Long> result = new ApiResult<>();
        Long c = vendorContractService.count(map);
        result.setResults(c);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}