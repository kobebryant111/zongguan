package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepCheckDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiContent;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepCheck;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepRel;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetail;
import com.work.ppm.application.model.depkpi.PpmDepkpiTitle;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiDepCheckInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiContentService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepCheckService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepRelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTargetService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTitleService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门考核详情接口
 *
 */
@Service("ppmDepkpiDepCheckService")
public class PpmDepkpiDepCheckService extends BaseDaoSupper implements IPpmDepkpiDepCheckService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIDEPCHECK = "com.work.ppm.application.model.depkpi.PpmDepkpiDepCheck";

    /**
     * 排序名称
     */
    private static final String ORDER_FILEDS = "depid";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 内容接口
     */
    @Autowired
    private IPpmDepkpiContentService iPpmDepkpiContentService;
    /**
     * 标题接口
     */
    @Autowired
    private IPpmDepkpiTitleService iPpmDepkpiTitleService;
    /**
     * 部门考核详情表
     */
    @Autowired
    private IPpmDepkpiDepmodelDetailService ppmDepkpiDepmodelDetailService;

    /**
     * 指标接口
     */
    @Autowired
    private IPpmDepkpiTargetService ppmDepkpiTargetService;

    /**
     * 部门关系表接口
     */
    @Autowired
    private IPpmDepkpiDepRelService ppmDepkpiDepRelService;

    @Override
    public PpmDepkpiDepCheckDto queryDetail(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiDepCheck ppmDepkpiDepCheck = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        PpmDepkpiDepCheck data = myBatisDao.selectClass(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                "queryDetail");
        return JsonUtil.jsonToClass(data, PpmDepkpiDepCheckDto.class);
    }

    @Override
    public int updatePpmDepkpiDepCheck(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiDepCheck ppmDepkpiDepCheck = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        return myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    /**
     * 修改评价提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    private int updateCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiDepCheck ppmDepkpiDepCheck = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        return myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck, IMyBatisDao.SQL.UPDATE, "updateCommit");
    }


    @Override
    @Transactional("defaultTx")
    public int commitZbzd(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        //考核制定提交
        PpmDepkpiDepCheck ppmDepkpiDepCheck = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        Integer res = pointCheck(ppmDepkpiDepCheckDto, ppmDepkpiDepCheck);
        if (res != null) {
            return res;
        }
        //公共
        if(ppmDepkpiDepCheckDto.getId()!=null){
            ppmDepkpiDepCheckDto.setStatus(3L);
            ppmDepkpiDepCheckDto.setDbuser("");
            this.updateCommit(ppmDepkpiDepCheckDto);
            //验证父级是否全部
            PpmDepkpiDepCheckDto pp = this.queryDetail(ppmDepkpiDepCheckDto);
            if (pp.getParentid()==null){
                return 1;
            }
            List<PpmDepkpiDepCheck>pars = myBatisDao.selectListT(PPMDEPKPIDEPCHECK,
                    JsonUtil.jsonToClass(pp, PpmDepkpiDepCheck.class), "queryByParentId");
            Map<Long, List<PpmDepkpiDepCheck>> byPar=new HashMap<>();
            byPar.put(pp.getParentid(),pars);
            updateParentCheck(byPar);
        }else {
            //自定义
            ppmDepkpiDepCheck.setStatus(3L);
            myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                    IMyBatisDao.SQL.UPDATE, "updatePojoByYearAndDepid");
            //修改父级 查询最底层子级
            List<PpmDepkpiDepCheck> sons = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                    "querySonByYearAndDepid");
            sons.forEach(e -> {
                if (e.getParentid() == null) {
                    e.setParentid(-1L);
                }
            });
            Map<Long, List<PpmDepkpiDepCheck>> byPar = sons.stream().
                    collect(Collectors.groupingBy(PpmDepkpiDepCheck::getParentid));
                updateParentCheck(byPar);
        }
        return 1;
    }

    /**
     * 分值判断
     * @param ppmDepkpiDepCheckDto 对象
     * @param ppmDepkpiDepCheck 对象
     * @return int
     */
    private Integer pointCheck(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, PpmDepkpiDepCheck ppmDepkpiDepCheck) {
        //分值判断
        List<PpmDepkpiDepCheck> list = new ArrayList<>();
        if (ppmDepkpiDepCheck.getDepid() != null) {
            ppmDepkpiDepCheck.setTargettype(2L);
            list = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck, "queryByDepId");
        } else {
            list.add(JsonUtil.jsonToClass(queryDetail(ppmDepkpiDepCheckDto), PpmDepkpiDepCheck.class));
        }
        for (PpmDepkpiDepCheck e : list) {
            List<PpmDepkpiTitle> titles = this.iPpmDepkpiTitleService.queryByDetailId(e.getId());
            if (titles.stream().mapToLong(PpmDepkpiTitle::getBasepoint).sum() != e.getBasepoint()) {
                return -1;
            }
            for (PpmDepkpiTitle title : titles) {
                List<PpmDepkpiContent> cons = this.iPpmDepkpiContentService.queryByTitleId(title.getId());
                if (cons.stream().mapToLong(PpmDepkpiContent::getBasepoint).sum() != title.getBasepoint()) {
                    return -2;
                }
            }
        }
        return null;
    }

    @Override
    public void queryCheckPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setIsdel(0L);
        ppmDepkpiDepCheckDto.setHasson(0L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        //考核分页查询
        if (ppmDepkpiDepCheckDto.getSign() == 1L) {
            myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryAllPagerList");
        } else {
            myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryCenterPagerList");
        }

    }

    @Override
    public void queryGgCheckPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setTargettype(1L);
        ppmDepkpiDepCheckDto.setHasson(0L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryGgCheckPage");
    }

    @Override
    public void queryGgCheckDetailPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setTargettype(1L);
        ppmDepkpiDepCheckDto.setHasson(0L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryGgCheckDetailPage");
    }

    @Override
    public int insertCheck(PpmDepkpiDepCheckDto ppm) {
        //查询详情表信息插入check表
        PpmDepkpiDepmodelDetailDto pd = new PpmDepkpiDepmodelDetailDto();
        pd.setSign(ppm.getSign());
        pd.setTyear(ppm.getTyear());
        //判断是自定义考核
        //查询考核详情信息PpmDepkpiDepmodelDetail  查询出当年未已发起数据
        List<PpmDepkpiDepmodelDetail> datas = ppmDepkpiDepmodelDetailService.queryPpmDepkpiTargetList(pd);
        if (datas.isEmpty()) {
            return 1;
        }
        //父级id为null手动塞-1，方便流分组
        datas.forEach(e -> {
            if (e.getParentid() == null) {
                e.setParentid(-1L);
            }
        });

        if (!Collections.isEmpty(datas)) {
            //根据父级id分组
            Map<Long, List<PpmDepkpiDepmodelDetail>> collect = datas.stream().
                    collect(Collectors.groupingBy(PpmDepkpiDepmodelDetail::getParentid));
            //获取一级记录
            List<PpmDepkpiDepmodelDetail> firsts = collect.get(-1L);
            //添加记录
            firsts.forEach(e -> addChek(collect, e, ppm.getTyear()));
        }
        return 1;
    }

    /**
     * 添加
     *
     * @param year                    cc
     * @param collect                 添加对象总分组集合
     * @param ppmDepkpiDepmodelDetail 添加目标对象
     */
    private void addChek(Map<Long, List<PpmDepkpiDepmodelDetail>> collect,
                         PpmDepkpiDepmodelDetail ppmDepkpiDepmodelDetail, String year) {

        PpmDepkpiDepCheck depCheck = JsonUtil.jsonToClass(ppmDepkpiDepmodelDetail, PpmDepkpiDepCheck.class);
        long newId = getNextSequence(PpmDepkpiDepCheck.class);
        depCheck.setId(newId);
        depCheck.setTyear(year);
        depCheck.setStatus(0L);
        depCheck.setParentid(ppmDepkpiDepmodelDetail.getParentid() == -1 ?
                null : ppmDepkpiDepmodelDetail.getParentid());
        PpmDepkpiDepRelDto ppmDepkpiDepRelDto = new PpmDepkpiDepRelDto();
        if (depCheck.getSign() == 1) {
            if (depCheck.getTargettype() == null || 2 == depCheck.getTargettype()) {
                // 直接用depid查部门制定人
                ppmDepkpiDepRelDto.setDepid(String.valueOf(depCheck.getDepid()));
                ppmDepkpiDepRelDto.setSign(1L);
                depCheck.setMarkerdepid(depCheck.getDepid());
            } else {
                // 查指标的制定部门的制定人
                ppmDepkpiDepRelDto.setSign(1L);
                ppmDepkpiDepRelDto.setDepid(String.valueOf(depCheck.getMarkerdepid()));
            }
        } else {
            if (depCheck.getTargettype() == null || 2 == depCheck.getTargettype()) {
                // 直接用depid查部门制定人
                ppmDepkpiDepRelDto.setCenterid(String.valueOf(depCheck.getDepid()));
                ppmDepkpiDepRelDto.setSign(2L);
                depCheck.setMarkerdepid(depCheck.getDepid());
            } else {
                // 查指标的制定部门的制定人
                ppmDepkpiDepRelDto.setSign(2L);
                ppmDepkpiDepRelDto.setCenterid(String.valueOf(depCheck.getMarkerdepid()));
            }
        }
        PpmDepkpiDepRel pr = ppmDepkpiDepRelService.queryDepRelByDepIdOrCenterid(ppmDepkpiDepRelDto);
        depCheck.setDbuser(pr.getMarker());
        myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, depCheck, IMyBatisDao.SQL.INSTER, "addPojo");

        //塞子级
        List<PpmDepkpiDepmodelDetail> sonLists = collect.get(ppmDepkpiDepmodelDetail.getId());
        if (!Collections.isEmpty(sonLists)) {
            sonLists.forEach(e -> {
                e.setParentid(newId);
                addChek(collect, e, year);
            });
        }

    }

    @Override
    public List<PpmDepkpiDepCheckInfo> queryMyDbNum(String userid) {
        PpmDepkpiDepCheck ppmDepkpiDepCheck = new PpmDepkpiDepCheck();
        ppmDepkpiDepCheck.setUserid(userid);
        ppmDepkpiDepCheck.setHasson(0L);
        List<PpmDepkpiDepCheck> pps = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                "queryMyDbNum");
        List<PpmDepkpiDepCheckInfo> resList = new ArrayList<>();
        //根据年分组
        Map<String, List<PpmDepkpiDepCheck>> byYear = pps.stream().
                collect(Collectors.groupingBy(PpmDepkpiDepCheck::getTyear));
        byYear.forEach((a, b) -> {
            //根据部门级别分组
            Map<Long, List<PpmDepkpiDepCheck>> buSign = b.stream().
                    collect(Collectors.groupingBy(PpmDepkpiDepCheck::getSign));
            buSign.forEach((e, f) -> {
                PpmDepkpiDepCheckInfo res = new PpmDepkpiDepCheckInfo();
                PpmDepkpiDepCheckInfo temp = new PpmDepkpiDepCheckInfo();
                res.setTyear(a);
                res.setSign(e);
                List<PpmDepkpiDepCheck> zdy = f.stream().filter(n -> n.getTargettype() == 2)
                        .collect(Collectors.toList());
                List<PpmDepkpiDepCheck> gg = f.stream().filter(n -> n.getTargettype() == 1)
                        .collect(Collectors.toList());
                temp.setZdydb_zd(zdy.stream().filter(zd -> zd.getStatus() == 0).count());
                temp.setGgdb_zd(gg.stream().filter(zd -> zd.getStatus() == 0).count());
                temp.setZdydb_zp(zdy.stream().filter(zp -> zp.getStatus() == 4).count());
                temp.setZdykh_pj((long) zdy.stream()
                        .filter(pj -> pj.getStatus() == 5||pj.getStatus() == 6)
                        .collect(Collectors.groupingBy(PpmDepkpiDepCheck::getDepid)).size());
                temp.setGgkh_pj((long) gg.stream()
                        .filter(pj ->pj.getStatus() == 6)
                        .collect(Collectors.groupingBy(PpmDepkpiDepCheck::getDepid)).size());
                temp.setGgdb_sh(0L);
                temp.setZdydb_sh(0L);
                temp.setKh_pji(0L);
                res.setData(temp);
                resList.add(res);
            });
        });
        return resList;
    }

    @Override
    public void queryZdyzbzdPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setHasson(0L);
        ppmDepkpiDepCheckDto.setIsdel(0L);
        ppmDepkpiDepCheckDto.setStatus(0L);
        ppmDepkpiDepCheckDto.setTargettype(2L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        pager.setSortOrder("desc");
        pager.setSortField(ORDER_FILEDS);
        myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryZdyzbzdPagerList");
    }

    @Override
    public void queryZdyzbzpPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setHasson(0L);
        ppmDepkpiDepCheckDto.setStatus(4L);
        ppmDepkpiDepCheckDto.setTargettype(2L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        pager.setSortOrder("desc");
        pager.setSortField(ORDER_FILEDS);
        myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryZdyzbzpPagerList");

    }

    @Override
    public void queryGgzbzdPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setHasson(0L);
        ppmDepkpiDepCheckDto.setIsdel(0L);
        ppmDepkpiDepCheckDto.setStatus(0L);
        ppmDepkpiDepCheckDto.setTargettype(1L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        pager.setSortOrder("desc");
        pager.setSortField(ORDER_FILEDS);
        myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryGgzbzdPagerList");
    }

    @Override
    @Transactional("defaultTx")
    public int zpCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        //查询内容表看是否全部自评完毕
        List<PpmDepkpiContent> pps = iPpmDepkpiContentService.queryZdyCommit(ppmDepkpiDepCheckDto);
        if (!Collections.isEmpty(pps)) {
            return -1;
        }
        //查询当年部门下所有数据
        ppmDepkpiDepCheckDto.setTargettype(2L);
        List<PpmDepkpiDepCheckInfo> pp = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheckDto,
                "zpCommit");
        PpmDepkpiDepCheckDto pp1 = new PpmDepkpiDepCheckDto();
        pp.forEach(e -> {
            pp1.setId(e.getId());
            pp1.setDbuser(e.getSecondcheck());
            pp1.setStatus(6L);
            if (e.getFirstcheck() != null && !e.getFirstcheck().equals(e.getSecondcheck())) {
                pp1.setDbuser(e.getFirstcheck());
                pp1.setStatus(5L);
            }
            this.updatePpmDepkpiDepCheck(pp1);
        });
        upPar(ppmDepkpiDepCheckDto);
        return 1;
    }

    /**
     * 查询数据按父级分组
     *
     * @param ppmDepkpiDepCheckDto 对象
     */
    private void upPar(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        //查询所有数据 按父级分组
        List<PpmDepkpiDepCheck> ppm = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheckDto,
                "queryAll");
        ppm.forEach(e -> {
            if (e.getParentid() == null) {
                e.setParentid(-1L);
            }
        });
        //修改父级
        this.updateParentCheck(ppm.stream().collect(Collectors.groupingBy(PpmDepkpiDepCheck::getParentid)));
    }

    @Override
    @Transactional("defaultTx")
    public int zdyPjCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        ppmDepkpiDepCheckDto.setRemark("自定义");
        List<PpmDepkpiContent> pps = iPpmDepkpiContentService.queryZdyCommit(ppmDepkpiDepCheckDto);
        if (!Collections.isEmpty(pps)) {
            //当前人不可以评价提交
            if (pps.get(0).getId()==null){
                return -2;
            }
            return -1;
        }
        //查询当年部门下所有数据
        List<PpmDepkpiDepCheckInfo> pp = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheckDto,
                "zdyPjCommit");
        PpmDepkpiDepCheckDto pp1 = new PpmDepkpiDepCheckDto();
        pp.forEach(e -> {
            pp1.setId(e.getId());
            pp1.setStatus(7L);
            pp1.setDbuser("");
            if (e.getStatus()==5L){
                pp1.setStatus(6L);
                pp1.setDbuser(e.getSecondcheck());
            }
            this.updateCommit(pp1);
        });
        //查询所有数据 按父级分组
        upPar(ppmDepkpiDepCheckDto);
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int upCheckStatus(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiDepCheck p = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        Long status = p.getStatus();
        updateCheckStatus(p, status);
        return 1;
    }

    /**
     * 递归修改状态
     * @param ppmDepkpiDepCheck 对象
     * @param status            状态
     */
    private void updateCheckStatus(PpmDepkpiDepCheck ppmDepkpiDepCheck, Long status) {
        //查询详情
        List<PpmDepkpiDepCheck> ppms = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                "queryTreeById");
        //最底层
        List<PpmDepkpiDepCheck> sons = ppms.stream().filter(e -> e.getHasson() == 0L).collect(Collectors.toList());
        for (PpmDepkpiDepCheck son : sons) {
            PpmDepkpiDepCheckDto pp1 = new PpmDepkpiDepCheckDto();
            pp1.setId(son.getId());
            pp1.setStatus(status);
            pp1.setSelfeval(son.getSelfeval());
            //查代办人
            PpmDepkpiDepCheckInfo queryDbuser = myBatisDao.selectClass(PPMDEPKPIDEPCHECK, son,
                    "queryDbuser");
            if(1 == son.getTargettype()){ //公共指标
                this.dealGgStatus(pp1, queryDbuser);
            } else if(2 == son.getTargettype()) { // 自定义指标
                this.dealZdyStatus(pp1, queryDbuser);
            }
            //修改
            this.updatePpmDepkpiDepCheck(pp1);
        }
        //修改完之后再查一遍
        List<PpmDepkpiDepCheck> upTree = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                "queryTreeById");
        List<PpmDepkpiDepCheck> upSons = upTree.stream().filter(e -> e.getHasson() == 0L).collect(Collectors.toList());
        //修改父级 查询最底层子级
        upSons.forEach(e -> {
            if (e.getParentid() == null) {
                e.setParentid(-1L);
            }
        });
        updateParentCheck(upSons.stream().collect(Collectors.groupingBy(PpmDepkpiDepCheck::getParentid)));

    }

    /**
     * 判断自定义状态
     * @param check 源对象
     * @param queryDbuser 代办人
     */
    private void dealZdyStatus(PpmDepkpiDepCheckDto check, PpmDepkpiDepCheckInfo queryDbuser){
        if((0 == check.getStatus())
                || (4 == check.getStatus() &&check.getSelfeval()!=null&& 1 == check.getSelfeval()) ) {
            // 修改状态为草稿中；修改状态为待自评，且确实需要自评
            check.setDbuser(queryDbuser.getDbuser());
        } else if (3 == check.getStatus() || 7 == check.getStatus()) { // 执行中、已完成状态无代办人
            check.setDbuser("");
        } else if (4 == check.getStatus() || 5 == check.getStatus()) {
            // 修改状态为自评，无自评直接判断是否有初评；或者修改状态为待初评，也需要判断是否有初评
            if (null == queryDbuser.getFirstcheck()
                    || queryDbuser.getFirstcheck().equals(queryDbuser.getSecondcheck())) {
                // 初评人为空，或者初评人与终评人一致，直接进入终评
                check.setStatus(6L);
                check.setDbuser(queryDbuser.getSecondcheck());
            } else {
                check.setStatus(5L);
                check.setDbuser(queryDbuser.getFirstcheck());
            }
        } else if(6 == check.getStatus()) {
            check.setDbuser(queryDbuser.getSecondcheck());
        }
    }

    /**
     * 判断公共状态
     * @param check 源对象
     * @param queryDbuser 代办人
     */
    private void dealGgStatus(PpmDepkpiDepCheckDto check, PpmDepkpiDepCheckInfo queryDbuser){
        if(0 == check.getStatus()) {
            // 修改状态为草稿中；
            check.setDbuser(queryDbuser.getDbuser());
        } else if (3 == check.getStatus() || 7 == check.getStatus()) {
            check.setDbuser("");
        } else if (4 == check.getStatus() || 5 == check.getStatus() || 6 == check.getStatus()) {
            check.setStatus(6L);
            check.setDbuser(queryDbuser.getSecondcheck());
        }
    }

    @Override
    public void getListPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        pager.setSortOrder("desc");
        pager.setSortField(ORDER_FILEDS);
        myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "getListPage");
    }

    @Override
    public List<PpmDepkpiDepCheckDto> getCheckTree(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        PpmDepkpiDepCheck p = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        List<PpmDepkpiDepCheckInfo> datas = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, p, "getCheckTree");
        return JsonUtil.jsonToList(datas, PpmDepkpiDepCheckDto.class);
    }

    @Override
    @Transactional("defaultTx")
    public int ggPjCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        //校验内容是否全部提交
        List<PpmDepkpiContent> pps = iPpmDepkpiContentService.queryGgCommit(ppmDepkpiDepCheckDto);
        if (!Collections.isEmpty(pps)) {
            return -1;
        }
        //修改状态
        List<PpmDepkpiDepCheckInfo> pp = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheckDto,
                "ggPjCommit");
        PpmDepkpiDepCheckDto pp1 = new PpmDepkpiDepCheckDto();
        pp.forEach(e -> {
            pp1.setId(e.getId());
            pp1.setStatus(7L);
            pp1.setDbuser("");
            this.updateCommit(pp1);
        });
        //查询所有数据 按父级分组
        upPar(ppmDepkpiDepCheckDto);
        return 1;
    }

    @Override
    public void queryPjPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager) {
        ppmDepkpiDepCheckDto.setIsdel(0L);
        ppmDepkpiDepCheckDto.setHasson(0L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepCheckDto, PpmDepkpiDepCheckDto.class);
        if(ppmDepkpiDepCheckDto.getSign() == 1){
            myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryPjPagerList");
        }else {
            myBatisDao.selectPager(PPMDEPKPIDEPCHECK, map, pager, "queryCenterPjPagerList");
        }

    }

    @Override
    public int updateCheck(PpmDepkpiDepCheckDto pdd) {
        //考核评价发起接口
        //查询出所有数据循环获取数据修改代办人和状态
        PpmDepkpiDepCheck ppmDepkpiDepCheck = JsonUtil.jsonToClass(pdd, PpmDepkpiDepCheck.class);
        myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck, IMyBatisDao.SQL.UPDATE, "updateCheck");
        myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck, IMyBatisDao.SQL.UPDATE, "updateBytargettype");
        myBatisDao.executionPojo(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck, IMyBatisDao.SQL.UPDATE, "updateGgCheck");
        //修改父级 查询最底层子级
        List<PpmDepkpiDepCheck> sons = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                "querySon");
        sons.forEach(e -> {
            if (e.getParentid() == null) {
                e.setParentid(-1L);
            }
        });
        Map<Long, List<PpmDepkpiDepCheck>> byPar = sons.stream().
                collect(Collectors.groupingBy(PpmDepkpiDepCheck::getParentid));
            updateParentCheck(byPar);
        return 1;
    }

    /**
     * 递归修改父级状态
     *
     * @param byPar 对象
     */
    private void updateParentCheck(Map<Long, List<PpmDepkpiDepCheck>> byPar) {
        if (Collections.isEmpty(byPar)){
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (!Collections.isEmpty(byPar)) {
            byPar.forEach((a, b) -> {
                sb.append(a).append(",");
                PpmDepkpiDepCheckDto pp = new PpmDepkpiDepCheckDto();
                pp.setId(a);
                Long status = b.stream().map(PpmDepkpiDepCheck::getStatus).distinct().
                        min(Comparator.comparing(Long::longValue)).get();
                pp.setStatus(status);
                String dbuser = b.stream().filter(e -> e.getStatus().equals(status))
                        .collect(Collectors.toList()).get(0).getDbuser();
                pp.setDbuser(dbuser==null?"":dbuser);
                this.updatePpmDepkpiDepCheck(pp);
            });
        }
        //查询父级
        String sql = sb.toString();
        sql = sql.substring(0, sql.length() - 1);//去除最后一个逗号
        PpmDepkpiDepCheck ppmDepkpiDepCheck = new PpmDepkpiDepCheck();
        ppmDepkpiDepCheck.setRemark(sql);
        List<PpmDepkpiDepCheck> pars = new ArrayList<>();
        if (sql.length() > 0) {
            pars = myBatisDao.selectListT(PPMDEPKPIDEPCHECK, ppmDepkpiDepCheck,
                    "queryParByIds");
        }
        if (!Collections.isEmpty(pars)) {
            pars.forEach(e -> {
                if (e.getParentid() == null) {
                    e.setParentid(-1L);
                }
            });
            updateParentCheck(pars.stream().collect(Collectors.groupingBy(PpmDepkpiDepCheck::getParentid)));
        }
    }

    @Override
    public PpmDepkpiDepCheckInfo queryCheckSum(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        //公共考核数据查询
        PpmDepkpiDepCheck p = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        p.setHasson(0L);
        PpmDepkpiDepCheckInfo res = myBatisDao.selectClass(PPMDEPKPIDEPCHECK, p,
                "getSumPointList");
        String sumpoint = res.getSumpoint();
        if (sumpoint.endsWith(".0")) {
            res.setSumpoint(sumpoint.substring(0, sumpoint.length() - 2));
        }
        return res;
    }

    @Override
    public PpmDepkpiDepCheckDto queryCheckSumAndPoint(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        //考核数据查询
        PpmDepkpiDepCheck p = JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class);
        PpmDepkpiDepCheck datas = myBatisDao.selectClass(PPMDEPKPIDEPCHECK, p,
                "getSumPoint");
        return JsonUtil.jsonToClass(datas, PpmDepkpiDepCheckDto.class);
    }

    @Override
    public PpmDepkpiDepCheckInfo querySumPointById(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        return myBatisDao.selectClass(PPMDEPKPIDEPCHECK,
                JsonUtil.jsonToClass(ppmDepkpiDepCheckDto, PpmDepkpiDepCheck.class), "querySumPointById");
    }
}
