package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiItemBalanceInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemComprehensiveAbilityInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInsertInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemKpiInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemPostEvaluationInfoDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiItemService {

    /**
     * 分页
     * @param ppmKpiItemDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiItemDto ppmKpiItemDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiItemDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKpiItem(PpmKpiItemDto ppmKpiItemDto) ;

    /**
     * 查询详情
     * @param ppmKpiItemDto 对象
     * @return PpmKpiItemDto
     */
    PpmKpiItemDto queryDetail(PpmKpiItemDto ppmKpiItemDto) ;

    /**
     * 更新
     * @param ppmKpiItemDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKpiItem(PpmKpiItemDto ppmKpiItemDto) ;

    /**
     * 删除
     * @param ppmKpiItemDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiItem(PpmKpiItemDto ppmKpiItemDto) ;

    /**
     * 查询kpi指标配置详情(全部数据)
     * @param ppmKpiItemKpiInfoDto
     * @return List<PpmKpiItemKpiInfoDto>
     */
    List<PpmKpiItemKpiInfoDto> ppmKpiItemInfoListKpi(PpmKpiItemKpiInfoDto ppmKpiItemKpiInfoDto);

    /**
     * 查询kpi指标配置详情（分页数据）
     * @param ppmKpiItemKpiInfoDto
     * @param pager
     */
    void queryPagerListKpi(PpmKpiItemKpiInfoDto ppmKpiItemKpiInfoDto, Pager pager);

    /**
     * 删除数据 多个和单个删除
     * @param ids
     * @return int
     */
    int deletePpmKpiItemListKpi(String ids);

    /**
     * 添加kpi的指标库
     * @param ppmKpiItemDto
     * @return int
     */
    int addPpmKpiItemKpi(PpmKpiItemDto ppmKpiItemDto,String userid);

    /**
     * 更新kpi的指标库
     * @param ppmKpiItemDto
     * @param userid
     * @return int
     */
    int updatePpmKpiItemKpi(PpmKpiItemDto ppmKpiItemDto,String userid);

    /**
     * 导入kpi数据
     * @param file
     * @param userid
     * @return boolean
     */
    boolean importExcelKpi(MultipartFile file, String userid);

    /**
     * 删除综合能力评估配置项
     * @param ids
     * @return int
     */
    int deletePpmKpiItemListComprehensiveAbility (String ids);

    /**
     * 获得指标库数据包括主表和子表
     * @param ppmKpiItemInfoDto
     * @return PpmKpiItemInfoDto
     */
    PpmKpiItemInfoDto getKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto);

    /**
     * 同时更新主子表数据
     * @param ppmKpiItemInfoDto
     * @param userid
     * @return int
     */
    int updateKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto,String userid);

    /**
     * 新增指标库数据主子表数据同时新增
     * @param ppmKpiItemInfoDto
     * @param userid
     * @return int
     */
    int addKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto,String userid);


    /**
     * 综合能力评估项分页查询
     * @param ppmKpiItemComprehensiveAbilityInfoDto
     * @param pager
     */
    void queryPagerListComprehensiveAbility(
            PpmKpiItemComprehensiveAbilityInfoDto ppmKpiItemComprehensiveAbilityInfoDto, Pager pager);

    /**
     * 获得综合能力评估项数据
     * @param ppmKpiItemComprehensiveAbilityInfoDto
     * @return List<PpmKpiItemComprehensiveAbilityInfoDto>
     */
    List<PpmKpiItemComprehensiveAbilityInfoDto> getPpmKpiItemInfoListComprehensiveAbility(
            PpmKpiItemComprehensiveAbilityInfoDto ppmKpiItemComprehensiveAbilityInfoDto);

    /**
     * 导入综合能力配置项
     * @param file
     * @param userid
     * @return boolean
     */
    boolean importExcelComprehensiveAbility(MultipartFile file, String userid);

    /**
     * 删除履职评估多个
     * @param ids
     * @return int
     */
    int deletePpmKpiItemListPostEvaluation(String ids);

    /**
     * 履职评估项分页查询
     * @param ppmKpiItemPostEvaluationInfoDto
     * @param pager
     */
    void queryPagerListPostEvaluation(PpmKpiItemPostEvaluationInfoDto ppmKpiItemPostEvaluationInfoDto, Pager pager);

    /**
     * g履职评估项数据查询
     * @param ppmKpiItemPostEvaluationInfoDto
     * @return List<PpmKpiItemPostEvaluationInfoDto>
     */
    List<PpmKpiItemPostEvaluationInfoDto> getPpmKpiItemInfoListPostEvaluation(
            PpmKpiItemPostEvaluationInfoDto ppmKpiItemPostEvaluationInfoDto);

    /**
     * 导入履职评估
     * @param file
     * @param userid
     * @return boolean
     */
    boolean importExcelPostEvaluation(MultipartFile file, String userid);

    /**
     * 平衡计分分页查询
     * @param ppmKpiItemBalanceInfoDto
     * @param pager
     */
    void queryPagerListBalance(PpmKpiItemBalanceInfoDto ppmKpiItemBalanceInfoDto, Pager pager);

    /**
     * 平衡计分数据查询
     * @param ppmKpiItemBalanceInfoDto
     * @return List<PpmKpiItemBalanceInfoDto>
     */
    List<PpmKpiItemBalanceInfoDto> getPpmKpiItemInfoListBalance(PpmKpiItemBalanceInfoDto ppmKpiItemBalanceInfoDto);

    /**
     * 平衡计分删除多个
     * @param ids
     * @return int
     */
    int deletePpmKpiItemListBalance(String ids);

    /**
     * 平衡计分导入
     * @param file
     * @param userid
     * @return boolean
     */
    boolean importExcelBalance(MultipartFile file, String userid);

    /**
     * 查询指标库数据主表加子表用于插入
     * @param ppmKpiItemDto
     * @return List<PpmKpiItemInsertInfoDto>
     */
    List<PpmKpiItemInsertInfoDto> querykpiItemAll(PpmKpiItemDto ppmKpiItemDto);


    /**
     * 获得kpi指标的下拉框
     * @param ppmKpiItemDto
     * @return List<PpmKpiItemDto>
     */
    List<PpmKpiItemDto> getKpiItems(PpmKpiItemDto ppmKpiItemDto);
}
