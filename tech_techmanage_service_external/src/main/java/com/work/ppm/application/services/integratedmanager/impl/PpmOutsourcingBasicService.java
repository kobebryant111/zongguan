package com.work.ppm.application.services.integratedmanager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.integratedmanager.CommDepAndOutsTreeDto;
import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicCardExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;
import com.work.ppm.application.model.integratedmanager.OutFlowData;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.model.usermanager.PpmDaTeamUser;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingBasicJobService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqProfesstionService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.impl.PpmProjectInfoService;
import com.work.ppm.application.services.systemmanager.impl.PpmSystemInfoService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamUserService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 外包人员基础接口
 *
 */
@Service("ppmOutsourcingBasicService")
@Slf4j
public class PpmOutsourcingBasicService extends BaseDaoSupper implements IPpmOutsourcingBasicService, IPpmOutsourcingBasicJobService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGBASIC = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private PpmProjectInfoService ppmProjectInfoService;

    @Autowired
    private PpmSystemInfoService ppmSystemInfoService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 组织树人员接口
     */
    @Autowired
    private IPpmDaTeamUserService PpmDaTeamUserService;

    /**
     * 文员接口
     */
    @Autowired
    private IPpmKqProfesstionService ppmKqProfesstionService;

    /**
     * 外包面试人员信息
     */
    @Autowired
    private IPpmIntervieweeInfoService ppmIntervieweeInfoService;

    /**
     * 团队接口
     */
    @Autowired
    private IPpmDaTeamGroupService ppmDaTeamGroupService;
    /**
     * 行员兼职
     */
    @Autowired
    private ICommParttimejobService commParttimejobService;

    /**
     * 文件
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;

    /**
     * 字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Value("${file.upload-path}")
    private String fileUploadPath;

    @Override
    public void queryPager(PpmOutsourcingBasic ppmOutsourcingBasic, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmOutsourcingBasic, PpmOutsourcingBasic.class);
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) {
        Long id = getNextSequence(PpmOutsourcingBasic.class);
        // 如果需要请set 主键id
        ppmOutsourcingBasic.setId(id);
        myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmOutsourcingBasic queryDetail(PpmOutsourcingBasic ppmOutsourcingBasic) {
        return myBatisDao.selectClass(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, "queryDetail");
    }

    @Override
    public Integer updatePpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) {
        if (StringUtil.isNotEmpty(ppmOutsourcingBasic.getDeptId())) {
            ppmOutsourcingBasic.setDeptId(ppmOutsourcingBasic.getDeptId().replace("dep", ""));
        }
        return myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer updatePpmOutsourcingBasicCus(PpmOutsourcingBasic ppmOutsourcingBasic) {
        if (StringUtil.isNotEmpty(ppmOutsourcingBasic.getDeptId())) {
            ppmOutsourcingBasic.setDeptId(ppmOutsourcingBasic.getDeptId()
                    .replace("dep", ""));
        }
        return myBatisDao.executionPojo(PPMOUTSOURCINGBASIC
                , ppmOutsourcingBasic
                , IMyBatisDao.SQL.UPDATE, "updatePojoCus");
    }

    @Override
    public Integer updatePpmOutsourcingBasicUpdate(PpmOutsourcingBasic ppmOutsourcingBasic) {
        if (StringUtil.isNotEmpty(ppmOutsourcingBasic.getDeptId())) {
            ppmOutsourcingBasic.setDeptId(ppmOutsourcingBasic.getDeptId().replace("dep", ""));
        }
        return myBatisDao.executionPojo(
                PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, IMyBatisDao.SQL.UPDATE, "updatePojoInfo");
    }

    @Override
    public Integer updatePpmOutsourcingBasicTransfer(PpmOutsourcingBasic ppmOutsourcingBasic) {
        if (StringUtil.isNotEmpty(ppmOutsourcingBasic.getDeptId())) {
            ppmOutsourcingBasic.setDeptId(ppmOutsourcingBasic.getDeptId().replace("dep", ""));
        }
        return myBatisDao.executionPojo(
                PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, IMyBatisDao.SQL.UPDATE, "updatePojoTransfer");
    }


    @Override
    public Integer deletePpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public PpmOutsourcingBasicInfo getBaseInfo(String jobid) {
        Map param = new HashMap();
        param.put("jobid", jobid);
        PpmOutsourcingBasicInfo info = myBatisDao.selectClass(PPMOUTSOURCINGBASIC, param, "queryBaseInfo");
        if (info != null && info.getSystemNo() != null) {
            PpmSystemInfoDto systemParam = new PpmSystemInfoDto();
            systemParam.setId(Long.parseLong(info.getSystemNo()));
            PpmSystemInfoDto systemInfoDto = ppmSystemInfoService.queryDetail(systemParam);
            if (null != systemInfoDto) {
                info.setSystemnames(systemInfoDto.getName());
            }
        }
        if (info != null && info.getProjectNumber() != null) {
            PpmProjectInfoDto projectParam = new PpmProjectInfoDto();
            projectParam.setId(Long.parseLong(info.getProjectNumber()));
            PpmProjectInfoDto projectInfoDto = ppmProjectInfoService.queryDetail(projectParam);
            if (null != projectInfoDto) {
                info.setProjectname(projectInfoDto.getName());
            }
        }
        return info;
    }

//    @Override
//    public List<PpmOutsourcingBasicInfo> getBaseInfoByTerms(PpmOutsourcingBasic ppmOutsourcingBasic) {
//        List<PpmOutsourcingBasicInfo> info =
//                myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, "queryBaseInfoByTerms");
//        return info;
//    }

    @Override
    public void queryUserIdForState(String s, String roleId, Pager pager) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", s);
        map.put("roleId", roleId);
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC, map, pager, "queryUserIdForState");
    }

    @Override
    public boolean repeatEntry(String name, String idCard) {
        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
//        basic.setUserName(name);
        basic.setCertificateNumber(idCard);
        basic.setState("0");
        List<PpmOutsourcingBasic> item = myBatisDao.selectListT(PPMOUTSOURCINGBASIC, basic, "getList");
        return null != item && !item.isEmpty();
    }

    @Override
    public boolean selJobIdRepart(String jobId) {
        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
//        basic.setUserName(name);
        basic.setJobId(jobId);
        basic.setState("0");
        List<PpmOutsourcingBasic> item = myBatisDao.selectListT(PPMOUTSOURCINGBASIC, basic, "getList");
        return null != item && !item.isEmpty();
    }

    @Transactional("defaultTx")
    @Override
    public void updateOldSystemOut() {
        Map param = new HashMap();
        //同步给老应用ppm_outsourcing_apply
        myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, param, IMyBatisDao.SQL.INSTER, "insertPpmOutsourcingApply");
        //同步给老应用comm_outsourcing_person
        myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, param, IMyBatisDao.SQL.INSTER, "insertCommOutsourcingPerson");
        //同步给老应用ppm_outsourcing_apply
        myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, param, IMyBatisDao.SQL.UPDATE, "updatePpmOutsourcingApply");
        //同步给老应用comm_outsourcing_person
        myBatisDao.executionPojo(PPMOUTSOURCINGBASIC, param, IMyBatisDao.SQL.UPDATE, "updateCommOutsourcingPerson");
    }

    @Override
    public void updateBasicState() {
        Map<String,Object> map = new HashMap<>();
        map.put("state","2");
        map.put("entryTime",DateUtil.strToDate(
                DateUtil.dateToStr(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd"));
        List<PpmOutsourcingBasic> outsourcingBasics = queryOutsList(map);
        if (CollectionUtils.isEmpty(outsourcingBasics)){
            log.info("定时变更外包人员状态:{}需要变更外包人员状态的数据为",
                    DateUtil.dateToStr(new Date(),"yyyy-MM-dd"));
            return;
        }

        outsourcingBasics.forEach(ppmOutsourcingBasic -> {
            PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
            basic.setId(ppmOutsourcingBasic.getId());
            basic.setState("0");
            updatePpmOutsourcingBasic(basic);
        });
    }

    @Override
    public List<PpmOutsourcingBasic> queryOutsList(Map param) {
        PpmOutsourcingBasic ppmOutsourcingBasic = JsonUtil.jsonToClass(param, PpmOutsourcingBasic.class);
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, "getList");
    }

    @Override
    public String getDepIdByJobId(String jobId) {
        PpmOutsourcingBasic ppmOutsourcingBasic = new PpmOutsourcingBasic();
        ppmOutsourcingBasic.setJobId(jobId);
        List<PpmOutsourcingBasic> list = myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, "getList");
        String depId = "";
        if (list != null && list.size() > 0) {
            depId = list.get(0).getDeptId();
        }
        return depId;
    }

    @Override
    public List<CommDepAndOutsTreeDto> getDepOutsTree() {
        List<CommDepartment> deps = commDepartmentService.queryHeadOffice();
        List<CommDepAndOutsTreeDto> trees = JsonUtil.jsonToList(deps, CommDepAndOutsTreeDto.class);
        if (trees.size() > 0) {
            for (int i = 0; i < trees.size(); i++) {
                Map param = new HashMap();
                param.put("deptId", trees.get(i).getDepid());
                param.put("state", "0");
                List<PpmOutsourcingBasic> outs = queryOutsList(param);
                List<PpmOutsourcingBasicDto> childern = JsonUtil.jsonToList(outs, PpmOutsourcingBasicDto.class);
                trees.get(i).setChildren(childern);
            }
        }
        return trees;
    }

    @Override
    public List<PpmOutsourcingBasic> queryDetailByIdAndName(PpmOutsourcingBasic ppmOutsourcingBasic) {
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, "queryDetailByIdAndName");
    }

    @Override
    public List<PpmOutsourcingBasicInfo> queryPpmOutsourcingBasicInfo(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo) {
        if (StringUtil.isNotBlank(ppmOutsourcingBasicInfo.getDeptId())) {
            ppmOutsourcingBasicInfo.setDeptId(ppmOutsourcingBasicInfo.getDeptId().replace("dep", ""));
        }
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasicInfo, "queryBaseInfoList");
    }

    @Override
    public List<PpmOutsourcingBasicInfo> queryHumanList(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo){
        if (StringUtil.isNotBlank(ppmOutsourcingBasicInfo.getDeptId())) {
            ppmOutsourcingBasicInfo.setDeptId(ppmOutsourcingBasicInfo.getDeptId().replace("dep", ""));
        }
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasicInfo, "queryHumanList");
    }

    @Override
    public List<PpmOutsourcingBasicInfo> queryMgrList(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo){
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasicInfo, "queryMgrList");
    }


    @Override
    public Integer saveOutBaseArchives(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo) {
        PpmOutsourcingBasic basic = JsonUtil.jsonToClass(ppmOutsourcingBasicInfo, PpmOutsourcingBasic.class);
        //查询原数据
        PpmOutsourcingBasic basic_old = new PpmOutsourcingBasic();
        basic_old.setId(basic.getId());
        basic_old = queryDetail(basic_old);
        //更新基本信息表
        updatePpmOutsourcingBasic(basic);
        //更新组织树岗位和小组
        PpmDaTeamUser user = new PpmDaTeamUser();
        user.setUserid(ppmOutsourcingBasicInfo.getJobId());
        user = PpmDaTeamUserService.queryDetailByUserid(user);
        user.setGw(ppmOutsourcingBasicInfo.getPostId());
        user.setTeamgrpupid(ppmOutsourcingBasicInfo.getGroupId());
        PpmDaTeamUserService.updatePpmDaTeamUser(user);
        return 1;
    }

    @Override
    public List<OutFlowData> getFlowData(OutDataDto outDataDto) {
        Map param = new HashMap();
        param.put("jobid", outDataDto.getJobid());
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, param, "getOutFlowData");
    }

    @Override
    public List<PpmOutsourcingBasicDto> queryOutList(PpmOutsourcingBasicDto ppmOutsourcingBasicDto) {
        PpmOutsourcingBasic ppmOutsourcingBasic = JsonUtil.jsonToClass(ppmOutsourcingBasicDto, PpmOutsourcingBasic.class);
        List<PpmOutsourcingBasic> list = myBatisDao.selectListT(PPMOUTSOURCINGBASIC, ppmOutsourcingBasic, "getList");
        return JsonUtil.jsonToList(list, PpmOutsourcingBasicDto.class);
    }

    @Override
    public String getPermissionByUserid(CommOperatorDoLoginDto user) {
        String auth = "common";
        List<ImRoleDoLoginDto> roles = user.getRoles();
        // 权限注释： common  普通行员、项目经理
        //                    president 一级部经理
        //                    managers 二级部经理
        //                    admin 系统管理员

        // 经理层
        List<CommDepartment> depList = commDepartmentService.queryDirectDeps(user.getUserid());
//        if (!ObjectUtils.isEmpty(depList)) {
//            auth = "managers";
//        }
        // 二级部经理
        if (roles.stream().anyMatch(r -> "SECONDMANAGER".equals(r.getRoleId()))) {
            auth = "president";
        }
        // 一级部经理
        if (roles.stream().anyMatch(r -> "GENERALMANAGER".equals(r.getRoleId()))) {
            auth = "permanage";
        }
        // 部门
        if (UserSessionUtil.isQueryTeam()) {
            auth = "managers";
        }
        // 系统管理员
        if (UserSessionUtil.isQueryMgr()) {
            auth = "admin";
        }

        return auth;
    }

    @Override
    public List<PpmProjectFlowDto> getOutFlowInfos(OutDataDto outDataDto) {
        Map<String, Object> map = StringUtil.objectTOMap(outDataDto, OutDataDto.class);
        return myBatisDao.selectListT(PPMOUTSOURCINGBASIC, map, "queryOutFlowsInfos");
    }

    @Override
    public List<PpmOutsourcingBasicExcelDto> exportOutsourcing(
            PpmOutsourcingBasicExcelDto ppmOutsourcingBasicDto, CommOperatorDoLoginDto user) {
        // 当前登录人是经理层
        CommEmployee emp = UsersUtil.getCommEmployeeInfo(user.getUserid());
        // 权限注释： common  普通行员、项目经理
        //                    president 一级部经理
        //                    managers 二级部经理
        //                    admin 系统管理员
        String authCode = this.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            ppmOutsourcingBasicDto.setDeptId(user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            ppmOutsourcingBasicDto.setPersonInCharge(user.getUserid());
            //系统管理员和一级部经理不做限制
        } else {
            String deptStr = commParttimejobService.queryDeptIdByBadge(user.getUserid());
            String[] ids = {"009", "010", "030", "037"};
            long rowId = Arrays.stream(ids).filter(i -> i.equals(emp.getHeadship())).count();
            if (rowId > 0) {
                String depId;
                if (StringUtil.isNotBlank(deptStr)) {
                    depId = "'" + user.getDeptId() + "'," + deptStr;
                } else {
                    depId = "'" + user.getDeptId() + "'";
                }
                ppmOutsourcingBasicDto.setDeptId(depId);
            }
        }
        PpmOutsourcingBasicInfo basic = JsonUtil.jsonToClass(ppmOutsourcingBasicDto, PpmOutsourcingBasicInfo.class);
        // 将时间查询条件转换为日期对象
        if (!ObjectUtils.isEmpty(ppmOutsourcingBasicDto.getEntryTimes())) {
            basic.setEntryStartDate(DateUtil.strToDate(ppmOutsourcingBasicDto.getEntryTimes()[0], "yyyy-MM-dd"));
            basic.setEntryEndDate(DateUtil.strToDate(ppmOutsourcingBasicDto.getEntryTimes()[1], "yyyy-MM-dd"));
        }
        if (!ObjectUtils.isEmpty(ppmOutsourcingBasicDto.getLeavingTimes())) {
            basic.setLeaveStartDate(DateUtil.strToDate(ppmOutsourcingBasicDto.getLeavingTimes()[0], "yyyy-MM-dd"));
            basic.setLeaveEndDate(DateUtil.strToDate(ppmOutsourcingBasicDto.getLeavingTimes()[1], "yyyy-MM-dd"));
        }
        List<PpmOutsourcingBasicInfo> infos = this.queryPpmOutsourcingBasicInfo(basic);
        List<PpmOutsourcingBasicExcelDto> dto = JsonUtil.jsonToList(infos, PpmOutsourcingBasicExcelDto.class);
        String format = "yyyy-MM-dd";
        dto.stream().forEach(item -> {
            if (item.getEntryTime() != null) {
                item.setEntryTimeFormate(DateUtil.dateToStr(item.getEntryTime(), format));
            }
            if (item.getLeavingTime() != null) {
                item.setLeavingTimeFormate(DateUtil.dateToStr(item.getLeavingTime(), format));
            }
            if (item.getAccessFlowTime() != null) {
                item.setAccessFlowTimeFormate(DateUtil.dateToStr(item.getAccessFlowTime(), format));
            }
            if (item.getLeavFlowTime() != null) {
                item.setLeavFlowTimeFormate(DateUtil.dateToStr(item.getLeavFlowTime(), format));
            }
            if (item.getQualityTime() != null) {
                item.setQualityTimeFormate(DateUtil.dateToStr(item.getQualityTime(), format));
            }
            if (item.getEitquetteTime() != null) {
                item.setEitquetteTimeFormate(DateUtil.dateToStr(item.getEitquetteTime(), format));
            }
            // 添加工作年限和建议等级
            PpmIntervieweeInfo ppmIntervieweeInfo = ppmIntervieweeInfoService.queryPpmIntervieweeInfoByIdcard(item.getCertificateNumber());
            if (!ObjectUtils.isEmpty(ppmIntervieweeInfo)) {
                item.setWorkYears(ppmIntervieweeInfo.getWorkyears());
                item.setSuggestLevel(ppmIntervieweeInfo.getSuggestlevel());
            }

            if(StringUtils.isNotEmpty(item.getFaceimage())){
                PpmDocument document = ppmDocumentService.getImageUrl(item.getFaceimage());
                if(document != null){
//                    item.setImage(document.getDocumentno());
//                    String url = "C:\\Users\\ywwb905\\Desktop\\194994.jpg";
                    String url = fileUploadPath + "ppmtest" + document.getStockpath() + document.getTag2();
                    byte[] faceByteValue ;
                    try {
                        faceByteValue = FileUtil.imageToBytes(url);
                        if(faceByteValue.length > 0){
                            item.setImage(faceByteValue);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });
        return dto;
    }

    @Override
    public boolean importOutsourcingCard(List<PpmOutsourcingBasicCardExcelDto> cardExcelDtos, CommOperatorDoLoginDto user) {
        cardExcelDtos.forEach(cardExcelDto -> {
            PpmOutsourcingBasic basic = JsonUtil.jsonToClass(cardExcelDto,PpmOutsourcingBasic.class);
            updatePpmOutsourcingBasic(basic);
        });
        return true;
    }

    @Override
    public void queryCrewPageList(CommEmployeeDto commEmployeeDto, Pager pager, CommOperatorDoLoginDto commOperatorDoLoginDto) {
        // 将时间查询条件转换为日期对象
        if (!ObjectUtils.isEmpty(commEmployeeDto.getEntryTime())) {
            commEmployeeDto.setEntryStartDate(DateUtil.strToDate(commEmployeeDto.getEntryTime()[0], "yyyy-MM-dd"));
            Date entryEndDate = DateUtil.strToDate(commEmployeeDto.getEntryTime()[1], "yyyy-MM-dd");
            commEmployeeDto.setEntryEndDate(DateUtil.addOrSubHour(entryEndDate, 24));
        }
        if (!ObjectUtils.isEmpty(commEmployeeDto.getLeavingTime())) {
            commEmployeeDto.setLeaveStartDate(DateUtil.strToDate(commEmployeeDto.getLeavingTime()[0], "yyyy-MM-dd"));
            Date leaveEndDate = DateUtil.strToDate(commEmployeeDto.getLeavingTime()[1], "yyyy-MM-dd");
            commEmployeeDto.setLeaveEndDate(DateUtil.addOrSubHour(leaveEndDate, 24));
        }
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        // 权限注释： common  普通行员、项目经理
        //                    president 一级部经理
        //                    managers 二级部经理
        //                    admin 系统管理员
//       String authCode = this.getPermissionByUserid(commOperatorDoLoginDto);
        String authCode = "admin";
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            map.put("depid", commOperatorDoLoginDto.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            map.put("personInCharge", commOperatorDoLoginDto.getUserid());
            //系统管理员和一级部经理不做限制
        } else {
            // 行员部门兼职
            String deptStr = commParttimejobService.queryDeptIdByBadge(commOperatorDoLoginDto.getUserid());
            // 业务处理
            String dep = null == commEmployeeDto.getDep() ? "" : commEmployeeDto.getDep();
            if (dep.contains("GRP") || dep.contains("TEAM")) {
                map.put("gro", commEmployeeDto.getDep().replaceAll("gro", ""));
                map.put("depid", "");
            } else if (dep.contains("dep")) {
                String dept = commEmployeeDto.getDep().replaceAll("dep", "");
                map.put("depid", "'" + dept + "'," + deptStr);
            }
        }
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC, map, pager, "queryCrewPageList");
    }

    @Override
    public List<CommEmployeeInfo> exportBasicOutsourcing(CommEmployeeDto commEmployeeDto, CommOperatorDoLoginDto user) {
        // 将时间查询条件转换为日期对象
        if (!ObjectUtils.isEmpty(commEmployeeDto.getEntryTime())) {
            commEmployeeDto.setEntryStartDate(DateUtil.strToDate(commEmployeeDto.getEntryTime()[0], "yyyy-MM-dd"));
            Date entryEndDate = DateUtil.strToDate(commEmployeeDto.getEntryTime()[1], "yyyy-MM-dd");
            commEmployeeDto.setEntryEndDate(DateUtil.addOrSubHour(entryEndDate, 24));
        }
        if (!ObjectUtils.isEmpty(commEmployeeDto.getLeavingTime())) {
            commEmployeeDto.setLeaveStartDate(DateUtil.strToDate(commEmployeeDto.getLeavingTime()[0], "yyyy-MM-dd"));
            Date leaveEndDate = DateUtil.strToDate(commEmployeeDto.getLeavingTime()[1], "yyyy-MM-dd");
            commEmployeeDto.setLeaveEndDate(DateUtil.addOrSubHour(leaveEndDate, 24));
        }
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        // 权限注释： common  普通行员、项目经理
        //                    president 一级部经理
        //                    managers 二级部经理
        //                    admin 系统管理员
//       String authCode = this.getPermissionByUserid(commOperatorDoLoginDto);
        String authCode = "admin";
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            map.put("depid", user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            map.put("personInCharge", user.getUserid());
            //系统管理员和一级部经理不做限制
        } else {
            // 行员部门兼职
            String deptStr = commParttimejobService.queryDeptIdByBadge(user.getUserid());
            // 业务处理
            String dep = null == commEmployeeDto.getDep() ? "" : commEmployeeDto.getDep();
            if (dep.contains("GRP") || dep.contains("TEAM")) {
                map.put("gro", commEmployeeDto.getDep().replaceAll("gro", ""));
                map.put("depid", "");
            } else if (dep.contains("dep")) {
                String dept = commEmployeeDto.getDep().replaceAll("dep", "");
                map.put("depid", "'" + dept + "'," + deptStr);
            }
        }
        List<CommEmployeeInfo> basicInfos = myBatisDao.selectListT(PPMOUTSOURCINGBASIC, map, "queryCrewPageList");
        if (CollectionUtil.isNotEmpty(basicInfos)) {
            //整合字典 在岗、资质级别
            List<EosDictEntry> eosDictEntryList =
                    eosDictEntryService.getTypeidForList(new String[]{"NEWKG_RYZT", "EVALUATION_LEVEL"});
            Map<String, String> dictMap = new HashMap<>();
            for (EosDictEntry eosDictEntry : eosDictEntryList) {
                dictMap.put(eosDictEntry.getDictid(), eosDictEntry.getDictname());
            }
            String format = "yyyy-MM-dd";
            // 前端外包花名册页面原有过滤数据条件

            // 重新赋值导出
            basicInfos.stream().forEach(item -> {

                if (item.getEntryTime() != null) {
                    item.setEntryTimeFormate(DateUtil.dateToStr(item.getEntryTime(), format));
                }
                if (item.getLeavingTime() != null) {
                    item.setLeavingTimeFormate(DateUtil.dateToStr(item.getLeavingTime(), format));
                }
                if (StringUtils.isNotEmpty(item.getChargeName()) && item.getChargeName().contains("-")) {
                    String[] split = item.getChargeName().split("-");
                    String chargeName = split[split.length - 1];
                    item.setChargeName(StringUtils.isNotEmpty(chargeName) ? chargeName : "金融科技部");
                } else if (StringUtils.isEmpty(item.getChargeName())) {
                    item.setChargeName("金融科技部");
                }
                if (StringUtils.isNotEmpty(item.getState())) {
                    item.setState(dictMap.get(item.getState()));
                }
                if (StringUtils.isNotEmpty(item.getEvaluationLevel())) {
                    item.setEvaluationLevel(dictMap.get(item.getEvaluationLevel()));
                }

            });
        }
        return basicInfos;
    }

}
