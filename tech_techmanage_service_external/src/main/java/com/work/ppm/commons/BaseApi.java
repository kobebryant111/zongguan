package com.work.ppm.commons;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.commons.constant.SystemConstant;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.CookieUtil;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.StringJoiner;

/**
 * api父类
 */
@Slf4j
@Component
public class BaseApi {

    /**
     * env
     */
    @Autowired
    private Environment env;

    /**
     * 获取session信息
     * @return Subject
     */
    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户信息
     * @return CommOperatorDoLoginDto
     */
    @Deprecated
    protected CommOperatorDoLoginDto getCurrentUser() {
        return (CommOperatorDoLoginDto) getSubject().getPrincipal();
    }


    /**
     * 获取用户信息
     * @param request request
     * @return CommOperatorDoLoginDto
     */
    public CommOperatorDoLoginDto getCurrentUser(HttpServletRequest request) {
        ICommOperatorService commOperatorService = (ICommOperatorService) SpringUtil.getBean("commOperatorService");
        //获取ip
        String ip= IpUtil.getIpAddress(request);
        log.warn("BaseApi：：ip:{}",ip);
        String token = request.getHeader(SystemConstant.XTOKEN);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>XTOKEN:{}" + token + "");
        String secertKey=request.getHeader(SystemConstant.SECERTKEY);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>SECERTKEY:{}" + secertKey + "");
        // 如果token为空 则从cookie里面找
        if(!StringUtil.isNotBlank(token)) {
            Enumeration headerEnu = request.getHeaderNames();
            StringJoiner joiner = new StringJoiner("||");
            while (headerEnu.hasMoreElements()) {
                String key = (String) headerEnu.nextElement();
                String value = request.getHeader(key);
                joiner.add("key:" + key + ",value:" + value);
            }
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>header:{}" + joiner.toString() + "");

            token = CookieUtil.getValue(SystemConstant.ADMIN_TOKEN, request);
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ADMIN_TOKEN:{}", token + "#");
            secertKey = CookieUtil.getValue(SystemConstant.SECRET_TOKEN, request);
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>SECRET_TOKEN:{}", secertKey + "#");
        }

        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>xtoekn:{}", token);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>SECERTKEY:{}", secertKey);
        CommOperator operator = new CommOperator();
        operator.setUserid(token);
        CommOperatorDoLoginDto item = commOperatorService.doLogin(operator);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>【新应用】用户信息:{}", JsonUtil.toJSONString(item));
        if(null == item) {
            log.error("新应用[{}]用户登录失败", token+"");
            throw new BizException(ApiCode.FAIL,"用户登录失败");
        }
        if(!(item.getSecretKey()).equals(secertKey)){
            log.error("新应用[{}]工号验证失败", token+"");
            throw new BizException(ApiCode.BADGE_VERIFICATION_ERROR,"工号验证失败");
        }

        return item;
    }

}