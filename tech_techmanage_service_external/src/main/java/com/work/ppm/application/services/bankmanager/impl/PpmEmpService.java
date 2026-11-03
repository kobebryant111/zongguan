package com.work.ppm.application.services.bankmanager.impl;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.services.bankmanager.IPpmEmpService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class PpmEmpService implements IPpmEmpService {

    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Override
    public String getPermissionByUserid(CommOperatorDoLoginDto user) {
        String auth = "common";
        List<ImRoleDoLoginDto> roles = user.getRoles();
        // 权限注释： common  普通行员、项目经理
        //                    president 一级部经理
        //                    managers 二级部经理
        //                    admin 系统管理员

        // 经理层
        List<CommDepartment> depList = commDepartmentService.queryDirectDeps(user.getUserid());
        if (!ObjectUtils.isEmpty(depList)) {
            auth = "managers";
        }
        // 研发管理部或一级部经理
        if ("80000122".equals(user.getDeptId()) ||
                roles.stream().anyMatch(r -> "GENERALMANAGER".equals(r.getRoleId()))) {
            auth = "president";
        }
        // 外包管理员
        if (roles.stream().anyMatch(r -> "PERMANAGE".equals(r.getRoleId()))) {
            auth = "permanage";
        }
        // 系统管理员
        if (roles.stream().anyMatch(r -> "00000001".equals(r.getRoleId()))) {
            auth = "admin";
        }

        return auth;
    }
}
