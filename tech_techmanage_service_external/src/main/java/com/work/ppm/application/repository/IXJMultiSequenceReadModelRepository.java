package com.work.ppm.application.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.model.inforTechOut.XJMultiSequencePO;

public interface IXJMultiSequenceReadModelRepository extends IService<XJMultiSequencePO> {
    /**
     * 插入自增
     * @param multiSequencePO
     */
    void insertMultiSequence(XJMultiSequencePO multiSequencePO);
}
