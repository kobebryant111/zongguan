package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmSystemUserInfoDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmSystemInfo;
import com.work.ppm.application.services.systemmanager.IPpmSystemInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 系统详情表 接口实现类
 *
 */
@Service("ppmSystemInfoService")
public class PpmSystemInfoService extends BaseDaoSupper implements IPpmSystemInfoService {
    /**
     * 实体类路径
     */
    private static String PPMSYSTEMINFO = "com.work.ppm.application.model.systemmanager.PpmSystemInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    @Autowired
    private PpmSystemUserInfoService ppmSystemUserInfoService;

    @Override
    public void queryPager(PpmSystemInfoDto ppmSystemInfoDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmSystemInfoDto, PpmSystemInfoDto.class);
        myBatisDao.selectPager(PPMSYSTEMINFO, map, pager, "queryPagerList");
        if (!CollectionUtils.isEmpty(pager.getList())) {
            pager.getList().forEach(item -> {
                PpmSystemInfo entry = (PpmSystemInfo) item;
                entry.setUserInfoList(myBatisDao.selectListT(PPMSYSTEMINFO, entry, "selectPpmSystemUserInfo"));
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addPpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) {
        PpmSystemInfo ppmSystemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        ppmSystemInfo.setId(getNextSequence(PpmSystemInfo.class));
        myBatisDao.executionPojo(PPMSYSTEMINFO, ppmSystemInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        // 判断系统负责人是否指定
        if (StringUtils.isEmpty(ppmSystemInfoDto.getBadges())) {
            throw new BizException(ApiCode.FAIL, "系统负责人必须指定");
        }
        // 保存系统负责人
        for (String badge : ppmSystemInfoDto.getBadges()) {
            PpmSystemUserInfoDto ppmSystemUserInfo = new PpmSystemUserInfoDto();
            ppmSystemUserInfo.setSysId(ppmSystemInfo.getId());
            ppmSystemUserInfo.setBadge(badge);
            ppmSystemUserInfoService.addPpmSystemUserInfo(ppmSystemUserInfo);
        }
        return 1;
    }

    @Override
    public List<PpmSystemInfo> querySystem(PpmSystemInfoDto ppmSystemInfoDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmSystemInfoDto, PpmSystemInfoDto.class);
        return myBatisDao.selectListT(PPMSYSTEMINFO, map, "getList");
    }

    @Override
    public List<CommEmployee> findByPpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) {
        PpmSystemInfo ppmSystemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        List<CommEmployee> result = myBatisDao.selectListT(PPMSYSTEMINFO, ppmSystemInfo, "findByPpmSystemInfo");
        return result;
    }

    @Override
    public PpmSystemInfoDto queryDetail(PpmSystemInfoDto ppmSystemInfoDto) {
        PpmSystemInfo ppmSystemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        PpmSystemInfo data = myBatisDao.selectClass(PPMSYSTEMINFO, ppmSystemInfo, "queryDetail");
        PpmSystemInfoDto item = JsonUtil.jsonToClass(data, PpmSystemInfoDto.class);
        return item;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) {
        PpmSystemInfo ppmSystemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        // 判断系统负责人是否指定
        if (!StringUtils.isEmpty(ppmSystemInfoDto.getBadges())) {
            // 先删除历史数据
            ppmSystemUserInfoService.deletePpmSystemUserInfo(new PpmSystemUserInfoDto(ppmSystemInfo.getId(), null));
            // 保存系统负责人
            for (String badge : ppmSystemInfoDto.getBadges()) {
                PpmSystemUserInfoDto ppmSystemUserInfo = new PpmSystemUserInfoDto();
                ppmSystemUserInfo.setSysId(ppmSystemInfo.getId());
                ppmSystemUserInfo.setBadge(badge);
                ppmSystemUserInfoService.addPpmSystemUserInfo(ppmSystemUserInfo);
            }
        }
        return myBatisDao.executionPojo(PPMSYSTEMINFO, ppmSystemInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) {
        PpmSystemInfo ppmSystemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        ppmSystemUserInfoService.deletePpmSystemUserInfo(new PpmSystemUserInfoDto(ppmSystemInfo.getId(), null));
        return myBatisDao.executionPojo(PPMSYSTEMINFO, ppmSystemInfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public List<PpmSystemInfo> queryTransferList(PpmSystemInfoDto ppmSystemInfoDto) {
        PpmSystemInfo systemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        List<PpmSystemInfo> systemInfos = myBatisDao.selectListT(PPMSYSTEMINFO, systemInfo, "queryPagerList");
        if (!CollectionUtils.isEmpty(systemInfos)) {
            systemInfos.forEach(item -> {
                item.setUserInfoList(myBatisDao.selectListT(PPMSYSTEMINFO, item, "selectPpmSystemUserInfo"));
                item.setDepName(BusinessUtil.getDeptNameValue(item.getDepName(),1));
            });
        }
        return systemInfos;
    }

    @Override
    public PpmSystemInfoDto querySystemDetail(String name,Long deptId) {
        PpmSystemInfoDto ppmSystemInfoDto = new PpmSystemInfoDto();
        ppmSystemInfoDto.setName(name);
        ppmSystemInfoDto.setDepId(deptId);
        PpmSystemInfo ppmSystemInfo = JsonUtil.jsonToClass(ppmSystemInfoDto, PpmSystemInfo.class);
        PpmSystemInfo data = myBatisDao.selectClass(PPMSYSTEMINFO, ppmSystemInfo, "querySystemDetail");
        return JsonUtil.jsonToClass(data, PpmSystemInfoDto.class);
    }
}
