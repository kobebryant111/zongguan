package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmApplyvirtualInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmApplyvirtualInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.Map;

/**
 * 虚拟机桌面变更
 */
public interface IPpmApplyvirtualInfoService {

    /**
     * 虚拟桌面 宁波地区管理审批
     * @param ppmApplyvirtualInfoDto 对象
     * @return String
     */
    String nbAreaManagerApprove(PpmApplyvirtualInfoDto ppmApplyvirtualInfoDto);

    /**
     * 分页
     * @param ppmApplyvirtualInfo 对象
     * @param pager 对象
     */
    @Deprecated
    void queryPager(PpmApplyvirtualInfo ppmApplyvirtualInfo, Pager pager) ;

    /**
     * 添加
     * @param ppmApplyvirtualInfo 对象
     * @return int
     */
    int addPpmApplyvirtualInfo(PpmApplyvirtualInfoDto ppmApplyvirtualInfo) ;

    /**
     * 查询详情
     * @param ppmApplyvirtualInfo 对象
     * @return PpmApplyvirtualInfo
     */
    @Deprecated
    PpmApplyvirtualInfo queryDetail(PpmApplyvirtualInfo ppmApplyvirtualInfo) ;

    /**
     * 更新
     * @param ppmApplyvirtualInfo 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmApplyvirtualInfo(PpmApplyvirtualInfo ppmApplyvirtualInfo) ;

    /**
     * 删除
     * @param ppmApplyvirtualInfo 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmApplyvirtualInfo(PpmApplyvirtualInfo ppmApplyvirtualInfo) ;

    /**
     * changeVirtualInfo
     * @param param 参数
     */
    void changeVirtualInfo(Map param);

    /**
     * 检查虚拟桌面号 是否重复
     * @param virtualNo 虚拟桌面号
     * @return boolean true：存在，false：不存在
     */
    boolean checkedVirtualNo(String virtualNo);
}
