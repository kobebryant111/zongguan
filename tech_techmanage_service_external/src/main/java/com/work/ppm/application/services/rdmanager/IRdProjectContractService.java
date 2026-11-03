package com.work.ppm.application.services.rdmanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.dto.rdmanager.ContractBatchCreationDto;
import com.work.ppm.application.dto.rdmanager.ContractResearchProjectDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractFinancialInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractPayInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.application.model.rdmanager.RdProjectContractExcel;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

public interface IRdProjectContractService extends IService<RdProjectContract> {


    /**
     * 分页查询
     * @param req
     * @param pager
     */
    void queryPager(RdProjectContractQueryDto req, Pager pager);
    /**
     * 导出
     */
    List<RdProjectContractExcel> queryPagerInfo(RdProjectContractQueryDto req, Pager pager);
    /**
     * 新建项目合同
     * @param req
     */
    public void  saveRdProjectContract(RdProjectContract req) ;


    /**
     * 修改合同预算信息
     * @param req
     */
    public RdProjectContract  updateContractInfo(RdProjectContractInfoDto req) ;



    /**
     * 研发项目关联
     * @param req
     */
    public void  updateContractResearchProject(ContractResearchProjectDto req) ;


    /**
     * 合同及支付信息
     * @param req
     */
    public void updateContractPayInfo( RdProjectContractPayInfoDto req) ;

    /**
     * 财务核算信息
     * @param req
     */
    public void  updateContractFinancialInfo(RdProjectContractFinancialInfoDto req);

    /**
     * 根据项目查询合同
     * @param rdProjectId
     * @return List<RdProjectContract>
     */
    List<RdProjectContract> queryByRdProjectId(String rdProjectId);

    /**
     * 批量新建
     *
     * @param contractBatchCreationDtoList 批量新建DTO
     */
    void importAchievement(List<ContractBatchCreationDto> contractBatchCreationDtoList);
}
