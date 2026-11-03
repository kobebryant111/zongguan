package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.query.WfEmailTemplateQuery;
import com.tansun.wf.platform.service.WfEmailTemplateService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/wf/emailTemplate")
public class WfEmailTemplateController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WfEmailTemplateService wfEmailTemplateService;

    /**
     * 插入/修改
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public WfResponseVO save(@RequestBody Map<String, Object> reqMap) {
        try {
            wfEmailTemplateService.save(reqMap);
            return WfResponseVO.successResponse(StringUtils.isNotBlank(MapUtils.getString(reqMap, "id"))?"修改成功":"新增成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 获取
     */
    @ResponseBody
    @RequestMapping(value = "query")
    public WfResponseVO query(@RequestBody Map<String, Object> reqMap) {
        WfEmailTemplateQuery wfEmailTemplateQuery = new WfEmailTemplateQuery();
        if(reqMap.get("tempName") != null) {
            wfEmailTemplateQuery.setTempName("%" + reqMap.get("tempName") + "%");
        }
        if(reqMap.get("tempTag") != null) {
            wfEmailTemplateQuery.setTempTag("%" + reqMap.get("tempTag") + "%");
        }
        wfEmailTemplateQuery.setTenantId((String) reqMap.get("tenantId"));
        wfEmailTemplateQuery.setTempStatus((String) reqMap.get("tempStatus"));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(reqMap.get("pageNum")==null?1: (int) reqMap.get("pageNum"));
        pageInfo.setPageSize(reqMap.get("pageSize")==null?10: (int) reqMap.get("pageSize"));
        try {
            return WfResponseVO.successResponse(wfEmailTemplateService.findPage(wfEmailTemplateQuery, pageInfo));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public WfResponseVO delete(@RequestBody Map<String, Object> reqMap) {
        try {
            String id = MapUtils.getString(reqMap, "id");
            if (StringUtils.isBlank(id)) {
                throw new RuntimeException("请选择模板");
            }
            wfEmailTemplateService.delete(id);
            return WfResponseVO.successResponse("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return  WfResponseVO.errorResponse(e.toString());
        }
    }

    /**
     * 启用/禁用
     */
    @ResponseBody
    @RequestMapping(value = "play")
    public WfResponseVO play(@RequestBody Map<String, Object> reqMap) {
        try {
            String id = MapUtils.getString(reqMap, "id");
            String status = MapUtils.getString(reqMap, "tempStatus");
            if (StringUtils.isBlank(id)) {
                throw new RuntimeException("请选择模板");
            }
            if (StringUtils.isBlank(status)) {
                throw new RuntimeException("请选择启用状态");
            }
            wfEmailTemplateService.play(id, status);
            return WfResponseVO.successResponse((status.equals("0")?"启用":"禁用") + "成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return  WfResponseVO.errorResponse(e.toString());
        }
    }

}
