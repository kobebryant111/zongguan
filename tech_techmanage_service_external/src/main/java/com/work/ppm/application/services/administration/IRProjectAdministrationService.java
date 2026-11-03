package com.work.ppm.application.services.administration;

import com.work.ppm.application.dto.rdmanager.FileUploadDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.administration.ExportResearchFile;
import com.work.ppm.application.model.administration.MessUploadDto;
import com.work.ppm.application.model.administration.WorkResearchFile;
import com.work.ppm.application.model.commonmanager.CommRdDepartment;
import com.work.ppm.application.model.rdmanager.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRProjectAdministrationService {
    /**
     * 项目查询
     *
     * @param commRdEmployeeDto
     * @return List<HrProject>
     */
    List<HrProject> userProjectMess(CommRdEmployeeDto commRdEmployeeDto);

    /**
     * 文件上传
     *
     * @param req 项目
     * @param file 二进制
     */
    void upload(MessUploadDto req, MultipartFile file);

    /**
     * 文件修改保存
     * @param workResearchFile 文件信息保存
     */
    void documentsChanged(WorkResearchFile workResearchFile);

    /**
     * 文件删除
     * @param workResearchFile 删除文件信息
     */
    void documentsDelete(WorkResearchFile workResearchFile);

    /**
     * 文件下载
     * @param pkId 主键ID
     * @return WorkResearchFile
     */
    WorkResearchFile download(String pkId);


    /**
     * 批量下载
     * @param rdProjectFileId 项目ID
     * @return List<WorkResearchFile>
     */
    WorkResearchFile downloadFiles(String rdProjectFileId, String belongUser);

    /**
     * 更新children值
     * @param pkId 主键
     * @return List<WorkResearchFile>
     */
    List<WorkResearchFile> renewChildren(String pkId, String badge);


    /**
     * 查询研发人员项目
     *
     * @param rdQueryDeptType
     * @return List<CommTreeDto>
     */
    List<CommRdEmployeeDto> rdQueryDeptTree(RdQueryDeptType rdQueryDeptType, CommOperatorDoLoginDto user);

    /**
     * 查询研发人员项目
     *
     * @param commExportFile 对象
     * @return List<ExportResearchFile>
     */
    List<ExportResearchFile> exportFileMsg(CommExportFile commExportFile, CommOperatorDoLoginDto user);

    /**
     * 查询部门信息
     *
     * @param commEmployeeDto:
     * @date 2025/2/20
     * @version JDK 8
     * @return List<CommRdDepartment>
     */
    List<CommRdDepartment> queryDepartment(CommEmployeeDto commEmployeeDto, CommOperatorDoLoginDto user);

    /**
     * 查询研发项目信息
     *
     * @date 2025/2/20
     * @version JDK 8
     * @return List<CommRdDepartment>
     */
    List<RdProject> queryProject();
}
