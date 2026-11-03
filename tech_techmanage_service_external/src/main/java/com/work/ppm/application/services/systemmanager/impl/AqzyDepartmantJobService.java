package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.quartz.interfaces.IAqzyDepartmantJobService;
import com.work.ppm.application.services.systemmanager.IAqzyDepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service("AqzyDepartmantJobService")
public class AqzyDepartmantJobService implements IAqzyDepartmantJobService {


    @Autowired
    private IAqzyDepartService aqzyDepartService;
    @Override
    public  void AqzyDepartmantJob() throws IOException {
        /*
          总部部门同步
         */
        aqzyDepartService.headSafetyspecialist();
        /*
          业务部部门同步
         */
        aqzyDepartService.businessSafetyspecialist();
    }



}
