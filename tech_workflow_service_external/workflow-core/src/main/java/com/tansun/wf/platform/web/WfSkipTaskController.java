//package com.tansun.wf.platform.web;
//
//import com.github.pagehelper.PageInfo;
//import com.tansun.wf.extend.vo.WfResponseVO;
////import com.tansun.wf.mocktest.model.WfSkipTaskConfigEntity;
//import com.tansun.wf.mocktest.query.WfSkipTaskConfigQuery;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/wf/wfSkipTestTask")
//public class WfSkipTaskController {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    // camunda-TODO
////    @Autowired
////    public WfSkipTaskConfigService wfSkipTaskConfigService;
//
//    @ResponseBody
//    @RequestMapping(value = "/list")
//    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo){
//
//        try {
//            WfSkipTaskConfigQuery query = new WfSkipTaskConfigQuery();
//            query.isOpen(MapUtils.getString(reqMap, "isOpen"));
//            query.processDefKey(MapUtils.getString(reqMap, "processDefKey"));
//            query.isGlobal("N");
//            // camunda-TODO
////            PageInfo<WfSkipTaskConfigEntity> page = wfSkipTaskConfigService.findPage(query, pageInfo);
////            return WfResponseVO.successResponse(page);
//            return null;
//        } catch (Exception e) {
//            logger.error(e.getMessage(),e);
//            return WfResponseVO.successResponse(e.getMessage());
//        }
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/save")
//    public WfResponseVO saveWfTenant(WfSkipTaskConfigEntity entity){
//        entity.setCreateTime(new Date());
//        return wfSkipTaskConfigService.saveConfig(entity);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/deleteByIds")
//    public WfResponseVO deleteByIds( @RequestParam(value = "id")  String id) {
//        if (StringUtils.isEmpty(id)) {
//            return WfResponseVO.errorResponse("删除异常，入参ids为空");
//        }else {
//            List<String> ids = new ArrayList<>();
//            ids.add(id);
//            wfSkipTaskConfigService.deleteByIds(ids);
//            return WfResponseVO.successResponse("删除成功");
//        }
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/changeOpenStatus")
//    public WfResponseVO changeOpenStatus( @RequestParam(value = "id")  String id, String isOpen) {
//        if (StringUtils.isEmpty(id)) {
//            return WfResponseVO.errorResponse("删除异常，入参ids为空");
//        }else {
//            List<String> ids = new ArrayList<>();
//            ids.add(id);
//            wfSkipTaskConfigService.changeOpenStatus(ids,isOpen);
//            String msg = "Y".equals(isOpen) ? "配置已生效" : "配置已失效";
//            return WfResponseVO.successResponse(msg);
//        }
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/getGlobalEntity")
//    public WfResponseVO getGlobalEntity( ) {
//        WfSkipTaskConfigQuery query = new WfSkipTaskConfigQuery();
//        query.isGlobal("Y");
//        WfSkipTaskConfigEntity one = wfSkipTaskConfigService.findOne(query);
//        return WfResponseVO.successResponse(one,"查询成功");
//    }
//
//}
