package com.work.ppm.commons.aop;

import com.google.common.collect.Lists;
import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.sql.SQLException;

/**
 * 全局异常处理类
 *
 */
@Component
@Aspect
@MercuryLog
@Order(1)
@Slf4j
public class ExceptionControllerAop {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.work.ppm.application.api.*.*.*(..))")
    public void pointCut() {
    }

    /**
     * 环绕通知
     *
     * @param point 对象
     * @return Object
     */
    @Around("pointCut()")
    public Object hanleControllerMethed(ProceedingJoinPoint point) {
        StopWatch stopWatch = new StopWatch("First");
        stopWatch.start();
        ApiResult<?> result = null;
        try {
            log.info("【新应用】执行Controller 开始{} 参数:{}", point.getSignature(),
                    Lists.newArrayList(point.getArgs()).toString());
            result = (ApiResult<?>) point.proceed(point.getArgs());
            if (null != result) {
                log.info("【新应用】执行Controller 结束{} 状态:{}", point.getSignature(), true);
                stopWatch.stop();
                log.info("【新应用】耗时:{}毫秒", stopWatch.getTotalTimeMillis());
            }
        } catch (Throwable e) {
            StackTraceElement ste = e.getStackTrace()[0];
            log.error("【新应用】异常类：{}", ste.getClassName());
            log.error("【新应用】异常文件名：{}", ste.getFileName());
            log.error("【新应用】异常行数：{}", ste.getLineNumber());
            log.error("【新应用】异常方法：{}", ste.getMethodName());
            result = handlerException(point, e);
        }
        return result;
    }

    /**
     * 异常处理
     *
     * @param point 对象
     * @param e     异常
     * @return ApiResult<?>
     */
    private ApiResult<?> handlerException(ProceedingJoinPoint point, Throwable e) {
        ApiResult<?> result = null;
        if (e instanceof BizException) {
            BizException exception = (BizException) e;
            result = exception.getApiResult();
            log.error("【新应用】详细异常日志1：{}", JsonUtil.toJSONString(result));
            return result;
        }

        try {
            if (null != e.getCause() && e.getCause().getClass().isAssignableFrom(BizException.class)) {
                BizException exception = (BizException) e.getCause();
                result = new ApiResult<>();
                result.setStatus(exception.getCode());
                result.setMessage(exception.getMsg());
            }
        } catch (Exception ec) {
            log.error("【新应用】详细异常日志2：", ec);
            result = this.getException(point, e);
        }
        if (null == result || result.getStatus() < 0) {
            result = this.getException(point, e);
        }
        log.error("【新应用】详细异常日志3：{}", JsonUtil.toJSONString(result));
        return result;
    }

    /**
     * 异常捕获
     *
     * @param point 对象
     * @param e     异常
     * @return ApiResult
     */
    private ApiResult getException(ProceedingJoinPoint point, Throwable e) {
        log.error("【新应用】详细异常日志4:", e);
        ApiResult result = new ApiResult<>();
        if (e instanceof SQLException) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage(e.getMessage());
        } else if (e instanceof DuplicateKeyException) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("违反唯一约束条件!");
        }else if (e instanceof BizRuntimeException) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage(StringUtil.isNotBlank(e.getMessage())?e.getMessage():"系统内部异常，请联系管理员");
        }else if (e instanceof BizException) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage(StringUtil.isNotBlank(e.getMessage())?e.getMessage():"系统内部异常，请联系管理员");
        } else if (e instanceof RuntimeException) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("系统内部异常，请联系管理员");
        } else {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("系统内部异常，请联系管理员");
        }
        return result;
    }
}
