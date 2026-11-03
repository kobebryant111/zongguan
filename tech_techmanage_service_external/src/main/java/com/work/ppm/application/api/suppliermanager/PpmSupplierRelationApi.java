package com.work.ppm.application.api.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSupplierRelationDto;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRelationService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.utils.BusinessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  供应商考核信息表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSupplierRelationApi"})
@RestController
@RequestMapping("supplierRelation")
@Slf4j
public class PpmSupplierRelationApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSupplierRelationService ppmSupplierRelationService;

    /**
     * 查询考核信息
     * @param ppmSupplierRelationDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "relationList")
    @ApiOperation(value = "查询考核信息", notes = "查询考核信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query")
    })
    public ApiResult<List<PpmSupplierRelationDto>> relationList(PpmSupplierRelationDto ppmSupplierRelationDto) {
        ApiResult<List<PpmSupplierRelationDto>> result =new ApiResult<>();
        List<PpmSupplierRelationDto> items =
                ppmSupplierRelationService.queryTypeList(ppmSupplierRelationDto.getType(),ppmSupplierRelationDto);

        items.forEach(item ->{
            Map<String,Object> map = BusinessUtil.stringRegexMap(
                    item.getArrays(),new String[]{BusinessUtil.REGEX_LINE_UN,BusinessUtil.REGEX_COLON});
            List<Map<String,Object>> arrayMap = new ArrayList<>();
            map.keySet().forEach(key ->{
                Map<String,Object> newMap = new HashMap<>();
                newMap.put("label",map.get(key));
                newMap.put("key",key);
                arrayMap.add(newMap);
            });
            item.setArrayMap(arrayMap);
        });

        result.setStatus(ApiCode.SUCCESS);
        result.setResults(items);
        return result;
    }

}
