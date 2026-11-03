package com.work.ppm.commons.interceptor;

import com.work.ppm.commons.constant.SystemConstant;
import com.work.ppm.commons.utils.CookieUtil;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 系统环境接口
     */
    private Environment environment = SpringUtil.getBean(Environment.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String depl = environment.getProperty(SystemConstant.SPRING_PROFILES_ACTIVE);
        // 如果是生产环境，不添加默认token
        String token;
        if(SystemConstant.SYSTEM_ENV_UAT.equals(depl)) {
            token = request.getHeader(SystemConstant.XTOKEN);
            // 如果token为空 则从cookie里面找
            if(!StringUtil.isStringNotEmpty(token)) {
                token = CookieUtil.getValue(SystemConstant.ADMIN_TOKEN, request);
            }
        } else {
            token = request.getHeader(SystemConstant.XTOKEN) !=   null
                    ? request.getHeader(SystemConstant.XTOKEN) :
                    SystemConstant.TOKEN_USERID;
        }
        String urlPrifx = request.getRequestURI();
        // 不拦截 ，其余皆拦截
        if(urlPrifx.indexOf("loginApi") != -1) {
            log.info("以[loginApi]开头 都不拦截!!");
            return true;
        }
        if(!StringUtil.isNotBlank(token)) {
            return false;
        }
        return true;
    }
}
