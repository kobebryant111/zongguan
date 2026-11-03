package com.work.ppm.application.services.suppliermanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRiskEvalDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
  *  风险评估表 接口描述
  */
public interface IPpmSupplierRiskEvalService {

    /**
     * 分页
     * @param ppmSupplierRiskEvalDto 对象
     * @param pager 分页对象
     * @param user 用户信息
     */
    void queryPager(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto, Pager pager, CommOperatorDoLoginDto user) ;

    /**
     * 添加
     * @param ppmSupplierRiskEvalDto 对象
     * @return int
     */
    int addPpmSupplierRiskEval(PpmSupplierRiskEvalDto  ppmSupplierRiskEvalDto) ;

    /**
     * 查询详情
     * @param ppmSupplierRiskEvalDto 对象
     * @param uniqueName 名称
     * @return PpmSupplierRiskEvalDto
     */
    PpmSupplierRiskEvalDto queryDetail(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,String uniqueName) ;

    /**
     * 查询集合
     * @param ppmSupplierRiskEvalDto 对象
     * @return PpmSupplierRiskEvalDto
     */
    List<PpmSupplierRiskEvalDto> queryDetailList(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) ;

    /**
     * 更新
     * @param ppmSupplierRiskEvalDto  对象
     * @return int
     */
    int updatePpmSupplierRiskEval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) ;

    /**
     * 删除
     * @param ppmSupplierRiskEvalDto 对象
     * @return int
     */
    int deletePpmSupplierRiskEval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) ;


    /**
     * 草稿 未发起
     * 已创建
     * @param ppmSupplierRiskEvalDto 对象
     * @return String
     */
    String managerCreated(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto);

    /**
     * 项目经理添加并发起流程
     * 已发起
     * @param ppmSupplierRiskEvalDto 对象
     * @return String
     */
    String managerInit(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) ;

    /**
     * 二级部经理审批
     * 处理中
     * @param ppmSupplierRiskEvalDto 对象
     * @return String
     */
    String departmentHeadApproval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto);

    /**
     * 一级部总经理室审批
     * 处理中 / 已结束
     * @param ppmSupplierRiskEvalDto 对象
     * @return String
     */
    String generalManagerApproval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto);

    /**
     * 导出pdf
     * @param ppmSupplierRiskEvalDto 对象
     * @param response 对象
     */
    void exportPdf(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto, HttpServletResponse response) throws Exception;


}
