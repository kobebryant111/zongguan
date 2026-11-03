package com.work.ppm.application.api.resourceplat;

import com.work.ppm.application.dto.resourceplat.ResConfigDto;
import com.work.ppm.application.dto.resourceplat.ResConfigQo;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResConfigSoft;
import com.work.ppm.application.model.resourceplat.ResConfigVm;
import com.work.ppm.application.services.resourceplat.IResConfigService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 资源配置
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"resConfigApi"})
@RestController
@RequestMapping("resConfig")
@Slf4j
public class ResConfigApi extends BaseApi {
    /**
     * 常量1
     */
    public static final String RESMSG_1 ="不能重复发布归档或专用数据";
    /**
     * 常量2
     */
    public static final String RESMSG_2 ="已发布过相同配置的套餐";
    /**
     * 常量3
     */
    public static final String RESMSG_3 ="已发布，不可重复发布！";
    /**
     * 配置管理接口
     */
    @Autowired
    private IResConfigService resConfigService;

    /**
     * 获取物理机列表数据
     *
     * @param resConfigDto 对象
     * @param pager        分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryConfigComboList")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboOneId", value = "", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "viewFlag", value = "", dataType = "String", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<ResConfigCombo>> queryPhysicalCombo(ResConfigDto resConfigDto, Pager pager) {
        ApiResult<List<ResConfigCombo>> result = new ApiResult<>();
        resConfigService.queryConfigComboList(resConfigDto, pager);
        result.setResults((List<ResConfigCombo>) pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 获取虚拟机列表数据
     *
     * @param resConfigDto 对象
     * @param pager        分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryVMPartsList")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "viewFlag", value = "", dataType = "String", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<ResConfigVm>> queryVMPartsList(ResConfigDto resConfigDto, Pager pager) {
        ApiResult<List<ResConfigVm>> result = new ApiResult<>();
        resConfigService.queryVMPartsList(resConfigDto, pager);
        result.setResults((List<ResConfigVm>) pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 获取软件列表数据
     *
     * @param resConfigDto 对象
     * @param pager        分页对象
     * @return ApiResult
     */
    @PostMapping(value = "querySoftList")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboOneId", value = "", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "viewFlag", value = "", dataType = "String", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<ResConfigSoft>> querySoftList(ResConfigDto resConfigDto, Pager pager) {
        ApiResult<List<ResConfigSoft>> result = new ApiResult<>();
        if("3".equals(resConfigDto.getViewFlag())){
            List<ResConfigSoft> list =resConfigService.querySoftData(resConfigDto);
            result.setResults(list);
        } else {


            resConfigService.querySoftList(resConfigDto, pager);
            result.setResults((List<ResConfigSoft>) pager.getList());
            result.setTotal(pager.getTotal());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 添加物理机数据
     *
     * @param resConfigQo 对象
     * @param request     对象
     * @return ApiResult
     */
    @PostMapping(value = "addConfigComboData")
    @ApiOperation(value = "添加数据", notes = "添加数据")
    public ApiResult<ResConfigCombo> addConfigComboData(
            @RequestBody ResConfigQo resConfigQo, HttpServletRequest request) {
        ApiResult<ResConfigCombo> result = new ApiResult<>();
        // 保存标识0：复制 1：增加
        String saveFlag = resConfigQo.getSaveFlag();
        // 套餐区分
        String comboOneId = resConfigQo.getComboOneId();

        CommOperatorDoLoginDto sys = super.getCurrentUser(request);
        List<ResConfigCombo> list = resConfigQo.getList();
        list.get(0).setCreator(sys.getOperatorname() + "(" + sys.getUserid() + ")");

        String ret =resConfigService.addConfigComboData(resConfigQo, saveFlag, comboOneId);
        if("1".equals(ret)){
            if ("1".equals(saveFlag)) {
                result.success("添加成功");
            } else if ("0".equals(saveFlag)) {
                result.success("复制成功");
            } else {
                result.success("发布成功");
            }
        } else if("01".equals(ret)){
            result.setStatus(ApiCode.FAIL);
            result.setMessage(RESMSG_1);

        }else if("02".equals(ret)){
            result.setStatus(ApiCode.FAIL);
            result.setMessage(RESMSG_2);

        }

        return result;
    }

    /**
     * 添加虚拟机数据
     *
     * @param resConfigQo 对象
     * @param request     对象
     * @return ApiResult
     */
    @PostMapping(value = "addVMPartsData")
    @ApiOperation(value = "添加数据", notes = "添加数据")
    public ApiResult<ResConfigVm> addVMPartsData(@RequestBody ResConfigQo resConfigQo, HttpServletRequest request) {
        ApiResult<ResConfigVm> result = new ApiResult<>();

        String saveFlag = resConfigQo.getSaveFlag();
        CommOperatorDoLoginDto sys = super.getCurrentUser(request);
        List<ResConfigVm> list = resConfigQo.getVmList();
        if ("2".equals(saveFlag)) {
            for (int i = 0; i < list.size(); i++) {
                String type = String.valueOf(list.get(i).getVmtype());
                // 获取该类型是否已发布
                int cnt = resConfigService.querVmPartsCount(type);
                if (cnt > 0) {
                    String typeName = "";
                    if ("1".equals(type)) {
                        typeName = "cpu";
                    } else if ("2".equals(type)) {
                        typeName = "内存";
                    } else if ("3".equals(type)) {
                        typeName = "硬盘";
                    }
                    result.setStatus(ApiCode.FAIL);
                    result.setMessage(typeName + RESMSG_3);
                    return result;
                }
            }
        }
        list.get(0).setCreator(sys.getOperatorname() + "(" + sys.getUserid() + ")");
        resConfigService.addVMPartsData(list, saveFlag);
        if ("1".equals(saveFlag)) {
            result.success("添加成功");
        } else if ("0".equals(saveFlag)) {
            result.success("复制成功");
        } else {
            result.success("发布成功");
        }
        return result;
    }

    /**
     * 添加软件数据
     *
     * @param resConfigQo 对象
     * @param request     对象
     * @return ApiResult
     */
    @PostMapping(value = "addSoftData")
    @ApiOperation(value = "添加数据", notes = "添加数据")
    public ApiResult<ResConfigSoft> addSoftData(@RequestBody ResConfigQo resConfigQo, HttpServletRequest request) {
        ApiResult<ResConfigSoft> result = new ApiResult<>();
        String saveFlag = resConfigQo.getSaveFlag();
        String comboOneId = resConfigQo.getComboOneId();
        CommOperatorDoLoginDto sys = super.getCurrentUser(request);
        List<ResConfigSoft> list = resConfigQo.getSoftList();
        list.get(0).setCreator(sys.getOperatorname() + "(" + sys.getUserid() + ")");
        resConfigService.addSoftData(list, saveFlag, comboOneId);
        if ("1".equals(saveFlag)) {
            result.success("添加成功");
        } else if ("0".equals(saveFlag)) {
            result.success("复制成功");
        } else {
            result.success("发布成功");
        }

        return result;
    }


    /**
     * 更新物理机套餐
     *
     * @param resConfigDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "upConfigComboData")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saveFlag", value = "保存状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "category", value = "套餐大类", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "subcategory", value = "套餐小项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyscene", value = "适用场景", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "describe", value = "资源配置", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "cpunum", value = "cpu数量（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "cpucore", value = "cpu核数（C）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "frequency", value = "主频（GHz）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "memory", value = "内存（G）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "diskcapacity",
                    value = "系统盘（G）数据库表空间（T）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "ssdnum", value = "SSD（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "ssd", value = "SSD（G）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sasnum", value = "SAS（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sas", value = "SAS（G）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "satanum", value = "SATA磁盘配置（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sata", value = "SATA磁盘数量（GB）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "netcardnum", value = "万兆网卡数量（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "fibercardnum", value = "光纤卡HBA数量（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "gpunum", value = "GPU数量（块）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "gputype", value = "GPU型号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "devopslevel", value = "系统运维等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isrelatefund", value = "是否涉及资金交易", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isspecial", value = "是否专用", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "价格（元）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<Object> upConfigComboData(ResConfigDto resConfigDto) {
        ApiResult<Object> result = new ApiResult<>();
        String saveFlag = resConfigDto.getSaveFlag();

        int id = resConfigService.upConfigComboData(resConfigDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
            if ("1".equals(saveFlag)) {
                result.setMessage("保存成功");
            } else if ("2".equals(saveFlag)) {
                result.setMessage("发布成功");
            }
        } else if(id == -2){
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage(RESMSG_1);
        } else if(id == -3){
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage(RESMSG_2);
        }else{
            result.setStatus(ApiCode.UPATE_FAIL);
            if ("1".equals(saveFlag)) {
                result.setMessage("保存失败");
            } else if ("2".equals(saveFlag)) {
                result.setMessage("发布失败");
            }
        }
        return result;
    }

    /**
     * 更新
     *
     * @param resConfigDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "upVMPartsData")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saveFlag", value = "保存状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "describe", value = "配置", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "价格（元）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query")

    })
    public ApiResult<Object> upVMPartsData(ResConfigDto resConfigDto) {
        ApiResult<Object> result = new ApiResult<>();
        String saveFlag = resConfigDto.getSaveFlag();
        if ("2".equals(saveFlag)) {
            // 获取该类型是否已发布
            int cnt = resConfigService.querVmPartsCount(resConfigDto.getVmtype());
            if (cnt > 0) {
                result.setStatus(ApiCode.UPATE_FAIL);
                String typeName = "";
                switch (resConfigDto.getVmtype()){
                    case "1" :
                        typeName = "cpu";
                        break;
                    case "2" :
                        typeName = "内存";
                        break;
                    case "3" :
                        typeName = "硬盘";
                        break;
                    default: typeName="";
                }

                result.setMessage(typeName + RESMSG_3);
                return result;
            }
        }
        int id = resConfigService.upVMPartsData(resConfigDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
            if ("1".equals(saveFlag)) {
                result.setMessage("保存成功");
            } else if ("2".equals(saveFlag)) {
                result.setMessage("发布成功");
            }
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            if ("1".equals(saveFlag)) {
                result.setMessage("保存失败");
            } else if ("2".equals(saveFlag)) {
                result.setMessage("发布失败");
            }
        }
        return result;
    }


    /**
     * 更新
     *
     * @param resConfigDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "upSoftData")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "subtype", value = "子项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "softname", value = "软件名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "softversion", value = "版本号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "billingmode", value = "计费模式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "价格（元）", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<Object> upSoftData(ResConfigDto resConfigDto) {
        ApiResult<Object> result = new ApiResult<>();
        String saveFlag = resConfigDto.getSaveFlag();
        int id = resConfigService.upSoftData(resConfigDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
            if ("1".equals(saveFlag)) {
                result.setMessage("保存成功");
            } else if ("2".equals(saveFlag)) {
                result.setMessage("发布成功");
            }
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            if ("1".equals(saveFlag)) {
                result.setMessage("保存失败");
            } else if ("2".equals(saveFlag)) {
                result.setMessage("发布失败");
            }

        }
        return result;
    }


    /**
     * 更新
     *
     * @param resConfigDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "upConfigStatus")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboOneId", value = "页面区分", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "saveFlag", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Integer", paramType = "query"),

    })
    public ApiResult<Object> upConfigStatus(ResConfigDto resConfigDto) {
        ApiResult<Object> result = new ApiResult<>();
        String saveFlag = resConfigDto.getSaveFlag();
        Map<String, Object> dataMap = resConfigService.upConfigStatus(resConfigDto);
        String msg= (String)dataMap.get("msg");
        Integer id= (Integer)dataMap.get("code");
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
            if ("2".equals(saveFlag)) {
                result.setMessage("发布成功");
            } else if ("3".equals(saveFlag)) {
                result.setMessage("作废成功");
            } else if ("4".equals(saveFlag)) {
                result.setMessage("删除成功");
            }
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            if(id == -1){
                if ("2".equals(saveFlag)) {
                    result.setMessage("发布失败");
                } else if ("3".equals(saveFlag)) {
                    result.setMessage("作废失败");
                } else if ("4".equals(saveFlag)) {
                    result.setMessage("删除失败");
                }
            } else {
                result.setMessage(msg);
            }
        }
        return result;
    }


    /**
     * 获取物理机配件
     *
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPhysicalParts")
    @ApiOperation(value = "查询", notes = "查询")
    public ApiResult<List> queryPhysicalParts() {
        ApiResult<List> result = new ApiResult<>();
        List<ResConfigCombo> configList = resConfigService.queryPhysicalParts();
        result.setResults(configList);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
