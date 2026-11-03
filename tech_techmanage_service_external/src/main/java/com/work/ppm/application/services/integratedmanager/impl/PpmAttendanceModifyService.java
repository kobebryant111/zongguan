package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmAttendanceModify;
import com.work.ppm.application.dto.integratedmanager.PpmAttendanceModifyDto;
import com.work.ppm.application.services.integratedmanager.IPpmAttendanceModifyService;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤修改流程
 */
@Service("ppmAttendanceModifyService")
public class PpmAttendanceModifyService extends BaseDaoSupper implements IPpmAttendanceModifyService, ICommonDeleteFlowService {
    /**
      * 实体类路径
      */
    private static String PPMATTENDANCEMODIFY = "com.work.ppm.application.model.integratedmanager.PpmAttendanceModify";
    /**
     * 流程实例名称
     */
    private static String FLOW_NAME = "com.work.ppm.bps.attendanceModify";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmAttendanceModifyDto ppmAttendanceModifyDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmAttendanceModifyDto,PpmAttendanceModifyDto.class);
        myBatisDao.selectPager(PPMATTENDANCEMODIFY, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmAttendanceModify(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        PpmAttendanceModify ppmAttendanceModify = JsonUtil.jsonToClass(ppmAttendanceModifyDto, PpmAttendanceModify.class);
        Long id = getNextSequence(PpmAttendanceModify.class);

        // 如果需要请set 主键id
        ppmAttendanceModify.setId(id);
        myBatisDao.executionPojo(PPMATTENDANCEMODIFY, ppmAttendanceModify, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmAttendanceModifyDto queryDetail(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        PpmAttendanceModify ppmAttendanceModify = JsonUtil.jsonToClass(ppmAttendanceModifyDto, PpmAttendanceModify.class);
        PpmAttendanceModify data = myBatisDao.selectClass(PPMATTENDANCEMODIFY, ppmAttendanceModify,
        "queryDetail");
        PpmAttendanceModifyDto item = JsonUtil.jsonToClass(data, PpmAttendanceModifyDto
        .class);
        return item;
    }

    @Override
    public int updatePpmAttendanceModify(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        PpmAttendanceModify ppmAttendanceModify = JsonUtil.jsonToClass(ppmAttendanceModifyDto, PpmAttendanceModify.class);
        return myBatisDao.executionPojo(PPMATTENDANCEMODIFY, ppmAttendanceModify, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
    @Transactional("defaultTx")
    @Override
    public ApiResult saveFlow(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        String instanceId = ppmAttendanceModifyDto.getInstanceId();
        // 更新
        Date date = new Date();
        if(StringUtil.isNotBlank(instanceId)) {
            ppmAttendanceModifyDto.setAddDate(date);
            updatePpmAttendanceModify(ppmAttendanceModifyDto);
        } else {
            String xid = BpsUtil.transactionBegin(ppmAttendanceModifyDto.getBadge());
            try{
                // 二级部主持工作经理
                CommEmployeeDto employee = UsersUtil.getTwoWorkManager(ppmAttendanceModifyDto.getDepId());
                BpsWorkFlowDto bps = new BpsWorkFlowDto();
                Map<String, String> bpsMap = new HashMap<>();
                bpsMap.put("users", employee.getBadge());
                bpsMap.put("status", "-1");


                bps.setRelaData(bpsMap);
                bps.setProcessDefName(FLOW_NAME);
                bps.setProcessInstDesc("【外包人员考勤修改】申请");
                bps.setProcessInstName("【"+ ppmAttendanceModifyDto.getUserName() +"】外包人员考勤修改");
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setUserId(ppmAttendanceModifyDto.getBadge());
                bps.setFlowUrl("attendanceModify");

                instanceId = BpsUtil.createProcessInstance(bps, xid);
                // 添加
                ppmAttendanceModifyDto.setIsdel(0);
                ppmAttendanceModifyDto.setInstanceId(instanceId);
                ppmAttendanceModifyDto.setAddDate(date);
                this.addPpmAttendanceModify(ppmAttendanceModifyDto);

                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            }catch (Exception e){
                BpsUtil.transactionRollback(ppmAttendanceModifyDto.getBadge(), xid);
                throw new BizException(e);
            }
        }
        String msg = BpsUtil.queryProcessInfo(instanceId);
        ApiResult result = new ApiResult();
        result.setResults(instanceId);
        result.setMessage(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public String liuZhuan(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        BpsWorkFlowDto bps = ppmAttendanceModifyDto.getBps();
        if(null == bps) {
            bps = new BpsWorkFlowDto();
            bps.setUserId(ppmAttendanceModifyDto.getBadge());
        }
        String xid = "";
        Date date = new Date();
        try{
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 二级部主持工作经理
            CommEmployeeDto employee = UsersUtil.getTwoWorkManager(ppmAttendanceModifyDto.getDepId());
            Map<String, String> bpsMap = new HashMap<>();
            bpsMap.put("users", employee.getBadge());
            bpsMap.put("status", "-1");
            bps.setRelaData(bpsMap);
            bps.setProcessDefName(FLOW_NAME);
            bps.setProcessInstDesc("【外包人员考勤修改】申请");
            bps.setProcessInstName("【"+ ppmAttendanceModifyDto.getUserName() +"】外包人员考勤修改");
            bps.setApplydesc(bps.getProcessInstDesc());
            bps.setConclusion("01");
            bps.setUserId(ppmAttendanceModifyDto.getBadge());
            bps.setFlowUrl("attendanceModify");
            ppmAttendanceModifyDto.setAddDate(date);
            // 直接提交
            if(!StringUtil.isNotBlank(bps.getProcessInstId())) {
                String insid = BpsUtil.createProcessAndFinishFirst(bps, xid);
                bps.setProcessInstId(insid);
                ppmAttendanceModifyDto.setInstanceId(insid);
                ppmAttendanceModifyDto.setIsdel(0);
                this.addPpmAttendanceModify(ppmAttendanceModifyDto);
            } else {
                this.updatePpmAttendanceModify(ppmAttendanceModifyDto);
                // 保存后提交
                BpsUtil.finshWorkItem(bps, xid);
            }
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Transactional("defaultTx")
    @Override
    public String hostManagerReview(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        BpsWorkFlowDto bps = ppmAttendanceModifyDto.getBps();
        String xid = "";
        try{
            xid = BpsUtil.transactionBegin(bps.getUserId());
            Map<String, String > bpsMap = new HashMap<>();
            bps.setApplydesc("二级部主持工作经理审核");
            // 同意
            if("01".equals(bps.getConclusion())) {
                ppmAttendanceModifyDto.setInstanceId(bps.getProcessInstId());
                ppmAttendanceModifyDto = this.queryDetail(ppmAttendanceModifyDto);
                // 分管总经理
                String userId = UsersUtil.getDeputyDirectorGeneral(ppmAttendanceModifyDto.getDepId());
                bpsMap.put("users", userId);
                bpsMap.put("status", "-1");
            } else {    //  不同意
                bpsMap.put("status", "02");
            }
            bps.setRelaData(bpsMap);
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }

        return BpsUtil.queryProcessInfo(bps);
    }

    @Transactional("defaultTx")
    @Override
    public String generalMnager(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        BpsWorkFlowDto bps = ppmAttendanceModifyDto.getBps();
        String xid = "";
        try{
            xid = BpsUtil.transactionBegin(bps.getUserId());
            Map<String, String > bpsMap = new HashMap<>();
            bps.setApplydesc("分管总经理审批");
            // 同意
            if("01".equals(bps.getConclusion())) {
                // 综合部 - 黄添益
                String userId = eosDictEntryService.getTypeidForDetail("NEW_ATTENDANCE_MODIFY","ZHHTY").getDictname();
                bpsMap.put("users", userId);
                bpsMap.put("status", "-1");
            } else {    //  不同意
                bpsMap.put("status", "02");
            }
            bps.setRelaData(bpsMap);
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Transactional("defaultTx")
    @Override
    public String generalDept(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        BpsWorkFlowDto bps = ppmAttendanceModifyDto.getBps();
        String xid = "";
        try{
            xid = BpsUtil.transactionBegin(bps.getUserId());
            Map<String, String > bpsMap = new HashMap<>();
//            bps.setApplydesc("研发管理部确认");
            bps.setApplydesc("金融科技部总经理室确认");
            // 同意
            if("01".equals(bps.getConclusion())) {
                bpsMap.put("status", "-1");
            } else {    //  不同意
                bpsMap.put("status", "02");
            }
            bps.setRelaData(bpsMap);
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    public List<PpmAttendanceModifyDto> exportExcel(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmAttendanceModifyDto, PpmAttendanceModifyDto.class);
        // queryPagerList
        List<PpmAttendanceModify> list = myBatisDao.selectListT(PPMATTENDANCEMODIFY, map, "queryPagerList");
        List<PpmAttendanceModifyDto> dtos = JsonUtil.jsonToList(list, PpmAttendanceModifyDto.class);
        dtos.stream().forEach(ii -> {
            ii.setAddDateStr(DateUtil.dateToStr(ii.getAddDate(),"yyyy-MM-dd"));
        });
        return dtos;
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmAttendanceModifyDto dto = new PpmAttendanceModifyDto();
        dto.setInstanceId(bps.getProcessInstId());
        dto.setIsdel(1);
        updatePpmAttendanceModify(dto);
    }
}
