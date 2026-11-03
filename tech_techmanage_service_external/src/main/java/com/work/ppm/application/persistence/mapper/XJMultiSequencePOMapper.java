package com.work.ppm.application.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.ppm.application.model.inforTechOut.XJMultiSequencePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XJMultiSequencePOMapper extends BaseMapper<XJMultiSequencePO> {
    /**
     * 插入自增
     * @param multiSequencePO
     */
    void insertMultiSequence(XJMultiSequencePO multiSequencePO);
}
