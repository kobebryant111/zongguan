package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfDefinedButtonEntity;
import com.tansun.wf.platform.query.WfDefinedButtonQuery;
import com.tansun.wf.platform.service.WfDefinedButtonService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/7
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping(value = "/wf/wfButton")
public class WfDefinedButtonController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WfDefinedButtonService wfDefinedButtonService;
    @RequestMapping(value = {"/query"})
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo) throws Exception {
        WfDefinedButtonQuery query = new WfDefinedButtonQuery();
        try {
            if(!StringUtils.isBlank(MapUtils.getString(reqMap, "key"))){
                query.setKey(reqMap.get("key").toString());
            }
            if(!StringUtils.isBlank(MapUtils.getString(reqMap, "name"))){
                query.setName(reqMap.get("name").toString());
            }
            query.setTenantId(MapUtils.getString(reqMap, "tenantId"));
            PageInfo<WfDefinedButtonEntity> page = wfDefinedButtonService.getPageList(query,pageInfo);
            return WfResponseVO.successResponse(page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    WfResponseVO get(@RequestParam Map<String,Object> reqMap) throws Exception {
        String id = MapUtils.getString(reqMap, "id");
        if (!StringUtils.isBlank(id)) {
            return WfResponseVO.successResponse(wfDefinedButtonService.getById(id));
        }else{
            return WfResponseVO.errorResponse(null);
        }
    }



    @RequestMapping(value = "/delete")
    @ResponseBody
    WfResponseVO delete(@RequestParam Map<String,Object> reqMap) throws Exception {
        String id = MapUtils.getString(reqMap, "id");
        if (!StringUtils.isEmpty(id)) {
            wfDefinedButtonService.delete(id);
            return WfResponseVO.successResponse("删除成功");
        }else{
            return WfResponseVO.errorResponse("id 参数为空!");
        }
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public WfResponseVO save(@RequestParam Map<String,Object> reqMap) throws Exception {
        if (!StringUtils.isBlank(MapUtils.getString(reqMap, "id"))) {
            wfDefinedButtonService.save(reqMap);
            return WfResponseVO.successResponse("修改成功");
        } else {
            wfDefinedButtonService.save(reqMap);
            return WfResponseVO.successResponse("保存成功");
        }
    }



}
