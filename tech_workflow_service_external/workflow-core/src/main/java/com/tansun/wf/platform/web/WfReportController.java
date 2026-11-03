package com.tansun.wf.platform.web;

import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.service.WfReportService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/12
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * 前端报表的接口
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/Report")
public class WfReportController {

    @Autowired
    WfReportService wfReportService;

    @RequestMapping("/processNumberReport")
    public WfResponseVO processNumberReport(@RequestParam Map<String,Object> reqMap){
        String tenantId = MapUtils.getString(reqMap,"tenantId");
        if(StringUtils.isBlank(tenantId)){
            return WfResponseVO.errorResponse("租户id为空");
        }
        Integer number = MapUtils.getInteger(reqMap,"number");
        if(number == null){
            number = 5;
        }
        return WfResponseVO.successResponse(wfReportService.queryProcessNumber(tenantId,number));
    }

    @RequestMapping("/processStateReport")
    public WfResponseVO processStateReport(@RequestParam Map<String,Object> reqMap){
        String tenantId = MapUtils.getString(reqMap,"tenantId");
        if(StringUtils.isBlank(tenantId)){
            return WfResponseVO.errorResponse("租户id为空");
        }
        return WfResponseVO.successResponse(wfReportService.queryProcessState(tenantId));
    }

    @RequestMapping("/userCompleteProcessReport")
    public WfResponseVO userProcessNumberReport(@RequestParam Map<String,Object> reqMap){
        String tenantId = MapUtils.getString(reqMap,"tenantId");
        String userId = MapUtils.getString(reqMap,"userId");
        String year = MapUtils.getString(reqMap,"year");
        if(StringUtils.isBlank(tenantId)){
            return WfResponseVO.errorResponse("租户id为空");
        }
        if(StringUtils.isBlank(userId)){
            return WfResponseVO.errorResponse("用户Id为空");
        }
        if(StringUtils.isBlank(year)){
            return WfResponseVO.errorResponse("年份为空");
        }
        return WfResponseVO.successResponse(wfReportService.queryUserProcessNumber(tenantId,userId,year));
    }

    @RequestMapping("/runProcessNumberReport")
    public WfResponseVO runProcessNumberReport(@RequestParam Map<String,Object> reqMap){
        String tenantId = MapUtils.getString(reqMap,"tenantId");
        if(StringUtils.isBlank(tenantId)){
            return WfResponseVO.errorResponse("租户id为空");
        }
        return WfResponseVO.successResponse(wfReportService.queryRunProcessNumber(tenantId));
    }
}
