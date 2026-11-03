package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmDeptdistributionReport;
import com.work.ppm.application.dto.integratedmanager.PpmDeptdistributionReportDto;
import com.work.ppm.application.services.integratedmanager.IPpmDeptdistributionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 入职部门分配报表
 */
@Service("ppmDeptdistributionReportService")
public class PpmDeptdistributionReportService extends BaseDaoSupper implements IPpmDeptdistributionReportService {
    /**
      * 实体类路径
      */
    private static String PPMDEPTDISTRIBUTIONREPORT =
            "com.work.ppm.application.model.integratedmanager.PpmDeptdistributionReport";
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


    @Override
    public void queryPager(PpmDeptdistributionReportDto ppmDeptdistributionReportDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDeptdistributionReportDto,PpmDeptdistributionReportDto.class);
        myBatisDao.selectPager(PPMDEPTDISTRIBUTIONREPORT, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmDeptdistributionReport(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) {
        PpmDeptdistributionReport ppmDeptdistributionReport = JsonUtil.jsonToClass(ppmDeptdistributionReportDto, PpmDeptdistributionReport.class);
        Long id = getNextSequence(PpmDeptdistributionReport.class);

        // 如果需要请set 主键id
        //ppmDeptdistributionReport.setId(id.intValue());
        myBatisDao.executionPojo(PPMDEPTDISTRIBUTIONREPORT, ppmDeptdistributionReport, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDeptdistributionReportDto queryDetail(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) {
        PpmDeptdistributionReport ppmDeptdistributionReport = JsonUtil.jsonToClass(ppmDeptdistributionReportDto, PpmDeptdistributionReport.class);
        PpmDeptdistributionReport data = myBatisDao.selectClass(PPMDEPTDISTRIBUTIONREPORT, ppmDeptdistributionReport,
        "queryDetail");
        PpmDeptdistributionReportDto item = JsonUtil.jsonToClass(data, PpmDeptdistributionReportDto
        .class);
        return item;
    }

    @Override
    public int updatePpmDeptdistributionReport(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) {
        PpmDeptdistributionReport ppmDeptdistributionReport = JsonUtil.jsonToClass(ppmDeptdistributionReportDto, PpmDeptdistributionReport.class);
        return myBatisDao.executionPojo(PPMDEPTDISTRIBUTIONREPORT, ppmDeptdistributionReport, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmDeptdistributionReport(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) {
        PpmDeptdistributionReport ppmDeptdistributionReport = JsonUtil.jsonToClass(ppmDeptdistributionReportDto, PpmDeptdistributionReport.class);
        return myBatisDao.executionPojo(PPMDEPTDISTRIBUTIONREPORT, ppmDeptdistributionReport, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public boolean importExcel(String batchNo, MultipartFile file) {
        List<PpmDeptdistributionReportDto> list = ExcelUtil.importExcel(file, PpmDeptdistributionReportDto.class);
        if(null == list) {
            return false;
        }
        list.stream().forEach(i -> {
            Long id = super.getNextSequence(PpmDeptdistributionReport.class);
            i.setId(id);
            i.setBatch(batchNo);
            // 根据部门名称 查询 部门id
            String depId = commDepartmentService.getDepNameForId(i.getDepName());
            i.setDepId(depId);
        });
       int id =  myBatisDao.executionPojo(PPMDEPTDISTRIBUTIONREPORT, list, IMyBatisDao.SQL.INSTER,"batchAdd");
        return id > 0;
    }
}
