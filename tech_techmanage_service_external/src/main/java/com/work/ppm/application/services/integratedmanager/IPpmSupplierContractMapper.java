package com.work.ppm.application.services.integratedmanager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContract;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContractResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 供应商合同信息表 Mapper 接口
 * </p>
 *
 * @since 2026-03-09
 */
@Mapper
@Repository
public interface IPpmSupplierContractMapper extends BaseMapper<PpmSupplierContract> {

    /**
     * 查询详情
     *
     * @param ppmSupplierContract 对象
     * @return 对象
     */
    List<PpmSupplierContractResponse> queryDetail(PpmSupplierContract ppmSupplierContract);
}
