package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmProjectOnlinefjh;
import com.work.ppm.application.dto.onlinemanager.PpmProjectOnlinefjhDto;
import com.work.ppm.application.services.onlinemanager.IPpmProjectOnlinefjhService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmProjectOnlinefjhService")
public class PpmProjectOnlinefjhService extends BaseDaoSupper implements IPpmProjectOnlinefjhService {
    /**
      * 实体类路径
      */
    private static String PPMPROJECTONLINEFJH = "com.work.ppm.application.model.onlinemanager.PpmProjectOnlinefjh";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;
    /**
     * 业务字典服务类
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProjectOnlinefjhDto,PpmProjectOnlinefjhDto.class);
        myBatisDao.selectPager(PPMPROJECTONLINEFJH, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh =
                JsonUtil.jsonToClass(ppmProjectOnlinefjhDto, PpmProjectOnlinefjh.class);
        Long id = getNextSequence(PpmProjectOnlinefjh.class);

        // 如果需要请set 主键id
        //ppmProjectOnlinefjh.setId(id.intValue());
        ppmProjectOnlinefjh.setId(id);
        myBatisDao.executionPojo(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmProjectOnlinefjhDto queryDetail(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh =
                JsonUtil.jsonToClass(ppmProjectOnlinefjhDto, PpmProjectOnlinefjh.class);
        PpmProjectOnlinefjh data = myBatisDao.selectClass(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh,
        "queryDetail");
        PpmProjectOnlinefjhDto item = JsonUtil.jsonToClass(data, PpmProjectOnlinefjhDto
        .class);
        return item;
    }

    @Override
    public int updatePpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh =
                JsonUtil.jsonToClass(ppmProjectOnlinefjhDto, PpmProjectOnlinefjh.class);
        return myBatisDao.executionPojo(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh =
                JsonUtil.jsonToClass(ppmProjectOnlinefjhDto, PpmProjectOnlinefjh.class);
        return myBatisDao.executionPojo(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmProjectOnlinefjhDto> queryNewFjhOnileFlow(String demandId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", demandId);
        List<PpmProjectOnlinefjh> onlinefjhs = myBatisDao.selectListT(
                PPMPROJECTONLINEFJH, param,"queryNewFjhOnileFlow");
        List<PpmProjectOnlinefjhDto> onlinefjhDtos = JsonUtil.jsonToList(onlinefjhs, PpmProjectOnlinefjhDto.class);
        return onlinefjhDtos;
    }

    @Override
    public PpmProjectOnlinefjhDto getOnlineInfo(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh =
                JsonUtil.jsonToClass(ppmProjectOnlinefjhDto, PpmProjectOnlinefjh.class);
        PpmProjectOnlinefjh data = myBatisDao.selectClass(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh,
                "getOnlineInfo");
        PpmProjectOnlinefjhDto item = JsonUtil.jsonToClass(data, PpmProjectOnlinefjhDto
                .class);

        List<PpmDocumentDto> list = ppmDocumentService.getList(ppmProjectOnlinefjhDto.getInstanceid());
        item.setDoclist(list);
        return item;
    }

    @Override
    public PpmProjectOnlinefjhDto queryDetailByInstanceid(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh = JsonUtil.jsonToClass(
                ppmProjectOnlinefjhDto, PpmProjectOnlinefjh.class);
        PpmProjectOnlinefjh data = myBatisDao.selectClass(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh,
                "queryDetailByInstanceid");
        PpmProjectOnlinefjhDto item = JsonUtil.jsonToClass(data, PpmProjectOnlinefjhDto
                .class);
        return item;
    }

    @Override
    public String queryInstanceidByOnlinenum(PpmProjectOnlinefjhDto onlinefjh) {
        PpmProjectOnlinefjh ppmProjectOnlinefjh = JsonUtil.jsonToClass(onlinefjh, PpmProjectOnlinefjh.class);
        PpmProjectOnlinefjh data = myBatisDao.selectClass(PPMPROJECTONLINEFJH, ppmProjectOnlinefjh,
                "queryInstanceidByOnlinenum");
        PpmProjectOnlinefjhDto item = JsonUtil.jsonToClass(data, PpmProjectOnlinefjhDto
                .class);
        if (StringUtil.isNotEmpty(item)) {
            return item.getInstanceid();
        } else {
            return "";
        }
    }

    @Override
    public PpmProjectOnlinefjhDto getMobileOnlineInfo(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        PpmProjectOnlinefjhDto item = this.getOnlineInfo(ppmProjectOnlinefjhDto);
        List<PpmDocumentDto> list = item.getDoclist();

        String mobilePath = ppmProjectOnlinefjhDto.getDir()
                + ppmProjectOnlinefjhDto.getInstanceid()
                + ppmProjectOnlinefjhDto.getSeparator();
        if (list != null && list.size() > 0) {
            list.stream().forEach(ii -> {
                String fileName = ii.getStockpath()
                        .substring(0, ii.getStockpath().lastIndexOf("/"));
                String fileName1 = StringUtil.isNotBlank(ii.getTag2()) ? ii.getTag2() : ii.getDocname();
                String sourceFilePath = eosDictEntryService
                        .getTypeidForDetail("NEW_MOBILE_FILE","fileurl").getDictname()
                        + "?stockPath=" + ii.getStockpath()
                        + "&docname=" + fileName1;
                ii.setSourceFilePath(sourceFilePath);
                ii.setMobilePath(mobilePath + fileName1);
            });
        }

        item.setDoclist(list);
        return item;
    }
}

