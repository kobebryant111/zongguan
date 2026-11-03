package com.work.ppm.application.api.projectmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.projectmanager.PpmProjectDto;
import com.work.ppm.application.services.projectmanager.IPpmProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProjectApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmProject")
@Slf4j
public class PpmProjectApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmProjectService ppmProjectService;

    /**
     * 分页
     * @param ppmProjectDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "??PPM_PROJECT_ID ??EOS?????????",
					dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subprojectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subtype", value = "01", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subprojectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectscale", value = "GLOBAL_PROJECT_SCALE",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busideptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "manauserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busiuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planTWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualTWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "planOWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualOWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "planBWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualBWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "builddate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdelay", value = "GLOBAL_YESNO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "expectdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "actualdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "closedate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "1-????0-???", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isproduction", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coupledsys", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gnd", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "healthscore", value = "健康率", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "fjhid", value = "最新用户故事ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issueworkload", value = "用户故事ID,故事名称,开发工作量,测试工作量",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "testdeptno", value = "测试部门", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmProjectDto ppmProjectDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmProjectService.queryPager(ppmProjectDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmProjectDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "??PPM_PROJECT_ID ??EOS?????????",
				   dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subprojectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subtype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subprojectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectscale", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busideptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "manauserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busiuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planTWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualTWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "planOWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualOWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "planBWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualBWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "builddate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdelay", value = "GLOBAL_YESNO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "expectdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "actualdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "closedate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "1-????0-???", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isproduction", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coupledsys", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gnd", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "healthscore", value = "健康率", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "fjhid", value = "最新用户故事ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issueworkload", value = "用户故事ID,故事名称,开发工作量,测试工作量",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "testdeptno", value = "测试部门", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmProjectDto> queryDetail(PpmProjectDto ppmProjectDto) {
        ApiResult<PpmProjectDto> result =new ApiResult<>();
        PpmProjectDto item = ppmProjectService.queryDetail(ppmProjectDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmProjectDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "??PPM_PROJECT_ID ??EOS?????????",
					dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subprojectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subtype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subprojectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectscale", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busideptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "manauserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busiuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planTWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualTWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "planOWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualOWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "planBWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "actualBWorkload", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "builddate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdelay", value = "GLOBAL_YESNO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "expectdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "actualdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "closedate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "1-????0-???", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isproduction", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coupledsys", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gnd", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "healthscore", value = "健康率", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "fjhid", value = "最新用户故事ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issueworkload", value = "用户故事ID,故事名称,开发工作量,测试工作量",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "testdeptno", value = "测试部门", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmProject(PpmProjectDto ppmProjectDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmProjectService.updatePpmProject(ppmProjectDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

	/**
	 * 根据project，planonline, onlineno获取项目列表信息
	 * @param ppmProjectDto 对象
	 * @return ApiResult
	 */
	@GetMapping(value = "queryPpmProjectList")
	@ApiOperation(value = "根据project，planonline, onlineno获取项目列表信息",
			notes = "根据project，planonline, onlineno获取项目列表信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectno", value = "项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "上线日期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query"),

	})
	public ApiResult<List<PpmProjectDto>> queryPpmProjectList(PpmProjectDto ppmProjectDto) {
		ApiResult<List<PpmProjectDto>> result =new ApiResult<>();
		List<PpmProjectDto> projectList = ppmProjectService.queryPpmProjectList(ppmProjectDto);
		if (projectList != null) {
			result.setResults(projectList);
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.FAIL);
			result.setMessage("获取项目信息列表失败");
		}

		return result;
	}
}
