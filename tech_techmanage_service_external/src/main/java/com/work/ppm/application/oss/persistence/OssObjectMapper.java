package com.work.ppm.application.oss.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.ppm.application.dto.inforTechOut.OssObjectPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface OssObjectMapper extends BaseMapper<OssObjectPo> {
}