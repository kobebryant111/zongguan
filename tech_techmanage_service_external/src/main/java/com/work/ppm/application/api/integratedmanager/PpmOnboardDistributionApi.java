package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOnboardDistributionDto;
import com.work.ppm.application.services.integratedmanager.IPpmOnboardDistributionService;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入职部门分配
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOnboardDistributionApi"},description="入职部门分配")
@RestController
@RequestMapping("ppmOnboardDistribution")
@Slf4j
public class PpmOnboardDistributionApi extends BaseUploadApi {
    /**
     * 入职部门分配
     */
    @Autowired
    private IPpmOnboardDistributionService ppmOnboardDistributionService;

    /**
     * 分页
     * @param ppmOnboardDistributionDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "sequence", value = "排序", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "center", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "direction", value = "方向", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "grade", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resumeId", value = "简历ID（唯一）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "totalScore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalScore", value = "技术面试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalScoring", value = "技术面试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalComments", value = "技术面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "administrativeScore", value = "行政面试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "administrativeScoring", value = "行政面试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "administrativeComments", value = "行政面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "trainingScore", value = "培训成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "trainingScoring", value = "培训记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "writtenScore", value = "笔试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "writtenScoring", value = "笔试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "otherScore", value = "其他成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "otherScoring", value = "其他记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bornDate", value = "出身日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "natives", value = "籍贯", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highest", value = "全日制最高学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coursesCollege", value = "全日制本科学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coursesProfessional", value = "全日制本科专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mastersCollege", value = "全日制硕士学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "degreeProfessional", value = "全日制硕士专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "batch", value = "批次", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:正常，1：删除)", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOnboardDistributionDto ppmOnboardDistributionDto,Pager pager, HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmOnboardDistributionService.queryPager(ppmOnboardDistributionDto,pager, user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

	/**
	 * 分页
	 * @param ppmOnboardDistributionDto 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "queryPagerNotAuth")
	@ApiOperation(value ="分页查询", notes = "分页查询")
	@ApiImplicitParams({

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
	})
	public ApiResult queryPagerNotAuth(PpmOnboardDistributionDto ppmOnboardDistributionDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmOnboardDistributionService.queryPagerNotAuth(ppmOnboardDistributionDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}



	/**
     * 查询详情
     * @param ppmOnboardDistributionDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "sequence", value = "排序", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "center", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "direction", value = "方向", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "grade", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resumeId", value = "简历ID（唯一）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "totalScore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalScore", value = "技术面试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalScoring", value = "技术面试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalComments", value = "技术面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "administrativeScore", value = "行政面试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "administrativeScoring", value = "行政面试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "administrativeComments", value = "行政面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "trainingScore", value = "培训成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "trainingScoring", value = "培训记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "writtenScore", value = "笔试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "writtenScoring", value = "笔试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "otherScore", value = "其他成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "otherScoring", value = "其他记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bornDate", value = "出身日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "natives", value = "籍贯", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highest", value = "全日制最高学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coursesCollege", value = "全日制本科学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coursesProfessional", value = "全日制本科专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mastersCollege", value = "全日制硕士学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "degreeProfessional", value = "全日制硕士专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "batch", value = "批次", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:正常，1：删除)", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<PpmOnboardDistributionDto> queryDetail(PpmOnboardDistributionDto ppmOnboardDistributionDto) {
        ApiResult<PpmOnboardDistributionDto> result =new ApiResult<>();
        PpmOnboardDistributionDto item = ppmOnboardDistributionService.queryDetail(ppmOnboardDistributionDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOnboardDistributionDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "sequence", value = "排序", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "center", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "direction", value = "方向", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "grade", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resumeId", value = "简历ID（唯一）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "totalScore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalScore", value = "技术面试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalScoring", value = "技术面试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "technicalComments", value = "技术面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "administrativeScore", value = "行政面试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "administrativeScoring", value = "行政面试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "administrativeComments", value = "行政面试评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "trainingScore", value = "培训成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "trainingScoring", value = "培训记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "writtenScore", value = "笔试成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "writtenScoring", value = "笔试记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "otherScore", value = "其他成绩", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "otherScoring", value = "其他记分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bornDate", value = "出身日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "natives", value = "籍贯", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highest", value = "全日制最高学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coursesCollege", value = "全日制本科学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "coursesProfessional", value = "全日制本科专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mastersCollege", value = "全日制硕士学院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "degreeProfessional", value = "全日制硕士专业", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "batch", value = "批次", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:正常，1：删除)", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOnboardDistribution(PpmOnboardDistributionDto ppmOnboardDistributionDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOnboardDistributionService.updatePpmOnboardDistribution(ppmOnboardDistributionDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 导入
     * @param batchNo 批次号
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "根据批次号导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNo", value = "批次号", paramType = "query"),
    })
    public ApiResult importExcel(String batchNo,@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();

        boolean falg = ppmOnboardDistributionService.importExcel(batchNo, file);
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

	/**
	 * 导出
	 * @param ppmOnboardDistributionDto 对象
	 * @param response  response
	 */
	@PostMapping("exportExcel")
	@ApiOperation(value = "导出", notes = "根据筛选条件导出 - 页面元素请参考 分页方法")
    public void exportExcel(PpmOnboardDistributionDto ppmOnboardDistributionDto, HttpServletResponse response) {
		ApiResult result = new ApiResult();
    	List<PpmOnboardDistributionDto> list = ppmOnboardDistributionService.queryList(ppmOnboardDistributionDto);
		ExcelUtil.exportExcel(list, PpmOnboardDistributionDto.class, "入职部门分配导出.xlsx",response);

	}

	/**
	 * 获取页面是否操作权限
	 * @param request request
	 * @return  ApiResult
	 */
	@GetMapping("getOperatAuth")
	@ApiOperation(value = " 获取操作权限列表", notes = " 获取操作权限列表")
	public ApiResult getOperatAuth(HttpServletRequest request) {
		ApiResult result = new ApiResult();
		CommOperatorDoLoginDto user = super.getCurrentUser(request);
		int type = ppmOnboardDistributionService.getAuth(user);
		result.setResults(type);
		result.setStatus(ApiCode.SUCCESS);
		if(1 == type) {
			result.setMessage("管理员");
		} else if( 2 == type) {
			result.setMessage("普通管理员");
		} else {
			result.setMessage("无权限查看");
		}
		return result;
	}

	/**
	 * 删除
	 * @param resumeIds 简历id
	 * @return  ApiResult
	 */
	@GetMapping("del")
	@ApiOperation(value = " 删除", notes = "根据简历编号和主键id删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "resumeIds", value = "简历id, 多个用 “,”分开", paramType = "query",required = true)
	})
	public ApiResult delete(String resumeIds ) {
		ApiResult result = new ApiResult();
		AssertUtil.notNull(resumeIds, "简历编号不能为空");
		int rid = ppmOnboardDistributionService.delete(resumeIds);
		if(rid > 0) {
			result.success("操作成功");
		} else {
			result.failure("删除失败");
		}
		return result;
	}
}
