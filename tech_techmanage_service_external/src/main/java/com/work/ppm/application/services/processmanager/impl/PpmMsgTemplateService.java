package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.application.dto.processmanager.PpmMsgTemplateDto;
import com.work.ppm.application.model.processmanager.PpmMsgTemplate;
import com.work.ppm.application.services.processmanager.IPpmMsgTemplateService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 发送消息模板
 */
@Service("ppmMsgTemplateService")
public class PpmMsgTemplateService extends BaseDaoSupper implements IPpmMsgTemplateService {
    /**
     * 实体类路径
     */
    private static String PPMMSGTEMPLATE = "com.work.ppm.application.model.processmanager.PpmMsgTemplate";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmMsgTemplate ppmMsgTemplate, Pager pager) {
        ppmMsgTemplate.setIsdel(0L);   // 0未删除，1：已删除
        Map<String,Object> map = JsonUtil.objectTOMap(ppmMsgTemplate,PpmMsgTemplate.class);
        myBatisDao.selectPager(PPMMSGTEMPLATE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmMsgTemplate(PpmMsgTemplate ppmMsgTemplate) {
        Long id = getNextSequence(PpmMsgTemplate.class);
        // 如果需要请set 主键id
        ppmMsgTemplate.setId(id);
        ppmMsgTemplate.setIsdel(0L);
        myBatisDao.executionPojo(PPMMSGTEMPLATE, ppmMsgTemplate, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmMsgTemplate queryDetail(PpmMsgTemplate ppmMsgTemplate) {
        return myBatisDao.selectClass(PPMMSGTEMPLATE, ppmMsgTemplate,"queryDetail");
    }

    @Override
    public Integer updatePpmMsgTemplate(PpmMsgTemplateDto ppmMsgTemplate) {
        PpmMsgTemplate template = JsonUtil.jsonToClass(ppmMsgTemplate, PpmMsgTemplate.class);
        if("add".equals(ppmMsgTemplate.getSqlStatus())) {
           this.addPpmMsgTemplate(template);
        } else {
            myBatisDao.executionPojo(PPMMSGTEMPLATE, template, IMyBatisDao.SQL.UPDATE,"updatePojo");
        }
        return 1;
    }

    @Override
    public Integer deletePpmMsgTemplate(PpmMsgTemplate ppmMsgTemplate) {
        return myBatisDao.executionPojo(PPMMSGTEMPLATE, ppmMsgTemplate, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmMsgTemplate> getList(PpmMsgTemplate ppmMsgTemplate) {
        ppmMsgTemplate.setIsdel(0L); // 0未删除，1：已删除
        return myBatisDao.selectListT(PPMMSGTEMPLATE, ppmMsgTemplate, "getList");
    }

    @Override
    public PpmMsgTemplate queryDetail(long id) {
        PpmMsgTemplate template = new PpmMsgTemplate();
        template.setId(id);
        return this.queryDetail(template);
    }
}
