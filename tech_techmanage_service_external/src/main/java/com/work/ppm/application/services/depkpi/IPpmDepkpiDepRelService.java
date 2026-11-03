package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.EosDepKpiGrade;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelInfoDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepRel;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmDepkpiDepRelService {

    /**
     * 分页
     * @param ppmDepkpiDepRelDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDepkpiDepRelDto 对象
     * @return int
     */
    int addPpmDepkpiDepRel(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) ;

    /**
     * 查询详情
     * @param ppmDepkpiDepRelDto 对象
     * @return PpmDepkpiDepRelDto
     */
    PpmDepkpiDepRelDto queryDetail(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) ;

    /**
     * 更新
     * @param ppmDepkpiDepRelDto  对象
     * @return int
     */
    int updatePpmDepkpiDepRelByDto(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) ;

    /**
     * 插入部门中心关系数据
     * @param ppmDepkpiDepRelDto 对象
     * @param userid 对象
     * @return int
     */
    int insertPpmDepKpiDepRels(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, String userid);

    /**
     * 获得部门考核关系数据
     * @param ppmDepkpiDepRelInfoDto 对象
     * @return List
     */
    List<PpmDepkpiDepRelInfoDto> getPpmDepKpiDepRelList(PpmDepkpiDepRelInfoDto ppmDepkpiDepRelInfoDto);

    /**
     * 查询部门考核关系数据分页
     * @param ppmDepkpiDepRelInfoDto 对象
     * @param pager
     */
    void queryPagerList(PpmDepkpiDepRelInfoDto ppmDepkpiDepRelInfoDto, Pager pager);

    /**
     * 批量从删除已，分割
     * @param ids 对象
     * @return int
     */
    int deletePpmDepKpiDepRelList(String ids);

    /**
     * 导入
     * @param file 对象
     * @param userid 对象
     * @return boolean
     */
    boolean importExcel(MultipartFile file, String userid);

    /**
     *  多个对象个更新
     * @param ppmDepkpiDepRelDtos 对象
     * @param userid 对象
     * @return int
     */
    int updatePpmDepKpiDepRelDtos(PpmDepkpiDepRelDto ppmDepkpiDepRelDtos, String userid);

    /**
     * 单个更新
     * @param ppmDepkpiDepRelDto 对象
     * @param userid 对象
     * @return int
     */
    int updatePpmDepKpiDepRelDto(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, String userid);

    /**
     * 获得考核层级业务字典并转化为对象
     * @return EosDepKpiGrade
     */
    EosDepKpiGrade getEosDepKpiGrade();

    /**
     * 保存层级设置
     * @param grade  对象
     * @return int
     */
    int saveEosDepKpiGrade(EosDepKpiGrade grade);
    /**
     * 部门模板分配分页查询
     * @param ppmDepKpidepModelDto 对象
     */
    void getPpmDepKpiDepRelPage(PpmDepkpiDepModelDto ppmDepKpidepModelDto, Pager pager);

    /**
     * 通过部门id查出初评人和终评人
     * @param ppm  对象
     * @return PpmDepkpiDepRelInfoDto
     */
    PpmDepkpiDepRel queryDepRelByDepIdOrCenterid(PpmDepkpiDepRelDto ppm);
}

