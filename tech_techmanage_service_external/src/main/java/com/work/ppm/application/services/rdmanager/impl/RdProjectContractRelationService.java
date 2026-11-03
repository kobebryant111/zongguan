package com.work.ppm.application.services.rdmanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dao.rdManager.RdProjectContractRelationMapper;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationQueryDto;
import com.work.ppm.application.model.rdmanager.RdProjectContractRelation;
import com.work.ppm.application.services.rdmanager.IRdProjectContractRelationService;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  项目合同关联表
 *
 */
@Service
public class RdProjectContractRelationService
        extends ServiceImpl<RdProjectContractRelationMapper, RdProjectContractRelation>
        implements IRdProjectContractRelationService {



    @Override
    public List<RdProjectContractRelationDto> query (RdProjectContractRelationQueryDto req) {
        return this.getBaseMapper().queryList(req);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRdProjectContractRelation(RdProjectContractRelation req) {
        if(req.getRatio()!=null&&(req.getRatio().compareTo(new BigDecimal("100"))>0)){
            throw new BizRuntimeException("项目占比不能超过100");
        }
        if(req.getRatio()!=null&&(req.getRatio().compareTo(new BigDecimal("0"))<=0)){
            throw new BizRuntimeException("项目占比不能小于等于0");
        }
        AssertUtil.notNull(req.getRdProjectContractId(),"合同id不能空");
        if(StringUtil.isStringNotEmpty(req.getPkId())){
			LambdaUpdateWrapper<RdProjectContractRelation> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(RdProjectContractRelation::getPkId,req.getPkId());
			updateWrapper.set(RdProjectContractRelation::getRatio,req.getRatio());
			updateWrapper.set(RdProjectContractRelation::getUpdateBy, UserSessionUtil.getCurrentUser().getUserid());
			updateWrapper.set(RdProjectContractRelation::getUpdateDate,new Date());
			this.update(updateWrapper);
		}else{
			LambdaQueryWrapper<RdProjectContractRelation> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(RdProjectContractRelation::getRdProjectContractId,req.getRdProjectContractId());
			queryWrapper.eq(RdProjectContractRelation::getRdProjectId,req.getRdProjectId());
			List<RdProjectContractRelation> list = this.list(queryWrapper);
			if(list!=null&&list.size()>0){
				throw new BizRuntimeException("该项目已经被关联");
			}
			req.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
			req.setCreateDate(new Date());
			this.save(req);
		}
        LambdaQueryWrapper<RdProjectContractRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RdProjectContractRelation::getRdProjectContractId,req.getRdProjectContractId());
        List<RdProjectContractRelation> list = this.list(queryWrapper);
        BigDecimal sumRatio = new BigDecimal("0");
        for(RdProjectContractRelation r:list){
            sumRatio = sumRatio.add(r.getRatio()==null?new BigDecimal("0"):r.getRatio());
        }
        if(sumRatio.compareTo(new BigDecimal("100"))>0){
            throw new BizRuntimeException("所有关联项目占比总和不能超过100");
        }


    }
}
