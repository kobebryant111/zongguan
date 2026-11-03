package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailTmpDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiModelDetailTmp;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiModelDetailTmpInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelDetailTmpService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模板详情临时
 *
 */
@Service("ppmDepkpiModelDetailTmpService")
public class PpmDepkpiModelDetailTmpService extends BaseDaoSupper implements IPpmDepkpiModelDetailTmpService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIMODELDETAILTMP = "com.work.ppm.application.model.depkpi.PpmDepkpiModelDetailTmp";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 模板服务
     */
    @Autowired
    private IPpmDepkpiModelService ppmDepkpiModelService;

    @Override
    public void queryPager(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiModelDetailTmpDto, PpmDepkpiModelDetailTmpDto.class);
        myBatisDao.selectPager(PPMDEPKPIMODELDETAILTMP, map, pager, "queryPagerList");
    }

    @Override
    public PpmDepkpiModelDetailTmpDto queryDetail(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto) {
        PpmDepkpiModelDetailTmp ppmDepkpiModelDetailTmp = JsonUtil.
                jsonToClass(ppmDepkpiModelDetailTmpDto, PpmDepkpiModelDetailTmp.class);
        PpmDepkpiModelDetailTmp pp = myBatisDao.selectClass(PPMDEPKPIMODELDETAILTMP, ppmDepkpiModelDetailTmp,
                "queryDetail");
        return JsonUtil.jsonToClass(pp, PpmDepkpiModelDetailTmpDto.class);

    }

    @Override
    @Transactional("defaultTx")
    public int updateModelTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto, String userid) {
        PpmDepkpiModelDetailTmp ppmDepkpiModelDetailTmp = JsonUtil.jsonToClass(ppmDepkpiModelDetailTmpDto,
                PpmDepkpiModelDetailTmp.class);
        //查询该模板是否有子级
        PpmDepkpiModelDetailTmp pp=new PpmDepkpiModelDetailTmp();
        pp.setParentid(ppmDepkpiModelDetailTmp.getId());
        List<PpmDepkpiModelDetailTmp>list=myBatisDao.selectListT(PPMDEPKPIMODELDETAILTMP,
                pp, "queryByParentId");
        if(!Collections.isEmpty(list)){
            return -1;
        }
        //修改模板表状态
        upModelStatus(ppmDepkpiModelDetailTmpDto.getModelid(), userid);
        return myBatisDao.executionPojo(PPMDEPKPIMODELDETAILTMP, ppmDepkpiModelDetailTmp,
                IMyBatisDao.SQL.UPDATE, "updatePojoSpecial");
    }

    @Override
    public List<PpmDepkpiModelDetailTmpInfo> queryDepModelTargetList(PpmDepkpiModelDetailTmpDto ppm) {
        PpmDepkpiModelDetailTmp ppd = JsonUtil.jsonToClass(ppm, PpmDepkpiModelDetailTmp.class);
        return myBatisDao.selectListT(PPMDEPKPIMODELDETAILTMP, ppd, "queryDepModelTargetList");
    }

    @Override
    @Transactional("defaultTx")
    public int modelAssignTarget(PpmDepkpiModelDetailTmpDto pp, String userid) {
        PpmDepkpiModelDetailTmp ppm = JsonUtil.jsonToClass(pp, PpmDepkpiModelDetailTmp.class);
        Long id = getNextSequence(PpmDepkpiModelDetailTmp.class);
        ppm.setId(id);
        if (ppm.getParentid() != null) {
            ppm.setHasson(1L);
            myBatisDao.executionPojo(PPMDEPKPIMODELDETAILTMP, ppm, IMyBatisDao.SQL.UPDATE,
                    "upPojoById");
        }
        ppm.setHasson(0L);
        upModelStatus(pp.getModelid(), userid);
        return myBatisDao.executionPojo(PPMDEPKPIMODELDETAILTMP, ppm, IMyBatisDao.SQL.INSTER,
                "addPojo");
    }


    @Override
    @Transactional("defaultTx")
    public int deleteModelTarget(PpmDepkpiModelDetailTmpDto pp, String userid) {
        PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto = this.queryDetail(pp);
        if (ppmDepkpiModelDetailTmpDto == null) {
            return 0;
        }
        //修改模板表状态
        upModelStatus(ppmDepkpiModelDetailTmpDto.getModelid(), userid);
        PpmDepkpiModelDetailTmp parpp = JsonUtil.jsonToClass(ppmDepkpiModelDetailTmpDto,
                PpmDepkpiModelDetailTmp.class);
        //删除
        myBatisDao.executionPojo(PPMDEPKPIMODELDETAILTMP, parpp,
                IMyBatisDao.SQL.DELETE, "deleteModelTarget");
        //判断此模块是否有父节点 且 是否有且仅有此一个子节点
        if (ppmDepkpiModelDetailTmpDto.getParentid() != null) {
            List<PpmDepkpiModelDetailTmp> pars = myBatisDao.selectListT(PPMDEPKPIMODELDETAILTMP,
                    parpp, "queryByParentId");
            if (Collections.isEmpty(pars)) {
                //修改父节点为无子节点
                parpp.setHasson(0L);
                myBatisDao.executionPojo(PPMDEPKPIMODELDETAILTMP, parpp, IMyBatisDao.SQL.UPDATE,
                        "upPojoById");
            }
        }
        return 1;
    }


    @Override
    public List<PpmDepkpiModelDetailTmpDto> queryByModelId(PpmDepkpiModelDetailTmpDto pp) {
        PpmDepkpiModelDetailTmp ppmDepkpiModelDetailTmp = JsonUtil.jsonToClass(pp, PpmDepkpiModelDetailTmp.class);
        List<PpmDepkpiModelDetailTmp> ppm = myBatisDao.selectListT(PPMDEPKPIMODELDETAILTMP, ppmDepkpiModelDetailTmp,
                "queryByModelId");
        List<PpmDepkpiModelDetailTmpDto> list = new ArrayList<>();
        ppm.forEach(e -> list.add(JsonUtil.jsonToClass(e, PpmDepkpiModelDetailTmpDto.class)));
        return list;
    }

    /**
     * 修改部门模板状态
     *
     * @param modelId 模板id
     * @param userid  userid
     */
    private void upModelStatus(long modelId, String userid) {
        //修改模板分配表表提交状态
        PpmDepkpiModelDto ppmDepkpiModelDto = new PpmDepkpiModelDto();
        ppmDepkpiModelDto.setId(modelId);
        ppmDepkpiModelDto.setCommitflag(0L);
        this.ppmDepkpiModelService.updateModel(ppmDepkpiModelDto, userid);
    }

    @Override
    public Long queryCount(PpmDepkpiModelDetailTmpDto pp) {
        Map<String, Object> map = JsonUtil.objectTOMap(pp, PpmDepkpiModelDetailTmpDto.class);
        return myBatisDao.selectCount(PPMDEPKPIMODELDETAILTMP, map,"queryCountByHassonAndTargettype");
    }

}
