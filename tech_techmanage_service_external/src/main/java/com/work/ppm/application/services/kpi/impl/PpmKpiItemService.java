package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.PpmKpiItemBalanceInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemComprehensiveAbilityInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemDetailDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemDetailInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInsertInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemKpiInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemPostEvaluationInfoDto;
import com.work.ppm.application.model.kpi.PpmKpiItemDetail;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemBalanceInfo;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemComprehensiveAbilityInfo;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemInfo;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemInsertInfo;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemKpiInfo;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemPostEvaluationInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiItemDetailService;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiItem;
import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiItemService")
public class PpmKpiItemService extends BaseDaoSupper implements IPpmKpiItemService {
    /**
      * 实体类路径
      */
    private static String PPMKPIITEM = "com.work.ppm.application.model.kpi.PpmKpiItem";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     *  中心表
     */
    @Autowired
    private IPpmDaCenterService PpmDaCenterService;

    /**
     *  人员角色
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;
    /**
     * 指标库详细表
     */
    @Autowired
    private IPpmKpiItemDetailService ppmKpiItemDetailService;

    @Override
    public void queryPager(PpmKpiItemDto ppmKpiItemDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemDto,PpmKpiItemDto.class);
        myBatisDao.selectPager(PPMKPIITEM, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiItem(PpmKpiItemDto ppmKpiItemDto) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        Long id = getNextSequence(PpmKpiItem.class);

        // 如果需要请set 主键id
        ppmKpiItem.setId(id);
        myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiItemDto queryDetail(PpmKpiItemDto ppmKpiItemDto) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        PpmKpiItem data = myBatisDao.selectClass(PPMKPIITEM, ppmKpiItem,
        "queryDetail");
        PpmKpiItemDto item = JsonUtil.jsonToClass(data, PpmKpiItemDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiItem(PpmKpiItemDto ppmKpiItemDto) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        return myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiItem(PpmKpiItemDto ppmKpiItemDto) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        return myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.DELETE,"deleteById");
    }


    @Override
    public List<PpmKpiItemKpiInfoDto> ppmKpiItemInfoListKpi(PpmKpiItemKpiInfoDto ppmKpiItemKpiInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemKpiInfoDto,PpmKpiItemKpiInfoDto.class);
        List<PpmKpiItemKpiInfo> datas = myBatisDao.selectListT(PPMKPIITEM, map, "getKpiDatas");
        List<PpmKpiItemKpiInfoDto> item = JsonUtil.jsonToList(datas, PpmKpiItemKpiInfoDto.class);
        return item;
    }

    @Override
    public void queryPagerListKpi(PpmKpiItemKpiInfoDto ppmKpiItemKpiInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemKpiInfoDto,PpmKpiItemKpiInfoDto.class);
        myBatisDao.selectPager(PPMKPIITEM, map,pager,"getKpiDatas");
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiItemListKpi(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmKpiItem ppmKpiItem = new PpmKpiItem();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmKpiItem.setId(Long.parseLong(idList[i]));
            myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.DELETE,"deleteById");
        }
        return 1;
    }

    @Override
    public int addPpmKpiItemKpi(PpmKpiItemDto ppmKpiItemDto,String userid) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        Long id = getNextSequence(PpmKpiItem.class);
        // 如果需要请set 主键id
        ppmKpiItem.setId(id);
        ppmKpiItem.setUptdate(new Date());
        ppmKpiItem.setUptuserid(userid);
        ppmKpiItem.setItype(1L);
        myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int updatePpmKpiItemKpi(PpmKpiItemDto ppmKpiItemDto,String userid) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        ppmKpiItem.setUptdate(new Date());
        ppmKpiItem.setUptuserid(userid);
        return myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    /**
     *  根据id获得Kpi指标数据
     * @param id
     * @return ppmKpiDepRole
     */
    private PpmKpiItem getPpmKpiItemById(Long id){
        PpmKpiItem ppmKpiItem=new PpmKpiItem();
        ppmKpiItem.setId(id);
        PpmKpiItem data = myBatisDao.selectClass(PPMKPIITEM, ppmKpiItem, "queryKpiDetailById");
        return data;
    }
    /**
     *  根据item获得指标库主数据
     * @param item
     * @param itype
     * @return ppmKpiDepRole
     */
    private PpmKpiItem getPpmKpiItemByItem(String item,long itype ){
        PpmKpiItem ppmKpiItem=new PpmKpiItem();
        ppmKpiItem.setItem(item);
        ppmKpiItem.setItype(itype);
        PpmKpiItem data = myBatisDao.selectClass(PPMKPIITEM, ppmKpiItem, "queryKpiDetailByItem");
        return data;
    }
    /**
     * 处理角色使用名称
     * @param userolename
     * @return String
     */
    private String getUserole(String userolename){
        String retCode="";
        // 业务字典
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KPI_ITEM_USE_ROLE");
        if(StringUtil.isNotBlank(userolename)){
            String [] userolenames=userolename.split(",");
            for (int i =0;i< userolenames.length ; i++) {
                String role=userolenames[i];
              if(i==0){
                  retCode=eos.stream().filter(iii-> role.equals(iii.getDictname())).
                          findFirst().get().getDictid();
              }else{
                  retCode=retCode+","+eos.stream().filter(iii-> role.
                          equals(iii.getDictname())).findFirst().get().getDictid();
              }
            }

        }
        return retCode;
    }
    @Override
    @Transactional("defaultTx")
    public boolean importExcelKpi(MultipartFile file, String userid) {
        // 业务字典
       // List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KPI_TYPE");
        // excel导入数据
        List<PpmKpiItemKpiInfoDto> list = ExcelUtil.importExcel(file, PpmKpiItemKpiInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiItemKpiInfoDto ii=list.get(i);
            if(null!=ii.getId()&&!"".equals(ii.getId())){
                Long id=ii.getId();
                PpmKpiItem kpi=getPpmKpiItemById(id);
                kpi.setItem(ii.getItem());
                kpi.setUserolename(ii.getUserolename());
                String roles=getUserole(ii.getUserolename());
                String centerid=ppmKpiRoleService.retCenterid(ii.getCentername());
                kpi.setCenterid(centerid);
                kpi.setUserole(roles);
                kpi.setUptuserid(userid);
                kpi.setUptdate(new Date());
                myBatisDao.executionPojo(PPMKPIITEM, kpi, IMyBatisDao.SQL.UPDATE,"updatePojo");
            }else{
                PpmKpiItem kpi=new PpmKpiItem();
                Long id = getNextSequence(PpmKpiItem.class);
                // 如果需要请set 主键id
                kpi.setId(id);
                kpi.setItem(ii.getItem());
                kpi.setUserolename(ii.getUserolename());
                String roles=getUserole(ii.getUserolename());
                kpi.setUserole(roles);
                String centerid=ppmKpiRoleService.retCenterid(ii.getCentername());
                kpi.setCenterid(centerid);
                kpi.setItype(1L);
                kpi.setUptuserid(userid);
                kpi.setUptdate(new Date());
                myBatisDao.executionPojo(PPMKPIITEM, kpi, IMyBatisDao.SQL.INSTER,"addPojo");
            }

        }
        return true;
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiItemListComprehensiveAbility(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmKpiItem ppmKpiItem = new PpmKpiItem();
        PpmKpiItemDetail ppmKpiItemDetail = new PpmKpiItemDetail();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmKpiItem.setId(Long.parseLong(idList[i]));
            myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.DELETE,"deleteById");
            ppmKpiItemDetail.setRid(Long.parseLong(idList[i]));
            ppmKpiItemDetailService.deletePpmKpiItemDetailByRid(ppmKpiItemDetail);
        }
        return 1;
    }

    @Override
    public PpmKpiItemInfoDto getKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemInfoDto,PpmKpiItemInfoDto.class);
        PpmKpiItemInfo data = myBatisDao.selectClass(PPMKPIITEM, map, "queryKpiItem");
        if(StringUtil.isNotEmpty(data)){
            ppmKpiItemInfoDto = JsonUtil.jsonToClass(data, PpmKpiItemInfoDto.class);
            PpmKpiItemDetailInfoDto dto=new PpmKpiItemDetailInfoDto();
            dto.setRid(ppmKpiItemInfoDto.getId());
            List<PpmKpiItemDetailInfoDto> list=ppmKpiItemDetailService.getPpmKpiItemDetailInfoList(dto);
            ppmKpiItemInfoDto.setDlist(list);
        }
        return ppmKpiItemInfoDto;
    }

    /**
     * 更新PpmKpiItem数据
     * @param ppmKpiItemDto
     * @param userid
     * @return int
     */
    private int updatePpmKpiItem(PpmKpiItemDto ppmKpiItemDto,String userid) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto, PpmKpiItem.class);
        ppmKpiItem.setUptdate(new Date());
        ppmKpiItem.setUptuserid(userid);
        return myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
    @Override
    @Transactional("defaultTx")
    public int updateKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto,String userid){
        PpmKpiItemDto ppmKpiItemdto = JsonUtil.jsonToClass(ppmKpiItemInfoDto, PpmKpiItemDto.class);
        updatePpmKpiItem(ppmKpiItemdto,userid);
        List<PpmKpiItemDetailDto> item = JsonUtil.jsonToList(ppmKpiItemInfoDto.getDlist(), PpmKpiItemDetailDto.class);
        ppmKpiItemDetailService.updatePpmKpiItemDetails(item,userid,ppmKpiItemdto);
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int addKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto,String userid){
        PpmKpiItemDto ppmKpiItemdto = JsonUtil.jsonToClass(ppmKpiItemInfoDto, PpmKpiItemDto.class);
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemdto, PpmKpiItem.class);
        Long id = getNextSequence(PpmKpiItem.class);
        // 如果需要请set 主键id
        ppmKpiItem.setId(id);
        ppmKpiItem.setUptdate(new Date());
        ppmKpiItem.setUptuserid(userid);
        //ppmKpiItem.setItype(1L);
        myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.INSTER, "addPojo");
        List<PpmKpiItemDetailDto> item = JsonUtil.jsonToList(ppmKpiItemInfoDto.getDlist(), PpmKpiItemDetailDto.class);
        ppmKpiItemDetailService.addPpmKpiItemDetails(item,userid,id,ppmKpiItemInfoDto.getItype()+"");
        return 1;
    }
    @Override
    public void queryPagerListComprehensiveAbility(
            PpmKpiItemComprehensiveAbilityInfoDto ppmKpiItemComprehensiveAbilityInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiItemComprehensiveAbilityInfoDto,PpmKpiItemComprehensiveAbilityInfoDto.class);
        myBatisDao.selectPager(PPMKPIITEM, map,pager,"queryComprehensiveAbilityList");
    }

    @Override
    public List<PpmKpiItemComprehensiveAbilityInfoDto> getPpmKpiItemInfoListComprehensiveAbility(
            PpmKpiItemComprehensiveAbilityInfoDto ppmKpiItemComprehensiveAbilityInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiItemComprehensiveAbilityInfoDto,PpmKpiItemComprehensiveAbilityInfoDto.class);
        List<PpmKpiItemComprehensiveAbilityInfo> datas = myBatisDao.selectListT(
                PPMKPIITEM, map, "queryComprehensiveAbilityDatas");
        List<PpmKpiItemComprehensiveAbilityInfoDto> item = JsonUtil.jsonToList(datas,
                PpmKpiItemComprehensiveAbilityInfoDto.class);
        return item;
    }


    @Override
    public boolean importExcelComprehensiveAbility(MultipartFile file, String userid) {
        // excel导入数据
        List<PpmKpiItemComprehensiveAbilityInfoDto> list = ExcelUtil.importExcel(
                file, PpmKpiItemComprehensiveAbilityInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiItemComprehensiveAbilityInfoDto ii=list.get(i);
            String item=ii.getItem();
            PpmKpiItem kpiItem=getPpmKpiItemByItem(item,2L);
            if(null==kpiItem){
                PpmKpiItem kpi=new PpmKpiItem();
                Long id = getNextSequence(PpmKpiItem.class);
                // 如果需要请set 主键id
                kpi.setId(id);
                kpi.setItem(ii.getItem());
                kpi.setItype(2L);
                kpi.setWeight(ii.getWeight());
                kpi.setUptuserid(userid);
                kpi.setUptdate(new Date());
                myBatisDao.executionPojo(PPMKPIITEM, kpi, IMyBatisDao.SQL.INSTER,"addPojo");
                String standard=ii.getStandard();
                PpmKpiItemDetail detail=ppmKpiItemDetailService.getPpmKpiItemDetailByStandard(standard);
                if(null==detail){
                    PpmKpiItemDetail itemDetail =new PpmKpiItemDetail();
                    itemDetail.setStandard(ii.getStandard());
                    itemDetail.setWorkcontent(ii.getWorkcontent());
                    itemDetail.setValue(ii.getValue());
                    if(StringUtil.isNotBlank(ii.getFinishdateStr())){
                        itemDetail.setFinishdate(DateUtil.strToDate(ii.getFinishdateStr(),"yyyy-MM-dd"));
                    }
                    ppmKpiItemDetailService.addPpmKpiItemDetail(itemDetail,userid,id,"2");
                }else{
                    detail.setWorkcontent(ii.getWorkcontent());
                    detail.setValue(ii.getValue());
                    if(StringUtil.isNotBlank(ii.getFinishdateStr())){
                        detail.setFinishdate(DateUtil.strToDate(ii.getFinishdateStr(),"yyyy-MM-dd"));
                    }
                    ppmKpiItemDetailService.updatePpmKpiItemDetail(detail,userid,"2");
                }

            }else{
                kpiItem.setWeight(ii.getWeight());
                kpiItem.setUptuserid(userid);
                kpiItem.setUptdate(new Date());
                myBatisDao.executionPojo(PPMKPIITEM, kpiItem, IMyBatisDao.SQL.UPDATE,"updatePojo");
                String standard=ii.getStandard();
                PpmKpiItemDetail detail=ppmKpiItemDetailService.getPpmKpiItemDetailByStandard(standard);
                if(null==detail){
                    PpmKpiItemDetail itemDetail =new PpmKpiItemDetail();
                    itemDetail.setStandard(ii.getStandard());
                    itemDetail.setWorkcontent(ii.getWorkcontent());
                    itemDetail.setValue(ii.getValue());
                    if(StringUtil.isNotBlank(ii.getFinishdateStr())){
                        itemDetail.setFinishdate(DateUtil.strToDate(ii.getFinishdateStr(),"yyyy-MM-dd"));
                    }
                    ppmKpiItemDetailService.addPpmKpiItemDetail(itemDetail,userid,kpiItem.getId(),"2");
                }else{
                    detail.setWorkcontent(ii.getWorkcontent());
                    detail.setValue(ii.getValue());
                    if(StringUtil.isNotBlank(ii.getFinishdateStr())){
                        detail.setFinishdate(DateUtil.strToDate(ii.getFinishdateStr(),"yyyy-MM-dd"));
                    }
                    ppmKpiItemDetailService.updatePpmKpiItemDetail(detail,userid,"2");
                }
            }

        }
        return true;
    }
    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiItemListPostEvaluation(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmKpiItem ppmKpiItem = new PpmKpiItem();
        PpmKpiItemDetail ppmKpiItemDetail = new PpmKpiItemDetail();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmKpiItem.setId(Long.parseLong(idList[i]));
            myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.DELETE,"deleteById");
            ppmKpiItemDetail.setRid(Long.parseLong(idList[i]));
            ppmKpiItemDetailService.deletePpmKpiItemDetailByRid(ppmKpiItemDetail);
        }
        return 1;
    }


    @Override
    public void queryPagerListPostEvaluation(
            PpmKpiItemPostEvaluationInfoDto ppmKpiItemPostEvaluationInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiItemPostEvaluationInfoDto,PpmKpiItemPostEvaluationInfoDto.class);
        myBatisDao.selectPager(PPMKPIITEM, map,pager,"queryPostEvaluationList");
    }

    @Override
    public List<PpmKpiItemPostEvaluationInfoDto> getPpmKpiItemInfoListPostEvaluation(
            PpmKpiItemPostEvaluationInfoDto ppmKpiItemPostEvaluationInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiItemPostEvaluationInfoDto,PpmKpiItemPostEvaluationInfoDto.class);
        List<PpmKpiItemPostEvaluationInfo> datas = myBatisDao.selectListT(
                PPMKPIITEM, map, "queryPostEvaluationDatas");
        List<PpmKpiItemPostEvaluationInfoDto> item = JsonUtil.jsonToList(
                datas, PpmKpiItemPostEvaluationInfoDto.class);
        return item;
    }

    @Override
    @Transactional("defaultTx")
    public boolean importExcelPostEvaluation(MultipartFile file, String userid) {
        // 业务字典
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KPI_MAIN");
        // excel导入数据
        List<PpmKpiItemPostEvaluationInfoDto> list = ExcelUtil.importExcel(file, PpmKpiItemPostEvaluationInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiItemPostEvaluationInfoDto ii=list.get(i);
            String item=ii.getItem();
            PpmKpiItem kpiItem=getPpmKpiItemByItem(item,3L);
            if(null==kpiItem){
                PpmKpiItem kpi=new PpmKpiItem();
                Long id = getNextSequence(PpmKpiItem.class);
                // 如果需要请set 主键id
                kpi.setId(id);
                kpi.setItem(ii.getItem());
                kpi.setItype(3L);
                String mainpart=eos.stream().filter(iii-> ii.getMainpartname().
                        equals(iii.getDictname())).findFirst().get().getDictid();
                kpi.setMainpart(mainpart);
                kpi.setWeight(ii.getWeight());
                kpi.setUptuserid(userid);
                kpi.setUptdate(new Date());
                myBatisDao.executionPojo(PPMKPIITEM, kpi, IMyBatisDao.SQL.INSTER,"addPojo");
                String standard=ii.getStandard();
                PpmKpiItemDetail detail=ppmKpiItemDetailService.getPpmKpiItemDetailByStandard(standard);
                if(null==detail){
                    PpmKpiItemDetail itemDetail =new PpmKpiItemDetail();
                    itemDetail.setStandard(ii.getStandard());
                    itemDetail.setWorkcontent(ii.getWorkcontent());
                    itemDetail.setValue(ii.getValue());
                    itemDetail.setFinishdate(ii.getFinishdate());
                    ppmKpiItemDetailService.addPpmKpiItemDetail(itemDetail,userid,id,"3");
                }else{
                    detail.setValue(ii.getValue());
                    detail.setFinishdate(ii.getFinishdate());
                    detail.setWorkcontent(ii.getWorkcontent());
                    ppmKpiItemDetailService.updatePpmKpiItemDetail(detail,userid,"3");
                }
            }else{
                kpiItem.setWeight(ii.getWeight());
                kpiItem.setUptuserid(userid);
                kpiItem.setUptdate(new Date());
                String mainpart=eos.stream().filter(iii-> ii.getMainpartname().
                        equals(iii.getDictname())).findFirst().get().getDictid();
                kpiItem.setMainpart(mainpart);
                kpiItem.setItype(3L);
                myBatisDao.executionPojo(PPMKPIITEM, kpiItem, IMyBatisDao.SQL.UPDATE,"updatePojo");
                String standard=ii.getStandard();
                PpmKpiItemDetail detail=ppmKpiItemDetailService.getPpmKpiItemDetailByStandard(standard);
                if(null==detail){
                    PpmKpiItemDetail itemDetail =new PpmKpiItemDetail();
                    itemDetail.setStandard(ii.getStandard());
                    itemDetail.setWorkcontent(ii.getWorkcontent());
                    itemDetail.setValue(ii.getValue());
                    itemDetail.setFinishdate(ii.getFinishdate());
                    ppmKpiItemDetailService.addPpmKpiItemDetail(itemDetail,userid,kpiItem.getId(),"3");
                }else{
                    detail.setValue(ii.getValue());
                    detail.setFinishdate(ii.getFinishdate());
                    detail.setWorkcontent(ii.getWorkcontent());
                    ppmKpiItemDetailService.updatePpmKpiItemDetail(detail,userid,"3");
                }
            }

        }
        return true;
    }

    @Override
    public void queryPagerListBalance(PpmKpiItemBalanceInfoDto ppmKpiItemBalanceInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemBalanceInfoDto,PpmKpiItemBalanceInfoDto.class);
        myBatisDao.selectPager(PPMKPIITEM, map,pager,"queryBalanceDatas");
    }

    @Override
    public List<PpmKpiItemBalanceInfoDto> getPpmKpiItemInfoListBalance(
            PpmKpiItemBalanceInfoDto ppmKpiItemBalanceInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemBalanceInfoDto,PpmKpiItemBalanceInfoDto.class);
        List<PpmKpiItemBalanceInfo> datas = myBatisDao.selectListT(PPMKPIITEM, map, "queryBalanceDatas");
        List<PpmKpiItemBalanceInfoDto> item = JsonUtil.jsonToList(datas, PpmKpiItemBalanceInfoDto.class);
        return item;
    }


    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiItemListBalance(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmKpiItem ppmKpiItem = new PpmKpiItem();
        PpmKpiItemDetail ppmKpiItemDetail = new PpmKpiItemDetail();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmKpiItem.setId(Long.parseLong(idList[i]));
            myBatisDao.executionPojo(PPMKPIITEM, ppmKpiItem, IMyBatisDao.SQL.DELETE,"deleteById");
            ppmKpiItemDetail.setRid(Long.parseLong(idList[i]));
            ppmKpiItemDetailService.deletePpmKpiItemDetailByRid(ppmKpiItemDetail);
        }
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public boolean importExcelBalance(MultipartFile file, String userid) {
        // excel导入数据
        List<PpmKpiItemBalanceInfoDto> list = ExcelUtil.importExcel(file, PpmKpiItemBalanceInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiItemBalanceInfoDto ii=list.get(i);
            String item=ii.getItem();
            PpmKpiItem kpiItem=getPpmKpiItemByItem(item,4L);
            if(null==kpiItem){
                PpmKpiItem kpi=new PpmKpiItem();
                Long id = getNextSequence(PpmKpiItem.class);
                // 如果需要请set 主键id
                kpi.setId(id);
                kpi.setItem(ii.getItem());
                kpi.setItype(4L);
                kpi.setUptuserid(userid);
                kpi.setUptdate(new Date());
                myBatisDao.executionPojo(PPMKPIITEM, kpi, IMyBatisDao.SQL.INSTER,"addPojo");
                String standard=ii.getStandard();
                PpmKpiItemDetail detail=ppmKpiItemDetailService.getPpmKpiItemDetailByStandard(standard);
                if(null==detail){
                    PpmKpiItemDetail itemDetail =new PpmKpiItemDetail();
                    itemDetail.setStandard(ii.getStandard());
                    itemDetail.setWorkcontent(ii.getWorkcontent());
                    ppmKpiItemDetailService.addPpmKpiItemDetail(itemDetail,userid,id,"4");
                }else{
                    detail.setWorkcontent(ii.getWorkcontent());
                    ppmKpiItemDetailService.updatePpmKpiItemDetail(detail,userid,"4");
                }
            }else{
                String standard=ii.getStandard();
                PpmKpiItemDetail detail=ppmKpiItemDetailService.getPpmKpiItemDetailByStandard(standard);
                if(null==detail){
                    PpmKpiItemDetail itemDetail =new PpmKpiItemDetail();
                    itemDetail.setStandard(ii.getStandard());
                    itemDetail.setWorkcontent(ii.getWorkcontent());
                    ppmKpiItemDetailService.addPpmKpiItemDetail(itemDetail,userid,kpiItem.getId(),"4");
                }else{
                    detail.setWorkcontent(ii.getWorkcontent());
                    ppmKpiItemDetailService.updatePpmKpiItemDetail(detail,userid,"4");
                }
            }
        }
        return true;
    }



    @Override
    public List<PpmKpiItemInsertInfoDto> querykpiItemAll(PpmKpiItemDto ppmKpiItemDto){
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemDto,PpmKpiItemDto.class);
        List<PpmKpiItemInsertInfo> datas = myBatisDao.selectListT(PPMKPIITEM, map, "querykpiItemAll");
        List<PpmKpiItemInsertInfoDto> items = JsonUtil.jsonToList(datas, PpmKpiItemInsertInfoDto.class);
        return items;
    }


    @Override
    public List<PpmKpiItemDto> getKpiItems(PpmKpiItemDto ppmKpiItemDto) {
        PpmKpiItem ppmKpiItem = JsonUtil.jsonToClass(ppmKpiItemDto,PpmKpiItem.class);
        List<PpmKpiItem> datas = myBatisDao.selectListT(PPMKPIITEM, ppmKpiItem, "getKpiItems");
        List<PpmKpiItemDto> item = JsonUtil.jsonToList(datas, PpmKpiItemDto.class);
        return item;
    }

}
