package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOnboardDistributionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 入职部门分配
  */
public interface IPpmOnboardDistributionService {

    /**
     * 分页
     * @param ppmOnboardDistributionDto 对象
     * @param pager 分页对象
     * @param user session对象
     */
    void queryPager(PpmOnboardDistributionDto ppmOnboardDistributionDto, Pager pager,CommOperatorDoLoginDto user) ;

    /**
     * 添加
     * @param ppmOnboardDistributionDto 对象
     * @return int
     */
     @Deprecated
    int addPpmOnboardDistribution(PpmOnboardDistributionDto ppmOnboardDistributionDto) ;

    /**
     * 查询详情
     * @param ppmOnboardDistributionDto 对象
     * @return PpmOnboardDistributionDto
     */
    PpmOnboardDistributionDto queryDetail(PpmOnboardDistributionDto ppmOnboardDistributionDto) ;

    /**
     * 查询详情
     * @param resumeId 简历id
     * @return PpmOnboardDistributionDto
     */
    PpmOnboardDistributionDto queryDetail(String resumeId) ;

    /**
     * 更新
     * @param ppmOnboardDistributionDto  对象
     * @return int
     */
    int updatePpmOnboardDistribution(PpmOnboardDistributionDto ppmOnboardDistributionDto) ;

    /**
     * Excel导入
     * @param batchNo 批次号
     * @param file 上传的excel
     * @return true:成功，false：失败
     */
    boolean importExcel(String batchNo, MultipartFile file);

    /**
     * 根据简历id更新入职部门分配主表id
     * @param id 入职部门分配主表id
     * @param resumeIds 简历id
     * @return  int
     */
    int updateInfoForId(String resumeIds, int id);

    /**
     * 根据条件查询集合
     * @param ppmOnboardDistributionDto 对象
     * @return list
     */
    List<PpmOnboardDistributionDto> queryList(PpmOnboardDistributionDto ppmOnboardDistributionDto);

    /**
     * 获取入职部门分配权限
     * @param user 对象
     * @return 1:管理员，2：普通管理员
     */
    int getAuth(CommOperatorDoLoginDto user);

    /**
     * 删除
     * @param resumeIds 简历id 多个逗号分开
     * @return  大于0 成功 反之失败
     */
    int delete(String resumeIds);

    /**
     * 无权限分页
     * @param ppmOnboardDistributionDto 对象
     *
     * @param pager 对象
     */
    void queryPagerNotAuth(PpmOnboardDistributionDto ppmOnboardDistributionDto, Pager pager);
}
