package com.work.ppm.application.services.basismanager;

import com.work.ppm.application.dto.basismanager.PpmQuartzManagerDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.basismanager.PpmQuartzManager;

import java.util.List;

/**
 * 定时器管理
 */
public interface IPpmQuartzManagerService {

    /**
     * 分页
     * @param ppmQuartzManager 对象
     * @param pager 分页对象
     */
    void queryPager(PpmQuartzManager ppmQuartzManager, Pager pager) ;

    /**
     * 添加
     * @param ppmQuartzManager 对象
     * @return int
     */
    int addPpmQuartzManager(PpmQuartzManager ppmQuartzManager) ;

    /**
     * 查询详情
     * @param ppmQuartzManager 对象
     * @return PpmQuartzManager
     */
    PpmQuartzManager queryDetail(PpmQuartzManager ppmQuartzManager) ;

    /**
     * 更新
     * @param ppmQuartzManager 对象
     * @return Integer
     */
    Integer updatePpmQuartzManager(PpmQuartzManager ppmQuartzManager) ;

    /**
     * 删除
     * @param ppmQuartzManager 对象
     * @return Integer
     */
    Integer deletePpmQuartzManager(PpmQuartzManager ppmQuartzManager) ;

    /**
     * 获取启用中的定时器
     * @return List<PpmQuartzManager>
     */
    List<PpmQuartzManager> queryEnableAll();

    /**
     * 启动
     * @param ppmQuartzManager 对象
     */
    void startQuartz(PpmQuartzManager ppmQuartzManager);

    /**
     * 停止
     * @param ppmQuartzManager 对象
     * @return  boolean
     */
    boolean stopQuartz(PpmQuartzManager ppmQuartzManager);

    /**
     * 立即执行任务
     * @param dto 参数对象
     * @return boolean true:成功，false：失败
     */
    boolean executeImmediately(PpmQuartzManagerDto dto);
}
