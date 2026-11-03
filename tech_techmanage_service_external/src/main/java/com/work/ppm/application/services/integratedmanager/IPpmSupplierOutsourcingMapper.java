package com.work.ppm.application.services.integratedmanager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.ppm.application.model.integratedmanager.PpmSupplierOutsourcing;
import com.work.ppm.application.model.integratedmanager.ext.PpmWorkloadKqInfoView;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @since 2026-03-10
 */
@Mapper
@Repository
public interface IPpmSupplierOutsourcingMapper extends BaseMapper<PpmSupplierOutsourcing> {

    /**
     * 查询考勤年月内的关联合同
     * @param  params params
     * @return list
     */
    List<PpmWorkloadKqInfoView> selectWorkload(Map<String, Object> params);
}
