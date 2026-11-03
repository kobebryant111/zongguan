package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.query.WfTaskExpandDataQuery;
import com.tansun.wf.platform.service.WfTaskExpandDataService;
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
 * @创建时间 2020/5/25
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfTaskExpandData")
public class WfTaskExpandDataController {

    @Autowired
    WfTaskExpandDataService wfTaskExpandDataService;

    @RequestMapping(value = "/list")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        WfTaskExpandDataQuery query = new WfTaskExpandDataQuery();
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processInstanceId"))){
            query.setProcessInstanceId(MapUtils.getString(reqMap, "processInstanceId"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processDefineId"))){
            query.setProcessDefineId(MapUtils.getString(reqMap, "processDefineId"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskId"))){
            query.setTaskId(MapUtils.getString(reqMap, "taskId"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldColumn")) &&
                StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldValue"))){
            query.setFieldColumn(MapUtils.getString(reqMap, "fieldColumn"));
            query.setFieldValue(MapUtils.getString(reqMap, "fieldValue"));
        }
        return WfResponseVO.successResponse(wfTaskExpandDataService.getPageList(query,page));
    }
}
