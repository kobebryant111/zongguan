package com.work.ppm.commons.authentication;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * 权限
     * @param shiroRealm  对象
     * @return  SecurityManager
     */
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        return securityManager;
    }

    /**
     *  authorizationAttributeSourceAdvisor
     * @param securityManager 对象
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     *  sessionManager
     * @return DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());

        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(new MemorySessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 修改 Cookie 名， 避免与servlet容器 默认的Cookie名（JSESSIONID) 冲突
        Cookie cookie = new SimpleCookie("ppm.session.id");
        cookie.setMaxAge(360);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     *  shiroFilterFactoryBean
     * @param securityManager 对象
     * @return  ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        Map<String,String> filterChainefinitionMap = new LinkedHashMap<>();
//        filterChainefinitionMap.put("/logout","anon");
//        filterChainefinitionMap.put("/static","anon");
//        filterChainefinitionMap.put("/**","authc");
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainefinitionMap);
        return shiroFilterFactoryBean;
    }


}
