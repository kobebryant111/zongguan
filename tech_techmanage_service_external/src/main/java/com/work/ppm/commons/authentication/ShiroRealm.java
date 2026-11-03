package com.work.ppm.commons.authentication;

import com.work.ppm.application.dto.flowmanager.PpmRelateuserDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.application.services.flowmanager.IPpmRelateuserService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.impl.ImRoleService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * shiroRealm 登录验证器
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    /**
     * 变量
     */
    public final static String SINGLE_TOKE="singleLoginPasssowrd";
    /**
     * 角色接口
     */
    @Autowired
    private ImRoleService imRoleService;
    /**
     * 登录人员接口
     */
    @Autowired
    private ICommOperatorService commOperatorService;
    /**
     * 外包人员关联账号接口
     */
    @Autowired
    private IPpmRelateuserService ppmRelateuserService;

    /**
     * get
     * @param principalCollection  对象
     * @return     AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        CommOperatorDoLoginDto user = (CommOperatorDoLoginDto) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getUserid();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        try{
            List<ImRoleDoLoginDto> roleList = imRoleService.getImRole(userId);
            Set<String> roleSet = roleList.stream().map(ImRoleDoLoginDto::getRoleName).collect(Collectors.toSet());
            simpleAuthorizationInfo.setRoles(roleSet);
        }catch (Exception e){
            log.error("" + e);
        }
        return simpleAuthorizationInfo;
    }
    /**
     * post 自定义验证
     * @param authenticationToken  对象
     * @return     AuthorizationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String credentials = new String((char[]) authenticationToken.getCredentials());
        CommOperator operator = new CommOperator();
        operator.setUserid(username);
        operator.setPassword(credentials);
        CommOperatorDoLoginDto user = null;
        try {
            user = commOperatorService.doLogin(operator);
        } catch (Exception e) {
            log.error("" + e);
            throw new BizException(ApiCode.LOGIN_FAIL,e.getMessage());
        }
        String passwd = "";
        // 如果是单点 密码则固定
        if(SINGLE_TOKE.equals(credentials)) {
            passwd = user.getPassword();
        } else {
            passwd = CryptoUtil.digestByMD5(credentials);
        }
        long days = 0;
        if (!ObjectUtils.isEmpty(user.getLastlogin())) {
            final Date date = new Date();
            long diffMillis = date.getTime() - user.getLastlogin().getTime();
            days = TimeUnit.MILLISECONDS.toDays(diffMillis);
        }
        if (user == null || !user.getPassword().equals(passwd)) {
            throw new UnknownAccountException("用户名或密码错误");
//            throw new BizException(ApiCode.LOGIN_FAIL,"用户名或密码错误!");
        } else if(!user.getStatus().equals("running")) {
            throw new UnknownAccountException("用户被停用");
//            throw new BizException(ApiCode.LOGIN_FAIL,"用户被停用");
        } else if (days >= 180) {
            throw new UnknownAccountException("用户超过180天未登陆被停用");
//            throw new BizException(ApiCode.LOGIN_FAIL,"用户超过180天未登陆被停用");
        }
        else if(null == user.getRoles() || user.getRoles().isEmpty()) {
            throw new UnknownAccountException("用户未被授权");
//            throw new BizException(ApiCode.LOGIN_FAIL,"用户未被授权");
        }
        // 查询关联账号
        PpmRelateuserDto relateuserDto = ppmRelateuserService.queryUserInfoByUserId(username);
        // 记录登陆时间
        commOperatorService.updateLastlogin(username);
        if(null != relateuserDto) {
            operator.setUserid(relateuserDto.getUserno());
            user = commOperatorService.doLogin(operator);
        }
        return new SimpleAuthenticationInfo(user, credentials, getName());
    }
}
