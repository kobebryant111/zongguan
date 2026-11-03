package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingChangeInfosService;
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
 * 员工信息变更详细
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingChangeInfosApi"},description="员工信息变更详细")
@RestController
@RequestMapping("ppmOutsourcingChangeInfos")
@Slf4j
public class PpmOutsourcingChangeInfosApi {
	/**
	 * 员工信息变更详细
	 */
    @Autowired
    private IPpmOutsourcingChangeInfosService ppmOutsourcingChangeInfosService;

    /**
     * 分页
     * @param ppmOutsourcingChangeInfos 对象
     * @param pager pager
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certnumber", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companyid", value = "公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companyname", value = "公司名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldDepid", value = "老部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldDepname", value = "老部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newDepid", value = "新部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newDepname", value = "新部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOrganizers", value = "老行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOrganizersname", value = "老行内负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOrganizers", value = "新行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOrganizersname", value = "新行内负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldGroupid", value = "老小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldGroupname", value = "老小组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newGroupid", value = "新小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newGroupname", value = "新小组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldProjectno", value = "老项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldProjectname", value = "老项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newProjectno", value = "新项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newProjectname", value = "新项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldSysno", value = "老系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldSyaname", value = "老系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newSysno", value = "新系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newSysname", value = "新系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOutsourcingtype", value = "老外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOutsourcingtype", value = "新外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldUsetype", value = "老结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newUsetype", value = "新结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPosition", value = "老岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPosition", value = "新岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPrice", value = "老价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPrice", value = "新价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldContractid", value = "老合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldContractname", value = "老合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newContractid", value = "新合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newContractname", value = "新合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "interviewtime", value = "面试时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "professionscore", value = "专业分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "valuescore", value = "价值观分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "imagescore", value = "形象分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "comments", value = "评论", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "years", value = "年限", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "grade", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "totalscore", value = "成绩", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "result", value = "结果", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "recommendedPrice", value = "建议价格", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "reason", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOutsourcingtypename", value = "老外包方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOutsourcingtypename", value = "新外包方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPositionname", value = "老岗位名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPositionname", value = "新岗位名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newUsetypename", value = "老结算方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldUsetypename", value = "新结算方式名称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingChangeInfosService.queryPager(ppmOutsourcingChangeInfos,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingChangeInfos 对象
     * @return ApiResult<PpmOutsourcingChangeInfos>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certnumber", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companyid", value = "公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companyname", value = "公司名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldDepid", value = "老部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldDepname", value = "老部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newDepid", value = "新部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newDepname", value = "新部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOrganizers", value = "老行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOrganizersname", value = "老行内负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOrganizers", value = "新行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOrganizersname", value = "新行内负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldGroupid", value = "老小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldGroupname", value = "老小组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newGroupid", value = "新小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newGroupname", value = "新小组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldProjectno", value = "老项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldProjectname", value = "老项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newProjectno", value = "新项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newProjectname", value = "新项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldSysno", value = "老系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldSyaname", value = "老系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newSysno", value = "新系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newSysname", value = "新系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOutsourcingtype", value = "老外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOutsourcingtype", value = "新外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldUsetype", value = "老结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newUsetype", value = "新结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPosition", value = "老岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPosition", value = "新岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPrice", value = "老价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPrice", value = "新价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldContractid", value = "老合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldContractname", value = "老合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newContractid", value = "新合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newContractname", value = "新合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "interviewtime", value = "面试时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "professionscore", value = "专业分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "valuescore", value = "价值观分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "imagescore", value = "形象分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "comments", value = "评论", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "years", value = "年限", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "grade", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "totalscore", value = "成绩", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "result", value = "结果", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "recommendedPrice", value = "建议价格", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "reason", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOutsourcingtypename", value = "老外包方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOutsourcingtypename", value = "新外包方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPositionname", value = "老岗位名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPositionname", value = "新岗位名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newUsetypename", value = "老结算方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldUsetypename", value = "新结算方式名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingChangeInfos> queryDetail(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {
        ApiResult<PpmOutsourcingChangeInfos> result =new ApiResult<>();
        PpmOutsourcingChangeInfos item = ppmOutsourcingChangeInfosService.queryDetail(ppmOutsourcingChangeInfos);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingChangeInfos 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certnumber", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companyid", value = "公司编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "companyname", value = "公司名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldDepid", value = "老部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldDepname", value = "老部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newDepid", value = "新部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newDepname", value = "新部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOrganizers", value = "老行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOrganizersname", value = "老行内负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOrganizers", value = "新行内负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOrganizersname", value = "新行内负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldGroupid", value = "老小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldGroupname", value = "老小组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newGroupid", value = "新小组", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newGroupname", value = "新小组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldProjectno", value = "老项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldProjectname", value = "老项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newProjectno", value = "新项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newProjectname", value = "新项目名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldSysno", value = "老系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldSyaname", value = "老系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newSysno", value = "新系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newSysname", value = "新系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOutsourcingtype", value = "老外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOutsourcingtype", value = "新外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldUsetype", value = "老结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newUsetype", value = "新结算方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPosition", value = "老岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPosition", value = "新岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPrice", value = "老价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPrice", value = "新价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldContractid", value = "老合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldContractname", value = "老合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newContractid", value = "新合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newContractname", value = "新合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "interviewtime", value = "面试时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "professionscore", value = "专业分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "valuescore", value = "价值观分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "imagescore", value = "形象分", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "comments", value = "评论", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "years", value = "年限", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "grade", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "totalscore", value = "成绩", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "result", value = "结果", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "recommendedPrice", value = "建议价格", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "reason", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldOutsourcingtypename", value = "老外包方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newOutsourcingtypename", value = "新外包方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldPositionname", value = "老岗位名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPositionname", value = "新岗位名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newUsetypename", value = "老结算方式名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oldUsetypename", value = "新结算方式名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingChangeInfosService.updatePpmOutsourcingChangeInfos(ppmOutsourcingChangeInfos);
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
     * @param ppmOutsourcingChangeInfos 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingChangeInfos (PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingChangeInfosService.deletePpmOutsourcingChangeInfos(ppmOutsourcingChangeInfos);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

}
