package com.work.ppm.application.services.rdmanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.dto.rdmanager.FileUploadDto;
import com.work.ppm.application.model.rdmanager.RdProjectFile;
import org.springframework.web.multipart.MultipartFile;

public interface IRdProjectFileService extends IService<RdProjectFile> {

    /**
     * 文件上传
     * @param req
     * @param file
     */
    void upload(FileUploadDto req, MultipartFile file);
}
