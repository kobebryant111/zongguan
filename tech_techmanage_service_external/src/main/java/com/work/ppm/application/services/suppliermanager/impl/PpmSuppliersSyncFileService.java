package com.work.ppm.application.services.suppliermanager.impl;

import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncFileDto;
import com.work.ppm.application.enums.DataIsDelEnums;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.suppliermanager.PpmSuppliersSyncFile;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncFileService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
  *  同步采购数据 - 项目供应商信息附件表 接口实现类
  */
@Service("ppmSuppliersSyncFileService")
public class PpmSuppliersSyncFileService extends BaseDaoSupper implements IPpmSuppliersSyncFileService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERSSYNCFILE = "com.work.ppm.application.model.suppliermanager.PpmSuppliersSyncFile";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmSuppliersSyncFile(PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto) {
        PpmSuppliersSyncFile ppmSuppliersSyncFile =
                JsonUtil.jsonToClass(ppmSuppliersSyncFileDto, PpmSuppliersSyncFile.class);
        // 如果需要请set 主键id
        // ppmSuppliersSyncFile.setId(getNextSequence(PpmSuppliersSyncFile.class));
        ppmSuppliersSyncFile.setIsDel(DataIsDelEnums.NO_DEL.getCode());
        ppmSuppliersSyncFile.setCreatedTime(new Date());
        myBatisDao.executionPojo(PPMSUPPLIERSSYNCFILE,
                ppmSuppliersSyncFile, IMyBatisDao.SQL.INSTER, XmlIdEnums.ADD.getCode());
        return 1;
    }

    @Override
    public List<PpmSuppliersSyncFileDto> queryDetailList(PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto) {
        PpmSuppliersSyncFile ppmSuppliersSyncFile =
                JsonUtil.jsonToClass(ppmSuppliersSyncFileDto, PpmSuppliersSyncFile.class);
        List<PpmSuppliersSyncFile> data =
                myBatisDao.selectListT(PPMSUPPLIERSSYNCFILE, ppmSuppliersSyncFile,XmlIdEnums.QUERYS.getCode());
        return JsonUtil.jsonToList(data, PpmSuppliersSyncFileDto.class);
    }

    @Override
    public int updatePpmSuppliersSyncFile(PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto,String uniqueName) {
        PpmSuppliersSyncFile ppmSuppliersSyncFile =
                JsonUtil.jsonToClass(ppmSuppliersSyncFileDto, PpmSuppliersSyncFile.class);
        ppmSuppliersSyncFile.setModifiedTime(new Date());
        return myBatisDao.executionPojo(PPMSUPPLIERSSYNCFILE, ppmSuppliersSyncFile, IMyBatisDao.SQL.UPDATE,uniqueName);
    }

}
