package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchItemDto;
import com.work.ppm.application.model.kpi.PpmKpiItemSwitch;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchItemService;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiItemSwitchService")
public class PpmKpiItemSwitchService extends BaseDaoSupper implements IPpmKpiItemSwitchService {
    /**
      * 实体类路径
      */
    private static String PPMKPIITEMSWITCH = "com.work.ppm.application.model.kpi.PpmKpiItemSwitch";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 考核配置项接口
     */
    @Autowired
    private IPpmKpiItemSwitchItemService ppmKpiItemSwitchItemService;

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmKpiItemSwitchDto ppmKpiItemSwitchDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemSwitchDto,PpmKpiItemSwitchDto.class);
        myBatisDao.selectPager(PPMKPIITEMSWITCH, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        PpmKpiItemSwitch ppmKpiItemSwitch = JsonUtil.jsonToClass(ppmKpiItemSwitchDto, PpmKpiItemSwitch.class);
        Long id = getNextSequence(PpmKpiItemSwitch.class);

        // 如果需要请set 主键id
        ppmKpiItemSwitch.setId(id);
        myBatisDao.executionPojo(PPMKPIITEMSWITCH, ppmKpiItemSwitch, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiItemSwitchDto queryDetail(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        PpmKpiItemSwitch ppmKpiItemSwitch = JsonUtil.jsonToClass(ppmKpiItemSwitchDto, PpmKpiItemSwitch.class);
        PpmKpiItemSwitch data = myBatisDao.selectClass(PPMKPIITEMSWITCH, ppmKpiItemSwitch,
        "queryDetail");
        PpmKpiItemSwitchDto item = JsonUtil.jsonToClass(data, PpmKpiItemSwitchDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        PpmKpiItemSwitch ppmKpiItemSwitch = JsonUtil.jsonToClass(ppmKpiItemSwitchDto, PpmKpiItemSwitch.class);
        return myBatisDao.executionPojo(PPMKPIITEMSWITCH, ppmKpiItemSwitch, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
    @Override
    public int deletePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        PpmKpiItemSwitch ppmKpiItemSwitch = JsonUtil.jsonToClass(ppmKpiItemSwitchDto, PpmKpiItemSwitch.class);
        return myBatisDao.executionPojo(PPMKPIITEMSWITCH, ppmKpiItemSwitch, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    @Override
    public List<PpmKpiItemSwitchDto> getPpmKpiItemSwitchList(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemSwitchDto,PpmKpiItemSwitchDto.class);
        List<PpmKpiItemSwitch> datas = myBatisDao.selectListT(PPMKPIITEMSWITCH, map, "queryPagerList");
        List<PpmKpiItemSwitchDto> item = JsonUtil.jsonToList(datas, PpmKpiItemSwitchDto.class);
        return item;
    }
    /**
     * 添加考核项配置
     * @param ppmKpiItemSwitch
     * @param userid
     * @return int
     */
    private int addPpmKpiItemSwitch(PpmKpiItemSwitch ppmKpiItemSwitch,String userid) {
        Long id = getNextSequence(PpmKpiItemSwitch.class);
        // 如果需要请set 主键id
        ppmKpiItemSwitch.setId(id);
        ppmKpiItemSwitch.setUptdate(new Date());
        ppmKpiItemSwitch.setUptuserid(userid);
        myBatisDao.executionPojo(PPMKPIITEMSWITCH, ppmKpiItemSwitch, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public int createPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,String userid) {
        PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto=new PpmKpiItemSwitchItemDto();
        List<PpmKpiItemSwitchItemDto> lists=ppmKpiItemSwitchItemService.
                getPpmKpiItemSwitchItemList(ppmKpiItemSwitchItemDto);
        for(int i=0;i<lists.size();i++){
            ppmKpiItemSwitchDto.setItemtype(lists.get(i).getItemtype());
            List<PpmKpiItemSwitchDto> items =getPpmKpiItemSwitchList(ppmKpiItemSwitchDto);
            if(items.size()>0){
                continue;
            }else{
                PpmKpiItemSwitch ppmKpiItemSwitch=new PpmKpiItemSwitch();
                ppmKpiItemSwitch.setTyear(ppmKpiItemSwitchDto.getTyear());
                ppmKpiItemSwitch.setTerm(lists.get(i).getTerm());
                ppmKpiItemSwitch.setContent(lists.get(i).getContent());
                ppmKpiItemSwitch.setRelroles(lists.get(i).getRelroles());
                ppmKpiItemSwitch.setIslaunch(lists.get(i).getIslaunch());
                ppmKpiItemSwitch.setIsswitch(lists.get(i).getIsswitch());
                ppmKpiItemSwitch.setItype(lists.get(i).getItype());
                ppmKpiItemSwitch.setLaunchtime(lists.get(i).getLaunchtime());
                ppmKpiItemSwitch.setIsdisabled(lists.get(i).getIsdisabled());
                ppmKpiItemSwitch.setIsvisible(lists.get(i).getIsvisible());
                ppmKpiItemSwitch.setItemtype(lists.get(i).getItemtype());
                if(null!=lists.get(i).getFinishtime()&&!"".equals(lists.get(i).getFinishtime())){
                    ppmKpiItemSwitch.setFinishtime(ppmKpiItemSwitchDto.getTyear()+"-"+lists.get(i).getFinishtime());
                }

//                if(null!=lists.get(i).getEndtime()&&!"".equals(lists.get(i).getEndtime())){
//                    ppmKpiItemSwitch.setEndtime(ppmKpiItemSwitchDto.getTyear()+"-"+lists.get(i).getEndtime());
//                }
                addPpmKpiItemSwitch(ppmKpiItemSwitch,userid);
            }
        }
        EosDictEntry eos =new EosDictEntry();
        eos.setDicttypeid("KPI_TIME");
        eos.setDictid("tyear");
        eos.setDictname(ppmKpiItemSwitchDto.getTyear());
        eosDictEntryService.updateEosDictEntry(eos);
        return 1;
    }


    @Override
    public int updatePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,String userid) {
        PpmKpiItemSwitch ppmKpiItemSwitch = JsonUtil.jsonToClass(ppmKpiItemSwitchDto, PpmKpiItemSwitch.class);
//        if("5".equals(ppmKpiItemSwitch.getItemtype())||"6".equals(ppmKpiItemSwitch.getItemtype())){
//            if(ppmKpiItemSwitch.getIsswitch()==0){
//                ppmKpiItemSwitch.setItype(ppmKpiItemSwitch.getIsswitch()+"");
//            }else{
//                ppmKpiItemSwitch.setItype(ppmKpiItemSwitch.getIsswitch()+"");
//            }
//        }
        ppmKpiItemSwitch.setUptuserid(userid);
        ppmKpiItemSwitch.setUptdate(new Date());
        return myBatisDao.executionPojo(PPMKPIITEMSWITCH, ppmKpiItemSwitch, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int addEvalSet(EosDictEntry eos){
        return eosDictEntryService.addEosDictEntry(eos);
    }

    @Override
    public int delEvalSet(EosDictEntry eos){
        return eosDictEntryService.deleteEosDictEntry(eos);
    }

    @Override
    public int updateEvalSet(EosDictEntry eos) {
        return eosDictEntryService.updateEosDictEntry(eos);
    }
    @Override
    public List<EosDictEntry> getEosDictEntrys (EosDictEntry eos){
        return eosDictEntryService.getTypeidForList(eos.getDicttypeid());
    }

    @Override
    public int updateLunchPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,String userid) {
        PpmKpiItemSwitch ppmKpiItemSwitch = JsonUtil.jsonToClass(ppmKpiItemSwitchDto, PpmKpiItemSwitch.class);
        if(ppmKpiItemSwitchDto.getIslaunch()==1){
            ppmKpiItemSwitch.setLaunchtime(new Date());
        }
        ppmKpiItemSwitch.setUptuserid(userid);
        ppmKpiItemSwitch.setUptdate(new Date());
        return myBatisDao.executionPojo(PPMKPIITEMSWITCH, ppmKpiItemSwitch, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public PpmKpiItemSwitchDto queryDetailByCondition(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemSwitchDto,PpmKpiItemSwitchDto.class);
        List<PpmKpiItemSwitch> datas = myBatisDao.selectListT(PPMKPIITEMSWITCH, map, "queryPagerList");
        List<PpmKpiItemSwitchDto> item = JsonUtil.jsonToList(datas, PpmKpiItemSwitchDto.class);
        if(item.size()>0){
            return item.get(0);
        }else{
            return null;
        }

    }
}
