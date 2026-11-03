package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.vendorcontract.VendorContractDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.model.vendormanager.VendorContract;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.vendormanager.IVendorContractService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 合同基本信息 服务实现类
 * </p>
 *
 * @since 2022-10-24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VendorContractService extends BaseDaoSupper implements IVendorContractService {

    /**
     * 实体类路径
     */
    private static String VENDORCONTRACT= "com.work.ppm.application.model.vendormanager.VendorContract";
    /**
     * 附件管理
     */
    private final IAttatchManagementService attatchManagementService;
    /**
     * 文档管理
     */
    private final IPpmDocumentService ppmDocumentService;

    private final String IN_EFFECTIVE_STATUS = "未生效";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(VendorContract vendorContract, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(vendorContract, VendorContract.class);
        myBatisDao.selectPager(VENDORCONTRACT, map, pager, "queryPagerList");
    }

    @Override
    public List<VendorContract> selectByMapSmall(Map map) {
        return  myBatisDao.selectListT(VENDORCONTRACT, map, "selectByMapSmall");
    }
    @Override
    public List<VendorContract> selectByMapBasic(HashMap map) {
        return myBatisDao.selectListT(VENDORCONTRACT, map, "selectByMapBasic");
    }

    @Override
    public List<VendorContractDto> queryListExt() {
        return myBatisDao.selectListT(VENDORCONTRACT, null, "queryListExt");
    }

    @Override
    public VendorContract queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return myBatisDao.selectClass(VENDORCONTRACT, map, "queryDetailsById");
    }

    @Override
    public long count(HashMap<String, Object> map) {
        return myBatisDao.selectCount(VENDORCONTRACT, map,"selectCount");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addVendorContract(VendorContract vendorContract) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("contractNo", vendorContract.getContractNo());
        long counts = count(map);
        if(counts > 0) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "合同编号已存在");
        }
        addVendorContractWithId(vendorContract);
        if (StringUtil.isStringNotEmpty(vendorContract.getAttachInfo())) {
            AttatchManagement attatchManagement = new AttatchManagement();
            attatchManagement.setIsAssociated("Y");
            attatchManagement.setId(Long.parseLong(vendorContract.getAttachInfo()));
            attatchManagementService.updateAttatchManagement(attatchManagement);
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addVendorContractWithId(VendorContract vendorContract) {
        if (!Objects.isNull(vendorContract.getTaxRate())) {
            vendorContract.setTaxRate(vendorContract.getTaxRate()/100);
        }
        myBatisDao.executionPojo(VENDORCONTRACT, vendorContract, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateVendorContract(VendorContract vendorContract) {
        if (!Objects.isNull(vendorContract.getTaxRate())) {
            vendorContract.setTaxRate(vendorContract.getTaxRate()/100);
        }
        // 总办议案是否更改
        String old = this.queryDetailsById(vendorContract.getId()).getAttachInfo();
        String newAttach = vendorContract.getAttachInfo();
        // 总办议案被更新
        if (!old.equalsIgnoreCase(newAttach)) {
            // 更新old
            updateAttachRela(old,1);
            // newAttach 总办议案：[是否关联]字段 -> '是'
            AttatchManagement attatchManagement = new AttatchManagement();
            attatchManagement.setId(Long.parseLong(newAttach));
            attatchManagement.setIsAssociated("Y");
            attatchManagementService.updateAttatchManagement(attatchManagement);
        }
        return myBatisDao.executionPojo(VENDORCONTRACT, vendorContract, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateVendorContractCus(VendorContract vendorContract) {
        return myBatisDao.executionPojo(VENDORCONTRACT, vendorContract, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttachRela(String attachInfo, long num) {
        HashMap map= new HashMap<>();
        map.put("attachInfo",attachInfo);
        map.put("isDel", LogicEnums.VALID.getCode());
        long count = this.count(map);
        if (count==num) {
            AttatchManagement attatchManagement = new AttatchManagement();
            attatchManagement.setIsAssociated("N");
            attatchManagement.setId(Long.parseLong(attachInfo));
            attatchManagementService.updateAttatchManagement(attatchManagement);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByIdsWithTerms(Long id) {
        if(Objects.isNull(id)) {
            return -1;
        }
        VendorContract entity = this.queryDetailsById(id);
        if (IN_EFFECTIVE_STATUS.equalsIgnoreCase(entity.getStatus())) {
            entity.setIsDel(LogicEnums.INVALID.getCode());
            int res = this.updateVendorContractCus(entity);
            if (res > 0) {
                // 当前总办议案无其他关联合同时, 更新总办议案：[是否关联]字段 -> '否'
                this.updateAttachRela(entity.getAttachInfo(),0);
            }
            return res;
        } else {
            throw new BizException(ApiCode.UPATE_FAIL, "此状态合同不可删除");
        }
    }
}