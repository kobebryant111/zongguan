package com.work.ppm.application.api.resourceplat;

import com.work.ppm.application.dto.resourceplat.ResNoticeConfigDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.resourceplat.ResNoticeConfig;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResNoticeConfigService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
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
 * 资源申请通知设置
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResNoticeConfigApi"})
@RestController
@RequestMapping("resNoticeConfig")
@Slf4j
public class ResNoticeConfigApi extends BaseApi {

    /**
     * resNoticeConfigService
     */
    @Autowired
    private IResNoticeConfigService resNoticeConfigService;

    /**
     * eosDictEntryService
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

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
    public ApiResult<List> queryPager(ResNoticeConfigDto modelData, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        if ("1".equals(modelData.getSelectCurrentUser())) {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            modelData.setUserId(user.getUserid());
        }
        resNoticeConfigService.queryPager(modelData, pager);
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
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<List> queryList(ResNoticeConfigDto modelData, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        if ("1".equals(modelData.getSelectCurrentUser())) {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            modelData.setUserId(user.getUserid());
        }
        List<ResNoticeConfig> configList = resNoticeConfigService.queryList(modelData);
        result.setResults(configList);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(configList.size());
        return result;
    }

    /**
     * 删除
     *
     * @param modelData 对象
     * @return list 数据集合
     */
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", dataType = "string", paramType = "delete")
    })
    public ApiResult<List> delete(ResNoticeConfigDto modelData) {
        ApiResult<List> result = new ApiResult<>();
        try {
            resNoticeConfigService.delete(modelData);
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
     * @return ResNoticeConfigDto 数据
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "保存/更新", notes = "保存/更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "save"),
            @ApiImplicitParam(name = "userId", value = "通知人员ID", dataType = "string", paramType = "save"),
    })
    public ApiResult<ResNoticeConfigDto> save(ResNoticeConfigDto modelData, HttpServletRequest request) {
        ApiResult<ResNoticeConfigDto> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        Date date = new Date();
        if (StringUtils.isBlank(modelData.getId())) {
            modelData.setCreateUser(user.getUserid());
            modelData.setCreateName(user.getOperatorname());
            modelData.setCreateTime(DateUtil.dateToStr(date,"yyyy-MM-dd hh:mm:ss"));
        }
        modelData.setUpdateUser(user.getUserid());
        modelData.setUpdateName(user.getOperatorname());
        modelData.setUpdateTime(DateUtil.dateToStr(date,"yyyy-MM-dd hh:mm:ss"));
        try {
            resNoticeConfigService.save(modelData);
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
     * 登录用户身份：通知设置通过字典配置的独立身份，与角色等无关
     * 返回值说明：1-管理员，2-配置的通知用户
     *
     * @return ApiResult<String>
     */
    @PostMapping(value = "loginUserIdentity")
    @ApiImplicitParams({})
    public ApiResult<String> loginUserIdentity(HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (Objects.isNull(user) || StringUtils.isBlank(user.getUserid())) {
            result.setStatus(ApiCode.SELECT_DATA_FAIL);
            return result;
        }
        // 检查是否配置的管理员
        EosDictEntry entryList = eosDictEntryService.getTypeidForDetail(BusinessConstant.NOTICE_CONFIG_DICT_MGR, user.getUserid());
        if (Objects.nonNull(entryList)) {
            result.setResults("1");
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }
        // 检查是否配置通知用户
        ResNoticeConfigDto modelData = new ResNoticeConfigDto();
        modelData.setUserId(user.getUserid());
        List<ResNoticeConfig> configList = resNoticeConfigService.queryList(modelData);
        if (Objects.nonNull(configList) && 0 < configList.size()) {
            result.setResults("2");
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
