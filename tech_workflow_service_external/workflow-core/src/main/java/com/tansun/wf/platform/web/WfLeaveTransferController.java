package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.extend.vo.WfResponseVO;


import com.tansun.wf.platform.model.WfLeaveTransferEntity;
import com.tansun.wf.platform.query.WfLeaveTransferQuery;
import com.tansun.wf.platform.service.WfLeaveTransferService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/9
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfLeaveTransfer")
public class WfLeaveTransferController {


    @Autowired
    WfLeaveTransferService wfLeaveTransferService;

    @Autowired
    EditorServiceProxyService editorServiceProxyService;


    @RequestMapping("/list")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo) throws Exception {
        //请求参数不建议使用HttpServletRequest request
        Map<String, Object> result = new HashMap<String, Object>();
//        Session session = SessionUtil.getSession();
//        Map<String,Object> user = (Map<String, Object>) session.getAttribute(Constants.CURRENT_USER);
//        String loginName = user.get("loginName") == null?"":user.get("loginName").toString();
//        reqMap.put("userId", "superadmin");
        WfLeaveTransferQuery query = new WfLeaveTransferQuery();
        //先写死
        query.setUserId("superadmin");
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "classifyId"))){
            query.setClassifyId(reqMap.get("classifyId").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "transferId"))){
            query.setTransferId(reqMap.get("transferId").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "status"))){
            query.setStatus(reqMap.get("status").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "tenantId"))){
            query.setTenantId(reqMap.get("tenantId").toString());
        }
        return WfResponseVO.successResponse(wfLeaveTransferService.getPageList(query,pageInfo));
    }
    


    @RequestMapping("/save")
    public WfResponseVO save(@RequestBody List<WfLeaveTransferEntity> wfLeaveTransferEntityList) throws Exception {
//        Session session = SessionUtil.getSession();
//        Map<String,Object> user = (Map<String, Object>) session.getAttribute(Constants.CURRENT_USER);
//        String loginName = user.get("loginName") == null?"":user.get("loginName").toString();
        //先写死
        String loginName = "superadmin";
        String id = "";
        if(wfLeaveTransferEntityList.size() == 0){
            return WfResponseVO.errorResponse("请填写完整参数");
        }else{
            WfLeaveTransferEntity Entity = wfLeaveTransferEntityList.get(0);
            String transferId = Entity.getTransferId();
            String userId = Entity.getUserId();
            if (transferId.equals(userId)) {
                return WfResponseVO.errorResponse("转办人与交接人不能为同一用户");
            }
            id = Entity.getId();
            if(StringUtils.isNotBlank(id)){
                //修改保存这里只有一条记录
                wfLeaveTransferService.save(wfLeaveTransferEntityList);
                return WfResponseVO.successResponse("修改成功");
            }else{
                //新增保存
                wfLeaveTransferService.save(wfLeaveTransferEntityList);
                return WfResponseVO.successResponse("保存成功");
            }
        }
    }


    @RequestMapping(value = "/stop")
    @ResponseBody
    public WfResponseVO stopLeaveTransfer(@RequestParam Map<String,Object> reqMap){
        if(reqMap!=null){
            Map<String,Object> ret = wfLeaveTransferService.stopLeaveTransfers(reqMap);
            if(StringUtils.isNotBlank(MapUtils.getString(ret, "FAILED"))){
                return WfResponseVO.errorResponse(MapUtils.getString(ret, "FAILED"));
            }
            return WfResponseVO.errorResponse(MapUtils.getString(ret, "SUCCEED"));
        }else{
            return WfResponseVO.errorResponse("强制终止移交授权失败，移交配置信息为空");
        }
    }

    @RequestMapping("/startWfLeaveTransfer")
    public WfResponseVO startWfLeaveTransfer(@RequestParam Map<String,Object> reqMap) throws Exception {
        if(reqMap!=null){
            Map<String,Object> ret = wfLeaveTransferService.startLeaveTransfers(reqMap);
            if(!org.springframework.util.StringUtils.isEmpty(MapUtils.getString(ret, "FAILED"))){
                return WfResponseVO.errorResponse(MapUtils.getString(ret, "FAILED"));
            }
            return WfResponseVO.errorResponse(MapUtils.getString(ret, "SUCCEED"));
        }else{
            return WfResponseVO.errorResponse("启动授权失败，移交配置信息为空");
        }
    }
}
