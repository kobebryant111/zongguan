package com.work.ppm.application.dao.rdManager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.work.ppm.application.dto.rdmanager.RdProjectQueryDto;
import com.work.ppm.application.model.rdmanager.RdProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 研发项目管理
 *
 */
@Mapper
public interface RdProjectMapper   extends BaseMapper<RdProject> {

    /**
     * 分页查询
     * @param req
     * @param page
     * @return List<RdProject>
     */
    List<RdProject> queryPager(@Param("req") RdProjectQueryDto req, Page page);
}
