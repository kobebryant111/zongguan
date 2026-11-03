package com.work.ppm.application.services.personnelinterview;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.dto.personnelinterview.PersonnelInterviewSaveDto;
import com.work.ppm.application.model.personnelinterview.PpmPersonnelInterview;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 人员约谈文件表 Service
 */
public interface IPpmPersonnelInterviewService extends IService<PpmPersonnelInterview> {

    /**
     * 人员约谈保存
     */
    void save(PersonnelInterviewSaveDto req, MultipartFile picFile, MultipartFile wordFile);

    /**
     * 人员约谈保存删除
     */
    void delete(List<String> personnelInterviewList);

    /**
     * 人员约谈分页查询
     *
     * @param pager 分页对象
     */
    void pageList(Pager pager) ;

    /**
     * 人员约谈文件下载
     */
    void download(String fileId, String type, HttpServletResponse response) throws IOException;

}
