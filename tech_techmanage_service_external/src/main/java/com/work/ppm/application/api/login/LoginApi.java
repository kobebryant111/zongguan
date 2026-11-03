package com.work.ppm.application.api.login;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImMenusDto;
import com.work.ppm.application.dto.systemmanager.LoginDto;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.IImMenusService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 未登录接口
 *
 */
@Api(value = "登录Api", tags = {"LoginApi"}, description = "未登录接口")
@RestController("loginApi")
@Slf4j
public class LoginApi extends BaseApi {
    /**
     * 操作接口
     */
    @Autowired
    private ICommOperatorService commOperatorService;
    /**
     * 菜单接口
     */
    @Autowired
    private IImMenusService iImMenusService;
    /**
     * 员工接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;


    /**
     * 登录
     *
     * @param username 工号
     * @param password 密码
     * @param request request
     * @return ApiResult
     */
    @PostMapping(value = "/login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "工号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult doLogin(String username, String password,HttpServletRequest request) {
        ApiResult result = new ApiResult();
        if (!StringUtil.isStringNotEmpty((password))) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("密码不能为空");
            return result;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(getCurrentUser());
        return result;
    }

    /**
     * 单点登录
     *
     * @param loginPojo
     * @param request request
     * @return ApiResult
     */
    @PostMapping({"/singleLogin"})
    @ApiOperation("单点登录")
    public ApiResult singleLogin(@RequestBody LoginDto loginPojo,HttpServletRequest request) {
        ApiResult result = new ApiResult();
//        LoginPojo loginPojo = JsonUtil.stringToObject(jsonstr, LoginPojo.class);
        if (StringUtils.isBlank(loginPojo.getUserId())) {
            log.error("参数异常，userId不能为空!");
            result.setStatus(ApiCode.LOGIN_FAIL);
            result.setMessage("参数异常，userId不能为空");
            return result;
        }
        if (StringUtils.isBlank(loginPojo.getToken())) {
            log.error("参数异常，token不能为空");
            result.setStatus(ApiCode.LOGIN_FAIL);
            result.setMessage("参数异常，token不能为空");
            return result;
        }
//        String ip = IpUtil.getIpAddress(request);
        loginPojo.setClientIP(loginPojo.getIp());
        result = commOperatorService.singleLogin(loginPojo);
        if (result.getStatus() == ApiCode.SUCCESS.getCode()) {
            result.setResults(getCurrentUser());
        }
        return result;
    }

    /**
     * 获取用户信息
     *
     * @return result
     */
    @GetMapping(value = "/user/info")
    @ApiOperation("获取用户信息")
    public ApiResult<CommOperatorDoLoginDto> FetchUserInfo(HttpServletRequest request) {
        ApiResult<CommOperatorDoLoginDto> result = new ApiResult();
        CommOperatorDoLoginDto imUser = getCurrentUser(request);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(imUser);
        return result;
    }

    /**
     * 根据角色id获取权限菜单
     *
     * @param rid
     * @return result
     * @throws Exception
     */
    @GetMapping(value = "/permiss/{rid}")
    @ApiOperation("根据角色id获取权限菜单")
    @ApiImplicitParam(name = "rid", value = "角色id", dataType = "int", paramType = "query", required = true)
    public ApiResult getPermissionsByRid(@PathVariable String rid, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        if (StringUtils.isBlank(rid)) {
            log.info("{}角色ID不能为空！<根据角色获得权限>");
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("参数异常");
            return result;
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ImMenusDto menusDto = iImMenusService.getLoginMenus(rid, user);
        result.setResults(menusDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 登出
     *
     * @return result
     * @throws Exception
     */
    @GetMapping("/logout")
    @ApiOperation("登出")
    public ApiResult logout() {
        ApiResult result = new ApiResult();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 获取Token
     *
     * @param request request
     * @param userId  工号
     * @return ApiResult
     */
    @PostMapping("/getToken")
    @ApiOperation("获取Token")
    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "sring", paramType = "query", required = true)
    public ApiResult getTokenMd5(HttpServletRequest request, @RequestParam("userId") String userId) {
        ApiResult result = new ApiResult<>();
        String ip = IpUtil.getIpAddress(request);
        String md5 = commEmployeeService.getTokenMd5(userId, ip);
        result.success("获取成功");
        result.setResults(md5);
        return result;
    }
}
