package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfoExport;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyInfoInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;

import java.util.List;

/**
 * 人力需求详情
 */
public interface IPpmManpowerdemandApplyInfoService {

    /**
     * 分页
     * @param ppmManpowerdemandApplyInfo 对象
     * @param pager
     * @throws Exception
     */
    void queryPager(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo, Pager pager) ;

    /**
     * 添加
     * @param ppmManpowerdemandApplyInfo 对象
     * @return int
     */
    int addPpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) ;

    /**
     * 查询详情
     * @param ppmManpowerdemandApplyInfo 对象
     * @return PpmManpowerdemandApplyInfo
     */
    PpmManpowerdemandApplyInfo queryDetail(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) ;

    /**
     * 更新
     * @param ppmManpowerdemandApplyInfo 对象
     * @return Integer
     */
    Integer updatePpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) ;

    /**
     * 删除
     * @param ppmManpowerdemandApplyInfo 对象
     * @return Integer
     */
    Integer deletePpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) ;

    /**
     * addPpmIntervieweeInfo
     * @param eeList 集合
     * @param instanceid 流程id
     * @return int
     */
    int addPpmIntervieweeInfo(List<PpmManpowerdemandApplyInfo> eeList,String instanceid,String userid);

    /**
     * queryPagerList
     * @param ppmManpowerdemandApplyInfo 对象
     * @return List<PpmManpowerdemandApplyInfo>
     */
    List<PpmManpowerdemandApplyInfo> queryPagerList(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo);
    /**
     * queryManpowerdemands 面试查询人力需求
     * @param manpowerdemandsInfo 对象
     * @return List<ManpowerdemandsInfoDto>
     */
    List<ManpowerdemandsInfoDto> queryManpowerdemands(ManpowerdemandsInfoDto manpowerdemandsInfo);
    /**
     * updateStatus 更新子表中数据的状态
     * @param ppmManpowerdemandApplyInfo 对象
     * @return int
     */
    int updateStatus(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo);
    /**
     * getList 查询子表数据
     * @param ppmManpowerdemandApplyInfo 对象
     * @return List<PpmManpowerdemandApplyInfo>
     */
    List<PpmManpowerdemandApplyInfo> getList(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo);
    /**
     * deleteByInstanceid 删除子表数据
     * @return int
     */
    int deleteByInstanceid(String instanceId);
    /**
     * queryPagerList 查询子表数据关联主表数据查询（后面可能加上面试数据）
     * @param manpowerdemandsInfoDto 对象
     * @param pager 分页对象
     * @param user session
     * @return  List<PpmManpowerdemandApplyInfoInfoDto>
     */
    List<PpmManpowerdemandApplyInfoInfoDto> queryPagerList(ManpowerdemandsInfoDto manpowerdemandsInfoDto, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 导出
     * @param manpowerdemandsInfoDto 对象
     * @param user session
     * @return List<PpmManpowerdemandApplyInfoExport>
     */
    List<PpmManpowerdemandApplyInfoExport> queryListExport(ManpowerdemandsInfoDto manpowerdemandsInfoDto, CommOperatorDoLoginDto user);

    /**
     * 根据id查询人力需中详细数据
     * @param id 编号
     * @return PpmManpowerdemandApplyInfo
     */
    PpmManpowerdemandApplyInfo queryDetailById(long  id);
    /**
     * 根据id查询人力需中详细数据
     * @param manpowerdemandsInfo 对象
     * @return ManpowerdemandsInfoDto
     */
    ManpowerdemandsInfoDto queryManpowerdemandsById(ManpowerdemandsInfoDto manpowerdemandsInfo);
}
