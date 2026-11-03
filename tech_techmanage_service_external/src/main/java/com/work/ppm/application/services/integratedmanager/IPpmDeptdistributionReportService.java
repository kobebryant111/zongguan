package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmDeptdistributionReportDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 入职部门分配报表
 */
public interface IPpmDeptdistributionReportService {

    /**
     * 分页
     * @param ppmDeptdistributionReportDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDeptdistributionReportDto ppmDeptdistributionReportDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDeptdistributionReportDto 对象
     * @return int
     */
     @Deprecated
    int addPpmDeptdistributionReport(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) ;

    /**
     * 查询详情
     * @param ppmDeptdistributionReportDto 对象
     * @return PpmDeptdistributionReportDto
     */
    @Deprecated
    PpmDeptdistributionReportDto queryDetail(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) ;

    /**
     * 更新
     * @param ppmDeptdistributionReportDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmDeptdistributionReport(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) ;

    /**
     * 删除
     * @param ppmDeptdistributionReportDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmDeptdistributionReport(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) ;

    /**
     * 导入
     * @param file 文件
     * @param batchNo 批次号
     * @return true 成功，false 失败
     */
    boolean importExcel(String batchNo, MultipartFile file);
}
