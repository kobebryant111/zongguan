package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmFhspbmpzDto;
import com.work.ppm.application.model.projectmanager.PpmFhspbmpz;
import com.work.ppm.application.services.projectmanager.IPpmFhspbmpzService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
  * 分行审批部门配置
  */
@Service("ppmFhspbmpzService")
public class PpmFhspbmpzService extends BaseDaoSupper implements IPpmFhspbmpzService {
    /**
      * 实体类路径
      */
    private static String PPMFHSPBMPZ = "com.work.ppm.application.model.projectmanager.PpmFhspbmpz";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public List<PpmFhspbmpzDto> queryList(PpmFhspbmpzDto ppmFhspbmpzDto) {
        ppmFhspbmpzDto.setIsdel("0");
        List<PpmFhspbmpz> list= myBatisDao.selectListT(PPMFHSPBMPZ, ppmFhspbmpzDto, "getList");
        List<PpmFhspbmpzDto> dtos = JsonUtil.jsonToList(list, PpmFhspbmpzDto.class);
        return dtos;
    }

    @Override
    public int addPpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto) {
        PpmFhspbmpz ppmFhspbmpz = JsonUtil.jsonToClass(ppmFhspbmpzDto, PpmFhspbmpz.class);
        Long id = getNextSequence(PpmFhspbmpz.class);
        // 如果需要请set 主键id
        ppmFhspbmpz.setId(id + "");
        ppmFhspbmpz.setIsdel("0");
        ppmFhspbmpz.setCreatetime(new Date());
        myBatisDao.executionPojo(PPMFHSPBMPZ, ppmFhspbmpz, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmFhspbmpzDto queryDetail(String deptno) {
        PpmFhspbmpz entity = new PpmFhspbmpz();
        entity.setDeptno(deptno);
        entity.setIsdel("0");
        PpmFhspbmpz data = myBatisDao.selectClass(PPMFHSPBMPZ, entity,
        "queryDetail");
        PpmFhspbmpzDto item = JsonUtil.jsonToClass(data, PpmFhspbmpzDto
        .class);
        return item;
    }

    @Override
    public int updatePpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto) {
        // id 不为空 则添加，反之 更新
        if(!StringUtil.isNotBlank(ppmFhspbmpzDto.getId())) {
            PpmFhspbmpzDto item = this.queryDetail(ppmFhspbmpzDto.getDeptno());
            if(null != item) {
                return -1;
            }
            this.addPpmFhspbmpz(ppmFhspbmpzDto);
            return 1;
        }
        // 更新
        PpmFhspbmpz ppmFhspbmpz = JsonUtil.jsonToClass(ppmFhspbmpzDto, PpmFhspbmpz.class);
        myBatisDao.executionPojo(PPMFHSPBMPZ, ppmFhspbmpz, IMyBatisDao.SQL.UPDATE,"updatePojo");
        return 2;
    }

    @Override
    public int deletePpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto) {
        ppmFhspbmpzDto.setIsdel("1");
        return this.updatePpmFhspbmpz(ppmFhspbmpzDto);
    }
}
