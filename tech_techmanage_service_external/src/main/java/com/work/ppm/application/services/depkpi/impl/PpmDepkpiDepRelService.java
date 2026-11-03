package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.EosDepKpiGrade;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepRel;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiDepRelInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepRelService;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 描述
 *
 */
@Service("ppmDepkpiDepRelService")
public class PpmDepkpiDepRelService extends BaseDaoSupper implements IPpmDepkpiDepRelService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIDEPREL = "com.work.ppm.application.model.depkpi.PpmDepkpiDepRel";

    /**
     *  字典
     */
    private static final String DEP_KPI_GRADE = "DEP_KPI_GRADE";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 部门分管接口
     */
    @Autowired
    private IPpmDaDeptManagerService ppmDaDeptManagerService;

    /**
     * 团队表
     */
    @Autowired
    private IPpmDaTeamGroupService ppmDaTeamGroupService;
    /**
     * 中心表
     */
    @Autowired
    private IPpmDaCenterService ppmDaCenterService;
    /**
     * 人员角色
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;

    @Override
    public void queryPager(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepRelDto, PpmDepkpiDepRelDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPREL, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmDepkpiDepRel(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) {
        PpmDepkpiDepRel ppmDepkpiDepRel = JsonUtil.jsonToClass(ppmDepkpiDepRelDto, PpmDepkpiDepRel.class);
        Long id = getNextSequence(PpmDepkpiDepRel.class);

        // 如果需要请set 主键id
        ppmDepkpiDepRel.setId(Long.valueOf(id.intValue()));
        myBatisDao.executionPojo(PPMDEPKPIDEPREL, ppmDepkpiDepRel, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmDepkpiDepRelDto queryDetail(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) {
        PpmDepkpiDepRel ppmDepkpiDepRel = JsonUtil.jsonToClass(ppmDepkpiDepRelDto, PpmDepkpiDepRel.class);
        PpmDepkpiDepRel data = myBatisDao.selectClass(PPMDEPKPIDEPREL, ppmDepkpiDepRel,
                "queryDetail");
        return JsonUtil.jsonToClass(data, PpmDepkpiDepRelDto
                .class);
    }

    @Override
    public int updatePpmDepkpiDepRelByDto(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) {
        return this.updatePpmDepkpiDepRel(JsonUtil.jsonToClass(ppmDepkpiDepRelDto, PpmDepkpiDepRel.class));
    }

    /**
     * 更新对象
     *
     * @param ppmDepkpiDepRel 入参
     * @return int
     */
    private int updatePpmDepkpiDepRel(PpmDepkpiDepRel ppmDepkpiDepRel) {
        return myBatisDao.executionPojo(PPMDEPKPIDEPREL, ppmDepkpiDepRel, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }



    /**
     * 保存数据
     *
     * @param ppmDepkpiDepRel 对象
     * @param userid          对象
     */
    private void insertPpmDepKpiDepRel(PpmDepkpiDepRel ppmDepkpiDepRel, String userid) {
        ppmDepkpiDepRel.setLastmodtime(new Date());
        ppmDepkpiDepRel.setLastmoduserid(userid);
        CommEmployeeDto employee = UsersUtil.getTwoWorkManager("131");
        String check = employee.getBadge();
        if (ppmDepkpiDepRel.getSign() == 1) {
            ppmDepkpiDepRel.setMarker(ppmDepkpiDepRel.getDirector());
            //默认不给初评人 ppmDepkpiDepRel.setFirstcheck(ppmDepkpiDepRel.getDepcharge())
            ppmDepkpiDepRel.setFirstcheck(null);
            ppmDepkpiDepRel.setSecondcheck(ppmDepkpiDepRel.getCentercharge());
        } else if (ppmDepkpiDepRel.getSign() == 2) {
            ppmDepkpiDepRel.setMarker(ppmDepkpiDepRel.getCentercharge());
            ppmDepkpiDepRel.setFirstcheck(ppmDepkpiDepRel.getCentercharge());
            ppmDepkpiDepRel.setSecondcheck(check);
        } else {
            ppmDepkpiDepRel.setMarker(ppmDepkpiDepRel.getDirector());
            ppmDepkpiDepRel.setSecondcheck(ppmDepkpiDepRel.getDirector());
        }
        Long id = getNextSequence(PpmDepkpiDepRel.class);
        // 如果需要请set 主键id
        ppmDepkpiDepRel.setId(id);
        myBatisDao.executionPojo(PPMDEPKPIDEPREL, ppmDepkpiDepRel, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    @Transactional("defaultTx")
    public int insertPpmDepKpiDepRels(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, String userid) {
        PpmDepkpiDepRel ppmDepkpiDepRel = JsonUtil.jsonToClass(ppmDepkpiDepRelDto, PpmDepkpiDepRel.class);
        //先删除表中数据
        myBatisDao.executionPojo(PPMDEPKPIDEPREL, ppmDepkpiDepRel, IMyBatisDao.SQL.DELETE, "deleteAll");
        //在进行插入操作
        List<PpmDepkpiDepRel> dtos = myBatisDao.selectListT(PPMDEPKPIDEPREL, ppmDepkpiDepRel, "getDepCenList");
        for (int i = 0; i < dtos.size(); i++) {
            PpmDepkpiDepRel item = dtos.get(i);
            insertPpmDepKpiDepRel(item, userid);
        }
        return 0;
    }

    /**
     * 更新数据
     *
     * @param ppmDepkpiDepRel 对象
     */
    private void updatePpmKpiDepRoleWithUserid(PpmDepkpiDepRel ppmDepkpiDepRel, String userid) {
        ppmDepkpiDepRel.setLastmodtime(new Date());
        ppmDepkpiDepRel.setLastmoduserid(userid);
        this.updatePpmDepkpiDepRel(ppmDepkpiDepRel);
    }

    @Override
    public List<PpmDepkpiDepRelInfoDto> getPpmDepKpiDepRelList(PpmDepkpiDepRelInfoDto ppmDepkpiDepRelInfoDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepRelInfoDto, PpmDepkpiDepRelInfoDto.class);
        if (StringUtil.isNotBlank(ppmDepkpiDepRelInfoDto.getDepid())) {
            StringBuilder depids = new StringBuilder();
            String[] depArr = ppmDepkpiDepRelInfoDto.getDepid().split(",");
            for (int i = 0; i < depArr.length; i++) {
                if (i == 0) {
                    depids.append("'").append(depArr[i]).append("'");
                } else {
                    depids.append(",'").append(depArr[i]).append("'");
                }
            }
            map.remove("depid");
            map.put("depids", depids.toString());
        }

        if (StringUtil.isNotBlank(ppmDepkpiDepRelInfoDto.getCenterid())) {
            StringBuilder centerids = new StringBuilder();
            String[] centeridArr = ppmDepkpiDepRelInfoDto.getCenterid().split(",");
            for (int i = 0; i < centeridArr.length; i++) {
                if (i == 0) {
                    centerids.append("'").append(centeridArr[i]).append("'");
                } else {
                    centerids.append(",'").append(centeridArr[i]).append("'");
                }
            }
            map.remove("centerid");
            map.put("centerids", centerids.toString());
        }
        List<PpmDepkpiDepRelInfo> datas = myBatisDao.selectListT(PPMDEPKPIDEPREL, map, "getDepKpiDepRelList");
        return JsonUtil.jsonToList(datas, PpmDepkpiDepRelInfoDto.class);
    }

    @Override
    public void queryPagerList(PpmDepkpiDepRelInfoDto ppmDepkpiDepRelInfoDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiDepRelInfoDto, PpmDepkpiDepRelInfoDto.class);
        if (StringUtil.isNotBlank(ppmDepkpiDepRelInfoDto.getDepid())) {
            StringBuilder depids = new StringBuilder();
            String[] depArr = ppmDepkpiDepRelInfoDto.getDepid().split(",");
            for (int i = 0; i < depArr.length; i++) {
                if (i == 0) {
                    depids.append("'").append(depArr[i]).append("'");
                } else {
                    depids.append(",'").append(depArr[i]).append("'");
                }
            }
            map.remove("depid");
            map.put("depids", depids.toString());
        }

        if (StringUtil.isNotBlank(ppmDepkpiDepRelInfoDto.getCenterid())) {
            StringBuilder centerids = new StringBuilder();
            String[] centeridArr = ppmDepkpiDepRelInfoDto.getCenterid().split(",");
            for (int i = 0; i < centeridArr.length; i++) {
                if (i == 0) {
                    centerids.append("'").append(centeridArr[i]).append("'");
                } else {
                    centerids.append(",'").append(centeridArr[i]).append("'");
                }
            }
            map.remove("centerid");
            map.put("centerids", centerids.toString());
        }
        myBatisDao.selectPager(PPMDEPKPIDEPREL, map, pager, "getDepKpiDepRelList");
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmDepKpiDepRelList(String ids) {
        if (!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmDepkpiDepRel ppmDepkpiDepRel = new PpmDepkpiDepRel();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmDepkpiDepRel.setId(Long.parseLong(idList[i]));
            myBatisDao.executionPojo(PPMDEPKPIDEPREL, ppmDepkpiDepRel, IMyBatisDao.SQL.DELETE, "deleteById");
        }
        return 1;
    }

    /**
     * 根据id获得数据
     *
     * @param id
     * @return PpmDepkpiDepRel
     */
    private PpmDepkpiDepRel getPpmDepkpiDepRelById(Long id) {
        PpmDepkpiDepRel ppmDepkpiDepRel = new PpmDepkpiDepRel();
        ppmDepkpiDepRel.setId(id);
        return myBatisDao.selectClass(PPMDEPKPIDEPREL, ppmDepkpiDepRel, "queryDetail");
    }


    @Override
    @Transactional("defaultTx")
    public boolean importExcel(MultipartFile file, String userid) {
        // 业务字典
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KHBMJSBZ");
        // excel导入数据
        List<PpmDepkpiDepRelInfoDto> list = ExcelUtil.importExcel(file, PpmDepkpiDepRelInfoDto.class);
        for (int i = 0; i < list.size(); i++) {
            PpmDepkpiDepRelInfoDto ii = list.get(i);
            Long id = ii.getId();
            PpmDepkpiDepRel role = getPpmDepkpiDepRelById(id);
            //查询角色id
            Optional<EosDictEntry> o =
                    eos.stream().filter(iii -> ii.getSignname().equals(iii.getDictname())).findFirst();
            if (!o.isPresent()) {
                continue;
            }
            String sign = o.get().getDictid();
            //查询部门id
            String depid = ppmKpiRoleService.retDepid(ii.getDepname());
            //查询中心编号
            String centerid = ppmKpiRoleService.retCenterid(ii.getCentername());
            //部门主持工作经理
            String director = ii.getDirector() == null ? "" : ii.getDirector();
            //部门分管总经理
            String depcharge = ii.getDepcharge() == null ? "" : ii.getDepcharge();
            //中心分管总经理
            String centercharge = ii.getCentercharge() == null ? "" : ii.getCentercharge();
            //制订人
            String marker = ii.getMarker() == null ? "" : ii.getMarker();
            //初评人
            String firstcheck = ii.getFirstcheck() == null ? "" : ii.getFirstcheck();
            //终评人
            String secondcheck = ii.getSecondcheck() == null ? "" : ii.getSecondcheck();

            if (StringUtil.isNotBlank(sign)) {
                role.setSign(Long.parseLong(sign));
            }
            if (StringUtil.isNotBlank(depid)) {
                role.setDepid(depid);
            }
            if (StringUtil.isNotBlank(centerid)) {
                role.setCenterid(centerid);
            }
            if (StringUtil.isNotBlank(director)) {
                role.setDirector(director);
            }
            if (StringUtil.isNotBlank(depcharge)) {
                role.setDepcharge(depcharge);
            }
            if (StringUtil.isNotBlank(centercharge)) {
                role.setCentercharge(centercharge);
            }
            if (StringUtil.isNotBlank(marker)) {
                role.setMarker(marker);
            }
            if (StringUtil.isNotBlank(firstcheck)) {
                role.setFirstcheck(firstcheck);
            } else {
                role.setFirstcheck("null");
            }
            if (StringUtil.isNotBlank(secondcheck)) {
                role.setSecondcheck(secondcheck);
            }
            this.updatePpmKpiDepRoleWithUserid(role, userid);
        }
        return true;
    }


    @Override
    public int updatePpmDepKpiDepRelDtos(PpmDepkpiDepRelDto ppmDepkpiDepRelDtos, String userid) {
        String ids = ppmDepkpiDepRelDtos.getIds();
        if (!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        for (int i = idList.length - 1; i >= 0; i--) {
            String id = idList[i];
            PpmDepkpiDepRel dto = this.getPpmDepkpiDepRelById(Long.parseLong(id));
            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getDepid())) {
                dto.setDepid(ppmDepkpiDepRelDtos.getDepid());
            }
            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getCenterid())) {
                dto.setCenterid(ppmDepkpiDepRelDtos.getCenterid());
            }
            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getDirector())) {
                dto.setDirector(ppmDepkpiDepRelDtos.getDirector());
            }

            if (ppmDepkpiDepRelDtos.getDepcharge() != null) {
                dto.setDepcharge(ppmDepkpiDepRelDtos.getDepcharge());
            }

            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getCentercharge())) {
                dto.setCentercharge(ppmDepkpiDepRelDtos.getCentercharge());
            }

            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getMarker())) {
                dto.setMarker(ppmDepkpiDepRelDtos.getMarker());
            }

            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getFirstcheck())) {
                dto.setFirstcheck(ppmDepkpiDepRelDtos.getFirstcheck());
            } else {
                dto.setFirstcheck("null");
            }

            if (StringUtil.isNotBlank(ppmDepkpiDepRelDtos.getSecondcheck())) {
                dto.setSecondcheck(ppmDepkpiDepRelDtos.getSecondcheck());
            }
            this.updatePpmDepkpiDepRel(dto);
        }
        return 1;
    }


    @Override
    public int updatePpmDepKpiDepRelDto(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, String userid) {
        ppmDepkpiDepRelDto.setLastmodtime(new Date());
        ppmDepkpiDepRelDto.setLastmoduserid(userid);
        return this.updatePpmDepkpiDepRelByDto(ppmDepkpiDepRelDto);
    }

    @Override
    public EosDepKpiGrade getEosDepKpiGrade() {
        EosDepKpiGrade grade = new EosDepKpiGrade();
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList(DEP_KPI_GRADE);
        if (StringUtil.isNotEmpty(eos)) {
            for (int i = 0; i < eos.size(); i++) {
                if ("DEP".equals(eos.get(i).getDictid())) {
                    grade.setDep(Long.parseLong(eos.get(i).getDictname()));
                } else if ("CEN".equals(eos.get(i).getDictid())) {
                    grade.setCen(Long.parseLong(eos.get(i).getDictname()));
                } else {
                    grade.setTec(Long.parseLong(eos.get(i).getDictname()));
                }
            }
        }
        return grade;
    }

    @Override
    public int saveEosDepKpiGrade(EosDepKpiGrade grade) {
        if (StringUtil.isNotBlank(String.valueOf(grade.getDep()))) {
            EosDictEntry entry = new EosDictEntry();
            entry.setDicttypeid(DEP_KPI_GRADE);
            entry.setDictid("DEP");
            entry.setDictname(String.valueOf(grade.getDep()));
            eosDictEntryService.updateEosDictEntry(entry);
        }
        if (StringUtil.isNotBlank(String.valueOf(grade.getCen()))) {
            EosDictEntry entry = new EosDictEntry();
            entry.setDicttypeid(DEP_KPI_GRADE);
            entry.setDictid("CEN");
            entry.setDictname(String.valueOf(grade.getCen()));
            eosDictEntryService.updateEosDictEntry(entry);
        }
        if (StringUtil.isNotBlank(String.valueOf(grade.getTec()))) {
            EosDictEntry entry = new EosDictEntry();
            entry.setDicttypeid(DEP_KPI_GRADE);
            entry.setDictid("TEC");
            entry.setDictname(String.valueOf(grade.getTec()));
            eosDictEntryService.updateEosDictEntry(entry);
        }
        return 1;
    }

    @Override
    public void getPpmDepKpiDepRelPage(PpmDepkpiDepModelDto ppmDepKpidepModelDto, Pager pager) {
        ppmDepKpidepModelDto.setIsdel(0L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepKpidepModelDto, PpmDepkpiDepModelDto.class);
        myBatisDao.selectPager(PPMDEPKPIDEPREL, map, pager, "queryModouAndRelList");
    }

    @Override
    public PpmDepkpiDepRel queryDepRelByDepIdOrCenterid(PpmDepkpiDepRelDto ppm) {
        //通过部门id查出初评人和终评人
        PpmDepkpiDepRel ppmDepkpiDepRel = JsonUtil.jsonToClass(ppm, PpmDepkpiDepRel.class);
        //pp.setid
        ppmDepkpiDepRel.setIsdel(0L);
        return myBatisDao.selectClass(PPMDEPKPIDEPREL, ppmDepkpiDepRel, "selectRelByDepidOrCenterid");
    }

}
