package com.tansun.wf.platform.web;

import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.query.WfClassifyQuery;
import com.tansun.wf.platform.service.WfClassifyService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/4/27
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "/wf/classify")
public class WfClassifyController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WfClassifyService wfClassifyService;


    @RequestMapping(value = "/save")
    @ResponseBody
    public WfResponseVO save(@RequestParam Map<String, Object> reqMap) {
        try {
            if(StringUtils.isBlank(MapUtils.getString(reqMap,"name"))){
                throw new RuntimeException("请填写分类名称");
            }
            if(StringUtils.isBlank(MapUtils.getString(reqMap,"code"))){
                throw new RuntimeException("请填写分类编码");
            }
            if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "id"))) {
                wfClassifyService.save(reqMap);
                return WfResponseVO.successResponse("修改成功");
            } else {
                //保存
                wfClassifyService.save(reqMap);
                return WfResponseVO.successResponse("新增成功");
            }
        }catch (Exception e){
            return WfResponseVO.errorResponse(e.getMessage());
        }

    }



    /**
     * 获取分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "query")
    public WfResponseVO query(@RequestParam Map<String, Object> reqMap) {
        WfClassifyQuery wfClassifyQuery = new WfClassifyQuery();
        if(reqMap.containsKey("parentId") && reqMap.get("parentId").toString() != null){
            wfClassifyQuery.setParentId(reqMap.get("parentId").toString());
        }
        try {
            return WfResponseVO.successResponse(wfClassifyService.findClassifyList(wfClassifyQuery));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 删除分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public WfResponseVO delete(@RequestParam Map<String, Object> reqMap) {
        try {
            String id = MapUtils.getString(reqMap, "id");
            wfClassifyService.deleteClassify(id);
            return WfResponseVO.successResponse("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return  WfResponseVO.errorResponse(e.toString());
        }
    }

}
