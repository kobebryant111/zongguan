package com.work.ppm.application.services.rdmanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.dto.rdmanager.RdProjectDetailsDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnerDto;
import com.work.ppm.application.dto.rdmanager.RdProjectInnovaDto;
import com.work.ppm.application.dto.rdmanager.RdProjectNewDto;
import com.work.ppm.application.dto.rdmanager.RdProjectQueryDto;
import com.work.ppm.application.model.rdmanager.CommRdEmployeeDto;
import com.work.ppm.application.model.rdmanager.RdProject;
import com.work.ppm.application.model.rdmanager.RdProjectExcel;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

public interface IRdProjectService  extends IService<RdProject> {

    /**
     * 分页查询
     * @param req
     * @param pager
     */
    void queryPager(RdProjectQueryDto req, Pager pager);

    /**
     * 导出
     * @param req
     * @param pager
     * @return
     */
    List<RdProjectExcel> queryPagerExcel(RdProjectQueryDto req, Pager pager);
    /**
     * 查询项目详情
     * @param pkId
     * @return
     */
    RdProjectDetailsDto getRdProjectDetails(String pkId);

    /**
     * 删除项目
     * @param pkId
     */
    void delete(String pkId);
    /**
     * 项目内部资源查询
     */
    List<CommRdEmployeeDto> queryByRdProjectCode(String projectCode);
    /**
     * 修改内部信息
     * @param req
     */
    void updateInnerInfo(RdProjectInnerDto req);

    /**
     * 保存项目基本信息
     * @param info
     */
    RdProject saveBaseInfo(RdProjectNewDto info);

    /**
     * 修改项目技术信息
     * @param req
     */
    void updateInnova(RdProjectInnovaDto req);
}
