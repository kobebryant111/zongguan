package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 员工登录操作
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"CommOperatorApi"},description = "员工登录操作")
@RestController
@RequestMapping("commOperator")
@Slf4j
public class CommOperatorApi {
    /**
     * 员工登录操作
     */
    @Autowired
    private ICommOperatorService commOperatorService;

    /**
     * 分页
     * @param commOperator 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "员工工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "operatorname", value = "登录名称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommOperator commOperator,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commOperatorService.queryPager(commOperator,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情.
     * @param commOperator 对象
     * @return ApiResult<CommOperator>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "unlocktime", value = "解锁时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastlogin", value = "最后登陆时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "invaldate", value = "失效时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "email", value = "EMAIL", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userType", value = "用户类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "menutype", value = "菜单风格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "errcount", value = "错误次数", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "maccode", value = "MAC地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "员工工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "authmode", value = "认证模式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态 NEFFECT:无权限  RUNNING:有权限  LOCK:锁定  STOP:用户失效", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "validtime", value = "生效时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "ipaddress", value = "IP地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "operatorid", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "enddate", value = "结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "partid", value = "兼职ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "operatorname", value = "登录名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<CommOperator> queryDetail(CommOperator commOperator) {
        ApiResult<CommOperator> result =new ApiResult<>();
        CommOperator item = commOperatorService.queryDetail(commOperator);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param commOperator 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unlocktime", value = "解锁时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastlogin", value = "最后登陆时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "invaldate", value = "失效时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "email", value = "EMAIL", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userType", value = "用户类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "menutype", value = "菜单风格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "errcount", value = "错误次数", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "maccode", value = "MAC地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "员工工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "authmode", value = "认证模式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态 NEFFECT:无权限  RUNNING:有权限  LOCK:锁定  STOP:用户失效", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "validtime", value = "生效时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "ipaddress", value = "IP地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "operatorid", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "enddate", value = "结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "partid", value = "兼职ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "operatorname", value = "登录名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updateCommOperator(CommOperator commOperator) {
        ApiResult<Object> result =new ApiResult<>();
        int id = commOperatorService.updateCommOperator(commOperator);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 更新
     * @param commOperator 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updateUserPwd")
    public ApiResult<Object> updateUserPwd(@RequestBody CommOperatorDto commOperator ) {
        ApiResult<Object> result =new ApiResult<>();
        String userId = commOperator.getUserid();
        String password = commOperator.getPassword();
        String newPwd = commOperator.getNewPassword();
        String confirmPwd = commOperator.getConfirmPassword();
        try {
            commOperatorService.updateUserPassword(userId, password, newPwd, confirmPwd);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 重置密码
     * @param commOperator 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "resetUserPwd")
    public ApiResult<Object> resetUserPwd(@RequestBody CommOperatorDto commOperator ) {
        ApiResult<Object> result =new ApiResult<>();
        String userId = commOperator.getUserid();

        // 重置密码
        CommOperator operator = new CommOperator();
        operator.setUserid(userId);
        CommOperator user = commOperatorService.getList(commOperator).get(0);
        user.setPassword(CryptoUtil.digestByMD5("work,111"));
        user.setInvaldate(DateUtil.addOrSubDate(new Date(), 90));
        user.setLastlogin(new Date());
        user.setStatus("running");
        int i = commOperatorService.updateCommOperatorCus(user);
        if(i > 0 ){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("未查询到对应的用户信息。");
        }

        return result;
    }

    /**
     * 获取头像
     * @param commOperator 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "getProfilePicture")
    @ApiOperation(value = "getProfilePicture", notes = "获取头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unlocktime", value = "解锁时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastlogin", value = "最后登陆时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "invaldate", value = "失效时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "EMAIL", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userType", value = "用户类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "menutype", value = "菜单风格", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "errcount", value = "错误次数", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "maccode", value = "MAC地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "员工工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "authmode", value = "认证模式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 NEFFECT:无权限  RUNNING:有权限  " +
                    "LOCK:锁定  STOP:用户失效",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "validtime", value = "生效时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "ipaddress", value = "IP地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "operatorid", value = "主键", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "startdate", value = "开始时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "enddate", value = "结束时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "partid", value = "兼职ID", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "operatorname", value = "登录名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query"),

    })
    public ApiResult<CommOperatorDoLoginDto> getProfilePicture(CommOperator commOperator) {
        ApiResult<CommOperatorDoLoginDto> result =new ApiResult<>();
        CommOperatorDoLoginDto item = commOperatorService.doLogin(commOperator);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
