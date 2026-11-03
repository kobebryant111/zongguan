package com.work.ppm.application.services.systemmanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.model.integratedmanager.FaceDeviceInfoJob;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.CommJob;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.quartz.interfaces.ISyncDataJobService;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommJobService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service("syncDataJobService")
public class SyncDataJobService implements ISyncDataJobService {

    @Value("${file.data-path}")
    private String dataFile;
    @Value("${file.devicedata-path}")
    private String devicedataFile;

    @Value("${file.data-path-sftp}")
    private String dataFileSftp;

    // 员工服务
    @Autowired
    private ICommJobService commJobService;
    // 员工服务
    @Autowired
    private ICommEmployeeService commEmployeeService;
    // 部门服务
    @Autowired
    private ICommDepartmentService commDepartmentService;
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;
    //闸机权限服务
    @Autowired
    private IPpmZjAuthorityService ppmZjAuthorityService;

    @Autowired
    private IFaceDeviceInfoService faceDeviceInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDepartmentJob() throws IOException {
        Date date = DateUtil.addOrSubDate(new Date(), -1);
        String dateStr = DateUtil.dateToStr(date, "yyyyMMdd");
        log.info("日期：{}", dateStr);
        String fileName = dataFileSftp + "department/SBJ_V_DEPARTMENT_INFO_" + dateStr + ".TXT";
//        String fileName = dataFileSftp + "department/SBJ_V_DEPARTMENT_INFO_20240305.TXT";
        log.info("文件名:{}", fileName);
        List<String> sources = FileUtil.parseTxt(fileName);
        if (!CollectionUtils.isEmpty(sources)) {
            List<CommDepartment> departmentList = Lists.newArrayList();
            sources.forEach(context -> {
                String[] lineStr = context.split("\u0007");
                CommDepartment department = CommDepartment.builder()
                        .depid(NumberUtil.getLong(lineStr[37]))
                        .depcode(lineStr[6])
                        .location(lineStr[16])
                        .depgrade(lineStr[8])
                        .compid(NumberUtil.getLong(lineStr[32]))
                        .adminid(NumberUtil.getLong(lineStr[38]))
                        .isdisabled(lineStr[62])
                        .remark(lineStr[14])
                        .director(NumberUtil.getLong(lineStr[9]))
                        .depcharge(NumberUtil.getLong(lineStr[10]))
                        .effectdate(NumberUtil.strToDateTime(lineStr[11], "yyyy-MM-dd HH:mm:ss"))
                        .disableddate(NumberUtil.strToDateTime(lineStr[13], "yyyy-MM-dd HH:mm:ss"))
                        .lastupdatedate(NumberUtil.strToDateTime(lineStr[63], "yyyy-MM-dd HH:mm:ss"))
//                        .displaycode(lineStr[15])
                        .build();
                if ("0".equals(department.getIsdisabled())) {
                    departmentList.add(department);
                }
            });
            if (!CollectionUtils.isEmpty(departmentList)) {
                commDepartmentService.deleteCommDepartment(new CommDepartment());
                for (CommDepartment department : departmentList) {
                    commDepartmentService.addCommDepartment(department);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEmployeeJob() throws IOException {
        Date date = DateUtil.addOrSubDate(new Date(), -1);
        String dateStr = DateUtil.dateToStr(date, "yyyyMMdd");
        log.info("日期：{}", dateStr);
        String fileName = dataFileSftp + "userinfo/SBJ_V_USER_INFO_ALL_" + dateStr + ".TXT";
//        String fileName = dataFileSftp + "userinfo/SBJ_V_USER_INFO_ALL_20240305.TXT";
//        String fileName = "C:/Users/ywwb2216/Desktop/新建文件夹/SBJ_V_USER_INFO_ALL_20240409.TXT";
        log.info("文件名:{}", fileName);
        List<String> sources = FileUtil.parseTxt(fileName);
        int i = 0; //记录插入条数
        int j = 0;  //记录修改条数
        int n = 0;  //记录异常条数
        if (CollectionUtils.isEmpty(sources)) {
            return;
        }
//            AtomicInteger eid = new AtomicInteger(2);
        List<CommEmployee> employeeList = Lists.newArrayList();
        sources.forEach(context -> {
            String[] lineStr = context.split("\u0007");
            if (lineStr[5].length() == 6) {
                CommEmployee employee = CommEmployee.builder()
//                            .eid(NumberUtil.getLong(String.valueOf(eid.get())))
                        .eid(NumberUtil.getLong(lineStr[13]))
                        .badge(lineStr[5])
                        .empname(lineStr[4])
                        .fullname(lineStr[15])
                        .spellname(lineStr[16])
                        .compid(NumberUtil.getLong(String.valueOf(1)))
                        .depid(NumberUtil.getLong(lineStr[18]))
                        .jobid(NumberUtil.getLong(lineStr[19]))
                        .headship(lineStr[21])
                        .empstatus(lineStr[60])
                        .empauth(lineStr[22])
                        .authproperty(lineStr[23])
                        .gender(lineStr[24])
                        .birthday(NumberUtil.strToDateTime(lineStr[26], "yyyy-MM-dd HH:mm:ss"))
                        .officephone(lineStr[29])
                        .mobiletelphone(lineStr[2])
                        .reportto(NumberUtil.getLong(lineStr[35]))
                        .boardline(lineStr[20])
//                        .eidhr(NumberUtil.getLong(lineStr[0]))
                        .isdisabled("0")
                        .certificateType(lineStr[25])
                        .certificateNumber(lineStr[11])
                        .build();
//                    eid.getAndIncrement();
                employeeList.add(employee);
            }
        });
        if (!CollectionUtils.isEmpty(employeeList)) {
            for (CommEmployee employee : employeeList) {
                try {
                    // 优化根据进行修改
                    int count = commEmployeeService.updateCommEmployee(employee);
                    j += count;
                    if (count <= 0) {
                        //如果未更新到数据则新增
                        i += commEmployeeService.addCommEmployee(employee);
                    }
                } catch (Exception e) {
                    n++;
                    log.error("组织架构人员数据同步,落入COMM_EMPLOYEE表时发生异常,异常信息:{},出现异常的组织架构人员数据为:{})", e, employee);
                }
            }
            log.info("组织架构人员数据同步,共修改" + j + "条数据,共插入" + i + "条数据,出现的异常数据有" + n + "条数据！");
            //同步操作人员名单
            commEmployeeService.syncOperator(new CommEmployee());
            //设计默认角色
            imUserRoleRelService.syncCommRole(new ImUserRoleRel());
        }
    }

    @Override
    public void syncPostJob() throws IOException {
        Date date = DateUtil.addOrSubDate(new Date(), -1);
        String dateStr = DateUtil.dateToStr(date, "yyyyMMdd");
        log.info("日期：{}", dateStr);
        String fileName = dataFileSftp + "ojob/SBJ_V_WORK_V_OJOB_" + dateStr + ".TXT";
        log.info("文件名:{}", fileName);
        List<String> sources = FileUtil.parseTxt(fileName);
        if (!CollectionUtils.isEmpty(sources)) {
            List<CommJob> commJobs = Lists.newArrayList();
            sources.forEach(context -> {
                String[] lineStr = context.split("\u0007");
                CommJob commJob = CommJob.builder()
                        .jobid(NumberUtil.getLong(lineStr[1]))
                        .jobcode(lineStr[2])
                        .location(lineStr[4])
                        .remark(lineStr[16])
                        .depid(NumberUtil.getLong(lineStr[6]))
                        .compid(NumberUtil.getLong(lineStr[19]))
                        .isdisabled(lineStr[14])
                        .boardline(lineStr[36])
                        .effectdate(NumberUtil.strToDateTime(lineStr[13], "yyyy-MM-dd HH:mm:ss"))
                        .disableddate(NumberUtil.strToDateTime(lineStr[15], "yyyy-MM-dd HH:mm:ss"))
                        .lastupdatedate(NumberUtil.strToDateTime(lineStr[34], "yyyy-MM-dd HH:mm:ss"))
                        .build();
                commJobs.add(commJob);
            });
            if (!CollectionUtils.isEmpty(commJobs)) {
                // 删除所有员工
                commJobService.deleteCommJob(new CommJob());
                // 保存新同步的员工
                for (CommJob commJob : commJobs) {
                    commJobService.addCommJob(commJob);
                }
            }
        }
    }

    @Override
    public void syncFaceDevice() throws IOException {
        Date date = DateUtil.addOrSubDate(new Date(), -1);
        String dateStr = DateUtil.dateToStr(date, "yyyyMMdd");
        log.info("日期：{}", dateStr);
        //S8H_FACE_DEVICE_INFO_${yyyyMMdd}
        String fileName = devicedataFile + dateStr + "/S8H_FACE_DEVICE_INFO_" + dateStr + ".TXT";
        log.info("文件名:{}", fileName);
        List<String> sources = FileUtil.parseTxt(fileName);
        if (!CollectionUtils.isEmpty(sources)) {
            List<FaceDeviceInfoJob> faceDeviceInfos = Lists.newArrayList();
            sources.forEach(context -> {
                String[] lineStr = context.split(String.valueOf('\007'));
                FaceDeviceInfoJob faceDeviceInfo = FaceDeviceInfoJob.builder()
                        .id(NumberUtil.getLong(lineStr[0]))
                        .deviceport(lineStr[1])
                        .deviceip(lineStr[2])
                        .deviceaddress(lineStr[3])
                        .luserid(lineStr[4])
                        .datasource(lineStr[5])
                        .compcode(lineStr[6])
                        .isshare(lineStr[7])
                        .deleted(lineStr[8])
                        .memo(lineStr[9])
                        .memo2(lineStr[10])
                        .memo3(lineStr[11])
                        .shareobject(lineStr[12].split(",")[0])
                        .build();
                faceDeviceInfos.add(faceDeviceInfo);
            });
            if (!CollectionUtils.isEmpty(faceDeviceInfos)) {
                // 删除所有员工
                faceDeviceInfoService.deleteFaceDeviceInfo(new FaceDeviceInfoDto());
                // 保存新同步的员工
                for (FaceDeviceInfoJob faceDeviceInfo : faceDeviceInfos) {
                    FaceDeviceInfoDto infoDto = JsonUtil.jsonToClass(faceDeviceInfo, FaceDeviceInfoDto.class);
                    faceDeviceInfoService.addFaceDeviceInfo(infoDto);
                }
            }
        } else {
            log.error("考勤机信息文件不存在或文件数据为空");
        }
    }
}
