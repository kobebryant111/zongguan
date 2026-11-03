package com.work.ppm.application.services.rdmanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dao.rdManager.RdContractPayRecordMapper;
import com.work.ppm.application.model.rdmanager.RdContractPayRecord;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.application.services.rdmanager.IRdContractPayRecordService;
import com.work.ppm.application.services.rdmanager.IRdProjectContractService;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同支付记录
 *
 */
@Service
public class RdContractPayRecordService extends ServiceImpl<RdContractPayRecordMapper, RdContractPayRecord>
        implements IRdContractPayRecordService {

    /**
     * 合同
     */
    @Autowired
    private IRdProjectContractService rdProjectContractService;

    @Transactional
    @Override
    public void saveRdContractPayRecord(RdContractPayRecord req) {
        if(StringUtil.isStringNotEmpty(req.getPkId())){
            LambdaUpdateWrapper<RdContractPayRecord> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RdContractPayRecord::getPkId, StringUtil.nullTo(req.getPkId()));
            updateWrapper.set(RdContractPayRecord::getPayNum,StringUtil.nullTo(req.getPayNum()));
            updateWrapper.set(RdContractPayRecord::getSerial,StringUtil.nullTo(req.getSerial()));
            updateWrapper.set(RdContractPayRecord::getPayAmount,StringUtil.nullTo(req.getPayAmount()));
            updateWrapper.set(RdContractPayRecord::getPayment,StringUtil.nullTo(req.getPayment()));
            updateWrapper.set(RdContractPayRecord::getComptime,StringUtil.nullTo(req.getComptime()));
            this.update(updateWrapper);
        }else{
            req.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
            req.setCreateDate(new Date());
            this.save(req);
        }

        RdProjectContract rdProjectContract = rdProjectContractService.getById(req.getRdProjectContractId());
        if(rdProjectContract.getContractAmount()!=null){
            BigDecimal  contractAmount= new BigDecimal(rdProjectContract.getContractAmount());
            LambdaQueryWrapper<RdContractPayRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdContractPayRecord::getRdProjectContractId,req.getRdProjectContractId());
            List<RdContractPayRecord> list = this.list(queryWrapper);
            BigDecimal sumAmount = new BigDecimal("0");
            for(RdContractPayRecord tmp:list){
                sumAmount = sumAmount.add(tmp.getPayAmount()==null?new BigDecimal("0"):
                        new BigDecimal(tmp.getPayAmount()));
            }
            if(sumAmount.compareTo(contractAmount)>0){
                throw new BizRuntimeException(String.format("支付金额(%s)不能大于合同金额(%s)",
                        sumAmount.toString(),rdProjectContract.getContractAmount().toString()));
            }
            //计算支付比例
            BigDecimal paymentScheduling = sumAmount.multiply(new BigDecimal(100))
                    .divide(contractAmount,2,BigDecimal.ROUND_UP);
            LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RdProjectContract::getPkId,StringUtil.nullTo(req.getRdProjectContractId()));
            updateWrapper.set(RdProjectContract::getPaymentScheduling,paymentScheduling);
            this.rdProjectContractService.update(updateWrapper);

        }
    }

    /**
     * 更新比例
     * @param RdProjectContractId
     */
    private void updatePaymentScheduling(String  RdProjectContractId){
        RdProjectContract rdProjectContract = rdProjectContractService.getById(RdProjectContractId);
        if(rdProjectContract.getContractAmount()!=null){
            BigDecimal  contractAmount= new BigDecimal(rdProjectContract.getContractAmount());
            LambdaQueryWrapper<RdContractPayRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RdContractPayRecord::getRdProjectContractId,RdProjectContractId);
            List<RdContractPayRecord> list = this.list(queryWrapper);
            BigDecimal sumAmount = new BigDecimal("0");
            for(RdContractPayRecord tmp:list){
                sumAmount = sumAmount.add(tmp.getPayAmount()==null?new BigDecimal("0"):
                        new BigDecimal(tmp.getPayAmount()));
            }
            if(sumAmount.compareTo(contractAmount)>0){
                throw new BizRuntimeException(String.format("支付金额(%s)不能大于合同金额(%s)",
                        sumAmount.toString(),rdProjectContract.getContractAmount().toString()));
            }
            //计算支付比例
            BigDecimal paymentScheduling = sumAmount.multiply(new BigDecimal(100))
                    .divide(contractAmount,2,BigDecimal.ROUND_UP);
            LambdaUpdateWrapper<RdProjectContract> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(RdProjectContract::getPkId,StringUtil.nullTo(RdProjectContractId));
            updateWrapper.set(RdProjectContract::getPaymentScheduling,paymentScheduling);
            this.rdProjectContractService.update(updateWrapper);

        }
    }

    @Override
    public void deleteById(String pkId) {
        RdContractPayRecord record =  this.getById(pkId);
        this.removeById(pkId);
        this.updatePaymentScheduling(record.getRdProjectContractId());

    }
}
