package com.work.ppm.application.services.outsourceledger;

import com.work.ppm.application.dto.outsourceledger.OutsourceLedgerDto;
import com.work.ppm.application.model.outsourceledger.OutsourceLedger;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 提前入场申请信息表 接口描述
 * </p>
 *
 * @since 2022-11-22
 */


public interface IOutsourceLedgerService {

    /**
     * import excel
     * @param file MultipartFile实例
     * @param userid /
     */
    Boolean importExcel(MultipartFile file, String userid);
    /**
     * 分页
     * @param outsourceLedger 对象
     * @param pager 分页对象
     */
    void queryPager(OutsourceLedger outsourceLedger, Pager pager);
//    /**
//     * OutsourceLedger 列表
//     * @param outsourceLedger 对象
//     */
//    List<OutsourceLedger> queryList(OutsourceLedger outsourceLedger);
//
//    /**
//     * 详情
//     * @param outsourceLedger 对象
//     */
//    OutsourceLedger queryDetails(OutsourceLedger outsourceLedger);
    /**
     * 详情
     * @param id
     */
    OutsourceLedger queryDetailsById(Long id);

//    /**
//     * 详情列表
//     * @param ids
//     */
//    List<OutsourceLedger> queryListByIds(List ids);
//    /**
//     * 计数
//     * @param map
//     */
//    long count(HashMap map);
    /**
     * 新增
     * @param outsourceLedger 实例
     */
    Integer addOutsourceLedger(OutsourceLedger outsourceLedger);
    /**
     * 新增 附带id
     * @param outsourceLedger 实例
     */
    Integer addOutsourceLedgerWithId(OutsourceLedger outsourceLedger);
    /**
     * 批量新增
     * @param list /
     */
    int batchAdd(List<OutsourceLedgerDto> list);
    /**
     * 编辑
     * @param outsourceLedger 实例
     */
    Integer updateOutsourceLedger(OutsourceLedger outsourceLedger);

//    /**
//     * 物理删除
//     * @param outsourceLedger 实例
//     */
//    Integer deleteOutsourceLedger(OutsourceLedger outsourceLedger);

    /**
     * 逻辑删除
     * @param ids
     */
    Integer deleteByIds(Long ids);

//    List<OutsourceLedger> selectByMapBasic(HashMap map);
//
//    List<OutsourceLedger> selectByMapSmall(HashMap map);
//
//    List<OutsourceLedger> selectByMap(HashMap map);

}