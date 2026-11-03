package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.PpmKpiItemDetailInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import com.work.ppm.application.model.kpi.ext.PpmKpiItemDetailInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiItemDetail;
import com.work.ppm.application.dto.kpi.PpmKpiItemDetailDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemDetailService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiItemDetailService")
public class PpmKpiItemDetailService extends BaseDaoSupper implements IPpmKpiItemDetailService {
    /**
      * 实体类路径
      */
    private static String PPMKPIITEMDETAIL = "com.work.ppm.application.model.kpi.PpmKpiItemDetail";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public void queryPager(PpmKpiItemDetailDto ppmKpiItemDetailDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemDetailDto,PpmKpiItemDetailDto.class);
        myBatisDao.selectPager(PPMKPIITEMDETAIL, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        PpmKpiItemDetail ppmKpiItemDetail = JsonUtil.jsonToClass(ppmKpiItemDetailDto, PpmKpiItemDetail.class);
        Long id = getNextSequence(PpmKpiItemDetail.class);

        // 如果需要请set 主键id
        ppmKpiItemDetail.setId(id);
        myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiItemDetailDto queryDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        PpmKpiItemDetail ppmKpiItemDetail = JsonUtil.jsonToClass(ppmKpiItemDetailDto, PpmKpiItemDetail.class);
        PpmKpiItemDetail data = myBatisDao.selectClass(PPMKPIITEMDETAIL, ppmKpiItemDetail,
        "queryDetail");
        PpmKpiItemDetailDto item = JsonUtil.jsonToClass(data, PpmKpiItemDetailDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        PpmKpiItemDetail ppmKpiItemDetail = JsonUtil.jsonToClass(ppmKpiItemDetailDto, PpmKpiItemDetail.class);
        return myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        PpmKpiItemDetail ppmKpiItemDetail = JsonUtil.jsonToClass(ppmKpiItemDetailDto, PpmKpiItemDetail.class);
        return myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.DELETE,"deleteById");
    }


    @Override
    public int deletePpmKpiItemDetailByRid (PpmKpiItemDetail ppmKpiItemDetail) {
        return myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.DELETE,"deleteByRid");
    }

    @Override
    public List<PpmKpiItemDetailInfoDto> getPpmKpiItemDetailInfoList(PpmKpiItemDetailInfoDto ppmKpiItemDetailInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemDetailInfoDto,PpmKpiItemDetailInfoDto.class);
        List<PpmKpiItemDetailInfo> datas = myBatisDao.selectListT(PPMKPIITEMDETAIL, map, "getPpmKpiItemDetailInfo");
        List<PpmKpiItemDetailInfoDto> item = JsonUtil.jsonToList(datas, PpmKpiItemDetailInfoDto.class);
        return item;
    }


    @Override
    public int  updatePpmKpiItemDetails(List<PpmKpiItemDetailDto> ppmKpiItemDetailDtos,
                                        String userid, PpmKpiItemDto ppmKpiItemdto) {
        for(int i=0;i<ppmKpiItemDetailDtos.size();i++) {
            PpmKpiItemDetail ppmKpiItemDetail = JsonUtil.jsonToClass(ppmKpiItemDetailDtos.get(i),
                    PpmKpiItemDetail.class);
            if(null!=ppmKpiItemDetail.getId()){
                if(ppmKpiItemdto.getItype()==2||ppmKpiItemdto.getItype()==3){
                    if(!StringUtil.isNotEmpty(ppmKpiItemDetail.getValue())){
                        ppmKpiItemDetail.setValue(100.00);
                    }
                    if(StringUtil.isNotEmpty(ppmKpiItemDetail.getFinishdate())){
                        Date now = new Date();
                        String year = DateUtil.dateToStr(now, "yyyy");
                        Date endDate=DateUtil.strToDate(getLastDayOfMonth(Integer.parseInt(year),11),"yyyy-MM-dd");
                        ppmKpiItemDetail.setFinishdate(endDate);
                    }
                }
                ppmKpiItemDetail.setUptdate(new Date());
                ppmKpiItemDetail.setUptuserid(userid);
                 myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail,
                        IMyBatisDao.SQL.UPDATE, "updatePojo");
            }else{
                Long id = getNextSequence(PpmKpiItemDetail.class);
                // 如果需要请set 主键id
                ppmKpiItemDetail.setId(id);
                ppmKpiItemDetail.setUptuserid(userid);
                ppmKpiItemDetail.setUptdate(new Date());
                ppmKpiItemDetail.setRid(ppmKpiItemdto.getId());
                ppmKpiItemDetail.setIsdel(0L);
                if(ppmKpiItemdto.getItype()==2||ppmKpiItemdto.getItype()==3) {
                    if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getValue())){
                        ppmKpiItemDetail.setValue(100.00);
                    }
                    if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getFinishdate())) {
                        Date now = new Date();
                        String year = DateUtil.dateToStr(now, "yyyy");
                        Date endDate=DateUtil.strToDate(getLastDayOfMonth(Integer.parseInt(year),11),"yyyy-MM-dd");
                        ppmKpiItemDetail.setFinishdate(endDate);
                    }
                }
                myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.INSTER,
                        "addPojo");
            }

        }
        return 1;
    }

    /**
     * 获得某年某月的最后一天
     * @param year
     * @param month
     * @return  Date
     */
    private String getLastDayOfMonth(int year,int month){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        int lastDay=cal.getMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,lastDay);
        return DateUtil.dateToStr(cal.getTime(),"yyyy-MM-dd");
    }
    @Override
    public int addPpmKpiItemDetails(List<PpmKpiItemDetailDto> ppmKpiItemDetailDtos,
                                    String userid,long rid,String itype) {
        for(int i=0;i<ppmKpiItemDetailDtos.size();i++) {
            PpmKpiItemDetail ppmKpiItemDetail = JsonUtil.jsonToClass(ppmKpiItemDetailDtos.get(i),
                    PpmKpiItemDetail.class);
            Long id = getNextSequence(PpmKpiItemDetail.class);
            // 如果需要请set 主键id
            ppmKpiItemDetail.setId(id);
            ppmKpiItemDetail.setUptuserid(userid);
            ppmKpiItemDetail.setUptdate(new Date());
            ppmKpiItemDetail.setRid(rid);
            ppmKpiItemDetail.setIsdel(0L);
            if("2".equals(itype)||"3".equals(itype)) {
                if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getValue())){
                    ppmKpiItemDetail.setValue(100.00);
                }
                if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getFinishdate())) {
                    Date now = new Date();
                    String year = DateUtil.dateToStr(now, "yyyy");
                    Date endDate=DateUtil.strToDate(getLastDayOfMonth(Integer.parseInt(year),11),"yyyy-MM-dd");
                    ppmKpiItemDetail.setFinishdate(endDate);
                }
            }
            myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.INSTER,
                    "addPojo");
        }
        return 1;
    }

    @Override
    public int  updatePpmKpiItemDetail(PpmKpiItemDetail ppmKpiItemDetail,String userid,String itype) {
        int retCode =0;
            if(StringUtil.isNotEmpty(ppmKpiItemDetail)){
                if("2".equals(itype)||"3".equals(itype)) {
                    if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getValue())) {
                        ppmKpiItemDetail.setValue(100.00);
                    }
                    if (StringUtil.isNotEmpty(ppmKpiItemDetail.getFinishdate())) {
                        Date now = new Date();
                        String year = DateUtil.dateToStr(now, "yyyy");
                        Date endDate=DateUtil.strToDate(getLastDayOfMonth(Integer.parseInt(year),11),"yyyy-MM-dd");
                        ppmKpiItemDetail.setFinishdate(endDate);
                    }
                }
                ppmKpiItemDetail.setUptdate(new Date());
                ppmKpiItemDetail.setUptuserid(userid);
                retCode=myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail,
                        IMyBatisDao.SQL.UPDATE, "updatePojo");
            }
        return retCode;
    }

    @Override
    public int addPpmKpiItemDetail(PpmKpiItemDetail ppmKpiItemDetail, String userid, long rid,String itype) {
        int retCode=0;
        Long id = getNextSequence(PpmKpiItemDetail.class);
        // 如果需要请set 主键id
        ppmKpiItemDetail.setId(id);
        ppmKpiItemDetail.setUptuserid(userid);
        ppmKpiItemDetail.setUptdate(new Date());
        ppmKpiItemDetail.setRid(rid);
        ppmKpiItemDetail.setIsdel(0L);
        if("2".equals(itype)||"3".equals(itype)) {
            if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getValue())) {
                ppmKpiItemDetail.setValue(100.00);
            }
            if (!StringUtil.isNotEmpty(ppmKpiItemDetail.getFinishdate())) {
                Date now = new Date();
                String year = DateUtil.dateToStr(now, "yyyy");
                Date endDate=DateUtil.strToDate(getLastDayOfMonth(Integer.parseInt(year),11),"yyyy-MM-dd");
                ppmKpiItemDetail.setFinishdate(endDate);
            }
        }
        retCode=myBatisDao.executionPojo(PPMKPIITEMDETAIL, ppmKpiItemDetail, IMyBatisDao.SQL.INSTER, "addPojo");
        return retCode;
    }
    @Override
    public PpmKpiItemDetail getPpmKpiItemDetailByStandard(String standard){
        PpmKpiItemDetail ppmKpiItemDetail=new PpmKpiItemDetail();
        ppmKpiItemDetail.setStandard(standard);
        PpmKpiItemDetail data = myBatisDao.selectClass(PPMKPIITEMDETAIL, ppmKpiItemDetail, "queryKpiDetailByStandard");
        return data;
    }
}
