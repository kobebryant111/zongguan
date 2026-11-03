package com.work.ppm.application.services.rdmanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractRelationQueryDto;
import com.work.ppm.application.model.rdmanager.RdProjectContractRelation;

import java.util.List;

public interface IRdProjectContractRelationService extends IService<RdProjectContractRelation> {


    /**
     * 查询项目
     * @param req
     * @return  List<RdProjectContractRelationDto>
     */
    List<RdProjectContractRelationDto> query(RdProjectContractRelationQueryDto req);

    /**
     * 保存
     * @param req
     */
    void saveRdProjectContractRelation(RdProjectContractRelation req);
}
