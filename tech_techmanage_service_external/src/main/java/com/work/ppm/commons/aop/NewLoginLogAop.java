package com.work.ppm.commons.aop;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.application.dto.basismanager.PpmNewloginLogDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.LoginDto;
import com.work.ppm.application.quartz.core.ExecutorManager;
import com.work.ppm.application.services.basismanager.IPpmNewloginLogService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 新应用登录日志
 *
 */
@Component
@Aspect
@MercuryLog
@Order(2)
@Slf4j
public class NewLoginLogAop {
    /**
     * 登录日志
     */
    @Autowired
    private IPpmNewloginLogService ppmNewloginLogService;
    /**
     * 登录切入点
     */
    @Pointcut("execution(* com.work.ppm.application.api.login.LoginApi.doLogin(..))")
    public void pointCut1() {
        // Do nothing because of pointcut expression
    }

    /**
     * 单点登录切入点
     */
    @Pointcut("execution(* com.work.ppm.application.api.login.LoginApi.singleLogin(..))")
    public void pointCut2() {
        // Do nothing because of pointcut expression
    }
    /**
     * 环绕通知
     * @param point 对象
     * @return  Object
     */
    @Around("pointCut1() || pointCut2()")
    public Object hanleControllerMethed(ProceedingJoinPoint point) {
        ApiResult<CommOperatorDoLoginDto> result = null;
        try {
//            log.warn("name:{}",point.getSignature().getName());
            result = (ApiResult<CommOperatorDoLoginDto>) point.proceed(point.getArgs());
            PpmNewloginLogDto logDto = new PpmNewloginLogDto();
            logDto.setCreateTime(new Date());
            CommOperatorDoLoginDto user = result.getResults();
            logDto.setUserId(user.getUserid());
            logDto.setUserName(user.getOperatorname());
            // 单点
            if("singleLogin".equals(point.getSignature().getName())) {
                logDto.setIfSingle(1);
                LoginDto loginDto = (LoginDto) point.getArgs()[0];
                HttpServletRequest request = (HttpServletRequest) point.getArgs()[1];
                logDto.setSingleParams(JsonUtil.toJSONString(loginDto));
                if(StringUtil.isNotBlank(loginDto.getIp())) {
                    logDto.setIp(loginDto.getIp());
                } else {
                    String ip = IpUtil.getIpAddress(request);
                    logDto.setIp(ip);
                }
            } else { // 登录
                HttpServletRequest request = (HttpServletRequest) point.getArgs()[2];
                String ip = IpUtil.getIpAddress(request);
                logDto.setIp(ip);
                logDto.setIfSingle(0);
            }
            ExecutorManager.getExecutor().submit(()-> {
                ppmNewloginLogService.addPpmNewloginLog(logDto);
                log.info("【新应用】登录日志：{}", JsonUtil.toJSONString(logDto));
            });
        } catch (Throwable throwable) {
           throw new BizException(ApiCode.LOGIN_LOG_COLLECT,throwable.getMessage());
        }
        return result;
    }
}
