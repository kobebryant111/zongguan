package com.work.ppm.application.persistence;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.model.inforTechOut.XJMultiSequencePO;
import com.work.ppm.application.persistence.mapper.XJMultiSequencePOMapper;
import com.work.ppm.application.repository.IXJMultiSequenceReadModelRepository;
import org.springframework.stereotype.Repository;

/**
 * 自动增加资源库实现类
 *
 */
@Repository
public class XJMultiSequenceReadModelRepository extends ServiceImpl<XJMultiSequencePOMapper, XJMultiSequencePO> implements IXJMultiSequenceReadModelRepository {
    @Override
    public void insertMultiSequence(XJMultiSequencePO multiSequencePO) {
        baseMapper.insertMultiSequence(multiSequencePO);
    }

}
