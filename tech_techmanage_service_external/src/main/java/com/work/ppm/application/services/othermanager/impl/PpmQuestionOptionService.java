package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.PpmQuestionOption;
import com.work.ppm.application.services.othermanager.IPpmQuestionOptionService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主题库子表接口
 */
@Service("ppmQuestionOptionService")
public class PpmQuestionOptionService implements IPpmQuestionOptionService {
    /**
     * 实体类路径
     */
    private static String PPMQUESTIONOPTION = "com.work.ppm.application.model.othermanager.PpmQuestionOption";
    /**
     * 导入Excel标记
     */
    private static final String EXCEL_TAG = "EXCEL";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmQuestionOption ppmQuestionOption, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmQuestionOption,PpmQuestionOption.class);
        myBatisDao.selectPager(PPMQUESTIONOPTION, map,pager,"queryPagerList");
    }

    @Override
    public PpmQuestionOption queryDetail(PpmQuestionOption ppmQuestionOption) {
        return myBatisDao.selectClass(PPMQUESTIONOPTION, ppmQuestionOption,"queryDetail");
    }

    @Override
    public Integer updatePpmQuestionOption(PpmQuestionOption ppmQuestionOption) {
        return myBatisDao.executionPojo(PPMQUESTIONOPTION, ppmQuestionOption, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmQuestionOption(PpmQuestionOption ppmQuestionOption) {
        ppmQuestionOption.setIsdel("1");
        return myBatisDao.executionPojo(PPMQUESTIONOPTION, ppmQuestionOption, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int batchAddPpmQuestionOption(List<PpmQuestionOption> list, String no) {
        List<PpmQuestionOption> addList = new ArrayList<>();
        List<PpmQuestionOption> updateList = new ArrayList<>();
        // 标记是否可以删除记录
        boolean falg = false;
        // 集合中不存在id 则是添加，存在则是添加
        for (PpmQuestionOption item : list) {
            if(null == item.getId()) {
                addList.add(item);
            } else {
                falg = true;
                updateList.add(item);
            }
        }
        // 如果 no 为 excel 时，则进行删除，在添加
        if(EXCEL_TAG.equals(no) && !falg) {
            myBatisDao.executionPojo(PPMQUESTIONOPTION,list.get(0), IMyBatisDao.SQL.DELETE,"deletePojo");
        }
        // 添加
        if(null != addList && !addList.isEmpty()) {
            Long id = myBatisDao.selectCount(PPMQUESTIONOPTION, new HashMap<>(), "getPrimaryKey");
            Long intId = null == id ? 0 : id;
            int size = addList.size();
            for (int i = 0; i < size; i++) {
               intId ++;
               if(!StringUtil.isStringNotEmpty(addList.get(i).getQuestionno())) {
                   addList.get(i).setQuestionno(no);
               }
                addList.get(i).setIsdel("0");
                addList.get(i).setId(intId);
            }
            myBatisDao.executionPojo(PPMQUESTIONOPTION, addList, IMyBatisDao.SQL.INSTER,"batchAdd");
        }

        // 修改
        if(null != updateList && !updateList.isEmpty()) {
            for (PpmQuestionOption item: updateList) {
                this.updatePpmQuestionOption(item);
            }
        }
        return 1;
    }

    @Override
    public List<PpmQuestionOption> getList(String no) {
        Map<String,Object> map = new HashMap<>();
        map.put("questionno", no);
        return myBatisDao.selectListT(PPMQUESTIONOPTION, map,"getList");
    }
    @Override
    public Integer deletePpmQuestionOption(String questionnoStr) {
        if(!StringUtil.isStringNotEmpty(questionnoStr)) {
            return -1;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("questionnos", questionnoStr);
        map.put("isdel", 1);
        return myBatisDao.executionMap(PPMQUESTIONOPTION, map, IMyBatisDao.SQL.UPDATE, "bacthDeletes");
    }
}
