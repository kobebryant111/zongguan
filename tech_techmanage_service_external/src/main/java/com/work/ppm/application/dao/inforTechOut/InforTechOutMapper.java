package com.work.ppm.application.dao.inforTechOut;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.work.ppm.application.dto.inforTechOut.PpmOutsourcingXklx;
import com.work.ppm.application.model.flowmanager.ext.PpmFlowLogInfo;
import com.work.ppm.application.model.inforTechOut.InforOutsourcFile;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InforTechOutMapper extends BaseMapper<InforOutsourcFile> {

    /**
     * 查询关联项目
     * @param req
     * @return List<RdProjectContractRelationDto>
     */
    List<CommEmployee> queryList(String req);

    void saveInforTechApply(PpmOutsourcingXklx ppmOutsourcingXklx);

    void addPpmOutsourcingXklx(PpmOutsourcingXklx infoDto);

    PpmOutsourcingXklx getDb(PpmOutsourcingXklx infoDto);

    void updatePpmOutsourcingXklx(PpmOutsourcingXklx ppmOutsourcingXklx);


    List<PpmFlowLogInfo> getLogs(Map<String, Object> map);

    void updateAllPpmOutsourcingXklx(PpmOutsourcingXklx infoDto);

    List<PpmFlowLogInfo> getHistoryLogs(Map<String, Object> map);

    List<CommDepartment> getMechanism(@Param("deptId") String deptId);

    List<CommDepartment> selectDirector(@Param("userid") String userid);

    PpmOutsourcingXklx getPhoneDb(PpmOutsourcingXklx info);

    int deleteByInstanceid(PpmOutsourcingXklx accessapply);

}



