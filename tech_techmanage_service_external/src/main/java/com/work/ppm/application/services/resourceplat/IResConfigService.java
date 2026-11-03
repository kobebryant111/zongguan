package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.resourceplat.ResConfigDto;
import com.work.ppm.application.dto.resourceplat.ResConfigQo;
import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResConfigSoft;
import com.work.ppm.application.model.resourceplat.ResConfigVm;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
 * 资源配置管理
 */
public interface IResConfigService {


    /**
     * 分页
     * @param resConfigDto 对象
     * @param pager 分页对象
     */
    void queryConfigComboList(ResConfigDto resConfigDto, Pager pager) ;

    /**
     * 分页
     * @param resConfigDto 对象
     * @param pager 分页对象
     */
    void queryVMPartsList(ResConfigDto resConfigDto, Pager pager) ;

    /**
     * 分页
     * @param resConfigDto 对象
     * @param pager 分页对象
     */
    void querySoftList(ResConfigDto resConfigDto, Pager pager) ;




    /**
     * 获取软件数据
     * @param resConfigDto 对象
     * @return List
     */
    List<ResConfigSoft> querySoftData(ResConfigDto resConfigDto) ;

    /**
     * 添加
     * @param resConfigQo 对象
     * @return int
     */
    String addConfigComboData(ResConfigQo resConfigQo , String saveFlag, String comboOneId) ;

    /**
     * 添加
     * @param resConfigVm 对象
     * @return int
     */
    int addVMPartsData(List<ResConfigVm> resConfigVm ,String saveFlag) ;

    /**
     * 添加
     * @param resConfigSoft 对象
     * @return int
     */
    int addSoftData(List<ResConfigSoft> resConfigSoft ,String saveFlag ,String comboOneId) ;

    /**
     * 更新
     * @param resConfigDto 对象
     * @return Integer
     */
    Integer upConfigComboData(ResConfigDto resConfigDto) ;


    /**
     * 更新
     * @param resConfigDto 对象
     * @return Integer
     */
    Integer upSoftData(ResConfigDto resConfigDto) ;


    /**
     * 更新
     * @param resConfigDto 对象
     * @return Integer
     */
    Integer upVMPartsData(ResConfigDto resConfigDto) ;

    /**
     * 状态更新
     * @param resConfigDto 对象
     * @return Integer
     */
    Map<String, Object> upConfigStatus(ResConfigDto resConfigDto) ;

    /**
     * 根据配件类型查询是否发布
     * @param type 配件类型
     * @return int
     */
    int querVmPartsCount(String type);

    /**
     * 根据是否涉及资金交易和系统运维等级查询是否发布
     * @param resConfigCombo
     * @return int
     */
    int getPublishCount(ResConfigCombo resConfigCombo);

    /**
     * 获取物理机配件
     * @param
     * @return List
     */
    List<ResConfigCombo> queryPhysicalParts();

}
