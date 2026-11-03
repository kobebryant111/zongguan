package com.work.ppm.application.api.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessGradeDto;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierAssessGradeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  供应商考核评分表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSupplierAssessGradeApi"})
@RestController
@RequestMapping("supplierAssessGrade")
@Slf4j
public class PpmSupplierAssessGradeApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSupplierAssessGradeService ppmSupplierAssessGradeService;

    /**
     * 查询详情
     * @param assessGradeDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryDetailQt")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmSupplierAssessGradeDto> queryDetailQt(
            @RequestBody PpmSupplierAssessGradeDto assessGradeDto) {

        //以项目编号为控制条件 如不满足条件则直接查E，仅项目编号为String时可以
        if(StringUtils.isEmpty(assessGradeDto.getAssessYear()) ||
                StringUtils.isEmpty(assessGradeDto.getSupplierCode()) ||
                StringUtils.isEmpty(assessGradeDto.getDeptId()) ||
                StringUtils.isEmpty(assessGradeDto.getOutsourceType())){
            assessGradeDto.setProjectId("E");
        }

        if("0".equals(assessGradeDto.getOutsourceType()) &&
                StringUtils.isEmpty(assessGradeDto.getProjectId())){
            assessGradeDto.setProjectId("E");
        }

        ApiResult<PpmSupplierAssessGradeDto> result = new ApiResult<>();

        PpmSupplierAssessGradeDto item =
                ppmSupplierAssessGradeService.queryDetail(assessGradeDto,"queryDetailQt");
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


}
