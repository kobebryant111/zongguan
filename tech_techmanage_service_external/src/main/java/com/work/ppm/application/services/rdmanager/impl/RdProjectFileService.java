package com.work.ppm.application.services.rdmanager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.work.ppm.application.dao.rdManager.RdProjectFileMapper;
import com.work.ppm.application.dto.rdmanager.FileUploadDto;
import com.work.ppm.application.model.rdmanager.RdProjectFile;
import com.work.ppm.application.services.rdmanager.IRdProjectFileService;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 研发项目管理
 *
 */
@Slf4j
@Service
public class RdProjectFileService extends ServiceImpl<RdProjectFileMapper, RdProjectFile>
        implements IRdProjectFileService {


    @Override
    public void upload(FileUploadDto req, MultipartFile file) {
        String f1 =file.getOriginalFilename();
        String f2 =file.getName();
        req.setFileName(StringUtil.isEmpty(req.getFileName())?file.getOriginalFilename():req.getFileName());
        String distFilePath = FileUtil.generateFilePath("RD_PROJECT",FileUtil.getFileType(req.getFileName()));
        try {
            File distFile = new File(distFilePath);
            file.transferTo(distFile);
            RdProjectFile fileUploadRecord = new RdProjectFile();
            fileUploadRecord.setRdProjectId(req.getRdProjectId());
            fileUploadRecord.setFilePath(distFilePath);
            fileUploadRecord.setFileName(req.getFileName());
            fileUploadRecord.setSourceFileName(file.getOriginalFilename());
            fileUploadRecord.setType(req.getType());
            fileUploadRecord.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
            fileUploadRecord.setDeleted("0");
            fileUploadRecord.setCreateDate(new Date());
            this.save(fileUploadRecord);
        } catch (IOException e) {
            log.error("上传文件失败({},{})",req.getFileName(),distFilePath,e);
            throw new BizRuntimeException("上传文件失败");
        }
    }

}
