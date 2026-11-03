package com.work.ppm.application.services.processmanager;

import com.work.ppm.application.dto.processmanager.PpmMsgTemplateDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.processmanager.PpmMsgTemplate;

import java.util.List;

/**
 * 发送消息模板
 */
public interface IPpmMsgTemplateService {

    /**
     * 分页
     * @param ppmMsgTemplate 对象
     * @param pager 分页对象
     */
    void queryPager(PpmMsgTemplate ppmMsgTemplate, Pager pager) ;

    /**
     * 添加
     * @param ppmMsgTemplate 对象
     * @return int
     */
     @Deprecated
    int addPpmMsgTemplate(PpmMsgTemplate ppmMsgTemplate) ;

    /**
     * 查询详情
     * @param ppmMsgTemplate 对象
     * @return PpmMsgTemplate
     */
    PpmMsgTemplate queryDetail(PpmMsgTemplate ppmMsgTemplate) ;

    /**
     * 更新
     * @param ppmMsgTemplate 对象
     * @return Integer
     */
    Integer updatePpmMsgTemplate(PpmMsgTemplateDto ppmMsgTemplate) ;

    /**
     * 删除
     * @param ppmMsgTemplate 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmMsgTemplate(PpmMsgTemplate ppmMsgTemplate) ;

    /**
     * 查询可用的模板
     * @param ppmMsgTemplate 对象
     * @return List<PpmMsgTemplate>
     */
    List<PpmMsgTemplate> getList(PpmMsgTemplate ppmMsgTemplate);

    /**
     * 查询详情
     * @param id id
     * @return PpmMsgTemplate
     */
    PpmMsgTemplate queryDetail(long  id) ;
}
