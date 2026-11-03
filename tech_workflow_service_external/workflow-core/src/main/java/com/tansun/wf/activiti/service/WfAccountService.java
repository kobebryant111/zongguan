package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.model.*;
import com.tansun.wf.activiti.util.DateUtil;
import com.tansun.wf.platform.mapper.WfAccountMapper;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.model.UserOrgRoleInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作量统计service
 */
@Service
public class WfAccountService {

    @Autowired
    private WfAccountMapper wfAccountMapper;

    @Autowired
    private EditorServiceProxyService editorServiceProxyService;

    /**
     * 工作量统计
     */
    @Transactional
    public List<WorkloadStatisticsDto> workloadStatistics(WorkloadStatisticsQo qo) {
        // 参数验证
        if(qo.getQtype()>3 || qo.getQtype()<1){
            throw new RuntimeException("查询类型参数必须是：1总体 2按年 3按月");
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            throw new RuntimeException("必须选择起始/终止时间点");
        }
        if(qo.getEntity()==null){
            // 构建顶层机构树数据（考验内存的时候到了，gc爆发点）
            EditorInfo editor = new EditorInfo();
            EditorBean es = editorServiceProxyService.findUserOrgRoleList(editor);
            List<UserOrgRoleInfo> list = es.getUserOrgRoleTree();
            List<WorkloadStatisticsVo> wsList = JSON.parseArray(JSON.toJSONString(list), WorkloadStatisticsVo.class);
            Map<String, WorkloadStatisticsVo> esMap = new HashMap<>();
            final WorkloadStatisticsVo[] superVo = new WorkloadStatisticsVo[1];
            wsList.forEach(ws -> {
                if(esMap.get(ws.getId()) == null || StringUtils.isBlank(esMap.get(ws.getId()).getPid())) {
                    esMap.put(ws.getId(), ws);
                }
                if(ws.getId().equals("#org")){
                    superVo[0] = ws;
                }
            });
            for(WorkloadStatisticsVo ws : esMap.values()){
                if(StringUtils.isBlank(ws.getPid())){
                    if(ws.getType().equals(WorkloadStatisticsVo.TYPE_ORG)) {
                        if (superVo[0].getSubs() == null) {
                            superVo[0].setSubs(new ArrayList<>());
                        }
                        superVo[0].getSubs().add(ws);
                    }
                }else{
                    WorkloadStatisticsVo sp = esMap.get(ws.getPid());
                    if(sp!=null){
                        if (sp.getSubs() == null) {
                            sp.setSubs(new ArrayList<>());
                        }
                        sp.getSubs().add(ws);
                    }
                }
            }
            qo.setEntity(superVo[0]);
        }
        Date searchTimeBegin = DateUtil.getYearFirstTime(qo.getSearchTimeBegin());
        Date searchTimeEnd = DateUtil.getYearLastTime(qo.getSearchTimeEnd());
        // 开始处理
        List<WorkloadStatisticsDto> res;
        if(qo.getQtype() != 1) {
            // 拉平节点为一个一维数组
            WorkloadStatisticsTypesVo typesVo = levelingTree(qo.getEntity());
            if(typesVo.getUserLoginNames().isEmpty()){
                throw new RuntimeException("参数缺失，没有解析到机构/部门下的用户数据");
            }
            typesVo.setSearchTimeBegin(searchTimeBegin);
            typesVo.setSearchTimeEnd(searchTimeEnd);
            typesVo.setQtype(qo.getQtype());
            // 创建数
            List<WorkloadStatisticsDto> creates = wfAccountMapper.workloadStatisticsCreateByDate(typesVo);
            // 处理数
            List<WorkloadStatisticsDto> handles = wfAccountMapper.workloadStatisticsHandleByDate(typesVo);
            // dto合并
            List<String> times = splitTimeSlice(qo.getQtype(), searchTimeBegin, searchTimeEnd);
            Map<String, WorkloadStatisticsDto> maps = new TreeMap<>();
            times.forEach(t -> maps.put(t, new WorkloadStatisticsDto(t)));
            creates.forEach(c -> maps.get(c.getName()).setCreateCnt(c.getCreateCnt()));
            handles.forEach(h -> maps.get(h.getName()).setHandleCnt(h.getHandleCnt()));
            res = new ArrayList<>(maps.values());
        } else {
            // 拉平n个一级子节点树结构至n个一维数组
            res = new ArrayList<>();
            List<WorkloadStatisticsVo> subs = qo.getEntity().getSubs();
            if(subs==null){
                subs = Collections.singletonList(qo.getEntity());
            }
            for (WorkloadStatisticsVo sub : subs) {
                WorkloadStatisticsTypesVo typesVo = levelingTree(sub);
                if(typesVo.getUserLoginNames().isEmpty()){
                    throw new RuntimeException("参数缺失，没有解析到机构/部门下的用户数据");
                }
                typesVo.setSearchTimeBegin(searchTimeBegin);
                typesVo.setSearchTimeEnd(searchTimeEnd);
                WorkloadStatisticsDto dto = new WorkloadStatisticsDto(sub, qo);
                dto.setCreateCnt(wfAccountMapper.workloadStatisticsCreate(typesVo));
                dto.setHandleCnt(wfAccountMapper.workloadStatisticsHandle(typesVo));
                res.add(dto);
            }
        }
        return res;
    }

    // 时间切片 2按年切片 !2按月切片
    private List<String> splitTimeSlice(int qtype, Date begin, Date end) {
        List<String> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(qtype==2?"yyyy":"yyyy-MM");
        int addType = qtype==2? Calendar.YEAR:Calendar.MONTH;
        while(begin.compareTo(end) <= 0){
            res.add(sdf.format(begin));
            Calendar cal = Calendar.getInstance();
            cal.setTime(begin);
            cal.add(addType, 1);
            begin = cal.getTime();
        }
        return res;
    }

    // 树结构拉平
    private WorkloadStatisticsTypesVo levelingTree(WorkloadStatisticsVo sub) {
        // 目前场景只用得上loginName，其它类型作预留
        List<String> userLoginNames = new ArrayList<>();
        List<String> roleCodes = new ArrayList<>();
        List<String> orgCodes = new ArrayList<>();
        List<String> deptCodes = new ArrayList<>();
        Queue<WorkloadStatisticsVo> queue = new LinkedList<>();
        queue.offer(sub);
        WorkloadStatisticsVo temp;
        while(!queue.isEmpty()){
            temp = queue.poll();
            switch (temp.getType()){
                case WorkloadStatisticsVo.TYPE_USER:
                    userLoginNames.add(temp.getId()); break;
                case WorkloadStatisticsVo.TYPE_ROLE:
                    roleCodes.add(temp.getId()); break;
                case WorkloadStatisticsVo.TYPE_ORG:
                    orgCodes.add(temp.getId()); break;
                case WorkloadStatisticsVo.TYPE_DEPT:
                    deptCodes.add(temp.getId()); break;
            }
            if(temp.getSubs() != null) {
                for (WorkloadStatisticsVo vo : temp.getSubs()) {
                    queue.offer(vo);
                }
            }
        }
        return new WorkloadStatisticsTypesVo(userLoginNames, roleCodes, orgCodes, deptCodes);
    }

    /**
     * 流程执行时间统计
     */
    @Transactional
    public List<WfExecutionTimeDto> executionTime(WfExecutionTimeQo qo) {
        // 参数验证
        if(qo.getQtype()>3 || qo.getQtype()<1){
            throw new RuntimeException("查询类型参数必须是：1总体 2按流程版本（一次只查一个流程） 3按月（一次只查一个流程的一个版本）");
        }
        if(qo.getQtype()==2 && qo.getEntitys().size()!=1){
            throw new RuntimeException("按流程版本查询，一次只查一个流程");
        }
        if(qo.getQtype()==3){
            if(qo.getEntity()==null && qo.getEntitys().size()!=1) {
                throw new RuntimeException("按月查询，一次只查一个流程的一个版本");
            }
            if(qo.getEntitys().get(0).getModelVersion()==null){
                throw new RuntimeException("按月查询，必须要选择流程版本");
            }
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            throw new RuntimeException("必须选择起始/终止时间点");
        }
        qo.setSearchTimeBeginDate(DateUtil.getYearFirstTime(qo.getSearchTimeBegin()));
        qo.setSearchTimeEndDate(DateUtil.getYearLastTime(qo.getSearchTimeEnd()));
        // 开始处理
        Map<String, WfExecutionTimeDto> temp = new TreeMap<>();
        if(qo.getQtype()==1){
            qo.getEntitys().forEach(e -> temp.put(e.getModelKey(),
                    new WfExecutionTimeDto(e.getModelName(), e.getModelKey(), e.getModelVersion())));
            List<WfExecutionTimeDto> ret = wfAccountMapper.executionTime(qo);
            ret.forEach(r -> WfExecutionTimeDto.businessCloneDto(temp.get(r.getModelKey()), r));
        }else if (qo.getQtype()==2) {
            wfAccountMapper.queryWfVersionInfo(qo.getEntitys().get(0).getModelKey())
                    .forEach(r -> temp.put(r.getModelVersion(), new WfExecutionTimeDto(r)));
            List<WfExecutionTimeDto> ret = wfAccountMapper.executionTimeByVersoin(qo);
            ret.forEach(r -> WfExecutionTimeDto.businessCloneDto(temp.get(r.getModelVersion()), r));
        }else {
            if(qo.getEntity()==null) {
                qo.setEntity(qo.getEntitys().get(0));
            }
            WfVersionVo e = qo.getEntity();
            splitTimeSlice(qo.getQtype(), qo.getSearchTimeBeginDate(), qo.getSearchTimeEndDate())
                    .forEach(t -> temp.put(t, new WfExecutionTimeDto(t, e.getModelKey(), e.getModelVersion())));
            List<WfExecutionTimeDto> ret = wfAccountMapper.executionTimeByMonth(qo);
            ret.forEach(r -> WfExecutionTimeDto.businessCloneDto(temp.get(r.getName()), r));
        }
        return new ArrayList<>(temp.values());
    }

    /**
     * 活动实例统计
     */
    @Transactional
    public List<WfActivityTimeDto> activityTime(WfActivityTimeQo qo) {
        // 参数验证
        if(qo.getQtype()>3 || qo.getQtype()<2){
            throw new RuntimeException("查询类型参数必须是：2按流程版本切片 3按月切片（一次只查一个流程版本的一个活动）");
        }
        if(qo.getModelKey()==null || qo.getModelVersion()==null){
            throw new RuntimeException("流程key和流程版本不可为空");
        }
        if(qo.getQtype()==3 && qo.getActId()==null){
            throw new RuntimeException("按月查询，活动ID不可为空");
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            throw new RuntimeException("必须选择起始/终止时间点");
        }
        qo.setSearchTimeBeginDate(DateUtil.getYearFirstTime(qo.getSearchTimeBegin()));
        qo.setSearchTimeEndDate(DateUtil.getYearLastTime(qo.getSearchTimeEnd()));
        qo.setProcDefIdLike(qo.getModelKey()+":"+qo.getModelVersion()+":%");
        List<WfActivityTimeDto> ret;
        // 开始处理
        if(qo.getQtype()==2){
            // 没办法获取流程版本下所有活动（没有执行到的活动没地方查）
            ret = wfAccountMapper.activityTime(qo);
            ret.forEach(r -> r.setModelVersion(qo.getModelVersion()));
        }else {
            // 按月切片查询
            Map<String, WfActivityTimeDto> temp = new TreeMap<>();
            splitTimeSlice(qo.getQtype(), qo.getSearchTimeBeginDate(), qo.getSearchTimeEndDate())
                    .forEach(s -> temp.put(s, new WfActivityTimeDto(s, qo.getModelKey(), qo.getModelName(), qo.getActId())));
            List<WfActivityTimeDto> list = wfAccountMapper.activityTimeByMonth(qo);
            list.forEach(r -> WfActivityTimeDto.businessCloneDto(temp.get(r.getName()), r));
            ret = new ArrayList<>(temp.values());
            String actName = list.isEmpty()?null:list.get(0).getActName();
            ret.forEach(r -> {
                r.setModelVersion(qo.getModelVersion());
                r.setActName(actName);
            });
        }
        return ret;
    }

    /**
     * 实例执行状态统计
     */
    @Transactional
    public List<WfExecutionStatDto> executionStat(WfExecutionStatQo qo) throws ParseException {
        // 参数验证
        if(qo.getQtype()<1 || qo.getQtype()>3){
            throw new RuntimeException("查询类型参数必须是：1总体 2按流程版本（一次只查一个流程） 3按月（一次只查一个流程的一个版本）");
        }
        if(qo.getQtype()==2 && qo.getEntitys().size()!=1){
            throw new RuntimeException("按流程版本查询，一次只查一个流程");
        }
        if(qo.getQtype()==3){
            if(qo.getEntitys().get(0).getModelVersion()==null){
                throw new RuntimeException("按月查询，必须要选择流程版本");
            }
            if(qo.getEntity()==null && qo.getEntitys().size()!=1) {
                throw new RuntimeException("按月查询，一次只查一个流程的一个版本");
            }
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            throw new RuntimeException("必须选择起始/终止时间点");
        }
        qo.setSearchTimeBeginDate(DateUtil.getYearFirstTimeString(qo.getSearchTimeBegin()));
        qo.setSearchTimeEndDate(DateUtil.getYearLastTimeString(qo.getSearchTimeEnd()));
        // 开始处理
        Map<String, WfExecutionStatDto> temp = new TreeMap<>();
        if(qo.getQtype()==1){
            qo.getEntitys().forEach(e -> temp.put(e.getModelKey(),
                    new WfExecutionStatDto(e.getModelName(), e.getModelKey(), e.getModelVersion())));
            List<WfExecutionStatDto> ret = wfAccountMapper.executionStat(qo);
            ret.forEach(r -> WfExecutionStatDto.businessCloneDto(temp.get(r.getModelKey()), r));
        }else if (qo.getQtype()==2) {
            wfAccountMapper.queryWfVersionInfo(qo.getEntitys().get(0).getModelKey())
                    .forEach(r -> temp.put(r.getModelVersion(), new WfExecutionStatDto(r)));
            List<WfExecutionStatDto> ret = wfAccountMapper.executionStatByVersoin(qo);
            ret.forEach(r -> WfExecutionStatDto.businessCloneDto(temp.get(r.getModelVersion()), r));
        }else {
            if(qo.getEntity()==null) {
                qo.setEntity(qo.getEntitys().get(0));
            }
            WfVersionVo e = qo.getEntity();
            qo.setProcDefIdLike(e.getModelKey()+":"+e.getModelVersion()+":%");
            splitTimeSlice(qo.getQtype(), DateUtil.stringToDate(qo.getSearchTimeBeginDate()), DateUtil.stringToDate(qo.getSearchTimeEndDate()))
                    .forEach(t -> temp.put(t, new WfExecutionStatDto(t, e.getModelKey(), e.getModelVersion())));
            List<WfExecutionStatDto> ret = wfAccountMapper.executionStatByMonth(qo);
            ret.forEach(r -> WfExecutionStatDto.businessCloneDto(temp.get(r.getName()), r));
        }
        return new ArrayList<>(temp.values());
    }

    /**
     * 超时率统计
     */
    @Transactional
    public List<WfTimeoutRateDto> timeoutRate(WfTimeoutRateQo qo) {
        // 参数验证
        if(qo.getQtype()<1 || qo.getQtype()>3){
            throw new RuntimeException("查询类型参数必须是：1总体 2按流程版本 3按月");
        }
        if(qo.getQtype()==2 && qo.getEntitys().size()!=1){
            throw new RuntimeException("按流程版本查询，一次只查一个流程");
        }
        if(qo.getQtype()==3){
            if(qo.getEntitys().size()!=1) {
                throw new RuntimeException("按月查询，一次只查一个流程的一个版本");
            }
            if(qo.getEntitys().get(0).getModelVersion()==null){
                throw new RuntimeException("按月查询，必须要选择流程版本");
            }
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            throw new RuntimeException("必须选择起始/终止时间点");
        }
        qo.setSearchTimeBeginDate(DateUtil.getYearFirstTime(qo.getSearchTimeBegin()));
        qo.setSearchTimeEndDate(DateUtil.getYearLastTime(qo.getSearchTimeEnd()));
        // 开始处理
        Map<String, WfTimeoutRateDto> temp = new TreeMap<>();
        if(qo.getQtype()==1){
            qo.getEntitys().forEach(e -> temp.put(e.getModelKey(),
                    new WfTimeoutRateDto(e.getModelName(), e.getModelKey(), e.getModelVersion())));
            List<WfTimeoutRateDto> ret = wfAccountMapper.instTimeoutRate(qo);
            timeoutRateInst(ret, temp, "key");
            ret = wfAccountMapper.taskTimeoutRate(qo);
            timeoutRateAct(ret, temp, "key");
        }else if(qo.getQtype()==2){
            wfAccountMapper.queryWfVersionInfo(qo.getEntitys().get(0).getModelKey())
                    .forEach(r -> temp.put(r.getModelVersion(), new WfTimeoutRateDto(r)));
            List<WfTimeoutRateDto> ret = wfAccountMapper.instTimeoutRateVersion(qo);
            timeoutRateInst(ret, temp, "version");
            ret = wfAccountMapper.taskTimeoutRateVersion(qo);
            timeoutRateAct(ret, temp, "version");
        }else{
            if(qo.getEntity()==null) {
                qo.setEntity(qo.getEntitys().get(0));
            }
            WfVersionVo e = qo.getEntity();
            splitTimeSlice(qo.getQtype(), qo.getSearchTimeBeginDate(), qo.getSearchTimeEndDate())
                    .forEach(t -> temp.put(t, new WfTimeoutRateDto(t, e.getModelKey(), e.getModelVersion())));
            List<WfTimeoutRateDto> ret = wfAccountMapper.instTimeoutRateMonth(qo);
            timeoutRateInst(ret, temp, "name");
            ret = wfAccountMapper.taskTimeoutRateMonth(qo);
            timeoutRateAct(ret, temp, "name");
        }
        return new ArrayList<>(temp.values());
    }

    private void timeoutRateInst(List<WfTimeoutRateDto> ret, Map<String, WfTimeoutRateDto> temp, String kvn){
        ret.forEach(r -> {
            if(r.getInstCompleted() + r.getInstTimeout() == 0){
                r.setInstRate("0.00%");
            }else{
                BigDecimal v1 = new BigDecimal(r.getInstTimeout()*100);
                BigDecimal v2 = new BigDecimal(r.getInstCompleted()+r.getInstTimeout());
                r.setInstRate(v1.divide(v2, 2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            }
            String key = kvn.equals("key")?r.getModelKey():(kvn.equals("version")?r.getModelVersion():r.getName());
            WfTimeoutRateDto.businessCloneDto(temp.get(key), r);
        });
    }

    private void timeoutRateAct(List<WfTimeoutRateDto> ret, Map<String, WfTimeoutRateDto> temp, String kvn){
        ret.forEach(r -> {
            if(r.getActCompleted() + r.getActTimeout() == 0){
                r.setActRate("0.00%");
            }else{
                BigDecimal v1 = new BigDecimal(r.getActTimeout()*100);
                BigDecimal v2 = new BigDecimal(r.getActCompleted()+r.getActTimeout());
                r.setActRate(v1.divide(v2, 2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            }
            String key = kvn.equals("key")?r.getModelKey():(kvn.equals("version")?r.getModelVersion():r.getName());
            WfTimeoutRateDto.businessCloneDto(temp.get(key), r);
        });
    }

    /**
     * 超时时长统计
     */
    @Transactional
    public PageInfo<WfTimeoutDurationDto> timeoutDuration(WfTimeoutDurationQo qo) {
        // 参数验证
        if(qo.getQtype()<1 || qo.getQtype()>2){
            throw new RuntimeException("查询类型参数必须是：1总体 2按流程版本 3按月");
        }
        if(qo.getEntity()==null || qo.getEntity().getModelKey()==null){
            throw new RuntimeException("必须选择查询的流程");
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            if(qo.getSearchTimeDetail()==null && qo.getEntity().getModelVersion()==null) {
                throw new RuntimeException("必须选择起始/终止时间点或者指定具体年/月与版本号");
            }
            String[] begin = qo.getSearchTimeDetail().split("-");
            String[] end = qo.getSearchTimeDetail().split("-");
            qo.setSearchTimeBeginDate(DateUtil.getMonthFirstTime(begin[0], begin[1]));
            qo.setSearchTimeEndDate(DateUtil.getMonthLastTime(end[0], end[1]));
        } else{
            qo.setSearchTimeBeginDate(DateUtil.getYearFirstTime(qo.getSearchTimeBegin()));
            qo.setSearchTimeEndDate(DateUtil.getYearLastTime(qo.getSearchTimeEnd()));
        }
        if(qo.getEntity().getModelVersion()==null){
            qo.setProcDefIdLike(qo.getEntity().getModelKey()+":%");
        } else {
            qo.setProcDefIdLike(qo.getEntity().getModelKey()+":"+qo.getEntity().getModelVersion()+":%");
        }
        // 开始查询
        PageHelper.startPage(qo.getPage(), qo.getPageSize());
        List<WfTimeoutDurationDto> list;
        if(qo.getQtype() == 1) {
            // 按实例查询
            list = wfAccountMapper.queryTimeoutDuration(qo);
        }else{
            // 按任务查询
            list = wfAccountMapper.queryActTimeoutDuration(qo);
        }
        list.forEach(l -> {
            l.setModelName(qo.getEntity().getModelName());
            l.setModelKey(qo.getEntity().getModelKey());
            l.setModelVersion(qo.getEntity().getModelVersion());
        });
        PageInfo<WfTimeoutDurationDto> retPage=new PageInfo<>(list);
        PageInfo<WfTimeoutDurationDto>ret=new PageInfo<>();
        ret.setTotal(retPage.getTotal());
        ret.setList(retPage.getList());
        return ret;
    }

    /**
     * 退回率统计
     */
    @Transactional
    public List<WfBackTaskDto> backTask(WfBackTaskQo qo) {
        // 参数验证
        if(qo.getQtype()<1 || qo.getQtype()>2){
            throw new RuntimeException("查询类型参数必须是：1按流程版本分片统计 2按流程版本下活动分片统计");
        }
        if(qo.getEntity()==null || qo.getEntity().getModelKey()==null){
            throw new RuntimeException("必须选择查询的流程");
        }
        if(qo.getSearchTimeBegin()==null || qo.getSearchTimeEnd()==null){
            throw new RuntimeException("必须选择起始/终止时间点");
        }
        qo.setSearchTimeBeginDate(DateUtil.getYearFirstTime(qo.getSearchTimeBegin()));
        qo.setSearchTimeEndDate(DateUtil.getYearLastTime(qo.getSearchTimeEnd()));
        // 开始查询
        qo.setProcDefIdLike(qo.getEntity().getModelKey()+":"+qo.getEntity().getModelVersion()+":%");
        List<WfBackTaskDto> list;
        if(qo.getQtype() == 1) {
            // 按实例查询
            list = wfAccountMapper.instBackTask(qo);
        }else{
            // 按任务查询
            list = wfAccountMapper.actBackTask(qo);
        }
        list.forEach(r -> {
            if(r.getStartCnt() == 0){
                r.setBackRate("0.00%");
            }else{
                BigDecimal v1 = new BigDecimal(r.getBackCnt()*100);
                BigDecimal v2 = new BigDecimal(r.getStartCnt());
                r.setBackRate(v1.divide(v2, 2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            }
        });
        return list;
    }

    /** 流程版本统计 */
    @Transactional
    public PageInfo<WfProVersionDto> proVersion(WfProVersionQo qo) {
        // 参数处理
        if(qo.getModelNameLike()!=null){
            qo.setModelNameLike("%"+qo.getModelNameLike()+"%");
        }
        // 开始查询
        PageHelper.startPage(qo.getPage(), qo.getPageSize());
        List<WfProVersionDto> list = wfAccountMapper.proVersion(qo);
        // 版本更新率计算
        list.forEach(r -> {
            if (r.getProCreateDays() == 0) {
                r.setProCreateDays(1);
            }
            BigDecimal v1 = new BigDecimal(r.getVersionCnt());
            BigDecimal v2 = new BigDecimal(r.getProCreateDays());
            r.setProUpdateRate(v1.divide(v2, 2, BigDecimal.ROUND_HALF_UP).toString());
        });
        PageInfo<WfProVersionDto> retPage = new PageInfo<>(list);
        PageInfo<WfProVersionDto> ret = new PageInfo<>();
        ret.setTotal(retPage.getTotal());
        ret.setList(retPage.getList());
        return ret;
    }
}
