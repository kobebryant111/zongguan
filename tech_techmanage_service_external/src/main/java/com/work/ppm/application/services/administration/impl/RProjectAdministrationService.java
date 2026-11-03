package com.work.ppm.application.services.administration.impl;

import cn.hutool.core.lang.hash.Hash;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dao.newrdmanager.RProjectAdministrationMapper;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.administration.ExportResearchFile;
import com.work.ppm.application.model.administration.MessUploadDto;
import com.work.ppm.application.model.administration.WorkResearchFile;
import com.work.ppm.application.model.commonmanager.CommRdDepartment;
import com.work.ppm.application.model.rdmanager.*;
import com.work.ppm.application.services.administration.IRProjectAdministrationService;
import com.work.ppm.application.services.rdmanager.IHrResourcesService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 研发项目管理
 *
 */
@Service
@Slf4j
public class RProjectAdministrationService extends ServiceImpl<RProjectAdministrationMapper, WorkResearchFile>
        implements IRProjectAdministrationService {

    /**
     * 实体类路径
     */
    private static final String HRPROJECT = "com.work.ppm.application.model.rdmanager.HrProject";
    /**
     * 实体类路径
     */
    private static String COMMRDEMPLOYEE = "com.work.ppm.application.model.rdmanager.CommRdEmployeeDto";

    @Autowired
    private IMyBatisDao myBatisDao;
    @Autowired
    private IHrResourcesService hrResourcesService;

    @Override
    public List<HrProject> userProjectMess(CommRdEmployeeDto commRdEmployeeDto) {
        List<HrProject> dataList = null;
        try {
            if (commRdEmployeeDto.getEid() == null) {
                return null;
            }
            Map<String, Object> map = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
            dataList = myBatisDao.selectListT(HRPROJECT, map, "userProjectMess");
            for (HrProject hrProject : dataList) {

                Map<String, Object> map1 = StringUtil.objectTOMap(hrProject, HrProject.class);
                List<WorkResearchFile> rdProjectFiles = myBatisDao.selectListT(HRPROJECT, map1, "rdProjectFiles");

                hrProject.setChildren(rdProjectFiles);
            }
        } catch (Exception e) {
            log.error("查询研发人员项目:" + e.getMessage(), e);
            throw new BizRuntimeException("查询研发人员项目");
        }
        return dataList;
    }

    @Override
    public void upload(MessUploadDto req, MultipartFile file) {

        req.setFileName(com.github.pagehelper.util.StringUtil.isEmpty(req.getFileName()) ? file.getOriginalFilename() : req.getFileName());
        String distFilePath = FileUtil.generateFilePath("PRO_ADMINIST", FileUtil.getFileType(req.getFileName()));
        try {
            File distFile = new File(distFilePath);
            file.transferTo(distFile);
            WorkResearchFile fileUploadRecord = new WorkResearchFile();
            fileUploadRecord.setRdProjectId(req.getRdProjectId());
            fileUploadRecord.setFilePath(distFilePath);
            fileUploadRecord.setFileName(req.getFileName());
            fileUploadRecord.setSourceFileName(file.getOriginalFilename());
            fileUploadRecord.setType(req.getType());
            fileUploadRecord.setCreateBy(UserSessionUtil.getCurrentUser().getUserid());
            fileUploadRecord.setDeleted("0");
            fileUploadRecord.setCreateDate(new Date());
            fileUploadRecord.setBelongUser(req.getBadge());
            extracted(fileUploadRecord);
            this.save(fileUploadRecord);
        } catch (IOException e) {
            log.error("上传文件失败({},{})", req.getFileName(), distFilePath, e);
            throw new BizRuntimeException("上传文件失败");
        }
    }

    private void extracted(WorkResearchFile req) {
        boolean duplicate = true;
        int suffix = 1;
        while (duplicate) {
            final LambdaQueryWrapper<WorkResearchFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WorkResearchFile::getRdProjectId, req.getRdProjectId());
            queryWrapper.eq(WorkResearchFile::getFileName, req.getFileName());
            queryWrapper.eq(WorkResearchFile::getBelongUser, req.getBelongUser());
            if (!ObjectUtils.isEmpty(req.getPkId())) {
                queryWrapper.ne(WorkResearchFile::getPkId, req.getPkId());
            }
            final List<WorkResearchFile> workResearchFileList = rProjectAdministrationMapper.selectList(queryWrapper);
            if (workResearchFileList.size() == 0) {
                duplicate = false;
            } else if (suffix == 1) {
                final String[] splitdot = req.getFileName().split("\\.");
                final String join = String.join(".", Arrays.copyOf(splitdot, splitdot.length - 1));
                req.setFileName(join + " " + "(" + suffix + ")" + "." + splitdot[splitdot.length - 1]);
                suffix++;
            } else {
                final String[] split = req.getFileName().split(" ");
                final String[] splitdot = req.getFileName().split("\\.");
                final String join = String.join(" ", Arrays.copyOf(split, split.length - 1));
                req.setFileName(join + " " + "(" + suffix + ")" + "." + splitdot[splitdot.length - 1]);
                suffix++;
            }
        }
    }

    @Override
    public void documentsChanged(WorkResearchFile workResearchFile) {
        try {
            extracted(workResearchFile);
            Map<String, Object> map = StringUtil.objectTOMap(workResearchFile, WorkResearchFile.class);
            myBatisDao.executionMap(HRPROJECT, map, IMyBatisDao.SQL.UPDATE, "documentsChanged");
        } catch (Exception e) {
            log.error("文件保存:" + e.getMessage(), e);
            throw new BizRuntimeException("文件保存");
        }
    }

    @Override
    public void documentsDelete(WorkResearchFile workResearchFile) {
        try {
            Map<String, Object> map = StringUtil.objectTOMap(workResearchFile, WorkResearchFile.class);
            myBatisDao.executionMap(HRPROJECT, map, IMyBatisDao.SQL.DELETE, "documentsDelete");
        } catch (Exception e) {
            log.error("文件删除:" + e.getMessage(), e);
            throw new BizRuntimeException("文件删除");
        }
    }

    @Override
    public WorkResearchFile download(String pkId) {
        try {
            return this.getById(pkId);
        } catch (Exception e) {
            log.error("下载文件:" + e.getMessage(), e);
            throw new BizRuntimeException("下载文件");
        }
    }

    @Autowired
    RProjectAdministrationMapper rProjectAdministrationMapper;


    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";

    public List<String> queryOperaAuthEncoding(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<String> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryRoleId");
        return list;
    }

    @Override
    public List<CommRdEmployeeDto> rdQueryDeptTree(RdQueryDeptType rdQueryDeptType, CommOperatorDoLoginDto user) {
        try {
            boolean isShow = false;
            List<String> roleIdList = queryOperaAuthEncoding(user.getUserid());

            List<ImRoleDoLoginDto> roles = user.getRoles();
            for (ImRoleDoLoginDto role : roles) {
                String roleId = role.getRoleId();
                if ("DEP_SPECIALIST".equals(roleId)) {
                    isShow = true;
                }
            }
            List<CommRdEmployeeDto> queryPagerList = new ArrayList<>();
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("badge", user.getUserid());
            Integer depid = myBatisDao.selectClass(COMMRDEMPLOYEE, map1, "querDepidByBadge");
            if (roleIdList.isEmpty()) {
                map1.put("depid", depid);
            }
            if (isShow) {
                map1.put("badge", "");
            }
            List<CommRdEmployeeDto> commRdEmployeeDtoList = myBatisDao.selectListT(HRPROJECT, map1, "rdQueryDeptTree");
            queryPagerList.addAll(commRdEmployeeDtoList);
            return queryPagerList;
        } catch (Exception e) {
            log.error("查询研发人员项目:" + e.getMessage(), e);
            throw new BizRuntimeException("查询研发人员项目");
        }
    }

    @Override
    public List<ExportResearchFile> exportFileMsg(CommExportFile commExportFile, CommOperatorDoLoginDto user) {
        try {
            // 将时间查询条件转换为日期对象
            if (!ObjectUtils.isEmpty(commExportFile.getEntryTime())) {
                commExportFile.setEntryStartDate(commExportFile.getEntryTime()[0]);
                commExportFile.setEntryEndDate(commExportFile.getEntryTime()[1]);
            }
        } catch (Exception e) {
            log.error("研发文档上传记录时间格式转化异常,错误信息为:{}。", e);
        }
        List<ImRoleDoLoginDto> roles = user.getRoles();
        boolean isShow = false;
        //判断是否是部门专员以及是总经理室成员
        for (ImRoleDoLoginDto role : roles) {
            String roleId = role.getRoleId();
            if ("DEP_SPECIALIST".equals(roleId)) {
                isShow = true;
            }
        }

        Map<String, Object> exportFileQueryMap = StringUtil.objectTOMap(commExportFile, CommExportFile.class);
        if (isShow) {
            exportFileQueryMap.put("badge", "");
        } else {
            exportFileQueryMap.put("badge", user.getUserid());
        }
        List<ExportResearchFile> exportFileMsg = myBatisDao.selectListT(HRPROJECT, exportFileQueryMap, "exportFileMsg");
        return exportFileMsg;
    }

    @Override
    public List<CommRdDepartment> queryDepartment(CommEmployeeDto commEmployeeDto, CommOperatorDoLoginDto user) {
        boolean isShow = false;
        List<ImRoleDoLoginDto> roles = user.getRoles();
        //是管理员则下拉框显示所有的部门信息
        for (ImRoleDoLoginDto role : roles) {
            String roleId = role.getRoleId();
            if ("ADMIN_SPECIALIST".equals(roleId) || "00000001".equals(roleId)) {
                isShow = true;
            }
        }
        //专员以及总裁室,或普通员工下拉框只显示本部门信息
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        if (isShow) {
            map.put("depid", "");
        } else {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("badge", user.getUserid());
            Integer depid = myBatisDao.selectClass(COMMRDEMPLOYEE, map1, "querDepidByBadge");
            map.put("depid", depid);
        }
        List<CommRdDepartment> list = myBatisDao.selectListT(HRPROJECT, map, "queryDepartment");
        return list;
    }

    @Override
    public List<RdProject> queryProject() {
        Map<String, Object> map = new HashMap<>();
        List<RdProject> list = myBatisDao.selectListT(HRPROJECT, map, "queryProject");
        return list;
    }

    @Override
    public WorkResearchFile downloadFiles(String rdProjectFileId, String belongUser) {
        final WorkResearchFile researchFile = new WorkResearchFile();
        // 输出压缩包路径
        String distFilePath = FileUtil.generateFilePath("PRO_ADMINIST", "zip");
        try (final FileOutputStream fos = new FileOutputStream(distFilePath);
             final ZipOutputStream zos = new ZipOutputStream(fos)) {
            final LambdaQueryWrapper<WorkResearchFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WorkResearchFile::getRdProjectId, rdProjectFileId);
            queryWrapper.eq(WorkResearchFile::getBelongUser, belongUser);
            final List<WorkResearchFile> workResearchFileList = rProjectAdministrationMapper.selectList(queryWrapper);
            // 遍历所有文件并将其添加到压缩包中
            for (WorkResearchFile file : workResearchFileList) {
                final File inputFile = new File(file.getFilePath());
                if (!(inputFile.exists() && inputFile.isFile())) {
                    log.error("文件不存在：" + file.getFilePath());
                    continue;
                }
                // 创建输入流
                try (final FileInputStream fis = new FileInputStream(inputFile)) {
                    // 创建一个新的zip条目
                    zos.putNextEntry(new ZipEntry(file.getFileName()));
                    // 将文件内容写入到压缩包中
                    final byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    // 关闭当前条目
                    zos.closeEntry();
                }
            }
            researchFile.setFilePath(distFilePath);
            return researchFile;
        } catch (Exception e) {
            log.error("批量下载:" + e.getMessage(), e);
            throw new BizRuntimeException("批量下载");
        }

    }


    @Override
    public List<WorkResearchFile> renewChildren(String pkId, String badge) {
        try {
            final LambdaQueryWrapper<WorkResearchFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WorkResearchFile::getRdProjectId, pkId);
            queryWrapper.eq(WorkResearchFile::getBelongUser, badge);
            queryWrapper.orderByDesc(WorkResearchFile::getCreateDate);
            final List<WorkResearchFile> workResearchFileList = rProjectAdministrationMapper.selectList(queryWrapper);
            return workResearchFileList;
        } catch (Exception e) {
            log.error("更新children值:" + e.getMessage(), e);
            throw new BizRuntimeException("更新children值");
        }
    }
}
