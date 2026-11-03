package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.dto.othermanager.PpmWorkKjlCghtDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmWorkKjlCght;

import java.util.List;

/**
 * 外包合同
 */
public interface IPpmWorkKjlCghtService {

    /**
     * 分页
     * @param ppmWorkKjlCght 对象
     * @param pager 分页对象
     */
    void queryPager(PpmWorkKjlCght ppmWorkKjlCght, Pager pager) ;

    /**
     * 添加
     * @param ppmWorkKjlCght 对象
     * @return int
     */
     @Deprecated
    int addPpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) ;

    /**
     * 查询详情
     * @param ppmWorkKjlCght 对象
     * @return PpmWorkKjlCght
     */
    @Deprecated
    PpmWorkKjlCght queryDetail(PpmWorkKjlCght ppmWorkKjlCght) ;

    /**
     * 更新
     * @param ppmWorkKjlCght 对象
     * @return  Integer
     */
    @Deprecated
    Integer updatePpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) ;

    /**
     * 删除
     * @param ppmWorkKjlCght 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) ;


    /**
     * queryContracts 查询合同的下拉框
     * @param info 对象
     * @return List<PpmWorkKjlCghtDto>
     */
    List<PpmWorkKjlCghtDto> queryContracts(PpmWorkKjlCghtDto info);
}
