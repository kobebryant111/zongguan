package com.work.ppm.application.services.otherdeptmanager.impl;

import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoDto;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.otherdeptmanager.PpmAccessCardApplyInfo;
import com.work.ppm.application.model.otherdeptmanager.ext.PpmAccessCardApplyInfoExt;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  *  监保部台账门禁卡流程详情 接口实现类
  */
@Service("ppmAccessCardApplyInfoService")
public class PpmAccessCardApplyInfoService extends BaseDaoSupper implements IPpmAccessCardApplyInfoService {
    /**
      * 实体类路径
      */
    private static String PPMACCESSCARDAPPLYINFO =
            "com.work.ppm.application.model.otherdeptmanager.PpmAccessCardApplyInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmAccessCardApplyInfo(PpmAccessCardApplyInfoDto ppmAccessCardApplyInfoDto) {
        PpmAccessCardApplyInfo ppmAccessCardApplyInfo =
                JsonUtil.jsonToClass(ppmAccessCardApplyInfoDto, PpmAccessCardApplyInfo.class);
        // 如果需要请set 主键id
        ppmAccessCardApplyInfo.setId(DateUtil.getDateRandom());
        ppmAccessCardApplyInfo.setIsDel("0");
        myBatisDao.executionPojo(
            PPMACCESSCARDAPPLYINFO, ppmAccessCardApplyInfo, IMyBatisDao.SQL.INSTER, XmlIdEnums.ADD.getCode());
        return 1;
    }

    @Override
    public List<PpmAccessCardApplyInfoDto> queryDetailList(
            PpmAccessCardApplyInfoDto ppmAccessCardApplyInfoDto,String uniqueName) {
        PpmAccessCardApplyInfo ppmAccessCardApplyInfo =
                JsonUtil.jsonToClass(ppmAccessCardApplyInfoDto, PpmAccessCardApplyInfo.class);
        List<PpmAccessCardApplyInfoExt> data =
                myBatisDao.selectListT(PPMACCESSCARDAPPLYINFO, ppmAccessCardApplyInfo,uniqueName);
        return JsonUtil.jsonToList(data, PpmAccessCardApplyInfoDto.class);
    }

    @Override
    public int updatePpmAccessCardApplyInfo(PpmAccessCardApplyInfoDto ppmAccessCardApplyInfoDto) {
        PpmAccessCardApplyInfo ppmAccessCardApplyInfo =
                JsonUtil.jsonToClass(ppmAccessCardApplyInfoDto, PpmAccessCardApplyInfo.class);
        return myBatisDao.executionPojo(
            PPMACCESSCARDAPPLYINFO, ppmAccessCardApplyInfo, IMyBatisDao.SQL.UPDATE,XmlIdEnums.UPDATE.getCode());
    }

}
