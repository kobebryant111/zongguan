package com.work.ppm.application.api.#{model};

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.#{model}.#{classNameUpperDto};
import com.work.ppm.application.services.#{model}.#{interfaceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import com.work.ppm.commons.utils.ExcelUtil;

/**
 *  #{tableComment} API接口
 * @author 作者
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"#{apiClassName}"})
@RestController
@RequestMapping("#{classNameLower}")
@Slf4j
public class #{apiClassName} {
    /**
     * 类接口描述
     */
    @Autowired
    private #{interfaceName} #{serviceLower};

    /**
     * 分页
     * @param #{classNameLowerDto} 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(#{classNameUpperDto} #{classNameLowerDto},Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        #{serviceLower}.queryPager(#{classNameLowerDto},pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param #{classNameLowerDto} 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<#{classNameUpperDto}> queryDetail(#{classNameUpperDto} #{classNameLowerDto}) {
        ApiResult<#{classNameUpperDto}> result =new ApiResult<>();
        #{classNameUpperDto} item = #{serviceLower}.queryDetail(#{classNameLowerDto});
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     * @param #{classNameLowerDto} 对象
     * @return ApiResult
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    public ApiResult<Object> add#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) {
        ApiResult<Object> result =new ApiResult<>();
        int id = #{serviceLower}.add#{classNameUpper}(#{classNameLowerDto});
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 更新
     * @param #{classNameLowerDto} 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> update#{classNameUpper}(#{classNameUpperDto} #{classNameLowerDto}) {
        ApiResult<Object> result =new ApiResult<>();
        int id = #{serviceLower}.update#{classNameUpper}(#{classNameLowerDto});
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param #{classNameLowerDto} 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult delete#{classNameUpper} (#{classNameUpperDto} #{classNameLowerDto}) {
        ApiResult result =new ApiResult<>();
        int id = #{serviceLower}.delete#{classNameUpper}(#{classNameLowerDto});
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出
     * @param #{classNameLowerDto} 对象
     */
    @PostMapping(value = "export")
    @ApiOperation(value = "导出", notes = "导出")
    public void export#{classNameUpper} (#{classNameUpperDto} #{classNameLowerDto},HttpServletResponse response) {
        List<#{classNameUpperDto}> list = #{serviceLower}.export#{classNameUpper}(#{classNameLowerDto});
        ExcelUtil.exportExcel(list, #{classNameUpperDto}.class,"xxx.xls", response);
    }
}