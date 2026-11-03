package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.dto.othermanager.PpmQuestionListDto;
import com.work.ppm.application.model.othermanager.PpmQuestionList;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 主题库表
 */
public interface IPpmQuestionListService {

    /**
     * 分页
     * @param ppmQuestionList 对象
     * @param pager 分页对象
     */
    void queryPager(PpmQuestionList ppmQuestionList, Pager pager) ;

    /**
     * 查询详情
     * @param ppmQuestionList 对象
     * @return PpmQuestionListDto
     */
    PpmQuestionListDto queryDetail(PpmQuestionList ppmQuestionList) ;

    /**
     * 更新
     * @param ppmQuestionList 对象
     * @return Integer
     */
    Integer updatePpmQuestionList(PpmQuestionListDto ppmQuestionList) ;

    /**
     * 删除
     * @param ppmQuestionList 对象
     * @return Integer
     */
    Integer deletePpmQuestionList(PpmQuestionList ppmQuestionList) ;

    /**
     * excel 导出
     * @param ppmQuestionList 对象
     * @param response  response
     * @return boolean
     */
    boolean exportExcel(PpmQuestionList ppmQuestionList, HttpServletResponse response) ;

    /**
     * 导入
     * @param file 对象
     * @param response response
     * @return int
     */
    int importExcel(MultipartFile file, HttpServletResponse response) ;

    /**
     * 批量删除
     * @param list 集合
     * @return int
     */
    int batchDeletes(List<PpmQuestionList> list) ;
}
