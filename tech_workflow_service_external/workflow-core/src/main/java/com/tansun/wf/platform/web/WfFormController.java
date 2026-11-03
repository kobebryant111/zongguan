package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.query.WfFormQuery;
import com.tansun.wf.platform.service.WfFromService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/11
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfForm")
public class WfFormController {


    @Autowired
    WfFromService wfFromService;
    @RequestMapping("/query")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo) throws Exception {
        WfFormQuery query = new WfFormQuery();
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "formCode"))){
            query.setFormCode(reqMap.get("formCode").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "formName"))){
            query.setFormName(reqMap.get("formName").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "tenantId"))){
            query.setTenantId(reqMap.get("tenantId").toString());
        }
        return  WfResponseVO.successResponse(wfFromService.getPageList(query,pageInfo));
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public WfResponseVO delete(@RequestParam Map<String,Object> reqMap) throws Exception {
        if (!StringUtils.isBlank(MapUtils.getString(reqMap, "id"))) {
            String id = reqMap.get("id").toString();
            if (wfFromService.delete(id) == 1) {
                return  WfResponseVO.successResponse("删除成功");
            }
            return  WfResponseVO.errorResponse("删除失败");
        } else {
           return WfResponseVO.errorResponse("删除失败,id不能为空");
        }
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public WfResponseVO save(@RequestParam Map<String,Object> reqMap) throws Exception {
        if(StringUtils.isBlank(MapUtils.getString(reqMap, "formCode"))) {
            return WfResponseVO.errorResponse("保存失败,表单编号、表单名称不能为空");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap, "formName"))) {
            return WfResponseVO.errorResponse("保存失败,表单编号、表单名称不能为空");
        }
        if (!StringUtils.isBlank(MapUtils.getString(reqMap, "id"))) {
            wfFromService.save(reqMap);
            return WfResponseVO.successResponse("修改成功");
        } else {
            wfFromService.save(reqMap);
            return WfResponseVO.successResponse("保存成功");
        }

    }


}


