package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmApplyvirtualInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.model.integratedmanager.PpmApplyvirtualInfo;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.integratedmanager.IPpmApplyvirtualInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingAccessapplyService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.notice.MessageNotice;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 虚拟机桌面变更
 */
@Service("ppmApplyvirtualInfoService")
public class PpmApplyvirtualInfoService extends BaseDaoSupper implements IPpmApplyvirtualInfoService {
    /**
     * 实体类路径
     */
    private static String PPMAPPLYVIRTUALINFO = "com.work.ppm.application.model.integratedmanager.PpmApplyvirtualInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 外包准入接口
     */
    @Autowired
    private IPpmOutsourcingAccessapplyService ppmOutsourcingAccessapplyService;
    /**
     * 用户接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 流程日志接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;

    @Override
    public void queryPager(PpmApplyvirtualInfo ppmApplyvirtualInfo, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmApplyvirtualInfo,PpmApplyvirtualInfo.class);
        myBatisDao.selectPager(PPMAPPLYVIRTUALINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmApplyvirtualInfo(PpmApplyvirtualInfoDto ppmApplyvirtualInfoDto) {
        PpmApplyvirtualInfo ppmApplyvirtualInfo = JsonUtil.jsonToClass(ppmApplyvirtualInfoDto,
                PpmApplyvirtualInfo.class);
        // 如果需要请set 主键id
        String id= CodeGenerater.generate("XZ", 14, "yyyyMMdd");
        ppmApplyvirtualInfo.setId(id);
        myBatisDao.executionPojo(PPMAPPLYVIRTUALINFO, ppmApplyvirtualInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmApplyvirtualInfo queryDetail(PpmApplyvirtualInfo ppmApplyvirtualInfo) {
        return myBatisDao.selectClass(PPMAPPLYVIRTUALINFO, ppmApplyvirtualInfo,"queryDetail");
    }

    @Override
    public Integer updatePpmApplyvirtualInfo(PpmApplyvirtualInfo ppmApplyvirtualInfo) {
        return myBatisDao.executionPojo(PPMAPPLYVIRTUALINFO, ppmApplyvirtualInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmApplyvirtualInfo(PpmApplyvirtualInfo ppmApplyvirtualInfo) {
        return myBatisDao.executionPojo(PPMAPPLYVIRTUALINFO, ppmApplyvirtualInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public void changeVirtualInfo(Map param) {
        myBatisDao.executionMap(PPMAPPLYVIRTUALINFO, param, IMyBatisDao.SQL.UPDATE,"updateVirtualState");
    }

    @Override
    public boolean checkedVirtualNo(String virtualNo) {
        PpmApplyvirtualInfo info = new PpmApplyvirtualInfo();
        info.setVirtualNo(virtualNo);
        List<PpmApplyvirtualInfo> list = myBatisDao.selectListT(PPMAPPLYVIRTUALINFO, info, "getList");
        return null != list && !list.isEmpty();
    }

    @Override
    public String nbAreaManagerApprove(PpmApplyvirtualInfoDto ppmApplyvirtualInfoDto) {
        BpsWorkFlowDto bps = ppmApplyvirtualInfoDto.getBps();
        // 详情
        PpmOutsourcingAccessapplyDto param = new PpmOutsourcingAccessapplyDto();
        param.setVirtualDesktopFlowId(bps.getProcessInstId());
        PpmOutsourcingAccessapplyDto dto = ppmOutsourcingAccessapplyService.queryDetail(param);
        Map<String,String> map = new HashMap<>();
        // 添加虚拟机桌面号
        ppmApplyvirtualInfoDto.setFlowid("XNZM"+bps.getProcessInstId());
        ppmApplyvirtualInfoDto.setUserType("WB");
        ppmApplyvirtualInfoDto.setPzx("32w");
        String endTime = DateUtil.dateToStr(DateUtil.intervalYear(new Date(), 1), "yyyyMMdd");
        ppmApplyvirtualInfoDto.setDuedate(endTime);
        ppmApplyvirtualInfoDto.setState("1");
        ppmApplyvirtualInfoDto.setStatus("1");
        ppmApplyvirtualInfoDto.setXmjl("");
        this.addPpmApplyvirtualInfo(ppmApplyvirtualInfoDto);
        // 通过
        if("01".equals(bps.getConclusion())) {
            map.put("status", "-1");
        }
        bps.setProcessDefName("com.work.ppm.bps.xnzmsqNew");
        bps.setApplydesc("宁波地区管理部审批");
        bps.setRelaData(map);
        BpsUtil.finshWorkItem(bps);
        String message = BpsUtil.queryProcessInfo(bps);
        // 1 开通svn申请
        if("1".equals(dto.getSvnLimit())) {
            Map<String, String> svnMap = new HashMap<>();
            String userIds = commEmployeeService.queryUserIdByRole("SVN0001");
            svnMap.put("users", userIds);
            svnMap.put("status", "-1");
            BpsWorkFlowDto svnBps = new BpsWorkFlowDto();
            svnBps.setProcessDefName("com.work.ppm.bps.svnqxsqNew");
            svnBps.setProcessInstName(dto.getJobId() + "申请svn权限");
            svnBps.setProcessInstDesc("新svn权限申请");
            svnBps.setRelaData(svnMap);
            svnBps.setUserId(bps.getUserId());
            // 日志
            svnBps.setApplydesc("svn权限申请");
            svnBps.setConclusion("01");
            svnBps.setSuggest("同意");
            String instanceId = BpsUtil.createProcessAndStart(svnBps);
            if (StringUtil.isNotBlank(instanceId)) {
                svnBps.setApplydesc("二级部经理审批");
                svnBps.setProcessInstId(instanceId);
                ppmFlowLogService.saveFlowLog(svnBps);
            }

            dto.setApplysvnno(instanceId);
            ppmOutsourcingAccessapplyService.updatePpmOutsourcingAccessapply(dto);
        }
        // 接手人
//        String username = UsersUtil.getUser(dto.getOrganizers());
        // 通知
        MessageNotice notice = new MessageNotice(dto.getJobId() + "@" + dto.getUserName(), dto.getOrganizers(),
                "com.work.ppm.bps.xnzmsqNew","finishActivity",
                dto.getJobId() + "申请虚拟桌面",bps.getProcessInstId(),bps.getWorkItemId());
        notice.sendFlowTemplateNotice();
        return message;
    }
}
