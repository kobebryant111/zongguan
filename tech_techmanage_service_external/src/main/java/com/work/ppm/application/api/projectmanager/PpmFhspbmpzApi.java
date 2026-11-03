package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFhspbmpzDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.projectmanager.IPpmFhspbmpzService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分行审批部门配置
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFhspbmpzApi"})
@RestController
@RequestMapping("ppmFhspbmpz")
@Slf4j
public class PpmFhspbmpzApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmFhspbmpzService ppmFhspbmpzService;

    /**
     * 查询集合
     * @param ppmFhspbmpzDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getList")
    @ApiOperation(value ="查询集合", notes = "查询集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fhyfjl", value = "分行研发经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fhyfjlName", value = "分行研发经理名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hgbzjl", value = "合规部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hgbzjlname", value = "合规部总经理名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzybzjl", value = "人力资源部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzybzjlname", value = "人力资源部总经理名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除   0:否    1:是", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createusername", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),

    })
    public ApiResult<List<PpmFhspbmpzDto>> queryPager(PpmFhspbmpzDto ppmFhspbmpzDto) {
        ApiResult<List<PpmFhspbmpzDto>> result =new ApiResult<>();
        List<PpmFhspbmpzDto> list = ppmFhspbmpzService.queryList(ppmFhspbmpzDto);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新|| 添加
     * @param ppmFhspbmpzDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fhyfjl", value = "分行研发经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fhyfjlName", value = "分行研发经理名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hgbzjl", value = "合规部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hgbzjlname", value = "合规部总经理名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzybzjl", value = "人力资源部总经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzybzjlname", value = "人力资源部总经理名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除   0:否    1:是", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createusername", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto, HttpServletRequest request) {
        AssertUtil.notNull(ppmFhspbmpzDto.getDeptno(), "[部门名称] 不能为空");
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        ppmFhspbmpzDto.setCreateuserid(user.getUserid());
        ppmFhspbmpzDto.setCreateusername(user.getOperatorname());
        int id = ppmFhspbmpzService.updatePpmFhspbmpz(ppmFhspbmpzDto);
        if(id == 1){
            result.success("添加成功");
        } else if(id == 2) {
            result.success("更新成功");
        } else {
            result.failure("添加失败，【" + ppmFhspbmpzDto.getDeptname() + "】不能重复");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmFhspbmpzDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete/{id}")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmFhspbmpz (PpmFhspbmpzDto ppmFhspbmpzDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmFhspbmpzService.deletePpmFhspbmpz(ppmFhspbmpzDto);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
