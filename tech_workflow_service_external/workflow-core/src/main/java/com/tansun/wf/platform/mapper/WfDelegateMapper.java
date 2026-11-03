package com.tansun.wf.platform.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tansun.wf.extend.db.WfBaseMapper;
import com.tansun.wf.platform.model.WfDelegateEntity;
import com.tansun.wf.platform.query.WfDelegateQuery;

@Repository
public interface WfDelegateMapper extends WfBaseMapper<WfDelegateEntity> {
	
	public  List<WfDelegateEntity>  findDelegateList(WfDelegateQuery   query);

}
