package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.model.projectmanager.ext.PpmprojectExtInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.projectmanager.PpmProject;
import com.work.ppm.application.dto.projectmanager.PpmProjectDto;
import com.work.ppm.application.services.projectmanager.IPpmProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmProjectService")
public class PpmProjectService extends BaseDaoSupper implements IPpmProjectService {
    /**
      * 实体类路径
      */
    private static String PPMPROJECT = "com.work.ppm.application.model.projectmanager.PpmProject";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmProjectDto ppmProjectDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProjectDto,PpmProjectDto.class);
        myBatisDao.selectPager(PPMPROJECT, map,pager,"queryPagerList");
    }

    @Override
    public PpmProjectDto queryDetail(PpmProjectDto ppmProjectDto) {
        PpmProject ppmProject = JsonUtil.jsonToClass(ppmProjectDto, PpmProject.class);
        PpmProject data = myBatisDao.selectClass(PPMPROJECT, ppmProject,
        "queryDetail");
        PpmProjectDto item = JsonUtil.jsonToClass(data, PpmProjectDto
        .class);
        return item;
    }

    @Override
    public int updatePpmProject(PpmProjectDto ppmProjectDto) {
        PpmProject ppmProject = JsonUtil.jsonToClass(ppmProjectDto, PpmProject.class);
        return myBatisDao.executionPojo(PPMPROJECT, ppmProject, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public List<PpmProjectDto> getPpmSubProjectView(String projectno, String planonline) {
        PpmprojectExtInfo ppmProjectExtInfo = new PpmprojectExtInfo();
        ppmProjectExtInfo.setProjectno(projectno);
        if(planonline != null && !"".equals(planonline) )
        {
            ppmProjectExtInfo.setPlanonline(planonline);
        }
        List<PpmprojectExtInfo> ppmprojectinfos = myBatisDao.
                selectListT(PPMPROJECT, ppmProjectExtInfo,"getPpmSubProjectView");

        return JsonUtil.jsonToList(ppmprojectinfos, PpmProjectDto.class);
    }

    @Override
    public void updatePpmProjectByUserid(PpmProjectDto ppmProjectDto, String userid) {
        PpmProject ppmProject = JsonUtil.jsonToClass(ppmProjectDto, PpmProject.class);
        PpmProject data = myBatisDao.selectClass(PPMPROJECT, ppmProject, "queryDetailBySubprojectno");
        PpmProjectDto item = JsonUtil.jsonToClass(data, PpmProjectDto.class);

        if (item != null) {
            PpmProjectDto projectDto = new PpmProjectDto();
            projectDto.setId(item.getId());
            projectDto.setOrgarea(ppmProjectDto.getOrg());
            projectDto.setLastmoduser(userid);
            projectDto.setLastmodtime(new Date());
            this.updatePpmProject(projectDto);
        }
    }

    @Override
    public List<PpmProjectDto> queryPpmProjectList(PpmProjectDto ppmProjectDto) {
        PpmprojectExtInfo ppmProject = JsonUtil.jsonToClass(ppmProjectDto, PpmprojectExtInfo.class);
        List<PpmprojectExtInfo> projectInfoList = myBatisDao.selectListT(PPMPROJECT, ppmProject, "queryPpmProjectList");
        return JsonUtil.jsonToList(projectInfoList, PpmProjectDto.class);
    }

    @Override
    public List<PpmProjectDto> queryProjectListByProjectno(String projectno) {
        PpmProject ppmProject = new PpmProject();
        ppmProject.setProjectno(projectno);
        List<PpmprojectExtInfo> projectInfoList = myBatisDao.selectListT(PPMPROJECT, ppmProject,
                "queryPagerList");
        return JsonUtil.jsonToList(projectInfoList, PpmProjectDto.class);
    }
}
