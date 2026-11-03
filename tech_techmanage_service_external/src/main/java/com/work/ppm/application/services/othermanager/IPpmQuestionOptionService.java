package com.work.ppm.application.services.othermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmQuestionOption;

import java.util.List;

/**
 * 主题库子表接口
 */
public interface IPpmQuestionOptionService {

    /**
     * 分页
     * @param ppmQuestionOption 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmQuestionOption ppmQuestionOption, Pager pager) ;

    /**
     * 查询详情
     * @param ppmQuestionOption 对象
     * @return PpmQuestionOption
     */
    @Deprecated
    PpmQuestionOption queryDetail(PpmQuestionOption ppmQuestionOption) ;

    /**
     * 更新
     * @param ppmQuestionOption 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmQuestionOption(PpmQuestionOption ppmQuestionOption) ;

    /**
     * 删除
     * @param ppmQuestionOption 对象
     * @return Integer
     */
    Integer deletePpmQuestionOption(PpmQuestionOption ppmQuestionOption) ;

    /**
     * 批量添加
     * @param list 集合
     * @param no 编号
     * @return int
     */
    int batchAddPpmQuestionOption(List<PpmQuestionOption> list, String no) ;

    /**
     * 查询集合
     * @param no 编号
     * @return List<PpmQuestionOption>
     */
    List<PpmQuestionOption> getList(String no ) ;
    /**
     * 批量删除
     * @param questionnoStr 多个编号
     * @return Integer
     */
    Integer deletePpmQuestionOption(String questionnoStr) ;
}
