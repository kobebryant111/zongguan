package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfFormDetailEntity;
import com.tansun.wf.platform.query.WfFormDetailQuery;
import com.tansun.wf.platform.service.WfFormDetailService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/7/7
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfFormDetail")
public class WfFormDetailController {

    @Autowired
    WfFormDetailService wfFormDetailService;

    @RequestMapping("/list")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo page)  {
        WfFormDetailQuery query = new WfFormDetailQuery();
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"name"))){
            query.setName(MapUtils.getString(reqMap,"name"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"formId"))){
            query.setFormId(MapUtils.getString(reqMap,"formId"));
        }
        Map<String, Object> result = new HashMap(1);
        result.put("grid", wfFormDetailService.getPageList(query,page));
        return WfResponseVO.successResponse(result);
    }

    @RequestMapping("/save")
    public WfResponseVO save(@RequestParam Map<String,Object> reqMap)  {
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "id"))) {
            reqMap.put("updateBy","superadmin");
            wfFormDetailService.update(reqMap);
            return WfResponseVO.successResponse("修改成功");
        } else {
            //保存
            reqMap.put("createBy","superadmin");
            reqMap.put("updateBy","superadmin");
            wfFormDetailService.save(reqMap);
            return WfResponseVO.successResponse("新增成功");
        }
    }

    @RequestMapping("/delete")
    public WfResponseVO delete(@RequestParam Map<String,Object> reqMap)  {
        if (StringUtils.isBlank(MapUtils.getString(reqMap, "id"))) {
          return WfResponseVO.errorResponse("删除失败,id不能为空");
        } else {
            if(wfFormDetailService.delete(MapUtils.getString(reqMap, "id")) == 1){
                return WfResponseVO.successResponse("删除成功");
            }
            return WfResponseVO.successResponse("删除失败");
        }
    }


}
