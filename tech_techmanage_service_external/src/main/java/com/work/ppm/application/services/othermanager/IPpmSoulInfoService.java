package com.work.ppm.application.services.othermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmSoulInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 鸡汤
 */
public interface IPpmSoulInfoService {

    /**
     * 分页
     * @param ppmSoulInfo 对象
     * @param pager 分页对象
     */
    void queryPager(PpmSoulInfo ppmSoulInfo, Pager pager) ;

    /**
     * 查询详情
     * @param ppmSoulInfo 对象
     * @return PpmSoulInfo
     */
    PpmSoulInfo queryDetail(PpmSoulInfo ppmSoulInfo) ;

    /**
     * 更新
     * @param ppmSoulInfo 对象
     * @return Integer
     */
    Integer updatePpmSoulInfo(PpmSoulInfo ppmSoulInfo) ;

    /**
     * 删除
     * @param ppmSoulInfo 对象
     * @return Integer
     */
    Integer deletePpmSoulInfo(PpmSoulInfo ppmSoulInfo) ;

    /**
     *  查询集合
     * @param ppmSoulInfo 对象
     * @return List<PpmSoulInfo>
     */
    List<PpmSoulInfo> getPpmSoulInfo(PpmSoulInfo ppmSoulInfo) ;

    /**
     * 导入
     * @param file 对象
     * @return int
     */
    int doImport(MultipartFile file) ;

    /**
     * 批量删除
     * @param list 集合
     * @return int
     */
    int batchDelete(List<PpmSoulInfo> list) ;
}
