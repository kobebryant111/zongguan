package com.work.ppm.application.persistence;

import com.work.ppm.application.dto.inforTechOut.PpmOutsourcingXklx;
import com.work.ppm.application.repository.IBaseDaoSuper;
import com.work.ppm.commons.dao.BaseDaoSupper;
import org.springframework.stereotype.Service;

@Service("baseDaoSuperService")
public class BaseDaoSuperService extends BaseDaoSupper implements IBaseDaoSuper {

    @Override
    public Long getNextSequence() {
        return getNextSequence(PpmOutsourcingXklx.class);
    }
}
