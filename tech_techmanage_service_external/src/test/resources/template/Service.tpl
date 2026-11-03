package com.work.ppm.application.services.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.#{model}.#{classNameUpper};
import com.work.ppm.application.dto.#{model}.#{classNameUpperDto};
import com.work.ppm.application.services.#{model}.#{interfaceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

/**
  *  #{tableComment} 接口实现类
  * @author 作者
  */
@Service("#{serviceLower}")
public class #{serviceUpper} extends BaseDaoSupper implements #{interfaceName} {
    /**
      * 实体类路径
      */
    private static String #{classNameUpperAll} = "com.work.ppm.application.model.#{model}.#{classNameUpper}";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(#{classNameUpperDto} #{classNameLowerDto}, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(#{classNameLowerDto},#{classNameUpperDto}.class);
        myBatisDao.selectPager(#{classNameUpperAll}, map,pager,"queryPagerList");
    }

    @Override
    public int add#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) {
        #{classNameUpper} #{classNameLower} =
            JsonUtil.jsonToClass(#{classNameLowerDto}, #{classNameUpper}.class);
        // 如果需要请set 主键id
        // #{classNameLower}.setId(DateUtil.getDateRandom());
        myBatisDao.executionPojo(#{classNameUpperAll}, #{classNameLower}, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public #{classNameUpperDto} queryDetail(#{classNameUpperDto} #{classNameLowerDto}) {
        #{classNameUpper} #{classNameLower} =
            JsonUtil.jsonToClass(#{classNameLowerDto}, #{classNameUpper}.class);
        #{classNameUpper} data =
            myBatisDao.selectClass(#{classNameUpperAll}, #{classNameLower},"queryDetail");
        return JsonUtil.jsonToClass(data, #{classNameUpperDto}.class);
    }

    @Override
    public List<#{classNameUpperDto}> queryDetailList(#{classNameUpperDto} #{classNameLowerDto}) {
        #{classNameUpper} #{classNameLower} =
            JsonUtil.jsonToClass(#{classNameLowerDto}, #{classNameUpper}.class);
        List<#{classNameUpper}> data =
            myBatisDao.selectListT(#{classNameUpperAll}, #{classNameLower},"getList");
        return JsonUtil.jsonToList(data, #{classNameUpperDto}.class);
    }

    @Override
    public int update#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) {
        #{classNameUpper} #{classNameLower} =
            JsonUtil.jsonToClass(#{classNameLowerDto}, #{classNameUpper}.class);
        return myBatisDao.executionPojo(
            #{classNameUpperAll}, #{classNameLower}, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int delete#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) {
        #{classNameUpper} #{classNameLower} =
            JsonUtil.jsonToClass(#{classNameLowerDto}, #{classNameUpper}.class);
        return myBatisDao.executionPojo(
            #{classNameUpperAll}, #{classNameLower}, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<#{classNameUpperDto}> export#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) {
        List<#{classNameUpper}> #{classNameLower}List =
            JsonUtil.jsonToList(#{classNameLowerDto}, #{classNameUpper}.class);
        List<#{classNameUpper}> data =
            myBatisDao.selectListT(#{classNameUpperAll}, #{classNameLower}List,"getList");
        return JsonUtil.jsonToList(data, #{classNameUpperDto}.class);
    }
}