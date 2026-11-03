package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiItemDetailInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import com.work.ppm.application.model.kpi.PpmKpiItemDetail;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemDetailDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiItemDetailService {

    /**
     * 分页
     * @param ppmKpiItemDetailDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiItemDetailDto ppmKpiItemDetailDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiItemDetailDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) ;

    /**
     * 查询详情
     * @param ppmKpiItemDetailDto 对象
     * @return PpmKpiItemDetailDto
     */
    PpmKpiItemDetailDto queryDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) ;

    /**
     * 更新
     * @param ppmKpiItemDetailDto  对象
     * @return int
     */
    int updatePpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) ;

    /**
     * 删除
     * @param ppmKpiItemDetailDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) ;

    /**
     * 根据rid删除数据
     * @param ppmKpiItemDetail
     * @return int
     */
    int deletePpmKpiItemDetailByRid(PpmKpiItemDetail ppmKpiItemDetail);

    /**
     * 查询指标库详细表信息
     * @param ppmKpiItemDetailInfoDto
     * @return List<PpmKpiItemDetailInfoDto>
     */
    List<PpmKpiItemDetailInfoDto> getPpmKpiItemDetailInfoList(PpmKpiItemDetailInfoDto ppmKpiItemDetailInfoDto);

    /**
     * 更新指标库详细数据
     * @param ppmKpiItemDetailDtos
     * @param userid
     * @param ppmKpiItemdto
     * @return int
     */
    int  updatePpmKpiItemDetails(List<PpmKpiItemDetailDto> ppmKpiItemDetailDtos, String userid,
                                 PpmKpiItemDto ppmKpiItemdto);

    /**
     * 新增指标库详细数据
     * @param ppmKpiItemDetailDtos
     * @param userid
     * @param rid
     * @param itype
     * @return int
     */
    int addPpmKpiItemDetails(List<PpmKpiItemDetailDto> ppmKpiItemDetailDtos,String userid,long rid,String itype);

    /**
     * 更新单条详细数据
     * @param ppmKpiItemDetail
     * @param userid
     * @param itype
     * @return int
     */
    int  updatePpmKpiItemDetail(PpmKpiItemDetail ppmKpiItemDetail,String userid,String itype);


    /**
     * 新增指标库单条详细数据
     * @param ppmKpiItemDetail
     * @param userid
     * @param rid
     * @param itype
     * @return int
     */
    int addPpmKpiItemDetail(PpmKpiItemDetail ppmKpiItemDetail,String userid,long rid,String itype);


    /**
     *  根据standard获得指标库详细数据
     * @param standard
     * @return ppmKpiDepRole
     */
    PpmKpiItemDetail getPpmKpiItemDetailByStandard(String standard);



}
