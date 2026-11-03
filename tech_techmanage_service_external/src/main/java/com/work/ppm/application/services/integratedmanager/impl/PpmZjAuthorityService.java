package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjManagerDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmZjAuthority;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityApplyService;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityService;
import com.work.ppm.application.services.integratedmanager.IPpmZjManagerService;
import com.work.ppm.application.services.integratedmanager.IPpmZjPersonService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.artemis.request.AuthAddReqt;
import com.work.ppm.commons.artemis.request.AuthDelReqt;
import com.work.ppm.commons.artemis.service.ArtemisService;
import com.work.ppm.commons.constant.SystemConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 闸机权限管理 接口实现类
 *
 */
@Service("ppmZjAuthorityService")
public class PpmZjAuthorityService extends BaseDaoSupper implements IPpmZjAuthorityService {
    /**
     * 实体类路径
     */
    private static String PPMZJAUTHORITY = "com.work.ppm.application.model.integratedmanager.PpmZjAuthority";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 系统环境接口
     */
    @Autowired
    private Environment environment;
    @Autowired
    private ArtemisService artemisService;
    @Autowired
    private ICommEmployeeService commEmployeeService;
    @Autowired
    private IPpmZjPersonService ppmZjPersonService;
    @Autowired
    private IPpmZjManagerService ppmZjManagerService;
    @Lazy
    @Autowired
    private IPpmZjAuthorityApplyService ppmZjAuthorityApplyService;

    @Override
    public void queryPager(PpmZjAuthorityDto ppmZjAuthorityDto,
                           Pager pager, CommOperatorDoLoginDto user) {

        List<ImRoleDoLoginDto> roles = user.getRoles();
        Map<String, Object> map = JsonUtil.objectTOMap(ppmZjAuthorityDto, PpmZjAuthorityDto.class);
        boolean zjManager = roles.stream().anyMatch(r -> "ROLE_ZJ_MANAGER".equals(r.getRoleId()));
        if (!zjManager) {
            map.put("depid", user.getDeptId());
        }
        myBatisDao.selectPager(PPMZJAUTHORITY, map, pager, "queryPagerList");
    }

    @Override
    public List<PpmZjAuthorityDto> queryList(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        List<PpmZjAuthority> data = myBatisDao.selectListT(PPMZJAUTHORITY, ppmZjAuthority, "getList");
        return JsonUtil.jsonToList(data, PpmZjAuthorityDto.class);
    }

    @Override
    public List<PpmZjAuthorityDto> queryListOther(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        List<PpmZjAuthority> data = myBatisDao.selectListT(PPMZJAUTHORITY, ppmZjAuthority, "getListNew");
        return JsonUtil.jsonToList(data, PpmZjAuthorityDto.class);
    }

    @Override
    public int addPpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        // 如果需要请set 主键id
        ppmZjAuthority.setId(getNextSequence(PpmZjAuthority.class));
        myBatisDao.executionPojo(PPMZJAUTHORITY, ppmZjAuthority, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmZjAuthorityDto queryDetail(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        PpmZjAuthority data = myBatisDao.selectClass(PPMZJAUTHORITY, ppmZjAuthority, "queryDetail");
        PpmZjAuthorityDto item = JsonUtil.jsonToClass(data, PpmZjAuthorityDto.class);
        return item;
    }

    @Override
    public PpmZjAuthorityDto queryByZjAuth(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        PpmZjAuthority data = myBatisDao.selectClass(PPMZJAUTHORITY, ppmZjAuthority, "queryByZjAuth");
        PpmZjAuthorityDto item = JsonUtil.jsonToClass(data, PpmZjAuthorityDto.class);
        return item;
    }

    @Override
    public int updatePpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        return myBatisDao.executionPojo(PPMZJAUTHORITY, ppmZjAuthority, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthority ppmZjAuthority = JsonUtil.jsonToClass(ppmZjAuthorityDto, PpmZjAuthority.class);
        return myBatisDao.executionPojo(PPMZJAUTHORITY, ppmZjAuthority, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }

    @Override
    public List<PpmZjAuthorityExcelDto> exportPpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto, CommOperatorDoLoginDto user) {
        List<ImRoleDoLoginDto> roles = user.getRoles();
        boolean zjManager = roles.stream().anyMatch(r -> "ROLE_ZJ_MANAGER".equals(r.getRoleId()));
        if (!zjManager) {
            ppmZjAuthorityDto.setDepid(Long.parseLong(user.getDeptId()));
        }
        List<PpmZjAuthorityDto> data = myBatisDao.selectListT(PPMZJAUTHORITY, ppmZjAuthorityDto, "getList");
        return JsonUtil.jsonToList(data, PpmZjAuthorityExcelDto.class);
    }

    @Override
    public boolean checkRepeatAuth(String certificateNumber, String ppmZjId) {
        PpmZjAuthorityDto ppmZjAuthorityDto = new PpmZjAuthorityDto();
        ppmZjAuthorityDto.setPpmZjId(ppmZjId);
        ppmZjAuthorityDto.setCertificateNumber(certificateNumber);
        List<PpmZjAuthorityDto> data = myBatisDao.selectListT(PPMZJAUTHORITY, ppmZjAuthorityDto, "checkRepeatAuth");
        if (!CollectionUtils.isEmpty(data)) {
            return true;
        }
        return false;
    }

    @Override
    public void cleanExpireData() {
        PpmZjAuthority ppmZjAuthority = PpmZjAuthority.builder().endDate(new Date()).build();
        List<PpmZjAuthority> result = myBatisDao.selectListT(PPMZJAUTHORITY, ppmZjAuthority, "getExpire");
        List<PpmZjAuthorityDto> authorities = JsonUtil.jsonToList(result, PpmZjAuthorityDto.class);
        if (!CollectionUtils.isEmpty(authorities)) {
            for (PpmZjAuthorityDto entry : authorities) {
                entry.setFlag(-1);
                this.updatePpmZjAuthority(entry);
            }
            //this.delAuthToZjServer(authorities);
        }
    }

    @Override
    public void cleanExpireData(List<String> certificateNumbers) {
        if (CollectionUtils.isEmpty(certificateNumbers)) {
            return;
        }
        for (String certificateNumber : certificateNumbers) {
            List<PpmZjAuthorityDto> entries = this.queryList(PpmZjAuthorityDto.builder()
                    .certificateNumber(certificateNumber).build());
            if (!CollectionUtils.isEmpty(entries)) {
                entries.stream().forEach(entry -> {
                    entry.setFlag(-1);
                    entry.setEmpstatus("离岗");
                    this.updatePpmZjAuthority(entry);
                });
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPpmZjAuthorityInfo(PpmZjAuthorityApplyDto applyDto) {
        String profile = environment.getProperty(SystemConstant.SPRING_PROFILES_ACTIVE);
        List<PpmZjAuthorityDto> authorities = applyDto.getAuthorities();
        //清理历史数据
        PpmZjAuthorityDto authorityDto = new PpmZjAuthorityDto();
        authorityDto.setInstanceid(applyDto.getInstanceid());
        //this.deletePpmZjAuthority(authorityDto);  //后改  多权限申请后无需根据流程编号删除对应记录，下面有根据身份证号及ppmZjId对权限进行重复性检验
        if (CollectionUtils.isEmpty(authorities)) {
            throw new BizException(ApiCode.FAIL, "请指定需闸机权限的人员！");
        }
        List<PpmZjAuthorityDto> repeatdData = Lists.newArrayList();
        List<PpmZjAuthorityDto> personData = Lists.newArrayList();
        List<PpmZjAuthorityDto> data = Lists.newArrayList();
        //写入最新数据
        for (PpmZjAuthorityDto ppmZjAuthorityDto : authorities) {
            // 仅生产环境检查闸机系统中人员是否存在
            if (SystemConstant.SYSTEM_ENV_PROD.equals(profile)) {
                if (!ppmZjPersonService.checkExitsPerson(ppmZjAuthorityDto.getCertificateNumber())) {
                    personData.add(ppmZjAuthorityDto);
                    continue;
                }
            }
            // 检查有没有重复的权限
            if (checkRepeatAuth(ppmZjAuthorityDto.getCertificateNumber(), applyDto.getPpmZjId())) {
                repeatdData.add(ppmZjAuthorityDto);
                continue;
            }
            // 如果需要请set 主键id
            ppmZjAuthorityDto.setFlag(0);
            ppmZjAuthorityDto.setId(super.getNextSequence(PpmZjAuthority.class));
            ppmZjAuthorityDto.setInstanceid(applyDto.getInstanceid());
            ppmZjAuthorityDto.setPpmZjId(applyDto.getPpmZjId());
            ppmZjAuthorityDto.setDepid(applyDto.getDepid());
            ppmZjAuthorityDto.setBeginDate(applyDto.getBeginDate());
            ppmZjAuthorityDto.setEndDate(applyDto.getEndDate());
            ppmZjAuthorityDto.setApplyType(applyDto.getApplyType());
            // 处理状态
            if (!StringUtil.isNotBlank(ppmZjAuthorityDto.getEmpstatus())) {
                ppmZjAuthorityDto.setEmpstatus("在岗");
            }
            data.add(ppmZjAuthorityDto);
        }
        // 收集闸机人员是否存在提示
        if (!CollectionUtils.isEmpty(personData)) {
            String errorMsg = "请先在闸机系统中增加以下人员：";
            for (PpmZjAuthorityDto ppmZjAuthorityDto : personData) {
                errorMsg += "【" + ppmZjAuthorityDto.getEmpname() + ":" + ppmZjAuthorityDto.getCertificateNumber() + "】、";
            }
            throw new BizException(ApiCode.FAIL, errorMsg.substring(0, errorMsg.lastIndexOf("、")));
        }
        // 收集重复的权限并提示
        if (!CollectionUtils.isEmpty(repeatdData)) {
            String errorMsg = "闸机人员权限重复：";
            for (PpmZjAuthorityDto ppmZjAuthorityDto : repeatdData) {
                errorMsg += "【" + ppmZjAuthorityDto.getEmpname() + ":" + ppmZjAuthorityDto.getCertificateNumber() + "】、";
            }
            throw new BizException(ApiCode.FAIL, errorMsg.substring(0, errorMsg.lastIndexOf("、")));
        }
        // 如果没有重复的权限数据则入库
        for (PpmZjAuthorityDto ppmZjAuthorityDto : data) {
            this.addPpmZjAuthority(ppmZjAuthorityDto);
        }
    }

    @Override
    public void addAuthToZjServer(List<PpmZjAuthorityDto> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            return;
        }
        List<AuthAddReqt.ResourceInfo> resourceInfos = Lists.newArrayList();
        List<AuthAddReqt.PersonData> personDatas = Lists.newArrayList();
        // 查询闸机信息
        PpmZjManagerDto zjManagerDto = ppmZjManagerService.queryDetail(PpmZjManagerDto.builder()
                .indexCode(authorities.get(0).getPpmZjId()).build());
        // 添加设备参数
        resourceInfos.add(AuthAddReqt.ResourceInfo.builder()
                .resourceIndexCode(zjManagerDto.getIndexCode())
                .resourceType(zjManagerDto.getResourceType()) //门禁控制器
                .channelNos(Lists.newArrayList(zjManagerDto.getChannelNo()))
                .build());
        // 取出闸机权限人员身份证号
        List<String> certificateNos = authorities.stream()
                .map(PpmZjAuthorityDto::getCertificateNumber)
                .collect(Collectors.toList());
        //构建闸机人员参数
        List<String> personIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(certificateNos)) {
            for (String personId : certificateNos) {
                personIds.add(ppmZjPersonService.getPersonId(personId));
            }
        }
        personDatas.add(AuthAddReqt.PersonData.builder()
                // 固定授权类型
                .personDataType("person")
                .indexCodes(personIds).build());
        //构建添加权限参数
        AuthAddReqt authAddReqt = AuthAddReqt.builder()
                .personDatas(personDatas)
                .resourceInfos(resourceInfos).build();
        artemisService.addAuth(authAddReqt);
    }

    @Override
    public void delAuthToZjServer(List<PpmZjAuthorityDto> authorities) {
        if (!CollectionUtils.isEmpty(authorities)) {
            List<AuthDelReqt.ResourceInfo> resourceInfos = Lists.newArrayList();
            List<AuthDelReqt.PersonData> personDatas = Lists.newArrayList();
            // 查询闸机信息
            PpmZjManagerDto zjManagerDto = ppmZjManagerService.queryDetail(PpmZjManagerDto.builder()
                    .indexCode(authorities.get(0).getPpmZjId()).build());
            // 添加设备参数
            resourceInfos.add(AuthDelReqt.ResourceInfo.builder()
                    .resourceIndexCode(zjManagerDto.getIndexCode())
                    .resourceType(zjManagerDto.getResourceType()) //门禁控制器
                    .channelNos(Lists.newArrayList(zjManagerDto.getChannelNo()))
                    .build());
            //构建闸机人员参数
            List<String> personIds = authorities.stream()
                    .map(PpmZjAuthorityDto::getCertificateNumber)
                    .collect(Collectors.toList());
            personDatas.add(AuthDelReqt.PersonData.builder()
                    // 固定授权类型
                    .personDataType("person")
                    .indexCodes(personIds).build());
            //构建添加权限参数
            AuthDelReqt authAddReqt = AuthDelReqt.builder()
                    .personDatas(personDatas)
                    .resourceInfos(resourceInfos).build();
            artemisService.delAuth(authAddReqt);
        }
    }
}
