package com.work.ppm.application.services;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.#{model}.#{classNameUpperDto};
import java.util.List;

/**
  *  #{tableComment} 接口描述
  * @author 作者
  */
public interface #{interfaceName} {

    /**
     * 分页
     * @param #{classNameLowerDto} 对象
     * @param pager 分页对象
     */
    void queryPager(#{classNameUpperDto} #{classNameLowerDto}, Pager pager) ;

    /**
     * 添加
     * @param #{classNameLowerDto} 对象
     * @return int
     */
    int add#{classNameUpper}(#{classNameUpperDto}  #{classNameLowerDto}) ;

    /**
     * 查询详情
     * @param #{classNameLowerDto} 对象
     * @return #{classNameUpperDto}
     */
    #{classNameUpperDto} queryDetail(#{classNameUpperDto} #{classNameLowerDto}) ;

    /**
     * 查询详情集合
     * @param #{classNameLowerDto} 对象
     * @return List<#{classNameUpperDto}>
     */
    List<#{classNameUpperDto}> queryDetailList(#{classNameUpperDto} #{classNameLowerDto}) ;


    /**
     * 更新
     * @param #{classNameLowerDto}  对象
     * @return int
     */
    int update#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) ;

    /**
     * 删除
     * @param #{classNameLowerDto} 对象
     * @return int
     */
    int delete#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) ;

    /**
     * 导出
     * @param #{classNameLowerDto} 对象
     * @return int
     */
    List<#{classNameUpperDto}> export#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) ;
}