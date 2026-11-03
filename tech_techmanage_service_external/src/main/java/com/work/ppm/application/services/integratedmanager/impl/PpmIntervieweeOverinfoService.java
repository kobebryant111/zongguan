package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.integratedmanager.IntervieweeInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeOverinfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeOverinfo;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmIntervieweeOverinfoInfo;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeOverinfoService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeScoreinfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面试归档
 *
 */
@Service("ppmIntervieweeOverinfoService")
public class PpmIntervieweeOverinfoService extends BaseDaoSupper implements IPpmIntervieweeOverinfoService {
    /**
     * 实体类路径
     */
    private static String PPMINTERVIEWEEOVERINFO = "" +
            "com.work.ppm.application.model.integratedmanager.PpmIntervieweeOverinfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 面试分数表
     */
    @Autowired
    private IPpmIntervieweeScoreinfoService ppmIntervieweeScoreinfoService;

    @Override
    public void queryPager(PpmIntervieweeOverinfo ppmIntervieweeOverinfo, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmIntervieweeOverinfo, PpmIntervieweeOverinfo.class);
        myBatisDao.selectPager(PPMINTERVIEWEEOVERINFO, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmIntervieweeOverinfo(PpmIntervieweeOverinfo ppmIntervieweeOverinfo) {
        Long id = getNextSequence(PpmIntervieweeOverinfo.class);
        // 如果需要请set 主键id
        ppmIntervieweeOverinfo.setId(id);
        myBatisDao.executionPojo(PPMINTERVIEWEEOVERINFO, ppmIntervieweeOverinfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmIntervieweeOverinfo queryDetail(PpmIntervieweeOverinfo ppmIntervieweeOverinfo) {
        return myBatisDao.selectClass(PPMINTERVIEWEEOVERINFO, ppmIntervieweeOverinfo, "queryDetail");
    }

    @Override
    public Integer updatePpmIntervieweeOverinfo(PpmIntervieweeOverinfo ppmIntervieweeOverinfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWEEOVERINFO, ppmIntervieweeOverinfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteByInstanceId(String instanceId) {
        PpmIntervieweeOverinfo ppmIntervieweeOverinfo = new PpmIntervieweeOverinfo();
        ppmIntervieweeOverinfo.setInstanceid(instanceId);
        return myBatisDao.executionPojo(PPMINTERVIEWEEOVERINFO, ppmIntervieweeOverinfo, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }

    @Override
    public Integer deletePpmIntervieweeOverinfo(PpmIntervieweeOverinfo ppmIntervieweeOverinfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWEEOVERINFO, ppmIntervieweeOverinfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public int queryCarryOutNum(Long rlId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rlId", rlId);
        Long num = myBatisDao.selectCount(PPMINTERVIEWEEOVERINFO, map, "queryCarryOutNum");
        return num.intValue();
    }

    @Override
    public PpmIntervieweeOverinfoDto getCheckedExistEntry(String number) {
        PpmIntervieweeOverinfo info = new PpmIntervieweeOverinfo();
        info.setIdcard(number);
        PpmIntervieweeOverinfo item = myBatisDao.selectClass(PPMINTERVIEWEEOVERINFO, info, "getCheckedExistEntry");
        PpmIntervieweeOverinfoDto dto = JsonUtil.jsonToClass(item, PpmIntervieweeOverinfoDto.class);
        return dto;
    }

    @Override
    public PpmIntervieweeOverinfoDto getCheckedPassEntry(String number) {
        PpmIntervieweeOverinfo info = new PpmIntervieweeOverinfo();
        info.setIdcard(number);
        PpmIntervieweeOverinfo item = myBatisDao.selectClass(PPMINTERVIEWEEOVERINFO, info, "getCheckedPassEntry");
        PpmIntervieweeOverinfoDto dto = JsonUtil.jsonToClass(item, PpmIntervieweeOverinfoDto.class);
        return dto;
    }

    @Override
    public IntervieweeInfoDto getIntervieweeInfo(String idcard) {
        IntervieweeInfoDto dto = new IntervieweeInfoDto();
        Map param = new HashMap();
        param.put("idcard", idcard);
        List<PpmIntervieweeOverinfoInfo> items = myBatisDao.selectListT(PPMINTERVIEWEEOVERINFO, param, "queryList");
        if (items.size() > 0) {
            dto.setPpmIntervieweeOverinfoInfo(items.get(0));
            String instanceid = items.get(0).getInstanceid();
            List<PpmIntervieweeScoreinfo> list = ppmIntervieweeScoreinfoService.getScoreList(instanceid);
            if (list.size() > 0) {
                dto.setPpmIntervieweeScoreinfoList(list);
            }
        }
        return dto;
    }

    @Override
    public List<IntervieweeInfoDto> getIntervieweeInfos(String idcard) {
        Map param = new HashMap();
        param.put("idcard", idcard);
        param.put("interviewresult",1);
        List<PpmIntervieweeOverinfoInfo> items = myBatisDao.selectListT(PPMINTERVIEWEEOVERINFO, param, "queryList");
        if (!CollectionUtils.isEmpty(items)) {
            List<IntervieweeInfoDto> result = Lists.newArrayList();
            for (PpmIntervieweeOverinfoInfo item : items) {
                IntervieweeInfoDto infoDto = new IntervieweeInfoDto();
                infoDto.setPpmIntervieweeOverinfoInfo(item);
                infoDto.setPpmIntervieweeScoreinfoList(ppmIntervieweeScoreinfoService
                        .getScoreListByInstIdAndIdCard(item.getInstanceid(),String.valueOf(item.getIdcard())));
                result.add(infoDto);
            }
            return result;
        }
        return null;
    }
}
