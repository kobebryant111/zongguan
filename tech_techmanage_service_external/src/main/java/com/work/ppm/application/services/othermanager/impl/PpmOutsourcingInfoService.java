package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingInfoPriceDto;
import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;
import com.work.ppm.application.enums.DataIsDelEnums;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingInfoPrice;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.othermanager.ext.PpmOutsourcingInfoExt;
import com.work.ppm.application.model.suppliermanager.PpmSupplierRosterInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingInfoPriceService;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRosterInfoService;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.application.services.systemmanager.IPpmSystemInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 外包商公司
 */
@Service("ppmOutsourcingInfoService")
@Slf4j
public class PpmOutsourcingInfoService extends BaseDaoSupper implements IPpmOutsourcingInfoService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGINFO = "com.work.ppm.application.model.othermanager.PpmOutsourcingInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmSupplierRosterInfoService supplierRosterInfoService;

    @Autowired
    private IPpmOutsourcingInfoPriceService ppmOutsourcingInfoPriceService;

    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    @Autowired
    private IPpmSystemInfoService ppmSystemInfoService;

    @Autowired
    private IPpmSuppliersSyncService ppmSuppliersSyncService;


    @Override
    public void queryPager(PpmOutsourcingInfo ppmOutsourcingInfo, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmOutsourcingInfo,PpmOutsourcingInfo.class);
        myBatisDao.selectPager(PPMOUTSOURCINGINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) {
//        Long key = this.getNextSequence(PpmOutsourcingInfo.class);
        // 如果需要请set 主键id
        //ppmFlowLog.setId(key);
        myBatisDao.executionPojo(PPMOUTSOURCINGINFO, ppmOutsourcingInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingInfo queryDetail(PpmOutsourcingInfo ppmOutsourcingInfo) {
        return myBatisDao.selectClass(PPMOUTSOURCINGINFO, ppmOutsourcingInfo,"queryDetail");
    }

    @Override
    public List<PpmOutsourcingInfoDto> queryDetailList(PpmOutsourcingInfoDto ppmOutsourcingInfoDto,String uniqueName) {
        PpmOutsourcingInfo ppmOutsourcingInfo = JsonUtil.jsonToClass(ppmOutsourcingInfoDto,PpmOutsourcingInfo.class);
        List<PpmOutsourcingInfo> outsourcingInfos =
                myBatisDao.selectListT(PPMOUTSOURCINGINFO, ppmOutsourcingInfo,uniqueName);
        return JsonUtil.jsonToList(outsourcingInfos,PpmOutsourcingInfoDto.class);
    }

    @Override
    public PpmOutsourcingInfo queryDetailByOrgCode(PpmOutsourcingInfo ppmOutsourcingInfo) {
        return myBatisDao.selectClass(PPMOUTSOURCINGINFO, ppmOutsourcingInfo,"queryDetailByOrgCode");
    }

    @Override
    public Integer updatePpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGINFO, ppmOutsourcingInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGINFO, ppmOutsourcingInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmOutsourcingInfo> getListAll() {
        Map<String,Object> map = new HashMap<>();
        map.put("isdel", "0");
        return myBatisDao.selectListT(PPMOUTSOURCINGINFO, map, "getList");
    }

    @Override
    public List<PpmOutsourcingInfo> getListByNos(String orgnos) {
        Map<String,Object> map = new HashMap<>();
        map.put("isdel", "0");
        String[] s=null;
        String str="";
        if(orgnos.contains(",")){
            s=orgnos.split(",");
            for(int i=0;i<s.length;i++){
                if(i==0){
                    str="'"+s[i]+"'";
                }else{
                    str=str+","+"'"+s[i]+"'";
                }
            }
        }else{
            str="'"+orgnos+"'";
        }
        map.put("orgnos", str);
        return myBatisDao.selectListT(PPMOUTSOURCINGINFO, map, "getList");
    }

    @Override
    public String queryIdByName(String contactsName) {
        PpmOutsourcingInfo info = new PpmOutsourcingInfo();
        info.setContactsname(contactsName);
        PpmOutsourcingInfo item = myBatisDao.selectClass(PPMOUTSOURCINGINFO, info, "queryIdByName");
        if(null != item) {
            return item.getOrgcode();
        }
        return null;
    }

    @Override
    public String queryNameByOrgCode(String orgCode) {
        PpmOutsourcingInfo info = new PpmOutsourcingInfo();
        info.setOrgcode(orgCode);
        PpmOutsourcingInfo item = myBatisDao.selectClass(PPMOUTSOURCINGINFO, info, "queryNameById");
        if(null != item) {
            return item.getOutsourcname();
        }
        return null;
    }

    @Override
    public PpmOutsourcingInfoDto queryNextOrgCode(PpmOutsourcingInfoDto ppmOutsourcingInfoDto) {
        PpmOutsourcingInfoExt ppmOutsourcingInfoExt =
                JsonUtil.jsonToClass(ppmOutsourcingInfoDto, PpmOutsourcingInfoExt.class);
        PpmOutsourcingInfoExt item =
                myBatisDao.selectClass(PPMOUTSOURCINGINFO, ppmOutsourcingInfoExt, "queryNextOrgCode");
        return JsonUtil.jsonToClass(item, PpmOutsourcingInfoDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto, CommOperatorDoLoginDto user) {

        //新增一行检验
        vaildCommitData(ppmOutsourcingInfoDto);

        String nextOrgCode = createNextOrgCode();
        if(StringUtils.isEmpty(nextOrgCode)){
            throw new BizException(ApiCode.FAIL,"编码存在问题，");
        }

        //存储供应商信息
        Long key = -getNextSequence(PpmOutsourcingInfo.class);
        ppmOutsourcingInfoDto.setOrgcode(nextOrgCode);
        ppmOutsourcingInfoDto.setIsdel(0L);
        ppmOutsourcingInfoDto.setId(key);
        if(user != null){
            ppmOutsourcingInfoDto.setCreators(user.getUserid());
            ppmOutsourcingInfoDto.setCreatetime(new Date());
        }

        //查询采购系统传输的供应商编号
        PpmSuppliersSyncDto ppmSuppliersSyncDto = new PpmSuppliersSyncDto();
        ppmSuppliersSyncDto.setGsmc(ppmOutsourcingInfoDto.getOutsourcname());
        List<PpmSuppliersSyncDto> ppmSuppliersSyncDtos =
                ppmSuppliersSyncService.queryDetailList(ppmSuppliersSyncDto, XmlIdEnums.QUERYS.getCode());
        if (ppmSuppliersSyncDtos != null && !ppmSuppliersSyncDtos.isEmpty()){
            ppmOutsourcingInfoDto.setCgSupplierId(ppmSuppliersSyncDtos.get(0).getGysbh());
        }

        PpmOutsourcingInfo ppmOutsourcingInfo = JsonUtil.jsonToClass(ppmOutsourcingInfoDto,PpmOutsourcingInfo.class);
        addPpmOutsourcingInfo(ppmOutsourcingInfo);

        //存储供应商详情信息
        List<PpmSupplierRosterInfoDto> rosterInfoDtos = ppmOutsourcingInfoDto.getSupplierRosterInfoDtos();
        if(rosterInfoDtos != null && !rosterInfoDtos.isEmpty()){
            rosterInfoDtos.forEach(infoDto ->{
                infoDto.setDeptId(infoDto.getDeptId().replace("dep",""));
                infoDto.setServiceType(
                        BusinessUtil.getStringArray(infoDto.getServiceTypeArray(),BusinessUtil.REGEX_LINE));
                infoDto.setSupplierName(ppmOutsourcingInfoDto.getOutsourcname());
                infoDto.setPid(key);
                infoDto.setSupplierCode(nextOrgCode);
                infoDto.setId(getNextSequence(PpmSupplierRosterInfo.class));
                supplierRosterInfoService.addPpmSupplierRosterInfo(infoDto);
            });
        }

        //增加价格表
        List<PpmOutsourcingInfoPriceDto> priceDtos = ppmOutsourcingInfoDto.getOutsourcingInfoPriceDtos();
        if(priceDtos != null && !priceDtos.isEmpty()){
            priceDtos.forEach(infoDto ->{
                if(StringUtils.isEmpty(infoDto.getPosition())){
                    return;
                }

                infoDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
                infoDto.setOrgcode(nextOrgCode);
                infoDto.setPosition(infoDto.getPosition());
                infoDto.setId(getNextSequence(PpmOutsourcingInfoPrice.class));
                if(user != null){
                    infoDto.setCreateusrid(user.getUserid());
                    infoDto.setCreatetime(new Date());
                }
                PpmOutsourcingInfoPrice infoPrice = JsonUtil.jsonToClass(infoDto,PpmOutsourcingInfoPrice.class);
                ppmOutsourcingInfoPriceService.addPpmOutsourcingInfoPrice(infoPrice);
            });
        }

    }

    /**
     * 获取供应商编码
     * @return string 供应商编码
     */
    public String createNextOrgCode(){
        String orgCodeVal = "ORGCODE" + DateUtil.dateToNoBar(new Date()) + "5";
        PpmOutsourcingInfoDto outsourcingInfoDto = new PpmOutsourcingInfoDto();
        outsourcingInfoDto.setOrgCodeVal("'"+orgCodeVal + "%'");
        PpmOutsourcingInfoDto ppmOutsourcingInfo = queryNextOrgCode(outsourcingInfoDto);
        if(ppmOutsourcingInfo != null){
            String val = ppmOutsourcingInfo.getOrgcode().replace(orgCodeVal,"");
            Long valLong = Long.parseLong(val) + 1 ;
            String str = String.format("%04d",valLong);
            return orgCodeVal + str;
        }else{
            String str = String.format("%04d",1);
            return orgCodeVal + str;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto, CommOperatorDoLoginDto user) {

        //新增一行检验
        vaildCommitData(ppmOutsourcingInfoDto);

        PpmOutsourcingInfo ppmOutsourcingInfo = JsonUtil.jsonToClass(ppmOutsourcingInfoDto,PpmOutsourcingInfo.class);
        updatePpmOutsourcingInfo(ppmOutsourcingInfo);

        //存储供应商详情信息
        List<PpmSupplierRosterInfoDto> rosterInfoDtos = ppmOutsourcingInfoDto.getSupplierRosterInfoDtos();
        if(rosterInfoDtos != null && !rosterInfoDtos.isEmpty()){
            //删掉删除的数据
            supplierRosterInfoService.deleteInId(rosterInfoDtos,ppmOutsourcingInfo.getId());

            rosterInfoDtos.forEach(infoDto ->{
                if(infoDto.getId() != null){
                    //去做修改
                    infoDto.setDeptId(infoDto.getDeptId().replace("dep",""));
                    infoDto.setSupplierName(ppmOutsourcingInfoDto.getOutsourcname());
                    infoDto.setServiceType(
                            BusinessUtil.getStringArray(infoDto.getServiceTypeArray(),BusinessUtil.REGEX_LINE));
                    infoDto.setModifiedBy(user.getUserid());
                    supplierRosterInfoService.updatePpmSupplierRosterInfo(infoDto);
                }else{
                    //去做新增
                    infoDto.setSupplierName(ppmOutsourcingInfoDto.getOutsourcname());
                    infoDto.setDeptId(infoDto.getDeptId().replace("dep",""));
                    infoDto.setServiceType(
                            BusinessUtil.getStringArray(infoDto.getServiceTypeArray(),BusinessUtil.REGEX_LINE));
                    infoDto.setPid(ppmOutsourcingInfo.getId());
                    infoDto.setSupplierCode(ppmOutsourcingInfo.getOrgcode());
                    infoDto.setCreatedBy(user.getUserid());
                    infoDto.setId(getNextSequence(PpmSupplierRosterInfo.class));

                    supplierRosterInfoService.addPpmSupplierRosterInfo(infoDto);
                }
            });
        }

        //增加价格表
        List<PpmOutsourcingInfoPriceDto> priceDtos = ppmOutsourcingInfoDto.getOutsourcingInfoPriceDtos();
        if(priceDtos != null && !priceDtos.isEmpty()){
            //删掉删除的数据
            ppmOutsourcingInfoPriceService.deleteInId(priceDtos,ppmOutsourcingInfo.getOrgcode());

            priceDtos.forEach(infoDto ->{
                if(infoDto.getId() != null){
                    //去做修改
                    PpmOutsourcingInfoPrice infoPrice = JsonUtil.jsonToClass(infoDto,PpmOutsourcingInfoPrice.class);
                    ppmOutsourcingInfoPriceService.updatePpmOutsourcingInfoPrice(infoPrice);
                }else{
                    if(StringUtils.isEmpty(infoDto.getPosition())){
                        return;
                    }
                    PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPriceDto = new PpmOutsourcingInfoPriceDto();
                    ppmOutsourcingInfoPriceDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
                    ppmOutsourcingInfoPriceDto.setOrgcode(ppmOutsourcingInfo.getOrgcode());
                    ppmOutsourcingInfoPriceDto.setPosition(infoDto.getPosition());
                    List<PpmOutsourcingInfoPriceDto> infoPriceDtos =
                            ppmOutsourcingInfoPriceService.queryList(ppmOutsourcingInfoPriceDto);
                    if(infoPriceDtos != null && !infoPriceDtos.isEmpty()){
                        PpmOutsourcingInfoPriceDto infoPriceDto = infoPriceDtos.get(0);
                        infoDto.setId(infoPriceDto.getId());
                        PpmOutsourcingInfoPrice infoPrice = JsonUtil.jsonToClass(infoDto,PpmOutsourcingInfoPrice.class);
                        ppmOutsourcingInfoPriceService.updatePpmOutsourcingInfoPrice(infoPrice);
                    }else {
                        infoDto.setOrgcode(ppmOutsourcingInfo.getOrgcode());
                        infoDto.setId(getNextSequence(PpmOutsourcingInfoPrice.class));
                        infoDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
                        infoDto.setCreateusrid(user.getUserid());
                        infoDto.setCreatetime(new Date());
                        PpmOutsourcingInfoPrice infoPrice = JsonUtil.jsonToClass(infoDto,PpmOutsourcingInfoPrice.class);
                        ppmOutsourcingInfoPriceService.addPpmOutsourcingInfoPrice(infoPrice);
                    }
                }
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto) {
        //修改删除状态
        PpmOutsourcingInfo ppmOutsourcingInfo = new PpmOutsourcingInfo();
        ppmOutsourcingInfo.setId(ppmOutsourcingInfoDto.getId());
        ppmOutsourcingInfo.setIsdel(1L);
        int num = updatePpmOutsourcingInfo(ppmOutsourcingInfo);

        if(num > 0 && ppmOutsourcingInfo.getId() != null){
            //删除供应商详情信息
            PpmSupplierRosterInfoDto rosterInfoDto = new PpmSupplierRosterInfoDto();
            rosterInfoDto.setPid(ppmOutsourcingInfoDto.getId());
            supplierRosterInfoService.deleteInId(null,ppmOutsourcingInfo.getId());
        }

        if(num > 0 && ppmOutsourcingInfo.getOrgcode() != null){
            //删除供应商详情信息
            PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice = new PpmOutsourcingInfoPrice();
            ppmOutsourcingInfoPrice.setOrgcode(ppmOutsourcingInfoDto.getOrgcode());
            ppmOutsourcingInfoPriceService.deleteInId(null,ppmOutsourcingInfo.getOrgcode());
        }

        log.info("删除成功");
    }

    @Override
    public PpmOutsourcingInfoDto querySupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto) {
        PpmOutsourcingInfo outsourcingInfo = JsonUtil.jsonToClass(ppmOutsourcingInfoDto,PpmOutsourcingInfo.class);
        PpmOutsourcingInfo ppmOutsourcingInfo = queryDetail(outsourcingInfo);
        PpmOutsourcingInfoDto outsourcingInfoDto = JsonUtil.jsonToClass(ppmOutsourcingInfo,PpmOutsourcingInfoDto.class);
        if(ppmOutsourcingInfo != null){
            PpmSupplierRosterInfoDto rosterInfoDto = new PpmSupplierRosterInfoDto();
            rosterInfoDto.setPid(outsourcingInfoDto.getId());
            rosterInfoDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
            List<PpmSupplierRosterInfoDto> rosterInfoDtos = supplierRosterInfoService.queryDetailList(rosterInfoDto);
            rosterInfoDtos.forEach(infoDto -> infoDto.setServiceTypeArray(
                    BusinessUtil.getArrayString(infoDto.getServiceType(),BusinessUtil.REGEX_LINE_UN)));
            outsourcingInfoDto.setSupplierRosterInfoDtos(rosterInfoDtos);

            //查询
            PpmOutsourcingInfoPriceDto infoPriceDto = new PpmOutsourcingInfoPriceDto();
            infoPriceDto.setOrgcode(ppmOutsourcingInfo.getOrgcode());
            infoPriceDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
            List<PpmOutsourcingInfoPriceDto> infoPriceDtos = ppmOutsourcingInfoPriceService.queryList(infoPriceDto);
            outsourcingInfoDto.setOutsourcingInfoPriceDtos(infoPriceDtos);
        }

        return outsourcingInfoDto;
    }


    /**
     * 新增校验
     * @param ppmOutsourcingInfoDto 对象
     */
    private void vaildCommitData(PpmOutsourcingInfoDto ppmOutsourcingInfoDto){
        //查询供应商名称是否存在
        PpmOutsourcingInfo outsourcingInfo = new PpmOutsourcingInfo();
        outsourcingInfo.setOutsourcname(ppmOutsourcingInfoDto.getOutsourcname());
        outsourcingInfo.setIsdel(0L);
        outsourcingInfo = queryDetail(outsourcingInfo);
        //如果供应商已存在，让用户去修改对应的供应商信息
        if(outsourcingInfo != null &&
                !Objects.equals(outsourcingInfo.getOrgcode(),ppmOutsourcingInfoDto.getOrgcode())){
            throw new BizException(ApiCode.FAIL,"供应商已存在，如需填写请修改对应供应商信息。");
        }

        List<PpmSupplierRosterInfoDto> list = ppmOutsourcingInfoDto.getSupplierRosterInfoDtos();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(obj -> {
                AssertUtil.notNull(obj.getDeptId(), "请填写用工部门");
//                AssertUtil.notNull(obj.getSupplierType(), "请填写供应商类型");
                AssertUtil.notNull(obj.getServiceMode(), "请填写服务方式");
                AssertUtil.notNull(obj.getIsSubcontract(), "请填写是否分包或转包");
                AssertUtil.notNull(obj.getSystemId(), "请填写服务系统名称");

//                if("0".equals(obj.getSupplierType())){
//                    AssertUtil.notNull(obj.getProjectId(), "请填写服务项目名称");
//                }

                AssertUtil.notNull(obj.getServiceTypeArray(), "请填写服务项目类型");
                AssertUtil.notNull(obj.getStartTime(),"请填写服务起始时间");
                AssertUtil.notNull(obj.getEndTime(),"请填写服务结束时间");
//                AssertUtil.notNull(obj.getSupplierYear(),"请填写服务年份");
                AssertUtil.notNull(obj.getFileNo(),"请上传尽职调查报告");
                AssertUtil.notValidator(obj.getStartTime(),"服务起始时间请按照xxxx年xx月格式输入","yyyy年MM月");
                AssertUtil.notValidator(obj.getEndTime(),
                        "服务结束时间请按照xxxx年xx月格式输入或填写至今","yyyy年MM月","至今");
            });
        }
    }

    /**
     * 导入校验
     * @param ppmSupplierRosterInfoDtos 对象
     */
    private void vaildCommitDataImport(List<PpmSupplierRosterInfoDto> ppmSupplierRosterInfoDtos){
        //
        StringJoiner bigJoiner = new StringJoiner(";");
        ppmSupplierRosterInfoDtos.forEach(infoDto ->{
            StringJoiner joiner = new StringJoiner(",");
            boolean flag = false;
            if(infoDto.getRid() == null){
                throw new BizException(ApiCode.FAIL,"序号必填！");
            }else {
                joiner.add("序号:" + infoDto.getRid());
            }

            if(StringUtils.isEmpty(infoDto.getDeptName())){
                flag = true;
                joiner.add("用工部门未填");
            }else {
                String deptId = commDepartmentService.getDepNameForId(infoDto.getDeptName());
                if(deptId == null){
                    flag = true;
                    joiner.add("无效用工部门");
                }
            }

            if(StringUtils.isEmpty(infoDto.getSupplierName())){
                flag = true;
                joiner.add("外包供应商名称未填");
            }

            if(StringUtils.isEmpty(infoDto.getServiceMode())){
                flag = true;
                joiner.add("服务方式未填");
            }

            if(StringUtils.isEmpty(infoDto.getIsSubcontract())){
                flag = true;
                joiner.add("是否分包或转包未填");
            }

            if(StringUtils.isEmpty(infoDto.getServiceType())){
                flag = true;
                joiner.add("服务类型未填");
            }else {
                String [] serviceTypes = infoDto.getServiceType().split(BusinessUtil.REGEX_SLASH);
                boolean serviceType = false;
                for(String name : serviceTypes){
                    EosDictEntry eosDictEntry =
                            eosDictEntryService.getTypenameForDetail(name,"BUSI_OUTSOURCE_POSITION");
                    if(eosDictEntry == null){
                        serviceType = true;
                    }
                }
                if(serviceType){
                    flag = true;
                    joiner.add("无效服务类型");
                }
            }

            if(StringUtils.isEmpty(infoDto.getStartTime())){
                flag = true;
                joiner.add("服务起始时间未填");
            }

            if(StringUtils.isEmpty(infoDto.getEndTime())){
                flag = true;
                joiner.add("服务结束时间未填");
            }

            if(flag){
                bigJoiner.add(joiner.toString());
            }
        });

        if(StringUtils.isNotEmpty(bigJoiner.toString())){
            log.info("上传异常{}",bigJoiner.toString());
            throw new BizException(ApiCode.FAIL,bigJoiner.toString());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importSupplierRoster(
            List<PpmSupplierRosterInfoDto> ppmSupplierRosterInfoDtos, CommOperatorDoLoginDto user){

        vaildCommitDataImport(ppmSupplierRosterInfoDtos);

        for(PpmSupplierRosterInfoDto infoDto : ppmSupplierRosterInfoDtos){
            PpmOutsourcingInfo ppmOutsourcingInfo = new PpmOutsourcingInfo();
            ppmOutsourcingInfo.setOutsourcname(infoDto.getSupplierName());
            ppmOutsourcingInfo.setIsdel(0L);
            Map<String,Object> infoMap = JsonUtil.objectTOMap(ppmOutsourcingInfo,PpmOutsourcingInfo.class);
            List<PpmOutsourcingInfo> outsourcingInfos =
                    myBatisDao.selectListT(PPMOUTSOURCINGINFO, infoMap, "getList");
            if(outsourcingInfos != null && !outsourcingInfos.isEmpty()){
                PpmOutsourcingInfo ppmOutsourcingInfo1 = outsourcingInfos.get(0);
                String orgCode = ppmOutsourcingInfo1.getOrgcode();
                if(StringUtils.isNotEmpty(orgCode)){
                    infoDto.setPid(ppmOutsourcingInfo1.getId());
                    infoDto.setSupplierCode(orgCode);
                    //获取部门id
                    saveSupplierRosterInfo(infoDto,user);
                }
            }else {
                //已存在的供应商信息
                String nextOrgCode = createNextOrgCode();
                if(StringUtils.isEmpty(nextOrgCode)){
                    throw new BizException(ApiCode.FAIL,"编码存在问题，");
                }

                //存储供应商信息
                PpmOutsourcingInfo ppmOutsourcingInfoDto = new PpmOutsourcingInfo();
                Long key = -getNextSequence(PpmOutsourcingInfo.class);
                ppmOutsourcingInfoDto.setOrgcode(nextOrgCode);
                ppmOutsourcingInfoDto.setIsdel(0L);
                ppmOutsourcingInfoDto.setId(key);
                ppmOutsourcingInfoDto.setOutsourcname(infoDto.getSupplierName());
                if(user != null){
                    ppmOutsourcingInfoDto.setCreators(user.getUserid());
                    ppmOutsourcingInfoDto.setCreatetime(new Date());
                }
                addPpmOutsourcingInfo(ppmOutsourcingInfoDto);

                //增加
                infoDto.setId(getNextSequence(PpmSupplierRosterInfo.class));
                infoDto.setPid(key);
                infoDto.setSupplierCode(nextOrgCode);
                //用工部门名称
                saveSupplierRosterInfo(infoDto,user);
            }
        }
    }

    /**
     * 存储信息
     * @param infoDto 对象
     * @param user 用户
     */
    private void saveSupplierRosterInfo(PpmSupplierRosterInfoDto infoDto,CommOperatorDoLoginDto user){
        //用工部门名称
        String deptId = commDepartmentService.getDepNameForId(infoDto.getDeptName());
        infoDto.setDeptId(deptId);



        if(StringUtils.isNotEmpty(infoDto.getServiceType())){
            String [] serviceTypes = infoDto.getServiceType().split(BusinessUtil.REGEX_SLASH);
            StringBuilder code = new StringBuilder();
            for(String name :serviceTypes){
                EosDictEntry eosDictEntry = eosDictEntryService.getTypenameForDetail(name,"BUSI_OUTSOURCE_POSITION");
                if(eosDictEntry != null){
                    code.append(eosDictEntry.getDictid()).append("|");
                }
            }
            infoDto.setServiceType(code.toString());
        }

        infoDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
        if(user != null){
            infoDto.setCreatedBy(user.getUserid());
        }
        infoDto.setCreatedTime(new Date());
        infoDto.setId(getNextSequence(PpmSupplierRosterInfo.class));
        supplierRosterInfoService.addPpmSupplierRosterInfo(infoDto);
    }



}
