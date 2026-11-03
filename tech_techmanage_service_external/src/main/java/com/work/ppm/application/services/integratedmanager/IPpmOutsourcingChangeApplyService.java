package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmEmpBaseInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingChangeApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingChangeInfosDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeApply;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos;
import com.work.ppm.application.model.integratedmanager.ext.PpmEmpBaseInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 外包基本信息变更
 */
public interface IPpmOutsourcingChangeApplyService {

    /**
     * 分页
     * @param ppmOutsourcingChangeApply 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingChangeApply ppmOutsourcingChangeApply, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingChangeApply 对象
     * @return int
     */
     @Deprecated
     int addPpmOutsourcingChangeApply(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) ;

    /**
     * 查询详情
     * @param ppmOutsourcingChangeApply 对象
     * @return PpmOutsourcingChangeApply
     */
    @Deprecated
    PpmOutsourcingChangeApply queryDetail(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) ;

    /**
     * 更新
     * @param ppmOutsourcingChangeApply 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmOutsourcingChangeApply(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) ;

    /**
     * 删除
     * @param ppmOutsourcingChangeApply 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmOutsourcingChangeApply(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) ;

    /**
     * 获得行员基本信息
     * @param info 对象
     * @return PpmEmpBaseInfo
     */
    PpmEmpBaseInfo getEmpBaseInfo(PpmEmpBaseInfo info);

    /**
     * 获得外包信息
     * @param jobid 工号
     * @return PpmOutsourcingChangeInfos
     */
    PpmOutsourcingChangeInfos getOutBaseInfo(String jobid);

    /**
     * 保存
     * @param ppmOutsourcingChangeApply 对象
     * @param infos 对象
     * @param user session
     * @return PpmOutsourcingChangeApplyDto
     */
    PpmOutsourcingChangeApplyDto save(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, CommOperatorDoLoginDto user);

    /**
     * addPpmOutsourcingChangeApplyDto 添加修改PpmManpowerdemandApply數據
     * @param ppmOutsourcingChangeApply 对象
     * @return int
     */
    int addPpmOutsourcingChangeApplyDto(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply);

    /**
     * apply 发起
     * @param ppmOutsourcingChangeApply 对象
     * @param infos infos
     * @param bps bps
     * @return String
     */
    String launch(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps);

    /**
     * interviewscore 面试打分
     * @param ppmOutsourcingChangeApply 对象
     * @param infos infos
     * @param bps bps
     * @return String
     */
    String interviewscore(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos,BpsWorkFlowDto bps);

    /**
     * 录入结果
     * @param ppmOutsourcingChangeApply 对象
     * @param infos infos
     * @param bps bps
     * @return String
     */
    String resultinput(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos,BpsWorkFlowDto bps);


    /**
     * 主持工作经理
     * @param ppmOutsourcingChangeApply 对象
     * @param infos infos
     * @param bps bps
     * @return String
     */
    String twoManager(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos,BpsWorkFlowDto bps);

    /**
     * 确认
     * @param ppmOutsourcingChangeApply 对象
     * @param infos infos
     * @param bps bps
     * @return String
     */
    String confirm(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos,BpsWorkFlowDto bps);

    /**
     * deputyDirectorGeneral
     * @param ppmOutsourcingChangeApply 对象
     * @param infos 对象
     * @param bps bps
     * @return String
     */
    String deputyDirectorGeneral(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos,BpsWorkFlowDto bps);

    /**
     * manager
     * @param ppmOutsourcingChangeApply 对象
     * @param infos 对象
     * @param bps bps
     * @return String
     */
    String manager(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos,BpsWorkFlowDto bps);

    /**
     * getFLowInfo
     * @param ppmOutsourcingChangeApply 对象
     * @return PpmOutsourcingChangeApplyDto
     */
    PpmOutsourcingChangeApplyDto getFLowInfo(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply);

    /**
     * 删除流程
     * @param bps bps
     */
    void doDelete(BpsWorkFlowDto bps);

    /**
     * 查询中心部门树
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryCenterDepTree(PpmEmpBaseInfoDto info);

    /**
     * 根据部门查询人员
     * @return List<PpmOutsourcingBasic>
     */
    List<PpmOutsourcingBasicExcelDto> queryOutBaseInfos(PpmOutsourcingBasicExcelDto info);

    /**
     * 根据条件查询外包数据
     * @param info
     * @return PpmOutsourcingBasic
     */
    List<PpmOutsourcingBasic> queryDetailByIdAndName(PpmOutsourcingBasic info);

    /**
     * 外包人员信息变更定时器
     */
    void outInfoChange();

}
