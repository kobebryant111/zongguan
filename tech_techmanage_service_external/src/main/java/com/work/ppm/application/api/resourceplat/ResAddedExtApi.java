package com.work.ppm.application.api.resourceplat;

import com.work.ppm.application.dto.resourceplat.IaasResourceDto;
import com.work.ppm.application.dto.resourceplat.ResAddedExtDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.resourceplat.AddedItemExtVo;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedExt;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.application.services.resourceplat.IResAddedExtService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 资源申请IAAS库
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResAddedExtApi"})
@RestController
@RequestMapping("resAddedExtApi")
@Slf4j
public class ResAddedExtApi extends BaseApi {

    /**
     * resAddedExtService IAAS库
     */
    @Autowired
    private IResAddedExtService resAddedExtService;

    /**
     * iaas分页
     * @param modelData 对象
     * @param pager     分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryIaasResourcePager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryIaasResourcePager(IaasResourceDto modelData, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        resAddedExtService.queryIaasResourcePager(modelData, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * iaas分页
     * @param modelData 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryIaasResourcePagerOhter")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryIaasResourcePagerOhter(IaasResourceDto modelData) {
        ApiResult<List> result = new ApiResult<>();
        List<AddedItemExtVo> list=resAddedExtService.queryIaasResourceOther(modelData);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据ID查询资源配置详情
     * @return result 数据List
     */
    @PostMapping(value = "queryResAddedConfig")
    @ApiOperation(value = "根据ID查询资源配置详情", notes = "根据ID查询资源配置详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源配置编号", dataType = "string", paramType = "query")
    })
    public ApiResult<List> queryResAddedConfig(String id) {
        ApiResult<List> result = new ApiResult<>();
        try {
            List<ResAdded> form = resAddedExtService.queryResAddedConfig(id);
            result.setResults(form);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 分页
     *
     * @param modelData 对象
     * @param pager       分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryPager(ResAddedExtDto modelData, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        resAddedExtService.queryPager(modelData, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 列表
     *
     * @param modelData 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryList")
    @ApiImplicitParams({})
    public ApiResult<List> queryList(ResAddedExtDto modelData, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        List<ResAddedExt> configList = resAddedExtService.queryList(modelData);
        result.setResults(configList);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(configList.size());
        return result;
    }

    /**
     * 删除
     *
     * @param modelData 对象
     * @return result 数据集合List
     */
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", dataType = "string", paramType = "delete")
    })
    public ApiResult<List> delete(ResAddedExtDto modelData) {
        ApiResult<List> result = new ApiResult<>();
        try {
            resAddedExtService.delete(modelData);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 保存/更新
     * @param modelData
     * @param request
     * @return result 集合数据
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "保存/更新", notes = "保存/更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "save"),
            @ApiImplicitParam(name = "userId", value = "通知人员ID", dataType = "string", paramType = "save"),
    })
    public ApiResult<ResAddedExtDto> save(ResAddedExtDto modelData, HttpServletRequest request) {
        ApiResult<ResAddedExtDto> result = new ApiResult<>();
        Date date = new Date();
        if (StringUtils.isBlank(modelData.getId())) {
            modelData.setCreateTime(DateUtil.dateToStr(date,"yyyy-MM-dd hh:mm:ss"));
        }
        modelData.setUpdateTime(DateUtil.dateToStr(date,"yyyy-MM-dd hh:mm:ss"));
        try {
            resAddedExtService.save(modelData);
            result.setMessage(ApiCode.SUCCESS.getMsg());
            result.setResults(modelData);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.UPATE_FAIL);
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 登录用户所属部门
     * 返回说明：系统部-0，基础设施部-1，网络部-2
     * @return ApiResult<String>
     */
    @PostMapping(value = "checkLoginUserDept")
    @ApiImplicitParams({})
    public ApiResult<String> checkLoginUserDept(HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (Objects.isNull(user) || StringUtils.isBlank(user.getUserid())) {
            result.setStatus(ApiCode.SELECT_DATA_FAIL);
            return result;
        }
        // 系统部DEPID：141
        if (Objects.nonNull(user.getDeptId()) && BusinessConstant.XTB_DEP_ID.equals(user.getDeptId())) {
            result.setResults("0");
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }
        // 基础设施部DEPID：7969
        if (Objects.nonNull(user.getDeptId()) && BusinessConstant.JCSCB_DEP_ID.equals(user.getDeptId())) {
            result.setResults("1");
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }
        // 网络部DEPID：142
        if (Objects.nonNull(user.getDeptId()) && BusinessConstant.WLB_DEP_ID.equals(user.getDeptId())) {
            result.setResults("2");
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 通过接口更新数据
     * @param resAddedExt 接口参数
     * @return ApiResult<String>
     */
    @PostMapping(value = "updateByInterface")
    @ApiImplicitParams({})
    public ApiResult<String> updateByInterface(ResAddedExt resAddedExt) {
        ApiResult<String> result = new ApiResult<>();
        try{
            resAddedExtService.updateByInterface(resAddedExt);
            result.setStatus(ApiCode.SUCCESS);
        }catch(Exception e){
            result.setStatus(ApiCode.FAIL);
            log.info("【updateByInterface】通过接口更新数据失败",e);
        }
        return result;
    }

    /**
     * 通过接口更新数据
     * @param id 关联系统的ID
     * @return ApiResult<String>
     */
    @PostMapping(value = "queryConfigListBySystem")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "query")
    })
    public ApiResult<List> queryConfigListBySystem(String id){
        ApiResult<List> result = new ApiResult<>();
        try{
            List<ResAddedItem> list = resAddedExtService.queryConfigListBySystem(id);
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(list);
        }catch (Exception e){
            result.setStatus(ApiCode.FAIL);
            log.info("【queryConfigListBySystem】通过接口更新数据失败",e);
        }
        return result;
    }

}
