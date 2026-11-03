package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.projectmanager.DemandFileDto;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.othermanager.PpmDocumentCatalog;
import com.work.ppm.application.model.othermanager.PpmDocumentLog;
import com.work.ppm.application.model.othermanager.ext.PpmDocumentInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.systemmanager.PpmSystParameter;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentLogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmSystParameterService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.FileProperties;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.RemoteAddrType;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 文档
 *
 */
@Slf4j
@Service("ppmDocumentService")
public class PpmDocumentService extends BaseDaoSupper implements IPpmDocumentService {
    /**
     * 实体类路径
     */
    private static String PPMDOCUMENT = "com.work.ppm.application.model.othermanager.PpmDocument";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 文件属性
     */
    @Autowired
    private FileProperties fileProperties;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 文档日志
     */
    @Autowired
    private IPpmDocumentLogService ppmDocumentLogService;
    /**
     * 通用配置
     */
    @Autowired
    private IPpmSystParameterService ppmSystParameterService;
    /**
     * 文档
     */
    @Autowired
    private IPpmDocumentCatalogService ppmDocumentCatalogService;

    @Override
    public void queryPager(PpmDocument ppmDocument, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(ppmDocument, PpmDocument.class);
        myBatisDao.selectPager(PPMDOCUMENT, map, pager, "queryPagerList");
    }

    @Override
    public PpmDocument getImageUrl(String documentno) {
        PpmDocument ppmDocument = new PpmDocument();
        ppmDocument.setDocumentno(documentno);
        PpmDocument item = myBatisDao.selectClass(PPMDOCUMENT, ppmDocument, "getImageUrl");
        // 拼接 image url
        if(item == null){
            return null;
        }
        int leangth = item.getDocumentno().length() + item.getStockpath().length();
        StringBuilder sb = new StringBuilder(leangth);
        // 获取域名
        sb.append(fileProperties.getHost());
        sb.append("/ppmtest").append(item.getStockpath());
        if (StringUtil.isStringNotEmpty(item.getTag2())) {
            sb.append(item.getTag2());
        } else if (StringUtil.isStringNotEmpty(item.getDocdomain())) {
            sb.append(item.getDocdomain());
        } else {
            sb.append(item.getDocname());
        }
        item.setDocumentno(sb.toString());
        return item;
    }

    @Override
    public Integer updatePpmDocument(PpmDocumentDto ppmDocument) {
        if (StringUtil.isNotBlank(ppmDocument.getDocumentnos())) {
            StringJoiner joiner = new StringJoiner("','", "'", "'");
            String[] docs = ppmDocument.getDocumentnos().split(",");
            for (int i = 0; i < docs.length; i++) {
                joiner.add(docs[i]);
            }
            ppmDocument.setDocumentnos(joiner.toString());
        }
        PpmDocumentInfo ppmDocumentInfo = JsonUtil.jsonToClass(ppmDocument, PpmDocumentInfo.class);
        return myBatisDao.executionPojo(PPMDOCUMENT, ppmDocumentInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deletePpmDocument(PpmDocumentDto ppmDocument) {
        AssertUtil.notNull(ppmDocument.getDocumentno(), "[documentno] 不能为空");
        ppmDocument.setIsdel(1L);
        return this.updatePpmDocument(ppmDocument);
    }

    @Override
    public String getDocmentnoSeq() {
        return myBatisDao.selectClass(PPMDOCUMENT, new HashMap<>(), "docmentnoSeq");
    }

    @Override
    public int addDocment(PpmDocument ppmDocument) {
        Long key = super.getNextSequence(PpmDocument.class);
        ppmDocument.setId(key);
        return myBatisDao.executionPojo(PPMDOCUMENT, ppmDocument, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public PpmDocumentDto authDocument(String documentno, String clientIp, String versionName,
                                       String type, String userId, String name) {
        PpmDocument doc = new PpmDocument();
        doc.setDocumentno(documentno);
        PpmDocument item = myBatisDao.selectClass(PPMDOCUMENT, doc, "getImageUrl");
        if (null == item) {
            throw new BizException(ApiCode.INTERNAL_SERVER_ERROR, "系统内部错误");
        }

        // 客户端环境
        String clientDev = eosDictEntryService.getProdEnv(clientIp);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>上传文件的ip：{}，客户端ip{}", item.getIparea(), clientIp);
        // 业务字典ip环境
        String dictDev = eosDictEntryService.getProdEnv(item.getIparea());
        log.info("客户端环境:{},务字典ip环境:{}", clientDev, dictDev);
        boolean falg = false;
        //根据文档网段类型取出可下载clientDev网段
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail("DownloadFileControll", clientDev);
        if (null != eos && null != eos.getDictname() && eos.getDictname().contains(",")) {
            String[] docAuth = eos.getDictname().split(",");
            //判断比较user网段是否具有权限
            for (int i = docAuth.length - 1; i >= 0; i--) {
                if (docAuth[i].equals(dictDev)) {
                    falg = true;
                    break;
                }
            }
        } else if (null != eos && null != eos.getDictname()) {
            if (eos.getDictname().equals(dictDev)) {
                falg = true;
            }
        }

        if (!falg) {
            log.info("文件未经授权，无法下载",new BizException());
            throw new BizException(ApiCode.FILE_NOT_AUTHORIZED, "文件未经授权，无法下载");
        }
        PpmDocumentDto documentDto = getDownloadFilePath(documentno, type, versionName);
        PpmDocumentLog log = new PpmDocumentLog();
        log.setRemark(documentDto.getDocname());
        log.setCatalogno(documentDto.getCatalogno());
        log.setDealkind("DOWNLOADDOC");
        log.setDocumentno(documentno);
        log.setUserid(userId);
        log.setUsername(name);
        ppmDocumentLogService.addPpmDocumentLog(log);
        return documentDto;
    }

    /**
     * 获取文档下载路径
     *
     * @param documentno  编号
     * @param type        类型
     * @param versionName 版本
     * @return PpmDocumentDto
     */
    @Override
    public PpmDocumentDto getDownloadFilePath(String documentno, String type, String versionName) {
        // 有权限下载
        EosDictEntry entity = eosDictEntryService.getTypeidForDetail("FileuploadRootpath", "rootPath");
        if (null == entity) {
            throw new BizException(ApiCode.INTERNAL_SERVER_ERROR, "系统内部错误");
        }
        String root = fileProperties.getUploadPath();
        PpmDocument docItem = this.getDetail(documentno);
        // 获得文件类型
        String fileSuffix = docItem.getDocname().substring(docItem.getDocname().lastIndexOf("."));
        // 文件名称
        String fileName = "";
        // 文件路径
        String filePath = "";
        String filePath1 = "";
        String filePath2 = "";
        if ("version".equals(type)) {
            fileName = docItem.getDocname().substring(0, docItem.getDocname().lastIndexOf("."));

            if (StringUtil.isNotBlank(docItem.getDocdomain())) {
                String docdomain = docItem.getDocdomain().substring(0, docItem.getDocdomain().lastIndexOf("."));
                filePath1 = "VERSION" + docItem.getStockpath() + fileName + "_(" + versionName + ")" + fileSuffix;
                filePath2 = "VERSION" + docItem.getStockpath() + docdomain + "_(" + versionName + ")" + fileSuffix;
                String realPath = root + filePath2;
                File filetest = new File(realPath);
                if (filetest.exists()) {
                    filePath = filePath2;
                } else {
                    filePath = filePath1;
                }
            } else {
                filePath = "VERSION" + docItem.getStockpath() + fileName + "_(" + versionName + ")" + fileSuffix;
            }
        } else {
            // 新增 tag2 为新应用 真实文件名称 k6991 2021-01-13
            if (StringUtil.isNotBlank(docItem.getTag2())) {
                filePath2 = "ppmtest" + docItem.getStockpath() + docItem.getTag2();
            } else {
                filePath1 = "ppmtest" + docItem.getStockpath() + docItem.getDocname();
                filePath2 = "ppmtest" + docItem.getStockpath() + docItem.getDocdomain();
            }
            String realPath = root + filePath2;
            File filetest = new File(realPath);
            if (filetest.exists()) {
                filePath = filePath2;
            } else {
                filePath = filePath1;
            }
        }

        File file = new File(root + filePath);
        log.info("=========>文件下载位置：{}",file);
        if (!file.exists()) {
            throw new BizException(ApiCode.FAIL, "文件不存在");
        }
        PpmDocumentDto documentDto = new PpmDocumentDto();
        // 完整路径
        documentDto.setTag1(root + filePath);
        // 相对路径
        documentDto.setTag2(filePath);
        documentDto.setDocname(docItem.getDocname());
        documentDto.setDocdomain(docItem.getDocdomain());
        documentDto.setCatalogno(docItem.getCatalogno());
        documentDto.setDocumentno(docItem.getDocumentno());
        return documentDto;
    }

    @Transactional("defaultTx")
    @Override
    public int addDocment(List<DemandFileDto> demandFileList, String demandNo, String userId) {
        if (null == demandFileList || demandFileList.isEmpty()) {
            return -1;
        }
        PpmDocumentCatalog catalog = ppmDocumentCatalogService.queryDetail("CATALOG2014091100026");
        for (int i = demandFileList.size() - 1; i >= 0; i--) {
            DemandFileDto item = demandFileList.get(i);
            String root = eosDictEntryService.getTypeidForDetail
                    ("FileuploadRootpath", "rootPath").getDictname();
            String uploadPath = root + "/ppmtest" + catalog.getStockpath();
            // 判断科管的本地路径是否存在
            File newfile = new File(uploadPath, item.getFileRealName());
            File folder = new File(uploadPath);
            if (!(folder.exists())) {
                folder.mkdirs();
            }
            // 判断奋进号路径是否存在
            String uploadPathTmp = root + "/ppmtest/ppmdemand/";
            File folderTmp = new File(uploadPathTmp);
            if (!(folderTmp.exists())) {
                folderTmp.mkdirs();
            }
            // 判断科管文件路径是否存在
            String sourceFile = uploadPathTmp + item.getFileRealName();
            File infile = new File(sourceFile);
            try {
                FileUtils.copyFile(infile, newfile);
            } catch (IOException e) {
                log.error("【新应用】奋进号文件拷贝到科管异常：{}", e);
            }

            PpmDocument document = new PpmDocument();
            //文档类型
            String type = PpmDocument.DocmentEnum.getName(item.getType()) + "";
            document.setObjectno(demandNo);
            document.setObjecttype(type);
            document.setStockpath(catalog.getStockpath());
            document.setDocname(item.getFileName());
            document.setTag2(item.getFileRealName());
            document.setCatalogno("CATALOG2014091100026");
            document.setCreateorid(userId);
            String name = UsersUtil.getCommEmployeeInfo(userId).getEmpname();
            document.setCreators(name);
            document.setCreatetime(Calendar.getInstance().getTime());
            document.setLastmodtime(Calendar.getInstance().getTime());
            document.setDocumentno(this.getDocmentnoSeq());
            document.setDocdomain(item.getFileRealName());
            this.addDocment(document);
            // 文档日志
            PpmDocumentLog log = new PpmDocumentLog();
            log.setCatalogno(document.getCatalogno());
            log.setDocumentno(document.getDocumentno());
            log.setDealkind("SAVEDOC");
            log.setOprdate(new Date());
            log.setUserid("work");
            log.setUsername("work");
            log.setRemark(document.getDocname());
            ppmDocumentLogService.addPpmDocumentLog(log);
        }
        return 1;
    }

    @Override
    public List<PpmDocumentDto> getList(String objectNo) {
        if (!StringUtil.isNotBlank(objectNo)) {
            return null;
        }
        PpmDocument document = new PpmDocument();
        document.setObjectno(objectNo);
        document.setIsdel(0L);
        List<PpmDocument> list = myBatisDao.selectListT(PPMDOCUMENT, document, "getList");
        List<PpmDocumentDto> documentDtos = JsonUtil.jsonToList(list, PpmDocumentDto.class);
        if (null == list || list.isEmpty()) {
            return null;
        }
        documentDtos.stream().forEach(ii -> {
            PpmDocumentDto dto = this.getDownloadFilePath(ii.getDocumentno(), null, null);
            ii.setTag1(dto.getTag1());
//            ii.setTag2(dto.getTag2());
        });
        return documentDtos;
    }

    @Override
    public PpmDocument getDetail(String documentno) {
        PpmDocument entity = new PpmDocument();
        entity.setDocumentno(documentno);
        return myBatisDao.selectClass(PPMDOCUMENT, entity, "getImageUrl");
    }

    @Override
    public PpmDocumentDto getDetail(CharSequence objectno) {
        PpmDocument entity = new PpmDocument();
        entity.setObjectno(objectno + "");
        PpmDocument item = myBatisDao.selectClass(PPMDOCUMENT, entity, "getDetail");
        PpmDocumentDto ditem = JsonUtil.jsonToClass(item, PpmDocumentDto.class);
        return ditem;
    }

    @Override
    public PpmDocument getDetail(String stockpath, String docname) {
        PpmDocument entity = new PpmDocument();
        entity.setStockpath(stockpath);
        entity.setDocname(docname);
        return myBatisDao.selectClass(PPMDOCUMENT, entity, "getList");
    }

    /**
     * 根据传入的ip数据获取网段信息
     *
     * @param list 集合
     * @param ip   ip
     * @return String
     */
    private String getInternetDev(List<EosDictEntry> list, String ip) {
        String dev = "other";
        for (int i = list.size() - 1; i >= 0; i--) {
            if (RemoteAddrType.analysisIp(ip, list.get(i).getDictname())) {
                dev = list.get(i).getDictid();
                break;
            }
        }
        return dev;
    }

    @Override
    public List<PpmDocument> queryDocumentsInfos(Map param) {
        List<PpmDocument> dto = myBatisDao.selectListT(PPMDOCUMENT, param, "getDocumentInfos");
        return dto;
    }

    @Override
    public PpmDocument getPreview(String documentno) {
        EosDictEntry entity = eosDictEntryService.getTypeidForDetail("FileuploadRootpath", "rootPath");
        if (null == entity) {
            throw new BizException(ApiCode.INTERNAL_SERVER_ERROR, "系统内部错误");
        }
        String fileName = "";
        PpmDocument docItem = this.getDetail(documentno);
        if (null == docItem) {
            return null;
        }
        String filePath2 = "/ppmtest" + docItem.getStockpath() + docItem.getDocdomain();
        File filetest = new File(filePath2);
        if (filetest.exists()) {
            fileName = docItem.getDocdomain();
        } else {
            fileName = docItem.getDocname();
        }
        String fileURL = fileProperties.getPreview() + "/ppmtest" + docItem.getStockpath() + fileName;
        docItem.setTag1(fileURL);
        return docItem;
    }

    @Override
    public PpmDocumentDto getDownloadUrl(PpmDocumentDto ppmDocumentDto) {

        PpmSystParameter item = ppmSystParameterService.queryDetail(ppmDocumentDto.getKey());
        String stockpath = "/sysmanage/download/";
        PpmDocument doc = this.getDetail(stockpath, item.getValue());
        PpmDocumentDto documentDto = JsonUtil.jsonToClass(doc, PpmDocumentDto.class);
//        PpmDocumentDto documentDto = ppmDocumentService.authDocument(doc.getDocumentno(),
// ppmDocumentDto.getIp(),null, null,ppmDocumentDto.getUserId(), ppmDocumentDto.getName());
        return documentDto;
    }

    @Override
    public String getFileDetail(PpmDocumentDto documentno) {
        String path = "";
        String realName = "";
        String pathRealName = "";
        PpmDocument doc = myBatisDao.selectClass(PPMDOCUMENT, documentno, "getImageUrl");
        if (StringUtil.isNotEmpty(doc)) {
            if (doc.getTag2() != null
                    && !"".equals(doc.getTag2())) {
                path = path + doc.getStockpath()
                        + doc.getTag2() + "|";
            } else {
                path = path + doc.getStockpath()
                        + doc.getDocname() + "|";
            }
            //真实文件名
            realName = realName + doc.getDocname();
            if (path.length() > 0) {
                path = path.substring(0, path.length() - 1);
                pathRealName = path + "@split@" + realName;
            }
        }
        return pathRealName;
    }
}
