package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessApplyDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssess;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IOutsrcAttrChgApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcEmpAssessService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 外包人员考核 服务实现类
 * </p>
 *
 * @since 2023-01-03
 */

@Slf4j
@RequiredArgsConstructor
@Service("outsrcEmpAssessService")
public class OutsrcEmpAssessService extends BaseDaoSupper implements IOutsrcEmpAssessService {

    /**
     * 实体类路径
     */
    private static String OUTSRCINGASSESS= "com.work.ppm.application.model.vendormanager.OutsrcEmpAssess";
    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;

    private  final IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    private final IOutsrcAttrChgApplyService outsrcAttrChgApplyService;
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<OutsrcEmpAssessDto> queryList(OutsrcEmpAssess outsrcingAssess) {
        List<OutsrcEmpAssessDto> dtoList = myBatisDao.selectListT(OUTSRCINGASSESS, outsrcingAssess, "getList");
        dtoList.forEach(x -> {
            x.setDeptName(BusinessUtil.getDeptNameValue(x.getDeptName(),1));
        });
        return dtoList;
    }

    @Override
    public List<OutsrcEmpAssessDto> getYearAssessWithAvgScore(OutsrcEmpAssess outsrcingAssess) {
        List<OutsrcEmpAssessDto> dto = myBatisDao.selectListT(OUTSRCINGASSESS,
                outsrcingAssess, "getYearAssessWithAvgScore");
        return dto;
    }

    @Override
    public List<OutsrcEmpAssessDto> getYearAssess(OutsrcEmpAssess outsrcingAssess) {
        List<OutsrcEmpAssessDto> dto = myBatisDao.selectListT(OUTSRCINGASSESS, outsrcingAssess, "getYearAssess");
        return dto;
    }

    @Override
    public List<OutsrcEmpAssessApplyDto> getYearAssessApply(OutsrcEmpAssess outsrcingAssess) {
        List<OutsrcEmpAssessApplyDto> dto = myBatisDao.selectListT(OUTSRCINGASSESS,
                outsrcingAssess, "getYearAssessApply");
        return dto;
    }

    @Override
    public List<PpmOutsourcingBasicInfo> getPersonListByMgr(
            OutsrcEmpAssess outsrcingAssess,OutsrcEmpAssessApplyDto apply) {
        Map<String,String> map = new HashMap<>();
        map.put("deptId", outsrcingAssess.getDeptId());
        map.put("personInCharge", outsrcingAssess.getPrjMgrCode()); //项目经理
        map.put("outsourceType", "1");
        List<PpmOutsourcingBasic> list = ppmOutsourcingBasicService.queryOutsList(map);
        List<PpmOutsourcingBasicInfo> annualList = new ArrayList<>();
        // 上一季度的最后一天
//        String lastDay = DateUtil.lastDayOfPreviousQuarter();
        String lastDay = DateUtil.getYearOfQuarter(apply.getAssessYear(),apply.getAssessAnnual(),"-1");
        String startDay = DateUtil.getYearOfQuarter(apply.getAssessYear(),apply.getAssessAnnual(),"0");
        Date lastDate = DateUtil.strToDate(lastDay,"yyyy-MM-dd");
        Date startDate = DateUtil.strToDate(startDay,"yyyy-MM-dd");
        list.forEach(x -> {
            // 离岗日期不为空， 且小于等于上1季度最后一天
            if (x.getEntryTime().after(lastDate) ||
                    (!Objects.isNull(x.getLeavingTime()) && x.getLeavingTime().before(startDate))){
                return;
            }
            PpmOutsourcingBasicInfo obj = JsonUtil.jsonToClass(x,PpmOutsourcingBasicInfo.class);
            String orgName = outsrcAttrChgApplyService.getOrgName(obj.getOutsourceNo());
            obj.setOutsourcname(orgName);
            annualList.add(obj);
        });
        return annualList;
    }

    @Override
    public List<OutsrcEmpAssessDto> queryListByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        List<OutsrcEmpAssessDto> dtoList = myBatisDao.selectListT(OUTSRCINGASSESS, map, "selectListByInstanceId");
        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcingAssess(OutsrcEmpAssess outsrcEmpAssess) {
        return myBatisDao.executionPojo(OUTSRCINGASSESS, outsrcEmpAssess, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAddOutsrcEmpAssess(List<OutsrcEmpAssessDto> outsrcEmpAssessList) {
        return myBatisDao.executionPojo(OUTSRCINGASSESS, outsrcEmpAssessList, IMyBatisDao.SQL.INSTER, "batchAdd");
    }

    @Override
    public int updateOutsrcEmpAssess(OutsrcEmpAssess outsrcingAssess) {
        return myBatisDao.executionPojo(OUTSRCINGASSESS, outsrcingAssess, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByInsId(OutsrcEmpAssess outsrcingAssess) {
        return myBatisDao.executionPojo(OUTSRCINGASSESS, outsrcingAssess, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
    }




}