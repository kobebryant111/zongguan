package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.application.dto.basismanager.PpmMissionInfoDto;
import com.work.ppm.application.dto.flowmanager.BpsDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.processmanager.PpmNewBranchBankAcceptanceExcelDto;
import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.processmanager.ext.PpmNewBranchBankAcceptanceInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.basismanager.IPpmMissionInfoService;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.notice.SendMessage;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.processmanager.PpmNewBranchBankAcceptance;
import com.work.ppm.application.dto.processmanager.PpmNewBranchBankAcceptanceDto;
import com.work.ppm.application.services.processmanager.IPpmNewBranchBankAcceptanceService;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 新支行交付流程
 *
 */
@Service("ppmNewBranchBankAcceptanceService")
@Slf4j
public class PpmNewBranchBankAcceptanceService extends BaseDaoSupper
        implements IPpmNewBranchBankAcceptanceService, ICommonDeleteFlowService {
    /**
     * 实体类路径
     */
    private static final String PPMNEWBRANCHBANKACCEPTANCE =
            "com.work.ppm.application.model.processmanager.PpmNewBranchBankAcceptance";
    /**
     * 流程名
     */
    private static final String FLOW_NAME = "com.work.ppm.bps.newBranchBankAcceptance";
    /**
     * 变更状态字典
     */
    private static final String STATE_DICT_NAME = "NEW_DEVICE_CHAGE_STATE";

    /**
     * 流程id空提示
     */
    private static final String INSTANCEID_EMPTY = "[InstanceId]不能为空";
    /**
     * 结论空提示
     */
    private static final String CONCLUSION_EMPTY = "[conclusion]不合法";
    /**
     * id空提示
     */
    private static final String INSTID_EMPTY = "[ProcessInstId]不能为空";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 督办任务生成方法
     */
    @Autowired
    private IPpmMissionInfoService ppmMissionInfoService;

    /**
     * 员工公共方法类
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    /**
     * 公共流程接口
     */
    @Autowired
    private ICommonFlowService commonFlowService;

    /**
     * 公共流程接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(
                ppmNewBranchBankAcceptanceDto, PpmNewBranchBankAcceptanceDto.class);
        myBatisDao.selectPager(PPMNEWBRANCHBANKACCEPTANCE, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmNewBranchBankAcceptance(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        ppmNewBranchBankAcceptanceDto.setState(1);
        PpmNewBranchBankAcceptance ppmNewBranchBankAcceptance = JsonUtil.
                jsonToClass(ppmNewBranchBankAcceptanceDto, PpmNewBranchBankAcceptance.class);
        Long id = getNextSequence(PpmNewBranchBankAcceptance.class);

        // 如果需要请set 主键id
        ppmNewBranchBankAcceptance.setId(id);
        myBatisDao.executionPojo(PPMNEWBRANCHBANKACCEPTANCE,
                ppmNewBranchBankAcceptance, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmNewBranchBankAcceptanceDto queryDetail(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        PpmNewBranchBankAcceptance ppmNewBranchBankAcceptance =
                JsonUtil.jsonToClass(ppmNewBranchBankAcceptanceDto, PpmNewBranchBankAcceptance.class);
        PpmNewBranchBankAcceptance data = myBatisDao.selectClass(PPMNEWBRANCHBANKACCEPTANCE,
                ppmNewBranchBankAcceptance, "queryDetail");
        PpmNewBranchBankAcceptanceDto item = JsonUtil.jsonToClass(data, PpmNewBranchBankAcceptanceDto.class);
        PpmNewBranchBankAcceptanceDto info = getCurrentInfo(item.getBadge());
        item.setDeptName(info.getDeptName());
        String netcoolManagerName = commEmployeeService.getBadgeAndName(item.getNetcoolManagerId());
        String upsManagerName = commEmployeeService.getBadgeAndName(item.getUpsManagerId());
        String ackManagerName = commEmployeeService.getBadgeAndName(item.getAckManagerId());
        item.setNetcoolManagerName(netcoolManagerName);
        item.setUpsManagerName(upsManagerName);
        item.setAckManagerName(ackManagerName);
        return item;
    }

    @Override
    @Transactional("defaultTx")
    public String draft(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(ppmNewBranchBankAcceptanceDto.getBadge());
        // 分布式事务
        String xid = BpsUtil.transactionBegin(ppmNewBranchBankAcceptanceDto.getBadge());
        Map<String, String> map;
        try {
            bps.setConclusion("01");
            bps.setProcessInstDesc("新支行验收流程");
            bps.setApplydesc("发起申请");
            bps.setProcessDefName(FLOW_NAME);
            bps.setFlowUrl("newBranchBankAcceptance");
            String userName = ppmNewBranchBankAcceptanceDto.getUserName();
            String userId = ppmNewBranchBankAcceptanceDto.getBadge();
            if (!StringUtil.isNotBlank(ppmNewBranchBankAcceptanceDto.getInstanceId())) {
                ppmNewBranchBankAcceptanceDto.setUserName(userName + "(" + userId + ")");
                ppmNewBranchBankAcceptanceDto.setProcessType(FLOW_NAME);
                if (StringUtil.isNotBlank(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId())) {
                    map = BpsDto.getInstance().setStatus("-1")
                            .setUsers(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId())
                            .build();
                    String namestr = commEmployeeService
                            .getBadgeAndName(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId());
                    ppmNewBranchBankAcceptanceDto.setCurrentId(namestr);
                    bps.setRelaData(map);
                }
                if (ppmNewBranchBankAcceptanceDto.getProcessTitle().length() > 50) {
                    String cut = ppmNewBranchBankAcceptanceDto.getProcessTitle().substring(0, 50);
                    bps.setProcessInstName(cut);
                    ppmNewBranchBankAcceptanceDto.setProcessTitle(cut);
                } else {
                    bps.setProcessInstName(ppmNewBranchBankAcceptanceDto.getProcessTitle());
                }
                String processInstId = BpsUtil.createProcessInstance(bps, xid);
                bps.setProcessInstId(processInstId);
                ppmNewBranchBankAcceptanceDto.setInstanceId(processInstId);

                Date date = new Date();
                ppmNewBranchBankAcceptanceDto.setCreateDate(date);
                ppmNewBranchBankAcceptanceDto.setLastMofiyDate(date);


                ppmNewBranchBankAcceptanceDto.setState(1);
                this.addPpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
            } else {
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getInstanceId(), INSTANCEID_EMPTY);
                checkDraftEnable(bps);
                String message = BpsUtil.queryProcessInfo(bps);
                this.updatePpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
                return message;
            }
            BpsUtil.transactionCommit(ppmNewBranchBankAcceptanceDto.getBadge(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(ppmNewBranchBankAcceptanceDto.getBadge(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }


    @Override
    @Transactional("defaultTx")
    public String initiate(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(ppmNewBranchBankAcceptanceDto.getBadge());
        // 分布式事务
        String xid = BpsUtil.transactionBegin(ppmNewBranchBankAcceptanceDto.getBadge());
        Map<String, String> map;
        try {
            bps.setConclusion("01");
            bps.setProcessInstDesc("新支行验收流程");
            bps.setApplydesc("发起申请");
            bps.setProcessDefName(FLOW_NAME);
            bps.setFlowUrl("newBranchBankAcceptance");
            String userName = ppmNewBranchBankAcceptanceDto.getUserName();
            String userId = ppmNewBranchBankAcceptanceDto.getBadge();
            if (!StringUtil.isNotBlank(ppmNewBranchBankAcceptanceDto.getInstanceId())) {
                ppmNewBranchBankAcceptanceDto.setUserName(userName + "(" + userId + ")");
                ppmNewBranchBankAcceptanceDto.setProcessType(FLOW_NAME);
                map = BpsDto.getInstance().setStatus("-1")
                        .setUsers(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId()).build();
                String namestr = commEmployeeService
                        .getBadgeAndName(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId());
                ppmNewBranchBankAcceptanceDto.setCurrentId(namestr);
                bps.setRelaData(map);
                if (ppmNewBranchBankAcceptanceDto.getProcessTitle().length() > 50) {
                    bps.setProcessInstName(ppmNewBranchBankAcceptanceDto.getProcessTitle().substring(0, 20));
                } else {
                    bps.setProcessInstName(ppmNewBranchBankAcceptanceDto.getProcessTitle());
                }
                String processInstId = BpsUtil.createProcessAndStart(bps, xid);
                bps.setProcessInstId(processInstId);
                ppmNewBranchBankAcceptanceDto.setInstanceId(processInstId);

                Date date = new Date();
                ppmNewBranchBankAcceptanceDto.setCreateDate(date);
                ppmNewBranchBankAcceptanceDto.setLastMofiyDate(date);


                ppmNewBranchBankAcceptanceDto.setState(1);
                this.addPpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
            } else {
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getInstanceId(), INSTANCEID_EMPTY);
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId(), "[NetcoolManagerId]不能为空");
                map = BpsDto.getInstance().setStatus("-1")
                        .setUsers(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId()).build();
                String namestr = commEmployeeService
                        .getBadgeAndName(ppmNewBranchBankAcceptanceDto.getNetcoolManagerId());
                ppmNewBranchBankAcceptanceDto.setCurrentId(namestr);
                bps.setRelaData(map);
                bps.setProcessInstId(ppmNewBranchBankAcceptanceDto.getInstanceId());
                checkDraftEnable(bps);
                BpsUtil.finshWorkItem(bps, xid);
                this.updatePpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
            }
            BpsUtil.transactionCommit(ppmNewBranchBankAcceptanceDto.getBadge(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(ppmNewBranchBankAcceptanceDto.getBadge(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    @Transactional("defaultTx")
    public String doFlow(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        AssertUtil.notNull(bps, "[bps]不能为空");
        String node = bps.getActivityDefID();
        switch (node) {
            case "manualActivity1":
                // Netcool网管监控确认
                return netcoolConfirm(ppmNewBranchBankAcceptanceDto);
            case "manualActivity2":
                // UPS监控确认
                return upsConfirm(ppmNewBranchBankAcceptanceDto);
            case "manualActivity3":
                // ACK监控确认
                return ackConfirm(ppmNewBranchBankAcceptanceDto);
            case "manualActivity99":
                // 宁管部二级部经理确认
                return ministryManagerConfirm(ppmNewBranchBankAcceptanceDto);
            default:
                throw new BizException(ApiCode.PARAMETER_ERROR, "[ActivityDefID]不合法");

        }
    }

    @Override
    @Transactional("defaultTx")
    public String netcoolConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        AssertUtil.notNull(bps.getProcessInstId(), INSTID_EMPTY);

        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(bps.getUserId());
        try {
            checkSubmitEnable(bps);
            // 根据流程id 查询 发起人
            ppmNewBranchBankAcceptanceDto.setInstanceId(bps.getProcessInstId());
            Map<String, String> map;
            bps.setApplydesc("Netcool网管监控确认");
            if ("01".equals(bps.getConclusion())) {
                // 关键业务参数校验
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getUpsManagerId(), "[upsManagerId]不能为空");
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getNetcoolDecision(), "[netcoolDecision]不能为空");

                map = BpsDto.getInstance().setStatus("-1")
                        .setUsers(ppmNewBranchBankAcceptanceDto.getUpsManagerId()).build();
                bps.setRelaData(map);
                ppmNewBranchBankAcceptanceDto.setLastMofiyDate(new Date());
                String userId = commEmployeeService
                        .getBadgeAndName(ppmNewBranchBankAcceptanceDto.getUpsManagerId());
                ppmNewBranchBankAcceptanceDto.setCurrentId(userId);
                this.updatePpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
            } else if ("02".equals(bps.getConclusion())) {
                cleanInfo(bps.getProcessInstId());
                map = BpsDto.getInstance().setStatus("02").build();
                bps.setRelaData(map);
            } else {
                throw new BizException(ApiCode.PARAMETER_ERROR, CONCLUSION_EMPTY);
            }
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(badge, xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(badge, xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    @Transactional("defaultTx")
    public String upsConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        AssertUtil.notNull(bps.getProcessInstId(), INSTID_EMPTY);


        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(bps.getUserId());
        try {
            checkSubmitEnable(bps);
            // 根据流程id 查询 发起人
            ppmNewBranchBankAcceptanceDto.setInstanceId(bps.getProcessInstId());
            Map<String, String> map;
            bps.setApplydesc("UPS监控确认");
            if ("01".equals(bps.getConclusion())) {
                // 关键业务参数校验
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getAckManagerId(), "[ackManagerId]不能为空");
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getUpsDecision(), "[upsDecision]不能为空");

                map = BpsDto.getInstance().setStatus("-1")
                        .setUsers(ppmNewBranchBankAcceptanceDto.getAckManagerId()).build();
                bps.setRelaData(map);
                ppmNewBranchBankAcceptanceDto.setLastMofiyDate(new Date());
                String userId = commEmployeeService
                        .getBadgeAndName(ppmNewBranchBankAcceptanceDto.getAckManagerId());
                ppmNewBranchBankAcceptanceDto.setCurrentId(userId);
                this.updatePpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
            } else if ("02".equals(bps.getConclusion())) {
                cleanInfo(bps.getProcessInstId());
                map = BpsDto.getInstance().setStatus("02").build();
                bps.setRelaData(map);
            } else {
                throw new BizException(ApiCode.PARAMETER_ERROR, CONCLUSION_EMPTY);
            }
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(badge, xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(badge, xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    @Transactional("defaultTx")
    public String ackConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        AssertUtil.notNull(bps.getProcessInstId(), INSTID_EMPTY);

        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(bps.getUserId());
        try {
            checkSubmitEnable(bps);
            // 根据流程id 查询 发起人
            ppmNewBranchBankAcceptanceDto.setInstanceId(bps.getProcessInstId());
            Map<String, String> map;
            bps.setApplydesc("ACK监控确认");
            if ("01".equals(bps.getConclusion())) {
                // 关键业务参数校验
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getAckDecision(), "[ackDecision]不能为空");

                ppmNewBranchBankAcceptanceDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getInstanceId(), INSTANCEID_EMPTY);
                String ministryManagers = getMinistryManagersString();
                map = BpsDto.getInstance().setStatus("-1").setUsers(ministryManagers).build();
                bps.setRelaData(map);
                ppmNewBranchBankAcceptanceDto.setLastMofiyDate(new Date());
                String userId = commEmployeeService.getBadgeAndName(ministryManagers);
                ppmNewBranchBankAcceptanceDto.setCurrentId(userId);
                this.updatePpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
            } else if ("02".equals(bps.getConclusion())) {
                cleanInfo(bps.getProcessInstId());
                map = BpsDto.getInstance().setStatus("02").build();
                bps.setRelaData(map);
            } else {
                throw new BizException(ApiCode.PARAMETER_ERROR, CONCLUSION_EMPTY);
            }
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(badge, xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(badge, xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    @Transactional("defaultTx")
    public String ministryManagerConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        BpsWorkFlowDto bps = ppmNewBranchBankAcceptanceDto.getBps();
        AssertUtil.notNull(bps.getProcessInstId(), INSTID_EMPTY);

        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(bps.getUserId());
        try {
            checkSubmitEnable(bps);
            // 根据流程id 查询 发起人
            ppmNewBranchBankAcceptanceDto.setInstanceId(bps.getProcessInstId());
            PpmNewBranchBankAcceptanceDto dto = queryDetail(ppmNewBranchBankAcceptanceDto);
            String creator = dto.getBadge();
            Map<String, String> map;
            bps.setApplydesc("宁管部二级部经理审批");
            if ("01".equals(bps.getConclusion())) {
                ppmNewBranchBankAcceptanceDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNewBranchBankAcceptanceDto.getInstanceId(), INSTANCEID_EMPTY);

                map = BpsDto.getInstance().setStatus("-1").build();
                bps.setRelaData(map);
                ppmNewBranchBankAcceptanceDto.setLastMofiyDate(new Date());
                ppmNewBranchBankAcceptanceDto.setCurrentId(" ");
                ppmNewBranchBankAcceptanceDto.setState(2);
                this.updatePpmNewBranchBankAcceptance(ppmNewBranchBankAcceptanceDto);
                ppmNewBranchBankAcceptanceDto.setProcessTitle(bps.getProcessInstName());
                addMission(ppmNewBranchBankAcceptanceDto, creator);
            } else if ("02".equals(bps.getConclusion())) {
                cleanInfo(bps.getProcessInstId());
                map = BpsDto.getInstance().setStatus("02").build();
                bps.setRelaData(map);
            } else {
                throw new BizException(ApiCode.PARAMETER_ERROR, CONCLUSION_EMPTY);
            }
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(badge, xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(badge, xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }


    @Override
    public List<PpmNewBranchBankAcceptanceExcelDto> exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto) {
        PpmNewBranchBankAcceptanceDto dto = adaptQueryDto(ppmNingGuanFlowQueryDto);
        if (StringUtil.isNotBlank(dto.getEndDate())) {
            dto.setEndDate(dto.getEndDate() + " 23:59:59");
        }
        Map<String, Object> map = JsonUtil.objectTOMap(dto, PpmNewBranchBankAcceptanceDto.class);
        List<PpmNewBranchBankAcceptanceInfo> list =
                myBatisDao.selectListT(PPMNEWBRANCHBANKACCEPTANCE, map, "queryPagerList");
        if (null == list || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<PpmNewBranchBankAcceptanceExcelDto> res =
                JsonUtil.jsonToList(list, PpmNewBranchBankAcceptanceExcelDto.class);
        return replaceExcelField(res);
    }

    @Override
    public int updatePpmNewBranchBankAcceptance(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto) {
        PpmNewBranchBankAcceptance ppmNewBranchBankAcceptance =
                JsonUtil.jsonToClass(ppmNewBranchBankAcceptanceDto, PpmNewBranchBankAcceptance.class);
        return myBatisDao.executionPojo(PPMNEWBRANCHBANKACCEPTANCE,
                ppmNewBranchBankAcceptance, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    /**
     * 获取当前用户信息
     */
    @Override
    public PpmNewBranchBankAcceptanceDto getCurrentInfo(String userid) {
        PpmNewBranchBankAcceptance stick = new PpmNewBranchBankAcceptance();
        stick.setBadge(userid);
        PpmNewBranchBankAcceptanceInfo item =
                myBatisDao.selectClass(PPMNEWBRANCHBANKACCEPTANCE, stick, "getCurrentInfo");
        if (null != item) {
            item.setCreateDate(new Date());
        }
        return JsonUtil.jsonToClass(item, PpmNewBranchBankAcceptanceDto.class);
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmNewBranchBankAcceptanceDto dto = new PpmNewBranchBankAcceptanceDto();
        dto.setInstanceId(bps.getProcessInstId());
        dto.setState(3);
        checkDraftEnable(bps);
        AssertUtil.notNull(dto.getInstanceId(), INSTANCEID_EMPTY);
        this.updatePpmNewBranchBankAcceptance(dto);
    }

    /**
     * 获取宁管二级部经理
     *
     * @return string 二级部经理名
     */
    private String getMinistryManagersString() {
        List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers("1502");
        StringJoiner joiner = new StringJoiner(",");
        employs.stream().forEach(ii -> joiner.add(ii.getBadge()));
        return joiner.toString();
    }

    /**
     * 生成督办任务
     *
     * @return String 督办任务编号
     */
    private String addMission(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto, String userId) {
        PpmMissionInfoDto missionInfoDto = new PpmMissionInfoDto();
        String missionId = null;
        missionInfoDto.setIsDel("0");
        missionInfoDto.setMissionName(ppmNewBranchBankAcceptanceDto.getProcessTitle());
        EosDictEntry e1 = eosDictEntryService.getTypeidForDetail("XZHYSDB", "DESC_SUFFIX");
        String suffix = e1.getDictname();
        missionInfoDto.setMissionDesc(ppmNewBranchBankAcceptanceDto.getProcessTitle() + suffix);
        Date now = new Date();
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        missionInfoDto.setPlanFinishDate(DateUtil.dateToStr(DateUtil.addOrSubDate(now, 90), dateFormat));
        missionInfoDto.setPlanStateDate(DateUtil.dateToStr(now, dateFormat));
        missionInfoDto.setSourceType("03");
        missionInfoDto.setCreateDate(DateUtil.dateToStr(now, dateFormat));
        missionInfoDto.setCreateUser(userId);
        missionInfoDto.setUpdateDate(DateUtil.dateToStr(now, dateFormat));
        missionInfoDto.setUpdateUser(userId);
        missionInfoDto.setAssignUser(userId);
        missionInfoDto.setTasklevel("03");
        missionInfoDto.setSendEmailType("01");
        EosDictEntry e2 = eosDictEntryService.getTypeidForDetail("XZHYSDB", "DBPERSON");
        missionInfoDto.setDirectuser(e2.getDictname());
        missionInfoDto.setSuperviseruser(userId);
        missionInfoDto.setMissiontype("01");
        missionInfoDto.setMissionimportance("02");
        missionInfoDto.setTasksource("07");
        missionInfoDto.setStatus("00");

        try {
            AssertUtil.notNull(userId, "[userId]不能为空");
            missionId = ppmMissionInfoService.saveMission(missionInfoDto);
            SendMessage msg = new SendMessage();
            StringBuilder dbtext = new StringBuilder();
            CommEmployee emp = commEmployeeService.getCommEmployeeInfo(userId);
            dbtext.append(emp.getEmpname())
                    .append("您好，您有一个督办任务，名为")
                    .append(missionInfoDto.getMissionName())
                    .append("，请及时跟进并负责该任务的整体进度和质量，谢谢。");
            msg.sendESBText(userId, dbtext.toString());
            log.info(userId + "生成了一条督办任务,任务id:" + missionId);
        } catch (Exception e) {
            log.warn("生成督办任务失败!错误信息:");
            log.warn(e.getMessage());
        }


        return missionId;
    }

    /**
     * 判断是否重复提交
     *
     * @param bps 流程对象
     */
    private void checkSubmitEnable(BpsWorkFlowDto bps) {
        String curr = commonFlowService.queryWorkItem(bps).getActivityDefID();
        String expect = bps.getActivityDefID();
        if (!expect.equals(curr)) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程该节点已被审批");
        }
    }

    /**
     * 判断草稿是否可编辑
     *
     * @param bps 流程对象
     */
    private void checkDraftEnable(BpsWorkFlowDto bps) {
        String curr = commonFlowService.queryWorkItem(bps).getActivityDefID();
        String expect = "manualActivity0";
        if (!expect.equals(curr)) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "该流程已发起，无法修改草稿");
        }
    }


    /**
     * dto适配
     *
     * @param ppmNingGuanFlowQueryDto 对象
     * @return 适配对象
     */
    private PpmNewBranchBankAcceptanceDto adaptQueryDto(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto) {
        ppmNingGuanFlowQueryDto.setProcessType(null);
        PpmNewBranchBankAcceptanceDto dto =
                JsonUtil.jsonToClass(ppmNingGuanFlowQueryDto, PpmNewBranchBankAcceptanceDto.class);
        dto.setUserName(ppmNingGuanFlowQueryDto.getCreator());
        String stateStr = ppmNingGuanFlowQueryDto.getFlowStatus();
        if ("进行中".equals(stateStr)) {
            dto.setState(1);
        } else if ("已结束".equals(stateStr)) {
            dto.setState(2);
        } else if ("已删除".equals(stateStr)) {
            dto.setState(3);
        }
        return dto;
    }


    /**
     * 字段替换
     *
     * @param list 对象
     * @return 替换字段后list
     */
    private List<PpmNewBranchBankAcceptanceExcelDto> replaceExcelField(List<PpmNewBranchBankAcceptanceExcelDto> list) {
        for (PpmNewBranchBankAcceptanceExcelDto item : list) {
            item.setCreateDateStr(DateUtil.dateToStr(item.getCreateDate(), "yyyy-MM-dd"));
            item.setLastMofiyDateStr(DateUtil.dateToStr(item.getLastMofiyDate(), "yyyy-MM-dd"));
            EosDictEntry entity = eosDictEntryService.getTypeidForDetail(STATE_DICT_NAME, item.getState());
            if (null != entity) {
                item.setState(entity.getDictname());
            }
            item.setNetcoolManagerId(commEmployeeService.getBadgeAndName(item.getNetcoolManagerId()));
            item.setUpsManagerId(commEmployeeService.getBadgeAndName(item.getUpsManagerId()));
            item.setAckManagerId(commEmployeeService.getBadgeAndName(item.getAckManagerId()));
            item.setNetcoolDecision(translateDecision(item.getNetcoolDecision()));
            item.setUpsDecision(translateDecision(item.getUpsDecision()));
            item.setAckDecision(translateDecision(item.getAckDecision()));
        }
        return list;
    }

    /**
     * 决策编码转换
     *
     * @param decision 决策编码
     * @return 决策中文
     */
    private String translateDecision(String decision) {
        if (StringUtil.isNotBlank(decision) && "01".contains(decision)) {
            return "1".equals(decision) ? "是" : "否";
        }
        return "";
    }

    /**
     * 退回后清除业务数据
     *
     * @param instanceId 流程id
     */
    private void cleanInfo(String instanceId) {
        myBatisDao.executionPojo(PPMNEWBRANCHBANKACCEPTANCE, instanceId, IMyBatisDao.SQL.UPDATE, "cleanInfo");
    }

}
