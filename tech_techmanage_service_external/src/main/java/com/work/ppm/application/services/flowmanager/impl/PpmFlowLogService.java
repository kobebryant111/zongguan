package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowSlainfoDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.model.flowmanager.ext.PpmFlowLogInfo;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmFlowSlainfoService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.commons.constant.WorkflowSendMsg;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.workflow.client.soentity.BPSWorkItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 流程日志
 *
 */
@Slf4j
@Service("ppmFlowLogService")
public class PpmFlowLogService extends BaseDaoSupper implements IPpmFlowLogService {
    /**
     * 类路径
     */
    private static String PPMFLOWLOG = "com.work.ppm.application.model.flowmanager.PpmFlowLog";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 操作
     */
    @Autowired
    private ICommOperatorService commOperatorService;
    /**
     * 流程对象接口
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;
    /**
     * sla 流程信息接口
     */
    @Autowired
    private IPpmFlowSlainfoService ppmFlowSlainfoService;

    @Override
    public void queryPager(PpmFlowLog ppmFlowLog, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(ppmFlowLog, PpmFlowLog.class);
        myBatisDao.selectPager(PPMFLOWLOG, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmFlowLog(PpmFlowLog ppmFlowLog) {
        Long id = getNextSequence(PpmFlowLog.class);
        ppmFlowLog.setId(id);
        myBatisDao.executionPojo(PPMFLOWLOG, ppmFlowLog, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmFlowLog queryDetail(PpmFlowLog ppmFlowLog) {

        PpmFlowLog item = myBatisDao.selectClass(PPMFLOWLOG, ppmFlowLog, "queryDetail");
        if (item.getConclusion() == null) {
            item.setConclusion("");
        }
        if (item.getFlowdraftno() == null) {
            item.setConclusion("");
        }

        return item;
    }

    @Override
    public PpmFlowLog selectOneByInstanceId(String instanceId) {
        Map map = new HashMap();
        map.put("flowcaseno", instanceId);
        PpmFlowLog item = myBatisDao.selectClass(PPMFLOWLOG, map, "selectOneByInstanceId");
        if (item.getConclusion() == null) {
            item.setConclusion("");
        }
        if (item.getFlowdraftno() == null) {
            item.setConclusion("");
        }

        return item;
    }

    @Override
    public Integer updatePpmFlowLog(PpmFlowLog ppmFlowLog) {
        String deptNo = commEmployeeService.getDeptNo(ppmFlowLog.getUserid());
        ppmFlowLog.setDeptno(deptNo);
        // 添加
        if (!StringUtil.isNotEmpty(ppmFlowLog.getId())) {
            Date date = new Date();
            ppmFlowLog.setCreatetime(date);
            ppmFlowLog.setExamine(date);
            ppmFlowLog.setLastmodtime(date);
            this.addPpmFlowLog(ppmFlowLog);
            return 1;
        }
        // 更新
        myBatisDao.executionPojo(PPMFLOWLOG, ppmFlowLog, IMyBatisDao.SQL.UPDATE, "updatePojo");
        return 2;

    }

    @Override
    public Integer deletePpmFlowLog(PpmFlowLog ppmFlowLog) {

        myBatisDao.executionPojo(PPMFLOWLOG, ppmFlowLog, IMyBatisDao.SQL.DELETE, "deleteById");
        return 1;
    }

    @Override
    public int batchAddFlowLog(List<PpmFlowLog> list) {
        for (int i = 0; i < list.size(); i++) {
            Long id = getNextSequence(PpmFlowLog.class);
            list.get(i).setId(id);
            log.info("==================================>>>>>>>> 批量插入日志：{}", id);
        }
        myBatisDao.executionPojo(PPMFLOWLOG, list, IMyBatisDao.SQL.INSTER, "batchAddPojo");
        return 1;
    }

    @Override
    public void saveFlowLog(BpsWorkFlowDto bps) {
        addFlowLog(bps);
        log.warn("=================>流程类型:{}，流程ID：{},用户: 保存日志-1结束<=====================",
                bps.getProcessDefName(), bps.getProcessInstId(), bps.getUserId());
        PpmFlowLog temp = new PpmFlowLog();
        temp.setFlowcaseno(bps.getProcessInstId());
        temp.setIsdel(0);
        temp.setIsfalse("01");
        if (null != bps.getProcessInstId() && bps.getProcessInstId().length() > 0) {
            this.deletePpmFlowLog(temp);
        }
        String infoStr = BpsUtil.queryProcessInfo(bps);
        String[] str = infoStr.split(",");
        if (str.length < 4) {
            return;
        }

        addFlowLog(str, infoStr, bps);
        log.warn("==========================>>流程定义id：{}，流程id：{}，工号：{}, SLA保存开始<=============", bps.getProcessDefName(),
                bps.getProcessInstId(), bps.getUserId());
        settingNotic(bps, infoStr);
        log.warn("==========================>>流程定义id：{}，流程id：{}，工号：{}, SLA保存结束<=============", bps.getProcessDefName(),
                bps.getProcessInstId(), bps.getUserId());
    }

    /**
     * 设置通知
     *
     * @param bps     对象
     * @param infoStr 流程节点信息
     */
    @Transactional("defaultTx")
    private void settingNotic(BpsWorkFlowDto bps, String infoStr) {
        if (!StringUtil.isNotBlank(bps.getProcessDefName())) {
            PpmProjectFlow flow = ppmProjectFlowService.queryDetail(bps.getProcessInstId());
            if (null != flow) {
                BpsUtil.checkedSlaMessage(infoStr, flow.getFlowid(), bps.getProcessInstId());
            }
        } else {
            BpsUtil.checkedSlaMessage(infoStr, bps.getProcessDefName(), bps.getProcessInstId());
        }
        // 判断下一个节点是否设置SLA
        PpmFlowSlainfoDto flowSlainfoDto = ppmFlowSlainfoService.queryDetail(bps.getProcessInstId());
        if (null != flowSlainfoDto) {
            PpmFlowLog q = new PpmFlowLog();
            q.setFlowcaseno(bps.getProcessInstId());
            q.setIsfalse("01");
            List<PpmFlowLog> logs = queryDetaiList(q);
            for (PpmFlowLog l : logs) {
                String msg = "预计完成时间为"
                        + DateUtil.dateToStr(flowSlainfoDto.getPlanfinishtime(), "yyyy-MM-dd HH:mm:ss")
                        + "，请及时处理流程";
                String old = l.getSuggest();
                if (StringUtil.isNotBlank(old)) {
                    msg = old + "|" + msg;
                }
                PpmFlowLog lg = new PpmFlowLog();
                lg.setFlowcaseno(bps.getProcessInstId());
                lg.setSuggest(msg);
                lg.setIsfalse("01");
                lg.setId(l.getId());
                myBatisDao.executionPojo(PPMFLOWLOG, lg, IMyBatisDao.SQL.UPDATE, "updatePojo");
            }
        }
    }

    /**
     * 添加日志
     *
     * @param str     bps 查询节点信息
     * @param infoStr 参与者信息
     * @param bps     bps 对象
     * @return string
     */
    @Transactional("defaultTx")
    private String addFlowLog(String[] str, String infoStr, BpsWorkFlowDto bps) {
        String nodeName = str[1].split(":")[1];
        String infoStr1 = infoStr.substring(infoStr.indexOf("参与者:") + 4).trim();
        String[] infoStr1Array = infoStr1.split(" ");
        List<PpmFlowLog> logList = new ArrayList<>();
        boolean isps = false;
        PpmFlowLog ppmFlowLog = null;
        PpmFlowLog ppmFlowLog1 = null;
        for (int i = 0; i < infoStr1Array.length; i++) {
            ppmFlowLog = new PpmFlowLog();
            ppmFlowLog.setFlowcaseno(bps.getProcessInstId());
            String part = infoStr1Array[i].split(",")[0];
            if (part.indexOf("PS") > -1 || part.indexOf("XM") > -1 || part.indexOf("DEP") > -1) {
                String userStr = commOperatorService.getNextRoleUser(infoStr1Array[i].split(",")[0]);
                String[] userArray = userStr.split(",");
                for (int j = 0; j < userArray.length; j++) {
                    ppmFlowLog1 = new PpmFlowLog();
                    ppmFlowLog1.setFlowcaseno(bps.getProcessInstId());
                    ppmFlowLog1.setUserid(userArray[j]);
                    ppmFlowLog1.setApplydesc(nodeName);
                    ppmFlowLog1.setIsfalse("01");
                    ppmFlowLog1.setIsdel(0);
                    String depno = commEmployeeService.getDeptNo(userArray[j]);
                    ppmFlowLog1.setDeptno(depno);
                    ppmFlowLog1.setRoleid(part);
                    this.addPpmFlowLog(ppmFlowLog1);
                }
                isps = true;
                continue;
            }
            // 获取工作项
            bps.setProcessInstId(bps.getProcessInstId());
            List<BPSWorkItem> list = BpsUtil.getWorkItem(bps);
            String sugges = "";
            if (null != list && !list.isEmpty()) {
                String activityDefID = list.get(0).getActivityDefID();
                // 工作流发送信息
                if (WorkflowSendMsg.getProcessorWechatApprovalConfig().contains(
                        bps.getProcessDefName() + "|" + activityDefID)
                        || WorkflowSendMsg.getProcessorMobileOAApprovalConfig().contains(
                        bps.getProcessDefName() + "|" + activityDefID)) {
                    sugges = "[该节点支持移动端审批]";
                }
            }
            String userid = infoStr1Array[i].split(",")[0];
            ppmFlowLog.setUserid(userid);
            ppmFlowLog.setApplydesc(nodeName);
            ppmFlowLog.setSuggest(sugges);
            ppmFlowLog.setIsfalse("01");
            ppmFlowLog.setIsdel(0);
            if ("workuser".equals(userid)) {
                ppmFlowLog.setDeptno("");
            } else {
                String depno = commEmployeeService.getDeptNo(userid);
                ppmFlowLog.setDeptno(depno);
            }
            logList.add(ppmFlowLog);
        }
        if (!isps) {
            this.batchAddFlowLog(logList);
        }
        return infoStr1;
    }

    /**
     * 添加流程日志
     *
     * @param bps 流程对象
     * @return PpmFlowLog
     */
    @Transactional("defaultTx")
    public PpmFlowLog addFlowLog(BpsWorkFlowDto bps) {
        PpmFlowLog flowLog = new PpmFlowLog();
        Date date = new Date();
        flowLog.setCreatetime(date);
        flowLog.setUserid(bps.getUserId());
        if (!"workuser".equals(bps.getUserId())) {
            String deptId = commEmployeeService.getDeptNo(bps.getUserId());
            flowLog.setDeptno(deptId);
        }
        flowLog.setCreators(bps.getUserId());
        // 审核结论(01：通过、02：退回)
        flowLog.setConclusion(bps.getConclusion());
        //审核意见
        flowLog.setSuggest(bps.getSuggest());
        //流程实例编号
        flowLog.setFlowcaseno(bps.getProcessInstId());
        flowLog.setExamine(date);
        // 审批节点描述
        flowLog.setApplydesc(StringUtil.isNotBlank(bps.getApplydesc()) ? bps.getApplydesc() : bps.getActivityInstName());
        // 流程定制编号
        flowLog.setFlowdraftno(bps.getProcessDefName());
        flowLog.setIsdel(0);
        this.addPpmFlowLog(flowLog);
        return flowLog;
    }

    @Override
    public List<PpmFlowLogDto> queryList(PpmFlowLog ppmFlowLog) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmFlowLog, PpmFlowLog.class);
        List<PpmFlowLogInfo> list = myBatisDao.selectListT(PPMFLOWLOG, map, "getLogs");
        List<PpmFlowLogDto> dtoList = JsonUtil.jsonToList(list, PpmFlowLogDto.class);
        return dtoList;
    }

    /**
     * 查询list
     * @param ppmFlowLog 对象
     * @return list
     */
    private List<PpmFlowLog> queryDetaiList(PpmFlowLog ppmFlowLog) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmFlowLog, PpmFlowLog.class);
        List<PpmFlowLog> list = myBatisDao.selectListT(PPMFLOWLOG, map, "getList");
        return list;
    }

    @Override
    public List<PpmFlowLogDto> queryDetaiList(String instid) {
        Map<String, Object> map = new HashMap<>();
        map.put("flowcaseno", instid);
        List<PpmFlowLog> list = myBatisDao.selectListT(PPMFLOWLOG, map, "getList");
        List<PpmFlowLogDto> dtos = JsonUtil.jsonToList(list, PpmFlowLogDto.class);
        return dtos;
    }

    @Override
    public void queryContainHisPager(PpmFlowLogDto ppmFlowLogDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmFlowLogDto, PpmFlowLogDto.class);
        myBatisDao.selectPager(PPMFLOWLOG, map, pager, "queryContainHisPager");
    }

    @Override
    public int deletePpmFlowLog(String[] idss) {
        PpmFlowLog flowLog = new PpmFlowLog();
        for (int i = idss.length - 1; i >= 0; i--) {
            flowLog.setId(Long.parseLong(idss[i]));
            this.deletePpmFlowLog(flowLog);
        }
        return 1;
    }

    @Override
    public PpmFlowLogDto getPwdFlow(String instanceid) {
        PpmFlowLog log= new PpmFlowLog();
        log.setFlowcaseno(instanceid);
        PpmFlowLog item = myBatisDao.selectClass(PPMFLOWLOG, log, "getPwdFlow");
        PpmFlowLogDto dto = JsonUtil.jsonToClass(item,PpmFlowLogDto.class);
        return dto;
    }

    @Override
    public List<PpmFlowLogDto> queryOnlineSplogs(PpmFlowLog ppmFlowLog) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmFlowLog, PpmFlowLog.class);
        List<PpmFlowLogInfo> list = myBatisDao.selectListT(PPMFLOWLOG, map, "queryOnlineSplogs");
        List<PpmFlowLogDto> dtoList = JsonUtil.jsonToList(list, PpmFlowLogDto.class);
        return dtoList;
    }

    @Override
    public int deleteByNullLog(PpmFlowLog ppmFlowLog) {
        myBatisDao.executionPojo(PPMFLOWLOG, ppmFlowLog, IMyBatisDao.SQL.DELETE, "deleteByNullLog");
        return 1;
    }

}
