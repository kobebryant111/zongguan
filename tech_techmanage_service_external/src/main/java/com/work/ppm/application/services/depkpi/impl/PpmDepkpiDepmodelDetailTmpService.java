package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailTmpDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetailTmp;
import com.work.ppm.application.model.depkpi.PpmDepkpiModelDetailTmp;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepModelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailTmpService;
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
 * 部门模板临时详情操作
 *
 */
@Service("ppmDepkpiDepmodelDetailTmpService")
public class PpmDepkpiDepmodelDetailTmpService extends BaseDaoSupper implements IPpmDepkpiDepmodelDetailTmpService {
    /**
     * 实体类路径
     */
    private static String
            PPMDEPKPIDEPMODELDETAILTMP ="com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetailTmp";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 数据接口
     */
    @Autowired
    private IPpmDepkpiDepModelService iPpmDepkpiDepModelService;

    @Override
    public void queryPager(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmpDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPMODELDETAILTMP, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmDepkpiDepmodelDetailTmp(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto) {
        PpmDepkpiDepmodelDetailTmp ppmDepkpiDepmodelDetailTmp = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmp.class);
        return myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, ppmDepkpiDepmodelDetailTmp, IMyBatisDao.SQL.INSTER,
                "addPojo");

    }

    @Override
    public PpmDepkpiDepmodelDetailTmpDto queryDetail(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto) {
        PpmDepkpiDepmodelDetailTmp ppmDepkpiDepmodelDetailTmp = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmp.class);
        PpmDepkpiDepmodelDetailTmp data = myBatisDao.selectClass(PPMDEPKPIDEPMODELDETAILTMP, ppmDepkpiDepmodelDetailTmp,
                "queryDetail");
        return JsonUtil.jsonToClass(data, PpmDepkpiDepmodelDetailTmpDto
                .class);

    }

    @Override
    public List<PpmDepkpiDepmodelDetailTmpDto> getDepkpiDepmodelDetailTmpList(PpmDepkpiDepmodelDetailTmpDto
                                                                                       ppmDepkpiDepmodelDetailTmpDto) {
        //查询部门模板详情数据（部门已分配指标查询）
        PpmDepkpiDepmodelDetailTmp ppmDepkpiDepmodelDetailTmp = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmp.class);
        List<PpmDepkpiDepmodelDetailTmp> datas = myBatisDao.selectListT(PPMDEPKPIDEPMODELDETAILTMP,
                ppmDepkpiDepmodelDetailTmp, "getTargetList");
        return JsonUtil.jsonToList(datas, PpmDepkpiDepmodelDetailTmpDto.class);
    }

    @Override
    @Transactional("defaultTx")
    public int addDepkpiDepmodelDetailTmpDto(PpmDepkpiDepmodelDetailTmpDto pp, String userid) {
        //部门分配指标
        PpmDepkpiDepmodelDetailTmp ppmDepkpiDepmodelDetailTmp = JsonUtil.jsonToClass(pp,
                PpmDepkpiDepmodelDetailTmp.class);
        ppmDepkpiDepmodelDetailTmp.setId(getNextSequence(PpmDepkpiDepmodelDetailTmp.class));
        //（第一次添加一级考核项存入数据库显示无子节点，添加二级通过判断进入修改，获取父级id当作主键id修改父级为有子节点）
        if (ppmDepkpiDepmodelDetailTmp.getParentid() != null) {
            ppmDepkpiDepmodelDetailTmp.setHasson(1L);
            myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, ppmDepkpiDepmodelDetailTmp,
                    IMyBatisDao.SQL.UPDATE, "upPojoById");
        }
        ppmDepkpiDepmodelDetailTmp.setHasson(0L);
        myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, ppmDepkpiDepmodelDetailTmp, IMyBatisDao.SQL.INSTER,
                "addPojo");
        //修改模分配表状态
        PpmDepkpiDepModelDto ppmDepkpiDepModelDto = new PpmDepkpiDepModelDto();
        ppmDepkpiDepModelDto.setDepid(pp.getDepid());
        ppmDepkpiDepModelDto.setCommitflag(0L);
        iPpmDepkpiDepModelService.updateDepModelCommitFlag(ppmDepkpiDepModelDto, userid);
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int deleteDepTarget(PpmDepkpiDepmodelDetailTmpDto pp, String userid) {
        //通过id查询出当前数据（留判断是否有父级 有父级修改无子节点）
        PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto = this.queryDetail(pp);
        if (ppmDepkpiDepmodelDetailTmpDto == null) {
            return 0;
        }
        PpmDepkpiDepmodelDetailTmp parpp = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmp.class);
        //修改模板表状态
        PpmDepkpiDepModelDto ppmDepkpiDepModelDto = new PpmDepkpiDepModelDto();
        ppmDepkpiDepModelDto.setDepid(ppmDepkpiDepmodelDetailTmpDto.getDepid());
        ppmDepkpiDepModelDto.setCommitflag(0L);
        iPpmDepkpiDepModelService.updateDepModelCommitFlag(ppmDepkpiDepModelDto, userid);
        //删除数据（递归删除）
        myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, parpp, IMyBatisDao.SQL.DELETE, "deleteTmpByDepid");
        //判断此模块是否有父节点 且 是否有且仅有此一个子节点
        if (ppmDepkpiDepmodelDetailTmpDto.getParentid() != null) {
            List<PpmDepkpiModelDetailTmp> pars = myBatisDao.selectListT(PPMDEPKPIDEPMODELDETAILTMP,
                    parpp, "queryByParentId");
            if (pars.isEmpty()) {
                //修改父节点为无子节点
                parpp.setHasson(0L);
                myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, parpp, IMyBatisDao.SQL.UPDATE, "upPojoById");
            }
        }
        return 1;
    }

    @Override
    public int deleteDepTargetByDepid(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto) {
        PpmDepkpiDepmodelDetailTmp ppmDepkpiDepmodelDetailTmp = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmp.class);
        return myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, ppmDepkpiDepmodelDetailTmp,
                IMyBatisDao.SQL.DELETE, "deleteByDepid");
    }

    @Override
    @Transactional("defaultTx")
    public int updateDetail(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto) {
        //部门修改已分配指标
        PpmDepkpiDepmodelDetailTmp ppmDepkpiDepmodelDetailTmp = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailTmpDto,
                PpmDepkpiDepmodelDetailTmp.class);
        //查询是否有子级（通过id当作父级id查询是否有子节点）
        PpmDepkpiDepmodelDetailTmp pp=new PpmDepkpiDepmodelDetailTmp();
        pp.setParentid(ppmDepkpiDepmodelDetailTmp.getId());
        List<PpmDepkpiDepmodelDetailTmp>list=myBatisDao.selectListT(PPMDEPKPIDEPMODELDETAILTMP,
                pp, "queryByParentId");
        //只能修改最下级数据，如果有子集则提示不能修改
        if(!Collections.isEmpty(list)){
            return -1;
        }
        return myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAILTMP, ppmDepkpiDepmodelDetailTmp, IMyBatisDao.SQL.UPDATE,
                "updatePojoSpecial");
    }

    @Override
    public List<PpmDepkpiDepmodelDetailTmpDto> queryByDepId(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailDto) {
        PpmDepkpiDepmodelDetailTmp pdmdt = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetailDto,
                PpmDepkpiDepmodelDetailTmp.class);
        List<PpmDepkpiDepmodelDetailTmp> ppms = myBatisDao.selectListT(PPMDEPKPIDEPMODELDETAILTMP, pdmdt,
                "queryByDepId");
        List<PpmDepkpiDepmodelDetailTmpDto> list = new ArrayList<>();
        ppms.forEach(e -> list.add(JsonUtil.jsonToClass(e, PpmDepkpiDepmodelDetailTmpDto.class)));
        return list;
    }

    @Override
    public Long queryCount(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepmodelDetailDto, PpmDepkpiDepmodelDetailTmpDto.class);
        return myBatisDao.selectCount(PPMDEPKPIDEPMODELDETAILTMP, map,"queryCountByHassonAndTargettype");
    }

}
