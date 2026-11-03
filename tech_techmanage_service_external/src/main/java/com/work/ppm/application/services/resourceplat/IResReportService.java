package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResExpandItemParts;
import com.work.ppm.application.model.resourceplat.ResReportInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产统计报表相关接口
 * @date 2021/09/03
 */
public interface IResReportService {
    /**
     * 根据查询条件查询出需要导出的资产统计数据
     * @param resAddedDto 查询dto
     * @return {@link List<ResExpandItemParts>}
     */
    List<ResAdded> queryResourceReportList(ResAddedDto resAddedDto);

    /**
     * 根据查询出的list进一步查询出统计信息
     * @param list 查询出的id列表
     * @return {@link List<ResReportInfo>}
     */
    List<ResReportInfo> queryStatisticReportInfo(List<String> list);

    /**
     * 查询关联系统树
     *
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryRepDeptTree();

    /**
     * 导出资产统计报表
     *
     * @param resAddedDto 查询resAddedDto
     */
    void exportResReport(ResAddedDto resAddedDto, HttpServletResponse response);

    /**
     * 查询当前登陆人的部门
     * @param userId
     * @return 1
     */
    String getDeptIdLogin (String userId);

}
