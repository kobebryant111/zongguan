package com.work.ppm.application.api.othermanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.dto.othermanager.PpmQuestionListDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.othermanager.PpmQuestionList;
import com.work.ppm.application.services.othermanager.IPpmQuestionListService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  题库主表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmQuestionListApi"}, description = "题库主表")
@RestController
@RequestMapping("ppmQuestionList")
@Slf4j
public class PpmQuestionListApi extends BaseApi {
    /**
     * 题库主表接口
     */
    @Autowired
    private IPpmQuestionListService ppmQuestionListService;

    /**
     * 分页
     * @param ppmQuestionList 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "questionno", value = "试题编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "提干", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "questiontype", value = "试题类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除，0为未删除，1为删除，默认为0", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "最后操作者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "answer", value = "答案", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "answerexplain", value = "答案解析", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "annotation", value = "注释", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmQuestionList ppmQuestionList,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmQuestionListService.queryPager(ppmQuestionList,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmQuestionList 对象
     * @return ApiResult<PpmQuestionListDto>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "questionno", value = "试题编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "提干", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "questiontype", value = "试题类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除，0为未删除，1为删除，默认为0", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "最后操作者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "answer", value = "答案", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "answerexplain", value = "答案解析", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "annotation", value = "注释", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmQuestionListDto> queryDetail(PpmQuestionList ppmQuestionList) {
        ApiResult<PpmQuestionListDto> result =new ApiResult<>();
        PpmQuestionListDto item = ppmQuestionListService.queryDetail(ppmQuestionList);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmQuestionListDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "update", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "questionno", value = "试题编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "提干", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "questiontype", value = "试题类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除，0为未删除，1为删除，默认为0", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "最后操作者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "answer", value = "答案", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "answerexplain", value = "答案解析", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "annotation", value = "注释", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmQuestionList(@RequestBody PpmQuestionListDto ppmQuestionListDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto login = this.getCurrentUser(request);
        ppmQuestionListDto.setCreator(login.getOperatorname() + "(" + login.getUserid() + ")");
        int id = ppmQuestionListService.updatePpmQuestionList(ppmQuestionListDto);
        if(id > 0){
            result.success("操作成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("操作失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmQuestionList 对象
     * @return ApiResult
     * @throws Exception
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmQuestionList (PpmQuestionList ppmQuestionList) {
        ApiResult result =new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(ppmQuestionList.getQuestionno()) || null == ppmQuestionList.getId()) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("缺少必要参数");
            return result;
        }
        int id = ppmQuestionListService.deletePpmQuestionList(ppmQuestionList);
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
     * @param ppmQuestionList 对象
     * @param response response
     */
    @PostMapping("export")
    public void exportExcel(PpmQuestionList ppmQuestionList, HttpServletResponse response) {
        ppmQuestionListService.exportExcel(ppmQuestionList, response);
    }

    /**
     * 导入
     * @param file 文件
     * @param response response
     * @return ApiResult
     */
    @PostMapping("import")
    public ApiResult importExcel(MultipartFile file, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        ppmQuestionListService.importExcel(file,response);
        result.success("导入成功");
        return result;
    }

    /**
     * 批量删除
     * @param list 集合对象
     * @return ApiResult
     */
    @PostMapping("deletes")
    public ApiResult doDeletes(@RequestBody  List<PpmQuestionList> list) {
        ApiResult result = new ApiResult();
        ppmQuestionListService.batchDeletes(list);
        result.success("删除成功");
        return result;
    }
}
