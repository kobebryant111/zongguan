package com.work.ppm.application.oss.ossServiceImpl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dto.inforTechOut.OssObjectPo;
import com.work.ppm.application.oss.ossServiceImpl.OssObjectService;
import com.work.ppm.application.oss.persistence.OssObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class OssObjectServiceImpl extends ServiceImpl<OssObjectMapper, OssObjectPo> implements OssObjectService {
}

