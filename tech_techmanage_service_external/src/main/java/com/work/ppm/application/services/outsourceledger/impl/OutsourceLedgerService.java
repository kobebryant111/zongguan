package com.work.ppm.application.services.outsourceledger.impl;

import com.work.ppm.application.dto.outsourceledger.OutsourceLedgerDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.outsourceledger.OutsourceLedger;
import com.work.ppm.application.services.outsourceledger.IOutsourceLedgerService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提前入场申请信息表 服务实现类
 * </p>
 *
 * @since 2022-11-22
 */
@Service
public class OutsourceLedgerService extends BaseDaoSupper implements IOutsourceLedgerService {

    /**
      * 实体类路径
      */
    private static String OUTSOURCELEDGER= "com.work.ppm.application.model.outsourceledger.OutsourceLedger";

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    @Transactional("defaultTx")
    public Boolean importExcel(MultipartFile file, String userid) {
        // excel导入数据
        List<OutsourceLedgerDto> list = ExcelUtil.importExcel(file, OutsourceLedgerDto.class);
        list.stream().forEach(x-> {
//            Date sDate = x.getStartDate();
//            Date sEnd = x.getEndDate();
//            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
//            String strSDate = sdf.format(sDate);
//            String strEDate = sdf.format(sEnd);
//
//            try {
//                Date strSDate1 = sdf.parse(strSDate);
//                Date strEDate1 = sdf.parse(strEDate);
//                x.setStartDate(strSDate1);
//                x.setEndDate(strEDate1);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        });
        if (null == list || list.size() == 0) {
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL, "excel导入失败");
        }
        int c = this.batchAdd(list);
        if (c <= 0 ) {
            return false;
        }
        return true;
    }

    @Override
    public void queryPager(OutsourceLedger outsourceLedger, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(outsourceLedger, OutsourceLedger.class);
        myBatisDao.selectPager(OUTSOURCELEDGER, map, pager, "queryPagerList");
    }

//    @Override
//    public List<OutsourceLedger> queryList(OutsourceLedger outsourceLedger) {
//        List<OutsourceLedger> res = myBatisDao.selectListT(OUTSOURCELEDGER, outsourceLedger, "getList");
//        return res;
//    }

//    @Override
//    public OutsourceLedger queryDetails(OutsourceLedger outsourceLedger) {
//        return myBatisDao.selectClass(OUTSOURCELEDGER, outsourceLedger, "queryDetails");
//    }

    @Override
    public OutsourceLedger queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return myBatisDao.selectClass(OUTSOURCELEDGER, map, "queryDetailsById");
    }

//    @Override
//    public List<OutsourceLedger> queryListByIds(List ids) {
//        return myBatisDao.selectListT(OUTSOURCELEDGER, ids, "selectListByIds");
//    }

//    @Override
//    public long count(HashMap map) {
//        return myBatisDao.selectCount(OUTSOURCELEDGER, map, "selectCount");
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOutsourceLedger(OutsourceLedger outsourceLedger) {
        return addOutsourceLedgerWithId(outsourceLedger);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOutsourceLedgerWithId(OutsourceLedger outsourceLedger) {
        outsourceLedger.setId(getNextSequence(OutsourceLedger.class));
        myBatisDao.executionPojo(OUTSOURCELEDGER, outsourceLedger, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAdd(List<OutsourceLedgerDto> list) {
        //outsourceLedger.setId(getNextSequence(OutsourceLedger.class));
        myBatisDao.executionPojo(OUTSOURCELEDGER, list, IMyBatisDao.SQL.INSTER, "batchAdd");
        return 1;
    }

    @Override
    public Integer updateOutsourceLedger(OutsourceLedger outsourceLedger) {
        return myBatisDao.executionPojo(OUTSOURCELEDGER, outsourceLedger, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

//    @Override
//    public Integer deleteOutsourceLedger(OutsourceLedger outsourceLedger) {
//        return myBatisDao.executionPojo(OUTSOURCELEDGER, outsourceLedger, IMyBatisDao.SQL.DELETE, "deletePojo");
//    }

    @Override
    public Integer deleteByIds(Long ids) {
        if(!StringUtil.isNotEmpty(ids)) {
            return -1;
        }
        OutsourceLedger entity = new OutsourceLedger();
        entity.setId(ids);
        entity.setIsDel(LogicEnums.INVALID.getCode());
        return this.updateOutsourceLedger(entity);
    }

//    @Override
//    public List<OutsourceLedger> selectByMapSmall(HashMap map) {
//        List<OutsourceLedger> res = myBatisDao.selectListT(OUTSOURCELEDGER, map, "selectByMapSmall");
//        return res;
//    }
//    @Override
//    public List<OutsourceLedger> selectByMapBasic(HashMap map) {
//        List<OutsourceLedger> res = myBatisDao.selectListT(OUTSOURCELEDGER, map, "selectByMapBasic");
//        return res;
//    }
//
//    @Override
//    public List<OutsourceLedger> selectByMap(HashMap map) {
//        List<OutsourceLedger> res = myBatisDao.selectListT(OUTSOURCELEDGER, map, "selectByMap");
//        return res;
//    }
}