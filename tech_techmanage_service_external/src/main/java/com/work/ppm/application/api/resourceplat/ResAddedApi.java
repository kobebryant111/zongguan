package com.work.ppm.application.api.resourceplat;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.application.services.resourceplat.IResAddedService;
import com.work.ppm.application.services.resourceplat.IResAddedShopService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源新增
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResAddedApi"})
@RestController
@RequestMapping("resAdded")
@Slf4j
public class ResAddedApi extends BaseApi {

    /**
     * resAddedService
     */
    @Autowired
    private IResAddedService resAddedService;
    /**
     * resAddedShopService
     */
    @Autowired
    private IResAddedShopService resAddedShopService;

    /**
     * 分页
     *
     * @param resAddedDto 对象
     * @param pager       分页对象
     * @param request     请求
     * @return ApiResult<List> 列表
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priceReqNo", value = "价格申请单号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "creator", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rangeDate", value = "申请时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "expectedOnlineSTimeRangDate", value = "预计上线时间",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sourceType", value = "申请类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "busiDeptId", value = "所属业务部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "techDeptId", value = "所属技术部ID", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

            @ApiImplicitParam(name = "request", value = "请求", dataType = "HttpServletRequest", paramType = "query")

    })
    public ApiResult<List> queryPager(ResAddedDto resAddedDto, Pager pager, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        if (StringUtils.isEmpty(resAddedDto.getCreator())) {
            resAddedDto.setCreator(user.getUserid());
        }

        //对时间做特殊处理
        String rangeDate = resAddedDto.getRangeDate();
        if (StringUtils.isNotEmpty(rangeDate)) {
            String[] tmp = rangeDate.split(",");
            resAddedDto.setStartTime(tmp[0]);
            resAddedDto.setEndTime(tmp[1]);
        }

        // 对预期上线时间做特殊处理
        String expectedOnlineTimeRangDate = resAddedDto.getExpectedOnlineTimeRangDate();
        if (StringUtils.isNotEmpty(expectedOnlineTimeRangDate)) {
            String[] tmp = expectedOnlineTimeRangDate.split(",");
            resAddedDto.setExpectedOnlineStartTime(tmp[0]);
            resAddedDto.setExpectedOnlineEndTime(tmp[1]);
        }
        ApiResult<List> result = new ApiResult<>();
        resAddedService.queryPager(resAddedDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除资源新增配置
     *
     * @param resAddedDto 对象
     * @return List
     */
    @PostMapping(value = "deleteResourceAdded")
    @ApiOperation(value = "删除资源新增配置", notes = "删除资源新增配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete")
    })
    public ApiResult<List> deleteResourceAdded(ResAddedDto resAddedDto) {
        ApiResult<List> result = new ApiResult<>();
        try {
            resAddedService.deleteResourceAdded(resAddedDto);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询关联系统树
     *
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryRelaSysTree")
    @ApiOperation(value = "查询关联系统树", notes = "查询关联系统树-2")
    @ApiImplicitParams({
    })
    public ApiResult queryRelaSysTree() {
        ApiResult result = new ApiResult<>();
        List<CommTreeDto> list = resAddedService.queryRelaSysTree();
        result.success("获取成功");
        result.setResults(list);
        return result;
    }

    /**
     * 查询关联系统信息
     *
     * @param pcode 系统ID
     * @return List 列表
     */
    @PostMapping(value = "queryRelaSysData")
    @ApiOperation(value = "查询关联系统信息", notes = "查询关联系统信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pcode", value = "系统ID", dataType = "string", paramType = "query")
    })
    public ApiResult<List> queryRelaSysData(String pcode) {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String, Object>> list = resAddedService.queryRelaSysData(pcode);
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询网络申请的网络位置
     *
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryIpApplyNetAddress")
    @ApiOperation(value = "查询网络申请的网络位置", notes = "查询网络申请的网络位置-2")
    @ApiImplicitParams({
    })
    public ApiResult queryIpApplyNetAddress() {
        ApiResult result = new ApiResult<>();
        List<Map<String, Object>> list = resAddedService.queryIpApplyNetAddress();
        result.success("获取成功");
        result.setResults(list);
        return result;
    }

    /**
     * 查询关联系统树
     *
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryRelaProgramTree")
    @ApiOperation(value = "查询关联项目树", notes = "查询关联项目树-2")
    @ApiImplicitParams({
    })
    public ApiResult queryRelaProgramTree() {
        ApiResult result = new ApiResult<>();
        List<CommTreeDto> list = resAddedService.queryRelaProgramTree();
        result.success("获取成功");
        result.setResults(list);
        return result;
    }


    /**
     * 查询预先设置的情况
     *
     * @return List
     */
    @GetMapping(value = "querySelectionConfig")
    @ApiOperation(value = "查询预先设置的情况", notes = "查询预先设置的情况")
    public ApiResult<List> querySelectionConfig() {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String, Object>> list = resAddedService.querySelectionConfig();
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询预先设置的情况
     *
     * @return List
     */
    @GetMapping(value = "queryHardWareRela")
    @ApiOperation(value = "获取配件关联关系表", notes = "获取配件关联关系表")
    public ApiResult<List> queryHardWareRela() {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String, Object>> list = resAddedService.queryHardWareRela();
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询软件设置的情况
     *
     * @return List
     */
    @GetMapping(value = "querySoftWareRela")
    @ApiOperation(value = "获取软件关联关系表", notes = "获取软件关联关系表")
    public ApiResult<List> querySoftWareRela() {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String, Object>> list = resAddedService.querySoftWareRela();
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 根据条件获取套餐列表
     *
     * @param comboType 套餐类型
     * @param nodeType  节点类型
     * @return List 列表
     */
    @PostMapping(value = "queryRelaComboList")
    @ApiOperation(value = "根据条件获取套餐列表", notes = "根据条件获取套餐列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboType", value = "套餐类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", paramType = "query")
    })
    public ApiResult<List> queryRelaComboList(String comboType, String nodeType) {
        ApiResult<List> result = new ApiResult<>();
        try {
            result = resAddedService.queryRelaComboList(comboType, nodeType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 发起新增资源配置流程
     *
     * @param resAddedDto 对象
     * @param request     请求
     * @return string 字符串
     */
    @PostMapping(value = "submitResourceAdded")
    @ApiOperation(value = "发起新增资源配置流程", notes = "发起新增资源配置流程")
    public ApiResult<String> submitResourceAdded(@RequestBody ResAddedDto resAddedDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        try {
            resAddedDto.setCreator(user.getUserid());
            resAddedDto.setCreatorName(user.getOperatorname());
            String processInfo = resAddedService.submitResourceAdded(resAddedDto);
            result.setResults(processInfo);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 重新提交发起工作项
     *
     * @param flowDto     审批项管信息
     * @param resAddedDto 资源申请信息
     * @param request     请求
     * @return ResAdded 对象
     */
    @PostMapping(value = "reSubmitWorkFlow")
    @ApiOperation(value = "重新提交发起工作项", notes = "重新提交发起工作项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例ID", dataType = "string", paramType = "delete"),
    })
    public ApiResult<ResAdded> reSubmitWorkFlow(@RequestBody FlowDto flowDto,
                                                @RequestBody ResAddedDto resAddedDto,
                                                HttpServletRequest request) {
        ApiResult<ResAdded> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            if (null == user) {
                result.setStatus(ApiCode.FAIL);
                return result;
            }
            flowDto.setCreator(user.getUserid());
            resAddedDto = resAddedService.queryResourceRepeat(resAddedDto);
            resAddedDto.setCreator(user.getUserid());
            resAddedDto.setCreatorName(user.getOperatorname());
            boolean flag = resAddedService.reSubmitWorkFlow(flowDto, resAddedDto);
            if (flag) {
                String processInfo = BpsUtil.queryProcessInfo(flowDto.getProcessInstId());
                result.setMessage(processInfo);
                result.setStatus(ApiCode.SUCCESS);
            } else {
                result.setStatus(ApiCode.FAIL);
            }

        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 保存资源新增配置数据
     *
     * @param resAddedDto 对象
     * @param request     请求
     * @return map
     */
    @PostMapping(value = "addResource")
    @ApiOperation(value = "保存资源新增配置数据", notes = "保存资源新增配置数据")
    public ApiResult<Map> addResource(@RequestBody ResAddedDto resAddedDto, HttpServletRequest request) {
        ApiResult<Map> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        try {

            resAddedDto = resAddedService.queryResourceRepeat(resAddedDto);
            String addedId = resAddedDto.getId();
            String priceReqNo = resAddedDto.getPriceReqNo();

            resAddedDto.setCreator(user.getUserid());
            resAddedDto.setCreatorName(user.getOperatorname());
            resAddedDto.setStatus("1");//状态设置为1 新建
            resAddedService.addResource(resAddedDto);
            result.setStatus(ApiCode.SUCCESS);
            Map<String, Object> map = new HashMap<>();
            map.put("id", addedId);
            map.put("priceReqNo", priceReqNo);
            result.setResults(map);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("保存失败");
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询预先设置的情况
     *
     * @return list
     */
    @GetMapping(value = "queryStorageComboData")
    @ApiOperation(value = "获取存储套餐数据", notes = "获取存储套餐数据")
    public ApiResult<List> queryStorageComboData() {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<Map<String, Object>> list = resAddedService.queryStorageComboData();
            result.setResults(list);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 根据ID查询资源配置详情
     *
     * @param id 资源配置编号
     * @return ResAdded 对象
     */
    @PostMapping(value = "queryResAddedConfig")
    @ApiOperation(value = "根据ID查询资源配置详情", notes = "根据ID查询资源配置详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源配置编号", dataType = "string", paramType = "query")
    })
    public ApiResult<ResAdded> queryResAddedConfig(String id) {
        ApiResult<ResAdded> result = new ApiResult<>();
        try {
            ResAdded resAdded = resAddedService.queryResAddedConfig(id);
            result.setResults(resAdded);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取补录数据
     *
     * @param resAddedDto 对象
     * @param pager       分页
     * @return List
     */
    @PostMapping(value = "queryAdditionalList")
    @ApiOperation(value = "获取补录数据", notes = "获取补录数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyCode", value = "补录编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flowTitle", value = "流程标题", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryAdditionalList(ResAddedDto resAddedDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("applyCode", resAddedDto.getApplyCode());
            param.put("flowTitle", resAddedDto.getFlowTitle());
            resAddedService.queryAdditionalList(resAddedDto, pager);
            result.setResults(pager.getList());
            result.setStatus(ApiCode.SUCCESS);
            result.setTotal(pager.getTotal());
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 终止退回发起的流程
     *
     * @param resAddedDto 对象
     * @param request     请求
     * @return ApiResult<List> 列表
     */

    @PostMapping(value = "deleteProcessInstance")
    @ApiOperation(value = "终止退回发起的流程", notes = "终止退回发起的流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete"),
            @ApiImplicitParam(name = "processInstID", value = "流程实例ID", dataType = "string", paramType = "delete")
    })
    public ApiResult<List> deleteProcessInstance(ResAddedDto resAddedDto, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        try {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            resAddedService.deleteProcessInstance(resAddedDto, user.getUserid());
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 导出资源新增技评总办报表
     *
     * @param id       资源新增主键
     * @param response 返回
     */
    @PostMapping(value = "exportZb")
    @ApiOperation(value = "导出", notes = "导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源新增主键", dataType = "string", paramType = "delete"),
    })
    public void exportZb(String id, HttpServletResponse response) {
        resAddedService.exportZb(id, response);
    }

    /**
     * 保存主表和Item表数据
     *
     * @param resAddedDto 主表Dto对象
     * @return ApiResult
     */
    @PostMapping(value = "putIntoShop")
    @ApiOperation(value = "保存主表和Item表数据", notes = "保存主表和Item表数据")
    @ApiImplicitParams({

    })
    public ApiResult<List> putIntoShop(
            @RequestBody ResAddedDto resAddedDto,
            HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        resAddedDto.setCreator(user.getUserid());
        resAddedDto.setCreatorName(user.getOperatorname());
        ResAddedItemDto resAddedItemDto = resAddedDto.getDynamicForm();
        return resAddedShopService.saveResGood(resAddedDto, resAddedItemDto);
    }

    /**
     * 根据Id查询Item表数据
     *
     * @param id 主键
     * @return ApiResult
     */
    @PostMapping(value = "showItemDetailById")
    @ApiOperation(value = "根据Id查询Item表数据", notes = "根据Id查询Item表数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Item表Id", dataType = "string", paramType = "query"),
    })
    public ApiResult<ResAddedItem> showItemDetailById(String id) {
        ApiResult<ResAddedItem> result = new ApiResult<>();
        result.setResults(resAddedShopService.showItemDetailById(id));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据ItemId删除Item表数据
     *
     * @param id 主键
     * @return ApiResult
     */
    @PostMapping(value = "deleteItemByItemId")
    @ApiOperation(value = "根据Id删除Item表数据", notes = "根据Id删除Item表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Item表Id", dataType = "string", paramType = "query"),
    })
    public ApiResult<ResAddedItem> deleteItemByItemId(String id) {
        ApiResult<ResAddedItem> result = new ApiResult<>();
        resAddedShopService.deleteItemByItemId(id);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据ItemId查询Item表 parts表 version表数据
     *
     * @param id 主键
     * @return ApiResult
     */
    @PostMapping(value = "getDynamicForms")
    @ApiOperation(value = "根据Id查询Item表 parts表 version表数据", notes = "根据Id查询Item表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Item表Id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> getDynamicForms(String id) {
        ApiResult<List> result = new ApiResult<>();
        result.setResults(resAddedShopService.getDynamicForms(id));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据主表Id删除Item表 parts表 version表数据
     * @param id 主表Id
     */
    @PostMapping(value = "deleteConfigItem")
    @ApiOperation(value = "根据主表Id删除Item表 parts表 version表数据",
            notes = "根据主表Id删除Item表 parts表 version表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Item表Id", dataType = "string", paramType = "delete"),
    })
    public void deleteConfigItem(String id){
        resAddedService.deleteConfigItem(id);
    }

    /**
     * 单独保存主表数据
     * @param resAddedDto 主表Dto
     * @param request 请求
     * @return ApiResult<String> 返回值
     */
    @PostMapping(value = "saveResMain")
    @ApiOperation(value = "单独保存主表数据",notes = "单独保存主表数据")
    @ApiImplicitParams({

    })
    public ApiResult<String> saveResMain(@RequestBody ResAddedDto resAddedDto,HttpServletRequest request){
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        resAddedDto.setCreator(user.getUserid());
        resAddedDto.setCreatorName(user.getOperatorname());
        resAddedShopService.saveResMain(resAddedDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据ID校验作废数据
     * @param id 主表ID
     * @return ApiResult<String> 返回值
     */
    @PostMapping(value = "validOutofDateMain")
    @ApiOperation(value = "根据ID校验作废数据",notes = "根据ID校验作废数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Item表Id", dataType = "string", paramType = "delete"),
    })
    public ApiResult<String> validOutofDateMain(String id){
        return resAddedShopService.validOutofDateMain(id);
    }


    /**
     * 根据前端数据校验作废数据
     * @param resAddedDto 主表Dto
     * @return ApiResult<String>
     */
    @PostMapping(value = "validOutofDateMainResubmit")
    @ApiOperation(value = "根据ID校验作废数据",notes = "根据ID校验作废数据")
    @ApiImplicitParams({
    })
    public ApiResult<String> validOutofDateMainResubmit(@RequestBody ResAddedDto resAddedDto){
        return resAddedShopService.validOutofDateMainResubmit(resAddedDto);
    }

}
