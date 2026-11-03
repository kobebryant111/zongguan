package com.work.ppm.application.api.integratedmanager;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.bankmamager.PpmOutSorceInterviewDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.FileInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAchievementDto;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttatchManagementDto;
import com.work.ppm.application.dto.vendorcontract.VendorContractDto;
import com.work.ppm.application.model.bankmanager.PpmOutSourceInterview;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.model.vendormanager.VendorContract;
import com.work.ppm.application.services.bankmanager.IPpmOutSourceInterviewService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingAccessapplyService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.vendormanager.IVendorContractService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.constant.FileProperties;
import com.work.ppm.commons.constant.SystemConstant;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 新外包准入流程
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmOutsourcingAccessapply")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingAccessapplyApi"}, description = "新外包准入流程")
public class PpmOutsourcingAccessapplyApi extends BaseUploadApi {
    /**
     * 新外包准入流程接口
     */
    @Autowired
    private IPpmOutsourcingAccessapplyService ppmOutsourcingAccessapplyService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private Environment env;

    @Autowired
    private IVendorContractService vendorContractService;

    @Autowired
    private IAttatchManagementService attatchManagementService;

    @Autowired
    private IPpmOutSourceInterviewService ppmOutSourceInterviewService;

    /**
     * 保存
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult<PpmOutsourcingAccessapplyDto>
     */
    @PostMapping("add")
    @ApiOperation("保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "maritalStatus", value = "婚姻状况", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "purchaseItem", value = "采购项目", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "creditReport", value = "征信报告", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sociaiSecrityPaymentProve", value = "社保缴纳证明/合同", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dueDiligenceReport", value = "尽调报告", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "qTFile", value = "其他", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "confidentialityCommitment", value = "保密承诺书", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "otherFile", value = "其他证明文件", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "systemPrincipal", value = "系统负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "编号", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flowId", value = "流程定义名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "外包人员名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourceNo", value = "外包公司编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "certifType", value = "证件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "certifNumber", value = "证件号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "education", value = "学历", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "specialty", value = "专业信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "school", value = "毕业学院", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "生日", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nativeplace", value = "籍贯", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "organizers", value = "行内负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectno", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "managerUserid", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "staffPosition", value = "人员位置（即所在大楼）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourceType", value = "外包方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userType", value = "结算方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyreg", value = "虚拟机配置要求", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appltbegTime", value = "实际入职时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "iscreditquery", value = "是否申请虚拟桌面", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "queryresult", value = "风控结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "whetherAttendance", value = "是否考勤(0:不考勤，1：考勤)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "creators", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodTime", value = "最后审批时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastmodUser", value = "最后一个审批人工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "childmanageruserid", value = "节点审批人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "insteanceTitle", value = "流程标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dutyType", value = "轮班类型 1:正常 2:轮班制", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "worktime", value = "上下班时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "addreason", value = "增加人员原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isOtherProject", value = "是否为其他项目", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sdprojectname", value = "其他项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sdmanageruserid", value = "其他项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workContent", value = "工作内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sociaiSecrityPayment", value = "社保缴纳", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "systemnos", value = "系统名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "systemuserids", value = "系统名称编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourcingcategories", value = "外包分类", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "notattendancereason", value = "不参加考勤的原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "svnLimit", value = "是否开通SVN权限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "svnRoad", value = "SVN地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applysvnno", value = "SVN流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupId", value = "小组", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "issatisfyrequire", value = "是否复核准入要求（0：否，1：是）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isnewdrawcomputer", value = "是否领新电脑", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pricechangereason", value = "价格变更原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractId", value = "所属合同ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iskey", value = "是否关键人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceType", value = "考勤类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "policecheck", value = "公安核查", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "courtinfo", value = "法院信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "businessinfo", value = "工商信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iseverentry", value = "是否重复入职", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "personcredit", value = "个人征信", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "faceimage", value = "人脸照片路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "partnerName", value = "配偶姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcardNumber", value = "配偶证件号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "spouseCertificateType", value = "配偶证件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "家庭地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "certificate", value = "所获证书", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "learnExperience", value = "学习经历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainJobs", value = "主要工作经历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainItem", value = "项目经历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainReward", value = "奖励记录", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainFamily", value = "家庭成员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialAuth", value = "特殊权限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "authDes", value = "权限描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "whetherMeetsClaim", value = "是否符合准入要求", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "meetRequirementsReason", value = "符合要求的原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specifyChargeGeneral", value = "指定分管总经理 ", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "virtualDesktopFlowId", value = "虚拟桌面流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "socialSecurityFlowId", value = "社保缴纳流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "entryTime", value = "入职时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "newContractId", value = "合同id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ceneralOfficeMotion", value = "总办议案id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scoreList", value = "外包面试官", dataType = "list", paramType = "query"),
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> addOutsourcingApply(HttpServletRequest request,
                                                                       @RequestBody PpmOutsourcingAccessapplyDto applyDto) throws Exception {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        BpsWorkFlowDto bps = applyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        PpmOutsourcingAccessapplyDto data = ppmOutsourcingAccessapplyService.add(applyDto, bps);
        if (null == data) {
            result.success("更新成功");
        }
        if (null != data) {
            result.success("添加成功");
            result.setResults(data);
        }
        return result;
    }


    /**
     * 获取评分
     * @param universalToolUse
     * @param projectManageKnowledge
     * @param commExpression
     * @param projectExperience
     * @param projectScaleXmjl
     * @param projectScaleJcjl
     * @param codeDevelopment
     * @param architectureDesign
     * @param profesKnowledge
     * @param standCondition
     * @param productAbility
     * @return
     */
    @GetMapping("/getScore")
    @ApiOperation("获取评分")
    public ApiResult<String> getScore(@RequestParam("universalToolUse") String universalToolUse ,
                                      @RequestParam("projectManageKnowledge") String projectManageKnowledge ,
                                      @RequestParam("commExpression") String commExpression ,
                                      @RequestParam("projectExperience") String projectExperience ,
                                      @RequestParam("projectScaleXmjl") String projectScaleXmjl ,
                                      @RequestParam("projectScaleJcjl") String projectScaleJcjl ,
                                      @RequestParam("codeDevelopment") String codeDevelopment ,
                                      @RequestParam("architectureDesign") String architectureDesign ,
                                      @RequestParam("profesKnowledge") String profesKnowledge ,
                                      @RequestParam("standCondition") String standCondition ,
                                      @RequestParam("msemployRole") String msemployRole ,
                                      @RequestParam("productAbility") String productAbility
    ){
        ApiResult result = new ApiResult<>();
        PpmOutSorceInterviewDto ppmOutSorceInterviewDto = new PpmOutSorceInterviewDto();
        ppmOutSorceInterviewDto.setUniversalToolUse(universalToolUse);
        ppmOutSorceInterviewDto.setProjectManageKnowledge(projectManageKnowledge);
        ppmOutSorceInterviewDto.setCommExpression(commExpression);
        ppmOutSorceInterviewDto.setProjectExperience(projectExperience);
        ppmOutSorceInterviewDto.setProjectScaleXmjl(projectScaleXmjl);
        ppmOutSorceInterviewDto.setProjectScaleJcjl(projectScaleJcjl);
        ppmOutSorceInterviewDto.setCodeDevelopment(codeDevelopment);
        ppmOutSorceInterviewDto.setArchitectureDesign(architectureDesign);
        ppmOutSorceInterviewDto.setProfesKnowledge(profesKnowledge);
        ppmOutSorceInterviewDto.setStandCondition(standCondition);
        ppmOutSorceInterviewDto.setProductAbility(productAbility);
        ppmOutSorceInterviewDto.setMsemployRole(msemployRole);
        String score = ppmOutsourcingAccessapplyService.getScore(ppmOutSorceInterviewDto);
        if (StringUtil.isStringNotEmpty(score)) {
            result.success("分数计算成功");
            result.setResults(score);
        }
        return result;
    }




    /**
     * 发起申请
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("liuzhuan")
    @ApiOperation("发起申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eitquetteAchievement", value = "礼仪成绩", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "eitquetteTime", value = "礼仪时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "interviewSumAchievement", value = "面试总成绩", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "interviewResult", value = "面试结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneInterviewer", value = "面试官1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oneComments", value = "面试评语1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoInterviewer", value = "面试官2", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "twoComments", value = "面试评语2", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maritalStatus", value = "婚姻状况", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "purchaseItem", value = "采购项目", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "creditReport", value = "征信报告", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sociaiSecrityPaymentProve", value = "社保缴纳证明/合同", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dueDiligenceReport", value = "尽调报告", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "qTFile", value = "其他", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "confidentialityCommitment", value = "保密承诺书", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "otherFile", value = "其他证明文件", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "systemPrincipal", value = "系统负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "编号", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flowId", value = "流程定义名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "外包人员名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourceNo", value = "外包公司编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "certifType", value = "证件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "certifNumber", value = "证件号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "education", value = "学历", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "specialty", value = "专业信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "school", value = "毕业学院", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "生日", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nativeplace", value = "籍贯", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "politicalStatus", value = "政治面貌", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "organizers", value = "行内负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectno", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "managerUserid", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "staffPosition", value = "人员位置（即所在大楼）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourceType", value = "外包方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userType", value = "结算方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyreg", value = "虚拟机配置要求", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appltbegTime", value = "实际入职时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "iscreditquery", value = "是否申请虚拟桌面", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "queryresult", value = "风控结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "whetherAttendance", value = "是否考勤(0:不考勤，1：考勤)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "creators", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodTime", value = "最后审批时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastmodUser", value = "最后一个审批人工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "childmanageruserid", value = "节点审批人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "insteanceTitle", value = "流程标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dutyType", value = "轮班类型 1:正常 2:轮班制", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "worktime", value = "上下班时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "addreason", value = "增加人员原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isOtherProject", value = "是否为其他项目", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sdprojectname", value = "其他项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sdmanageruserid", value = "其他项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workContent", value = "工作内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sociaiSecrityPayment", value = "社保缴纳", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "systemnos", value = "系统名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "systemuserids", value = "系统名称编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourcingcategories", value = "外包分类", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "notattendancereason", value = "不参加考勤的原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "svnLimit", value = "是否开通SVN权限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "svnRoad", value = "SVN地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applysvnno", value = "SVN流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupId", value = "小组", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "issatisfyrequire", value = "是否复核准入要求（0：否，1：是）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isnewdrawcomputer", value = "是否领新电脑", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pricechangereason", value = "价格变更原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractId", value = "所属合同ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iskey", value = "是否关键人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceType", value = "考勤类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "policecheck", value = "公安核查", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "courtinfo", value = "法院信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "businessinfo", value = "工商信息", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iseverentry", value = "是否重复入职", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "personcredit", value = "个人征信", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "faceimage", value = "人脸照片路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "partnerName", value = "配偶姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcardNumber", value = "配偶证件号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "spouseCertificateType", value = "配偶证件类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "家庭地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "certificate", value = "所获证书", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "learnExperience", value = "学习经历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainJobs", value = "主要工作经历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainItem", value = "项目经历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainReward", value = "奖励记录", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainFamily", value = "家庭成员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specialAuth", value = "特殊权限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "authDes", value = "权限描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "whetherMeetsClaim", value = "是否符合准入要求", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "meetRequirementsReason", value = "符合要求的原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "specifyChargeGeneral", value = "指定分管总经理 ", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "virtualDesktopFlowId", value = "虚拟桌面流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "socialSecurityFlowId", value = "社保缴纳流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "entryTime", value = "入职时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "newContractId", value = "合同id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ceneralOfficeMotion", value = "总办议案id", dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "processInstDesc", value = "流程描述,例如：发起申请 节点", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "username", value = "申请人名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "processDefName", value = "流程定义名称 (com.work.ppm.bps.glzxzdgz)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "processInstName", value = "流程实例名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "ppmOutsourcingApplyDto", value = "json数据", dataType = "json", paramType = "body",
                    defaultValue = "\"{\\\"bps\\\":{\\\"processInstId\\\":\\\"1238186\\\", \\\"activityInstId\\\":6890765, \\\"conclusion\\\":\\\"01\\\", \\\"applydesc\\\":\\\"结果录入\\\"}}\""),
    })
    public ApiResult<String> liuzhuan(@RequestBody PpmOutsourcingAccessapplyDto applyDto, HttpServletRequest request) throws Exception {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        BpsWorkFlowDto bps = applyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = ppmOutsourcingAccessapplyService.liuzhuan(applyDto, bps);
        result.success(msg);

        return result;
    }

    /**
     * 二级部经理审批 20260312 需求变更为总经理室审批
     *
     * @param ppmOutsourcingApply 对象
     * @param request             request
     * @return ApiResult<String>
     */
    @PostMapping("twoManagerAudit")
    @ApiOperation("二级部经理审批")
    public ApiResult<String> twoManagerAudit(@RequestBody PpmOutsourcingAccessapplyDto ppmOutsourcingApply
            , HttpServletRequest request) throws Exception {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = null;
        if (null == ppmOutsourcingApply.getBps()) {
            bps = new BpsWorkFlowDto();
        } else {
            bps = ppmOutsourcingApply.getBps();
        }
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingAccessapplyService.twoManagerAudit(ppmOutsourcingApply, bps);
        result.success(msg);
        return result;
    }

    /**
     * 人事审批
     *
     * @param ppmOutsourcingApply 对象
     * @param request             request
     * @return ApiResult<String>
     */
    @PostMapping("zhbrsAudit")
    @ApiOperation("人事审批")
    public ApiResult<String> zhbrsAudit(@RequestBody PpmOutsourcingAccessapplyDto ppmOutsourcingApply, HttpServletRequest request) throws Exception {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = null;
        if (null == ppmOutsourcingApply.getBps()) {
            bps = new BpsWorkFlowDto();
        } else {
            bps = ppmOutsourcingApply.getBps();
        }
        bps.setUserId(user.getUserid());
        String msg = ppmOutsourcingAccessapplyService.rsAudit(ppmOutsourcingApply, bps);
        result.success(msg);
        return result;
    }

    /**
     * 风控征信审批
     *
     * @param ppmOutsourcingApply 对象
     * @param request             request
     * @return ApiResult<String>
     */
    @PostMapping("zhbfkzxAudit")
    @ApiOperation("风控征信审批")
    public ApiResult<String> zhbfkzxAudit(@RequestBody PpmOutsourcingAccessapplyDto ppmOutsourcingApply, HttpServletRequest request) throws Exception {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = null;
        if (null == ppmOutsourcingApply.getBps()) {
            bps = new BpsWorkFlowDto();
        } else {
            bps = ppmOutsourcingApply.getBps();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = ppmOutsourcingAccessapplyService.fkzxAudit(ppmOutsourcingApply, bps);
        result.success(msg);
        return result;
    }

    /**
     * 卡牌和桌牌预览
     *
     * @param accessapply 对象
     * @return ApiResult
     */
    @GetMapping("card/preview")
    @ApiOperation(value = "卡牌和桌牌预览")
    public ApiResult cardPreview(PpmOutsourcingAccessapplyDto accessapply) {
        ApiResult result = new ApiResult();
        // 取卡片信息
        Map<String, String> cardInfo = ppmOutsourcingAccessapplyService.getCardInfo(accessapply);
        PpmDocument item = ppmDocumentService.getImageUrl(cardInfo.get("faceImage"));
        cardInfo.put("headImage", item.getDocumentno());
        result.setResults(cardInfo);
        result.success("操作成功");
        return result;
    }

    /**
     * 卡牌和桌牌下载
     * @param accessapply 对象
     * @param response 对象
     * @throws IOException io异常
     */
    @GetMapping("card/download")
    @ApiOperation(value = "卡牌和桌牌下载")
    public void cardDownload(
            PpmOutsourcingAccessapplyDto accessapply, HttpServletResponse response) throws IOException {
        InputStream inputStream = null;
        try {
            // 取卡片信息
            Map<String, String> cardInfo = ppmOutsourcingAccessapplyService.getCardInfo(accessapply);
            String root = fileProperties.getUploadPath() + "ppmtest/";
            PpmDocument item = ppmDocumentService.getImageUrl(cardInfo.get("faceImage"));
            String filePath = root + item.getStockpath() + item.getTag2();

            String fileName = "卡牌-" + System.currentTimeMillis() + ".pdf";
            String profiles = env.getProperty(SystemConstant.SPRING_PROFILES_ACTIVE);
            String targetFile = System.getProperty("java.io.tmpdir") + fileName;
            // 生产环境运行用户非ROOT，临时目录操作不了
            if (SystemConstant.SYSTEM_ENV_PROD.equals(profiles)) {
                targetFile = fileProperties.getTempPath() + fileName;
            }
            PdfUtils.createCardPdf(cardInfo, filePath, targetFile);
            response.setHeader("Content-disposition", "attachment;filename=\"" +
                    URLEncoder.encode("" + fileName + "", "UTF-8") + "\"");
            OutputStream out = response.getOutputStream();
            inputStream = new FileInputStream(targetFile);
            IOUtils.copy(inputStream, out);
            inputStream.close();
            out.flush();
        } catch (Exception e) {
            log.error("生成卡片信息失败,{}", e);
            throw new BizException(ApiCode.FAIL, e.getMessage());
        } finally {
            inputStream.close();
        }
    }

    /**
     * 删除
     *
     * @param accessapplyDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmInterviewApply(PpmOutsourcingAccessapplyDto accessapplyDto) {
        ApiResult result = new ApiResult<>();
        PpmOutsourcingAccessapply accessapply = JsonUtil.jsonToClass(accessapplyDto,PpmOutsourcingAccessapply.class);

        int id = ppmOutsourcingAccessapplyService.deletePpmOutsourcingAccessapply(accessapply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmOutsourcingApply 对象
     * @return ApiResult<PpmOutsourcingAccessapplyDto>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceId", value = "流程id", paramType = "query", dataType = "string", required = true)
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> queryDetail(PpmOutsourcingAccessapplyDto ppmOutsourcingApply) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        PpmOutsourcingAccessapplyDto item = ppmOutsourcingAccessapplyService.queryDetail(ppmOutsourcingApply);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
    /*
     * 查询流程编号
     *
     * @param ppmOutsourcingApply 对象
     * @return ApiResult<PpmOutsourcingAccessapplyDto>

    @GetMapping(value = "queryInstanceId")
    @ApiOperation(value = "queryInstanceId", notes = "查询详情")
    public ApiResult<PpmOutsourcingAccessapplyDto> queryInstanceId(PpmOutsourcingAccessapplyDto ppmOutsourcingApply) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        PpmOutsourcingAccessapplyDto item =
                ppmOutsourcingAccessapplyService.queryInstanceIdForJobId(ppmOutsourcingApply.getJobId());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }*/

    /**
     * 创建joid
     *
     * @return ApiResult<PpmOutsourcingAccessapplyDto>
     */
    @GetMapping("getJobid")
    @ApiOperation("创建joid-员工工号")
    public ApiResult<PpmOutsourcingAccessapplyDto> getJoid() {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        PpmOutsourcingAccessapplyDto dto = ppmOutsourcingAccessapplyService.createJoid();
        result.setResults(dto);
        result.success("获取成功");
        return result;
    }

    /**
     * 创建门禁卡编号
     * @param accessapplyDto 对象
     * @return ApiResult
     */
    @GetMapping("getEntranceCard")
    @ApiOperation("创建门禁卡编号")
    public ApiResult getEntranceCard(PpmOutsourcingAccessapplyDto accessapplyDto) {
        ApiResult result = new ApiResult<>();
        PpmOutsourcingAccessapplyDto item = ppmOutsourcingAccessapplyService.queryDetail(accessapplyDto);
        if (ObjectUtils.isEmpty(item)) {
            throw new BizException(ApiCode.FAIL, "参数错误");
        }
        if (StringUtil.isNotEmpty(item.getEntranceCard())) {
            result.setResults(item.getEntranceCard());
        } else {
            String prex = "JZ" + DateUtil.dateToStr(item.getEntryTime(), "yyyyMMdd");
            // 查询门禁编码同日期存在的数量
            long count = ppmOutsourcingAccessapplyService.countEntranceCard(prex);
            // 编码固定3位
            DecimalFormat decimalFormat = new DecimalFormat("000");
            String entranceCard = prex + decimalFormat.format(count + 1);
            result.setResults(entranceCard);
        }
        result.success("获取成功");
        return result;
    }

    /**
     * 图片上传
     *
     * @param objectNo  唯一编号(这里一般指流程id
     * @param catalogNo 业务字典编号
     * @param type      类型
     * @param file      文件
     * @param request   request
     * @return ApiResult
     */
    @PostMapping("upload")
    @ApiOperation(value = "资料上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectNo", value = "唯一编号(这里一般指流程id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query", defaultValue = "AI"),
            @ApiImplicitParam(name = "catalogNo", value = "业务字典编号", dataType = "string", paramType = "query", defaultValue = "CATALOG2014091500001"),
    })
    public ApiResult uploadResurous(String objectNo, String type, String catalogNo,
                                    @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        ApiResult result = new ApiResult();
        super.setFile(file);
        String mesage = super.uploadImage(objectNo, "RLZP", catalogNo, request);
        result.setResults(mesage);
        result.success("上传成功");
        return result;
    }

    /**
     * 一键下载文件
     *
     * @param instanceId
     * @param response   response
     */
    @GetMapping("download/{instanceId}")
    @ApiOperation(value = "download", notes = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceId", value = "流程id", paramType = "query", dataType = "string", required = true)
    })
    public void download(@PathVariable String instanceId, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
        InputStream inputStream = null;
        try {
            PpmOutsourcingAccessapplyDto accessapplyDto = new PpmOutsourcingAccessapplyDto();
            accessapplyDto.setInstanceId(instanceId);
            PpmOutsourcingAccessapplyDto item = ppmOutsourcingAccessapplyService.queryDetail(accessapplyDto);
            if (null != item) {
                List<String> fileList = Lists.newArrayList();
                fileList.addAll(Arrays.asList(item.getCreditReport().split(",")));
                fileList.addAll(Arrays.asList(item.getSociaiSecrityPaymentProve().split(",")));
                fileList.addAll(Arrays.asList(item.getConfidentialityCommitment().split(",")));
                fileList.addAll(Arrays.asList(item.getOtherFile().split(",")));
                fileList.addAll(Arrays.asList(item.getDueDiligenceReport().split(",")));
                fileList.addAll(Arrays.asList(item.getQtFile().split(",")));
                fileList.add(item.getFaceimage());

                List<FileInfoDto> sourceFiles = Lists.newArrayList();
                PpmDocumentDto documentDto;
                for (String document : fileList) {
                    documentDto = ppmDocumentService.getDownloadFilePath(document, null, null);
                    FileInfoDto fileInfo = new FileInfoDto();
                    fileInfo.setFileName(documentDto.getDocname());
                    fileInfo.setFilePath(documentDto.getTag1());
                    sourceFiles.add(fileInfo);
                }
                List<FileInfoDto> entryList = sourceFiles.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(FileInfoDto::getFileName))), ArrayList::new));
                String fileName = "准入材料-" + System.currentTimeMillis() + ".zip";
                String profiles = env.getProperty(SystemConstant.SPRING_PROFILES_ACTIVE);
                String zipFile = System.getProperty("java.io.tmpdir") + fileName;
                // 生产环境运行用户非ROOT，临时目录操作不了
                if (SystemConstant.SYSTEM_ENV_PROD.equals(profiles)) {
                    zipFile = fileProperties.getTempPath() + fileName;
                }
                log.info("===========》zipFile:：{}",zipFile);
                CompressUtil.compress(entryList, zipFile);
                OutputStream out = response.getOutputStream();
                log.warn("===========》下载地址：{}", zipFile);
                inputStream = new FileInputStream(zipFile);
                response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode("" + fileName + "", "UTF-8") + "\"");
                IOUtils.copy(inputStream, out);
                inputStream.close();
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            log.error("文件下载异常:{}", e);
            throw new BizException(e);
        } finally {
            inputStream.close();
        }

    }

    /**
     * 查询风控排查
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @return ApiResult
     */
    @GetMapping("getWindControl")
    @ApiOperation(value = "查询风控排查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "外包名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "certifNumber", value = "身份证", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> getWindControl(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        AssertUtil.notNull(ppmOutsourcingAccessapplyDto.getUserName(), "[UserName]不能为空");
        AssertUtil.notNull(ppmOutsourcingAccessapplyDto.getCertifNumber(), "[CertifNumber]不能为空");
        PpmOutsourcingAccessapplyDto item = new PpmOutsourcingAccessapplyDto();
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 是否重复入职
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @return ApiResult
     */
    @GetMapping("getCheckedIdCard")
    @ApiOperation(value = "是否重复入职")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "certifNumber", value = "身份证", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> getCheckedIdCard(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, HttpServletRequest request) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        AssertUtil.notNull(ppmOutsourcingAccessapplyDto.getCertifNumber(), "[CertifNumber]不能为空");
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmOutsourcingAccessapplyDto item = ppmOutsourcingAccessapplyService.getCheckedIdCard(ppmOutsourcingAccessapplyDto.getCertifNumber(), user.getUserid());
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 公安信息
     *
     * PpmOutsourcingAccessapplyDto item =
     * @param ppmOutsourcingAccessapplyDto 对象
     * @return ApiResult
     */
    @GetMapping("getCheckedSecurity")
    @ApiOperation(value = "公安信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "外包名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "certifNumber", value = "身份证", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> getCheckedSecurity(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        AssertUtil.notNull(ppmOutsourcingAccessapplyDto.getUserName(), "[UserName]不能为空");
        AssertUtil.notNull(ppmOutsourcingAccessapplyDto.getCertifNumber(), "[CertifNumber]不能为空");
//		PpmOutsourcingAccessapplyDto item =
//				ppmOutsourcingAccessapplyService.getCheckedSecurity(ppmOutsourcingAccessapplyDto.getUserName(),
//						ppmOutsourcingAccessapplyDto.getCertifNumber());
        // 暂时屏蔽身份证验证
        PpmOutsourcingAccessapplyDto item = new PpmOutsourcingAccessapplyDto();
        item.setPublicSecurity("通过");
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmOutsourcingApplyinfo 对象
     * @return ApiResult<PpmOutsourcingAccessapplyInfoDto>
     */
    @GetMapping(value = "queryDetailExt")
    @ApiOperation(value = "queryDetailExt", notes = "查询详情Ext")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "工号", paramType = "query", dataType = "string", required = true)
    })
    public ApiResult<PpmOutsourcingAccessapplyInfoDto> queryDetailExt(PpmOutsourcingAccessapplyInfoDto ppmOutsourcingApplyinfo) {
        ApiResult<PpmOutsourcingAccessapplyInfoDto> result = new ApiResult<>();
        PpmOutsourcingAccessapplyInfoDto item = ppmOutsourcingAccessapplyService.queryDetailExt(ppmOutsourcingApplyinfo);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 查询面试官详情
     *
     * @param ppm 对象
     * @return ApiResult<List<PpmOutSorceInterviewDto>>
     */
    @GetMapping(value = "queryInterDataList")
    @ApiOperation(value = "queryInterDataList", notes = "查询详情Ext")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceId", value = "流程id", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "msgid", value = "面试官id", paramType = "query", dataType = "string", required = true)
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> queryInterDataList(PpmOutSourceInterview ppm) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult<>();
        PpmOutsourcingAccessapplyDto item = ppmOutSourceInterviewService.selInterviewData(ppm);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询面试官详情
     *
     * @param ppm 对象
     * @return ApiResult<List<PpmOutSorceInterviewDto>>
     */
    @GetMapping(value = "delData")
    @ApiOperation(value = "queryInterDataList", notes = "查询详情Ext")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "msgid", value = "面试官id", paramType = "query", dataType = "string", required = true)
    })
    public ApiResult<PpmOutsourcingAccessapplyDto> delData(PpmOutSourceInterview ppm) {
        ApiResult result = new ApiResult<>();

        int id = ppmOutSourceInterviewService.delInter(ppm);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导入考试成绩
     *
     * @param file    对象
     * @param request request
     */
    @PostMapping("importAchievement")
    @ApiOperation(value = "导入考试成绩")
    public ApiResult importAchievement(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        try {
            log.info("{}开始上传考试成绩！", user.getOperatorname());
            List<PpmOutsourcingAchievementDto> list = ExcelUtil.importExcel(file, PpmOutsourcingAchievementDto.class);
            if (!CollectionUtils.isEmpty(list)) {
                PpmOutsourcingAccessapplyDto param = new PpmOutsourcingAccessapplyDto();
                for (PpmOutsourcingAchievementDto achievementDto : list) {
                    if (!StringUtil.isStringNotEmpty(achievementDto.getJobId())
                            || !StringUtil.isStringNotEmpty(achievementDto.getCertificateNumber())) {
                        String jobStr = null == achievementDto.getJobId() ? " " : achievementDto.getJobId();
                        String idStr = null == achievementDto.getCertificateNumber() ? " " : achievementDto.getCertificateNumber();
                        throw new RuntimeException("工号：[" + jobStr + "]与身份证号:[" + idStr + "]数据有误!");
                    }
//                    boolean qualityFlag1 = StringUtil.isNotBlank(achievementDto.getQualityTimeFormate())
//                    && ObjectUtils.isEmpty(achievementDto.getQualityAchievement());
//                    boolean qualityFlag2 = !StringUtil.isNotBlank(achievementDto.getQualityTimeFormate())
//                    && !ObjectUtils.isEmpty(achievementDto.getQualityAchievement());
//                    if (qualityFlag1 || qualityFlag2) {
//                        String jobStr = null == achievementDto.getJobId() ? " " : achievementDto.getJobId();
//                        String idStr = null ==
//                        achievementDto.getCertificateNumber() ? " " : achievementDto.getCertificateNumber();
//                        throw new RuntimeException("工号：[" + jobStr + "]与身份证号
//                        :[" + idStr + "] 资质考试成绩数据有问题!");
//                    }
//
//                    boolean eitquetteFlag1 = StringUtil.isNotBlank(achievementDto.getEitquetteTimeFormate())
//                    && ObjectUtils.isEmpty(achievementDto.getEitquetteAchievement());
//                    boolean eitquetteFlag2 = !StringUtil.isNotBlank(achievementDto.getEitquetteTimeFormate())
//                    && !ObjectUtils.isEmpty(achievementDto.getEitquetteAchievement());
//                    if (eitquetteFlag1 || eitquetteFlag2) {
//                        String jobStr = null == achievementDto.getJobId() ? " " : achievementDto.getJobId();
//                        String idStr = null == achievementDto.getCertificateNumber() ? " " :
//                        achievementDto.getCertificateNumber();
//                        throw new RuntimeException("工号：[" + jobStr + "]与身份证号:[" + idStr + "]礼仪考试成绩数据有问题!");
//                    }

                    param.setJobId(achievementDto.getJobId());
                    PpmOutsourcingAccessapplyDto accessApplyDto = ppmOutsourcingAccessapplyService.queryDetail(param);
                    if (ObjectUtils.isEmpty(accessApplyDto)) {
                        String jobStr = null == achievementDto.getJobId() ? " " : achievementDto.getJobId();
                        String idStr = null ==
                                achievementDto.getCertificateNumber() ? " " : achievementDto.getCertificateNumber();
                        throw new RuntimeException("工号：[" + jobStr + "]与身份证号:[" + idStr + "]数据问题，工号不存在!");
                    }
                    if (!accessApplyDto.getCertifNumber().equals(achievementDto.getCertificateNumber())) {
                        String jobStr = null == achievementDto.getJobId() ? " " : achievementDto.getJobId();
                        String idStr = null ==
                                achievementDto.getCertificateNumber() ? " " : achievementDto.getCertificateNumber();
                        throw new RuntimeException("工号：[" + jobStr + "]与身份证号:[" + idStr + "]不匹配!");
                    }

                    if (StringUtil.isStringNotEmpty(achievementDto.getQualityTimeFormate())) {
                        Date qualityTime =
                                DateUtil.dataFormat(achievementDto.getQualityTimeFormate().trim(), "yyyy-MM-dd");
                        accessApplyDto.setQualityTime(qualityTime);
                        accessApplyDto.setQualityAchievement(achievementDto.getQualityAchievement());

                    }
                    if (StringUtil.isStringNotEmpty(achievementDto.getEitquetteTimeFormate())) {
                        Date eitquetteTime =
                                DateUtil.dataFormat(achievementDto.getEitquetteTimeFormate().trim(), "yyyy-MM-dd");
                        accessApplyDto.setEitquetteTime(eitquetteTime);
                        accessApplyDto.setEitquetteAchievement(achievementDto.getEitquetteAchievement());
                    }
                    ppmOutsourcingAccessapplyService.updatePpmOutsourcingAccessapply(accessApplyDto);
                }
            }
            result.success("导入成功");
        } catch (Exception e) {
            log.error("成绩导入失败:", e.getCause());
            result.failure(e.getLocalizedMessage());
        }
        log.info("{}上传考试成绩结束！", user.getOperatorname());
        return result;
    }

    /**
     * 查询有效合同
     * @param  vendorContractDto 对象
     * @return ApiResult<List<VendorContract>>
     */
    @GetMapping(value = "validContract")
    @ApiOperation(value = "validContract", notes = "查询有效合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attachInfo", value = "总办议案编号",
                    paramType = "query", dataType = "string", required = true)
    })
    public ApiResult<List<VendorContract>> validContract(VendorContractDto vendorContractDto) {
        ApiResult<List<VendorContract>> result = new ApiResult<>();
        HashMap map = new HashMap();
        map.put("attachInfo",vendorContractDto.getAttachInfo());
        map.put("outsourceType",vendorContractDto.getOutsourceType());
        map.put("isDel","0");
        List<VendorContract> list = vendorContractService.selectByMapSmall(map);

        if(list == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("无有效合同信息");
            return result;
        }
        List<VendorContract> newList = list.stream()
                .filter(x -> "生效中".equals(x.getStatus())).collect(Collectors.toList());
        result.setResults(newList);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询有效总办议案
     * @param attatchManagementDto 对象
     * @return ApiResult<List<AttatchManagement>>
     */
    @GetMapping(value = "validMotion")
    @ApiOperation(value = "validMotion", notes = "查询有效总办议案信息")
    public ApiResult<List<AttatchManagement>> validMotion(AttatchManagementDto attatchManagementDto) {
        ApiResult<List<AttatchManagement>> result = new ApiResult<>();
        HashMap map = new HashMap();
        map.put("fileType",attatchManagementDto.getFileType());
        map.put("isDel","0");
        List<AttatchManagement> list = attatchManagementService.selectByMapSmall(map);
        if(list == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("无有效总办议案信息");
            return result;
        }

        List<AttatchManagement> newList = list.stream().filter(x -> "生效中".equals(x.getStatus()))
                .filter(x-> "0".equals(x.getFileType())).collect(Collectors.toList());
        result.setResults(newList);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


}
