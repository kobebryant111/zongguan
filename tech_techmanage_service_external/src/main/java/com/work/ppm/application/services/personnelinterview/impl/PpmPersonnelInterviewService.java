package com.work.ppm.application.services.personnelinterview.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dao.personnelinterview.PpmPersonnelInterviewMapper;
import com.work.ppm.application.dto.personnelinterview.PersonnelInterviewPageDto;
import com.work.ppm.application.dto.personnelinterview.PersonnelInterviewSaveDto;
import com.work.ppm.application.model.personnelinterview.PpmPersonnelInterview;
import com.work.ppm.application.model.personnelinterview.PpmPersonnelInterviewFile;
import com.work.ppm.application.services.personnelinterview.IPpmPersonnelInterviewService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.TemplateStructureCheckUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * 人员约谈表 ServiceImpl
 *
 */
@Slf4j
@Service
public class PpmPersonnelInterviewService extends ServiceImpl<PpmPersonnelInterviewMapper, PpmPersonnelInterview>
        implements IPpmPersonnelInterviewService {

    /**
     * 实体类路径
     */
    private static String PPMPERSONNELINTERVIEW = "com.work.ppm.application.dto.personnelinterview.PersonnelInterviewPageDto";

    /**
     * 模板地址
     */
    @Value("${file.personnelInterview}")
    private String templatePath;

    /**
     * ppmPersonnelInterviewFileService
     */
    @Autowired
    PpmPersonnelInterviewFileService ppmPersonnelInterviewFileService;

    /**
     * templateStructureCheckUtil
     */
    @Autowired
    TemplateStructureCheckUtil templateStructureCheckUtil;

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    @Transactional
    public void save(PersonnelInterviewSaveDto req, MultipartFile picFile, MultipartFile wordFile) {
        Date date = new Date();
        String userid = UserSessionUtil.getCurrentUser().getUserid();
        PpmPersonnelInterview ppmPersonnelInterview = new PpmPersonnelInterview();
        ppmPersonnelInterview.setTitle(req.getTitle());
        ppmPersonnelInterview.setCreateBy(userid);
        ppmPersonnelInterview.setCreateDate(date);
        ppmPersonnelInterview.setDeleted("0");
        this.save(ppmPersonnelInterview);
        String personnelInterviewId = ppmPersonnelInterview.getPkId();
        //约谈内容
        if (!ObjectUtils.isEmpty(wordFile)) {
            Path template = Paths.get(templatePath);
            TemplateStructureCheckUtil.CheckResult checkResult = templateStructureCheckUtil.check(wordFile, template);
            if (!checkResult.isPass()) {
                throw new BizRuntimeException(checkResult.getErrors().toString());
            }
            String wordFileName = wordFile.getOriginalFilename();
            String distFilePath = FileUtil.generateFilePath(FileUtil.getFileType(wordFileName));
            File distFile = new File(distFilePath);
            try {
                wordFile.transferTo(distFile);
                PpmPersonnelInterviewFile fileUploadRecord = new PpmPersonnelInterviewFile();
                fileUploadRecord.setPersonnelInterviewId(personnelInterviewId);
                fileUploadRecord.setFilePath(distFilePath);
                fileUploadRecord.setFileName(wordFileName);
                fileUploadRecord.setType("1");//约谈内容
                fileUploadRecord.setCreateBy(userid);
                fileUploadRecord.setDeleted("0");
                fileUploadRecord.setCreateDate(date);
                ppmPersonnelInterviewFileService.save(fileUploadRecord);
            } catch (Exception e) {
                distFile.delete();
                log.error("上传约谈内容失败({},{})", wordFileName, distFilePath, e);
                throw new BizRuntimeException("上传约谈内容失败");
            }
        }
        //约谈照片
        if (!ObjectUtils.isEmpty(picFile)) {
            String picFileName = picFile.getOriginalFilename();
            String distFilePath = FileUtil.generateFilePath(FileUtil.getFileType(picFileName));
            File distFile = new File(distFilePath);
            try {
                picFile.transferTo(distFile);
                PpmPersonnelInterviewFile fileUploadRecord = new PpmPersonnelInterviewFile();
                fileUploadRecord.setPersonnelInterviewId(personnelInterviewId);
                fileUploadRecord.setFilePath(distFilePath);
                fileUploadRecord.setFileName(picFileName);
                fileUploadRecord.setType("0");//约谈照片
                fileUploadRecord.setCreateBy(userid);
                fileUploadRecord.setDeleted("0");
                fileUploadRecord.setCreateDate(date);
                ppmPersonnelInterviewFileService.save(fileUploadRecord);
            } catch (Exception e) {
                distFile.delete();
                log.error("上传约谈照片失败({},{})", picFileName, distFilePath, e);
                throw new BizRuntimeException("上传约谈照片失败");
            }
        }
    }

    @Override
    public void delete(List<String> personnelInterviewList) {
        try {
            LambdaQueryWrapper<PpmPersonnelInterview> personnelInterviewWrapper = new LambdaQueryWrapper<>();
            personnelInterviewWrapper.in(PpmPersonnelInterview::getPkId, personnelInterviewList);
            remove(personnelInterviewWrapper);
            LambdaQueryWrapper<PpmPersonnelInterviewFile> personnelInterviewFileWrapper = new LambdaQueryWrapper<>();
            personnelInterviewFileWrapper.in(PpmPersonnelInterviewFile::getPersonnelInterviewId, personnelInterviewList);
            List<PpmPersonnelInterviewFile> ppmPersonnelInterviewFiles = ppmPersonnelInterviewFileService
                    .getBaseMapper().selectList(personnelInterviewFileWrapper);
            for (PpmPersonnelInterviewFile ppmPersonnelInterviewFile : ppmPersonnelInterviewFiles) {
                File distFile = new File(ppmPersonnelInterviewFile.getFilePath());
                distFile.delete();
            }
            ppmPersonnelInterviewFileService.remove(personnelInterviewFileWrapper);
        } catch (Exception e) {
            log.error("人员约谈记录删除失败", e);
            throw new BizRuntimeException("人员约谈记录删除失败");
        }
    }

    @Override
    public void pageList(Pager pager) {
        myBatisDao.selectPager(PPMPERSONNELINTERVIEW, null, pager,"pageList");
        List<PersonnelInterviewPageDto> list = JsonUtil.jsonToList(pager.getList(), PersonnelInterviewPageDto.class);
        int index = pager.getIndex();
        int size = pager.getSize();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSeq((index - 1) * size + i + 1);
        }
        pager.setList(list);
    }

    @Override
    public void download(String fileId, String type, HttpServletResponse response)  throws IOException {
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
        InputStream inputStream = null;
        try {
            if (StringUtils.equals("0", type)) {
                log.warn("===========》约谈模板下载地址：{}", templatePath);
                inputStream = Files.newInputStream(Paths.get(templatePath));
                response.setHeader("Content-disposition", "attachment;filename=\"" +
                        URLEncoder.encode("外包人员约谈表.docx", "UTF-8") + "\"");
            } else {
                PpmPersonnelInterviewFile file = ppmPersonnelInterviewFileService.getById(fileId);
                log.warn("===========》约谈文件下载地址：{}", file.getFilePath());
                inputStream = Files.newInputStream(Paths.get(file.getFilePath()));
                response.setHeader("Content-disposition", "attachment;filename=\"" +
                        URLEncoder.encode(file.getFileName(), "UTF-8") + "\"");
            }
            OutputStream out = response.getOutputStream();

            IOUtils.copy(inputStream, out);
            inputStream.close();
            response.getOutputStream().flush();

        } catch (Exception e) {
            log.error("约谈文件下载异常:", e);
            throw new BizException(e);
        } finally {
            inputStream.close();
        }
    }

}
