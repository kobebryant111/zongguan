package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmKqProfesstion;
import com.work.ppm.application.dto.integratedmanager.PpmKqProfesstionDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqProfesstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKqProfesstionService")
public class PpmKqProfesstionService extends BaseDaoSupper implements IPpmKqProfesstionService {
    /**
      * 实体类路径
      */
    private static String PPMKQPROFESSTION = "com.work.ppm.application.model.integratedmanager.PpmKqProfesstion";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKqProfesstionDto ppmKqProfesstionDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqProfesstionDto,PpmKqProfesstionDto.class);
        myBatisDao.selectPager(PPMKQPROFESSTION, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKqProfesstion(PpmKqProfesstionDto ppmKqProfesstionDto) {
        PpmKqProfesstion ppmKqProfesstion = JsonUtil.jsonToClass(ppmKqProfesstionDto, PpmKqProfesstion.class);
        Long id = getNextSequence(PpmKqProfesstion.class);

        // 如果需要请set 主键id
        //ppmKqProfesstion.setId(id.intValue());
        myBatisDao.executionPojo(PPMKQPROFESSTION, ppmKqProfesstion, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKqProfesstionDto queryDetail(PpmKqProfesstionDto ppmKqProfesstionDto) {
        PpmKqProfesstion ppmKqProfesstion = JsonUtil.jsonToClass(ppmKqProfesstionDto, PpmKqProfesstion.class);
        PpmKqProfesstion data = myBatisDao.selectClass(PPMKQPROFESSTION, ppmKqProfesstion,
        "queryDetail");
        PpmKqProfesstionDto item = JsonUtil.jsonToClass(data, PpmKqProfesstionDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKqProfesstion(PpmKqProfesstionDto ppmKqProfesstionDto) {
        PpmKqProfesstion ppmKqProfesstion = JsonUtil.jsonToClass(ppmKqProfesstionDto, PpmKqProfesstion.class);
        return myBatisDao.executionPojo(PPMKQPROFESSTION, ppmKqProfesstion, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKqProfesstion(PpmKqProfesstionDto ppmKqProfesstionDto) {
        PpmKqProfesstion ppmKqProfesstion = JsonUtil.jsonToClass(ppmKqProfesstionDto, PpmKqProfesstion.class);
        return myBatisDao.executionPojo(PPMKQPROFESSTION, ppmKqProfesstion, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    @Override
    public List<PpmKqProfesstionDto> getPpmKqProfesstions(PpmKqProfesstionDto ppmKqProfesstionDto){
        PpmKqProfesstion ppmKqProfesstion = JsonUtil.jsonToClass(ppmKqProfesstionDto, PpmKqProfesstion.class);
        List<PpmKqProfesstion> professions= myBatisDao.selectListT(PPMKQPROFESSTION, ppmKqProfesstion,"getList");
        List<PpmKqProfesstionDto> dtos=JsonUtil.jsonToList(professions, PpmKqProfesstionDto.class);
        return dtos;
    }
}
