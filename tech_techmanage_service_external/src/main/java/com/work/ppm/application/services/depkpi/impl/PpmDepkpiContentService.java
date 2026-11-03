package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiContentDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepCheckDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiContent;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepRel;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiContentInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiContentService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepCheckService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepRelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTitleService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 考核内容接口实现
 *
 */
@Service("ppmDepkpiContentService")
public class PpmDepkpiContentService extends BaseDaoSupper implements IPpmDepkpiContentService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPICONTENT = "com.work.ppm.application.model.depkpi.PpmDepkpiContent";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 标题接口
     */
    @Autowired
    private IPpmDepkpiTitleService iPpmDepkpiTitleService;

    /**
     * 部门关系
     */
    @Autowired
    private IPpmDepkpiDepRelService ippmDepkpiDepRelService;

    /**
     * check关系表
     */
    @Autowired
    private IPpmDepkpiDepCheckService ppmDepkpiDepCheckService;

    @Override
    @Transactional("defaultTx")
    public int addPpmDepkpiContent(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //新增考核内容
        PpmDepkpiContent ppmDepkpiContent = JsonUtil.jsonToClass(ppmDepkpiContentDto, PpmDepkpiContent.class);
        Long id = getNextSequence(PpmDepkpiContent.class);
        ppmDepkpiContent.setId(id);
        ppmDepkpiContent.setIsdel(0L);
        ppmDepkpiContent.setLastmoduserid(userid);
        ppmDepkpiContent.setLastmodtime(new Date());
        //根据id查询check表
        PpmDepkpiDepCheckDto pp=new PpmDepkpiDepCheckDto();
        pp.setId(ppmDepkpiContentDto.getDetailid());
        PpmDepkpiDepCheckDto respp = ppmDepkpiDepCheckService.queryDetail(pp);
        ppmDepkpiContent.setEvaltype(respp.getEvaltype());
        ppmDepkpiContent.setSelfeval(respp.getSelfeval());
        ppmDepkpiContent.setMaxpoint(respp.getMaxpoint());
        ppmDepkpiContent.setMinpoint(respp.getMinpoint());
        ppmDepkpiContent.setTargettype(respp.getTargettype());
        myBatisDao.executionPojo(PPMDEPKPICONTENT, ppmDepkpiContent, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int updatePpmDepkpiContent(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //修改考核内容
        PpmDepkpiContent ppmDepkpiContent = JsonUtil.jsonToClass(ppmDepkpiContentDto, PpmDepkpiContent.class);
        ppmDepkpiContent.setLastmodtime(new Date());
        ppmDepkpiContent.setLastmoduserid(userid);
        ppmDepkpiContent.setIsdel(0L);
        //根据id查询check表
        PpmDepkpiDepCheckDto pp=new PpmDepkpiDepCheckDto();
        pp.setId(ppmDepkpiContentDto.getDetailid());
        PpmDepkpiDepCheckDto respp = ppmDepkpiDepCheckService.queryDetail(pp);
        ppmDepkpiContent.setEvaltype(respp.getEvaltype());
        ppmDepkpiContent.setSelfeval(respp.getSelfeval());
        ppmDepkpiContent.setMaxpoint(respp.getMaxpoint());
        ppmDepkpiContent.setMinpoint(respp.getMinpoint());
        ppmDepkpiContent.setTargettype(respp.getTargettype());
        return this.updatePojo(ppmDepkpiContent);
    }

    @Override
    @Transactional("defaultTx")
    public int updateIsdelById(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //刪除考核内容
        PpmDepkpiContent ppmDepkpiContent = new PpmDepkpiContent();
        ppmDepkpiContent.setId(ppmDepkpiContentDto.getId());
        ppmDepkpiContent.setIsdel(1L);
        ppmDepkpiContent.setLastmodtime(new Date());
        ppmDepkpiContent.setLastmoduserid(userid);
        return this.updatePojo(ppmDepkpiContent);
    }

    @Override
    @Transactional("defaultTx")
    public int eval(PpmDepkpiContentDto ppm, String userid) {
        // 通过部门id查询出对象获取终评人和初评人
        PpmDepkpiContent ppmDepkpi = JsonUtil.jsonToClass(ppm, PpmDepkpiContent.class);
        PpmDepkpiContentInfo ppmm = myBatisDao.selectClass(PPMDEPKPICONTENT, ppmDepkpi,
                "queryDepIdAndSign");

        PpmDepkpiContent queryDetail = myBatisDao.selectClass(PPMDEPKPICONTENT, ppmDepkpi,
                "queryDetail");

        PpmDepkpiDepRelDto ppmDepkpiDepRelDto = new PpmDepkpiDepRelDto();
        if (ppmm.getSign() == 2) {
            ppmDepkpiDepRelDto.setSign(2L);
            ppmDepkpiDepRelDto.setCenterid(ppmm.getDepid() + "");
        } else {
            ppmDepkpiDepRelDto.setSign(1L);
            ppmDepkpiDepRelDto.setDepid(ppmm.getDepid() + "");
        }
        PpmDepkpiDepRel ppmDepkpiDepRel = ippmDepkpiDepRelService.queryDepRelByDepIdOrCenterid(ppmDepkpiDepRelDto);
        if (ppmDepkpiDepRel == null) {
            return -1;
        }
        PpmDepkpiContent ppmDepkpiContent = new PpmDepkpiContent();
        ppmDepkpiContent.setLastmoduserid(userid);
        ppmDepkpiContent.setLastmodtime(new Date());
        ppmDepkpiContent.setId(ppm.getId());
        int i;
        // 判断初评还是终评
        if (queryDetail.getTargettype() != 1
                && ppmDepkpiDepRel.getFirstcheck() != null
                && !ppmDepkpiDepRel.getFirstcheck().equals(ppmDepkpiDepRel.getSecondcheck())
                && userid.equals(ppmDepkpiDepRel.getFirstcheck())) {
            ppmDepkpiContent.setFirstcheckres(ppm.getCheckres());
            ppmDepkpiContent.setFirstcheckpoint(ppm.getCheckpoint());
            ppmDepkpiContent.setFirstcheckremark(ppm.getCheckremark());
            ppmDepkpiContent.setFirstcheckstatus(1L);
            ppmDepkpiContent.setFirstchecktime(new Date());
            i = myBatisDao.executionPojo(PPMDEPKPICONTENT, ppmDepkpiContent, IMyBatisDao.SQL.UPDATE, "upFir");
        } else {
            ppmDepkpiContent.setSecondcheckres(ppm.getCheckres());
            ppmDepkpiContent.setSecondcheckpoint(ppm.getCheckpoint());
            ppmDepkpiContent.setSecondcheckremark(ppm.getCheckremark());
            ppmDepkpiContent.setSecondchecktime(new Date());
            ppmDepkpiContent.setSecondcheckstatus(1L);
            i = myBatisDao.executionPojo(PPMDEPKPICONTENT, ppmDepkpiContent, IMyBatisDao.SQL.UPDATE, "upSec");
        }
        return i;
    }

    @Override
    public List<PpmDepkpiContent> queryZdyCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiContentInfo ppmDepkpiContentInfo = new PpmDepkpiContentInfo();
        ppmDepkpiContentInfo.setTyear(ppmDepkpiDepCheckDto.getTyear());
        ppmDepkpiContentInfo.setDepid(ppmDepkpiDepCheckDto.getDepid());
        ppmDepkpiContentInfo.setSign(3L);
        if ("自定义".equals(ppmDepkpiDepCheckDto.getRemark())) {
            PpmDepkpiDepRelDto ppmDepkpiDepRelDto = new PpmDepkpiDepRelDto();
            if (ppmDepkpiDepCheckDto.getSign() == 2) {
                ppmDepkpiDepRelDto.setSign(2L);
                ppmDepkpiDepRelDto.setCenterid(ppmDepkpiDepCheckDto.getDepid() + "");
            } else {
                ppmDepkpiDepRelDto.setSign(1L);
                ppmDepkpiDepRelDto.setDepid(ppmDepkpiDepCheckDto.getDepid() + "");
            }
            PpmDepkpiDepRel ppmDepkpiDepRel = ippmDepkpiDepRelService.queryDepRelByDepIdOrCenterid(ppmDepkpiDepRelDto);
            if (ppmDepkpiDepCheckDto.getUserid().equals(ppmDepkpiDepRel.getSecondcheck())) {
                ppmDepkpiContentInfo.setSign(2L);
            }else if (ppmDepkpiDepCheckDto.getUserid().equals(ppmDepkpiDepRel.getFirstcheck())){
                ppmDepkpiContentInfo.setSign(1L);
            }else {
                List<PpmDepkpiContent> list=new ArrayList<>();
                list.add(new PpmDepkpiContent());
                return list;
            }
        }
        return myBatisDao.selectListT(PPMDEPKPICONTENT, ppmDepkpiContentInfo, "queryZdyCommit");

    }

    @Override
    public List<PpmDepkpiContent> queryGgCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiContentInfo ppmDepkpiContentInfo = new PpmDepkpiContentInfo();
        ppmDepkpiContentInfo.setTyear(ppmDepkpiDepCheckDto.getTyear());
        ppmDepkpiContentInfo.setDepid(ppmDepkpiDepCheckDto.getDepid());
        ppmDepkpiContentInfo.setUserid(ppmDepkpiDepCheckDto.getUserid());
        return myBatisDao.selectListT(PPMDEPKPICONTENT, ppmDepkpiContentInfo, "queryGgCommit");
    }

    @Override
    public List<PpmDepkpiContentInfo> queryContentList(PpmDepkpiContentDto ppmDepkpiContentDto) {
        PpmDepkpiContentInfo ppmDepkpiContentInfo = JsonUtil.jsonToClass(ppmDepkpiContentDto,
                PpmDepkpiContentInfo.class);
        ppmDepkpiContentInfo.setIsdel(0L);
        return myBatisDao.selectListT(PPMDEPKPICONTENT, ppmDepkpiContentInfo, "queryContentList");
    }

    @Override
    public List<PpmDepkpiContentInfo> queryGgDbContentList(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //考核内容代办查询
        PpmDepkpiContentInfo ppmDepkpiContentInfo = JsonUtil.jsonToClass(ppmDepkpiContentDto,
                PpmDepkpiContentInfo.class);
        ppmDepkpiContentInfo.setIsdel(0L);
        ppmDepkpiContentInfo.setUserid(userid);
        return  myBatisDao.selectListT(PPMDEPKPICONTENT, ppmDepkpiContentInfo, "queryGgDbContentList");
    }

    @Override
    public int updateIsdelByTitleid(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //修改考核内容
        PpmDepkpiContent ppmDepkpiContent = JsonUtil.jsonToClass(ppmDepkpiContentDto, PpmDepkpiContent.class);
        ppmDepkpiContent.setLastmodtime(new Date());
        ppmDepkpiContent.setLastmoduserid(userid);
        ppmDepkpiContent.setIsdel(1L);
        return myBatisDao.executionPojo(PPMDEPKPICONTENT, ppmDepkpiContent,
                IMyBatisDao.SQL.UPDATE, "updatePojoBytitileid");
    }

    @Override
    public PpmDepkpiContentDto querySumPointAndTargettype(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //考核汇总结果查询
        //通过depid查询出所有考核部门表数据
        PpmDepkpiDepRelDto ppmDepkpiDepRel = new PpmDepkpiDepRelDto();
        if (ppmDepkpiContentDto.getSign() == 1) {
            ppmDepkpiDepRel.setSign(1L);
            ppmDepkpiDepRel.setDepid(String.valueOf(ppmDepkpiContentDto.getDepid()));
        } else {
            ppmDepkpiDepRel.setSign(2L);
            ppmDepkpiDepRel.setCenterid(String.valueOf(ppmDepkpiContentDto.getDepid()));
        }
        PpmDepkpiDepRel rel = ippmDepkpiDepRelService.queryDepRelByDepIdOrCenterid(ppmDepkpiDepRel);
        PpmDepkpiContent p = new PpmDepkpiContent();
        p.setTyear(ppmDepkpiContentDto.getTyear());
        p.setDepid(ppmDepkpiContentDto.getDepid());
        PpmDepkpiContentInfo data = new PpmDepkpiContentInfo();
        //总数查询有误，只查询初评分，公共项分在SECONDCHECKPOINT里
        if (userid.equals(rel.getSecondcheck())) {
            data = myBatisDao.selectClass(PPMDEPKPICONTENT, p,
                    "queryZpSum");
        } else if (userid.equals(rel.getFirstcheck())) {
            data = myBatisDao.selectClass(PPMDEPKPICONTENT, p,
                    "queryCpSum");
        } else {
             data.setCommitsum(0L);
        }
        return JsonUtil.jsonToClass(data, PpmDepkpiContentDto.class);
    }

    @Override
    public List<PpmDepkpiContentInfo> getEvalContentList(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        // 通过部门id查询出对象获取终评人和初评人
        List<PpmDepkpiContentInfo> datas=new ArrayList<>();
        //通过depid查询出所有考核部门表数据
        PpmDepkpiDepRelDto ppmDepkpiDepRel = new PpmDepkpiDepRelDto();
        if (ppmDepkpiContentDto.getSign() == 1) {
            ppmDepkpiDepRel.setSign(1L);
            ppmDepkpiDepRel.setDepid(String.valueOf(ppmDepkpiContentDto.getDepid()));
        } else {
            ppmDepkpiDepRel.setSign(2L);
            ppmDepkpiDepRel.setCenterid(String.valueOf(ppmDepkpiContentDto.getDepid()));
        }
        PpmDepkpiDepRel rel = ippmDepkpiDepRelService.queryDepRelByDepIdOrCenterid(ppmDepkpiDepRel);
        PpmDepkpiContentInfo ppmDepkpiContentInfo = JsonUtil.jsonToClass(ppmDepkpiContentDto,
                PpmDepkpiContentInfo.class);
        ppmDepkpiContentInfo.setIsdel(0L);

        if (rel.getSecondcheck().equals(userid)) {// 当前状态为终评
            if (!rel.getSecondcheck().equals(rel.getFirstcheck())) {
                ppmDepkpiContentInfo.setIsSecond(2L); // 有初评
            } else if (rel.getSecondcheck().equals(rel.getFirstcheck())) {
                ppmDepkpiContentInfo.setIsSecond(1L); // 无初评
            }
        } else if (rel.getFirstcheck().equals(userid)) {
            ppmDepkpiContentInfo.setIsSecond(0L); // 直接是初评
        } else {
            return datas;
        }

        datas = myBatisDao.selectListT(PPMDEPKPICONTENT, ppmDepkpiContentInfo, "getListIsSecond");
        datas = JsonUtil.jsonToList(datas, PpmDepkpiContentInfo.class);
        return datas;
    }

    @Override
    public List<PpmDepkpiContent> queryByTitleId(Long id) {
        PpmDepkpiContent ppmDepkpiContent = new PpmDepkpiContent();
        ppmDepkpiContent.setIsdel(0L);
        ppmDepkpiContent.setTitleid(id);
        return myBatisDao.selectListT(PPMDEPKPICONTENT, ppmDepkpiContent, "queryByTitleId");
    }

    @Override
    public int upSelfeval(PpmDepkpiContentDto ppmDepkpiContentDto, String userid) {
        //自评提交
        PpmDepkpiContent ppmDepkpiContent = JsonUtil.jsonToClass(ppmDepkpiContentDto, PpmDepkpiContent.class);
        ppmDepkpiContent.setLastmodtime(new Date());
        ppmDepkpiContent.setLastmoduserid(userid);
        ppmDepkpiContent.setSelfevalstatus(1L);
        return this.updatePojo(ppmDepkpiContent);
    }

    /**
     * 方法
     *
     * @param ppmDepkpiContent 对象
     * @return int
     */
    private int updatePojo(PpmDepkpiContent ppmDepkpiContent) {
        return myBatisDao.executionPojo(PPMDEPKPICONTENT, ppmDepkpiContent, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

}
