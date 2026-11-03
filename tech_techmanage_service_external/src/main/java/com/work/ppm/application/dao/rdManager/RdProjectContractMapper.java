package com.work.ppm.application.dao.rdManager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryResultDto;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.commons.pager.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 研发项目管理
 *
 */
@Mapper
public interface RdProjectContractMapper extends BaseMapper<RdProjectContract> {


    /**
     * 查询合同
     * @param req
     * @param pager
     * @return List<RdProjectContract>
     */
    List<RdProjectContractQueryResultDto> queryPager(@Param("req") RdProjectContractQueryDto req, Pager pager);

    /**
     * 根据项目id合同
     * @param rdProjectId
     * @return
     */
    List<RdProjectContract> queryByRdProjectId(@Param("rdProjectId") String rdProjectId);
}
