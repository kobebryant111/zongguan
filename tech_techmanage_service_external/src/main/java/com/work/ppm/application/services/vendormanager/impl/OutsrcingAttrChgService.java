package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;
import com.work.ppm.application.dto.vendormanager.OutsrcingAttrChgDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.vendormanager.OutsrcingAttrChg;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IOutsrcingAttrChgService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 人员属性变更 服务实现类
 * </p>
 *
 * @since 2022-12-07
 */

@Slf4j
@RequiredArgsConstructor
@Service("outsrcingAttrChgService")
public class OutsrcingAttrChgService extends BaseDaoSupper implements IOutsrcingAttrChgService {

    /**
     * 实体类路径
     */
    private static String OUTSRCINGATTRCHG= "com.work.ppm.application.model.vendormanager.OutsrcingAttrChg";
    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;
    /**
     * 外包花名册
     */
    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    private final IPpmKqAnnualVacationService kqAnnualVacationService;
    /**
     * 流程定义ID
     */
    private static final String FLOW_ID = "";
    /**
     * 流程名称
     */
    private static final String FLOW_TITLE = "";

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<OutsrcingAttrChgDto> queryPager(OutsrcingAttrChgDto outsrcingAttrChg, Pager pager) {
        if(StringUtils.isNotEmpty(outsrcingAttrChg.getDeptId())){
            outsrcingAttrChg.setDeptId(
                    outsrcingAttrChg.getDeptId().replace("dep",""));
        }
        Map<String, Object> map = StringUtil.objectTOMap(outsrcingAttrChg, OutsrcingAttrChgDto.class);
        Map<String, Object> map2 = StringUtil.objectTOMap(outsrcingAttrChg, OutsrcingAttrChg.class);
        map.putAll(map2);
        myBatisDao.selectPager(OUTSRCINGATTRCHG, map, pager, "queryPagerList");
        List<OutsrcingAttrChgDto> dtoList =
                JsonUtil.jsonToList(pager.getList(), OutsrcingAttrChgDto.class);
        dtoList.forEach(x -> x.setDeptName(BusinessUtil.getDeptNameValue(x.getDeptName(),1)));
        return dtoList;
    }

    @Override
    public List<OutsrcingAttrChg> queryList(OutsrcingAttrChg outsrcingAttrChg) {
        List<OutsrcingAttrChg> res = myBatisDao.selectListT(OUTSRCINGATTRCHG, outsrcingAttrChg, "getList");
        return res;
    }


    @Override
    public OutsrcingAttrChgDto queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        OutsrcingAttrChgDto dto = myBatisDao.selectClass(OUTSRCINGATTRCHG, map, "selectOneById");
        dto.setDeptName(BusinessUtil.getDeptNameValue(dto.getDeptName(),1));
        // OutsrcingAttrChgDto dto = JsonUtil.jsonToClass(outsrcingAttrChg, OutsrcingAttrChgDto.class);
        return dto;
    }

    @Override
    public List<OutsrcingAttrChgDto> queryListByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        List<OutsrcingAttrChgDto> dtoList = myBatisDao.selectListT(OUTSRCINGATTRCHG, map, "selectListByInstanceId");
        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcingAttrChg(OutsrcingAttrChg outsrcingAttrChg) {
        return myBatisDao.executionPojo(OUTSRCINGATTRCHG, outsrcingAttrChg, IMyBatisDao.SQL.INSTER, "addPojo");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAddOutsrcingAttrChg(List<OutsrcingAttrChgDto> outsrcingAttrChgList) {
        return myBatisDao.executionPojo(OUTSRCINGATTRCHG, outsrcingAttrChgList, IMyBatisDao.SQL.INSTER, "batchAdd");
    }

    @Override
    public int updateOutsrcingAttrChg(OutsrcingAttrChg outsrcingAttrChg) {
        return myBatisDao.executionPojo(OUTSRCINGATTRCHG, outsrcingAttrChg, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public void attrCron(){
        OutsrcingAttrChgDto outsrcingAttrChg= new OutsrcingAttrChgDto();
        outsrcingAttrChg.setIsDel("0");
        outsrcingAttrChg.setDispatched("0");
        outsrcingAttrChg.setApprovalStatus("1");
        List<OutsrcingAttrChg> list = queryList(outsrcingAttrChg);
        DecimalFormat decimalFormat = new DecimalFormat("###########.#######");
        list.stream().forEach(x ->{
            Date chgDate = DateUtil.strToDate( x.getChgDate(), "yyyy-MM-dd");
            // 更新字段：合同，总办议案，系统编号，项目编号
            if (chgDate.getTime() <= System.currentTimeMillis()) {
                PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                basic.setJobId(x.getUserCode());
                basic.setOutsourceType(x.getOutsourceType());
                //外包方式
                String outsourceType = x.getOutsourceType();
                if ("0".equalsIgnoreCase(outsourceType)) {
                    //项目
                    basic.setUserType("1");
                    basic.setProjectManager(x.getPrjMgrCode());
                }else{
                    basic.setUserType("0");
                    basic.setSystemPrincipal(x.getPrjMgrCode());
                }
                basic.setSystemNo(x.getSystemId());
                basic.setProjectNumber(x.getProjectId());
                basic.setNewContractId(x.getContractId());
                basic.setCeneralOfficeMotion(x.getAttachId());
                // 用serviceYear代替'grade'字段
                basic.setGrade(x.getServiceYear());
                basic.setPrice(decimalFormat.format(x.getPrice()));
                if (Objects.isNull(basic.getJobId())) {return;}
                ppmOutsourcingBasicService.updatePpmOutsourcingBasicCus(basic);
                x.setDispatched("1");
                this.updateOutsrcingAttrChg(x);

                //更新
                PpmKqAnnualVacationDto annualVacationDto = new PpmKqAnnualVacationDto();
                annualVacationDto.setJobId(x.getUserCode());
                annualVacationDto.setAnnualVacationDate(DateUtil.intervalMonth(chgDate,6));
                annualVacationDto.setAnnualVacationSurplus(0.0);
                annualVacationDto.setAnnualVacationDays(0.0);
                kqAnnualVacationService.updatePpmKqAnnualVacation(annualVacationDto);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByInstanceId(OutsrcingAttrChg outsrcingAttrChg) {
        return myBatisDao.executionPojo(OUTSRCINGATTRCHG, outsrcingAttrChg,
                          IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
    }

    @Override
    public List<OutsrcingAttrChgDto> selectByMapSmall(OutsrcingAttrChgDto outsrcingAttrChgDto) {
        OutsrcingAttrChg list = JsonUtil.jsonToClass(outsrcingAttrChgDto,OutsrcingAttrChg.class);
        List<OutsrcingAttrChg> objList = myBatisDao.selectListT(OUTSRCINGATTRCHG, list, "selectByMapNotSmall");
        return JsonUtil.jsonToList(objList,OutsrcingAttrChgDto.class);
    }
}