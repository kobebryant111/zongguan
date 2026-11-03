package com.work.ppm.application.dao.rdManager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationQueryDto;
import com.work.ppm.application.model.rdmanager.RdProjectContractRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 研发项目合同关系
 *
 */
@Mapper
public interface RdProjectContractRelationMapper extends BaseMapper<RdProjectContractRelation> {


    /**
     * 查询关联项目
     * @param req
     * @return List<RdProjectContractRelationDto>
     */
    List<RdProjectContractRelationDto> queryList(@Param("req") RdProjectContractRelationQueryDto req);
}
