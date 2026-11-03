package com.work.ppm.application.services.kpi;

import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiItemSwitchService {

    /**
     * 分页
     * @param ppmKpiItemSwitchDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiItemSwitchDto ppmKpiItemSwitchDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiItemSwitchDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) ;

    /**
     * 查询详情
     * @param ppmKpiItemSwitchDto 对象
     * @return PpmKpiItemSwitchDto
     */
    @Deprecated
    PpmKpiItemSwitchDto queryDetail(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) ;

    /**
     * 更新
     * @param ppmKpiItemSwitchDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) ;

    /**
     * 删除
     * @param ppmKpiItemSwitchDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) ;

    /**
     * 生成考核配置项开关数据
     * @param ppmKpiItemSwitchDto
     * @param userid
     * @return int
     */
    int createPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,String userid);

    /**
     * 查询考核配置项数据
     * @param ppmKpiItemSwitchDto
     * @return List<PpmKpiItemSwitchDto>
     */
    List<PpmKpiItemSwitchDto> getPpmKpiItemSwitchList(PpmKpiItemSwitchDto ppmKpiItemSwitchDto);


    /**
     * 更新开关数据
     * @param ppmKpiItemSwitchDto
     * @param userid
     * @return int
     */
    int updatePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,String userid);

    /**
     * 增加评价设置
     * @param eos
     * @return int
     */
    int addEvalSet(EosDictEntry eos);

    /**
     * 删除根据dictid和DICTTYPEID进行删除
     * @param eos
     * @return int
     */
    int delEvalSet(EosDictEntry eos);

    /**
     * 更新根据dictid和DICTTYPEID进行更新
     * @param eos
     * @return int
     */
    int updateEvalSet(EosDictEntry eos);

    /**
     * 根据DICTTYPEID进行数据查询
     * @param eos
     * @return List<EosDictEntry>
     */
    List<EosDictEntry> getEosDictEntrys (EosDictEntry eos);

    /**
     * 发起更新发起时间字段
     * @param ppmKpiItemSwitchDto
     * @param userid
     * @return int
     */
    int updateLunchPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,String userid);

    /**
     * 根据条件查询数据返回第一条
     * @param ppmKpiItemSwitchDto
     * @return PpmKpiItemSwitchDto
     */
    PpmKpiItemSwitchDto queryDetailByCondition(PpmKpiItemSwitchDto ppmKpiItemSwitchDto);
}
