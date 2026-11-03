package com.work.ppm.application.api.rdmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.rdmanager.SystemListDto;
import com.work.ppm.application.services.rdmanager.IPpmOperatorKeyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.SmsContext;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyAgreement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * 密码管理
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmOperatorKey"}, description = "密码管理")
@RestController
@RequestMapping("ppmOperatorKey")
@Slf4j
@Validated
public class PpmOperatorKey extends BaseApi {


    @Autowired
    private IPpmOperatorKeyService ppmOperatorKeyService;

    private static final String SMS_SESSION_KEY = "sms_context";

    /**
     * 分页
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query")
    })

    public ApiResult<Object> queryPager(@RequestBody Pager pager, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmOperatorKeyService.queryPager(pager, user);
        ApiResult<Object> result = new ApiResult<>();
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "queryAllPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobidResponsible", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nameResponsible", value = "姓名", dataType = "string", paramType = "query")
    })

    public ApiResult<Object> queryAllPager(@RequestBody Pager pager, @RequestBody SystemListDto systemListDto, HttpServletRequest request) {
        ppmOperatorKeyService.queryAllPager(pager, systemListDto);
        ApiResult<Object> result = new ApiResult<>();
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页
     *
     * @param systemListDto
     * @return
     */
    @PostMapping(value = "resetUserScodPwd")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobidResponsible", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nameResponsible", value = "姓名", dataType = "string", paramType = "query")
    })

    public ApiResult<Object> resetUserScodPwd(@RequestBody SystemListDto systemListDto, HttpServletRequest request) {
        ppmOperatorKeyService.resetUserScodPwd(systemListDto, request);
        ApiResult<Object> result = new ApiResult<>();
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 判断是否首次进入密码管理模块
     *
     * @param request
     * @return ApiResult<Object>
     */
    @PostMapping(value = "firstEnter")
    @ApiOperation(value = "判断是否首次进入密码管理模块", notes = "判断是否首次进入密码管理模块")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query")
    })

    public ApiResult<Object> firstEnter(HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ApiResult<Object> result = new ApiResult<>();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(ppmOperatorKeyService.firstEnter(user));
        return result;
    }

    /**
     * 首次进入密码管理模块设置密码
     *
     * @param verificationCode
     * @param confirmPassWord
     * @param passWord
     * @param request
     * @return
     */
    @PostMapping(value = "setPassword")
    @ApiOperation(value = "首次进入密码管理模块设置密码", notes = "首次进入密码管理模块设置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "工号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true),
    })

    public ApiResult<Object> setPassword(String verificationCode, String confirmPassWord, String passWord, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        if (!(StringUtil.isNotBlank(confirmPassWord) && StringUtil.isNotBlank(passWord))) {
            throw new BizRuntimeException("参数不能为空");
        }
        if (!passWord.equals(confirmPassWord)) {
            result.setResults("两次输入的密码不一致");
            result.setStatus(ApiCode.ATTENDANCE_ERROR);
            return result;
        }
        try {
            ppmOperatorKeyService.setPassword(request, verificationCode, confirmPassWord);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }

        return result;
    }


    @PostMapping(value = "changePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "工号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult<Object> changePassword(String verificationCode, String confirmPassword,
                                            String newPassword, String password, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        ppmOperatorKeyService.changePassword(verificationCode, confirmPassword, newPassword, password, request);

        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    @GetMapping(value = "getCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @ApiImplicitParams({
    })

    public ApiResult<Object> getCode(HttpServletRequest request) {
        ApiResult<Object> result = ppmOperatorKeyService.getCode(request);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 校验验证码
     *
     * @param request
     * @return
     */
    @PostMapping(value = "verifyCode")
    @ApiOperation(value = "校验验证码", notes = "校验验证码")
    @ApiImplicitParams({
    })

    public ApiResult<Object> verifyCode(String verificationCode, String passWord, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();

        try {
            ppmOperatorKeyService.verifyCodeAndPassword(request, verificationCode, passWord);
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 关闭验证码
     *
     * @param request
     * @return
     */
    @PostMapping(value = "clossverifyCode")
    @ApiOperation(value = "关闭验证码", notes = "关闭验证码")
    @ApiImplicitParams({
    })

    public ApiResult<Object> clossverifyCode(HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            ppmOperatorKeyService.clossverifyCode(request);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 校验验证码
     *
     * @param request
     * @return
     */
    @PostMapping(value = "selectverifyCode")
    @ApiOperation(value = "校验验证码", notes = "校验验证码")
    @ApiImplicitParams({
    })

    public ApiResult<Object> selectverifyCode(HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            result.setResults(ppmOperatorKeyService.selectverifyCode(request));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 第一次ecdhe
     *
     * @param body
     * @param request
     * @return
     */
    @PostMapping(value = "ecdhefirst")
    @ApiOperation(value = "第一次ecdhe", notes = "第一次ecdhe")
    @ApiImplicitParams({
    })
    public ApiResult<Object> ecdhefirst(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            result.setResults(ppmOperatorKeyService.ecdhefirst(body, request));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "ecdheAddfirst")
    @ApiOperation(value = "第一次添加ecdhe", notes = "第一次添加ecdhe")
    @ApiImplicitParams({
    })
    public ApiResult<Object> ecdheAddfirst(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            result.setResults(ppmOperatorKeyService.ecdheAddfirst(body, request));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 删除系统密码
     *
     * @param body
     * @return
     */
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除系统密码", notes = "删除系统密码")
    @ApiImplicitParams({
    })
    public ApiResult<Object> delete(@RequestBody Map<String, ArrayList<String>> body) {
        ApiResult<Object> result = new ApiResult<>();
        ArrayList<String> ids = body.get("ids");
        try {
            ppmOperatorKeyService.deleteSysList(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加系统密码
     *
     * @param body
     * @return
     */
    @PostMapping(value = "addSysPsd")
    @ApiOperation(value = "添加系统密码", notes = "添加系统密码")
    @ApiImplicitParams({
    })
    public ApiResult<Object> addSysPsd(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            ppmOperatorKeyService.addSysPsd(body, request);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "updataSysPsd")
    @ApiOperation(value = "修改系统密码", notes = "修改系统密码")
    @ApiImplicitParams({
    })
    public ApiResult<Object> updataSysPsd(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        try {
            ppmOperatorKeyService.updataSysPsd(body, request);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizRuntimeException(e.getMessage());
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
