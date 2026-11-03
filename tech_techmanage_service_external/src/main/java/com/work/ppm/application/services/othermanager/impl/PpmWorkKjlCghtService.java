package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.dto.othermanager.PpmWorkKjlCghtDto;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.application.model.othermanager.PpmWorkKjlCght;
import com.work.ppm.application.services.othermanager.IPpmWorkKjlCghtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 外包合同
 */
@Service("ppmWorkKjlCghtService")
public class PpmWorkKjlCghtService extends BaseDaoSupper implements IPpmWorkKjlCghtService {
    /**
     * 实体类路径
     */
    private static String PPMWORKKJLCGHT = "com.work.ppm.application.model.othermanager.PpmWorkKjlCght";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmWorkKjlCght ppmWorkKjlCght, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmWorkKjlCght,PpmWorkKjlCght.class);
        myBatisDao.selectPager(PPMWORKKJLCGHT, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) {
        Long key = this.getNextSequence(PpmWorkKjlCght.class);
        // 如果需要请set 主键id
        //ppmWorkKjlCght.setId(key);
        myBatisDao.executionPojo(PPMWORKKJLCGHT, ppmWorkKjlCght, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmWorkKjlCght queryDetail(PpmWorkKjlCght ppmWorkKjlCght) {
        return myBatisDao.selectClass(PPMWORKKJLCGHT, ppmWorkKjlCght,"queryDetail");
    }

    @Override
    public Integer updatePpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) {
        return myBatisDao.executionPojo(PPMWORKKJLCGHT, ppmWorkKjlCght, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) {
        return myBatisDao.executionPojo(PPMWORKKJLCGHT, ppmWorkKjlCght, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmWorkKjlCghtDto> queryContracts(PpmWorkKjlCghtDto info) {
        PpmWorkKjlCght ppmWorkKjlCght = JsonUtil.jsonToClass(info, PpmWorkKjlCght.class);
        List<PpmWorkKjlCght> dtocontracts = myBatisDao.selectListT(PPMWORKKJLCGHT, ppmWorkKjlCght,  "queryContracts");
        List<PpmWorkKjlCghtDto> dto = JsonUtil.jsonToList(dtocontracts,PpmWorkKjlCghtDto.class);
        return dto;
    }
}
