package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBlacklist;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBlacklistDto;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBlacklistService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 外包黑名单
  */
@Service("ppmOutsourcingBlacklistService")
public class PpmOutsourcingBlacklistService extends BaseDaoSupper implements IPpmOutsourcingBlacklistService {
    /**
      * 实体类路径
      */
    private static String PPMOUTSOURCINGBLACKLIST = "" +
            "com.work.ppm.application.model.integratedmanager.PpmOutsourcingBlacklist";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 外包商公司
     */
    @Autowired
    private IPpmOutsourcingInfoService ppmOutsourcingInfoService;


    @Override
    public void queryPager(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto, Pager pager) {
        ppmOutsourcingBlacklistDto.setIsdel(0);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingBlacklistDto,PpmOutsourcingBlacklistDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGBLACKLIST, map,pager,"queryPagerList");
    }

    @Override
    public PpmOutsourcingBlacklistDto queryDetail(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        PpmOutsourcingBlacklist ppmOutsourcingBlacklist = JsonUtil.jsonToClass(ppmOutsourcingBlacklistDto, PpmOutsourcingBlacklist.class);
        PpmOutsourcingBlacklist data = myBatisDao.selectClass(PPMOUTSOURCINGBLACKLIST, ppmOutsourcingBlacklist,
        "queryDetail");
        PpmOutsourcingBlacklistDto item = JsonUtil.jsonToClass(data, PpmOutsourcingBlacklistDto
        .class);
        return item;
    }

    @Override
    public int updatePpmOutsourcingBlacklist(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        // 标记
        int tag = 0;
        PpmOutsourcingBlacklist ppmOutsourcingBlacklist = JsonUtil.jsonToClass(ppmOutsourcingBlacklistDto, PpmOutsourcingBlacklist.class);
        // 更新
        if(StringUtil.isNotEmpty(ppmOutsourcingBlacklistDto.getId())) {
            myBatisDao.executionPojo(PPMOUTSOURCINGBLACKLIST, ppmOutsourcingBlacklist, IMyBatisDao.SQL.UPDATE,"updatePojo");
            tag = 2;
        } else  {
            long id = this.getNextSequence(PpmOutsourcingBlacklist.class);
            ppmOutsourcingBlacklist.setId(id);
           myBatisDao.executionPojo(PPMOUTSOURCINGBLACKLIST, ppmOutsourcingBlacklist, IMyBatisDao.SQL.INSTER,
                   "addPojo");
           tag = 1;
        }

        return tag;
    }

    @Override
    public int deletePpmOutsourcingBlacklist(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        // 删除
        ppmOutsourcingBlacklistDto.setIsdel(1);
        return this.updatePpmOutsourcingBlacklist(ppmOutsourcingBlacklistDto);
    }

    @Override
    public boolean importExcel(MultipartFile file, String badge,String name) {
        List<PpmOutsourcingBlacklistDto> excelList = ExcelUtil.importExcel(file, PpmOutsourcingBlacklistDto.class);
        if(null == excelList) {
            return false;
        }
        Date date = new Date();
        excelList.stream().forEach(ii -> {
            long id = this.getNextSequence(PpmOutsourcingBlacklist.class);
            ii.setIcard(ii.getIcard().trim());
            ii.setId(id);
            ii.setIsdel(0);
            ii.setAddUserId(badge);
            ii.setAddUserName(name + "(" + badge + ")");
            ii.setAddDate(date);
            String deptid = commDepartmentService.getDepNameForId(ii.getDepName());
            ii.setDepId(deptid);
            String orgCode = ppmOutsourcingInfoService.queryIdByName(ii.getSupplier());
            ii.setOrgCode(orgCode);
        });
        int rowId = myBatisDao.executionPojo(PPMOUTSOURCINGBLACKLIST, excelList, IMyBatisDao.SQL.INSTER, "batchAdd");
        return rowId > 0;
    }

    @Override
    public List<PpmOutsourcingBlacklistDto> getList(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        ppmOutsourcingBlacklistDto.setIsdel(0);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingBlacklistDto,PpmOutsourcingBlacklistDto.class);
        List<PpmOutsourcingBlacklist> list = myBatisDao.selectListT(PPMOUTSOURCINGBLACKLIST, map,"queryPagerList");
        List<PpmOutsourcingBlacklistDto> dtos = JsonUtil.jsonToList(list, PpmOutsourcingBlacklistDto.class);
        dtos.stream().forEach(ii -> {
            ii.setOfficeDateStr(DateUtil.dateToStr(ii.getOfficeDate(), "yyyy-MM-dd"));
        });
        return dtos;
    }
}
