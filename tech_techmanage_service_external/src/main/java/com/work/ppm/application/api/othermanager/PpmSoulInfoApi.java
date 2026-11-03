package com.work.ppm.application.api.othermanager;

import com.work.ppm.application.model.othermanager.PpmSoulInfo;
import com.work.ppm.application.services.othermanager.IPpmSoulInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 鸡汤库管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSoulInfoApi"}, description = "鸡汤库管理")
@RestController
@RequestMapping("ppmSoulInfo")
@Slf4j
public class PpmSoulInfoApi {
    /**
     * 鸡汤库管理接口
     */
    @Autowired
    private IPpmSoulInfoService ppmSoulInfoService;

    /**
     * 分页
     * @param ppmSoulInfo 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "roles", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "鸡汤", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "加班开始时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "加班结束时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "style", value = "分类", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmSoulInfo ppmSoulInfo,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmSoulInfoService.queryPager(ppmSoulInfo,pager);
        result.success("获取成功");
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmSoulInfo 对象
     * @return ApiResult<PpmSoulInfo>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "roles", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "鸡汤", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "加班开始时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "加班结束时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "style", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmSoulInfo> queryDetail(PpmSoulInfo ppmSoulInfo) {

        ApiResult<PpmSoulInfo> result =new ApiResult<>();
        if(!StringUtil.isNotEmpty(ppmSoulInfo.getId())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            return result;
        }
        PpmSoulInfo item = ppmSoulInfoService.queryDetail(ppmSoulInfo);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新或者添加鸡汤库
     * @param ppmSoulInfo 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "update", notes = "更新或者添加鸡汤库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "roles", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "鸡汤", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "加班开始时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "加班结束时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "style", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmSoulInfo(PpmSoulInfo ppmSoulInfo) {

        ApiResult<Object> result =new ApiResult<>();
        int id = ppmSoulInfoService.updatePpmSoulInfo(ppmSoulInfo);
        // 添加
        if(StringUtil.isNotEmpty(ppmSoulInfo.getId())) {
            if(id > 0){
                result.success("添加成功");
            } else {
                result.setStatus(ApiCode.UPATE_FAIL);
                result.setMessage("添加失败");
            }
            return result;
        }
        // 更新
        if(id > 0){
            result.success("更新成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmSoulInfo 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "int", paramType = "query"),

    })
    public ApiResult deletePpmSoulInfo (PpmSoulInfo ppmSoulInfo) {
        ApiResult result =new ApiResult<>();
        int id = ppmSoulInfoService.deletePpmSoulInfo(ppmSoulInfo);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出
     * @param ppmSoulInfo 对象
     * @param response  response
     */
    @PostMapping(value = "export")
    public void doExport(HttpServletResponse response, PpmSoulInfo ppmSoulInfo) {
        ApiResult result = new ApiResult();
        List<PpmSoulInfo> list =  ppmSoulInfoService.getPpmSoulInfo(ppmSoulInfo);
        ExcelUtil.exportExcel(list,PpmSoulInfo.class,"jtk.xls",response);
        result.success("数据导出成功");
//        return result;
    }

    /**
     * 导入
     * @param file 文件
     * @param response response
     * @return ApiResult
     */
    @PostMapping("import")
    public ApiResult doImport(MultipartFile file, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        ppmSoulInfoService.doImport(file);
        result.success("导入成功");
        return result;
    }

    /**
     * 批量删除
     * @param list 集合
     * @return ApiResult
     */
    @PostMapping("deletes")
    public ApiResult doDeletes(@RequestBody List<PpmSoulInfo> list) {
        ApiResult result = new ApiResult();
        int rowId = ppmSoulInfoService.batchDelete(list);
        if(rowId > 0) {
            result.success("删除成功");
        } else if(rowId == -1) {
            result.failure("请至少选择一条记录");
        } else {
            result.failure("删除失败");
        }
        return result;
    }
}
