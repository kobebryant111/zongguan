package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineOrgareaDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmOnlineOrgareaService {

    /**
     * 分页
     * @param ppmOnlineOrgareaDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmOnlineOrgareaDto ppmOnlineOrgareaDto, Pager pager) ;

    /**
     * 添加
     * @param ppmOnlineOrgareaDto 对象
     * @return int
     */
    int addPpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) ;

    /**
     * 查询详情
     * @param ppmOnlineOrgareaDto 对象
     * @return PpmOnlineOrgareaDto
     */
    @Deprecated
    PpmOnlineOrgareaDto queryDetail(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) ;

    /**
     * 更新
     * @param ppmOnlineOrgareaDto  对象
     * @return int
     */
    int updatePpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) ;

    /**
     * 删除
     * @param ppmOnlineOrgareaDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) ;

    /**
     * 添加(批量)
     * @param ppmOnlineOrgareaDtos 对象
     * @return int
     */
    int addPpmOnlineOrgareaBatch(PpmOnlineOrgareaDto[] ppmOnlineOrgareaDtos);

    /**
     * 根据上线编号更新
     * @param ppmOnlineOrgareaDto
     * @return int
     */
    int updateOrgareaByOnlineno(PpmOnlineOrgareaDto ppmOnlineOrgareaDto);
    /**
     * 根据上线编号查询系统编号
     * @param onlineno 上线编号
     * @return list
     */
    List<PpmOnlineOrgareaDto> querySysNoForOnlineno(String onlineno);
}
