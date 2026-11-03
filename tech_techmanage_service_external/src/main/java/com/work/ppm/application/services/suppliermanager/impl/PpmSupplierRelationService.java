package com.work.ppm.application.services.suppliermanager.impl;

import com.work.ppm.application.dto.suppliermanager.PpmSupplierRelationDto;
import com.work.ppm.application.model.suppliermanager.PpmSupplierRelation;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRelationService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
  *  供应商考核信息表 接口实现类
  */
@Service("ppmSupplierRelationService")
public class PpmSupplierRelationService extends BaseDaoSupper implements IPpmSupplierRelationService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERRELATION = "com.work.ppm.application.model.suppliermanager.PpmSupplierRelation";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PpmSupplierRelationDto> queryTypeList(String type,PpmSupplierRelationDto ppmSupplierRelationDto) {
        String uniqueName = "getList";
        if(StringUtils.isNotEmpty(type)){
            if(type.contains("RL") || type.contains("XM")){
                uniqueName = "getSimpleKhList";
            }else if(type.contains("PG")){
                uniqueName = "getSimplePgList";
            }
        }

        if(ppmSupplierRelationDto == null){
            ppmSupplierRelationDto = new PpmSupplierRelationDto();
            ppmSupplierRelationDto.setType(type);
        }

        return queryDetailList(ppmSupplierRelationDto,uniqueName);
    }

    @Override
    public List<PpmSupplierRelationDto> queryDetailList(
            PpmSupplierRelationDto ppmSupplierRelationDto,String uniqueName) {
        PpmSupplierRelation ppmSupplierRelation =
                JsonUtil.jsonToClass(ppmSupplierRelationDto, PpmSupplierRelation.class);
        List<PpmSupplierRelation> data =
                myBatisDao.selectListT(PPMSUPPLIERRELATION, ppmSupplierRelation,uniqueName);
        return JsonUtil.jsonToList(data,PpmSupplierRelationDto.class);
    }

    @Override
    public List<String> queryDetailKey(String type){
        List<String> keyList = new ArrayList<>();

        List<PpmSupplierRelationDto> relationDtos = queryTypeList(type,null);
        if(relationDtos != null){
            relationDtos.forEach(relationDto ->{
                keyList.add(relationDto.getKey());
            });
        }

        return keyList;
    }

}
