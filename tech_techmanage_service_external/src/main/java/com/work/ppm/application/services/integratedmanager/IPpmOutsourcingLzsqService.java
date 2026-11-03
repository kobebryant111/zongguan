package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.esb.req.PpmKqOutsourcingDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingLzsqInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.commons.pager.Pager;
import com.tansun.workflow.model.TaskBean;

import java.util.List;

/**
 * 外包离职
 *
 */
public interface IPpmOutsourcingLzsqService {

    /**
     * 分页
     *
     * @param ppmOutsourcingLzsq 对象
     * @param pager
     * @throws Exception
     */
    @Deprecated
    void queryPager(PpmOutsourcingLzsq ppmOutsourcingLzsq, Pager pager);

    /**
     * 添加
     *
     * @param ppmOutsourcingLzsq 对象
     * @return int
     */
    @Deprecated
    int addPpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq);

    /**
     * 根据流程编号查询详情
     * @param instanceId 流程编号
     * @return PpmOutsourcingLzsq
     */
    PpmOutsourcingLzsq getInfoByInstanceId(String instanceId);
    /**
     * 查询详情
     *
     * @param ppmOutsourcingLzsq 对象
     * @return * @throws Exception
     */
    PpmOutsourcingLzsq queryDetail(PpmOutsourcingLzsq ppmOutsourcingLzsq);

    /**
     * 查询离职时间/审批结束时间到期数据
     *
     * @param leavingApproveTime 对象
     * @return * @throws Exception
     */
    List<PpmOutsourcingLzsq> queryLeavingDetailList(String leavingApproveTime);

    /**
     * 更新
     *
     * @param ppmOutsourcingLzsq 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq);

    /**
     * 删除
     *
     * @param ppmOutsourcingLzsq 对象
     * @return Integer
     */
    Integer deletePpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq);

    /**
     * 保存数据
     *
     * @param info 对象
     * @param bps session
     * @return PpmOutsourcingLzsq
     */
    PpmOutsourcingLzsq save(PpmOutsourcingLzsq info,BpsWorkFlowDto bps);

    /**
     * 提交流程
     *
     * @param info 对象
     * @param bps  对象
     * @return String
     */
    String outLeaveStart(PpmOutsourcingLzsq info, BpsWorkFlowDto bps);

    /**
     * 二级部经理审批
     *
     * @param info 对象
     * @param bps  对象
     * @return String
     */
    String managerApprove(PpmOutsourcingLzsq info, BpsWorkFlowDto bps);

    /**
     * 获取离场信息
     *
     * @param info 对象
     * @return PpmOutsourcingLzsqInfos
     */
    PpmOutsourcingLzsqInfoDto getBaseInfo(PpmOutsourcingLzsq info);

    /**
     * 是否存在
     *
     * @param jobid
     * @return boolean
     */
    boolean isExist(String jobid);

    /**
     * 工作平台入离场审批通知
     * @param empList
     * @param taskBean
     * @param info
     */
    void handFaceDevice(TaskBean taskBean, PpmOutsourcingLzsq info);

    Integer updatePpmOutsourcingLzsq(PpmOutsourcingBasic basic);
}
