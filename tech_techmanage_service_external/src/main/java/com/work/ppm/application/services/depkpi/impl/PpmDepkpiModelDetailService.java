package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailTmpDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiModelDetail;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelDetailService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelDetailTmpService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelService;
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
 * 模板详情
 *
 */
@Service("ppmDepkpiModelDetailService")
public class PpmDepkpiModelDetailService extends BaseDaoSupper implements IPpmDepkpiModelDetailService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIMODELDETAIL = "com.work.ppm.application.model.depkpi.PpmDepkpiModelDetail";
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

    /**
     * 临时表服务
     */
    @Autowired
    private IPpmDepkpiModelDetailTmpService iPpmDepkpiModelDetailTmpService;

    @Override
    public void queryPager(PpmDepkpiModelDetailDto ppmDepkpiModelDetailDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiModelDetailDto, PpmDepkpiModelDetailDto.class);
        myBatisDao.selectPager(PPMDEPKPIMODELDETAIL, map, pager, "queryPagerList");
    }


    @Override
    public PpmDepkpiModelDetailDto queryDetail(PpmDepkpiModelDetailDto pp) {
        PpmDepkpiModelDetail ppmDepkpiModelDetail = JsonUtil.jsonToClass(pp, PpmDepkpiModelDetail.class);
        return JsonUtil.jsonToClass(myBatisDao.selectClass(PPMDEPKPIMODELDETAIL, ppmDepkpiModelDetail,
                "queryDetail"), PpmDepkpiModelDetailDto
                .class);

    }
    @Override
    @Transactional("defaultTx")
    public int commitModelTarget(PpmDepkpiModelDetailDto ppm, String userid) {
        PpmDepkpiModelDetailTmpDto pp = new PpmDepkpiModelDetailTmpDto();
        pp.setModelid(ppm.getModelid());
        List<PpmDepkpiModelDetailTmpDto> ppmds = this.iPpmDepkpiModelDetailTmpService.queryByModelId(pp);
        //当hasson为0的时候 判断targettype为空返回错误提示
        PpmDepkpiModelDetailTmpDto detailTmpDto = new PpmDepkpiModelDetailTmpDto();
        detailTmpDto.setModelid(ppm.getModelid());
        Long count = iPpmDepkpiModelDetailTmpService.queryCount(detailTmpDto);
        if (count > 0) {
            return 0;
        }

        //末节点分数求和
        long sumPoint = ppmds.stream().filter(e -> e.getHasson() == 0L)
                .mapToLong(PpmDepkpiModelDetailTmpDto::getBasepoint).sum();
        if (sumPoint != 100) {
            return -1;
        }
        //删除原有数据
        PpmDepkpiModelDetail pd = new PpmDepkpiModelDetail();
        pd.setModelid(ppm.getModelid());
        myBatisDao.executionPojo(PPMDEPKPIMODELDETAIL, pd,
                IMyBatisDao.SQL.DELETE, "deleteByModelId");
        ppmds.forEach(e -> {
            //同步到表
            PpmDepkpiModelDetail ppmDepkpiModelDetail = JsonUtil.jsonToClass(e, PpmDepkpiModelDetail.class);
            myBatisDao.executionPojo(PPMDEPKPIMODELDETAIL, ppmDepkpiModelDetail,
                    IMyBatisDao.SQL.INSTER, "addPojo");
        });
        //修改模板表状态已提交
        PpmDepkpiModelDto ppmDepkpiModelDto = new PpmDepkpiModelDto();
        ppmDepkpiModelDto.setId(ppm.getModelid());
        //用于服务区别
        ppmDepkpiModelDto.setCommitflag(2L);
        this.ppmDepkpiModelService.updateModel(ppmDepkpiModelDto, userid);
        return 1;
    }

    @Override
    public List<PpmDepkpiModelDetailDto> queryByMid(PpmDepkpiModelDetailDto pp) {
        PpmDepkpiModelDetail ppmDepkpiModelDetail = JsonUtil.jsonToClass(pp, PpmDepkpiModelDetail.class);
        List<PpmDepkpiModelDetail> pps = myBatisDao.selectListT(PPMDEPKPIMODELDETAIL, ppmDepkpiModelDetail,
                "queryByMid");
        return JsonUtil.jsonToList(pps, PpmDepkpiModelDetailDto.class);
    }
}
