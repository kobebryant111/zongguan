package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.dto.usermanager.PpmStaffFileDto;
import com.work.ppm.application.model.usermanager.PpmStaffFile;
import com.work.ppm.application.model.usermanager.PpmStaffFileEdu;
import com.work.ppm.application.model.usermanager.PpmStaffFileRelatives;
import com.work.ppm.application.model.usermanager.ext.PpmStaffFileInfo;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.usermanager.IPpmStaffFileEduService;
import com.work.ppm.application.services.usermanager.IPpmStaffFileRelativesService;
import com.work.ppm.application.services.usermanager.IPpmStaffFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 行员档案信息接口
 */
@Slf4j
@Service("ppmStaffFileService")
public class PpmStaffFileService implements IPpmStaffFileService {
    /**
     * 实体类路径
     */
    private static String PPMSTAFFFILE = "com.work.ppm.application.model.usermanager.PpmStaffFile";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 行员档案教育接口
     */
    @Autowired
    private IPpmStaffFileEduService ppmStaffFileEduService;
    /**
     * 行员档案家庭接口
     */
    @Autowired
    private IPpmStaffFileRelativesService ppmStaffFileRelativesService;
    /**
     * 文档目录
     */
    @Autowired
    private IPpmDocumentCatalogService ppmDocumentCatalogService;
    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmStaffFile ppmStaffFile, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmStaffFile,PpmStaffFile.class);
        myBatisDao.selectPager(PPMSTAFFFILE, map,pager,"queryPagerList");
    }

    @Override
    public PpmStaffFileDto queryUserFile(String badge) {
        PpmStaffFile entity = new PpmStaffFile();
        entity.setBadge(badge);
        PpmStaffFileInfo info = myBatisDao.selectClass(PPMSTAFFFILE, entity,"queryByBadge");
        PpmStaffFileDto dto = JsonUtil.jsonToClass(info, PpmStaffFileDto.class);
        if(null == dto) {
            return dto;
        }
        // 教育信息
        List<PpmStaffFileEdu> jyList = ppmStaffFileEduService.getBadgeForList(badge);
        // 家庭信息
        List<PpmStaffFileRelatives> jtList = ppmStaffFileRelativesService.getBadgeForList(badge);
        dto.setJyList(jyList);
        dto.setJtList(jtList);
        return dto;
    }

    @Override
    public Integer updatePpmStaffFile(PpmStaffFile ppmStaffFile) {
        return myBatisDao.executionPojo(PPMSTAFFFILE, ppmStaffFile, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmStaffFile(PpmStaffFile ppmStaffFile) {
        return myBatisDao.executionPojo(PPMSTAFFFILE, ppmStaffFile, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public String uploadImage(PpmStaffFileDto dto) {
        // 更新员工头像
        PpmStaffFile staffFile = new PpmStaffFile();
        staffFile.setBadge(dto.getBadge());
        staffFile.setImage(dto.getImage());
        this.updatePpmStaffFile(staffFile);
        return "1";
    }
}
