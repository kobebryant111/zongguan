package com.work.ppm.application.services.suppliermanager.impl;

import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncDto;
import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncFileDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.enums.DataIsDelEnums;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.othermanager.PpmDocumentCatalog;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.suppliermanager.PpmSuppliersSync;
import com.work.ppm.application.model.suppliermanager.PpmSuppliersSyncFile;
import com.work.ppm.application.model.systemmanager.PpmProjectInfo;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncFileService;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
  *  同步采购数据 - 项目供应商信息表 接口实现类
  */
@Slf4j
@Service("ppmSuppliersSyncService")
public class PpmSuppliersSyncService extends BaseDaoSupper
        implements IPpmSuppliersSyncService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERSSYNC = "com.work.ppm.application.model.suppliermanager.PpmSuppliersSync";

    @Value("${file.oss.authorization}")
    private String ossAuthorization;
    @Value("${file.oss.url}")
    private String ossUrl;
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmDocumentCatalogService ppmDocumentCatalogService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IPpmSuppliersSyncFileService ppmSuppliersSyncFileService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmOutsourcingInfoService ppmOutsourcingInfoService;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    private String errorMsg;
    private void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }

    private String getErrorMsg(){
        return this.errorMsg;
    }

    @Override
    public int addPpmSuppliersSync(PpmSuppliersSyncDto ppmSuppliersSyncDto) {
        PpmSuppliersSync ppmSuppliersSync = JsonUtil.jsonToClass(ppmSuppliersSyncDto, PpmSuppliersSync.class);
//         如果需要请set 主键id
        ppmSuppliersSync.setId(getNextSequence(PpmSuppliersSync.class));
        ppmSuppliersSync.setSyncTime(new Date());
        return myBatisDao.executionPojo(PPMSUPPLIERSSYNC, ppmSuppliersSync, IMyBatisDao.SQL.INSTER, "addPojo");
    }
    @Override
    public PpmSuppliersSyncDto queryDetail(PpmSuppliersSyncDto ppmSuppliersSyncDto) {
        PpmSuppliersSync ppmSuppliersSync = JsonUtil.jsonToClass(ppmSuppliersSyncDto, PpmSuppliersSync.class);
        PpmSuppliersSync data = myBatisDao.selectClass(PPMSUPPLIERSSYNC, ppmSuppliersSync,"queryDetail");
        return JsonUtil.jsonToClass(data, PpmSuppliersSyncDto.class);
    }

    @Override
    public List<PpmSuppliersSyncDto> queryDetailList(PpmSuppliersSyncDto ppmSuppliersSyncDto,String uniqueName) {
        PpmSuppliersSync ppmSuppliersSync = JsonUtil.jsonToClass(ppmSuppliersSyncDto, PpmSuppliersSync.class);
        List<PpmSuppliersSync> data = myBatisDao.selectListT(PPMSUPPLIERSSYNC, ppmSuppliersSync,uniqueName);
        return JsonUtil.jsonToList(data,PpmSuppliersSyncDto.class);
    }

    @Override
    public int updatePpmSuppliersSync(PpmSuppliersSyncDto ppmSuppliersSyncDto) {
        PpmSuppliersSync ppmSuppliersSync = JsonUtil.jsonToClass(ppmSuppliersSyncDto, PpmSuppliersSync.class);
        ppmSuppliersSyncDto.setSyncTime(new Date());
        return myBatisDao.executionPojo(PPMSUPPLIERSSYNC, ppmSuppliersSync, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Map<String,Object> syncSupplierData(PpmSuppliersSyncDto ppmSuppliersSyncDto){
        //定义返回参数集合
        Map<String,Object> map = new HashMap<>();

        //校验
        if(!vaildCommitData(ppmSuppliersSyncDto)){
            map.put("errorCode","999999");
            map.put("errorMsg",getErrorMsg());
            return map;
        }

        PpmSuppliersSyncDto suppliersSyncDto = new PpmSuppliersSyncDto();
        suppliersSyncDto.setXmbh(ppmSuppliersSyncDto.getXmbh());
        suppliersSyncDto = queryDetail(suppliersSyncDto);

        //存储数据
        try{
            int i = 0;
            if(suppliersSyncDto != null){
                ppmSuppliersSyncDto.setId(suppliersSyncDto.getId());
                i = updatePpmSuppliersSync(ppmSuppliersSyncDto);
            }else {
                i = addPpmSuppliersSync(ppmSuppliersSyncDto);
            }
            if(i > 0){
                //处理对象存储id
                String array = ppmSuppliersSyncDto.getDxccid();
                if(StringUtils.isNotEmpty(array)){
                    String [] objectIds = array.split(BusinessUtil.REGEX_COMMA_UN);

                    PpmSuppliersSyncFileDto suppliersSyncFileDto = new PpmSuppliersSyncFileDto();
                    suppliersSyncFileDto.setXmbh(ppmSuppliersSyncDto.getYzbm());
                    suppliersSyncFileDto.setXmid(ppmSuppliersSyncDto.getXmbh());
                    ppmSuppliersSyncFileService.updatePpmSuppliersSyncFile(suppliersSyncFileDto, "updatePojoIsDel");

                    for(String objectId : objectIds){
                        saveDocumentLog(objectId,"CATALOG2023023301002",
                                ppmSuppliersSyncDto.getYzbm(),ppmSuppliersSyncDto.getXmbh());
                    }
                }

                //供应商名称

                //采购系统同步信息后，需要将采购供应商id存储到对应的供应商信息里，若是一个新的供应商，则新建一笔供应商信息数据
                PpmOutsourcingInfo outsourcingInfo = new PpmOutsourcingInfo();
                outsourcingInfo.setOutsourcname(ppmSuppliersSyncDto.getGsmc());
                outsourcingInfo.setIsdel(0L);
                outsourcingInfo = ppmOutsourcingInfoService.queryDetail(outsourcingInfo);
                //如果供应商已存在，让用户去修改对应的供应商信息
                if(outsourcingInfo != null ){
                    PpmOutsourcingInfo ppmOutsourcingInfo = new PpmOutsourcingInfo();
                    ppmOutsourcingInfo.setId(outsourcingInfo.getId());
                    ppmOutsourcingInfo.setCgSupplierId(ppmSuppliersSyncDto.getGysbh());
                    ppmOutsourcingInfoService.updatePpmOutsourcingInfo(ppmOutsourcingInfo);
                }else {
                    CommOperatorDoLoginDto user = new CommOperatorDoLoginDto();
                    user.setUserid("sysadmin-cg");

                    PpmOutsourcingInfoDto ppmOutsourcingInfoDto = new PpmOutsourcingInfoDto();
                    ppmOutsourcingInfoDto.setOutsourcname(ppmSuppliersSyncDto.getGsmc());
                    ppmOutsourcingInfoDto.setCgSupplierId(ppmSuppliersSyncDto.getGysbh());
                    ppmOutsourcingInfoService.addSupplierRoster(ppmOutsourcingInfoDto,user);
                }

            }
        }catch (Exception e){
            log.error(e.toString());
            map.put("errorCode","999999");
            map.put("errorMsg","失败");
            return map;
        }

        map.put("errorCode","000000");
        map.put("errorMsg","成功");
        return map;
    }

    /**
     * 存储文本信息
     * @param objectId 文件
     * @param catalogNo 文件编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDocumentLog (String objectId,String catalogNo,String xmbh,String xmid){
        Date date = new Date();
        // 文件别名
        String aliasName = DateUtil.dateToStr(date, "yyyyMMddHHmmss");
        // 文件后缀
        String suffix = objectId.substring(objectId.lastIndexOf("."));

        if (objectId.contains("\\")) {
            objectId = objectId.substring(objectId.lastIndexOf("\\") + 1);
        }
        // 新文件
        String newName = aliasName + suffix;

        // 获取上传路径
        PpmDocumentCatalog catalog = ppmDocumentCatalogService.queryDetail(catalogNo);

        //上传路径
        String uploadPathAll = uploadPath + "ppmtest" + catalog.getStockpath() + newName;
        boolean flag = FileUtil.downloadObject(ossUrl,ossAuthorization,objectId,uploadPathAll);
        //存储成功的数据添加对应记录
        if(flag){
            // 添加记录
            PpmDocument document = new PpmDocument();
            document.setCatalogno(catalogNo);
            document.setObjectno(null);
            document.setObjecttype("RLWBXQ");
            document.setStockpath(catalog.getStockpath());
            document.setDocname(objectId);
            document.setTag2(newName);  // 真实上传文件名称
            document.setCreateorid("sysadmin");
            document.setCreators("sysadmin");
            document.setCreatetime(date);
            document.setLastmodtime(date);
            String docNo = ppmDocumentService.getDocmentnoSeq();
            document.setDocumentno(docNo);
            document.setIparea("");
            document.setLastmoduser(null);
            ppmDocumentService.addDocment(document);

            PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto = new PpmSuppliersSyncFileDto();
            ppmSuppliersSyncFileDto.setFileNo(docNo);
            ppmSuppliersSyncFileDto.setId(getNextSequence(PpmSuppliersSyncFile.class));
            ppmSuppliersSyncFileDto.setFileName(objectId);
            ppmSuppliersSyncFileDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
            ppmSuppliersSyncFileDto.setObjectId(objectId);
            ppmSuppliersSyncFileDto.setXmbh(xmbh);
            ppmSuppliersSyncFileDto.setXmid(xmid);
            ppmSuppliersSyncFileDto.setCreatedTime(new Date());
            ppmSuppliersSyncFileService.addPpmSuppliersSyncFile(ppmSuppliersSyncFileDto);
        }
    }

    /**
     * 校验字段
     * @param ppmSuppliersSyncDto 对象
     */
    private boolean vaildCommitData(PpmSuppliersSyncDto ppmSuppliersSyncDto){
        boolean flag = true;
        StringJoiner retMsg = new StringJoiner(",");

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getXmbh())){
            retMsg.add("xmbh");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getHtbh())){
            retMsg.add("htbh");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getHtmc())){
            retMsg.add("htmc");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getXmmc())){
            retMsg.add("xmmc");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getJfrq())){
            retMsg.add("jfrq");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getYzbm())){
            retMsg.add("yzbm");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getGysbh())){
            retMsg.add("gysbh");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getGsmc())){
            retMsg.add("gsmc");
            flag = false;
        }

        if(StringUtils.isEmpty(ppmSuppliersSyncDto.getHtid())){
            retMsg.add("htid");
            flag = false;
        }

        if(!flag){
            setErrorMsg(retMsg + "不可为空.");
        }

        return flag;
    }


    @Override
    public List<PpmSuppliersSyncDto> queryProjectList(PpmSuppliersSyncDto ppmSuppliersSyncDto) {
        List<PpmSuppliersSyncDto> items = queryDetailList(ppmSuppliersSyncDto,"getProjectList");

        List<PpmSuppliersSyncDto> list = new ArrayList<>();
        items.forEach(item -> {
            PpmProjectInfoDto ppmProjectInfoDto = new PpmProjectInfoDto();
            ppmProjectInfoDto.setProjectNumber(item.getYzbm());
            List<PpmProjectInfo> projectInfoDtos = ppmProjectInfoService.queryProject(ppmProjectInfoDto);
            if(projectInfoDtos != null && !projectInfoDtos.isEmpty()){
                return;
            }

            item.setProjectSource("01");

            //获取对应的附件信息
            PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto = new PpmSuppliersSyncFileDto();
            ppmSuppliersSyncFileDto.setXmbh(item.getYzbm());
            ppmSuppliersSyncFileDto.setXmid(item.getXmbh());
            ppmSuppliersSyncFileDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
            List<PpmSuppliersSyncFileDto> suppliersSyncFileDtos =
                    ppmSuppliersSyncFileService.queryDetailList(ppmSuppliersSyncFileDto);
            List<String> supplierSyncFiles = new ArrayList<>();
            suppliersSyncFileDtos.forEach(dto ->{
                supplierSyncFiles.add(dto.getFileNo());
            });
            item.setSupplierSyncFiles(supplierSyncFiles);
            list.add(item);
        });
        return list;
    }

    @Override
    public List<PpmSuppliersSyncDto> queryProjectListUpdate(PpmSuppliersSyncDto ppmSuppliersSyncDto) {

        List<PpmSuppliersSyncDto> items = queryDetailList(ppmSuppliersSyncDto,"getProjectList");

        List<PpmSuppliersSyncDto> list = new ArrayList<>();
        items.forEach(item -> {
            item.setProjectSource("01");

            //获取对应的附件信息
            PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto = new PpmSuppliersSyncFileDto();
            ppmSuppliersSyncFileDto.setXmbh(item.getYzbm());
            ppmSuppliersSyncFileDto.setXmid(item.getXmbh());
            ppmSuppliersSyncFileDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
            List<PpmSuppliersSyncFileDto> suppliersSyncFileDtos =
                    ppmSuppliersSyncFileService.queryDetailList(ppmSuppliersSyncFileDto);
            List<String> supplierSyncFiles = new ArrayList<>();
            suppliersSyncFileDtos.forEach(dto ->{
                supplierSyncFiles.add(dto.getFileNo());
            });
            item.setSupplierSyncFiles(supplierSyncFiles);
            list.add(item);
        });
        return list;
    }


}
