package com.work.ppm.application.services.otherdeptmanager.impl;

import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.otherdeptmanager.ext.PpmDevelopGroupLedgerExt;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.otherdeptmanager.PpmDevelopGroupLedger;
import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupLedgerDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmDevelopGroupLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
  *  研发小组台账 接口实现类
  */
@Service("ppmDevelopGroupLedgerService")
public class PpmDevelopGroupLedgerService extends BaseDaoSupper implements IPpmDevelopGroupLedgerService {
    /**
      * 实体类路径
      */
    private static String PPMDEVELOPGROUPLEDGER = "com.work.ppm.application.model.otherdeptmanager.PpmDevelopGroupLedger";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDevelopGroupLedgerDto,PpmDevelopGroupLedgerDto.class);
        myBatisDao.selectPager(PPMDEVELOPGROUPLEDGER, map,pager,"queryBaseInfoList");
        List<PpmDevelopGroupLedgerDto> list = JsonUtil.jsonToList(pager.getList(),PpmDevelopGroupLedgerDto.class);
        list.forEach(dto -> dto.setDeptName(BusinessUtil.getDeptNameValue(dto.getDeptName(),1)));
        pager.setList(list);
    }

    @Override
    public int addPpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
        PpmDevelopGroupLedger ppmDevelopGroupLedger = JsonUtil.jsonToClass(ppmDevelopGroupLedgerDto, PpmDevelopGroupLedger.class);
        // 如果需要请set 主键id
        ppmDevelopGroupLedger.setId(DateUtil.getDateRandom());
        myBatisDao.executionPojo(PPMDEVELOPGROUPLEDGER, ppmDevelopGroupLedger, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
//    @Override
//    public PpmDevelopGroupLedgerDto queryDetail(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
//        PpmDevelopGroupLedger ppmDevelopGroupLedger = JsonUtil.jsonToClass(ppmDevelopGroupLedgerDto, PpmDevelopGroupLedger.class);
//        PpmDevelopGroupLedger data = myBatisDao.selectClass(PPMDEVELOPGROUPLEDGER, ppmDevelopGroupLedger,"queryDetail");
//        return JsonUtil.jsonToClass(data, PpmDevelopGroupLedgerDto.class);
//    }

    @Override
    public int updatePpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
        PpmDevelopGroupLedger ppmDevelopGroupLedger = JsonUtil.jsonToClass(ppmDevelopGroupLedgerDto, PpmDevelopGroupLedger.class);
        return myBatisDao.executionPojo(PPMDEVELOPGROUPLEDGER, ppmDevelopGroupLedger, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

//    @Override
//    public int deletePpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
//        PpmDevelopGroupLedger ppmDevelopGroupLedger = JsonUtil.jsonToClass(ppmDevelopGroupLedgerDto, PpmDevelopGroupLedger.class);
//        return myBatisDao.executionPojo(PPMDEVELOPGROUPLEDGER, ppmDevelopGroupLedger, IMyBatisDao.SQL.DELETE,"deleteById");
//    }

    @Override
    public List<PpmDevelopGroupLedgerDto> exportPpmDevelopGroupLedger(PpmDevelopGroupLedgerDto developGroupLedgerDto) {
        List<PpmDevelopGroupLedgerExt> data =
                myBatisDao.selectListT(PPMDEVELOPGROUPLEDGER, developGroupLedgerDto, XmlIdEnums.BASES.getCode());
        data.forEach(obj -> {
            obj.setIfStarPerson(obj.getIfStarPerson() == null ? "" : obj.getIfStarPerson());
            obj.setDeptName(BusinessUtil.getDeptNameValue(obj.getDeptName(),1));
        });
        return JsonUtil.jsonToList(data, PpmDevelopGroupLedgerDto.class);
    }
}
