package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.processmanager.PpmChagedeviceChildrenDto;
import com.work.ppm.application.dto.processmanager.PpmNetworkStickDto;
import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.processmanager.PpmNetworkStick;
import com.work.ppm.application.model.processmanager.ext.PpmNetworkStickInfo;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.processmanager.IPpmChagedeviceChildrenService;
import com.work.ppm.application.services.processmanager.IPpmNetworkStickService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 宁管部网点设备变更维护
 *
 */
@Service("ppmNetworkStickService")
public class PpmNetworkStickService extends BaseDaoSupper implements IPpmNetworkStickService, ICommonDeleteFlowService {
    /**
     * 实体类路径
     */
    private static String PPMNETWORKSTICK = "com.work.ppm.application.model.processmanager.PpmNetworkStick";
    /**
     * 流程实例名称
     */
    private static String FLOW_NAME = "com.work.ppm.bps.deviceChangeStick";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 员工接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 设备名称选项接口
     */
    @Autowired
    private IPpmChagedeviceChildrenService ppmChagedeviceChildrenService;


    @Override
    public void queryPager(PpmNetworkStickDto ppmNetworkStickDto, Pager pager) {
        if(StringUtil.isNotBlank(ppmNetworkStickDto.getEndDate())) {
            ppmNetworkStickDto.setEndDate(ppmNetworkStickDto.getEndDate() + " 23:59:59");
        }
        Map<String, Object> map = JsonUtil.objectTOMap(ppmNetworkStickDto, PpmNetworkStickDto.class);
        myBatisDao.selectPager(PPMNETWORKSTICK, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmNetworkStick(PpmNetworkStickDto ppmNetworkStickDto) {
        ppmNetworkStickDto.setState(1);
        PpmNetworkStick ppmNetworkStick = JsonUtil.jsonToClass(ppmNetworkStickDto, PpmNetworkStick.class);
        Long id = getNextSequence(PpmNetworkStick.class);
        // 如果需要请set 主键id
        ppmNetworkStick.setId(id);
        myBatisDao.executionPojo(PPMNETWORKSTICK, ppmNetworkStick, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    /**
     * 更新
     *
     * @param ppmNetworkStickDto 对象
     * @return int
     */
    private int updatePpmNetworkStick(PpmNetworkStickDto ppmNetworkStickDto) {
        PpmNetworkStick ppmNetworkStick = JsonUtil.jsonToClass(ppmNetworkStickDto, PpmNetworkStick.class);
        return myBatisDao.executionPojo(PPMNETWORKSTICK, ppmNetworkStick, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public PpmNetworkStickDto queryDetail(PpmNetworkStickDto ppmNetworkStickDto) {
        PpmNetworkStick ppmNetworkStick = JsonUtil.jsonToClass(ppmNetworkStickDto, PpmNetworkStick.class);
        PpmNetworkStick data = myBatisDao.selectClass(PPMNETWORKSTICK, ppmNetworkStick,
                "queryDetail");

        PpmNetworkStickDto item = JsonUtil.jsonToClass(data, PpmNetworkStickDto
                .class);
        Map<String, Object> map = JsonUtil.objectTOMap(item, PpmNetworkStickDto.class);
        Map<String, Object> map1 ;
        if(StringUtil.isNotEmpty(item.getChangeDeviceData())) {
            map1 = JsonUtil.jsonToMap(item.getChangeDeviceData());
        } else {
            map1 = new HashMap<>();
        }

        map.putAll(map1);
        item = JsonUtil.jsonToClass(map, PpmNetworkStickDto
                .class);
        return item;
    }

    @Override
    @Transactional("defaultTx")
    public String draft(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(ppmNetworkStickDto.getBadge());
        String xid = BpsUtil.transactionBegin(ppmNetworkStickDto.getBadge());
        try {
            bps.setConclusion("01");
            bps.setProcessInstDesc("网点设备变更流程");
            bps.setApplydesc(bps.getProcessInstDesc());
            bps.setProcessDefName(FLOW_NAME);
            bps.setFlowUrl("deviceChangeStick");
            Map<String, String> map;
            ppmNetworkStickDto.setUserName(ppmNetworkStickDto.getUserName() + "(" + ppmNetworkStickDto.getBadge() + ")");
            ppmNetworkStickDto.setCurrentToId(ppmNetworkStickDto.getUserName());
            // 添加
            if (!StringUtil.isNotEmpty(ppmNetworkStickDto.getInstanceId())) {
                //宁管二级部经理层
                List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers("1502");
                StringJoiner joiner = new StringJoiner(",");
                employs.stream().forEach(ii -> {
                    joiner.add(ii.getBadge());
                });
                map = BpsDto.getInstance().setStatus("-1").setUsers(joiner.toString()).build();
                bps.setRelaData(map);
                // 流程标题
                if(ppmNetworkStickDto.getProcessTitle().length() > 20) {
                    bps.setProcessInstName(ppmNetworkStickDto.getProcessTitle().substring(0,20));
                } else {
                    bps.setProcessInstName(ppmNetworkStickDto.getProcessTitle());
                }

                String instancedId = BpsUtil.createProcessInstance(bps, xid);
                bps.setProcessInstId(instancedId);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                Date date = new Date();
                ppmNetworkStickDto.setCreateDate(
                        DateUtil.strToDateTime(ppmNetworkStickDto.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
                ppmNetworkStickDto.setLastMofiyDate(date);

                // 1:进行中，2：已完成，3：已删除
                ppmNetworkStickDto.setState(1);
                this.addPpmNetworkStick(ppmNetworkStickDto);
            } else {
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                this.updatePpmNetworkStick(ppmNetworkStickDto);
                return BpsUtil.queryProcessInfo(bps);
            }
            BpsUtil.transactionCommit(ppmNetworkStickDto.getBadge(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(ppmNetworkStickDto.getBadge(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    public List<PpmNetworkStickDto> exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto) {
        PpmNetworkStickDto ppmNetworkStickDto = adaptQueryDto(ppmNingGuanFlowQueryDto);
        if(StringUtil.isNotBlank(ppmNetworkStickDto.getEndDate())) {
            ppmNetworkStickDto.setEndDate(ppmNetworkStickDto.getEndDate() + " 23:59:59");
        }
        Map<String, Object> map = JsonUtil.objectTOMap(ppmNetworkStickDto, PpmNetworkStickDto.class);
        List<PpmNetworkStickInfo> list = myBatisDao.selectListT(PPMNETWORKSTICK, map, "queryPagerList");
        if (null == list || list.isEmpty()) {
            return null;
        }
        List<PpmNetworkStickDto> dtos = JsonUtil.jsonToList(list, PpmNetworkStickDto.class);
        List<PpmNetworkStickDto> newDtos = this.handleDeviceOptions(dtos);
        return newDtos;

    }

    @Override
    public List<PpmNetworkStickDto> exportExcel(PpmNetworkStickDto ppmNetworkStickDto) {
        if(StringUtil.isNotBlank(ppmNetworkStickDto.getEndDate())) {
            ppmNetworkStickDto.setEndDate(ppmNetworkStickDto.getEndDate() + " 23:59:59");
        }
        Map<String, Object> map = JsonUtil.objectTOMap(ppmNetworkStickDto, PpmNetworkStickDto.class);
        List<PpmNetworkStickInfo> list = myBatisDao.selectListT(PPMNETWORKSTICK, map, "queryPagerList");
        if (null == list || list.isEmpty()) {
            return null;
        }
        List<PpmNetworkStickDto> dtos = JsonUtil.jsonToList(list, PpmNetworkStickDto.class);
        List<PpmNetworkStickDto> newDtos = this.handleDeviceOptions(dtos);
        return newDtos;
    }

    @Override
    @Transactional("defaultTx")
    public String initiatePpmNetworkStick(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(ppmNetworkStickDto.getBadge());
        String xid = BpsUtil.transactionBegin(ppmNetworkStickDto.getBadge());
        Map<String, String> map;
        try {
            bps.setConclusion("01");
            bps.setProcessInstDesc("网点设备变更流程");
            bps.setApplydesc(bps.getProcessInstDesc());
            bps.setProcessDefName(FLOW_NAME);
            bps.setFlowUrl("deviceChangeStick");
            ppmNetworkStickDto.setUserName(ppmNetworkStickDto.getUserName() + "(" + ppmNetworkStickDto.getBadge() + ")");
            // 直接流转
            if (!StringUtil.isNotBlank(ppmNetworkStickDto.getInstanceId())) {
                // 如果是采购/报废则直接流转至出入库录入确认
                if (StringUtil.isNotEmpty(ppmNetworkStickDto.getChangeType())
                        && (1 == ppmNetworkStickDto.getChangeType()
                        || 2 == ppmNetworkStickDto.getChangeType())) {
                    map = BpsDto.getInstance().setStatus("03").setUsers(ppmNetworkStickDto.getForConfirm()).build();
                    String namestr = commEmployeeService.getBadgeAndName(ppmNetworkStickDto.getForConfirm());
                    ppmNetworkStickDto.setCurrentToId(namestr);
                } else {
                    List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers("1502");
                    StringJoiner joiner = new StringJoiner(",");
                    employs.stream().forEach(ii -> {
                        joiner.add(ii.getBadge());
                    });
                    map = BpsDto.getInstance().setStatus("-1").setUsers(joiner.toString()).build();
                    String namestr = commEmployeeService.getBadgeAndName(joiner.toString());
                    ppmNetworkStickDto.setCurrentToId(namestr);
                }

                bps.setRelaData(map);
                // 流程标题
                if(ppmNetworkStickDto.getProcessTitle().length() > 20) {
                    bps.setProcessInstName(ppmNetworkStickDto.getProcessTitle().substring(0,20));
                } else {
                    bps.setProcessInstName(ppmNetworkStickDto.getProcessTitle());
                }
                // 发起并流转
                String processInstId = BpsUtil.createProcessAndStart(bps, xid);
                bps.setProcessInstId(processInstId);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                Date date = new Date();
                ppmNetworkStickDto.setCreateDate(
                        DateUtil.strToDateTime(ppmNetworkStickDto.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
                ppmNetworkStickDto.setLastMofiyDate(date);

                // 1:进行中，2：已完成，3：已删除
                ppmNetworkStickDto.setState(1);
                this.addPpmNetworkStick(ppmNetworkStickDto);
            } else {
                AssertUtil.notNull(ppmNetworkStickDto.getChangeType(), "[changeType]不能为空");
//                AssertUtil.notNull(ppmNetworkStickDto.getForConfirm(), "[forConfirm]不能为空");
                // 更新 如果是采购/报废 则流转到 出入库录入确认
                if (ppmNetworkStickDto.getChangeType().intValue() == 1
                        || ppmNetworkStickDto.getChangeType().intValue() == 2) {
                    map = BpsDto.getInstance().setStatus("03").setUsers(ppmNetworkStickDto.getForConfirm()).build();
                    String namestr = commEmployeeService.getBadgeAndName(ppmNetworkStickDto.getForConfirm());
                    ppmNetworkStickDto.setCurrentToId(namestr);
                } else {
                    List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers("1502");
                    StringJoiner joiner = new StringJoiner(",");
                    employs.stream().forEach(ii -> {
                        joiner.add(ii.getBadge());
                    });
                    map = BpsDto.getInstance().setStatus("-1").setUsers(joiner.toString()).build();
                    String namestr = commEmployeeService.getBadgeAndName(joiner.toString());
                    ppmNetworkStickDto.setCurrentToId(namestr);
                }
                bps.setRelaData(map);
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                this.updatePpmNetworkStick(ppmNetworkStickDto);
                BpsUtil.finshWorkItem(bps, xid);
            }
            BpsUtil.transactionCommit(ppmNetworkStickDto.getBadge(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(ppmNetworkStickDto.getBadge(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    @Transactional("defaultTx")
    public String ministryManagerExamination(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(bps.getUserId());
        try {
            // 根据流程id 查询 发起人
            ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
            PpmNetworkStickDto dto = this.queryDetail(ppmNetworkStickDto);
            Map<String, String> map;
            bps.setApplydesc("宁波地区管理部二级部经理审批");
            if ("01".equals(bps.getConclusion())) {
                map = BpsDto.getInstance().setStatus("-1").setUsers(dto.getBadge()).build();
                bps.setRelaData(map);
                ppmNetworkStickDto.setLastMofiyDate(new Date());
                String userId = commEmployeeService.getBadgeAndName(ppmNetworkStickDto.getBadge());
                ppmNetworkStickDto.setCurrentToId(userId);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                this.updatePpmNetworkStick(ppmNetworkStickDto);
            } else {
                // 退回
                map = BpsDto.getInstance().setStatus("02").build();
                bps.setRelaData(map);
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
    public String dealingExamination(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        Map<String, String> map;
        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(badge);
        try {
            bps.setApplydesc("处理人审批");
            if ("01".equals(bps.getConclusion())) {
                // 各个数据是否正常，如果否，则直接结束流程，否则正常流转
                if (ppmNetworkStickDto.getDataIfNormal().intValue() == 0) {
                    map = BpsDto.getInstance().setStatus("03").build();
                    ppmNetworkStickDto.setState(2);
                    ppmNetworkStickDto.setCurrentToId(" ");
                } else {
                    // 监控变更AB岗
                    AssertUtil.notNull(ppmNetworkStickDto.getMonitoringConfirm(), "[monitoringConfirm]不能为空");
                    map = BpsDto.getInstance().setStatus("-1").setUsers(ppmNetworkStickDto.getMonitoringConfirm()).build();
                    String userId = commEmployeeService.getBadgeAndName(ppmNetworkStickDto.getMonitoringConfirm());
                    ppmNetworkStickDto.setCurrentToId(userId);
                }
                bps.setRelaData(map);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                ppmNetworkStickDto.setLastMofiyDate(new Date());
                this.updatePpmNetworkStick(ppmNetworkStickDto);
            } else {
                // 退回
                map = BpsDto.getInstance().setStatus("02").build();
                bps.setRelaData(map);
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
    public String monitorChangeConfirm(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        Map<String, String> map;
        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(badge);
        try {
            if ("01".equals(bps.getConclusion())) {
                AssertUtil.notNull(ppmNetworkStickDto.getForConfirm(), "[forConfirm]不能为空");
                map = BpsDto.getInstance().setStatus("-1").setUsers(ppmNetworkStickDto.getForConfirm()).build();
                bps.setApplydesc("监控变更确认");
                bps.setRelaData(map);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                ppmNetworkStickDto.setLastMofiyDate(new Date());
                String userId = commEmployeeService.getBadgeAndName(ppmNetworkStickDto.getForConfirm());
                ppmNetworkStickDto.setCurrentToId(userId);
                this.updatePpmNetworkStick(ppmNetworkStickDto);
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
    public String skinprinterLibConfirm(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        Map<String, String> map;
        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(badge);
        try {
            if ("01".equals(bps.getConclusion())) {
                AssertUtil.notNull(ppmNetworkStickDto.getCmdbConfirm(), "[cmdbConfirm]不能为空");
                map = BpsDto.getInstance().setStatus("-1").setUsers(ppmNetworkStickDto.getCmdbConfirm()).build();
                bps.setApplydesc("出入库录入确认");
                bps.setRelaData(map);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                ppmNetworkStickDto.setLastMofiyDate(new Date());
                String userId = commEmployeeService.getBadgeAndName(ppmNetworkStickDto.getCmdbConfirm());
                ppmNetworkStickDto.setCurrentToId(userId);
                this.updatePpmNetworkStick(ppmNetworkStickDto);
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
    public String cmdbEntryConfirm(PpmNetworkStickDto ppmNetworkStickDto) {
        BpsWorkFlowDto bps = ppmNetworkStickDto.getBps();
        Map<String, String> map;
        String badge = bps.getUserId();
        String xid = BpsUtil.transactionBegin(badge);
        try {
            if ("01".equals(bps.getConclusion())) {
                map = BpsDto.getInstance().setStatus("-1").build();
                bps.setApplydesc("CMDB录入确认");
                bps.setRelaData(map);
                ppmNetworkStickDto.setInstanceId(bps.getProcessInstId());
                AssertUtil.notNull(ppmNetworkStickDto.getInstanceId(), "[InstanceId]不能为空");
                ppmNetworkStickDto.setLastMofiyDate(new Date());
                ppmNetworkStickDto.setCurrentToId(" ");
                // 1:进行中，2：已完成，3：已删除
                ppmNetworkStickDto.setState(2);
                this.updatePpmNetworkStick(ppmNetworkStickDto);
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
    public PpmNetworkStickDto getCurrentInfo(String userid) {
        PpmNetworkStick stick = new PpmNetworkStick();
        stick.setBadge(userid);
        PpmNetworkStickInfo item = myBatisDao.selectClass(PPMNETWORKSTICK, stick, "getCurrentInfo");
        if (null != item) {
            item.setCreateDate(new Date());
        }
        PpmNetworkStickDto dto = JsonUtil.jsonToClass(item, PpmNetworkStickDto.class);
        return dto;
    }

    /**
     * 处理设备选项名称
     *
     * @param dtos
     * @return list
     */
    private List<PpmNetworkStickDto> handleDeviceOptions(List<PpmNetworkStickDto> dtos) {
        List<PpmChagedeviceChildrenDto> cList = ppmChagedeviceChildrenService.queryListAll();
        String[] temps = {"是", "否"};
        List<PpmNetworkStickDto> news = new ArrayList<>();
        dtos.stream().forEach(ii -> {
            String value;
            String json = ii.getChangeDeviceData();
            Map<String, Object> map1 = JsonUtil.objectTOMap(ii, PpmNetworkStickDto.class);
            Map<String, Object> map2 ;
            if(StringUtil.isNotBlank(json)) {
                map2 = JsonUtil.jsonToMap(json);
            } else {
                map2 = new HashMap<>();
            }

            map1.putAll(map2);
            ii = JsonUtil.mapToObject(map1, PpmNetworkStickDto.class);
            PpmNetworkStickDto finalIi = ii;
            // 如果设备类型为空 或者 各个数据为空
            if(!StringUtil.isNotEmpty(finalIi.getChangeDevice())
                || (!StringUtil.isNotEmpty(finalIi.getDataIfNormal())
                    || finalIi.getDataIfNormal().intValue() == 0) ) {
                ii.setStartDate(DateUtil.dateToStr(ii.getCreateDate(), "yyyy-MM-dd"));
                ii.setLastMofiyDateStr(DateUtil.dateToStr(ii.getLastMofiyDate(), "yyyy-MM-dd"));
                if(StringUtil.isNotEmpty(ii.getDataIfNormal())) {
                    ii.setDataIfNormalStr(ii.getDataIfNormal().intValue() == 1 ? temps[0] : temps[1]);
                }
                //监控变更确认AB岗
                if(StringUtil.isNotEmpty(ii.getMonitoringConfirm())) {
                    String monitoringConfirm = commEmployeeService.getBadgeAndName(ii.getMonitoringConfirm());
                    ii.setMonitoringConfirm(monitoringConfirm);
                }
                //出入库确认AB岗
                if(StringUtil.isNotEmpty(ii.getForConfirm())) {
                    String forConfirm = commEmployeeService.getBadgeAndName(ii.getForConfirm());
                    ii.setForConfirm(forConfirm);
                }
                //CMDB确认AB
                if(StringUtil.isNotEmpty(ii.getCmdbConfirm())) {
                    String cmdbConfirm = commEmployeeService.getBadgeAndName(ii.getCmdbConfirm());
                    ii.setCmdbConfirm(cmdbConfirm);
                }
                news.add(ii);
                return ;
            }
            List<PpmChagedeviceChildrenDto> ccList =
                    cList.stream().filter(i -> i.getMid().intValue() == finalIi.getChangeDevice()).collect(Collectors.toList());
            StringJoiner joiner = new StringJoiner("|");
            // UPS电池
            if (ii.getChangeDevice().intValue() == 1) {
                for (int i = 0; i < ccList.size()-1; i++) {
                    joiner.add(ccList.get(i).getItemName());
                    if (i == 0) {
                        value = StringUtil.isNotBlank(ii.getBatteryModel())
                                ? ii.getBatteryModel() : "";
                        joiner.add("【" + value + "】");
                    } else if (i == 1 && StringUtil.isNotEmpty(ii.getBatterySit())) {
                        value = Integer.parseInt(ii.getBatterySit()) == 1 ? temps[0] : temps[1];
                        joiner.add("【" + value + "】");
                    } else {
                        joiner.add("【】");
                    }
                }
                // UPS主机
            } else if (ii.getChangeDevice().intValue() == 2) {
                for (int i = 0; i < ccList.size()-1; i++) {
                    joiner.add(ccList.get(i).getItemName());
                    if (i == 0 && StringUtil.isNotBlank(ii.getOrElectricity())) {
                        value = Integer.parseInt(ii.getOrElectricity()) == 1 ? temps[0] : temps[1];
                        joiner.add("【" + value + "】");
                    } else {
                        joiner.add("【】");
                    }
                }
                // UPS监控盒子
            } else if (ii.getChangeDevice().intValue() == 3) {
                for (int i = 0; i < ccList.size()-1; i++) {
                    joiner.add(ccList.get(i).getItemName());
                    if (i == 0 && StringUtil.isNotBlank(ii.getOrNetwork())) {
                        value = Integer.parseInt(ii.getOrNetwork()) == 1 ? temps[0] : temps[1];
                        joiner.add("【" + value + "】");
                    } else {
                        joiner.add("【】");
                    }
                }
                // 交换机
            } else if (ii.getChangeDevice().intValue() == 4) {
                for (int i = 0; i < ccList.size()-1; i++) {
                    joiner.add(ccList.get(i).getItemName());
                    if (i == 0 && StringUtil.isNotBlank(ii.getUpOrNetwork())) {
                        value = Integer.parseInt(ii.getUpOrNetwork()) == 1 ? temps[0] : temps[1];
                        joiner.add("【" + value + "】");
                    } else {
                        joiner.add("【】");
                    }
                }
                // STS
            } else if (ii.getChangeDevice().intValue() == 5) {
                for (int i = 0; i < ccList.size()-1; i++) {
                    joiner.add(ccList.get(i).getItemName());
                    switch (i) {
                        case 0:
                            if (StringUtil.isNotBlank(ii.getOrWiring())) {
                                value = Integer.parseInt(ii.getOrWiring()) == 1 ? temps[0] : temps[1];
                                joiner.add("【" + value + "】");
                            } else {
                                joiner.add("【】");
                            }
                            break;
                        case 1:
                            if (StringUtil.isNotBlank(ii.getOrOutput())) {
                                value = Integer.parseInt(ii.getOrOutput()) == 1 ? temps[0] : temps[1];
                                joiner.add("【" + value + "】");
                            } else {
                                joiner.add("【】");
                            }
                            break;
                        case 2:
                            if (StringUtil.isNotBlank(ii.getOrTest())) {
                                value = Integer.parseInt(ii.getOrTest()) == 1 ? temps[0] : temps[1];
                                joiner.add("【" + value + "】");
                            } else {
                                joiner.add("【】");
                            }
                            break;
                        default:
                            joiner.add("【】");
                    }
                }
                // 无线网AP
            } else {
                for (int i = 0; i < ccList.size()-1; i++) {
                    joiner.add(ccList.get(i).getItemName());
                    if (i == 0 && StringUtil.isNotBlank(ii.getActiveDisable())) {
                        value = Integer.parseInt(ii.getActiveDisable()) == 1 ? temps[0] : temps[1];
                        joiner.add("【" + value + "】");
                    } else {
                        joiner.add("【】");
                    }
                }
            }
            ii.setOptionsName(joiner.toString());

            ii.setStartDate(DateUtil.dateToStr(ii.getCreateDate(), "yyyy-MM-dd"));
            ii.setLastMofiyDateStr(DateUtil.dateToStr(ii.getLastMofiyDate(), "yyyy-MM-dd"));
            if(StringUtil.isNotEmpty(ii.getDataIfNormal())) {
                ii.setDataIfNormalStr(ii.getDataIfNormal().intValue() == 1 ? temps[0] : temps[1]);
            }
            //监控变更确认AB岗
            if(StringUtil.isNotEmpty(ii.getMonitoringConfirm())) {
                String monitoringConfirm = commEmployeeService.getBadgeAndName(ii.getMonitoringConfirm());
                ii.setMonitoringConfirm(monitoringConfirm);
            }
            //出入库确认AB岗
            if(StringUtil.isNotEmpty(ii.getForConfirm())) {
                String forConfirm = commEmployeeService.getBadgeAndName(ii.getForConfirm());
                ii.setForConfirm(forConfirm);
            }
            //CMDB确认AB
            if(StringUtil.isNotEmpty(ii.getCmdbConfirm())) {
                String cmdbConfirm = commEmployeeService.getBadgeAndName(ii.getCmdbConfirm());
                ii.setCmdbConfirm(cmdbConfirm);
            }
            news.add(ii);
        });
        return news;
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmNetworkStickDto dto = new PpmNetworkStickDto();
        dto.setInstanceId(bps.getProcessInstId());
        dto.setState(3);
        AssertUtil.notNull(dto.getInstanceId(), "[InstanceId]不能为空");
        this.updatePpmNetworkStick(dto);
    }

    /**
     * 适配dto
     * @param ppmNingGuanFlowQueryDto 对象
     * @return ppmNetworkStickDto 对象
     */
    private PpmNetworkStickDto adaptQueryDto(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto) {
        ppmNingGuanFlowQueryDto.setProcessType(null);
        PpmNetworkStickDto dto = JsonUtil.jsonToClass(ppmNingGuanFlowQueryDto, PpmNetworkStickDto.class);
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
}
