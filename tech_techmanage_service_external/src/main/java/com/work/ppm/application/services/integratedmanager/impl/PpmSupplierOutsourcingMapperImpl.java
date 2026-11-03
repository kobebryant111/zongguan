package com.work.ppm.application.services.integratedmanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.model.integratedmanager.PpmSupplierOutsourcing;
import com.work.ppm.application.services.integratedmanager.IPpmSupplierOutsourcingMapper;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 持久化实现类
 * </p>
 *
 * @since 2026-03-10
 */
@Slf4j
@Component
public class PpmSupplierOutsourcingMapperImpl extends ServiceImpl<IPpmSupplierOutsourcingMapper, PpmSupplierOutsourcing> {
    /**
     * 查询有效关联外包人数
     *
     * @param relateId 关联合同序号
     * @return count
     */
    public Integer getOutsourceNum(Long relateId) {
        LambdaQueryWrapper<PpmSupplierOutsourcing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PpmSupplierOutsourcing::getRelateId, String.valueOf(relateId))
                .eq(PpmSupplierOutsourcing::getRelateState, "Y");
        return this.count(queryWrapper);
    }

    /**
     * 查询关联级别人数
     *
     * @param relateId        合同序号
     * @param evaluationLevel 级别
     * @return count
     */
    public Integer getLevelNum(String relateId, String evaluationLevel) {
        LambdaQueryWrapper<PpmSupplierOutsourcing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PpmSupplierOutsourcing::getRelateId, relateId)
                .eq(PpmSupplierOutsourcing::getRelateState, "Y")
                .eq(StringUtil.isNotBlank(evaluationLevel),
                        PpmSupplierOutsourcing::getEvaluationLevel, evaluationLevel);
        return this.count(queryWrapper);
    }

    /**
     * 查询开发人员所有关联
     *
     * @param jobId 工号
     * @return list
     */
    public List<PpmSupplierOutsourcing> getRelateByJobId(String jobId) {
        LambdaQueryWrapper<PpmSupplierOutsourcing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PpmSupplierOutsourcing::getJobId, jobId);
        return this.list(queryWrapper);
    }

}
