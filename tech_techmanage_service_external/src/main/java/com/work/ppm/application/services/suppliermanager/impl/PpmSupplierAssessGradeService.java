package com.work.ppm.application.services.suppliermanager.impl;

import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessGradeDto;
import com.work.ppm.application.model.suppliermanager.PpmSupplierAssessGrade;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierAssessGradeService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
  *  供应商考核评分表 接口实现类
  */
@Service("ppmSupplierAssessGradeService")
public class PpmSupplierAssessGradeService extends BaseDaoSupper implements IPpmSupplierAssessGradeService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERASSESSGRADE =
            "com.work.ppm.application.model.suppliermanager.PpmSupplierAssessGrade";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmSupplierAssessGrade(PpmSupplierAssessGradeDto ppmSupplierAssessGradeDto) {
        PpmSupplierAssessGrade ppmSupplierAssessGrade =
                JsonUtil.jsonToClass(ppmSupplierAssessGradeDto, PpmSupplierAssessGrade.class);
        // 如果需要请set 主键id
        // ppmSupplierAssessGrade.setId(getNextSequence(PpmSupplierAssessGrade.class));
        ppmSupplierAssessGrade.setCreatedTime(new Date());
        myBatisDao.executionPojo(
                PPMSUPPLIERASSESSGRADE, ppmSupplierAssessGrade, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSupplierAssessGradeDto queryDetail(
            PpmSupplierAssessGradeDto ppmSupplierAssessGradeDto,String uniqueName) {
        PpmSupplierAssessGrade ppmSupplierAssessGrade =
                JsonUtil.jsonToClass(ppmSupplierAssessGradeDto, PpmSupplierAssessGrade.class);
        PpmSupplierAssessGrade data =
                myBatisDao.selectClass(PPMSUPPLIERASSESSGRADE, ppmSupplierAssessGrade,uniqueName);
        return JsonUtil.jsonToClass(data, PpmSupplierAssessGradeDto.class);
    }

    @Override
    public int updatePpmSupplierAssessGrade(PpmSupplierAssessGradeDto ppmSupplierAssessGradeDto) {
        PpmSupplierAssessGrade ppmSupplierAssessGrade =
                JsonUtil.jsonToClass(ppmSupplierAssessGradeDto, PpmSupplierAssessGrade.class);
        ppmSupplierAssessGrade.setModifiedTime(new Date());
        return myBatisDao.executionPojo(
                PPMSUPPLIERASSESSGRADE, ppmSupplierAssessGrade, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

}
