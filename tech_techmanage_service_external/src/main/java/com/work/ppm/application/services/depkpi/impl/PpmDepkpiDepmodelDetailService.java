package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailTmpDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetail;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiDepmodelDetailInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepModelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailTmpService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 部门模板详情接口
 *
 */
@Service("ppmDepkpiDepmodelDetailService")
public class PpmDepkpiDepmodelDetailService extends BaseDaoSupper implements IPpmDepkpiDepmodelDetailService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIDEPMODELDETAIL = "com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetail";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 数据接口
     */
    @Autowired
    IPpmDepkpiDepmodelDetailTmpService iPpmDepkpiDepmodelDetailTmpService;

    /**
     * 部门模板分配服务
     */
    @Autowired
    private IPpmDepkpiDepModelService iPpmDepkpiDepModelService;

    @Override
    public void queryPager(PpmDepkpiDepmodelDetailDto ppmDepkpiDepmodelDetailDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepmodelDetailDto, PpmDepkpiDepmodelDetailDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPMODELDETAIL, map, pager, "queryPagerList");
    }

    @Override
    @Transactional("defaultTx")
    public int commitDepModelTarget(PpmDepkpiDepmodelDetailDto ppmDepkpiDepmodelDetailDto, String userid) {
        //部门提交接口
        PpmDepkpiDepmodelDetailTmpDto pp = new PpmDepkpiDepmodelDetailTmpDto();
        pp.setDepid(ppmDepkpiDepmodelDetailDto.getDepid());
        List<PpmDepkpiDepmodelDetailTmpDto> ppmds = iPpmDepkpiDepmodelDetailTmpService.queryByDepId(pp);
        //当hasson为0的时候 判断targettype为空返回错误提示
        PpmDepkpiDepmodelDetailTmpDto detailTmpDto = new PpmDepkpiDepmodelDetailTmpDto();
        detailTmpDto.setDepid(ppmDepkpiDepmodelDetailDto.getDepid());
        //验证是否有最子集（有子节点则求分数）
        Long count = iPpmDepkpiDepmodelDetailTmpService.queryCount(detailTmpDto);
        if (count > 0) {
            return 0;
        }
        //末节点分数求和
        long sumPoint = ppmds.stream().filter(e -> e.getHasson() == 0L)
                .mapToLong(PpmDepkpiDepmodelDetailTmpDto::getBasepoint).sum();
        if (sumPoint != 100) {
            return -1;
        }
        //刪除原有数据
        PpmDepkpiDepmodelDetail pd = new PpmDepkpiDepmodelDetail();
        pd.setDepid(ppmDepkpiDepmodelDetailDto.getDepid());
        myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAIL, pd, IMyBatisDao.SQL.DELETE, "deleteByDepid");
        ppmds.forEach(e -> {
            //同步到表
            PpmDepkpiDepmodelDetail ppmDepkpiDepmodelDetail = JsonUtil.jsonToClass(e, PpmDepkpiDepmodelDetail.class);
            myBatisDao.executionPojo(PPMDEPKPIDEPMODELDETAIL, ppmDepkpiDepmodelDetail, IMyBatisDao.SQL.INSTER,
                    "addPojo");
        });
        //修改部门模板分配表状态已提交
        PpmDepkpiDepModelDto ppmDepkpiDepModelDto = new PpmDepkpiDepModelDto();
        ppmDepkpiDepModelDto.setDepid(ppmDepkpiDepmodelDetailDto.getDepid());
        ppmDepkpiDepModelDto.setCommitflag(1L);
        iPpmDepkpiDepModelService.updateDepModelCommitFlag(ppmDepkpiDepModelDto, userid);
        return 1;

    }

    @Override
    public List<PpmDepkpiDepmodelDetail> queryPpmDepkpiTargetList(PpmDepkpiDepmodelDetailDto pp) {
        //查询出所有未制定的部门
        PpmDepkpiDepmodelDetailInfo p = JsonUtil.jsonToClass(pp, PpmDepkpiDepmodelDetailInfo.class);
        return myBatisDao.selectListT(PPMDEPKPIDEPMODELDETAIL, p, "queryNoLaunchList");
    }

}
