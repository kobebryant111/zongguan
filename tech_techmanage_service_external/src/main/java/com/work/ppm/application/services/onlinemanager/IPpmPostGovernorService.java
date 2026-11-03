package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.application.dto.onlinemanager.PpmPostGovernorDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.onlinemanager.ext.PpmPostGovernorInfo;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmPostGovernorService {

    /**
     * 分页
     * @param ppmPostGovernorDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmPostGovernorDto ppmPostGovernorDto, Pager pager) ;

    /**
     * 添加
     * @param ppmPostGovernorDto 对象
     * @return int
     */
    int addPpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto, CommOperatorDoLoginDto user) ;

    /**
     * 查询详情
     * @param ppmPostGovernorDto 对象
     * @return PpmPostGovernorDto
     */
    PpmPostGovernorInfo queryDetail(PpmPostGovernorDto ppmPostGovernorDto) ;

    /**
     * 更新
     * @param ppmPostGovernorDto  对象
     * @return int
     */
    int updatePpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto) ;

    /**
     * 删除
     * @param ppmPostGovernorDto 对象
     * @return int
     */
    int deletePpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto) ;

    /**
     * 导入excel
     * @param file
     * @return boolean
     */
    boolean importExcelInfo(MultipartFile file, CommOperatorDoLoginDto user);
    /**
     * 批量删除
     * @param ppmPostGovernorDtos
     * @return int
     */
    int batchDeletePpmPostGovernor(List<PpmPostGovernorDto> ppmPostGovernorDtos);
    /**
     * 点击触发反馈流程
     * @param ppmPostGovernorDtos,user
     * @return ApiResult
     */
    ApiResult flowStartAndRun(List<PpmPostGovernorDto> ppmPostGovernorDtos, CommOperatorDoLoginDto user);
    /**
     * 关联人员审批接口
     * @param ppmPostGovernorDto
     * @return ApiResult
     */
    ApiResult relationUserRunFlowInfo(PpmPostGovernorDto ppmPostGovernorDto);
    /**
     * 二级部经理审批接口
     * @param ppmPostGovernorDto
     * @return  ApiResult
     */
    ApiResult managerRunFlowInfo(PpmPostGovernorDto ppmPostGovernorDto);
    /**
     * 后督管理人员审核审批接口
     * @param ppmPostGovernorDto
     * @return  ApiResult
     */
    ApiResult endRunFlowInfo(PpmPostGovernorDto ppmPostGovernorDto);

    /**
     * 查询不分页内容
     * @param ppmPostGovernorDto
     * @return  List<PpmPostGovernorInfo>
     */
    List<PpmPostGovernorInfo> selectList(PpmPostGovernorDto ppmPostGovernorDto);

    /**
     * 录入excel导出查询列表内容
     * @param ppmPostGovernorDto
     * @return  List<PpmPostGovernorDto>
     */
    List<PpmPostGovernorDto> exportPostGovernorInputDatas(PpmPostGovernorDto ppmPostGovernorDto);
}
