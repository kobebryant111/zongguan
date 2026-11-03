package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiItemSwitchItem;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchItemDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiItemSwitchItemService")
public class PpmKpiItemSwitchItemService extends BaseDaoSupper implements IPpmKpiItemSwitchItemService {
    /**
      * 实体类路径
      */
    private static String PPMKPIITEMSWITCHITEM = "com.work.ppm.application.model.kpi.PpmKpiItemSwitchItem";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemSwitchItemDto,PpmKpiItemSwitchItemDto.class);
        myBatisDao.selectPager(PPMKPIITEMSWITCHITEM, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        PpmKpiItemSwitchItem ppmKpiItemSwitchItem = JsonUtil.jsonToClass(
                ppmKpiItemSwitchItemDto, PpmKpiItemSwitchItem.class);
        Long id = getNextSequence(PpmKpiItemSwitchItem.class);

        // 如果需要请set 主键id
        ppmKpiItemSwitchItem.setId(id);
        myBatisDao.executionPojo(PPMKPIITEMSWITCHITEM, ppmKpiItemSwitchItem, IMyBatisDao.SQL.INSTER,
                "addPojo");
        return 1;
    }
    @Override
    public PpmKpiItemSwitchItemDto queryDetail(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        PpmKpiItemSwitchItem ppmKpiItemSwitchItem = JsonUtil.jsonToClass(
                ppmKpiItemSwitchItemDto, PpmKpiItemSwitchItem.class);
        PpmKpiItemSwitchItem data = myBatisDao.selectClass(PPMKPIITEMSWITCHITEM, ppmKpiItemSwitchItem,
        "queryDetail");
        PpmKpiItemSwitchItemDto item = JsonUtil.jsonToClass(data, PpmKpiItemSwitchItemDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        PpmKpiItemSwitchItem ppmKpiItemSwitchItem = JsonUtil.jsonToClass(
                ppmKpiItemSwitchItemDto, PpmKpiItemSwitchItem.class);
        return myBatisDao.executionPojo(PPMKPIITEMSWITCHITEM, ppmKpiItemSwitchItem, IMyBatisDao.SQL.UPDATE,
                "updatePojo");
    }

    @Override
    public int deletePpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        PpmKpiItemSwitchItem ppmKpiItemSwitchItem = JsonUtil.jsonToClass(
                ppmKpiItemSwitchItemDto, PpmKpiItemSwitchItem.class);
        return myBatisDao.executionPojo(PPMKPIITEMSWITCHITEM, ppmKpiItemSwitchItem, IMyBatisDao.SQL.DELETE,
                "deleteById");
    }

    @Override
    public List<PpmKpiItemSwitchItemDto> getPpmKpiItemSwitchItemList(
            PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiItemSwitchItemDto,PpmKpiItemSwitchItemDto.class);
        List<PpmKpiItemSwitchItem> datas = myBatisDao.selectListT(PPMKPIITEMSWITCHITEM, map,
                "queryPagerList");
        List<PpmKpiItemSwitchItemDto> item = JsonUtil.jsonToList(datas, PpmKpiItemSwitchItemDto.class);
        return item;
    }
}
