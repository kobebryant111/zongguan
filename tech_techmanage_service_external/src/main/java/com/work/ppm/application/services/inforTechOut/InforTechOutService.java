package com.work.ppm.application.services.inforTechOut;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.inforTechOut.InformatTechnologyApplicatDto;
import com.work.ppm.application.dto.inforTechOut.OssObjectPo;
import com.work.ppm.application.dto.inforTechOut.OssObjectVo;
import com.work.ppm.application.dto.inforTechOut.PhoneApproveDto;
import com.work.ppm.application.dto.inforTechOut.PpmOutsourcingXklx;
import com.work.ppm.application.dto.inforTechOut.XJMultiSequenceRequestDTO;
import com.work.ppm.application.dto.inforTechOut.XKFileUploadDto;
import com.work.ppm.application.dto.rdmanager.FileUploadDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.inforTechOut.InforOutsourcFile;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply;
import com.work.ppm.application.model.rdmanager.RdProjectFile;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.commons.pager.Pager;
import com.tansun.workflow.model.TaskBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface InforTechOutService {
    /**
     * 文件上传
     * @param req
     * @param file
     */
    OssObjectVo upload(XKFileUploadDto req, MultipartFile file);

    /**
     *获取项目编号字符串
     * @param xJMultiSequenceRequestDTO
     * @return
     */
    String getNextSeqNo(XJMultiSequenceRequestDTO xJMultiSequenceRequestDTO);

    /**
     * 查询项目文件
     * @param outReqid
     * @return
     */
    List<InforOutsourcFile> queryInforOutsourcFile(@NotBlank(message = "研发项目id不能为空") String outReqid);

    /**
     * 下载文件
     * @param id
     * @return
     */
    void download(@NotBlank(message = "研发项目id不能为空")String id, HttpServletResponse response) throws Exception;

    /**
     * 删除文件
     * @param id
     */
    void delete(@NotBlank(message = "研发项目id不能为空") String id);

    /**
     * 获取申请部门负责人
     * @param depid
     * @return
     */
    List<CommEmployee> getAplyDirector(@NotBlank(message = "研发项目id不能为空") String depid);

    /**
     * 获取用工部门负责人
     * @param outDepid
     * @return
     */
    List<CommEmployee> getOutDirector(@NotBlank(message = "用工部门负责人部门id不能为空") String outDepid);

    /**
     * 保存信息科技外包立项申请
     * @param itadto
     */
    PpmOutsourcingXklx saveInforTechApply(PpmOutsourcingXklx itadto, BpsWorkFlowDto bps);

    /**
     * 提交信息科技外包立项流程
     * @param infoDto
     * @param bps
     * @return
     */
    String outLeaveStart(PpmOutsourcingXklx infoDto, BpsWorkFlowDto bps);

    /**
     * 待审批
     * @param info
     * @return
     */
    PpmOutsourcingXklx getDb(PpmOutsourcingXklx info);

    /**
     * 申请部门负责人审批
     * @param ppmOutsourcingXklx
     * @param bps
     * @return
     */
    String managerApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps);

    /**
     * 用工部门负责人审批
     * @param ppmOutsourcingXklx
     * @param bps
     * @return
     */
    String hiringDepApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps);

    /**
     * 金融科技部经办人审核
     * @param ppmOutsourcingXklx
     * @param bps
     * @return
     */
    String financeApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps);

    /**
     * 风险管理部经办人审核
     * @param ppmOutsourcingXklx
     * @param bps
     * @return
     */
    String riskHandApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps);

    /**
     * 人力资源部负责人审批
     * @param ppmOutsourcingXklx
     * @param bps
     * @return
     */
    String resPerApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps);

    /**
     * 分页查询
     * @param ppmOutsourcingXklx
     * @param pager
     * @param user
     */
    void queryList(PpmOutsourcingXklx ppmOutsourcingXklx, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 根据流程instanceid查询审批信息
     * @param info
     * @return
     */
    List<PpmFlowLogDto> getSignDetail(PpmFlowLog info);

    /**
     * 手机工作平台查询接口
     * @param info
     * @param response
     * @return
     */
    PpmOutsourcingXklx getPhoneDb(PpmOutsourcingXklx info);

    /**
     * 手机工作平台审批接口
     * @param info
     * @param bps
     * @return
     */
    String phoneApprove(PhoneApproveDto info, BpsWorkFlowDto bps);
    /**
     * 删除
     *
     * @param accessapply 对象
     * @return Integer
     */
    int deletePpmOutsourcingXklx(PpmOutsourcingXklx accessapply);
}
