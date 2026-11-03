package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程对象信息
 */
@Service("ppmProjectFlowService")
public class PpmProjectFlowService extends BaseDaoSupper implements IPpmProjectFlowService {
    /**
     * 实体类路径
     */
    private static final String PPMPROJECTFLOW = "com.work.ppm.application.model.flowmanager.PpmProjectFlow";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmProjectFlowDto ppmProjectFlow, Pager pager) {
        if(StringUtil.isNotBlank(ppmProjectFlow.getFlowid())) {
            String[] ids = ppmProjectFlow.getFlowid().split(",");
            StringBuilder sb = new StringBuilder(ids.length);
            for (String item: ids) {
                sb.append("'").append(item).append("',");
            }
            ppmProjectFlow.setFlowid(sb.toString().substring(0,sb.toString().length()-1));
        }
        Map<String,Object> map = StringUtil.objectTOMap(ppmProjectFlow,PpmProjectFlowDto.class);
        myBatisDao.selectPager(PPMPROJECTFLOW, map,pager,"queryPagerList");
    }

    @Override
    public void queryPagerDateBetween(PpmProjectFlowDto ppmProjectFlow, Pager pager, String dateBegin, String dateEnd) {
        if(StringUtil.isNotBlank(ppmProjectFlow.getFlowid())) {
            String[] ids = ppmProjectFlow.getFlowid().split(",");
            StringBuilder sb = new StringBuilder(ids.length);
            for (String item: ids) {
                sb.append("'").append(item).append("',");
            }
            ppmProjectFlow.setFlowid(sb.toString().substring(0,sb.toString().length()-1));
        }
        Map<String,Object> map = StringUtil.objectTOMap(ppmProjectFlow,PpmProjectFlowDto.class);
        if(StringUtil.isNotBlank(dateBegin) && StringUtil.isNotBlank(dateEnd)){
            map.put("dateBegin",dateBegin);
            map.put("dateEnd",dateEnd);
        }
        myBatisDao.selectPager(PPMPROJECTFLOW, map,pager,"queryPagerList");
    }

    @Override
    public List<PpmProjectFlowDto> queryList(PpmProjectFlowDto ppmProjectFlow) {
        if(StringUtil.isNotBlank(ppmProjectFlow.getFlowid())) {
            String[] ids = ppmProjectFlow.getFlowid().split(",");
            StringBuilder sb = new StringBuilder(ids.length);
            for (String item: ids) {
                sb.append("'").append(item).append("',");
            }
            ppmProjectFlow.setFlowid(sb.toString().substring(0,sb.toString().length()-1));
        }
        Map<String,Object> map = StringUtil.objectTOMap(ppmProjectFlow,PpmProjectFlowDto.class);
        List<PpmProjectFlow> list = myBatisDao.selectListT(PPMPROJECTFLOW, map,"queryPagerList");
        List<PpmProjectFlowDto> dtoList = JsonUtil.jsonToList(list,PpmProjectFlowDto.class);
        return dtoList;
    }

    @Override
    public int addPpmProjectFlow(PpmProjectFlow ppmProjectFlow) {
        Long id = getNextSequence(PpmProjectFlow.class);
        // 如果需要请set 主键id
        ppmProjectFlow.setId(id);
        myBatisDao.executionPojo(PPMPROJECTFLOW, ppmProjectFlow, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmProjectFlow queryDetail(PpmProjectFlow ppmProjectFlow) {
        return myBatisDao.selectClass(PPMPROJECTFLOW, ppmProjectFlow,"queryDetail");
    }

    @Override
    public Integer updatePpmProjectFlow(PpmProjectFlow ppmProjectFlow) {
        return myBatisDao.executionPojo(PPMPROJECTFLOW, ppmProjectFlow, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmProjectFlow(PpmProjectFlow ppmProjectFlow) {
        return myBatisDao.executionPojo(PPMPROJECTFLOW, ppmProjectFlow, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmProjectFlow> queryList(PpmProjectFlow entity) {
        return myBatisDao.selectListT(PPMPROJECTFLOW, entity, "getList");
    }

    @Override
    public PpmProjectFlow queryDetail(String instanceid) {
        PpmProjectFlow entity = new PpmProjectFlow();
        entity.setInstanceid(instanceid);
        return this.queryDetail(entity);
    }

    @Override
    public int updateProjectFlowByInstanceId(PpmProjectFlowDto ppmProjectFlowDto) {
        PpmProjectFlow ppmProjectFlow = JsonUtil.jsonToClass(ppmProjectFlowDto, PpmProjectFlow.class);
        return myBatisDao.executionPojo(PPMPROJECTFLOW,
                ppmProjectFlow, IMyBatisDao.SQL.UPDATE,"updatePojoByInstanceid");
    }

    @Override
    public PpmProjectFlowDto queryDetailForProjectno(String projectno) {
        PpmProjectFlow flow = new PpmProjectFlow();
        flow.setProjectno(projectno);
        PpmProjectFlow item = myBatisDao.selectClass(PPMPROJECTFLOW, flow, "queryDetail");
        PpmProjectFlowDto dto = JsonUtil.jsonToClass(item, PpmProjectFlowDto.class);
        return dto;
    }
}
