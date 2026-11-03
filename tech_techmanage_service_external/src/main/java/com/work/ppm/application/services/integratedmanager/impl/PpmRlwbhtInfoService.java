package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.services.othermanager.impl.PpmOutsourcingInfoService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmRlwbhtInfo;
import com.work.ppm.application.dto.integratedmanager.PpmRlwbhtInfoDto;
import com.work.ppm.application.services.integratedmanager.IPpmRlwbhtInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmRlwbhtInfoService")
public class PpmRlwbhtInfoService extends BaseDaoSupper implements IPpmRlwbhtInfoService {
    /**
      * 实体类路径
      */
    private static String PPMRLWBHTINFO = "com.work.ppm.application.model.integratedmanager.PpmRlwbhtInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 外包商
     */
    @Autowired
    private PpmOutsourcingInfoService ppmOutsourcingInfoService;

    @Override
    public void queryPager(PpmRlwbhtInfoDto ppmRlwbhtInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmRlwbhtInfoDto,PpmRlwbhtInfoDto.class);
        myBatisDao.selectPager(PPMRLWBHTINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        PpmRlwbhtInfo ppmRlwbhtInfo = JsonUtil.jsonToClass(ppmRlwbhtInfoDto, PpmRlwbhtInfo.class);
        Long id = getNextSequence(PpmRlwbhtInfo.class);

        // 如果需要请set 主键id
        //ppmRlwbhtInfo.setId(id.intValue());
        myBatisDao.executionPojo(PPMRLWBHTINFO, ppmRlwbhtInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmRlwbhtInfoDto queryDetail(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        PpmRlwbhtInfo ppmRlwbhtInfo = JsonUtil.jsonToClass(ppmRlwbhtInfoDto, PpmRlwbhtInfo.class);
        PpmRlwbhtInfo data = myBatisDao.selectClass(PPMRLWBHTINFO, ppmRlwbhtInfo,
        "queryDetail");
        PpmRlwbhtInfoDto item = JsonUtil.jsonToClass(data, PpmRlwbhtInfoDto
        .class);
        return item;
    }

    @Override
    public int updatePpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        PpmRlwbhtInfo ppmRlwbhtInfo = JsonUtil.jsonToClass(ppmRlwbhtInfoDto, PpmRlwbhtInfo.class);
        return myBatisDao.executionPojo(PPMRLWBHTINFO, ppmRlwbhtInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        PpmRlwbhtInfo ppmRlwbhtInfo = JsonUtil.jsonToClass(ppmRlwbhtInfoDto, PpmRlwbhtInfo.class);
        return myBatisDao.executionPojo(PPMRLWBHTINFO, ppmRlwbhtInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    /**
     * 插入数据
     * @param ppmRlwbhtInfo
     * @return int
     */
    private int insertPpmRlwbhtInfo(PpmRlwbhtInfo ppmRlwbhtInfo) {
        myBatisDao.executionPojo(PPMRLWBHTINFO, ppmRlwbhtInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public List<PpmRlwbhtInfoDto> queryOrInsertPpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        List<PpmRlwbhtInfoDto> dtoList=new ArrayList<PpmRlwbhtInfoDto>();
        LocalDate local= LocalDate.now();
        DateTimeFormatter f=DateTimeFormatter.ofPattern("yyyy");
        PpmRlwbhtInfo ppmRlwbhtInfo = JsonUtil.jsonToClass(ppmRlwbhtInfoDto, PpmRlwbhtInfo.class);
        ppmRlwbhtInfo.setYear(local.format(f));
        List<PpmRlwbhtInfo> list = myBatisDao.selectListT(PPMRLWBHTINFO, ppmRlwbhtInfo, "getList");
        if(list.size()==0){
            PpmOutsourcingInfo info=new PpmOutsourcingInfo();
            info.setOrgcode(ppmRlwbhtInfo.getOrgcode());
            info =ppmOutsourcingInfoService.queryDetailByOrgCode(info);
            if(null!=info){
                ppmRlwbhtInfo.setHtmc("借用人力合同("+info.getAbbreviation()+")");
                ppmRlwbhtInfo.setHtid(info.getOrgcode()+"_"+local.format(f));
                //ppmRlwbhtInfo.setYear(local.format(f));
                int i =insertPpmRlwbhtInfo(ppmRlwbhtInfo);
                if(i==1){
                    dtoList= queryOrInsertPpmRlwbhtInfo(ppmRlwbhtInfoDto);
                }
            }
        }else{
            dtoList = JsonUtil.jsonToList(list, PpmRlwbhtInfoDto.class);

        }
        return dtoList;
   }
    @Override
    public PpmRlwbhtInfo queryDetail(PpmRlwbhtInfo ppmRlwbhtInfo) {
        PpmRlwbhtInfo data = myBatisDao.selectClass(PPMRLWBHTINFO, ppmRlwbhtInfo, "queryDetailByParam");
        return data;
    }
}
