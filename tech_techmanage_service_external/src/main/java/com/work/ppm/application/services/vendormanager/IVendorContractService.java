package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.vendorcontract.VendorContractDto;
import com.work.ppm.application.model.vendormanager.VendorContract;
import com.work.ppm.commons.pager.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同基本信息 接口描述
 * </p>
 *
 * @since 2022-10-24
 */


public interface IVendorContractService {

    /**
     * 分页
     * @param vendorContract 对象
     * @param pager 分页对象
     */
    void queryPager(VendorContract vendorContract, Pager pager);

    /**
     * 查询 dto list
     */
    List<VendorContractDto> queryListExt();

    /**
     * 详情
     * @param id /
     */
    VendorContract queryDetailsById(Long id);

    /**
     * 计数
     * @param map /
     */
    long count(HashMap<String, Object> map);

    /**
     * 新增
     * @param vendorContract 实例
     */
    int addVendorContract(VendorContract vendorContract);
    /**
     * 新增 附id
     * @param vendorContract 实例
     */
    int addVendorContractWithId(VendorContract vendorContract);
    /**
     * 编辑
     * @param vendorContract 实例
     */
    int updateVendorContract(VendorContract vendorContract);

    int updateVendorContractCus(VendorContract vendorContract);

    /**
     * 携带条件的逻辑删除
     * @param id /
     */
    int deleteByIdsWithTerms(Long id);

    /**
     * 附件是否关联
     * @param attachInfo /
     * @param num /
     */
    void updateAttachRela(String attachInfo, long num);

    /**
     * byBasic
     * @param map /
     */
    List<VendorContract> selectByMapBasic(HashMap map);

    /**
     * byMap
     * @param map /
     */
    List<VendorContract> selectByMapSmall(Map map);


}