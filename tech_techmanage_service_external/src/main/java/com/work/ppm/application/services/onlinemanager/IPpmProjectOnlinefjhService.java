package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmProjectOnlinefjhDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmProjectOnlinefjhService {

    /**
     * 分页
     * @param ppmProjectOnlinefjhDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto, Pager pager) ;

    /**
     * 添加
     * @param ppmProjectOnlinefjhDto 对象
     * @return int
     */
    int addPpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) ;

    /**
     * 查询详情
     * @param ppmProjectOnlinefjhDto 对象
     * @return PpmProjectOnlinefjhDto
     */
    PpmProjectOnlinefjhDto queryDetail(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) ;

    /**
     * 更新
     * @param ppmProjectOnlinefjhDto  对象
     * @return int
     */
    int updatePpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) ;

    /**
     * 删除
     * @param ppmProjectOnlinefjhDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) ;

    /**
     * 查询奋进号上线流程
     * @param demandId
     * @return List<PpmProjectOnlinefjhDto>
     */
    List<PpmProjectOnlinefjhDto> queryNewFjhOnileFlow(String demandId);

    /**
     * 查询上线信息
     * @param ppmProjectOnlinefjhDto
     * @return PpmProjectOnlinefjhDto
     */
    PpmProjectOnlinefjhDto getOnlineInfo(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto);

    /**
     * 查询奋进号上线信息
     * @param ppmProjectOnlinefjhDto
     * @return PpmProjectOnlinefjhDto
     */
    PpmProjectOnlinefjhDto queryDetailByInstanceid(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) ;

    /**
     * 根据上线编号查询流程ID
     * @param onlinefjh
     * @return String
     */
    String queryInstanceidByOnlinenum(PpmProjectOnlinefjhDto onlinefjh) ;

    /**
     * 查询上线信息(手机端)
     * @param ppmProjectOnlinefjhDto
     * @return PpmProjectOnlinefjhDto
     */
    PpmProjectOnlinefjhDto getMobileOnlineInfo(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) ;
}
