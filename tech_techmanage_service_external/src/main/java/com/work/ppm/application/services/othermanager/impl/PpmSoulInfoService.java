package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.PpmSoulInfo;
import com.work.ppm.application.services.othermanager.IPpmSoulInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 鸡汤
 */
@Slf4j
@Service("ppmSoulInfoService")
public class PpmSoulInfoService extends BaseDaoSupper implements IPpmSoulInfoService {
    /**
     * 实体类路径
     */
    private static String PPMSOULINFO = "com.work.ppm.application.model.othermanager.PpmSoulInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmSoulInfo ppmSoulInfo, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmSoulInfo,PpmSoulInfo.class);
        myBatisDao.selectPager(PPMSOULINFO, map,pager,"queryPagerList");
    }

    @Override
    public PpmSoulInfo queryDetail(PpmSoulInfo ppmSoulInfo) {
        return myBatisDao.selectClass(PPMSOULINFO, ppmSoulInfo,"queryDetail");
    }

    @Override
    public Integer updatePpmSoulInfo(PpmSoulInfo ppmSoulInfo) {
        if(StringUtil.isNotEmpty(ppmSoulInfo.getId())) {
            // 更新
            if("老话".equals(ppmSoulInfo.getStyle())) {
                ppmSoulInfo.setStarttime(" ");
                ppmSoulInfo.setEndtime(" ");
            }
            myBatisDao.executionPojo(PPMSOULINFO, ppmSoulInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
        } else {
            Long id = getNextSequence(PpmSoulInfo.class);
            // 添加
            ppmSoulInfo.setRoles("");
            ppmSoulInfo.setId(id);
            myBatisDao.executionPojo(PPMSOULINFO, ppmSoulInfo, IMyBatisDao.SQL.INSTER,"addPojo");
        }
        return 1;
    }

    @Override
    public Integer deletePpmSoulInfo(PpmSoulInfo ppmSoulInfo) {
        return myBatisDao.executionPojo(PPMSOULINFO, ppmSoulInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmSoulInfo> getPpmSoulInfo(PpmSoulInfo ppmSoulInfo) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmSoulInfo,PpmSoulInfo.class);
        return myBatisDao.selectListT(PPMSOULINFO, map,"queryPagerList");
    }

    @Override
    public int doImport(MultipartFile file) {
        List<PpmSoulInfo> list = ExcelUtil.importExcel(file,PpmSoulInfo.class);
        Long id = this.getNextSequence(PpmSoulInfo.class);
        // 增加主键id
        int size = list.size();
        for (int i = 0; i < size; i++) {
            id = id + i;
            list.get(i).setId(id);
        }
        myBatisDao.executionPojo(PPMSOULINFO, list, IMyBatisDao.SQL.INSTER,"batchAdd");
        return 1;
    }

    @Override
    public int batchDelete(List<PpmSoulInfo> list) {
        if(null != list && list.isEmpty()) {
            return -1;
        }
        Map<String,Object> map = new HashMap<>();
        int size = list.size();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append("'").append(list.get(i).getId()).append("',");
        }
        String param = sb.toString().substring(0,sb.toString().length()-1);
        map.put("ids", param);
        myBatisDao.executionMap(PPMSOULINFO, map, IMyBatisDao.SQL.DELETE, "batchDelForId");
        return 1;
    }
}
