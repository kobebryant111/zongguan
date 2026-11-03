package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailTmpDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepModel;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepModelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepRelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailTmpService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelDetailService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门模板分配
 *
 */
@Service("ppmDepkpiDepModelService")
public class PpmDepkpiDepModelService extends BaseDaoSupper implements IPpmDepkpiDepModelService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIDEPMODEL = "com.work.ppm.application.model.depkpi.PpmDepkpiDepModel";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 数据接口
     */
    @Autowired
    private IPpmDepkpiDepRelService ippmDepkpiDepRelService;
    /**
     * 部门模板详情接口
     */
    @Autowired
    private IPpmDepkpiDepmodelDetailTmpService iPpmDepkpiDepmodelDetailTmpService;

    /**
     * 部门模板详情接口
     */
    @Autowired
    private IPpmDepkpiDepmodelDetailService iPpmDepkpiDepmodelDetailService;
    /**
     * 模板service接口
     */
    @Autowired
    private IPpmDepkpiModelService ippmDepkpiModelService;

    /**
     * 模板service接口
     */
    @Autowired
    private IPpmDepkpiModelDetailService iPpmDepkpiModelDetailService;


    @Override
    public void queryPager(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepModelDto, PpmDepkpiDepModelDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPMODEL, map, pager, "queryPagerList");
    }

    @Override
    public void queryList(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, Pager pager) {
        //部门模板分配分页查询
        ippmDepkpiDepRelService.getPpmDepKpiDepRelPage(ppmDepkpiDepModelDto, pager);
    }

    @Override
    @Transactional("defaultTx")
    public int updateDepModel(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, String userid) {
        int i = 0;
        //部门更换模板
        PpmDepkpiDepModel ppmDepkpiDepModel = JsonUtil.jsonToClass(ppmDepkpiDepModelDto, PpmDepkpiDepModel.class);
        ppmDepkpiDepModel.setLastmodtime(new Date());
        ppmDepkpiDepModel.setLastmoduserid(userid);
        PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto = new PpmDepkpiDepmodelDetailTmpDto();
        ppmDepkpiDepmodelDetailTmpDto.setDepid(ppmDepkpiDepModelDto.getDepid());
        if (ppmDepkpiDepModel.getModelid() == null) {
            ppmDepkpiDepModel.setCommitflag(0L);
            i = myBatisDao.executionPojo(PPMDEPKPIDEPMODEL, ppmDepkpiDepModel, IMyBatisDao.SQL.UPDATE,
                    "changeDepModelByDepid");
            //没有则新增（没有更新到说明模板分配表没有这条模板）
            if (i == 0) {
                ppmDepkpiDepModel.setId(getNextSequence(PpmDepkpiDepModel.class));
                myBatisDao.executionPojo(PPMDEPKPIDEPMODEL, ppmDepkpiDepModel,
                        IMyBatisDao.SQL.INSTER, "addPojo");
            }
        }
        //塞详情
        if (ppmDepkpiDepModelDto.getModelid() != null) {
            ppmDepkpiDepModel.setCommitflag(1L);
            i = myBatisDao.executionPojo(PPMDEPKPIDEPMODEL, ppmDepkpiDepModel, IMyBatisDao.SQL.UPDATE,
                    "changeDepModelByDepid");
            //没有则新增（没有更新到说明模板分配表没有这条模板）
            if (i == 0) {
                ppmDepkpiDepModel.setId(getNextSequence(PpmDepkpiDepModel.class));
                myBatisDao.executionPojo(PPMDEPKPIDEPMODEL, ppmDepkpiDepModel,
                        IMyBatisDao.SQL.INSTER, "addPojo");
            }
            // 删除部门模板详情数据
            iPpmDepkpiDepmodelDetailTmpService.deleteDepTargetByDepid(ppmDepkpiDepmodelDetailTmpDto);
            PpmDepkpiModelDetailDto ppmDepkpiModelDto = new PpmDepkpiModelDetailDto();
            ppmDepkpiModelDto.setModelid(ppmDepkpiDepModelDto.getModelid());
            List<PpmDepkpiModelDetailDto> pp1 = iPpmDepkpiModelDetailService.queryByMid(ppmDepkpiModelDto);
            //父级id为null手动塞-1，方便流分组
            pp1.forEach(e -> {
                if (e.getParentid() == null) {
                    e.setParentid(-1L);
                }
            });
            if (!Collections.isEmpty(pp1)) {
                //根据父级id分组
                Map<Long, List<PpmDepkpiModelDetailDto>> collect = pp1.stream().
                        collect(Collectors.groupingBy(PpmDepkpiModelDetailDto::getParentid));
                //获取一级记录
                List<PpmDepkpiModelDetailDto> firsts = collect.get(-1L);
                //添加记录
                firsts.forEach(e -> addModelDeatilTmp(collect, e, ppmDepkpiDepModelDto.getDepid()));
            }
            PpmDepkpiDepmodelDetailDto ppD = new PpmDepkpiDepmodelDetailDto();
            ppD.setDepid(ppmDepkpiDepModelDto.getDepid());
            iPpmDepkpiDepmodelDetailService.commitDepModelTarget(ppD, userid);
        }
        return 1;
    }

    /**
     * 添加模板临时详情
     *
     * @param depId          部门id
     * @param collect        添加对象总分组集合
     * @param modelDetailDto 添加目标对象
     */
    private void addModelDeatilTmp(Map<Long, List<PpmDepkpiModelDetailDto>> collect,
                                   PpmDepkpiModelDetailDto modelDetailDto, Long depId) {
        long newId = getNextSequence(PpmDepkpiDepModel.class);
        PpmDepkpiDepmodelDetailTmpDto pdd = JsonUtil.jsonToClass(modelDetailDto, PpmDepkpiDepmodelDetailTmpDto.class);
        pdd.setId(newId);
        pdd.setParentid(modelDetailDto.getParentid() == -1 ? null : modelDetailDto.getParentid());
        pdd.setDepid(depId);
        iPpmDepkpiDepmodelDetailTmpService.addPpmDepkpiDepmodelDetailTmp(pdd);
        //塞子级
        List<PpmDepkpiModelDetailDto> sonLists = collect.get(modelDetailDto.getId());
        if (!Collections.isEmpty(sonLists)) {
            sonLists.forEach(e -> {
                e.setParentid(newId);
                addModelDeatilTmp(collect, e, depId);
            });
        }

    }

    @Override
    public int updateDepModelCommitFlag(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, String userid) {
        PpmDepkpiDepModel ppmDepkpiDepModel = JsonUtil.jsonToClass(ppmDepkpiDepModelDto, PpmDepkpiDepModel.class);
        ppmDepkpiDepModel.setLastmodtime(new Date());
        ppmDepkpiDepModel.setLastmoduserid(userid);
        return myBatisDao.executionPojo(PPMDEPKPIDEPMODEL, ppmDepkpiDepModel,
                IMyBatisDao.SQL.UPDATE, "updateDepModelByDepid");
    }
}
