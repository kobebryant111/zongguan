package com.work.ppm.application.services.personnelinterview.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dao.personnelinterview.PpmPersonnelInterviewFileMapper;
import com.work.ppm.application.model.personnelinterview.PpmPersonnelInterviewFile;
import com.work.ppm.application.services.personnelinterview.IPpmPersonnelInterviewFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 人员约谈文件表 ServiceImpl
 *
 */
@Slf4j
@Service
public class PpmPersonnelInterviewFileService extends ServiceImpl<PpmPersonnelInterviewFileMapper, PpmPersonnelInterviewFile>
        implements IPpmPersonnelInterviewFileService {

}
