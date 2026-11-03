package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.integratedmanager.CommDepAndOutsTreeDto;
import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicCardExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.OutFlowData;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
 * 外包人员基础接口
 */
public interface IPpmOutsourcingBasicService {

    /**
     * 分页
     * @param ppmOutsourcingBasic 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingBasic ppmOutsourcingBasic, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingBasic 对象
     * @return int
     */
    int addPpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) ;

    /**
     * 查询详情
     * @param ppmOutsourcingBasic 对象
     * @return PpmOutsourcingBasic
     */
    PpmOutsourcingBasic queryDetail(PpmOutsourcingBasic ppmOutsourcingBasic) ;

    /**
     * 更新
     * @param ppmOutsourcingBasic 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) ;

    /**
     * 更新-cus
     * @param ppmOutsourcingBasic 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingBasicCus(PpmOutsourcingBasic ppmOutsourcingBasic);
    /**
     * 更新 变更申请
     * @param ppmOutsourcingBasic 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingBasicUpdate(PpmOutsourcingBasic ppmOutsourcingBasic) ;
    /**
     * 更新 变更申请
     * @param ppmOutsourcingBasic 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingBasicTransfer(PpmOutsourcingBasic ppmOutsourcingBasic) ;

    /**
     * 删除
     * @param ppmOutsourcingBasic 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmOutsourcingBasic(PpmOutsourcingBasic ppmOutsourcingBasic) ;

    /**
     * 获取外包人员基本信息
     * @param jobid 工号
     * @return PpmOutsourcingBasicInfo
     */
    PpmOutsourcingBasicInfo getBaseInfo(String jobid);

    /**
     * 根据岗位状态查询用户编号 - 分页
     * @param s 状态
     * @param roleId  角色id
     * @param pager 对象
     */
    void queryUserIdForState(String s,String roleId, Pager pager);

//    /**
//     * 获取外包人员基本信息
//     * @param ppmOutsourcingBasic /
//     * @return PpmOutsourcingBasicInfo
//     */
//    List<PpmOutsourcingBasicInfo> getBaseInfoByTerms(PpmOutsourcingBasic ppmOutsourcingBasic);

    /**
     * 查询外包人员
     * @param param
     * @return List<PpmOutsourcingBasic>
     */
    List<PpmOutsourcingBasic> queryOutsList(Map param);

    /**
     * 根据工号获取部门Id
     * @param jobId
     * @return
     */
    String getDepIdByJobId(String jobId);

    /**
     * 查询部门外包树
     * @return List<CommDepAndOutsTreeDto>
     */
    List<CommDepAndOutsTreeDto> getDepOutsTree();
    /**
     * 重复入职
     * @param name 外包名称
     * @param idCard 身份证号
     * @return boolean
     */
    boolean repeatEntry(String name, String idCard);

    /**
     * 工号重复
     * @param jobId 工号
     * @return boolean
     */
    boolean selJobIdRepart(String jobId);

    /**
     * 根据工号查询或者姓名模糊查询
     * @param ppmOutsourcingBasic
     * @return PpmOutsourcingBasic
     */
    List<PpmOutsourcingBasic> queryDetailByIdAndName(PpmOutsourcingBasic ppmOutsourcingBasic);

    /**
     * 查询外包数据扩展
     * @param  ppmOutsourcingBasicInfo PpmOutsourcingBasicInfo
     * @return List<PpmOutsourcingBasicInfo>
     */
    List<PpmOutsourcingBasicInfo> queryPpmOutsourcingBasicInfo(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo);

    /**
     * 人员移交 查询人力外包的花名册信息
     * @param  ppmOutsourcingBasicInfo PpmOutsourcingBasicInfo
     * @return List<PpmOutsourcingBasicInfo>
     */
    List<PpmOutsourcingBasicInfo> queryHumanList(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo);

    /**
     * 人员考核  项目经理查询
     * @param  ppmOutsourcingBasicInfo PpmOutsourcingBasicInfo
     * @return List<PpmOutsourcingBasic>
     */
    List<PpmOutsourcingBasicInfo> queryMgrList(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo);

    /**
     * saveOutBaseArchives保存外包数据
     * @param ppmOutsourcingBasicInfo
     * @return Integer
     */
    Integer saveOutBaseArchives(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo);

    /**
     * 获得流程数据
     * @param outDataDto
     * @return List<OutFlowData>
     */
    List<OutFlowData> getFlowData(OutDataDto outDataDto);


    /**
     * 外包花名册
     * @param commEmployeeDto 对象
     * @param pager 分页对象
     */
    void queryCrewPageList(CommEmployeeDto commEmployeeDto, Pager pager, CommOperatorDoLoginDto commOperatorDoLoginDto) ;

    /**
     * 查询保外包数据
     * @param ppmOutsourcingBasic
     * @return List<PpmOutsourcingBasicDto>
     */
    List<PpmOutsourcingBasicDto> queryOutList(PpmOutsourcingBasicDto ppmOutsourcingBasic);

    /**
     * getPermissionByUserid
     * @param user
     * @return String
     */
    String getPermissionByUserid(CommOperatorDoLoginDto user);

    /**
     * getOutFlowInfos
     * @param outDataDto
     * @return  List<PpmProjectFlowDto>
     */
    List<PpmProjectFlowDto> getOutFlowInfos(OutDataDto outDataDto);

    /**
     * 导出
     * @param ppmOutsourcingBasicDto 对象
     * @param user session
     * @return list
     */
    List<PpmOutsourcingBasicExcelDto> exportOutsourcing(PpmOutsourcingBasicExcelDto
                                                                ppmOutsourcingBasicDto,CommOperatorDoLoginDto user);
    /**
     * 导入员工虚拟机账户
     * @param cardExcelDto 对象
     * @param user session
     * @return list
     */
    boolean importOutsourcingCard(List<PpmOutsourcingBasicCardExcelDto>
                                                                cardExcelDto, CommOperatorDoLoginDto user);

    /**
     * 导出
     * @param commEmployeeDto 对象
     * @param user session
     * @return list
     */
    List<CommEmployeeInfo> exportBasicOutsourcing(CommEmployeeDto commEmployeeDto,
                                                  CommOperatorDoLoginDto user);
}
