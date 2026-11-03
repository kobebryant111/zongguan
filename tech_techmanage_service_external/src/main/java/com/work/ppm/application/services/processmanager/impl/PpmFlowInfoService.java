package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.application.dto.processmanager.PpmFlowInfoDto;
import com.work.ppm.application.model.processmanager.PpmFlowInfo;
import com.work.ppm.application.services.processmanager.IPpmFlowInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程信息
 */
@Service("ppmFlowInfoService")
public class PpmFlowInfoService extends BaseDaoSupper implements IPpmFlowInfoService {
    /**
     * 实体类路径
     */
    private static String PPMFLOWINFO = "com.work.ppm.application.model.processmanager.PpmFlowInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmFlowInfo(PpmFlowInfo ppmFlowInfo) {
        Long id = getNextSequence(PpmFlowInfo.class);
        // 如果需要请set 主键id
        ppmFlowInfo.setId(id);
        ppmFlowInfo.setIsdel(0);
        myBatisDao.executionPojo(PPMFLOWINFO, ppmFlowInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmFlowInfo queryDetail(PpmFlowInfo ppmFlowInfo) {
        return myBatisDao.selectClass(PPMFLOWINFO, ppmFlowInfo,"queryDetail");
    }

    @Override
    public Integer updatePpmFlowInfo(PpmFlowInfo ppmFlowInfo) {
        // 添加
        if(null == ppmFlowInfo.getId()) {

            this.addPpmFlowInfo(ppmFlowInfo);
            return 1;
        }
        // 更新
        myBatisDao.executionPojo(PPMFLOWINFO, ppmFlowInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
        return 2;
    }

    @Override
    public Integer deletePpmFlowInfo(PpmFlowInfo ppmFlowInfo) {
        ppmFlowInfo.setIsdel(1);
        this.updatePpmFlowInfo(ppmFlowInfo);
        return 1;
    }

    @Override
    public List<PpmFlowInfoDto> queryList(PpmFlowInfo ppmFlowInfo) {
        ppmFlowInfo.setIsdel(0);
        List<PpmFlowInfo> list = myBatisDao.selectListT(PPMFLOWINFO, ppmFlowInfo, "getList");
        List<PpmFlowInfoDto> dtos = JsonUtil.jsonToList(list, PpmFlowInfoDto.class);
        Map<String,List<PpmFlowInfoDto>> map = dtos.stream().collect(Collectors.groupingBy(PpmFlowInfoDto::getFlowname));
        List<PpmFlowInfoDto> newList = new ArrayList<>();
        map.forEach((v,k)->{
            PpmFlowInfoDto temp = new PpmFlowInfoDto();
            temp.setFlowname(v);
            temp.setChildren(k);
            newList.add(temp);
        });
        return newList;
    }

    @Override
    public PpmFlowInfo queryDetail(String flownodeCode, String flowdraftno) {
        PpmFlowInfo item = new PpmFlowInfo();
        item.setFlownodecode(flownodeCode);
        item.setFlowdraftno(flowdraftno);
        item.setIsdel(0);
        return this.queryDetail(item);
    }
}
