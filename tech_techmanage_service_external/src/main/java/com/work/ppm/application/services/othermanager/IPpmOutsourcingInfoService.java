package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 外包商公司
 */
public interface IPpmOutsourcingInfoService {

    /**
     * 分页
     * @param ppmOutsourcingInfo 对象
     * @param pager 分页对象
     */
    void queryPager(PpmOutsourcingInfo ppmOutsourcingInfo, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingInfo 对象
     * @return int
     */
    int addPpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) ;

    /**
     * 查询详情
     * @param ppmOutsourcingInfo 对象
     * @return PpmOutsourcingInfo
     */
    PpmOutsourcingInfo queryDetail(PpmOutsourcingInfo ppmOutsourcingInfo) ;

    /**
     * 查询详情集合
     * @param ppmOutsourcingInfoDto 对象
     * @param uniqueName 名称
     * @return list
     */
    List<PpmOutsourcingInfoDto> queryDetailList(PpmOutsourcingInfoDto ppmOutsourcingInfoDto,String uniqueName) ;


    /**
     * 更新
     * @param ppmOutsourcingInfo 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) ;

    /**
     * 删除
     * @param ppmOutsourcingInfo 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmOutsourcingInfo(PpmOutsourcingInfo ppmOutsourcingInfo) ;

    /**
     * 获取所有外包商
     * @return List<PpmOutsourcingInfo>
     */
    List<PpmOutsourcingInfo> getListAll();

    /**
     * getListByNos
     * @param orgnos 编号
     * @return List<PpmOutsourcingInfo>
     */
    List<PpmOutsourcingInfo> getListByNos(String orgnos);

    /**
     * 根据外包商名称查询编号
     * @param outsourcName 外包商名称
     * @return 外包商编号
     */
    String queryIdByName(String outsourcName);

    /**
     * 根据外包商编号查询名称
     * @param orgCode 外包商名称
     * @return 供应商名称
     */
    String queryNameByOrgCode(String orgCode);

    /**
     *  根据外包商编号查询
     * @param ppmOutsourcingInfo 对象
     * @return PpmOutsourcingInfo
     */
    PpmOutsourcingInfo queryDetailByOrgCode(PpmOutsourcingInfo ppmOutsourcingInfo);
    /**
     *  根据外包商编号查询
     * @param ppmOutsourcingInfoDto 对象
     * @return PpmOutsourcingInfo
     */
    PpmOutsourcingInfoDto queryNextOrgCode(PpmOutsourcingInfoDto ppmOutsourcingInfoDto);
    /**
     * 供应商花名册 新增
     * @param ppmOutsourcingInfoDto 对象
     * @return int
     */
    void addSupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto, CommOperatorDoLoginDto user);

    /**
     * 供应商花名册 修改
     * @param ppmOutsourcingInfoDto 对象
     */
    void updateSupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto,CommOperatorDoLoginDto user);

    /**
     * 供应商花名册 删除
     * @param ppmOutsourcingInfoDto 对象
     */
    void deleteSupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto);

    /**
     * 供应商花名册 详情
     * @param ppmOutsourcingInfoDto 对象
     * @return int
     */
    PpmOutsourcingInfoDto querySupplierRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto);

    /**
     * 导入供应商花名册 详情
     * @param ppmOutsourcingInfoDtos list
     */
    void importSupplierRoster(List<PpmSupplierRosterInfoDto> ppmOutsourcingInfoDtos, CommOperatorDoLoginDto user);


}
