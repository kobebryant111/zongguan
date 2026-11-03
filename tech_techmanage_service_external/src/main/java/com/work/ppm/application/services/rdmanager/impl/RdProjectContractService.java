package com.work.ppm.application.services.rdmanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dao.rdManager.RdProjectContractMapper;
import com.work.ppm.application.dao.rdManager.RdProjectMapper;
import com.work.ppm.application.dto.rdmanager.ContractBatchCreationDto;
import com.work.ppm.application.dto.rdmanager.ContractResearchProjectDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractFinancialInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractPayInfoDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.model.rdmanager.RdContractPayRecord;
import com.work.ppm.application.model.rdmanager.RdProject;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.application.model.rdmanager.RdProjectContractExcel;
import com.work.ppm.application.model.rdmanager.RdProjectContractRelation;
import com.work.ppm.application.services.rdmanager.IRdContractPayRecordService;
import com.work.ppm.application.services.rdmanager.IRdProjectContractService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 研发项目管理
 *
 */
@Service
public class RdProjectContractService extends ServiceImpl<RdProjectContractMapper, RdProjectContract>
        implements IRdProjectContractService {

    /**
     * 关联关系
     */
    @Autowired
    private RdProjectContractRelationService rdProjectContractRelationService;

    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGBASIC = "com.work.ppm.application.dao.rdManager.RdProjectContractMapper";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 合同支付记录
     */
    @Autowired
    private IRdContractPayRecordService rdContractPayRecordService;

    /**
     * 研发项目管理 Mapper
     */
    @Autowired
    private RdProjectMapper rdProjectMapper;

    @Override
    public void queryPager(RdProjectContractQueryDto req, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(req, RdProjectContractQueryDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC,map, pager, "queryPager");
    }

    @Override
    public List<RdProjectContractExcel> queryPagerInfo(RdProjectContractQueryDto req, Pager pager){
        Map<String, Object> map = JsonUtil.objectTOMap(req, RdProjectContractQueryDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGBASIC,map,pager,"queryPagerExcel");
        List<RdProjectContractExcel> repsJson = JsonUtil.jsonToList(pager.getList(), RdProjectContractExcel.class);
        return repsJson;
    }

    /**
     * 新建项目合同
     * @param req
     */
    @Override
    public void  saveRdProjectContract(RdProjectContract req) {
        if(StringUtil.isStringNotEmpty(req.getPkId())){
            LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RdProjectContract::getPkId,req.getPkId());
//			updateWrapper.set(RdProjectContract::getBudgetCode,req.getBudgetCode());
            updateWrapper.set(RdProjectContract::getBudgetName,StringUtil.nullTo(req.getBudgetName()));
            updateWrapper.set(RdProjectContract::getContractCode,StringUtil.nullTo(req.getContractCode()));
            updateWrapper.set(RdProjectContract::getContractStartDate,StringUtil.nullTo(req.getContractStartDate()));
            updateWrapper.set(RdProjectContract::getPaymentSche,StringUtil.nullTo(req.getPaymentSche()));
            updateWrapper.set(RdProjectContract::getProjectAllocationRatio,
                    StringUtil.nullTo(req.getProjectAllocationRatio()));
            updateWrapper.set(RdProjectContract::getContractName,StringUtil.nullTo(req.getContractName()));
            this.update(updateWrapper);
        }else{
            LambdaQueryWrapper<RdProjectContract> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdProjectContract::getBudgetCode,req.getBudgetCode());
            List<RdProjectContract> list = this.list(queryWrapper);
            if(list!=null&&list.size()>0){
                throw new BizRuntimeException(String.format("该预算编号（%s）已经使用",req.getBudgetCode()));
            }
            req.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
            req.setCreateDate(new Date());
            req.setDeleted("0");
            this.save(req);
        }
    }


    /**
     * 修改合同预算信息
     * @param req
     */
    @Override
    public RdProjectContract  updateContractInfo(RdProjectContractInfoDto req) {
        String pkId = req.getRdProjectContractId();
        if(StringUtil.isStringNotEmpty(req.getRdProjectContractId())){
            LambdaQueryWrapper<RdProjectContract> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdProjectContract::getBudgetName,req.getBudgetName());
            queryWrapper.notIn(RdProjectContract::getPkId,req.getRdProjectContractId());
            queryWrapper.eq(RdProjectContract::getDeleted,"0");
            List<RdProjectContract> list = this.list(queryWrapper);
            if(list!=null&&list.size()>0){
                throw new BizRuntimeException(String.format("该预算名称（%s）已经使用",req.getBudgetName()));
            }
            LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RdProjectContract::getPkId,req.getRdProjectContractId());
            updateWrapper.set(RdProjectContract::getBudgetCode,StringUtil.nullTo(req.getBudgetCode()));
            updateWrapper.set(RdProjectContract::getBudgetName,StringUtil.nullTo(req.getBudgetName()));
            updateWrapper.set(RdProjectContract::getApprovedDate,StringUtil.nullTo(req.getApprovedDate()));
            updateWrapper.set(RdProjectContract::getBudgetAmount,StringUtil.nullTo(req.getBudgetAmount()));
            updateWrapper.set(RdProjectContract::getSupplier,StringUtil.nullTo(req.getSupplier()));
            updateWrapper.set(RdProjectContract::getType,StringUtil.nullTo(req.getType()));
            updateWrapper.set(RdProjectContract::getSoftwareManpower,StringUtil.nullTo(req.getSoftwareManpower()));
            updateWrapper.set(RdProjectContract::getAssistManpower,StringUtil.nullTo(req.getAssistManpower()));
            updateWrapper.set(RdProjectContract::getRiskManagementManpower,StringUtil.nullTo(req.getRiskManagementManpower()));
            this.update(updateWrapper);
        }else{
            LambdaQueryWrapper<RdProjectContract> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdProjectContract::getBudgetCode,req.getBudgetCode());
            queryWrapper.eq(RdProjectContract::getDeleted,"0");
            List<RdProjectContract> list = this.list(queryWrapper);
            if(list!=null&&list.size()>0){
                throw new BizRuntimeException(String.format("该预算编号（%s）已经使用",req.getBudgetCode()));
            }
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdProjectContract::getBudgetName,req.getBudgetName());
            queryWrapper.eq(RdProjectContract::getDeleted,"0");
            list = this.list(queryWrapper);
            if(list!=null&&list.size()>0){
                throw new BizRuntimeException(String.format("该预算名称（%s）已经使用",req.getBudgetName()));
            }
            RdProjectContract info = JsonUtil.jsonToClass(req,RdProjectContract.class);
            info.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
            info.setCreateDate(new Date());
            info.setDeleted("0");
            this.save(info);
            pkId = info.getPkId();
        }
        return this.getById(pkId);
    }



    /**
     * 研发项目关联
     * @param req
     */
    @Override
    public void  updateContractResearchProject(ContractResearchProjectDto req) {
        AssertUtil.notNull(req.getRdProjectContractId(),"合同id不能为空");
        LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RdProjectContract::getPkId,req.getRdProjectContractId());
        updateWrapper.set(RdProjectContract::getIsResearchProject,StringUtil.nullTo(req.getIsResearchProject()));
        updateWrapper.set(RdProjectContract::getTechInnoPoint,StringUtil.nullTo(req.getTechInnoPoint()));
        this.update(updateWrapper);

        if("N".equals(req.getIsResearchProject())){
            LambdaQueryWrapper<RdProjectContractRelation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdProjectContractRelation::getRdProjectContractId,req.getRdProjectContractId());
            rdProjectContractRelationService.remove(queryWrapper);
        }
    }


    /**
     * 合同及支付信息
     * @param req
     */
    @Override
    public void updateContractPayInfo( RdProjectContractPayInfoDto req) {
        if("N".equals(req.getContractStartDateFlag())){
            req.setContractStartDate("");
        }
        if("N".equals(req.getContractEndDateFlag())){
            req.setContractEndDate("");
        }
        BigDecimal paymentScheduling = new BigDecimal("0");
        LambdaQueryWrapper<RdContractPayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RdContractPayRecord::getRdProjectContractId,req.getRdProjectContractId());
        List<RdContractPayRecord> list = rdContractPayRecordService.list(queryWrapper);
        if(req.getContractAmount()!=null&&list.size()>0){
            BigDecimal contractAmount= new BigDecimal(req.getContractAmount());
            BigDecimal sumAmount = new BigDecimal("0");
            for(RdContractPayRecord tmp:list){
                sumAmount = sumAmount.add(tmp.getPayAmount()==null?new BigDecimal("0"):
                        new BigDecimal(tmp.getPayAmount()));
            }
            if(sumAmount.compareTo(contractAmount)>0){
                throw new BizRuntimeException(String.format("支付金额(%s)不能大于合同金额(%s)",
                        sumAmount.toString(),req.getContractAmount().toString()));
            }
            //计算支付比例
            paymentScheduling = sumAmount.multiply(new BigDecimal(100))
                    .divide(contractAmount,2,BigDecimal.ROUND_UP);
        }

        LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RdProjectContract::getPkId,req.getRdProjectContractId());
        updateWrapper.set(RdProjectContract::getContractCode, StringUtil.nullTo(req.getContractCode()));
        updateWrapper.set(RdProjectContract::getContractName,StringUtil.nullTo(req.getContractName()));
        updateWrapper.set(RdProjectContract::getContractStartDate,StringUtil.nullTo(req.getContractStartDate()));
        updateWrapper.set(RdProjectContract::getContractEndDate,StringUtil.nullTo(req.getContractEndDate()));

        updateWrapper.set(RdProjectContract::getMaintenanceStartDate,StringUtil.nullTo(req.getMaintenanceStartDate()));
        updateWrapper.set(RdProjectContract::getMaintenanceEndDate,StringUtil.nullTo(req.getMaintenanceEndDate()));


        updateWrapper.set(RdProjectContract::getSupplierName,StringUtil.nullTo(req.getSupplierName()));
        updateWrapper.set(RdProjectContract::getContractTaxPoints,StringUtil.nullTo(req.getContractTaxPoints()));
        updateWrapper.set(RdProjectContract::getPaymentScheduling,paymentScheduling);
        updateWrapper.set(RdProjectContract::getAcceptancePhase,StringUtil.nullTo(req.getAcceptancePhase()));
        updateWrapper.set(RdProjectContract::getContractAmount,StringUtil.nullTo(req.getContractAmount()));
        updateWrapper.set(RdProjectContract::getInvoiceType,StringUtil.nullTo(req.getInvoiceType()));
        updateWrapper.set(RdProjectContract::getContractStartDateFlag,
                StringUtil.nullTo(req.getContractStartDateFlag()));
        updateWrapper.set(RdProjectContract::getContractEndDateFlag,
                StringUtil.nullTo(req.getContractEndDateFlag()));

        this.update(updateWrapper);
    }

    /**
     * 财务核算信息
     * @param req
     */
    @Override
    public void  updateContractFinancialInfo(RdProjectContractFinancialInfoDto req) {
        LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RdProjectContract::getPkId,req.getRdProjectContractId());
        updateWrapper.set(RdProjectContract::getAccountSubject,StringUtil.nullTo(req.getAccountSubject()));
        updateWrapper.set(RdProjectContract::getExpenseType,StringUtil.nullTo(req.getExpenseType()));
        updateWrapper.set(RdProjectContract::getReimbursementItem,StringUtil.nullTo(req.getReimbursementItem()));
        updateWrapper.set(RdProjectContract::getIsInvoice,StringUtil.nullTo(req.getIsInvoice()));
        updateWrapper.set(RdProjectContract::getRemarkLan,StringUtil.nullTo(req.getRemarkLan()));
        this.update(updateWrapper);
    }

    @Override
    public List<RdProjectContract> queryByRdProjectId(String rdProjectId) {
        return this.getBaseMapper().queryByRdProjectId(rdProjectId);
    }

    @Override
    @Transactional
    public void importAchievement(List<ContractBatchCreationDto> contractBatchCreationDtoList) {
        for (ContractBatchCreationDto o : contractBatchCreationDtoList) {
            if (!o.init()) {
                throw new RuntimeException("批量新建失败，必填字段为空或格式错误");
            }

            //保存合同预算信息
            RdProjectContractInfoDto rdProjectContractInfoDto = JsonUtil.jsonToClass(o, RdProjectContractInfoDto.class);
            RdProjectContract rdProjectContract = updateContractInfo(rdProjectContractInfoDto);
            String pkId = rdProjectContract.getPkId();

            //搜索研发项目信息
            LambdaUpdateWrapper<RdProject> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(RdProject::getProjectCode, o.getRdProjectCode());
            wrapper.eq(RdProject::getProjectName, o.getRdProjectName());
            RdProject rdProject = rdProjectMapper.selectOne(wrapper);
            if (rdProject == null) {
                throw new RuntimeException("研发项目不存在");
            }
            //保存项目合同关联表
            RdProjectContractRelation rdProjectContractRelation = new RdProjectContractRelation();
            rdProjectContractRelation.setRdProjectContractId(pkId);
            rdProjectContractRelation.setRatio(o.getProportion());
            rdProjectContractRelation.setRdProjectId(rdProject.getPkId());
            rdProjectContractRelationService.saveRdProjectContractRelation(rdProjectContractRelation);
            //研发项目关联
            ContractResearchProjectDto contractResearchProjectDto = new ContractResearchProjectDto();
            contractResearchProjectDto.setRdProjectContractId(pkId);
            contractResearchProjectDto.setIsResearchProject(o.getIsResearchProject());
            updateContractResearchProject(contractResearchProjectDto);

            //合同及支付信息
            RdProjectContractPayInfoDto rdProjectContractPayInfoDto = JsonUtil.jsonToClass(o, RdProjectContractPayInfoDto.class);
            LambdaUpdateWrapper<RdProjectContract> rdProjectContractWrapper = new LambdaUpdateWrapper<>();
            rdProjectContractWrapper.eq(RdProjectContract::getContractCode, rdProjectContractPayInfoDto.getContractCode());
            if(this.getOne(rdProjectContractWrapper) != null) {
                throw new RuntimeException("合同编号已存在");
            }
            if (StringUtil.isValidDateYmd(rdProjectContractPayInfoDto.getContractStartDate())) {
                rdProjectContractPayInfoDto.setContractStartDateFlag("Y");
            } else {
                rdProjectContractPayInfoDto.setContractStartDateFlag("N");
            }
            if (StringUtil.isValidDateYmd(rdProjectContractPayInfoDto.getContractEndDate())) {
                rdProjectContractPayInfoDto.setContractEndDateFlag("Y");
            } else {
                rdProjectContractPayInfoDto.setContractEndDateFlag("N");
            }
            rdProjectContractPayInfoDto.setRdProjectContractId(pkId);
            updateContractPayInfo(rdProjectContractPayInfoDto);

            //财务核算信息
            RdProjectContractFinancialInfoDto rdProjectContractFinancialInfoDto = JsonUtil.jsonToClass(o, RdProjectContractFinancialInfoDto.class);
            rdProjectContractFinancialInfoDto.setRdProjectContractId(pkId);
            updateContractFinancialInfo(rdProjectContractFinancialInfoDto);
        }
    }
}
