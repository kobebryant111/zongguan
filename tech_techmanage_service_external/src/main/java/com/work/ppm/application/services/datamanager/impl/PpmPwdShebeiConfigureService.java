package com.work.ppm.application.services.datamanager.impl;

import com.work.ppm.application.dto.datamanager.PpmPwdShebeiConfigureDto;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.application.model.datamanager.PpmPwdShebeiConfigure;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiConfigureService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
  * 密管设备配置
  */
@Service("ppmPwdShebeiConfigureService")
public class PpmPwdShebeiConfigureService extends BaseDaoSupper implements IPpmPwdShebeiConfigureService {
    /**
      * 实体类路径
      */
    private static String PPMPWDSHEBEICONFIGURE = "com.work.ppm.application.model.datamanager.PpmPwdShebeiConfigure";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmPwdShebeiConfigureDto ppmPwdShebeiConfigureDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmPwdShebeiConfigureDto,PpmPwdShebeiConfigureDto.class);
        myBatisDao.selectPager(PPMPWDSHEBEICONFIGURE, map,pager,"queryPagerList");
    }

    @Override
    public List<PpmPwdShebeiConfigureDto> queryList(PpmPwdShebeiConfigureDto ppmPwdShebeiConfigureDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmPwdShebeiConfigureDto,PpmPwdShebeiConfigureDto.class);
        List<PpmPwdShebeiConfigure> list = myBatisDao.selectListT(PPMPWDSHEBEICONFIGURE, map, "getList");
        List<PpmPwdShebeiConfigureDto> dtos = JsonUtil.jsonToList(list, PpmPwdShebeiConfigureDto.class);
        return dtos;
    }

    @Override
    public PpmPwdShebeiConfigureDto getSheBei(PpmPwdShebeiDto ii) {
        if("个人账号".equals(ii.getSystemid())) {
            ii.setIp("");
        }
        ii.setStatus("0");
        PpmPwdShebeiConfigure sb = JsonUtil.jsonToClass(ii, PpmPwdShebeiConfigure.class);
        PpmPwdShebeiConfigure coinf = myBatisDao.selectClass(PPMPWDSHEBEICONFIGURE, sb, "getSheBei");
        PpmPwdShebeiConfigureDto dto = JsonUtil.jsonToClass(coinf, PpmPwdShebeiConfigureDto.class);
        return dto;
    }

    @Override
    public int updateSheBei(PpmPwdShebeiConfigureDto dto) {
        PpmPwdShebeiConfigure configure = JsonUtil.jsonToClass(dto,PpmPwdShebeiConfigure.class);
        int row = myBatisDao.executionPojo(PPMPWDSHEBEICONFIGURE, configure, IMyBatisDao.SQL.UPDATE, "updatePojo");
        return row;
    }
}
