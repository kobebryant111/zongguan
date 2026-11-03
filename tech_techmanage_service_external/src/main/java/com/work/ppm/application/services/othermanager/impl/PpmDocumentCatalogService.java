package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.dto.projectmanager.DemandFileDto;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.othermanager.PpmDocumentLog;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.othermanager.IPpmDocumentLogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.othermanager.PpmDocumentCatalog;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 文档目录
 */
@Service("ppmDocumentCatalogService")
@Slf4j
public class PpmDocumentCatalogService implements IPpmDocumentCatalogService {
    /**
     * 实体类路径
     */
    private static String PPMDOCUMENTCATALOG = "com.work.ppm.application.model.othermanager.PpmDocumentCatalog";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 业务字典接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;

    /**
     * 文档日志接口
     */
    @Autowired
    private IPpmDocumentLogService ppmDocumentLogService;

    /**
     * 员工服务类
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Override
    public void queryPager(PpmDocumentCatalog ppmDocumentCatalog, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmDocumentCatalog,PpmDocumentCatalog.class);
        myBatisDao.selectPager(PPMDOCUMENTCATALOG, map,pager,"queryPagerList");
    }

    @Override
    public PpmDocumentCatalog queryDetail(String catalogNo) {
        PpmDocumentCatalog ppmDocumentCatalog = new PpmDocumentCatalog();
        ppmDocumentCatalog.setCatalogno(catalogNo);
        return myBatisDao.selectClass(PPMDOCUMENTCATALOG, ppmDocumentCatalog,"queryDetail");
    }

    @Override
    public Integer updatePpmDocumentCatalog(PpmDocumentCatalog ppmDocumentCatalog) {
        return myBatisDao.executionPojo(PPMDOCUMENTCATALOG, ppmDocumentCatalog, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmDocumentCatalog(PpmDocumentCatalog ppmDocumentCatalog) {
        return myBatisDao.executionPojo(PPMDOCUMENTCATALOG, ppmDocumentCatalog, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public void saveDoucment(DemandFileDto demandFileDto, String demandno, String userid) {
        PpmDocumentCatalog ppmDocumentCatalog = this.queryDetail("CATALOG2014091100035");

        String fileName = demandFileDto.getFileRealName();

        EosDictEntry root = eosDictEntryService.getTypeidForDetail("FileuploadRootpath", "rootPath");
        String uploadPath = root.getDictname() + "/ppmtest" + ppmDocumentCatalog.getStockpath();
        File newfile = new File(uploadPath, fileName);
        File folder = new File(uploadPath);
        if (!(folder.exists())) {
            folder.mkdirs();
        }

        String uploadPathTmp = root.getDictname() + "/ppmtest/ppmdemand/";

        File folderTmp = new File(uploadPathTmp);
        if (!(folderTmp.exists())) {
            folderTmp.mkdirs();
        }
        try
        {
            String sourceFile = uploadPathTmp + fileName;
            File infile = new File(sourceFile);
            FileUtils.copyFile(infile, newfile);
            PpmDocument document = new PpmDocument();
            document.setObjectno(demandno);


            if("安全测试".equals(demandFileDto.getType())){
                document.setObjecttype("A21");
            }else{
                document.setObjecttype("A10");
            }

            document.setStockpath(ppmDocumentCatalog.getStockpath());
            document.setDocname(demandFileDto.getFileName());
            document.setCatalogno("CATALOG2014091100026");
            document.setCreateorid(userid);
            CommEmployee employee = commEmployeeService.getCommEmployeeInfo(userid);
            document.setCreators(employee.getEmpname());
            document.setCreatetime(Calendar.getInstance().getTime());
            document.setLastmodtime(Calendar.getInstance().getTime());
            document.setDocumentno(ppmDocumentService.getDocmentnoSeq());
            document.setDocdomain(demandFileDto.getFileRealName());
            document.setTag2(demandFileDto.getFileRealName());
            ppmDocumentService.addDocment(document);

            PpmDocumentLog documentLog = new PpmDocumentLog();
            documentLog.setCatalogno(document.getCatalogno());
            documentLog.setDocumentno(document.getDocumentno());
            documentLog.setDealkind("SAVEDOC");
            documentLog.setOprdate(new Date());
            documentLog.setUserid("work");
            documentLog.setUsername("work");
            documentLog.setRemark(document.getDocname());
            ppmDocumentLogService.addPpmDocumentLog(documentLog);

        }
        catch (Throwable e)
        {
            log.info(e.getMessage(), e);
        }
    }
}
