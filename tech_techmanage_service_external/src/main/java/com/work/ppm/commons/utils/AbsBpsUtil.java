package com.work.ppm.commons.utils;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowSlainfoDto;
import com.work.ppm.application.dto.flowmanager.PpmSlaSetinfoDto;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquanLog;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.model.othermanager.PpmWeixin;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmFlowShouquanLogService;
import com.work.ppm.application.services.flowmanager.IPpmFlowSlainfoService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.flowmanager.IPpmSlaSetinfoService;
import com.work.ppm.application.services.othermanager.IPpmWeixinService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.workflow.client.soentity.BPSWorkItem;
import com.work.workflow.service.spi.ProcessInstManagerService;
import com.work.workflow.service.spi.RelativeDataManagerService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * BPSUtis 抽象類
 *
 */
@MercuryLog
public abstract class AbsBpsUtil {
    /**
     * bps内部对象
     */
    protected static Clr clr = new Clr();
    /**
     * bps-webserver
     */
    protected static ProcessInstManagerService processInstManagerService = new ProcessInstManagerService();
    /**
     * 流程日志
     */
    protected static IPpmFlowLogService ppmFlowLogService = (IPpmFlowLogService) SpringUtil.getBean("ppmFlowLogService");
    /**
     * 流程对象
     */
    protected static IPpmProjectFlowService ppmProjectFlowService = (IPpmProjectFlowService) SpringUtil.getBean("ppmProjectFlowService");
    /**
     * 员工登录
     */
    protected static ICommOperatorService commOperatorService = (ICommOperatorService) SpringUtil.getBean("commOperatorService");
    /**
     * 流程授权日志
     */
    protected static IPpmFlowShouquanLogService ppmFlowShouquanLogService = (IPpmFlowShouquanLogService) SpringUtil.getBean("ppmFlowShouquanLogService");
    /**
     * 工作平台接口
     */
    protected static IPpmWeixinService ppmWeixinService = (IPpmWeixinService) SpringUtil.getBean("ppmWeixinService");
    /**
     * sla 流程信息
     */
    protected static IPpmFlowSlainfoService ppmFlowSlainfoService = (IPpmFlowSlainfoService) SpringUtil.getBean("ppmFlowSlainfoService");
    /**
     * sla 流程配置接口
     */
    protected static IPpmSlaSetinfoService ppmSlaSetinfoService = (IPpmSlaSetinfoService) SpringUtil.getBean(
            "ppmSlaSetinfoService");
    /**
     * 数据接口
     */
    protected static RelativeDataManagerService relativeDataManagerService = new RelativeDataManagerService();
    /**
     * 根据用户id查询用户名称和用户id
     *
     * @param userId 工号
     * @return String
     */
    protected static String getUser(String userId) {
       return UsersUtil.getUser(userId);
    }

    /**
     *  业务字典
     */
    protected static IEosDictEntryService eosDictEntryService = (IEosDictEntryService)
            SpringUtil.getBean("eosDictEntryService");

    /**
     * 添加授权日志
     *
     * @param bps 对象
     */
    protected static void savePpmFlowShouquanLog(BpsWorkFlowDto bps) {
        PpmFlowShouquanLog flowLog = new PpmFlowShouquanLog();
        flowLog.setProcessinstid(bps.getProcessInstId());
        flowLog.setWorkitemid(bps.getWorkItemId());
        flowLog.setShouquanren(bps.getUserId());
        flowLog.setBeishouquanren(bps.getPersonId());
        flowLog.setProcessinstname(bps.getProcessInstName());
        flowLog.setProcessdefname(bps.getProcessDefName());
        flowLog.setCreatetime(new Date());
        flowLog.setCreators(bps.getUserId());
        ppmFlowShouquanLogService.addPpmFlowShouquanLog(flowLog);
    }

    /**
     * 添加流程数据
     *
     * @param projectno       流程编号
     * @param processInstName 流程实例名称
     * @param processInstDesc 流程实例描述
     * @param processInstId   流程相关数据
     * @param processDefName  流程定义
     * @param userId          用户名Id
     */
    private static void saveProjectFlow(String projectno, String processInstName,
                                        String processInstDesc, String processInstId,
                                        String processDefName, String userId) {
        // 获取 bean 接口
        saveProjectFlow(projectno, processInstName, processInstDesc, processInstId, processDefName, userId, "");
    }

    /**
     * 添加流程数据
     *
     * @param projectno       流程编号
     * @param processInstName 流程实例名称
     * @param processInstDesc 流程实例描述
     * @param processInstId   流程相关数据
     * @param processDefName  流程定义
     * @param userId          用户名Id
     * @param flowurl         结束流程的地址
     */
    protected static void saveProjectFlow(String projectno, String processInstName,
                                          String processInstDesc, String processInstId,
                                          String processDefName, String userId, String flowurl) {
        // 获取 bean 接口


        BpsUtil.Clr clrItem = getClrAndClrId(processInstId);

        Date date = new Date();
        PpmProjectFlow flow = new PpmProjectFlow();
        flow.setProjectno(projectno);
        flow.setFlowtitle(processInstName);
        flow.setFlowname(processInstDesc);
        flow.setClr(clrItem.getClr());
        flow.setClrid(clrItem.getClrId());
        flow.setInstanceid(processInstId);
        flow.setFlowid(processDefName);
        flow.setFlowurl(flowurl);
        flow.setFlowcreateuserid(userId);
        flow.setStartdate(date);
        flow.setFlowstatus("进行中");
        flow.setIsdel(0);
        flow.setLastmoduser(userId);
        flow.setCreatetime(date);
        String userName = commOperatorService.getUserNameByUserId(userId);
        flow.setCreators(userName + "(" + userId + ")");
        flow.setLastmodtime(date);
        flow.setActivityname(clrItem.getNodeName());
        flow.setActivityid(clrItem.getActivityId());
        int id = ppmProjectFlowService.addPpmProjectFlow(flow);
        if(id <= 0) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"创建流程日志失败");
        }
        log.warn("【新应用】创建projectFlow记录成功,{}", JsonUtil.toJSONString(flow));
    }

    /**
     * 更新流程对象表
     *
     * @param processInstId 流程id
     */
    protected static void updateProjectFlow(String processInstId) {
        try {
            PpmProjectFlow flow = new PpmProjectFlow();
            flow.setInstanceid(processInstId);
            flow.setFlowstatus("已结束");
            flow.setLastmodtime(new Date());
            flow.setClr("");
            flow.setClrid("");
            flow.setActivityname("");
            ppmProjectFlowService.updatePpmProjectFlow(flow);
        } catch (Exception e) {
            log.error("BpsUtil-更新流程对象异常", e);
        }
    }

    /**
     * 获取clr和clrid
     *
     * @param processInstId 流程id
     * @return Clr
     */
    protected static Clr getClrAndClrId(String processInstId) {
        // 获取流程信息
        String info = BpsUtil.queryProcessInfo(processInstId);
        String[] infos = info.split(",");
        // 节点名称
        String nodeName = "";
        // 名称+工号
        String nameAndBage = "";
        // 工号
        String bage = "";
        // 活动id
        String activityId = "";
        // 存在参与者
        if (infos.length >= 4) {
            nodeName = infos[1].split(":")[1];
            String participantsName = info.substring(info.indexOf("参与者:") + 4).trim();
            String[] participantsNames = participantsName.split(" ");
            activityId = infos[2].split(":")[1];
            int length = participantsNames.length;
            String roleid = "";
            for (int i = 0; i < length; i++) {
                String infoStr = participantsNames[i].split(",")[0];
                if(i+1 < participantsNames.length) {

                    if (infoStr.indexOf("PS") > -1
                            || infoStr.indexOf("XM") > -1
                            || infoStr.indexOf("DEP") > -1) {
                        String[] emples = participantsNames[i].split(",");
                        nameAndBage += emples[1] + "(" + emples[0] + ")" + ",";
                        bage += emples[0]+",";

                        roleid = participantsNames[i].split(",")[0];
                        nameAndBage = nameAndBage + commOperatorService.getNextRoleUser(roleid, 1) + ",";
                        bage = bage + commOperatorService.getNextRoleUser(roleid, 2) + ",";
                    } else {
                        String[] emples = participantsNames[i].split(",");
                        nameAndBage += emples[1] + "(" + emples[0] + ")" + ",";
                        bage += emples[0]+",";
                    }
                } else {
                    if (infoStr.indexOf("PS") > -1
                            || infoStr.indexOf("XM") > -1
                            || infoStr.indexOf("DEP") > -1) {
                        roleid = participantsNames[i].split(",")[0];
                        nameAndBage = nameAndBage + commOperatorService.getNextRoleUser(roleid, 1) ;
                        bage = bage + commOperatorService.getNextRoleUser(roleid, 2) ;
                    } else {
                        String[] emples = participantsNames[i].split(",");
                        nameAndBage += emples[1] + "(" + emples[0] + ")" ;
                        bage += emples[0];
                    }
                }

            }
        }
        clr.setClr(StringUtil.isNotBlank(nameAndBage) ? nameAndBage.substring(0, nameAndBage.length() - 1) : "");
        clr.setClrId(StringUtil.isNotBlank(bage) ? bage.substring(0, bage.length() - 1) : "");
        clr.setNodeName(nodeName);
        // 活动id
        clr.setActivityId(activityId);
        return clr;
    }

    /**
     * 判断map不为空
     *
     * @param map map
     * @return boolean
     */
    protected static boolean isMapNotEmpty(Map<String, String> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 处理 map中用户和角色
     *
     * @param map map
     */
    protected static void setMapUserAndRoles(Map<String, String> map) {
        if (StringUtil.isNotBlank(map.get("users"))) {
            map.put("users", getUser(map.get("users")));
        }
        if (StringUtil.isNotBlank(map.get("roles"))) {
            map.put("roles", getUser(map.get("roles")));
        }
    }

    /**
     * 添加工作平台日志
     *
     * @param list 集合
     * @return WeiXin
     */
    protected static WeiXin addWeiXin(List<BPSWorkItem> list) {
        BPSWorkItem item;
        String processtitle = "";
        String workItems = "";
        String partiName = "";
        List<String> participants = Arrays.asList("PS", "XM", "DEP");
        for (int i = list.size() - 1; i >= 0; i--) {
            item = list.get(i);
            processtitle = item.getProcessInstName();
            if (participants.contains(item.getParticipant())) {
                String[] idAndNames = partiName.split("@");
                String name1 = "";
                String id1 = "";
                // String partiNameTemp=partiName;
                if (idAndNames.length == 2) {
                    id1 = idAndNames[0];
                    name1 = idAndNames[1];
                }

                String partiNameTemp = commOperatorService.getRoleUserFormat(item.getParticipant()).replaceAll("\\|", ",");

                String name2 = "";
                String id2 = "";
                String[] partiNameTemps = partiNameTemp.split("@");
                if (partiNameTemps.length == 2) {
                    id2 = partiNameTemps[0];
                    name2 = partiNameTemps[1];
                }

                String id3 = "";
                String name3 = "";
                if (idAndNames.length == 2) {
                    id3 = id1;
                    name3 = name1;
                    if (partiNameTemps.length == 2) {
                        id3 = id3 + "|" + id2;
                        name3 = name3 + "|" + name2;
                    }
                    partiNameTemp = id3 + "@" + name3;
                } else {
                    if ("".equals(partiName)) {
                        partiName = partiNameTemp;
                    }
                    partiNameTemp = partiName;
                }

                partiName = partiNameTemp;
            } else {
                partiName = partiName + item.getParticipant() + "@"
                        + item.getPartiName() + ",";
            }
            workItems = workItems + item.getWorkItemID() + ",";
            PpmWeixin wx = new PpmWeixin();
            wx.setInstanceid(item.getProcessInstID() + "");
            wx.setWorkitemid(item.getWorkItemID() + "");
            wx.setSafecode(item.getActivityDefID());
            wx.setFlowname(item.getProcessDefName());
            wx.setFlowtext(item.getProcessInstName());
            wx.setUsers(item.getParticipant());
            wx.setActivityinstid(item.getActivityInstID() + "");
            wx.setActivityinstname(item.getActivityInstName());
            ppmWeixinService.addPpmWeixin(wx);

        }
        WeiXin weiXin = new WeiXin();
        weiXin.setPartiName(partiName);
        weiXin.setWorkItems(workItems);
        weiXin.setProcessTitle(processtitle);
        return weiXin;
    }

    /**
     * 检查流程预计完成时间
     *
     * @param bpsInfo     节点信息
     * @param flowdraftNo 流程定义id
     * @param instanceId  流程id
     */
    @Transactional("defaultTx")
    public static void checkedSlaMessage(String bpsInfo, String flowdraftNo, String instanceId) {
        String[] str = bpsInfo.split(",");
        String activityid = str[2].split(":")[1];

        PpmFlowSlainfoDto fsdto = ppmFlowSlainfoService.queryDetail(instanceId, activityid);
        if (null != fsdto) {
            ppmFlowSlainfoService.updatePpmFlowSlainfo(bpsInfo, fsdto);
        } else {
            ppmFlowSlainfoService.updatePpmFlowSlainfo(instanceId);
            PpmSlaSetinfoDto slaSetinfoDto = ppmSlaSetinfoService.queryDetail(flowdraftNo, activityid);
            if (null == slaSetinfoDto) {
                return;
            }
            if ("1".equals(slaSetinfoDto.getIssetplandate())) {
                Date plandate;
                Date newDate = new Date();
                if ("01".equals(slaSetinfoDto.getDaytype())) {//工作日
                    plandate = DateUtil.getNextTime(newDate, Integer.parseInt(slaSetinfoDto.getDays())); //到达该节点+n天
                } else {
                    plandate = DateUtil.addOrSubDate(newDate, Integer.parseInt(slaSetinfoDto.getDays())); //到达该节点+n天
                }
                // 添加记录
                PpmFlowSlainfoDto ppmFlowSlainfoDto = PpmFlowSlainfoDto.builder()
                        .planfinishtime(plandate)
                        .instanceid(instanceId)
                        .activityid(activityid)
                        .flowcaseno(flowdraftNo)
                        .build();
                ppmFlowSlainfoService.addPpmFlowSlainfo(bpsInfo, ppmFlowSlainfoDto);
            }
        }
    }


    /**
     * 内部实体类
     *
     */
    @Setter
    @Getter
    public static class WeiXin {
        /**
         * 名称
         */
        private String partiName;
        /**
         * workitem
         */
        private String workItems;
        /**
         * 流程标题
         */
        private String processTitle;
    }

    /**
     * 内部实体类
     *
     */
    @Setter
    @Getter
    public static class Clr {
        /**
         * clr
         */
        private String clr;
        /**
         * id
         */
        private String clrId;
        /**
         * 节点名称
         */
        private String nodeName;
        /**
         * 活动id 例如：manualActivity2
         */
        private String activityId;

        /**
         * 构造函数
         */
        private Clr() {
        }
    }
}
