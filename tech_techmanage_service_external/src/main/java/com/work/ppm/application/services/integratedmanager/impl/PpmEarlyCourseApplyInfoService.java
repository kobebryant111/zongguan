package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmEarlyCourseApplyInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmEarlyCourseApplyInfoView;
import com.work.ppm.application.services.integratedmanager.IPpmEarlyCourseApplyInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 提前入场申请信息
 *
 */
@Service("ppmEarlyCourseApplyInfoService")
public class PpmEarlyCourseApplyInfoService extends BaseDaoSupper implements IPpmEarlyCourseApplyInfoService {
    /**
     * 实体类路径
     */
    private static String PPM_EARLY_COURSE_APPLY_INFO =
            "com.work.ppm.application.model.integratedmanager.PpmEarlyCourseApplyInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmIntervieweeInfoService ppmIntervieweeInfoService;

    @Autowired
    private ICommParttimejobService commParttimejobService;
    /**
     * 外包人员信息服务
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Override
    public List<PpmEarlyCourseApplyInfoDto> queryPager(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyInfoDto,
                                                   Pager pager, CommOperatorDoLoginDto user) {

        if(StringUtils.isNotEmpty(ppmEarlyCourseApplyInfoDto.getDeptNo())){
            ppmEarlyCourseApplyInfoDto.setDeptNo(
                    ppmEarlyCourseApplyInfoDto.getDeptNo().replace("dep",""));
        }

        handleAuthCode(ppmEarlyCourseApplyInfoDto,user);

        Map<String, Object> map = JsonUtil.objectTOMap(ppmEarlyCourseApplyInfoDto, PpmEarlyCourseApplyInfoDto.class);
        myBatisDao.selectPager(PPM_EARLY_COURSE_APPLY_INFO, map, pager, "queryBaseInfoList");

        List<PpmEarlyCourseApplyInfoDto> infoDtos =
                JsonUtil.jsonToList(pager.getList(), PpmEarlyCourseApplyInfoDto.class);
        infoDtos.forEach(infoDto -> infoDto.setDeptName(BusinessUtil.getDeptNameValue(infoDto.getDeptName(),1)));
        return infoDtos;
    }

    @Override
    public List<PpmEarlyCourseApplyInfoDto> queryPmPager(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyInfoDto,
                                                       Pager pager, CommOperatorDoLoginDto user) {

        if(ppmEarlyCourseApplyInfoDto.getDeptNo() != null){
            ppmEarlyCourseApplyInfoDto.setDeptNo(
                    ppmEarlyCourseApplyInfoDto.getDeptNo().replace("dep",""));
        }

//        handleAuthCode(ppmEarlyCourseApplyInfoDto,user);

        ppmEarlyCourseApplyInfoDto.setCampaignTimeVal(DateUtil.dateToIsoDateFormat(new Date()));
        ppmEarlyCourseApplyInfoDto.setApplyer(user.getUserid());

        Map<String, Object> map = JsonUtil.objectTOMap(ppmEarlyCourseApplyInfoDto, PpmEarlyCourseApplyInfoDto.class);
        myBatisDao.selectPager(PPM_EARLY_COURSE_APPLY_INFO, map, pager, "queryBaseInfoList");

        List<PpmEarlyCourseApplyInfoDto> infoDtos =
                JsonUtil.jsonToList(pager.getList(), PpmEarlyCourseApplyInfoDto.class);
        infoDtos.forEach(infoDto -> {
            infoDto.setDeptName(BusinessUtil.getDeptNameValue(infoDto.getDeptName(),1));
            infoDto.setIsUpdate("Y".equals(infoDto.getIsComplement())+"");
        });
        return infoDtos;
    }

    @Override
    public int addPpmEarlyCourseApplyInfo(List<PpmEarlyCourseApplyInfoDto> applyInfoDtos) {
        if (applyInfoDtos == null || applyInfoDtos.isEmpty()) {
            return -1;
        }
        applyInfoDtos.stream().forEach(item -> {
            PpmEarlyCourseApplyInfo applyInfo = JsonUtil.jsonToClass(item,PpmEarlyCourseApplyInfo.class);
            if (applyInfo.getId() == null) {
                // 如果需要请set 主键id
                applyInfo.setId(getNextSequence(PpmEarlyCourseApplyInfo.class));
                myBatisDao.executionPojo(
                        PPM_EARLY_COURSE_APPLY_INFO, applyInfo, IMyBatisDao.SQL.INSTER, "addPojo");
            } else {
                this.updatePpmEarlyCourseApplyInfo(item);
            }
        });

        return 1;
    }

    @Override
    public List<PpmEarlyCourseApplyInfoDto> queryListForInstanceId(String instanceId) {
        PpmEarlyCourseApplyInfoDto applyInfoDto = new PpmEarlyCourseApplyInfoDto();
        applyInfoDto.setInstanceid(instanceId);
        return queryBaseList(applyInfoDto);
    }

    @Override
    public List<PpmEarlyCourseApplyInfoDto> queryBaseList(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyInfoDto) {
        List<PpmEarlyCourseApplyInfoView> list =
                myBatisDao.selectListT(PPM_EARLY_COURSE_APPLY_INFO, ppmEarlyCourseApplyInfoDto, "queryBaseInfoList");
        return JsonUtil.jsonToList(list, PpmEarlyCourseApplyInfoDto.class);
    }

    @Override
    public Integer updatePpmEarlyCourseApplyInfo(PpmEarlyCourseApplyInfoDto applyInfoDto) {
        PpmEarlyCourseApplyInfo applyInfo = JsonUtil.jsonToClass(applyInfoDto,PpmEarlyCourseApplyInfo.class);
        return myBatisDao.executionPojo(
                PPM_EARLY_COURSE_APPLY_INFO, applyInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteByInstanceId(String instanceId) {
        PpmEarlyCourseApplyInfo applyInfo = new PpmEarlyCourseApplyInfo();
        applyInfo.setInstanceid(instanceId);
        return myBatisDao.executionPojo(
                PPM_EARLY_COURSE_APPLY_INFO, applyInfo, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }

    @Override
    public Integer deleteInCardNo(List<PpmEarlyCourseApplyInfoDto> infoDtos, String instanceId) {
        StringBuffer cardNoDel = new StringBuffer();
        infoDtos.stream().filter(infoDto -> StringUtils.isNotEmpty(infoDto.getCardNo())).collect(Collectors.toList()).
                forEach(infoDto -> cardNoDel.append("'").append(infoDto.getCardNo()).append("'").append(","));

        PpmEarlyCourseApplyInfoDto applyInfo = new PpmEarlyCourseApplyInfoDto();
        applyInfo.setCardNoDel(cardNoDel.substring(0,cardNoDel.length()-1));
        applyInfo.setInstanceid(instanceId);
        return myBatisDao.executionPojo(
                PPM_EARLY_COURSE_APPLY_INFO, applyInfo, IMyBatisDao.SQL.DELETE, "deleteInCardNo");
    }

    @Override
    public PpmEarlyCourseApplyInfoDto queryDetailCert(String cardNo) {
        PpmEarlyCourseApplyInfo applyInfo = new PpmEarlyCourseApplyInfo();
        applyInfo.setCardNo(cardNo);
        PpmEarlyCourseApplyInfo item =
                myBatisDao.selectClass(PPM_EARLY_COURSE_APPLY_INFO, applyInfo, "queryDetailCert");
        return JsonUtil.jsonToClass(item, PpmEarlyCourseApplyInfoDto.class);
    }

    @Override
    public List<PpmIntervieweeInfoDto> interViews(String deptNo) {
        if(StringUtil.isNotEmpty(deptNo)){
            return ppmIntervieweeInfoService.getCourseInfoList(deptNo.replace("dep",""));
        }
        return null;
    }

    @Override
    public List<PpmEarlyCourseApplyInfoDto> exportExcel(PpmEarlyCourseApplyInfoDto applyInfoDto,
                                                        CommOperatorDoLoginDto user) {

        if(StringUtils.isNotEmpty(applyInfoDto.getDeptNo())){
            applyInfoDto.setDeptNo(applyInfoDto.getDeptNo().replace("dep",""));
        }

        handleAuthCode(applyInfoDto,user);

        List<PpmEarlyCourseApplyInfoDto> infoDtos = queryBaseList(applyInfoDto);
        if(infoDtos != null && !infoDtos.isEmpty()){
            infoDtos.forEach(infoDto -> infoDto.setDeptName(BusinessUtil.getDeptNameValue(infoDto.getDeptName(),1)));
        }

        return infoDtos;
    }

    /**
     * 处理权限
     * @param applyInfoDto ppmKqInfoDto
     * @param user user
     */
    private void handleAuthCode(PpmEarlyCourseApplyInfoDto applyInfoDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            applyInfoDto.setDeptNoQue(user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            applyInfoDto.setApplyer(user.getUserid());
            //系统管理员和一级部经理不做限制
        }
    }


}
