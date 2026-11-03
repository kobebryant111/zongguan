package com.work.ppm.application.services.rdmanager.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.dao.rdManager.RdProjectMapper;
import com.work.ppm.application.dao.rdManager.RdProjectUserMapper;
import com.work.ppm.application.dto.rdmanager.RdProjectDetailsDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnerDto;
import com.work.ppm.application.dto.rdmanager.RdProjectNewDto;
import com.work.ppm.application.dto.rdmanager.RdProjectQueryDto;
import com.work.ppm.application.model.rdmanager.RdProject;
import com.work.ppm.application.model.rdmanager.RdProjectUser;
import com.work.ppm.application.services.rdmanager.IRdProjectService;
import com.work.ppm.application.services.rdmanager.IRdProjectUserService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 研发项目管理
 *
 */
@Service
public class RdProjectUserService extends ServiceImpl<RdProjectUserMapper, RdProjectUser>
        implements IRdProjectUserService {



}
