package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectUserInfoDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmProjectInfo;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 项目详情表 接口实现类
 *
 */
@Service("ppmProjectInfoService")
public class PpmProjectInfoService extends BaseDaoSupper implements IPpmProjectInfoService {
    /**
     * 实体类路径
     */
    private static String PPMPROJECTINFO = "com.work.ppm.application.model.systemmanager.PpmProjectInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmProjectInfoDto ppmProjectInfoDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmProjectInfoDto, PpmProjectInfoDto.class);
        myBatisDao.selectPager(PPMPROJECTINFO, map, pager, "queryPagerList");
    }

    @Override
    public List<CommEmployee> findByPpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) {
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        List<CommEmployee> result = myBatisDao.selectListT(PPMPROJECTINFO, ppmProjectInfo, "findByPpmProjectInfo");
        if (!CollectionUtils.isEmpty(result)){
            result = result.stream().distinct().collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public int addPpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) {
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        ppmProjectInfo.setId(getNextSequence(PpmProjectInfo.class));
        myBatisDao.executionPojo(PPMPROJECTINFO, ppmProjectInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int savePpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto, CommOperatorDoLoginDto loginDto) {

        vaildCommitData(ppmProjectInfoDto);

        int count;
        if (ObjectUtils.isEmpty(ppmProjectInfoDto.getId())) {
            ppmProjectInfoDto.setCreateBy(loginDto.getOperatorname());
            ppmProjectInfoDto.setUpdateBy(loginDto.getOperatorname());
            ppmProjectInfoDto.setCreateDate(new Date());
            ppmProjectInfoDto.setUpdateDate(new Date());
            count = addPpmProjectInfo(ppmProjectInfoDto);
        } else {
            ppmProjectInfoDto.setUpdateBy(loginDto.getOperatorname());
            ppmProjectInfoDto.setUpdateDate(new Date());
            count = updatePpmProjectInfo(ppmProjectInfoDto);
        }
        return count;
    }

    @Override
    public List<PpmProjectInfo> queryProject(PpmProjectInfoDto ppmProjectInfoDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmProjectInfoDto, PpmProjectInfoDto.class);
        return myBatisDao.selectListT(PPMPROJECTINFO, map,  "getList");
    }

    @Override
    public List<PpmProjectInfo> queryNewProject(PpmProjectInfoDto ppmProjectInfoDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmProjectInfoDto, PpmProjectInfoDto.class);
        return myBatisDao.selectListT(PPMPROJECTINFO, map,  "getListForProject");
    }

    @Override
    public List<PpmProjectInfo> queryApplyProject(PpmProjectInfoDto ppmProjectInfoDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmProjectInfoDto, PpmProjectInfoDto.class);
        return myBatisDao.selectListT(PPMPROJECTINFO, map,  "getApplyList");
    }

    @Override
    public PpmProjectInfoDto queryDetail(PpmProjectInfoDto ppmProjectInfoDto) {
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        PpmProjectInfo data = myBatisDao.selectClass(PPMPROJECTINFO, ppmProjectInfo, "queryDetail");
        PpmProjectInfoDto item = JsonUtil.jsonToClass(data, PpmProjectInfoDto
                .class);
        return item;
    }

    @Override
    public List<PpmProjectInfoDto> queryDetailList(PpmProjectInfoDto ppmProjectInfoDto,String uniqueName) {
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        List<PpmProjectInfo> data = myBatisDao.selectListT(PPMPROJECTINFO, ppmProjectInfo, uniqueName);
        return JsonUtil.jsonToList(data, PpmProjectInfoDto.class);
    }

    @Override
    public int updatePpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) {
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        return myBatisDao.executionPojo(PPMPROJECTINFO, ppmProjectInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) {
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        return myBatisDao.executionPojo(PPMPROJECTINFO, ppmProjectInfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public PpmProjectInfoDto queryProjectDetail(String name, Long deptId,String projectNumber) {
        PpmProjectInfoDto ppmProjectInfoDto = new PpmProjectInfoDto();
        ppmProjectInfoDto.setName(name);
        ppmProjectInfoDto.setDepId(deptId);
        ppmProjectInfoDto.setProjectNumber(projectNumber);
        PpmProjectInfo ppmProjectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        PpmProjectInfo data = myBatisDao.selectClass(PPMPROJECTINFO, ppmProjectInfo, "queryProjectDetail");
        return JsonUtil.jsonToClass(data, PpmProjectInfoDto.class);
    }

    @Override
    public List<PpmProjectInfoDto> queryTransferList(PpmProjectInfoDto ppmProjectInfoDto) {
        PpmProjectInfo projectInfo = JsonUtil.jsonToClass(ppmProjectInfoDto, PpmProjectInfo.class);
        List<PpmProjectInfo> projectInfos = myBatisDao.selectListT(PPMPROJECTINFO, projectInfo, "queryPagerList");

        List<PpmProjectInfoDto> projectInfoDtos =  JsonUtil.jsonToList(projectInfos,PpmProjectInfoDto.class);
        if (!CollectionUtils.isEmpty(projectInfoDtos)) {
            projectInfoDtos.forEach(item -> {
                List<PpmProjectUserInfoDto> list = new ArrayList<>();
                PpmProjectUserInfoDto projectUserInfoDto = new PpmProjectUserInfoDto();
                projectUserInfoDto.setBadge(item.getBadge());
                projectUserInfoDto.setEmpName(item.getEmpName());
                projectUserInfoDto.setBusiBadge(item.getBusiBadge());
                projectUserInfoDto.setBusiBadgeName(item.getBusiBadgeName());
                projectUserInfoDto.setDeptId(item.getDepId()+"");
                projectUserInfoDto.setDeptName(BusinessUtil.getDeptNameValue(item.getDepName(),1));
                list.add(projectUserInfoDto);

                if (StringUtils.isNotEmpty(item.getTestBadge())
                        && !Objects.equals(item.getTestBadge(),item.getBadge())){
                    PpmProjectUserInfoDto projectUserInfoDto1 = new PpmProjectUserInfoDto();
                    projectUserInfoDto1.setBadge(item.getTestBadge());
                    projectUserInfoDto1.setEmpName(item.getTestBadgeName());
                    projectUserInfoDto1.setBusiBadge(item.getBusiBadge());
                    projectUserInfoDto1.setBusiBadgeName(item.getBusiBadgeName());
                    projectUserInfoDto1.setDeptId(item.getTestBadgeDept());
                    projectUserInfoDto1.setDeptName(BusinessUtil.getDeptNameValue(item.getTestBadgeDeptName(),1));
                    list.add(projectUserInfoDto1);
                }
                item.setUserInfoList(list);
                item.setDepName(BusinessUtil.getDeptNameValue(item.getDepName(),1));
            });
        }
        return projectInfoDtos;
    }

    /**
     * 对提交的数据做校验
     * @param ppmProjectInfoDto 对象
     */
    private void vaildCommitData(PpmProjectInfoDto ppmProjectInfoDto){

//        PpmProjectInfoDto projectInfoDto =
//                queryProjectDetail(ppmProjectInfoDto.getName(),ppmProjectInfoDto.getDepId(),null);
//        if(projectInfoDto != null && !Objects.equals(ppmProjectInfoDto.getId(),projectInfoDto.getId())){
//            throw new BizException(ApiCode.FAIL,"此项目同部门的已创建！");
//        }

        if(StringUtils.isEmpty(ppmProjectInfoDto.getBusiBadge())){
            throw new BizException(ApiCode.FAIL,"请填写业务项目经理");
        }


    }


}

