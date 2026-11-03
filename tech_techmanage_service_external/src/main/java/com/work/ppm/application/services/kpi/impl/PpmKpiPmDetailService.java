package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.ComprehensiveAbilityEvalDto;
import com.work.ppm.application.dto.kpi.PostEvaluationEvalDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInsertInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import com.work.ppm.application.model.kpi.ext.PpmKpiPmDetailInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiItemService;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchService;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiPmDetailService")
public class PpmKpiPmDetailService extends BaseDaoSupper implements IPpmKpiPmDetailService {
    /**
      * 实体类路径
      */
    private static String PPMKPIPMDETAIL = "com.work.ppm.application.model.kpi.PpmKpiPmDetail";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 指标库接口
     */
    @Autowired
    private IPpmKpiItemService ppmKpiItemService;

    /**
     * 配置发起中心接口
     */
    @Autowired
    private IPpmKpiItemSwitchService ppmKpiItemSwitchService;

    /**
     * 考核主表接口
     */
    @Autowired
    private IPpmKpiPmService ppmKpiPmService;
    /**
     * 考核主表接口
     */
    @Autowired
    private IPpmKpiPm1Service ppmKpiPm1Service;

    /**
     * 考核主表接口
     */
    @Autowired
    private IPpmKpiPm2Service ppmKpiPm2Service;
    /**
     * 考核人员角色接口
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmKpiPmDetailDto ppmKpiPmDetailDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmDetailDto,PpmKpiPmDetailDto.class);
        myBatisDao.selectPager(PPMKPIPMDETAIL, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        Long id = getNextSequence(PpmKpiPmDetail.class);

        // 如果需要请set 主键id
        ppmKpiPmDetail.setId(id);
        myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiPmDetailDto queryDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        PpmKpiPmDetail data = myBatisDao.selectClass(PPMKPIPMDETAIL, ppmKpiPmDetail,
        "queryDetail");
        PpmKpiPmDetailDto item = JsonUtil.jsonToClass(data, PpmKpiPmDetailDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiItemDto ppmKpiItemDto=new PpmKpiItemDto();
        ppmKpiItemDto.setId(Long.parseLong(ppmKpiPmDetailDto.getItem()));
        PpmKpiItemDto dto=ppmKpiItemService.queryDetail(ppmKpiItemDto);
        ppmKpiPmDetailDto.setItemdescribe(dto.getItem());
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    @Override
    public int insertPpmKpiPmDetailEmp(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        //查询指标库中员工考核所需要的指标并插入到考核指标表中
        //综合能力指标
        PpmKpiItemDto comprehensiveAbilityParam=new PpmKpiItemDto();
        comprehensiveAbilityParam.setItype(2L);
        List<PpmKpiItemInsertInfoDto> comprehensiveAbilitys=
                ppmKpiItemService.querykpiItemAll(comprehensiveAbilityParam);
        if(comprehensiveAbilitys.size()>0){
            for (int i=0;i<comprehensiveAbilitys.size();i++) {
                PpmKpiPmDetailDto detail = new PpmKpiPmDetailDto();
                BeanUtils.copyProperties(ppmKpiPmDetailDto, detail);
                detail.setKpitype(2L);
                detail.setItem(comprehensiveAbilitys.get(i).getId()+"");
                detail.setItemdescribe(comprehensiveAbilitys.get(i).getItem());
                detail.setPlan(comprehensiveAbilitys.get(i).getDid()+"");
                detail.setPlandescribe(comprehensiveAbilitys.get(i).getStandard());
                detail.setWeight(comprehensiveAbilitys.get(i).getWeight());
                detail.setMainpart(comprehensiveAbilitys.get(i).getMainpart());
                detail.setValue(comprehensiveAbilitys.get(i).getValue());
                detail.setWorkremark(comprehensiveAbilitys.get(i).getWorkcontent());
                detail.setItemstatus(2L);
                //查出配置项中自动完成时间
                PpmKpiItemSwitchDto ppmKpiItemSwitchDto=new PpmKpiItemSwitchDto();
                ppmKpiItemSwitchDto.setTyear(detail.getTyear());
                ppmKpiItemSwitchDto.setTerm(detail.getTerm());
                ppmKpiItemSwitchDto.setIsswitch(1L);
                ppmKpiItemSwitchDto.setItype("1");
                if("上半年".equals(detail.getTerm())){
                    ppmKpiItemSwitchDto.setItemtype("7");
                }else{
                    ppmKpiItemSwitchDto.setItemtype("8");
                }
                PpmKpiItemSwitchDto dto=ppmKpiItemSwitchService.queryDetailByCondition(ppmKpiItemSwitchDto);
                if(StringUtil.isNotEmpty(dto)){
                    detail.setFinishdate(DateUtil.strToDate(dto.getFinishtime(),"yyyy-MM-dd"));
                }else{
                    detail.setFinishdate(comprehensiveAbilitys.get(i).getFinishdate());
                }
                addPpmKpiPmDetail(detail);
            }
        }
        insertBalance(ppmKpiPmDetailDto);
        return 1;
    }

    /**
     * 插入平衡计分有就不做操作
     * @param ppmKpiPmDetailDto
     */
    private void insertBalance(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        Map param=new HashMap();
        param.put("tyear",ppmKpiPmDetailDto.getTyear());
        param.put("eid",ppmKpiPmDetailDto.getEid());
        param.put("kpitype","4");
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL, param, "queryPagerList");
        if(data.size()<=0){
            //平衡计分指标
            PpmKpiItemDto balanceParam=new PpmKpiItemDto();
            balanceParam.setItype(4L);
            List<PpmKpiItemInsertInfoDto> balances= ppmKpiItemService.querykpiItemAll(balanceParam);
            if(balances.size()>0){
                for (int i=0;i<balances.size();i++) {
                    PpmKpiPmDetailDto detail = new PpmKpiPmDetailDto();
                    BeanUtils.copyProperties(ppmKpiPmDetailDto, detail);
                    detail.setKpitype(4L);
                    detail.setItem(balances.get(i).getId()+"");
                    detail.setItemdescribe(balances.get(i).getItem());
                    detail.setPlan(balances.get(i).getDid()+"");
                    detail.setPlandescribe(balances.get(i).getStandard());
                    detail.setWorkremark(balances.get(i).getWorkcontent());
                    detail.setValue(balances.get(i).getValue());
                    detail.setFinishdate(balances.get(i).getFinishdate());
                    detail.setItemstatus(7L);
                    addPpmKpiPmDetail(detail);
                }
            }
        }


    }

    @Override
    public int insertPpmKpiPmDetailManagers(PpmKpiPmDetailDto ppmKpiPmDetailDto){

        //查询指标库中员工考核所需要的指标并插入到考核指标表中
        //履职评估指标
        PpmKpiItemDto postEvaluationParam=new PpmKpiItemDto();
        postEvaluationParam.setItype(3L);
        List<PpmKpiItemInsertInfoDto> postEvaluations= ppmKpiItemService.querykpiItemAll(postEvaluationParam);
        if(postEvaluations.size()>0){
            for (int i=0;i<postEvaluations.size();i++) {
                PpmKpiPmDetailDto detail = new PpmKpiPmDetailDto();
                BeanUtils.copyProperties(ppmKpiPmDetailDto, detail);
                detail.setKpitype(3L);
                detail.setItem(postEvaluations.get(i).getId()+"");
                detail.setItemdescribe(postEvaluations.get(i).getItem());
                detail.setPlan(postEvaluations.get(i).getDid()+"");
                detail.setPlandescribe(postEvaluations.get(i).getStandard());
                detail.setWeight(postEvaluations.get(i).getWeight());
                detail.setMainpart(postEvaluations.get(i).getMainpart());
                detail.setValue(postEvaluations.get(i).getValue());
                detail.setWorkremark(postEvaluations.get(i).getWorkcontent());
                detail.setItemstatus(2L);
                //查出配置项中自动完成时间
                PpmKpiItemSwitchDto ppmKpiItemSwitchDto=new PpmKpiItemSwitchDto();
                ppmKpiItemSwitchDto.setTyear(detail.getTyear());
                ppmKpiItemSwitchDto.setTerm(detail.getTerm());
                ppmKpiItemSwitchDto.setIsswitch(1L);
                ppmKpiItemSwitchDto.setItype("1");
                ppmKpiItemSwitchDto.setItemtype("5");
                PpmKpiItemSwitchDto dto=ppmKpiItemSwitchService.queryDetailByCondition(ppmKpiItemSwitchDto);
                if(StringUtil.isNotEmpty(dto)){
                    detail.setFinishdate(DateUtil.strToDate(dto.getFinishtime(),"yyyy-MM-dd"));
                }else{
                    detail.setFinishdate(postEvaluations.get(i).getFinishdate());
                }
                addPpmKpiPmDetail(detail);
            }
        }
        insertBalance(ppmKpiPmDetailDto);
        return 1;
    }
    @Override
    public int insertPpmKpiPmDetailWorkManager(PpmKpiPmDetailDto ppmKpiPmDetailDto){

        //查询指标库中员工考核所需要的指标并插入到考核指标表中
        //履职评估指标
        PpmKpiItemDto postEvaluationParam=new PpmKpiItemDto();
        postEvaluationParam.setItype(3L);
        List<PpmKpiItemInsertInfoDto> postEvaluations= ppmKpiItemService.querykpiItemAll(postEvaluationParam);
        if(postEvaluations.size()>0){
            for (int i=0;i<postEvaluations.size();i++) {
                PpmKpiPmDetailDto detail = new PpmKpiPmDetailDto();
                BeanUtils.copyProperties(ppmKpiPmDetailDto, detail);
                detail.setKpitype(3L);
                detail.setItem(postEvaluations.get(i).getId()+"");
                detail.setItemdescribe(postEvaluations.get(i).getItem());
                detail.setPlan(postEvaluations.get(i).getDid()+"");
                detail.setPlandescribe(postEvaluations.get(i).getStandard());
                detail.setWeight(postEvaluations.get(i).getWeight());
                detail.setMainpart(postEvaluations.get(i).getMainpart());
                detail.setValue(postEvaluations.get(i).getValue());
                detail.setWorkremark(postEvaluations.get(i).getWorkcontent());
                detail.setItemstatus(2L);
                //查出配置项中自动完成时间
                PpmKpiItemSwitchDto ppmKpiItemSwitchDto=new PpmKpiItemSwitchDto();
                ppmKpiItemSwitchDto.setTyear(detail.getTyear());
                ppmKpiItemSwitchDto.setTerm(detail.getTerm());
                ppmKpiItemSwitchDto.setIsswitch(1L);
                ppmKpiItemSwitchDto.setItype("1");
                ppmKpiItemSwitchDto.setItemtype("5");
                PpmKpiItemSwitchDto dto=ppmKpiItemSwitchService.queryDetailByCondition(ppmKpiItemSwitchDto);
                if(StringUtil.isNotEmpty(dto)){
                    detail.setFinishdate(DateUtil.strToDate(dto.getFinishtime(),"yyyy-MM-dd"));
                }else{
                    detail.setFinishdate(postEvaluations.get(i).getFinishdate());
                }
                addPpmKpiPmDetail(detail);
            }
        }
        insertBalance(ppmKpiPmDetailDto);
        return 1;
    }
    @Override
    public long queryKpiPmDetails(Map param) {
        long count=0L;
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL, param,
                "getPpmKpiPmDetails");
        List<PpmKpiPmDetailDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailDto
                .class);
        if(item.size()>0){
            count=item.size();
        }
        return count;
    }
    @Override
    public List<PpmKpiPmDetailInfoDto> getPpmKpiPmDetails(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto){
        PpmKpiPmDetailInfo ppmKpiPmDetailInfo = JsonUtil.jsonToClass(ppmKpiPmDetailInfoDto, PpmKpiPmDetailInfo.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(PPMKPIPMDETAIL,
                ppmKpiPmDetailInfo, "getItemList");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailInfoDto.class);
        return item;

    }

    @Override
    public int updatePpmKpiPmDetailApprove(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        if(ppmKpiPmDetailDto.getKpitype()==1) {
            PpmKpiItemDto ppmKpiItemDto=new PpmKpiItemDto();
            ppmKpiItemDto.setId(Long.parseLong(ppmKpiPmDetailDto.getItem()));
            PpmKpiItemDto dto = ppmKpiItemService.queryDetail(ppmKpiItemDto);
            ppmKpiPmDetailDto.setItemdescribe(dto.getItem());
        }
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
    @Transactional("defaultTx")
    @Override
    public int updatePpmKpiPmDetailFormulate(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        //设置主表为草稿
        setKpiPmStatus(ppmKpiPmDetailDto.getPid(),0L);
        if(ppmKpiPmDetailDto.getKpitype()==1) {
            PpmKpiItemDto ppmKpiItemDto=new PpmKpiItemDto();
            ppmKpiItemDto.setId(Long.parseLong(ppmKpiPmDetailDto.getItem()));
            PpmKpiItemDto dto = ppmKpiItemService.queryDetail(ppmKpiItemDto);
            ppmKpiPmDetailDto.setItemdescribe(dto.getItem());
        }
        ppmKpiPmDetailDto.setItemstatus(0L);
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int addPpmKpiPmDetailItemApprove(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid) {
        // 如果需要请set 主键id
        PpmKpiItemDto ppmKpiItemDto=new PpmKpiItemDto();
        if(ppmKpiPmDetailDto.getKpitype()==1){
            ppmKpiItemDto.setId(Long.parseLong(ppmKpiPmDetailDto.getItem()));
            PpmKpiItemDto dto=ppmKpiItemService.queryDetail(ppmKpiItemDto);
            ppmKpiPmDetailDto.setItemdescribe(dto.getItem());
        }
        ppmKpiPmDetailDto.setRegby(userid);
        ppmKpiPmDetailDto.setRegtime(new Date());
        ppmKpiPmDetailDto.setIsedit(0L);
        ppmKpiPmDetailDto.setItemstatus(1L);
        this.addPpmKpiPmDetail(ppmKpiPmDetailDto);
        return 1;
    }

    @Override
    public int addPpmKpiPmDetailItemFormulate(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid) {
        //设置主表为草稿
        setKpiPmStatus(ppmKpiPmDetailDto.getPid(),0L);
        // 如果需要请set 主键id
        PpmKpiItemDto ppmKpiItemDto=new PpmKpiItemDto();
        if(ppmKpiPmDetailDto.getKpitype()==1){
            ppmKpiItemDto.setId(Long.parseLong(ppmKpiPmDetailDto.getItem()));
            PpmKpiItemDto dto=ppmKpiItemService.queryDetail(ppmKpiItemDto);
            ppmKpiPmDetailDto.setItemdescribe(dto.getItem());
        }
        ppmKpiPmDetailDto.setRegby(userid);
        ppmKpiPmDetailDto.setRegtime(new Date());
        ppmKpiPmDetailDto.setIsedit(0L);
        ppmKpiPmDetailDto.setItemstatus(0L);
        this.addPpmKpiPmDetail(ppmKpiPmDetailDto);
        return 1;
    }
    /**
     * 根据id更新主表状态
     * @param id
     * @param status
     * @return int
     */
    private int setKpiPmStatus(long id,long status){
        PpmKpiPmDto ppmKpiPmDto=new PpmKpiPmDto();
        ppmKpiPmDto.setId(id);
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        if(pm!=null){
            pm.setStatus(status);
            ppmKpiPmService.updatePpmKpiPm(pm);
        }
        return 1;
    }
    @Override
    public List<PpmKpiPmDetail> queryPpmKpiPmDetails(Map param) {
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL, param, "queryPpmKpiPmDetails");
        return data;
    }


    @Override
    public int updatePpmKpiPmDetail(PpmKpiPmDetail ppmKpiPmDetail) {
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public PpmKpiPmDetailInfoDto getPpmKpiPmDetailInfo(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
        PpmKpiPmDetailInfo ppmKpiPmDetailInfo = JsonUtil.jsonToClass(ppmKpiPmDetailInfoDto, PpmKpiPmDetailInfo.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(PPMKPIPMDETAIL, ppmKpiPmDetailInfo, "getItemList");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailInfoDto.class);
        if(data.size()>0){
            return item.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int updatePpmKpiPmDetailDto(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        ppmKpiPmDetail.setEvalselftime(new Date());
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail,
                IMyBatisDao.SQL.UPDATE,"updatePojo");
    }


    @Override
    public int updateItemStatus(Map param) {
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, param, IMyBatisDao.SQL.UPDATE,"updateItemStatus");
    }

    @Override
    public PpmKpiPmDetail querySumValue(Map param) {
        PpmKpiPmDetail data = myBatisDao.selectClass(PPMKPIPMDETAIL, param, "querySumValue");
        return data;
    }

    @Override
    public List<PpmKpiPmDetailInfoDto> getPpmKpiPmComprehensiveAbilityEvalInfo(
            PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
        PpmKpiPmDetailInfo ppmKpiPmDetailInfo = JsonUtil.jsonToClass(
                ppmKpiPmDetailInfoDto, PpmKpiPmDetailInfo.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(
                PPMKPIPMDETAIL, ppmKpiPmDetailInfo,
                "getPpmKpiPmComprehensiveAbilityEvalInfo");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailInfoDto.class);
        return item;
    }
    @Override
    public int updateDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        PpmKpiPmDetailDto ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetailDto.class);
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }


    @Override
    public int updateDetails(List<PpmKpiPmDetailDto> ppmKpiPmDetailDtos){
        for (int i=0;i<ppmKpiPmDetailDtos.size();i++){
            PpmKpiPmDetailDto ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDtos.get(i), PpmKpiPmDetailDto.class);
            myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
        }
        return 1;
    }

    @Override
    public int backEvalSelf(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid){
        ppmKpiPmDetailDto.setItemstatus(4L);
        if(ppmKpiPmDetailDto.getItemstatus()==5){
            ppmKpiPmDetailDto.setFirsteval(userid);
            ppmKpiPmDetailDto.setFirstevaltime(new Date());
        }else if(ppmKpiPmDetailDto.getItemstatus()==6){
            ppmKpiPmDetailDto.setSecondeval(userid);
            ppmKpiPmDetailDto.setSecondevaltime(new Date());
        }
        return updateDetail(ppmKpiPmDetailDto);
    }

    @Override
    public int backFirstEval(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid){
        ppmKpiPmDetailDto.setItemstatus(5L);
        ppmKpiPmDetailDto.setSecondeval(userid);
        ppmKpiPmDetailDto.setSecondevaltime(new Date());
        return updateDetail(ppmKpiPmDetailDto);
    }
    @Override
    public int submitEval(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid){
        int retCode=0;
        if(ppmKpiPmDetailDto.getItemstatus()==5){
            ppmKpiPmDetailDto.setItemstatus(6L);
            ppmKpiPmDetailDto.setFirsteval(userid);
            ppmKpiPmDetailDto.setFirstevaltime(new Date());
        }else if(ppmKpiPmDetailDto.getItemstatus()==6){
            ppmKpiPmDetailDto.setItemstatus(7L);
            ppmKpiPmDetailDto.setSecondeval(userid);
            ppmKpiPmDetailDto.setSecondevaltime(new Date());
        }else{
            ppmKpiPmDetailDto.setItemstatus(5L);
            ppmKpiPmDetailDto.setEvalselftime(new Date());
        }
        retCode= updateDetail(ppmKpiPmDetailDto);
        if(retCode>0){
            PpmKpiPmDetailDto detail=queryDetail(ppmKpiPmDetailDto);
            //计算分数
            ppmKpiPm2Service.summaryScore(detail.getPid());
            //判断主记录状态
            ppmKpiPm1Service.updateMainStatusByItemstatus(detail.getPid());
        }
        return retCode;

    }
    @Override
    public List<PpmKpiPmDetailDto> getPpmKpiPmDetails(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL, ppmKpiPmDetail, "queryPagerList");
        List<PpmKpiPmDetailDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailDto.class);
        return item;

    }
    @Override
    public ComprehensiveAbilityEvalDto queryComprehensiveAbilityEvalInfo(
            ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto){
        PpmKpiPmDetailDto ppmKpiPmDetailDto=new PpmKpiPmDetailDto();
        ppmKpiPmDetailDto.setPid(comprehensiveAbilityEvalDto.getPid());
        ppmKpiPmDetailDto.setItem(comprehensiveAbilityEvalDto.getItem());
        ppmKpiPmDetailDto.setItemstatus(comprehensiveAbilityEvalDto.getItemstatus());
        List<PpmKpiPmDetailDto> list =getPpmKpiPmDetails(ppmKpiPmDetailDto);
        if(list.size()>0){
            comprehensiveAbilityEvalDto.setEid(list.get(0).getEid());
            comprehensiveAbilityEvalDto.setKpitype(list.get(0).getKpitype());
            comprehensiveAbilityEvalDto.setItemdescribe(list.get(0).getItemdescribe());
            comprehensiveAbilityEvalDto.setWeight(list.get(0).getWeight());

            PpmKpiRoleDto roleParam=new PpmKpiRoleDto();
            roleParam.setBadge(comprehensiveAbilityEvalDto.getEid());
            PpmKpiRoleDto  role=ppmKpiRoleService.getPpmKpiRoleDtoByBadge(roleParam);
            if(StringUtil.isNotEmpty(role)){
                comprehensiveAbilityEvalDto.setFirsteval(role.getFirstcheck());
                comprehensiveAbilityEvalDto.setSecondeval(role.getSecondcheck());
            }
            comprehensiveAbilityEvalDto.setList(list);
        }
        return comprehensiveAbilityEvalDto;
    }

    @Override
    public int submitComprehensiveAbilityEval(
            ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto,String userid){
        List<PpmKpiPmDetailDto> list =comprehensiveAbilityEvalDto.getList();
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5){
                detail.setItemstatus(6L);
                detail.setFirsteval(userid);
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setItemstatus(7L);
                detail.setSecondeval(userid);
                detail.setSecondevaltime(new Date());
            }else{
                continue;
            }
            updateDetail(detail);

        }
        //计算分数
        ppmKpiPm2Service.summaryScore(comprehensiveAbilityEvalDto.getPid());
        //判断主记录状态
        ppmKpiPm1Service.updateMainStatusByItemstatus(comprehensiveAbilityEvalDto.getPid());
        return 1;
    }


    @Override
    public int submitWholeComment(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid){
        int retCode=0;
        if(ppmKpiPmDetailDto.getItemstatus()==5){
            ppmKpiPmDetailDto.setItemstatus(6L);
            ppmKpiPmDetailDto.setFirsteval(userid);
            ppmKpiPmDetailDto.setFirstevaltime(new Date());
        }else if(ppmKpiPmDetailDto.getItemstatus()==6){
            ppmKpiPmDetailDto.setItemstatus(7L);
            ppmKpiPmDetailDto.setSecondeval(userid);
            ppmKpiPmDetailDto.setSecondevaltime(new Date());
        }
        retCode=updateDetail(ppmKpiPmDetailDto);
        if(retCode>0){
            PpmKpiPmDetailDto detail=queryDetail(ppmKpiPmDetailDto);
            ppmKpiPm1Service.updateMainStatusByItemstatus(detail.getPid());
        }
        return retCode;
    }


    @Override
    public double getKpiOrBalanceSumScore(Map param){
        double tscore=0L;
        PpmKpiPmDetail data = myBatisDao.selectClass(PPMKPIPMDETAIL,
                param, "queryKpiOrBalanceSumScore");
        if(StringUtil.isNotEmpty(data)){
            tscore=data.getTscore();
        }
        return tscore;
    }

    @Override
    public double getComprehensiveAbilityOrPostEvaluationSumScore(Map param){
        double tscore=0L;
        PpmKpiPmDetail data = myBatisDao.selectClass(PPMKPIPMDETAIL,
                param, "queryComprehensiveAbilityOrPostEvaluationSumScore");
        if(StringUtil.isNotEmpty(data)){
            tscore=data.getTscore();
        }
        return tscore;
    }

    @Override
    public List<PpmKpiPmDetailDto> getBalanceItem(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL,
                ppmKpiPmDetail, "getBalanceItem");
        List<PpmKpiPmDetailDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailDto.class);
        return item;
    }

    @Override
    public List<PpmKpiPmDetailDto> getBalancePlan(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL,
                ppmKpiPmDetail, "getBalancePlan");
        List<PpmKpiPmDetailDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailDto.class);
        return item;
    }


    @Override
    public PpmKpiPmDetail queryPpmKpiPmDetailByCondition(PpmKpiPmDetail ppmKpiPmDetail) {
        PpmKpiPmDetail data = myBatisDao.selectClass(PPMKPIPMDETAIL,
                ppmKpiPmDetail, "queryPpmKpiPmDetailByCondition");
        return data;
    }

    @Override
    public List<PpmKpiPmDetailDto> queryPpmKpiPmDetailEvalGroup(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        List<PpmKpiPmDetail> data = myBatisDao.selectListT(PPMKPIPMDETAIL,
                ppmKpiPmDetail, "queryPpmKpiPmDetailEvalGroup");
        List<PpmKpiPmDetailDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailDto.class);
        return item;

    }


    @Override
    public int deletePpmKpiPmDetailByPid(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        return myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail, IMyBatisDao.SQL.DELETE,"deleteByPid");
    }



    @Override
    public int saveComprehensiveAbilityEval(
            ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto,String userid){
        List<PpmKpiPmDetailDto> list =comprehensiveAbilityEvalDto.getList();
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5){
                detail.setFirsteval(userid);
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setSecondeval(userid);
                detail.setSecondevaltime(new Date());
            }else{
                continue;
            }
            updateDetail(detail);
        }
        //计算分数
        ppmKpiPm2Service.summaryScore(comprehensiveAbilityEvalDto.getPid());
        return 1;
    }
    @Override
    public int submitTotalComprehensiveAbilityEval(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid){
        List<PpmKpiPmDetailDto> list= getPpmKpiPmDetails(ppmKpiPmDetailDto);
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5&&null==detail.getFirstevalscore()){
               return 0;
            }else if(detail.getItemstatus()==6&&null==detail.getSecondevalscore()){
                return 0;
            }else{
               continue;
            }

        }
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5){
                detail.setItemstatus(6L);
                detail.setFirsteval(userid);
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setItemstatus(7L);
                detail.setSecondeval(userid);
                detail.setSecondevaltime(new Date());
            }else{
                continue;
            }
            updateDetail(detail);
        }
        //计算分数
        ppmKpiPm2Service.summaryScore(ppmKpiPmDetailDto.getPid());
        //判断主记录状态
        ppmKpiPm1Service.updateMainStatusByItemstatus(ppmKpiPmDetailDto.getPid());
        return 1;
    }


    @Override
    public int saveWholeComment(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid){
        int retCode=0;
        if(ppmKpiPmDetailDto.getItemstatus()==5){
            ppmKpiPmDetailDto.setFirsteval(userid);
            ppmKpiPmDetailDto.setFirstevaltime(new Date());
        }else if(ppmKpiPmDetailDto.getItemstatus()==6){
            ppmKpiPmDetailDto.setSecondeval(userid);
            ppmKpiPmDetailDto.setSecondevaltime(new Date());
        }
        retCode=updateDetail(ppmKpiPmDetailDto);
        return retCode;
    }



    @Override
    public List<PpmKpiPmDetailInfoDto> queryPpmKpiPmDetailInfoGroup(
            PpmKpiPmDetailDto ppmKpiPmDetailDto){
        PpmKpiPmDetail ppmKpiPmDetail = JsonUtil.jsonToClass(
                ppmKpiPmDetailDto, PpmKpiPmDetail.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(
                PPMKPIPMDETAIL, ppmKpiPmDetail, "queryPpmKpiPmDetailInfoGroup");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailInfoDto.class);
        return item;

    }


    @Override
    public List<PpmKpiPmDetailInfoDto> getDetailItemList(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
        PpmKpiPmDetailInfo ppmKpiPmDetailInfo = JsonUtil.jsonToClass(
                ppmKpiPmDetailInfoDto, PpmKpiPmDetailInfo.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(
                PPMKPIPMDETAIL, ppmKpiPmDetailInfo, "getDetailItemList");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(
                data, PpmKpiPmDetailInfoDto.class);
        return item;
    }

    @Override
    public int backStageUpdateListStatus(List<PpmKpiPmDetailDto> ppmKpiPmDetailDtos){
        for(int i =0;i<ppmKpiPmDetailDtos.size();i++){
            backStageUpdateStatus(ppmKpiPmDetailDtos.get(i));
        }
        return 1;
    }


    @Override
    public int backStageUpdateStatus(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        //查询主记录
        PpmKpiPmDto ppmKpiPmDto=new PpmKpiPmDto();
        ppmKpiPmDto.setId(ppmKpiPmDetailDto.getPid());
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        if(!StringUtil.isNotEmpty(pm)){
            return -1;
        }

        if(ppmKpiPmDetailDto.getKpitype()==1){
            updateStatus(ppmKpiPmDetailDto,pm);
        }else if(ppmKpiPmDetailDto.getKpitype()==2){
            //查询详细记录
            Map kpiparam = new HashMap();
            kpiparam.put("pid", ppmKpiPmDetailDto.getPid());
            kpiparam.put("kpitype", ppmKpiPmDetailDto.getKpitype());
            kpiparam.put("item", ppmKpiPmDetailDto.getItem());
            List<PpmKpiPmDetail> kpidDtails = queryPpmKpiPmDetails(kpiparam);
            if(kpidDtails.size()>0){
                for(int i =0;i<kpidDtails.size();i++){
                    PpmKpiPmDetailDto detail= JsonUtil.jsonToClass(kpidDtails.get(i), PpmKpiPmDetailDto.class);
                    detail.setItemstatus(ppmKpiPmDetailDto.getItemstatus());
                    updateStatus(detail,pm);
                }

            }
        }else if(ppmKpiPmDetailDto.getKpitype()==3){
            //查询详细记录
            Map kpiparam = new HashMap();
            kpiparam.put("pid", ppmKpiPmDetailDto.getPid());
            kpiparam.put("kpitype", ppmKpiPmDetailDto.getKpitype());
            kpiparam.put("item", ppmKpiPmDetailDto.getItem());
            List<PpmKpiPmDetail> kpidDtails = queryPpmKpiPmDetails(kpiparam);
            if(kpidDtails.size()>0){
                for(int i =0;i<kpidDtails.size();i++){
                    PpmKpiPmDetailDto detail= JsonUtil.jsonToClass(kpidDtails.get(i), PpmKpiPmDetailDto.class);
                    detail.setItemstatus(ppmKpiPmDetailDto.getItemstatus());
                    updateStatus(detail,pm);
                }

            }
        }else if(ppmKpiPmDetailDto.getKpitype()==5){
            updateStatus(ppmKpiPmDetailDto,pm);
        }else{//为了代码规范
            updateStatus(ppmKpiPmDetailDto,pm);
        }
        return 1;
    }

    /**
     * kpi指标项后台修改团队
     * @param ppmKpiPmDetailDto
     * @param pm
     */
    private void updateStatus(PpmKpiPmDetailDto ppmKpiPmDetailDto ,PpmKpiPmDto pm){
            if(ppmKpiPmDetailDto.getItemstatus()==0){//草稿
                setMainStatus(pm,0L);
                ppmKpiPmDetailDto.setItemstatus(0L);
            }else if(ppmKpiPmDetailDto.getItemstatus()==1){//审批中
                ppmKpiPmDetailDto.setItemstatus(ppmKpiPmDetailDto.getItemstatus());//赋值原状态的数据状态判断先留着后续可能用
            }else if(ppmKpiPmDetailDto.getItemstatus()==2){//生效中
                ppmKpiPmDetailDto.setItemstatus(ppmKpiPmDetailDto.getItemstatus());//赋值原状态的数据状态判断先留着后续可能用
            }else if(ppmKpiPmDetailDto.getItemstatus()==3){//半年度自评
                setMainStatus(pm,4L);
            }else if(ppmKpiPmDetailDto.getItemstatus()==4){//待自评
                setMainStatus(pm,4L);
            }else if(ppmKpiPmDetailDto.getItemstatus()==5){//待初评
                setMainStatus(pm,4L);
            }else if(ppmKpiPmDetailDto.getItemstatus()==6){//待终评
                setMainStatus(pm,4L);
            }else if(ppmKpiPmDetailDto.getItemstatus()==7){//已完成
                //可能要判断是否都已经完成，先不判断
                ppmKpiPmDetailDto.setItemstatus(ppmKpiPmDetailDto.getItemstatus());//赋值原状态的数据状态判断先留着后续可能用
            }else if(ppmKpiPmDetailDto.getItemstatus()==8){//待初审
                setMainStatus(pm,1L);
                ppmKpiPmDetailDto.setItemstatus(1L);
            }else if(ppmKpiPmDetailDto.getItemstatus()==9){//待终审
                setMainStatus(pm,2L);
                ppmKpiPmDetailDto.setItemstatus(1L);
            }else{
                ppmKpiPmDetailDto.setItemstatus(ppmKpiPmDetailDto.getItemstatus());//赋值原状态的数据状态判断先留着后续可能用
            }
        ppmKpiPmService.updatePpmKpiPm(pm);
        updateDetail(ppmKpiPmDetailDto);
    }

    /**
     * 设置主状态状态已小为准
     * @param pm
     * @param status
     */
    private void setMainStatus(PpmKpiPmDto pm ,Long status){
        if(pm.getStatus()>status){
            pm.setStatus(status);
        }else{
            //代码规范必须有else
            pm.setStatus(pm.getStatus());
        }
    }


    /**
     * 获得最小状态的数据
     * @param param
     * @return PpmKpiPmDetail
     */
    private PpmKpiPmDetail getMinStatusDetail(Map param){
        PpmKpiPmDetail data = myBatisDao.selectClass(PPMKPIPMDETAIL, param, "getMinStatusDetail");
        return data;
    }

    @Override
    public int singleBackStageUpdateStatus(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        Long itemStatus=ppmKpiPmDetailDto.getItemstatus();
        //查询主记录
        PpmKpiPmDto ppmKpiPmDto=new PpmKpiPmDto();
        ppmKpiPmDto.setId(ppmKpiPmDetailDto.getPid());
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        if(!StringUtil.isNotEmpty(pm)){
            return -1;
        }
        setBackStageStatus(pm,ppmKpiPmDetailDto);
        setBackStageMainStatus(pm,ppmKpiPmDetailDto,itemStatus);
        return 1;
    }

    /**
     * 后台更新主状态
     * @param pm
     * @param ppmKpiPmDetailDto
     */
    @Transactional("defaultTx")
    private void setBackStageMainStatus(PpmKpiPmDto pm,PpmKpiPmDetailDto ppmKpiPmDetailDto,Long itemStatus){
        //查询最小子状态
        Map kpiparam = new HashMap();
        kpiparam.put("pid", ppmKpiPmDetailDto.getPid());
        PpmKpiPmDetail detail = getMinStatusDetail(kpiparam);
        if(StringUtil.isNotEmpty(detail)){
            if(detail.getItemstatus()==0){//草稿
                pm.setStatus(0L);
            }else if(detail.getItemstatus()==1){//审批中
                if(itemStatus==8){
                    pm.setStatus(1L);
                }else if(itemStatus==9){
                    pm.setStatus(2L);
                }else{
                    pm.setStatus(pm.getStatus());
                }

            }else if(detail.getItemstatus()==2){//生效中
                pm.setStatus(3L);
            }else if(detail.getItemstatus()==3){//半年度自评
                pm.setStatus(4L);
            }else if(detail.getItemstatus()==4){//待自评
                pm.setStatus(4L);
            }else if(detail.getItemstatus()==5){//待初评
                pm.setStatus(4L);
            }else if(detail.getItemstatus()==6){//待终评
                pm.setStatus(4L);
            }else if(detail.getItemstatus()==7){//已完成
                pm.setStatus(8L);
            }
            ppmKpiPmService.updatePpmKpiPm(pm);
        }
    }
    /**
     * 后台更新状态事务
     * @param pm
     * @param ppmKpiPmDetailDto
     */
    @Transactional("defaultTx")
    private void setBackStageStatus(PpmKpiPmDto pm,PpmKpiPmDetailDto ppmKpiPmDetailDto){

        if(ppmKpiPmDetailDto.getKpitype()==2) {
            //查询详细记录
            Map kpiparam = new HashMap();
            kpiparam.put("pid", ppmKpiPmDetailDto.getPid());
            kpiparam.put("kpitype", ppmKpiPmDetailDto.getKpitype());
            kpiparam.put("item", ppmKpiPmDetailDto.getItem());
            List<PpmKpiPmDetail> kpidDtails = queryPpmKpiPmDetails(kpiparam);
            if (kpidDtails.size() > 0) {
                for (int i = 0; i < kpidDtails.size(); i++) {
                    PpmKpiPmDetailDto detail = JsonUtil.jsonToClass(
                            kpidDtails.get(i), PpmKpiPmDetailDto.class);
                    detail.setItemstatus(ppmKpiPmDetailDto.getItemstatus());
                    updateDetail(detail);
                }

            }
        }else if(ppmKpiPmDetailDto.getKpitype()==3) {
            //查询详细记录
            Map kpiparam = new HashMap();
            kpiparam.put("pid", ppmKpiPmDetailDto.getPid());
            kpiparam.put("kpitype", ppmKpiPmDetailDto.getKpitype());
            kpiparam.put("item", ppmKpiPmDetailDto.getItem());
            List<PpmKpiPmDetail> kpidDtails = queryPpmKpiPmDetails(kpiparam);
            if (kpidDtails.size() > 0) {
                for (int i = 0; i < kpidDtails.size(); i++) {
                    PpmKpiPmDetailDto detail = JsonUtil.jsonToClass(
                            kpidDtails.get(i), PpmKpiPmDetailDto.class);
                    detail.setItemstatus(ppmKpiPmDetailDto.getItemstatus());
                    updateDetail(detail);
                }

            }

        } else{
            if(ppmKpiPmDetailDto.getItemstatus()==8){
                pm.setStatus(1L);
                ppmKpiPmDetailDto.setItemstatus(1L);
                updateDetail(ppmKpiPmDetailDto);
                ppmKpiPmService.updatePpmKpiPm(pm);

            }else if(ppmKpiPmDetailDto.getItemstatus()==9){
                pm.setStatus(2L);
                ppmKpiPmDetailDto.setItemstatus(1L);
                updateDetail(ppmKpiPmDetailDto);
                ppmKpiPmService.updatePpmKpiPm(pm);
            }else{
                updateDetail(ppmKpiPmDetailDto);
            }
        }
    }

    @Override
    public int updateDetailEval(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        PpmKpiPmDetailDto ppmKpiPmDetail = JsonUtil.jsonToClass(
                ppmKpiPmDetailDto, PpmKpiPmDetailDto.class);
        int i= myBatisDao.executionPojo(PPMKPIPMDETAIL, ppmKpiPmDetail,
                IMyBatisDao.SQL.UPDATE,"updatePojo");
        if(i>0){
            //计算分数
            ppmKpiPm2Service.summaryScore(ppmKpiPmDetailDto.getPid());
        }
        return i;
    }

    @Override
    public List<PpmKpiPmDetailInfoDto> getPpmKpiPmPostEvaluationEvalInfo(
            PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
        Map param = JsonUtil.objectTOMap(ppmKpiPmDetailInfoDto,PpmKpiPmDetailInfoDto.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(
                PPMKPIPMDETAIL, param, "getPpmKpiPmPostEvaluationInfo");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailInfoDto.class);
        return item;
    }

    @Override
    public PostEvaluationEvalDto queryPostEvaluationEvalEvalInfo(
            PostEvaluationEvalDto postEvaluationEvalDto) {
        PpmKpiPmDetailDto ppmKpiPmDetailDto=new PpmKpiPmDetailDto();
        ppmKpiPmDetailDto.setPid(postEvaluationEvalDto.getPid());
        ppmKpiPmDetailDto.setItem(postEvaluationEvalDto.getItem());
        ppmKpiPmDetailDto.setItemstatus(postEvaluationEvalDto.getItemstatus());
        List<PpmKpiPmDetailDto> list =getPpmKpiPmDetails(ppmKpiPmDetailDto);
        if(list.size()>0){
            postEvaluationEvalDto.setEid(list.get(0).getEid());
            postEvaluationEvalDto.setKpitype(list.get(0).getKpitype());
            postEvaluationEvalDto.setItemdescribe(list.get(0).getItemdescribe());
            postEvaluationEvalDto.setWeight(list.get(0).getWeight());
            postEvaluationEvalDto.setFirsteval(list.get(0).getFirsteval());
            postEvaluationEvalDto.setSecondeval(list.get(0).getSecondeval());
            postEvaluationEvalDto.setMainpart(list.get(0).getMainpart());
            EosDictEntry entry=eosDictEntryService.
                    getTypeidForDetail("KPI_MAIN",list.get(0).getMainpart());
            if(StringUtil.isNotEmpty(entry)){
                postEvaluationEvalDto.setMainpartname(entry.getDictname());
            }
            postEvaluationEvalDto.setList(list);
        }
        //计算履职评估分数
        Map param=new HashMap();
        param.put("pid",postEvaluationEvalDto.getPid());
        param.put("kpitype","3");
        double postEvaluation=getComprehensiveAbilityOrPostEvaluationSumScore(param);
        postEvaluationEvalDto.setPostEvalTscore(postEvaluation);
        return postEvaluationEvalDto;
    }


    @Override
    public int savePostEvaluationEval(PostEvaluationEvalDto postEvaluationEvalDto){
        List<PpmKpiPmDetailDto> list =postEvaluationEvalDto.getList();
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5){
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setSecondevaltime(new Date());
            }else{
                continue;
            }
            updateDetail(detail);
        }
        //计算分数
        ppmKpiPm2Service.summaryScore(postEvaluationEvalDto.getPid());
        return 1;
    }


    @Override
    public int submitTotalPostEvaluationEval(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        List<PpmKpiPmDetailDto> list= getPpmKpiPmDetails(ppmKpiPmDetailDto);
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5&&null==detail.getFirstevalscore()){
                return 0;
            }else if(detail.getItemstatus()==6&&null==detail.getSecondevalscore()){
                return 0;
            }else{
                continue;
            }
        }
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5){
                detail.setItemstatus(6L);
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setItemstatus(7L);
                detail.setSecondevaltime(new Date());
            }else{
                continue;
            }
            updateDetail(detail);
        }
        //计算分数
        ppmKpiPm2Service.summaryScore(ppmKpiPmDetailDto.getPid());
        //判断主记录状态
        ppmKpiPm1Service.updateMainStatusByItemstatus(ppmKpiPmDetailDto.getPid());
        return 1;
    }

    @Override
    public void getPostEvaluationPager(PpmKpiPmDetailInfoDto  ppmKpiPmDetailInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiPmDetailInfoDto,PpmKpiPmDetailInfoDto.class);
        myBatisDao.selectPager(PPMKPIPMDETAIL, map,pager,"getPostEvaluationList");
    }


    @Override
    public List<PpmKpiPmDetailInfoDto> getPostEvaluationList(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto){
        PpmKpiPmDetailInfo ppmKpiPmDetailInfo = JsonUtil.jsonToClass(
                ppmKpiPmDetailInfoDto, PpmKpiPmDetailInfo.class);
        List<PpmKpiPmDetailInfo> data = myBatisDao.selectListT(
                PPMKPIPMDETAIL, ppmKpiPmDetailInfo, "getPostEvaluationList");
        List<PpmKpiPmDetailInfoDto> item = JsonUtil.jsonToList(data, PpmKpiPmDetailInfoDto.class);
        return item;

    }


    @Override
    @Transactional("defaultTx")
    public boolean importExcel(MultipartFile file) {
        // 业务字典
       // List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KPI_ROLE");
        PpmKpiPmDetail ppmKpiPmDetail=new PpmKpiPmDetail();
        // excel导入数据
        List<PpmKpiPmDetailInfoDto> list = ExcelUtil.importExcel(file, PpmKpiPmDetailInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailInfoDto ii=list.get(i);
            Long id=ii.getId();
            ppmKpiPmDetail.setId(id);
            ppmKpiPmDetail.setSecondevalscore(ii.getSecondevalscore());
            ppmKpiPmDetail.setTscore(ii.getSecondevalscore());
            updatePpmKpiPmDetail(ppmKpiPmDetail);
        }


        return true;
    }

    @Override
    public int submitTechPostEvaluation(PpmKpiPmDetailDto ppmKpiPmDetailDto){
        // 业务字典
        List<PpmKpiPmDetailDto> list= getPpmKpiPmDetails(ppmKpiPmDetailDto);
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5&&null==detail.getFirstevalscore()){
                return 0;
            }else if(detail.getItemstatus()==6&&null==detail.getSecondevalscore()){
                return 0;
            }else{
                continue;
            }
        }
        for (int i=0;i<list.size();i++){
            PpmKpiPmDetailDto detail=list.get(i);
            if(detail.getItemstatus()==5){
                detail.setItemstatus(6L);
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setItemstatus(7L);
                detail.setSecondevaltime(new Date());
            }else{
                continue;
            }
            updateDetail(detail);
            //计算分数
            ppmKpiPm2Service.summaryScore(detail.getPid());
            //判断主记录状态
            ppmKpiPm1Service.updateMainStatusByItemstatus(detail.getPid());
        }

        return 1;
    }


    @Override
    public void getPostEvaluationGroupPager(PpmKpiPmDetailInfoDto  ppmKpiPmDetailInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmDetailInfoDto,PpmKpiPmDetailInfoDto.class);
        myBatisDao.selectPager(PPMKPIPMDETAIL, map,pager,"getPostEvaluationListGroup");
    }

}
