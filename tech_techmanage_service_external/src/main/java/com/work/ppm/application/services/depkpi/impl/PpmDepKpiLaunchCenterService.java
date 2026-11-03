package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepKpiConfigurationItemDto;
import com.work.ppm.application.dto.depkpi.PpmDepKpiLaunchCenterDto;
import com.work.ppm.application.model.depkpi.PpmDepKpiLaunchCenter;
import com.work.ppm.application.services.depkpi.IPpmDepKpiLaunchCenterService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 */
@Service("ppmDepKpiLaunchCenterService")
public class PpmDepKpiLaunchCenterService extends BaseDaoSupper implements IPpmDepKpiLaunchCenterService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPILAUNCHCENTER = "" +
            "com.work.ppm.application.model.depkpi.PpmDepKpiLaunchCenter";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 部门考核配置项接口
     */
    @Autowired
    private PpmDepKpiConfigurationItemService ppmDepKpiConfigurationItemService;

    @Override
    public void queryPager(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(
                ppmDepKpiLaunchCenterDto, PpmDepKpiLaunchCenterDto.class);
        myBatisDao.selectPager(PPMDEPKPILAUNCHCENTER, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) {
        PpmDepKpiLaunchCenter ppmDepKpiLaunchCenter = JsonUtil.jsonToClass(
                ppmDepKpiLaunchCenterDto, PpmDepKpiLaunchCenter.class);
        Long id = getNextSequence(PpmDepKpiLaunchCenter.class);

        // 如果需要请set 主键id
        ppmDepKpiLaunchCenter.setId(id);
        myBatisDao.executionPojo(PPMDEPKPILAUNCHCENTER, ppmDepKpiLaunchCenter,
                IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmDepKpiLaunchCenterDto queryDetail(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) {
        PpmDepKpiLaunchCenter ppmDepKpiLaunchCenter = JsonUtil.jsonToClass(
                ppmDepKpiLaunchCenterDto, PpmDepKpiLaunchCenter.class);
        PpmDepKpiLaunchCenter data = myBatisDao.selectClass(PPMDEPKPILAUNCHCENTER,
                ppmDepKpiLaunchCenter, "queryDetail");
        return JsonUtil.jsonToClass(data, PpmDepKpiLaunchCenterDto.class);
    }

    @Override
    public int updatePpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) {
        PpmDepKpiLaunchCenter ppmDepKpiLaunchCenter = JsonUtil.jsonToClass(
                ppmDepKpiLaunchCenterDto, PpmDepKpiLaunchCenter.class);
        return myBatisDao.executionPojo(PPMDEPKPILAUNCHCENTER, ppmDepKpiLaunchCenter,
                IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional("defaultTx")
    public int createPpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto, String userid) {
        PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto = new PpmDepKpiConfigurationItemDto();
        List<PpmDepKpiConfigurationItemDto> lists = ppmDepKpiConfigurationItemService.
                getPpmDepKpiConfigurationItemList(ppmDepKpiConfigurationItemDto);
        for (int i = 0; i < lists.size(); i++) {
            PpmDepKpiLaunchCenterDto item = JsonUtil.jsonToClass(lists.get(i), PpmDepKpiLaunchCenterDto.class);
            item.setTyear(ppmDepKpiLaunchCenterDto.getTyear());
            item.setCreatetime(new Date());
            addPpmDepKpiLaunchCenter(item);
        }
        return 1;
    }

    @Override
    public List<PpmDepKpiLaunchCenterDto> getPpmDepKpiLaunchCenterList(
            PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) {
        PpmDepKpiLaunchCenter ppmDepKpiLaunchCenter = JsonUtil.jsonToClass(
                ppmDepKpiLaunchCenterDto, PpmDepKpiLaunchCenter.class);
        List<PpmDepKpiLaunchCenter> datas = myBatisDao.selectListT(
                PPMDEPKPILAUNCHCENTER, ppmDepKpiLaunchCenter, "getList");
        return JsonUtil.jsonToList(datas, PpmDepKpiLaunchCenterDto.class);
    }

}
