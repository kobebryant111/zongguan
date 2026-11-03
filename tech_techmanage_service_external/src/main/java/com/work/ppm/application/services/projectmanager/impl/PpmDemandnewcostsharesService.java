package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmDemandnewcostsharesDto;
import com.work.ppm.application.model.projectmanager.PpmDemandnewcostshares;
import com.work.ppm.application.model.projectmanager.ext.PpmDemandnewcostsharesInfo;
import com.work.ppm.application.services.projectmanager.IPpmDemandnewcostsharesService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * 分摊
  */
@Service("ppmDemandnewcostsharesService")
public class PpmDemandnewcostsharesService extends BaseDaoSupper implements IPpmDemandnewcostsharesService {
    /**
      * 实体类路径
      */
    private static String PPMDEMANDNEWCOSTSHARES = "" +
            "com.work.ppm.application.model.projectmanager.PpmDemandnewcostshares";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmDemandnewcostshares(List<PpmDemandnewcostsharesDto> ppmDemandnewcostsharesDto) {

        if(null == ppmDemandnewcostsharesDto || ppmDemandnewcostsharesDto.isEmpty()) {
            return -1;
        }

        ppmDemandnewcostsharesDto.stream().forEach(ii -> {
            Long id = getNextSequence(PpmDemandnewcostshares.class);
            ii.setId(id);
        });
        List<PpmDemandnewcostshares> list = JsonUtil.jsonToList(ppmDemandnewcostsharesDto,
                PpmDemandnewcostshares.class);
        myBatisDao.executionPojo(PPMDEMANDNEWCOSTSHARES, list, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public List<PpmDemandnewcostsharesDto> getList(String demandno) {
        PpmDemandnewcostshares shares = new PpmDemandnewcostshares();
        shares.setDemandno(demandno);
        List<PpmDemandnewcostsharesInfo> list = myBatisDao.selectListT(PPMDEMANDNEWCOSTSHARES, shares, "getList");
        if(null == list || list.isEmpty()) {
            return null;
        }
        list.stream().forEach(ii -> {
            ii.setManagerUserName(ii.getManagerUserName() + "(" + ii.getManageruser() + ")");
        });
        List<PpmDemandnewcostsharesDto> dto = JsonUtil.jsonToList(list, PpmDemandnewcostsharesDto.class);
        return dto;
    }
}
