package com.work.ppm.application.services.otherdeptmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.otherdeptmanager.PpmDevelopGroupInfo;
import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupInfoDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmDevelopGroupInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  *  对接手机平台信息 接口实现类
  */
@Service("ppmDevelopGroupInfoService")
public class PpmDevelopGroupInfoService extends BaseDaoSupper implements IPpmDevelopGroupInfoService {
    /**
      * 实体类路径
      */
    private static String PPMDEVELOPGROUPINFO = "com.work.ppm.application.model.otherdeptmanager.PpmDevelopGroupInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmDevelopGroupInfoDto developGroupInfoDto, Pager pager) {
        if (StringUtils.isNotEmpty(developGroupInfoDto.getDeptId())){
            developGroupInfoDto.setDeptId(developGroupInfoDto.getDeptId().replace("dep",""));
        }
        Map<String,Object> map = JsonUtil.objectTOMap(developGroupInfoDto,PpmDevelopGroupInfoDto.class);
        myBatisDao.selectPager(PPMDEVELOPGROUPINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        PpmDevelopGroupInfo developGroupInfo = JsonUtil.jsonToClass(ppmDevelopGroupInfoDto, PpmDevelopGroupInfo.class);
        // 如果需要请set 主键id
        developGroupInfo.setId(DateUtil.getDateRandom());
        myBatisDao.executionPojo(PPMDEVELOPGROUPINFO, developGroupInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
//    @Override
//    public PpmDevelopGroupInfoDto queryDetail(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
//        PpmDevelopGroupInfo developGroupInfo = JsonUtil.jsonToClass(ppmDevelopGroupInfoDto, PpmDevelopGroupInfo.class);
//        PpmDevelopGroupInfo data = myBatisDao.selectClass(PPMDEVELOPGROUPINFO, developGroupInfo,"queryDetail");
//        return JsonUtil.jsonToClass(data, PpmDevelopGroupInfoDto.class);
//    }

    @Override
    public List<PpmDevelopGroupInfoDto> queryDetailList(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        PpmDevelopGroupInfo developGroupInfo = JsonUtil.jsonToClass(ppmDevelopGroupInfoDto, PpmDevelopGroupInfo.class);
        List<PpmDevelopGroupInfo> data = myBatisDao.selectListT(PPMDEVELOPGROUPINFO, developGroupInfo,"getList");
        return JsonUtil.jsonToList(data, PpmDevelopGroupInfoDto.class);
    }

    @Override
    public int updatePpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        PpmDevelopGroupInfo developGroupInfo = JsonUtil.jsonToClass(ppmDevelopGroupInfoDto, PpmDevelopGroupInfo.class);
        developGroupInfo.setModifiedTime(new Date());
        return myBatisDao.executionPojo(PPMDEVELOPGROUPINFO, developGroupInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        PpmDevelopGroupInfo developGroupInfo = JsonUtil.jsonToClass(ppmDevelopGroupInfoDto, PpmDevelopGroupInfo.class);
        return myBatisDao.executionPojo(
                PPMDEVELOPGROUPINFO, developGroupInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmDevelopGroupInfoDto> exportPpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        PpmDevelopGroupInfo ppmDevelopGroupInfoList =
                JsonUtil.jsonToClass(ppmDevelopGroupInfoDto, PpmDevelopGroupInfo.class);
        List<PpmDevelopGroupInfo> data =
                myBatisDao.selectListT(PPMDEVELOPGROUPINFO, ppmDevelopGroupInfoList,"getList");
        return JsonUtil.jsonToList(data, PpmDevelopGroupInfoDto.class);
    }

}
