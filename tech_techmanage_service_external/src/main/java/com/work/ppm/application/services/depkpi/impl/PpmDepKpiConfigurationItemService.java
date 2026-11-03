package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.depkpi.PpmDepKpiConfigurationItem;
import com.work.ppm.application.dto.depkpi.PpmDepKpiConfigurationItemDto;
import com.work.ppm.application.services.depkpi.IPpmDepKpiConfigurationItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmDepKpiConfigurationItemService")
public class PpmDepKpiConfigurationItemService extends BaseDaoSupper implements IPpmDepKpiConfigurationItemService {
    /**
      * 实体类路径
      */
    private static String PPMDEPKPICONFIGURATIONITEM =
            "com.work.ppm.application.model.depkpi.PpmDepKpiConfigurationItem";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDepKpiConfigurationItemDto,
                PpmDepKpiConfigurationItemDto.class);
        myBatisDao.selectPager(PPMDEPKPICONFIGURATIONITEM, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        PpmDepKpiConfigurationItem ppmDepKpiConfigurationItem = JsonUtil.jsonToClass(
                ppmDepKpiConfigurationItemDto,
                PpmDepKpiConfigurationItem.class);
        myBatisDao.executionPojo(PPMDEPKPICONFIGURATIONITEM, ppmDepKpiConfigurationItem,
                IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDepKpiConfigurationItemDto queryDetail(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        PpmDepKpiConfigurationItem ppmDepKpiConfigurationItem = JsonUtil.jsonToClass(
                ppmDepKpiConfigurationItemDto, PpmDepKpiConfigurationItem.class);
        PpmDepKpiConfigurationItem data = myBatisDao.selectClass(PPMDEPKPICONFIGURATIONITEM,
                ppmDepKpiConfigurationItem,
        "queryDetail");
        return JsonUtil.jsonToClass(data, PpmDepKpiConfigurationItemDto
        .class);
    }

    @Override
    public int updatePpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        PpmDepKpiConfigurationItem ppmDepKpiConfigurationItem = JsonUtil.jsonToClass(
                ppmDepKpiConfigurationItemDto, PpmDepKpiConfigurationItem.class);
        return myBatisDao.executionPojo(PPMDEPKPICONFIGURATIONITEM,
                ppmDepKpiConfigurationItem, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        PpmDepKpiConfigurationItem ppmDepKpiConfigurationItem = JsonUtil.jsonToClass(
                ppmDepKpiConfigurationItemDto, PpmDepKpiConfigurationItem.class);
        return myBatisDao.executionPojo(PPMDEPKPICONFIGURATIONITEM, ppmDepKpiConfigurationItem,
                IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmDepKpiConfigurationItemDto> getPpmDepKpiConfigurationItemList(
            PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        PpmDepKpiConfigurationItem ppmDepKpiConfigurationItem = JsonUtil.jsonToClass(
                ppmDepKpiConfigurationItemDto, PpmDepKpiConfigurationItem.class);
        List<PpmDepKpiConfigurationItem> datas = myBatisDao.selectListT(PPMDEPKPICONFIGURATIONITEM,
                ppmDepKpiConfigurationItem, "getList");
        return  JsonUtil.jsonToList(datas, PpmDepKpiConfigurationItemDto.class);

    }
}
