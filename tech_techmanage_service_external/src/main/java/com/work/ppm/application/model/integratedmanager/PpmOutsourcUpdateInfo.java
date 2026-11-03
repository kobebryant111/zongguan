package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



/**
 * 信息变更申请信息表 实体对象
 */
@Data
public class PpmOutsourcUpdateInfo {

    /**
     *	唯一编号
     **/
    @Id
    private Long id;
    /**
     *	父编号
     **/
    private Long pid;
    /**
     *	工号
     **/
    private String jobId;
    /**
     *	姓名
     **/
    private String userName;
    /**
     *	流程ID
     **/
    private String instanceid;
    /**
     *	前岗位
     **/
    private String positionBe;
    /**
     *	现岗位
     **/
    private String positionAf;
    /**
     *	前外包方式
     **/
    private String outsourceTypeBe;
    /**
     *	现外包方式
     **/
    private String outsourceTypeAf;
    /**
     *	前结算方式
     **/
    private String userTypeBe;
    /**
     *	现结算方式
     **/
    private String userTypeAf;
    /**
     *	前虚拟机账号
     **/
    private String vmAccountBe;
    /**
     *	现虚拟机账号
     **/
    private String vmAccountAf;
    /**
     *	前门禁卡编号
     **/
    private String entranceCardBe;
    /**
     *	现门禁卡编号
     **/
    private String entranceCardAf;
    /**
     *	前所属合同
     **/
    private Long newContractIdBe;
    /**
     *	现所属合同
     **/
    private Long newContractIdAf;
    /**
     *	前总办议案 修改时需改变人数
     **/
    private Long ceneralOfficeMotionBe;
    /**
     *	现总办议案
     **/
    private Long ceneralOfficeMotionAf;
    /**
     *	前征信报告
     **/
    private String creditReportBe;
    /**
     *	现征信报告
     **/
    private String creditReportAf;
    /**
     *	前社保缴纳证明/合同
     **/
    private String ssPaymentProveBe;
    /**
     *	现社保缴纳证明/合同
     **/
    private String ssPaymentProveAf;
    /**
     *	前保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表
     **/
    private String confidentCommitmentBe;
    /**
     *	现保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表
     **/
    private String confidentCommitmentAf;
    /**
     *	前其他文件
     **/
    private String otherFileBe;
    /**
     *	现其他文件
     **/
    private String otherFileAf;
    /**
     *	前人脸照片
     **/
    private String faceimageBe;
    /**
     *	现人脸照片
     **/
    private String faceimageAf;
    /**
     *	前尽调报告
     **/
    private String dueDiligenceReportBe;
    /**
     *	现尽调报告
     **/
    private String dueDiligenceReportAf;
    /**
     *	前其他附件
     **/
    private String qtFileBe;
    /**
     *	现其他附件
     **/
    private String qtFileAf;
    /**
     *	创建时间
     **/
    private Date createdTime;
    /**
     *	创建人
     **/
    private String createdBy;
    /**
     *	修改时间
     **/
    private Date modifiedTime;
    /**
     *	修改人
     **/
    private String modifiedBy;
    /**
     * 部门编号
     */
    private String deptIdBe;
    /**
     * 变更后部门编号
     */
    private String deptIdAf;
    /**
     * 系统编号
     */
    private String systemNoBe;
    /**
     * 变更后系统编号
     */
    private String systemNoAf;
    /**
     * 负责人
     */
    private String personInChargeBe;
    /**
     * 变更后负责人
     */
    private String personInChargeAf;
    /**
     * 定级级别
     * junior-初级
     * intermediate-中级
     * senior-高级
     * expert-资深
     */
    private String evaluationLevelBe;
    /**
     * 变更后定级级别
     * junior-初级
     * intermediate-中级
     * senior-高级
     * expert-资深
     */
    private String evaluationLevelAf;
    /**
     * 定级理由，200字以内
     */
    private String levelReasonBe;
    /**
     * 变更后定级理由，200字以内
     */
    private String levelReasonAf;
    /**
     * 定级人员
     */
    private String evaluatorIdBe;
    /**
     * 变更后定级人员
     */
    private String evaluatorIdAf;
    /**
     * 定级时间
     */
    private Date evaluationTime;
    /**
     * 变更前定级时间
     */
    private String evaluationTimeBe;

}
