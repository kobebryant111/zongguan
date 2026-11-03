package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfActHiTaskinstEntity;
import com.tansun.wf.platform.query.WfActHiTaskinstQuery;
import com.tansun.wf.platform.service.WfActHiTaskinstService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/1
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfProEff")
public class WfProEffController {

    @Autowired
    WfActHiTaskinstService wfActHiTaskinstService;

    /**
     * 按步骤统计排序 /按员工统计排序 员工统计额外加查询条件
     */
    @RequestMapping("/wfProEffCount")
    public WfResponseVO wfProEffCount(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        Map<String, Object> result = new HashMap<>();
        WfActHiTaskinstQuery query = new WfActHiTaskinstQuery();
        int pageNum = page.getPageNum() == 0? 1:page.getPageNum();
        int pageSize = page.getPageSize() == 0? 999:page.getPageSize();
        query.setPageNum(String.valueOf(pageNum));
        query.setPageSize(String.valueOf(pageSize));
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"name"))){
            query.setName(MapUtils.getString(reqMap,"name"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"procDefName"))){
            query.setProcDefName(MapUtils.getString(reqMap,"procDefName"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"orderBy"))){
            query.setOrderBy(MapUtils.getString(reqMap,"orderBy"));
        }
        PageInfo<WfActHiTaskinstEntity> actHiTaskinstPageInfo = wfActHiTaskinstService.findPageByWfSuccess(query);
        result.put("grid", actHiTaskinstPageInfo);
        return WfResponseVO.successResponse(result);
    }


    /**
     * 查询处理效率红/蓝榜（人员/机构/流程维度） 根据type来调用不同的查询方法
     */
    @RequestMapping("/wfProEffInfo")
    public WfResponseVO wfProEffPersonnelInfo(@RequestParam Map<String,Object> reqMap,PageInfo page) throws Exception {
        WfActHiTaskinstQuery query = new WfActHiTaskinstQuery();
        int pageNum = page.getPageNum() == 0? 1:page.getPageNum();
        int pageSize = page.getPageSize() == 0? 999:page.getPageSize();
        query.setPageNum(String.valueOf(pageNum));
        query.setPageSize(String.valueOf(pageSize));
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"beginDate"))){
            query.setBeginDate(MapUtils.getString(reqMap,"beginDate"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"orderBy"))){
            query.setOrderBy(MapUtils.getString(reqMap,"orderBy"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"orderType"))){
            query.setOrderType(MapUtils.getString(reqMap,"orderType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"endDate"))){
            query.setEndDate(MapUtils.getString(reqMap,"endDate"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"type"))){
            query.setType(MapUtils.getString(reqMap,"type"));
        }
        Map<String, Object> result = wfActHiTaskinstService.getWfProEfflInfo(query);
        return WfResponseVO.successResponse(result);
    }


}
