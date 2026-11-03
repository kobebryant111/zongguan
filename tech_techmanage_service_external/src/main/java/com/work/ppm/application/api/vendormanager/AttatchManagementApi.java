package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttatchManagementDto;
import com.work.ppm.application.enums.AttatchTypeEnums;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.model.vendormanager.Doc;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * <p>
 * 附件管理 前端控制器
 * </p>
 *
 * @since 2022-10-26
 */

@Api(produces="application/json;charset=UTF-8", tags= {"attatchmanagementApi"})
@RestController
@RequestMapping("/attatchManagement")
public class AttatchManagementApi extends BaseUploadApi {

    @Autowired
    private IAttatchManagementService attatchManagementService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;
    @Autowired
    private  WorkFlowHttpClientService workFlowHttpClientService;

    /**
     * 文件编号序列max
     */
    private static String MAX_SERIAL = "1000";
    /**
     * 文件编号序列max
     */
    private static String MIN_SERIAL = "001";
    /**
     * 未生效
     */
    private static String INVALID ="已失效";
    /**
     * 分页
     * @param  attatchManagement 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "fileNo", value = "文件编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "生效日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "失效日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "文件状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "doc", value = "文档", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isDel", value = "逻辑删除", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "totals", value = "总人数", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "availableCount", value = "可用人数", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "numberUsed", value = "已用人数", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateDate", value = "更新时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "更新人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody AttatchManagementDto attatchManagement, @RequestBody Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        attatchManagement.setIsDel(LogicEnums.VALID.getCode());
        attatchManagementService.queryPager(attatchManagement,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
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
        Long c = attatchManagementService.count(map);
        result.setResults(c);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 依据id查询详情
     * @param id 编号
     * @return ApiResult
     */
    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query", required = true),
    })
    @GetMapping("/queryDetails/{id}")
    public ApiResult queryDetailsById(@PathVariable("id") Long id) {

        ApiResult<AttatchManagementDto> result =new ApiResult<>();
        AttatchManagement item = attatchManagementService.queryDetailsById(id);
        AttatchManagementDto dto = JsonUtil.jsonToClass(item, AttatchManagementDto.class);
        String docStr = item.getDoc();
        if (StringUtil.isStringNotEmpty(docStr)) {
            List<String> docList = Arrays.asList(docStr.split(",")) ;
            List list = new ArrayList();
            docList.forEach(x->{
                Doc doc = new Doc();
                doc.setCode(x);
                doc.setName(ppmDocumentService.getDetail(x).getDocname());
                list.add(doc);
            });
            dto.setDocList(list);
        }
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 取得最新记录的文件编号
     * @return  ApiResult<HashMap>
     */
    @GetMapping(value = "queryLatestRecord")
    @ApiOperation(value = "查询最新文件编号记录", notes = "查询最新文件编号记录")
    public ApiResult<HashMap> queryLatestRecord() {
        ApiResult<HashMap> result =new ApiResult<>();
        AttatchManagement item = attatchManagementService.selectOneDesc();
        if (ObjectUtils.isEmpty(item)) {
            throw new BizException(ApiCode.FAIL, "表数据为空");
        }
        HashMap map = new HashMap();
        String fileNo = item.getFileNo();
        String resEnd = fileNo.substring(fileNo.length() -3);
        String resTmp = String.format("%03d",Integer.parseInt(resEnd) + 1);
        if (resTmp.equalsIgnoreCase(MAX_SERIAL)) {
            resTmp = MIN_SERIAL;
        }
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String fmtDate = fmt.format(date);
        resTmp = fmtDate + resTmp;
        map.put("fileNo",resTmp);
        map.put("fileName",item.getFileName());
        result.setResults(map);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询总办议案-用于下拉列表
     * @return  ApiResult<List<AttatchManagement>>
     */
    @GetMapping(value = "queryZBYA")
    @ApiOperation(value = "查询总办议案下拉", notes = "查询总办议案")
    public ApiResult<List<AttatchManagement>> queryZBYA() {
        ApiResult<List<AttatchManagement>> result =new ApiResult<>();
        HashMap map = new HashMap();
        map.put("fileType", AttatchTypeEnums.ZBYA.getCode());
        map.put("isDel", LogicEnums.VALID.getCode());
        List<AttatchManagement> list = attatchManagementService.selectByMapSmall(map);
        List<AttatchManagement> listFilter
                = list.stream().filter(x-> !INVALID.equals(x.getStatus()))
                .collect(Collectors.toList());
        result.setResults(listFilter);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     *  添加记录
     *  @param attatchManagement 实体对象
     *  @param request /
     *  @return ApiResult<Object>
     */
     @PostMapping(value = "add")
     @ApiOperation(value = "添加", notes = "添加")
     public ApiResult<Object> addAttatchManagement(@Valid @RequestBody AttatchManagement attatchManagement,
                                                   HttpServletRequest request) {
         ApiResult<Object> result = new ApiResult<>();
         CommOperatorDoLoginDto user = super.getCurrentUser(request);
         attatchManagement.setCreateUser(user.getUserid());
         attatchManagement.setIsDel(LogicEnums.VALID.getCode());
         int id = attatchManagementService.addAttatchManagement(attatchManagement);
         if (id > 0) {
             result.setStatus(ApiCode.SUCCESS);
         } else {
             result.setStatus(ApiCode.UPATE_FAIL);
             result.setMessage("添加失败");
         }
         return result;
     }

    /**
     * 记录更新
     * @param request /
     * @param dto /
     * @return ApiResult
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updateAttatchManagement(HttpServletRequest request,
                                                     @RequestBody AttatchManagementDto dto) {
        // 请求参数判空
        if (ObjectUtils.isEmpty(dto)) {
            throw new BizException(ApiCode.FAIL, "请求参数错误！");
        }
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String res = String.valueOf(attatchManagementService.updateWithFlow(dto, user));
        // 数值解析
        int num;
        try {
            num = Integer.parseInt(res);
        } catch(Exception e) {
            num = 0;
        }
        // 更新状态
        if (num >= 0) {
            result.setMessage(res);
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
    public ApiResult deleteAttatchManagement(@PathVariable("ids") Long ids) {
        ApiResult result = new ApiResult<>();
        int id = attatchManagementService.deleteByIdsWithTerms(ids);
        if (id > 0) {
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
     * @param request  /
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
}