package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgApplyDto;
import com.work.ppm.application.dto.projectmanager.SyncPatchObjectDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.Date;
import java.util.List;

/**
 * 分行项目变更
 */
public interface IPpmFhyyxtbgApplyService {

    /**
     * 分页
     * @param ppmFhyyxtbgApplyDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, Pager pager) ;

    /**
     * 添加
     * @param ppmFhyyxtbgApplyDto 对象
     * @return int
     */
     @Deprecated
    int addPpmFhyyxtbgApply(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) ;

    /**
     * 查询详情
     * @param ppmFhyyxtbgApplyDto 对象
     * @return PpmFhyyxtbgApplyDto
     */
    PpmFhyyxtbgApplyDto queryDetail(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) ;

    /**
     * 更新
     * @param ppmFhyyxtbgApplyDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmFhyyxtbgApply(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) ;

    /**
     * 删除
     * @param ppmFhyyxtbgApplyDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmFhyyxtbgApply(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) ;

    /**
     * 保存草稿
     * @param ppmFhyyxtbgApplyDto 对象
     * @param user 对象
     * @return ApiResult
     */
    ApiResult save(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user);

    /**
     * 流转
     * @param ppmFhyyxtbgApplyDto 对象
     * @param user 对象
     * @return ApiResult
     */
    ApiResult liuzhuan(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user);

    /**
     * 分行研发部/IT支持部经理审批
     * @param ppmFhyyxtbgApplyDto 对象
     * @param user 对象
     * @return ApiResult
     */
    ApiResult liuzhuan2(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user);

    /**
     * 分行科技部/运营部总经理审批
     * @param ppmFhyyxtbgApplyDto 对象
     * @return ApiResult
     */
    ApiResult liuzhuan3(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto);

    /**
     * 主办人员办理
     * @param ppmFhyyxtbgApplyDto 对象
     * @return ApiResult
     */
    ApiResult liuzhuan4(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto);

    /**
     * 获取项目变更基本信息
     * @param userid 工号
     * @param name 名称
     * @param deptId 部门id
     * @return PpmFhyyxtbgApplyDto
     */
    PpmFhyyxtbgApplyDto getProjectChangeInfo(String userid,String name,String deptId);

    /**
     * 查询缺陷
     * @param userId 工号
     * @return list
     */
    List<SyncPatchObjectDto> queryBugsList(String userId);

    /**
     * 是否白天
     * @param date 时间
     * @return int
     */
    int getIsDayTime(Date date);

    /**
     * 获取密管密码
     * 判断是否可以直接获取密码
     * @param instanceid 流程id
     * @return ApiResult
     */
    ApiResult queryPasswordIs(String instanceid);

    /**
     * 流程密码获取模式
     * （1：可直接获取密码，2：审批模式但未发起申请3：已发起申请但未开始，4：已发起申请且已开始）
     * @param projectno 项目编号
     * @return ApiResult
     */
    ApiResult queryPwdModel(String projectno);

    /**
     *  保存并获取密管密码
     *  如第一次点击时，维护时间 -1h 到   维护时间+预计变更时长+1小时 时间范围内,直接获取密码
     * @param ppmFhyyxtbgApplyDto 对象
     * @param user session
     * @return ApiResult
     */
    ApiResult querySavePasswd(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user);

    /**
     * 获取流程信息，在运维审计
     * @param ppmFhyyxtbgApplyDto 对象
     * @param user session
     * @return  ApiResult
     */
    ApiResult getFlowinfoForPwd(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user);

    /**
     * 获取通用密码密钥
     * @param user session
     * @param clientIp 客户端ip
     * @return PpmFhyyxtbgApplyDto
     */
    PpmFhyyxtbgApplyDto getGeneralPassworkKey(CommOperatorDoLoginDto user,String clientIp);

    /**
     * 查询详情(手机端)
     * @param ppmFhyyxtbgApplyDto 对象
     * @return PpmFhyyxtbgApplyDto
     */
    PpmFhyyxtbgApplyDto queryMobileDetail(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) ;

    /**
     * 是否隐藏按钮
     * @param ppmFhyyxtbgApplyDto 对象
     * @return ApiResult
     */
    ApiResult ifHiddenButton(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto);
}
