package com.tansun.wf.platform.mapper;

import com.tansun.wf.activiti.model.HiIdentityLinkEntity;
import org.camunda.bpm.engine.impl.db.ListQueryParameterObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijiang
 * @create 2021-03-01 9:35
 */
@Repository
public interface ActHiIdentityLinkMapper {

    void insertHiIdentityLink(HiIdentityLinkEntity hiIdentityLinkEntity);

    List<HiIdentityLinkEntity>  selectHiIdentityLinks(ListQueryParameterObject parameter);
}
