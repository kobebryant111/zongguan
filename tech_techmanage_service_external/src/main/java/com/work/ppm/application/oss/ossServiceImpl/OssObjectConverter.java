package com.work.ppm.application.oss.ossServiceImpl;

import com.work.ppm.application.dto.inforTechOut.OssObjectPo;
import com.work.ppm.application.dto.inforTechOut.OssObjectVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OssObjectConverter extends BaseConverter<OssObjectPo, OssObjectVo> {

}
