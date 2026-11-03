package com.work.ppm.application.services.rdmanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.work.ppm.application.dao.rdManager.RdProjectMapper;
import com.work.ppm.application.dto.integratedmanager.PpmRdEmployeeInfoExportDto;
import com.work.ppm.application.dto.rdmanager.RdProjectDetailsDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnerDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnovaDto;
import com.work.ppm.application.dto.rdmanager.RdProjectNewDto;
import com.work.ppm.application.dto.rdmanager.RdProjectQueryDto;
import com.work.ppm.application.model.rdmanager.CommRdEmployeeDto;
import com.work.ppm.application.model.rdmanager.RdProject;
import com.work.ppm.application.model.rdmanager.RdProjectExcel;
import com.work.ppm.application.services.rdmanager.IRdProjectService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 研发项目管理
 *
 */
@Service
@Slf4j
public class RdProjectService  extends ServiceImpl<RdProjectMapper, RdProject> implements IRdProjectService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGBASIC = "com.work.ppm.application.dao.rdManager.RdProjectMapper";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(RdProjectQueryDto req, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(req, RdProjectQueryDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC,map, pager, "queryPager");
    }

    @Override
    public List<RdProjectExcel> queryPagerExcel(RdProjectQueryDto req, Pager pager){
        Map<String, Object> map = JsonUtil.objectTOMap(req, RdProjectQueryDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC,map,pager,"queryPagerExcel");
        List<RdProjectExcel> repsJson = JsonUtil.jsonToList(pager.getList(), RdProjectExcel.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Gson gson = new Gson();
        for (RdProjectExcel rdProjectExcel : repsJson) {
            List<String> objects = new ArrayList<>();
            if(!ObjectUtils.isEmpty(rdProjectExcel.getParticipatingDept())){
                List<String> array = gson.fromJson(rdProjectExcel.getParticipatingDept(), List.class);
                map.put("depids",array);
                String location = myBatisDao.selectClass(PPMOUTSOURCINGBASIC, map, "departmentExcel");
                rdProjectExcel.setParticipatingDept(location);
            }
        }
        return repsJson;
    }

    @Override
    public List<CommRdEmployeeDto> queryByRdProjectCode(String projectCode){
//        Map<String, Object> map = JsonUtil.objectTOMap(req, RdProjectQueryDto.class);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("projectCode",projectCode);
        List<CommRdEmployeeDto> commRdEmployeeDto = myBatisDao.selectListT(PPMOUTSOURCINGBASIC,map,"queryByRdProjectCode");
        return commRdEmployeeDto;
    }

    @Override
    public RdProjectDetailsDto getRdProjectDetails(String pkId) {
        RdProject info = this.getById(pkId);
        RdProjectDetailsDto result = JsonUtil.jsonToClass(info,RdProjectDetailsDto.class);
        return result;
    }

    @Override
    public void delete(String pkId) {
        RdProject info = this.getById(pkId);
        AssertUtil.notNull(info,"该项目不存在");
        AssertUtil.isTrue("0".equals(info.getDeleted()),"该项目已经删除");
        LambdaUpdateWrapper<RdProject> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RdProject::getPkId,pkId);
        updateWrapper.set(RdProject::getDeleted,"1");//删除状态
        this.update(updateWrapper);
    }

    @Override
    public void updateInnerInfo(RdProjectInnerDto req) {
        RdProject info = this.getById(req.getPkId());
        AssertUtil.notNull(info,"该项目不存在");
        AssertUtil.isTrue("0".equals(info.getDeleted()),"该项目已经删除");
        LambdaUpdateWrapper<RdProject> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RdProject::getPkId,req.getPkId());
        updateWrapper.set(RdProject::getCompetentDept,StringUtil.nullTo(req.getCompetentDept()));
        updateWrapper.set(RdProject::getParticipatingDept,StringUtil.nullTo(req.getParticipatingDept()));
        updateWrapper.set(RdProject::getDevUser,StringUtil.nullTo(req.getDevUser()));
        updateWrapper.set(RdProject::getHeadUser,StringUtil.nullTo(req.getHeadUser()));
        updateWrapper.set(RdProject::getUpdateBy, UserSessionUtil.getCurrentUser(true).getUserid());
        updateWrapper.set(RdProject::getUpdateDate,new Date());
        this.update(updateWrapper);
    }

    @Override
    public RdProject saveBaseInfo(RdProjectNewDto req) {

        RdProject info = JsonUtil.jsonToClass(req,RdProject.class);
        if(StringUtil.isStringNotEmpty(info.getPkId())){
            LambdaUpdateWrapper<RdProject> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RdProject::getPkId,req.getPkId());
//            updateWrapper.set(RdProject::getProjectCode,StringUtil.nullTo(req.getProjectCode()));
            updateWrapper.set(RdProject::getProjectName,StringUtil.nullTo(req.getProjectName()));
            updateWrapper.set(RdProject::getProjectState,StringUtil.nullTo(req.getProjectState()));
            updateWrapper.set(RdProject::getStartDate,StringUtil.nullTo(req.getStartDate()));
            updateWrapper.set(RdProject::getEndDate,StringUtil.nullTo(req.getEndDate()));
            updateWrapper.set(RdProject::getSysName,StringUtil.nullTo(req.getSysName()));
            updateWrapper.set(RdProject::getUpdateBy, UserSessionUtil.getCurrentUser(true).getUserid());
            updateWrapper.set(RdProject::getUpdateDate,new Date());
            this.update(updateWrapper);
        }else{
            LambdaQueryWrapper<RdProject> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdProject::getProjectCode,req.getProjectCode());
            queryWrapper.eq(RdProject::getDeleted,"0");
            List<RdProject> list = this.list(queryWrapper);
            if(list!=null&&list.size()>0){
                throw new BizRuntimeException("该项目编码已经被使用");
            }
            info.setCreateDate(new Date());
            info.setCreateBy(UserSessionUtil.getCurrentUser(true).getUserid());
            this.save(info);
        }
        return info;
    }

    @Override
    public void updateInnova(RdProjectInnovaDto req) {
        RdProject info = this.getById(req.getPkId());
        AssertUtil.notNull(info,"该项目不存在");
        AssertUtil.isTrue("0".equals(info.getDeleted()),"该项目已经删除");
        LambdaUpdateWrapper<RdProject> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RdProject::getPkId,req.getPkId());
        updateWrapper.set(RdProject::getBusInnova,StringUtil.nullTo(req.getBusInnova()));
        updateWrapper.set(RdProject::getTechInnova,StringUtil.nullTo(req.getTechInnova()));
        this.update(updateWrapper);
    }
}
