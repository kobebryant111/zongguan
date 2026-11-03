package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.PpmDaCenter;
import com.work.ppm.application.dto.systemmanager.PpmDaCenterDto;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 中心接口
  */
@Service("ppmDaCenterService")
public class PpmDaCenterService extends BaseDaoSupper implements IPpmDaCenterService {
    /**
      * 实体类路径
      */
    private static String PPMDACENTER = "com.work.ppm.application.model.systemmanager.PpmDaCenter";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public List<PpmDaCenterDto> getList() {
        List<PpmDaCenter> list = myBatisDao.selectListT(PPMDACENTER, null,
                "getList");
        List<PpmDaCenterDto> dtos = JsonUtil.jsonToList(list,PpmDaCenterDto.class);
        return dtos;
    }

    @Override
    public List<PpmDaCenterDto> queryDeptList(String centerId) {
        PpmDaCenter center = new PpmDaCenter();
        if(StringUtil.isNotBlank(centerId)){
            center.setId(Integer.parseInt(centerId));
        }
        List<PpmDaCenter> list = myBatisDao.selectListT(PPMDACENTER, center, "queryDeptList");
        List<PpmDaCenterDto> dtos = JsonUtil.jsonToList(list,PpmDaCenterDto.class);
        return dtos;
    }

    @Override
    public PpmDaCenterDto queryCenter(String depId) {
        Map<String,Object> map = new HashMap<>();
        map.put("depId", depId);
        PpmDaCenter item = myBatisDao.selectClass(PPMDACENTER, map, "queryCenter");
        PpmDaCenterDto dto = JsonUtil.jsonToClass(item, PpmDaCenterDto.class);
        return dto;
    }
    @Override
    public List<PpmDaCenterDto> queryLists(PpmDaCenterDto ppmDaCenterDto) {
        PpmDaCenter ppmDaCenter = JsonUtil.jsonToClass(ppmDaCenterDto, PpmDaCenter.class);
        List<PpmDaCenter> list= myBatisDao.selectListT(PPMDACENTER, ppmDaCenter,"getList");
        List<PpmDaCenterDto> dtos = JsonUtil.jsonToList(list,PpmDaCenterDto.class);
        return dtos;
    }


    @Override
    public List<PpmDaCenterDto> queryDeptListBycenterIds(String centerIds) {
        Map param=new HashMap();
        if(StringUtil.isNotBlank(centerIds)){
            String sentersStr="";
            String[] centers=centerIds.split(",");
            for(int i=0;i<centers.length;i++){
                if(i==0){
                    sentersStr="'"+centers[i]+"'";
                }else{
                    sentersStr=sentersStr+","+"'"+centers[i]+"'";
                }
            }
            param.put("centers",sentersStr);
        }
        List<PpmDaCenter> list = myBatisDao.selectListT(PPMDACENTER, param, "queryDeptListByCenters");
        List<PpmDaCenterDto> dtos = JsonUtil.jsonToList(list,PpmDaCenterDto.class);
        return dtos;
    }
}
