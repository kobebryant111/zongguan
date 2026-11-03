package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarTodoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarService;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarTodoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 考勤日历表 API接口
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmKqCalendarApi"})
@RestController
@RequestMapping("ppmKqCalendar")
@Slf4j
public class PpmKqCalendarApi extends BaseApi {
    /**
     * 考勤日历服务接口
     */
    @Autowired
    private IPpmKqCalendarService ppmKqCalendarService;
    /**
     * 考勤日历任务服务接口
     */
    @Autowired
    private IPpmKqCalendarTodoService ppmKqCalendarTodoService;

    /**
     * 任务审批
     *
     * @param request
     * @param ppmKqCalendarTodoDto
     * @return
     */
    @PostMapping(value = "approve")
    @ApiOperation(value = "任务审批", notes = "任务审批")
    public ApiResult<String> approve(HttpServletRequest request,
                                     @RequestBody PpmKqCalendarTodoDto ppmKqCalendarTodoDto) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmKqCalendarTodoDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = ppmKqCalendarTodoService.approve(ppmKqCalendarTodoDto, bps);
        result.success(msg);
        return result;
    }

    /**
     * 分页
     *
     * @param ppmKqCalendarDto 对象
     * @param pager            分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKqCalendarDto ppmKqCalendarDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmKqCalendarService.queryPager(ppmKqCalendarDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmKqCalendarDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmKqCalendarDto> queryDetail(PpmKqCalendarDto ppmKqCalendarDto) {
        ApiResult<PpmKqCalendarDto> result = new ApiResult<>();
        PpmKqCalendarDto item = ppmKqCalendarService.queryDetail(ppmKqCalendarDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询当前登陆人角色
     *
     * @param request
     * @return
     */
    @PostMapping("queryRole")
    @ApiOperation(value = "当前登陆人角色")
    public ApiResult<List<ImRoleDoLoginDto>> queryRole(HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ApiResult<List<ImRoleDoLoginDto>> result = new ApiResult();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(user.getRoles());
        return result;
    }

    /**
     * 4
     * 导入日历信息
     *
     * @param file    文件
     * @param request
     * @return ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "导入日历信息")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        log.info(user.getOperatorname() + "开始导入日历信息");
        if (ppmKqCalendarService.importExcel(file, user)) {
            result.success("导入成功");
            log.info(user.getOperatorname() + "导入日历信息成功");
        } else {
            result.failure("导入失败");
            log.info(user.getOperatorname() + "导入日历信息失败");
        }
        return result;
    }

    /**
     * 更新
     *
     * @param ppmKqCalendarDto 对象
     * @param request          对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmKqCalendar(@RequestBody PpmKqCalendarDto ppmKqCalendarDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmKqCalendarDto entity = ppmKqCalendarService.queryDetail(ppmKqCalendarDto);
        if (!entity.getIsHoliday().equals(ppmKqCalendarDto.getIsHoliday())) {
            ppmKqCalendarService.updatePpmKqCalendar(ppmKqCalendarDto);
            String dateStr = DateUtil.dateToStr(ppmKqCalendarDto.getCalendarDate(), "yyyy-MM-dd");
            String transferStr = entity.getIsHoliday().equals("Y") ? "从节假日修改为工作日" : "从工作日修改为节假日";
            //添加操作历史
            ppmKqCalendarService.addPpmKqCalendarHistory(user, "将" + dateStr + transferStr);
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("节假日类型未发生改变！");
        }
        return result;
    }

}
