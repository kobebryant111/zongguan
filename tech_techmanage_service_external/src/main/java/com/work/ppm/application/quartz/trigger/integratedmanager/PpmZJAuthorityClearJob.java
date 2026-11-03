package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityService;
import com.work.ppm.application.services.integratedmanager.impl.PpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.impl.PpmZjAuthorityService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.impl.CommEmployeeService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 定时清洗离职人员闸机权限和到期权限任务
 *
 */
@Slf4j
public class PpmZJAuthorityClearJob implements IQuartzExecutorJob {

    /**
     * 闸机权限服务
     */
    private IPpmZjAuthorityService ppmZjAuthorityService = SpringUtil.getBean(PpmZjAuthorityService.class);
    /**
     * 行员服务
     */
    private ICommEmployeeService commEmployeeService = SpringUtil.getBean(CommEmployeeService.class);
    /**
     * 外包服务
     */
    private IPpmOutsourcingBasicService outsourcingBasicService = SpringUtil.getBean(PpmOutsourcingBasicService.class);

    @Override
    public void executeInternal() {
        try {
            log.info("清洗离职人员闸机权限和到期权限定时器开始");
            // 先清理过期的权限数据
            ppmZjAuthorityService.cleanExpireData();

            // 处理离场的行员和外包人员
            List<String> certificateNumbers = Lists.newArrayList();
            // 取出离场外包人员
            PpmOutsourcingBasicDto ppmOutsourcingBasicDto = PpmOutsourcingBasicDto.builder().state("1").build();
            List<PpmOutsourcingBasicDto> expireOutsourcings = outsourcingBasicService.queryOutList(ppmOutsourcingBasicDto);
            if (!CollectionUtils.isEmpty(expireOutsourcings)) {
                for (PpmOutsourcingBasicDto expireOutsourcing : expireOutsourcings) {
                    certificateNumbers.add(expireOutsourcing.getCertificateNumber());
                }
            }
            // 取出离场行员
            CommEmployeeDto commEmployeeDto = CommEmployeeDto.builder().empstatus("Loff").build();
            List<CommEmployeeDto> expreEmployees = commEmployeeService.getEmployeeLists(commEmployeeDto);
            if (!CollectionUtils.isEmpty(expreEmployees)) {
                for (CommEmployeeDto employeeDto : expreEmployees) {
                    certificateNumbers.add(employeeDto.getCertificateNumber());
                }
            }
            //清理权限过期数据
            ppmZjAuthorityService.cleanExpireData(certificateNumbers);
            log.info("清洗离职人员闸机权限和到期权限定时器结束");
        } catch (Exception e) {
            log.error("清洗离职人员闸机权限和到期权限定时器异常", e);
        }

    }

}
