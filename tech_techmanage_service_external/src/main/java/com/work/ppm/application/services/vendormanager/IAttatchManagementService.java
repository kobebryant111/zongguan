package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttatchManagementDto;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.commons.pager.Pager;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 附件管理 接口描述
 * </p>
 *
 * @since 2022-10-26
 */


public interface IAttatchManagementService {

    /**
     * 分页
     * @param attatchManagement 对象
     * @param pager 分页对象
     */
    void queryPager(AttatchManagement attatchManagement, Pager pager);

    /**
     * 详情
     * @param id
     */
    AttatchManagement queryDetailsById(Long id);

    /**
     * 依据map条件 - 计数
     * @param map
     */
    long count(HashMap map);

    /**
     * 新增 附带id
     * @param attatchManagement 实例
     */
    Integer addAttatchManagement(AttatchManagement attatchManagement);

    /**
     * 更新时 - 发起流程
     * @param dto 附件管理实例
     * @param user /
     */
    Object updateWithFlow(AttatchManagementDto dto, CommOperatorDoLoginDto user);

    /**
     * 二级部门经理及以上
     * @param jobId  工号
     */
    boolean level2ManagerAbove(String jobId);
    /**
     * 编辑
     * @param attatchManagement 实例
     */
    Integer updateAttatchManagement(AttatchManagement attatchManagement);

    /**
     * 逻辑删除
     * @param ids
     */
    Integer deleteByIdsWithTerms(Long ids);

    /**
     * 查询最新文件编号
     */
    AttatchManagement selectOneDesc();

    /**
     * 查询局部字段
     *
     */
    List<AttatchManagement> selectByMapSmall(HashMap map);

    /**
     * 场景：变更流程删除 准入流程删除
     * 增加原可用人数 减少原已用人数 减少现可用人数 增加现已用人数
     * 场景：变更流程发起 准入流程发起
     * 增加原已用人数 减少原可用人数 减少现已用人数 增加现可用人数
     * @param beforeId 前总办议案id
     * @param afterId 现总办议案id
     */
    boolean calMotionNumber (Long beforeId,Long afterId);
    /**
     * 增加已用人数
     * @param id 附件id
     */
    boolean calMotionNumberPlus (Long id);
    /**
     * 减少已用人数
     * @param id 附件
     */
    boolean calMotionNumberMinus (Long id);

    /**
     * 场景：变更流程删除 准入流程删除
     * 判断原可用人数 原已用人数 现可用人数 现已用人数
     * 场景：变更流程发起 准入流程发起
     * 判断原已用人数 原可用人数 现已用人数 现可用人数
     * @param beforeId 前总办议案id
     * @param afterId 现总办议案id
     */
    boolean judgeMotionNumber (Long beforeId,Long afterId);
    /**
     * 判断已用人数
     * @param id 附件id
     */
    boolean judgeMotionNumberPlus (Long id);
    /**
     * 判断已用人数
     * @param id 附件
     */
    boolean judgeMotionNumberMinus (Long id);

}