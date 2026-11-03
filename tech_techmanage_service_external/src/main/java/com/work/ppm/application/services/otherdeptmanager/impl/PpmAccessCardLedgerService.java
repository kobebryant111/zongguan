package com.work.ppm.application.services.otherdeptmanager.impl;

import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerImportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.otherdeptmanager.PpmAccessCardLedger;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardLedgerService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  *  外包人员监保部台账 接口实现类
  */
@Service("ppmAccessCardLedgerService")
public class PpmAccessCardLedgerService extends BaseDaoSupper implements IPpmAccessCardLedgerService {
    /**
      * 实体类路径
      */
    private static String PPMACCESSCARDLEDGER = "com.work.ppm.application.model.otherdeptmanager.PpmAccessCardLedger";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Override
    public void queryPager(PpmAccessCardLedgerDto ppmAccessCardLedgerDto, Pager pager, CommOperatorDoLoginDto user) {
        List<ImRoleDoLoginDto> roles = user.getRoles();
        //权限仅限于管理员+监保部管理员+外包管理员
        long num = -1L;
        if (roles.stream().anyMatch(r -> "PERMANAGE".equals(r.getRoleId()))){
            num = 0L;
        }else if (roles.stream().anyMatch(r -> "JBB_MANAGER".equals(r.getRoleId()))){
            num = 1L;
            if ("0".equals(ppmAccessCardLedgerDto.getIfMakeCard())){
                ppmAccessCardLedgerDto.setIfMakeCard("3");
            }else {
                ppmAccessCardLedgerDto.setIfMakeCard("2");
            }

        }
        ppmAccessCardLedgerDto.setIsDel("0");
        Map<String,Object> map = JsonUtil.objectTOMap(ppmAccessCardLedgerDto,PpmAccessCardLedgerDto.class);
        myBatisDao.selectPager(PPMACCESSCARDLEDGER, map,pager,XmlIdEnums.BASES.getCode());
        List<PpmAccessCardLedgerDto> pagerList = JsonUtil.jsonToList(pager.getList(),PpmAccessCardLedgerDto.class);
        long finalNum = num;
        pagerList.forEach(pagerObj ->{
            pagerObj.setDeptName(BusinessUtil.getDeptNameValue(pagerObj.getDeptName(),1));
            if (finalNum == 0){
                pagerObj.setRemark(pagerObj.getRemarkYgb());
            }else if (finalNum == 1){
                pagerObj.setRemark(pagerObj.getRemarkJbb());
            }
            pagerObj.setIfUpdateRoles(finalNum+"");
        });
        pager.setList(pagerList);
    }

//    @Override
//    public String insertPpmAccessCardLedger(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
//        // 如果需要请set 主键id
//        int number = updatePpmAccessCardLedger(ppmAccessCardLedgerDto);
//        if (number == 0){
//            ppmAccessCardLedgerDto.setJobId(ppmAccessCardLedgerDto.getJobId());
//            addPpmAccessCardLedger(ppmAccessCardLedgerDto);
//        }
//        return ppmAccessCardLedgerDto.getId();
//    }

    @Override
    public int addPpmAccessCardLedger(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
        PpmAccessCardLedger ppmAccessCardLedger =
                JsonUtil.jsonToClass(ppmAccessCardLedgerDto, PpmAccessCardLedger.class);
        // 如果需要请set 主键id
//        ppmAccessCardLedger.setId(DateUtil.getDateRandom());
        ppmAccessCardLedger.setIsDel("0");
        ppmAccessCardLedger.setState("NEW");
        myBatisDao.executionPojo(
            PPMACCESSCARDLEDGER, ppmAccessCardLedger, IMyBatisDao.SQL.INSTER, XmlIdEnums.ADD.getCode());
        return 1;
    }

    @Override
    public PpmAccessCardLedgerDto queryDetail(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
        PpmAccessCardLedger ppmAccessCardLedger =
                JsonUtil.jsonToClass(ppmAccessCardLedgerDto, PpmAccessCardLedger.class);
        PpmAccessCardLedger data =
            myBatisDao.selectClass(PPMACCESSCARDLEDGER, ppmAccessCardLedger,XmlIdEnums.QUERY.getCode());
        return JsonUtil.jsonToClass(data, PpmAccessCardLedgerDto.class);
    }

    @Override
    public List<PpmAccessCardLedgerDto> queryDetailList(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
        PpmAccessCardLedger ppmAccessCardLedger =
                JsonUtil.jsonToClass(ppmAccessCardLedgerDto, PpmAccessCardLedger.class);
        List<PpmAccessCardLedger> dataList =
                myBatisDao.selectListT(PPMACCESSCARDLEDGER, ppmAccessCardLedger,XmlIdEnums.QUERYS.getCode());
        return JsonUtil.jsonToList(dataList, PpmAccessCardLedgerDto.class);
    }

    @Override
    public int updatePpmAccessCardLedger(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) {
        PpmAccessCardLedger ppmAccessCardLedger =
                JsonUtil.jsonToClass(ppmAccessCardLedgerDto, PpmAccessCardLedger.class);
        return myBatisDao.executionPojo(
            PPMACCESSCARDLEDGER, ppmAccessCardLedger, IMyBatisDao.SQL.UPDATE,XmlIdEnums.UPDATE.getCode());
    }

//    @Override
//    public int updatePpmAccessCardLedgers(PpmAccessCardLedgerDto accessCardLedgerDto) {
//        PpmAccessCardLedger ppmAccessCardLedger =
//                JsonUtil.jsonToClass(accessCardLedgerDto, PpmAccessCardLedgerExt.class);
//        return myBatisDao.executionPojo(
//                PPMACCESSCARDLEDGER, ppmAccessCardLedger, IMyBatisDao.SQL.UPDATE,"updatePojos");
//    }

    @Override
    public void importExcel(List<PpmAccessCardLedgerImportDto> list, CommOperatorDoLoginDto user) {
        list.forEach(obj ->{
            Map<String,Object> map = new HashMap<>();
            map.put("entranceCard",obj.getVirtualMachineId());
            List<PpmOutsourcingBasic> basics =ppmOutsourcingBasicService.queryOutsList(map);
            PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
            if (!basics.isEmpty()){
                basic = basics.get(0);
            }

            List<PpmAccessCardLedgerDto> ledgerDtoList =
                    queryDetailList(PpmAccessCardLedgerDto.builder().jobId(basic.getJobId()).isDel("0")
                            .state("NEW").build());
            if (!ledgerDtoList.isEmpty()){
                PpmAccessCardLedgerDto accessCardLedgerDto = ledgerDtoList.get(0);
                PpmAccessCardLedgerDto ledgerDto = PpmAccessCardLedgerDto.builder()
                        .remarkYgb(obj.getRemarkYgb())
                        .placeOfAccount(obj.getPlaceOfAccount())
                        .accountNumber(obj.getAccountNumber())
                        .creditCardNumbers(obj.getCreditCardNumbers())
                        .outBankName(obj.getOutBankName()).id(accessCardLedgerDto.getId()).build();
                updatePpmAccessCardLedger(ledgerDto);
            }
        });
    }

}
