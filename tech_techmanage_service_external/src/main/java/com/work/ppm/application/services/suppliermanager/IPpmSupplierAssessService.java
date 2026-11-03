package com.work.ppm.application.services.suppliermanager;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
  *  供应商考核信息表 接口描述
  */
public interface IPpmSupplierAssessService {

    /**
     * 分页
     * @param ppmSupplierAssessDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmSupplierAssessDto ppmSupplierAssessDto, Pager pager,CommOperatorDoLoginDto user) ;

    /**
     * 添加
     * @param ppmSupplierAssessDto 对象
     * @return int
     */
    int addPpmSupplierAssess(PpmSupplierAssessDto  ppmSupplierAssessDto) ;

    /**
     * 查询详情
     * @param ppmSupplierAssessDto 对象
     * @param uniqueName 查询id
     * @return PpmSupplierAssessDto
     */
    PpmSupplierAssessDto queryDetail(PpmSupplierAssessDto ppmSupplierAssessDto,String uniqueName) ;

    /**
     * 查询集合
     * @param ppmSupplierAssessDto 对象
     * @return PpmSupplierAssessDto
     */
    List<PpmSupplierAssessDto> queryDetailList(PpmSupplierAssessDto ppmSupplierAssessDto) ;

    /**
     * 查询集合
     * @param ppmSupplierAssessDto 对象
     * @return PpmSupplierAssessDto
     */
    List<PpmSupplierAssessDto> queryBaseList(PpmSupplierAssessDto ppmSupplierAssessDto) ;

    /**
     * 查询集合
     * @param ppmSupplierAssessDto 对象
     * @return PpmSupplierAssessDto
     */
    List<PpmSupplierAssessDto> countSupplierPmRL(PpmSupplierAssessDto ppmSupplierAssessDto) ;

    /**
     * 更新
     * @param ppmSupplierAssessDto  对象
     * @return int
     */
    int updatePpmSupplierAssess(PpmSupplierAssessDto ppmSupplierAssessDto) ;

    /**
     * 删除
     * @param ppmSupplierAssessDto 对象
     * @return int
     */
    int deletePpmSupplierAssess(PpmSupplierAssessDto ppmSupplierAssessDto) ;

    /**
     * 草稿 未发起
     * 已创建
     * @param ppmSupplierAssessDto 对象
     * @return String
     */
    String managerCreated(PpmSupplierAssessDto ppmSupplierAssessDto);

    /**
     * 项目经理添加并发起流程
     * 已发起
     * @param ppmSupplierAssessDto 对象
     * @return String
     */
    String managerInit(PpmSupplierAssessDto ppmSupplierAssessDto) ;

    /**
     * 二级部经理审批
     * 处理中
     * @param ppmSupplierAssessDto 对象
     * @return String
     */
    String departmentHeadApproval(PpmSupplierAssessDto ppmSupplierAssessDto);

    /**
     * 一级部总经理室审批
     * 处理中 / 已结束
     * @param ppmSupplierAssessDto 对象
     * @return String
     */
    String generalManagerApproval(PpmSupplierAssessDto ppmSupplierAssessDto);

    /*文件管理 */

    /**
     * 导出pdf
     * @param ppmSupplierAssessDto 对象
     * @return List
     */
    void exportPdf(PpmSupplierAssessDto ppmSupplierAssessDto,HttpServletResponse response) throws Exception;

    /*
     * 测试用
     * @param body 报文
     * @return boolean

    boolean handSupplierAssess(JSONObject body);*/

}
