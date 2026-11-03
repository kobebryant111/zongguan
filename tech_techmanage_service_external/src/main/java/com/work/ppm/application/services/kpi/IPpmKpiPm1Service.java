package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.CheckEvalItemsSummary;
import com.work.ppm.application.dto.kpi.MyKpiDataDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmBalanceSummaryDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoTotalDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import com.work.ppm.application.model.kpi.ext.PpmKpiPmInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
  * 考核主表
  */
public interface IPpmKpiPm1Service {

    /**
     * 指标审批
     * @param ppmKpiPmDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int approveKpiDetail(PpmKpiPmDto ppmKpiPmDto, String userid);

    /**
     * 指标退回
     * @param ppmKpiPmDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int rollbackKpiDetail(PpmKpiPmDto ppmKpiPmDto, String userid);

    /**
     * 提交自评数据（全部提交）
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
    int submitSelfEval(PpmKpiPmDto ppmKpiPmDto);

    /**
     * 考核查询页面汇总数据查询
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     * @param userid 对象
     */
    void getPpmKpiPmInfoSummaryPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);

    /**
     * 发起经理半年度KPI自评
     * @param tyear 对象
     * @return int 对象
     */
    int launchSelfEval(String tyear);

    /**
     * 提交半年度考核自评
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
    int submitSelfEvalHalfSelf(PpmKpiPmDto ppmKpiPmDto);

    /**
     * 查询我的考核主记录数据
     * @param myKpiDataDto 对象
     * @return MyKpiDataDto 对象
     */
    MyKpiDataDto getMyKpi(MyKpiDataDto myKpiDataDto);


    /**
     * 获得考核主项的统计项
     * @param ppmKpiPmInfoTotalDto 对象
     * @return PpmKpiPmInfoTotalDto 对象
     */
    PpmKpiPmInfoTotalDto getPpmKpiPmInfoTotalDto(PpmKpiPmInfoTotalDto ppmKpiPmInfoTotalDto);

    /**
     * 查询员工考核查询数据
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getPpmKpiPmInfoSummaryList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 根据工号查询年份
     * @param ppmKpiPmDto 对象
     * @return List<PpmKpiPmDto> 对象
     */
    List<PpmKpiPmDto> getPpmKpiPmTyears(PpmKpiPmDto ppmKpiPmDto);

    /**
     * 查询考核评价信息
     * @param param 对象
     * @return  List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getPpmKpiPmEvalInfo(Map param);


    /**
     * 查询考核评价信息分页
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     * @param userid 对象
     */
    void getPpmKpiPmEvalInfoPage(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);

    /**
     * 员工自己更新角色时需要更新自己相对应的考核数据
     * @param ppmKpiPmDto 对象
     * @param ppmKpiRoleDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int empUpdateRole(PpmKpiPmDto ppmKpiPmDto, PpmKpiRoleDto ppmKpiRoleDto, String userid);

    /**
     * 提交考核评价
     * @param ppmKpiPmDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int  submitKpiEval(PpmKpiPmDto ppmKpiPmDto, String userid);

    /**
     * 获得平衡计分评价汇总数据
     * @param ppmKpiPmBalanceSummaryDto 对象
     * @param pager 对象
     */
    void getBalanceSummaryInfoPage(PpmKpiPmBalanceSummaryDto ppmKpiPmBalanceSummaryDto, Pager pager);

    /**
     * 获得平衡计分评价汇总数据
     * @param ppmKpiPmBalanceSummaryDto 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmBalanceSummaryDto> getBalanceSummaryInfoPage(PpmKpiPmBalanceSummaryDto ppmKpiPmBalanceSummaryDto);

    /**
     * 获得评价项数目
     * @param checkEvalItemsSummary 对象
     * @return CheckEvalItemsSummary 对象
     */
    CheckEvalItemsSummary getCheckEvalItemsSummary(CheckEvalItemsSummary checkEvalItemsSummary);

    /**
     * 根据详细项状态修改主状态信息
     * @return int 对象
     */
     int updateMainStatusByItemstatus(long pid);

    /**
     * 发起履职评估
     * @param detail 对象
     */
     void launchPostEvaluation(PpmKpiPmDetail detail, PpmKpiPmDto ppmKpiPmDto);


    /**
     * 查询预期总分
     * @param ppmKpiPmInfo 对象
     * @return double
     */
     double getExpectScore(PpmKpiPmInfoDto ppmKpiPmInfo);


    /**
     * 修改状态
     * @param details 对象
     * @return int 对象
     */
     int changeStatus(List<PpmKpiPmDetail> details,String userid);
}
