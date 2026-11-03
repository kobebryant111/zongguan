package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKhOutsourcepersonDto;
import com.work.ppm.application.model.integratedmanager.OutEvalMonthData;
import com.work.ppm.application.model.integratedmanager.ext.OutEvalOrderDataInfo;
import com.work.ppm.application.services.integratedmanager.IPpmKhOutsourcepersonService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
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

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKhOutsourcepersonApi"},description="外包考核数据")
@RestController
@RequestMapping("ppmKhOutsourceperson")
@Slf4j
public class PpmKhOutsourcepersonApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKhOutsourcepersonService ppmKhOutsourcepersonService;

    /**
     * 分页
     * @param ppmKhOutsourcepersonDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "outsourceno", value = "公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "organizers", value = "负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workday", value = "工作天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lvworkday", value = "请假天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtime", value = "加班时数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstlevel", value = "第一次评级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondlevel", value = "第二次评级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gznr", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "manageuserid", value = "二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "by1", value = "备用字段1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "by2", value = "备用字段2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "考核状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "appltbegtime", value = "入场时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "yeartotalscore", value = "年度总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "yearavgscore", value = "年度平均分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "personstatus", value = "人员状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "totalmonth", value = "月计数", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tlevel", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fgmanageuserid", value = "分管二级部经理", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKhOutsourcepersonService.queryPager(ppmKhOutsourcepersonDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKhOutsourcepersonDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "outsourceno", value = "公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "organizers", value = "负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workday", value = "工作天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lvworkday", value = "请假天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtime", value = "加班时数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstlevel", value = "第一次评级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondlevel", value = "第二次评级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gznr", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "manageuserid", value = "二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "by1", value = "备用字段1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "by2", value = "备用字段2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "考核状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "appltbegtime", value = "入场时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "yeartotalscore", value = "年度总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "yearavgscore", value = "年度平均分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "personstatus", value = "人员状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "totalmonth", value = "月计数", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tlevel", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fgmanageuserid", value = "分管二级部经理", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKhOutsourcepersonDto> queryDetail(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        ApiResult<PpmKhOutsourcepersonDto> result =new ApiResult<>();
        PpmKhOutsourcepersonDto item = ppmKhOutsourcepersonService.queryDetail(ppmKhOutsourcepersonDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKhOutsourcepersonDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "outsourceno", value = "公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "organizers", value = "负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workday", value = "工作天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lvworkday", value = "请假天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtime", value = "加班时数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstlevel", value = "第一次评级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondlevel", value = "第二次评级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gznr", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "manageuserid", value = "二级部经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "by1", value = "备用字段1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "by2", value = "备用字段2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "考核状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "appltbegtime", value = "入场时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "yeartotalscore", value = "年度总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "yearavgscore", value = "年度平均分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "personstatus", value = "人员状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "totalmonth", value = "月计数", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tlevel", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fgmanageuserid", value = "分管二级部经理", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKhOutsourcepersonService.updatePpmKhOutsourceperson(ppmKhOutsourcepersonDto);
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
     * @param ppmKhOutsourcepersonDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKhOutsourceperson (PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKhOutsourcepersonService.deletePpmKhOutsourceperson(ppmKhOutsourcepersonDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

	/**
	 * 查询外包考核月度统计数据
	 * @param outDataDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getOutEvalMonthData")
	@ApiOperation(value = "查询外包考核月度统计数据", notes = "查询外包考核月度统计数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
	})
	public ApiResult<OutEvalMonthData> getOutEvalMonthData(OutDataDto outDataDto) {
		ApiResult<OutEvalMonthData> result =new ApiResult<>();
		OutEvalMonthData item = ppmKhOutsourcepersonService.getOutEvalMonthData(outDataDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 查询外包考核月度比例数据
	 * @param outDataDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getOutEvalOrderData")
	@ApiOperation(value = "查询外包考核月度比例数据", notes = "查询外包考核月度比例数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
	})
	public ApiResult<OutEvalOrderDataInfo> getOutEvalOrderData(OutDataDto outDataDto) {
		ApiResult<OutEvalOrderDataInfo> result =new ApiResult<>();
		OutEvalOrderDataInfo item = ppmKhOutsourcepersonService.getOutEvalOrderData(outDataDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
}
