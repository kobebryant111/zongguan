package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.application.dto.onlinemanager.PpmDacenterDemandDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.onlinemanager.ext.PpmDacenterDemandInfo;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.workflow.client.soentity.BPSWorkItem;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmDacenterDemandService {

    /**
     * 分页
     * @param ppmDacenterDemandDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDacenterDemandDto ppmDacenterDemandDto, Pager pager) ;

    /**
     * excel 导出  不做分页查询
     * @param ppmDacenterDemandDto 对象
     * @return 返回Vo 不分页对象集合
     */
    List<PpmDacenterDemandInfo>  selectList(PpmDacenterDemandDto ppmDacenterDemandDto);

    /**
     * 添加
     * @param ppmDacenterDemandDto 对象
     * @return int
     */
    int addPpmDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto) ;

    /**
     * 查询详情
     * @param ppmDacenterDemandDto 对象
     * @return PpmDacenterDemandDto
     */
    PpmDacenterDemandInfo queryDetail(PpmDacenterDemandDto ppmDacenterDemandDto) ;

    /**
     * 更新
     * @param ppmDacenterDemandDto  对象
     * @return int
     */
    int updatePpmDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto) ;

    /**
     * 删除
     * @param ppmDacenterDemandDto 对象
     * @return int
     */
    int deletePpmDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto) ;
    /**
     * 保存草稿
     * @param ppmDacenterDemandDto 对象
     * @return ApiResult 返回流程对象
     */
    ApiResult saveDraft(PpmDacenterDemandDto ppmDacenterDemandDto, CommOperatorDoLoginDto user);
    /**
     * 发起并直接流转流程
     * @param ppmDacenterDemandDto 对象
     * @return ApiResult 返回流程对象
     */
    ApiResult flowStartAndRun(PpmDacenterDemandDto ppmDacenterDemandDto, CommOperatorDoLoginDto user);
    /**
     * 流程开始审批
     * @param ppmDacenterDemandDto 对象
     * @return ApiResult 返回流程对象
     */
    ApiResult runFlowInfo(PpmDacenterDemandDto ppmDacenterDemandDto, BPSWorkItem item);
    /**
     * 手机端查询详情
     * @param ppmDacenterDemandDto 对象
     * @return  PpmDacenterDemandInfo 返回Vo 对象
     */
    PpmDacenterDemandInfo queryMobileDetail(PpmDacenterDemandDto ppmDacenterDemandDto);
    /**
     * 科管数据中心需求管理线上化修改接口
     * @param ppmDacenterDemandDto 对象
     * @return  ApiResult
     */
    ApiResult modifyDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto, CommOperatorDoLoginDto user);
}
