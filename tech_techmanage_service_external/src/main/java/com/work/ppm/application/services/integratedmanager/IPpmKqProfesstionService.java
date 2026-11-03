package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmKqProfesstionDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKqProfesstionService {

    /**
     * 分页
     * @param ppmKqProfesstionDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKqProfesstionDto ppmKqProfesstionDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKqProfesstionDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKqProfesstion(PpmKqProfesstionDto ppmKqProfesstionDto) ;

    /**
     * 查询详情
     * @param ppmKqProfesstionDto 对象
     * @return PpmKqProfesstionDto
     */
    @Deprecated
    PpmKqProfesstionDto queryDetail(PpmKqProfesstionDto ppmKqProfesstionDto) ;

    /**
     * 更新
     * @param ppmKqProfesstionDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKqProfesstion(PpmKqProfesstionDto ppmKqProfesstionDto) ;

    /**
     * 删除
     * @param ppmKqProfesstionDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKqProfesstion(PpmKqProfesstionDto ppmKqProfesstionDto) ;

    /**
     * 获得文员信息
     * @param ppmKqProfesstionDto
     * @return List<PpmKqProfesstionDto>
     */
    List<PpmKqProfesstionDto> getPpmKqProfesstions(PpmKqProfesstionDto ppmKqProfesstionDto);
}
