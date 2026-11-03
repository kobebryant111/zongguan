package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmDaDeptManagerDto;
import com.work.ppm.application.dto.integratedmanager.PpmOnboardDataauthDto;
import com.work.ppm.application.dto.integratedmanager.PpmOnboardDistributionDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmOnboardDistribution;
import com.work.ppm.application.model.integratedmanager.ext.PpmOnboardDistributionInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmOnboardDataauthService;
import com.work.ppm.application.services.integratedmanager.IPpmOnboardDistributionService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
  * 入职部门分配
  */
@Service("ppmOnboardDistributionService")
public class PpmOnboardDistributionService extends BaseDaoSupper implements IPpmOnboardDistributionService {
    /**
      * 实体类路径
      */
    private static String PPMONBOARDDISTRIBUTION = "com.work.ppm.application.model.integratedmanager.PpmOnboardDistribution";
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
     * 入职部门分配数据权限接口
     */
    @Autowired
    private IPpmOnboardDataauthService ppmOnboardDataauthService;
    /**
     * 门中心分管经理配置接口
     */
    @Autowired
    private IPpmDaDeptManagerService ppmDaDeptManagerService;

    @Override
    public void queryPager(PpmOnboardDistributionDto ppmOnboardDistributionDto, Pager pager,
                           CommOperatorDoLoginDto user) {
        int roleType = 0;
        // 是否总经理
        List<PpmDaDeptManagerDto> daDeptManagerDtos = ppmDaDeptManagerService.queryGeneral(user.getUserid());
        if(null != daDeptManagerDtos && ! daDeptManagerDtos.isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");

            List<PpmDaDeptManagerDto> disList = daDeptManagerDtos.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o-> o.getCenterid()))),
                            ArrayList::new
                    ));
            disList.stream().forEach(ii->{
                joiner.add(ii.getCenterid());
            });
            ppmOnboardDistributionDto.setCenters(joiner.toString());
        } else {
            // 权限
            roleType = this.getAuth(user);
            if(roleType == 0) {
                throw new BizException(ApiCode.FAIL,"查看未经授权");
            }
            // 普通管理员
            if(roleType != 1) {
                List<PpmOnboardDataauthDto> authList = ppmOnboardDataauthService.checkedAuth(user.getUserid());
                StringJoiner joiner = new StringJoiner(",");
                authList.stream().forEach(ii -> {
                    joiner.add(ii.getCenterId() +"");
                });
                ppmOnboardDistributionDto.setCenters(joiner.toString());
            }
        }
        ppmOnboardDistributionDto.setIsdel(0);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOnboardDistributionDto,PpmOnboardDistributionDto.class);
        myBatisDao.selectPager(PPMONBOARDDISTRIBUTION, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOnboardDistribution(PpmOnboardDistributionDto ppmOnboardDistributionDto) {
        PpmOnboardDistribution ppmOnboardDistribution = JsonUtil.jsonToClass(ppmOnboardDistributionDto, PpmOnboardDistribution.class);
        Long id = getNextSequence(PpmOnboardDistribution.class);
        // 如果需要请set 主键id
        ppmOnboardDistribution.setId(id);
        myBatisDao.executionPojo(PPMONBOARDDISTRIBUTION, ppmOnboardDistribution, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOnboardDistributionDto queryDetail(PpmOnboardDistributionDto ppmOnboardDistributionDto) {
        PpmOnboardDistribution ppmOnboardDistribution = JsonUtil.jsonToClass(ppmOnboardDistributionDto, PpmOnboardDistribution.class);
        PpmOnboardDistribution data = myBatisDao.selectClass(PPMONBOARDDISTRIBUTION, ppmOnboardDistribution,
        "queryDetail");
        PpmOnboardDistributionDto item = JsonUtil.jsonToClass(data, PpmOnboardDistributionDto
        .class);
        return item;
    }

    @Override
    public PpmOnboardDistributionDto queryDetail(String resumeId) {
        PpmOnboardDistributionDto item = new PpmOnboardDistributionDto();
        item.setResumeId(resumeId);
        return this.queryDetail(item);
    }

    @Override
    public int updatePpmOnboardDistribution(PpmOnboardDistributionDto ppmOnboardDistributionDto) {
        PpmOnboardDistributionInfo ppmOnboardDistribution = JsonUtil.jsonToClass(ppmOnboardDistributionDto,
                PpmOnboardDistributionInfo.class);
        return myBatisDao.executionPojo(PPMONBOARDDISTRIBUTION, ppmOnboardDistribution, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Transactional("defaultTx")
    @Override
    public boolean importExcel(String batchNo, MultipartFile file) {

        // excel导入数据
        List<PpmOnboardDistributionDto> list = ExcelUtil.importExcel(file, PpmOnboardDistributionDto.class);
        if(null == list || list.isEmpty()) {
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"文件导入失败");
        }
        if(!StringUtil.isNotBlank(list.get(0).getResumeId())
            && !StringUtil.isNotBlank(list.get(0).getName())) {
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"文件导入失败");
        }
        // 业务字典
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("BUSI_OUTSOURCE_GENFER");
        // 标记 是否添加
        AtomicBoolean falg = new AtomicBoolean(false);
        list.stream().forEach(ii -> {
            PpmOnboardDistributionDto tempDto = this.queryDetail(ii.getResumeId());
            ii.setBatch(batchNo);
            ii.setIsdel(0);
            // 更新
            if(null != tempDto) {
                this.updatePpmOnboardDistribution(ii);
            } else {    // 添加
                long id = super.getNextSequence(PpmOnboardDistribution.class);
                ii.setId(id);
                ii.setBornDate(DateUtil.strToDate(ii.getBirthday(), "yyyy-MM-dd"));
                String sexId =
                        eos.stream().filter(i-> ii.getSexName().equals(i.getDictname())).findFirst().get().getDictid();
                ii.setSex(sexId);
                falg.set(true);
            }
        });

        if(falg.get()) {
            myBatisDao.executionPojo(PPMONBOARDDISTRIBUTION, list, IMyBatisDao.SQL.INSTER, "batchAdd");
        }
        return true;
    }

    @Override
    public int updateInfoForId(String resumeIds, int id) {
        if(!StringUtil.isNotBlank(resumeIds)) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "请选择至少一条记录");
        }
        String[] rids = resumeIds.split(",");
        StringJoiner joiner = new StringJoiner("','","'","'");
        for (int i = rids.length - 1; i >= 0; i--) {
            joiner.add(rids[i]);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("mid", id);
        map.put("resumeId", joiner.toString());
        int i = myBatisDao.executionMap(PPMONBOARDDISTRIBUTION, map, IMyBatisDao.SQL.UPDATE,"updateInfoForId");
        return i;
    }

    @Override
    public List<PpmOnboardDistributionDto> queryList(PpmOnboardDistributionDto ppmOnboardDistributionDto) {
        if(StringUtil.isNotBlank(ppmOnboardDistributionDto.getResumeIds())) {
            StringJoiner joiner = new StringJoiner("','","'","'");
            String[] ids = ppmOnboardDistributionDto.getResumeIds().split(",");
            for (int i = ids.length - 1; i >= 0; i--) {
                joiner.add(ids[i]);
            }
            ppmOnboardDistributionDto.setResumeIds(joiner.toString());
        }
        ppmOnboardDistributionDto.setIsdel(0);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOnboardDistributionDto, PpmOnboardDistributionDto.class);
        List<PpmOnboardDistributionInfo> list = myBatisDao.selectListT(PPMONBOARDDISTRIBUTION, map, "queryPagerList");
        List<PpmOnboardDistributionDto> dtos = JsonUtil.jsonToList(list, PpmOnboardDistributionDto.class);
        return dtos;
    }

    @Override
    public int getAuth(CommOperatorDoLoginDto user) {
        // 权限类型
        int authType = 0;
        List<ImRoleDoLoginDto> list = user.getRoles();
        // 管理员
        Long row = list.stream().filter(i -> "RZBMFPM0001".equals(i.getRoleId())).count();
        if(null != row && row > 0) {
            // 管理员
            authType = 1;
        }
        // 普通管理员
        Long ptRole = list.stream().filter(i -> "RZBMFPM0002" .equals(i.getRoleId())).count();
        if(null != ptRole && ptRole>0) {
            authType = 2;
        }

        return authType;
    }

    @Override
    public int delete(String resumeId) {
        PpmOnboardDistributionDto ppmOnboardDistributionDto = new PpmOnboardDistributionDto();
        String[] resumeIds = resumeId.split(",");
        StringJoiner joiner = new StringJoiner("','","'","'");
        for (int i = resumeIds.length - 1; i >= 0; i--) {
            joiner.add(resumeIds[i]);
        }
        ppmOnboardDistributionDto.setResumeIds(joiner.toString());
        ppmOnboardDistributionDto.setIsdel(1);
        int rid = this.updatePpmOnboardDistribution(ppmOnboardDistributionDto);
        return rid;
    }

    @Override
    public void queryPagerNotAuth(PpmOnboardDistributionDto ppmOnboardDistributionDto, Pager pager) {
        ppmOnboardDistributionDto.setIsdel(0);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOnboardDistributionDto,PpmOnboardDistributionDto.class);
        myBatisDao.selectPager(PPMONBOARDDISTRIBUTION, map,pager,"queryPagerList");
    }

}
