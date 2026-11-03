package com.work.ppm.commons.lang.other;

import com.isprint.am.xmlrpc.clientcore.ApiProxy;
import com.isprint.am.xmlrpc.clientcore.ApiProxyFactory;
import com.isprint.am.xmlrpc.clientcore.AuthResultTO;
import com.isprint.am.xmlrpc.clientcore.UCMCheckoutEntry;
import com.isprint.am.xmlrpc.clientcore.UCMCredential;
import com.isprint.am.xmlrpc.clientcore.UCMSearchParams;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取密管密码
 */
@Component
@Slf4j
public class UCMGetPasswordClient {
    /**
     * realmid
     */
    private static String realmid="SystemRealm";
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 代理
     */
    private static ApiProxyFactory factory;
    /**
     * 代理
     */
    private static   ApiProxy proxy;

    /**
     * 构造函数
     */
    @PostConstruct
    public  void getSingleton() {
        EosDictEntry eos = eosDictEntryService
                .getTypeidForDetail("PWD_LOGIN_USER","01");
        String value = "";
        if(null != eos) {
            value = eos.getDictname();
            if(!StringUtil.isNotBlank(value)) {
                ApiProxyFactory.shutdownFactory("MyFactory");
            }
        }
        try {
            factory = ApiProxyFactory.registerFactory("MyFactory",
                    new String[] { value }, 30000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     * @param userId  工号
     * @param password 密码
     * @return AuthResultTO
     */
    public static AuthResultTO login(String userId,String password) {
        proxy = factory.createProxy();
        //login
        Map<String, String> params = new HashMap<>();
        params.put("realmId", realmid); // ntlm
        AuthResultTO result = null;
        try {
            result = proxy.getAuthnService().login("", userId,
                    password, params);
        } catch (XmlRpcException e) {
            log.error("密管登录异常：", e);
        }
        return result;
    }

    /**
     * 获取凭证状态
     * @param result result
     * @param key key
     * @return String
     */
    public static String getStatus(AuthResultTO result,String key)  {
        proxy = factory.createProxy();
        Map<String, String> params = new HashMap<>();
        params.put("realmId", realmid); // ntlm

        UCMCredential template = new UCMCredential();
        template.setUUID("");
        template.setStatus("");
        UCMSearchParams filter = new UCMSearchParams();
        filter.setCredentialId_like(key);
        String sessionToken1=result.getSession().getSessionToken();
        String pluginId = "findCredentials";
        List searchParams=new ArrayList();
        //设置凭证标识，如果为模糊查询则加入*，例如查询以带192标识的则为192*
        searchParams.add(key);

        Object[] credentials= new Object[0];
        try {
            credentials = (Object[]) proxy.getDataProtectionService().
                    executePluginService(sessionToken1,
                            pluginId, searchParams.toArray(),new HashMap());
        } catch (XmlRpcException e) {
            log.error("密管获取凭证状态异常：", e);
        }

        String s="";
        for (Object object : credentials) {
            HashMap<String, String> l =(HashMap<String, String>) object;
            s=l.get("workflowState");
        }
        return s;
    }

    /**
     * 获取密码
     * @param result 对象
     * @param key 密钥
     * @return 密码
     */
    public String getPassword(AuthResultTO result,String key) {
        String msg="" ;
        try {
            proxy = factory.createProxy();

            String sessionToken1 = result.getSession().getSessionToken();

            String pluginId = "UcmGetPassword";
            msg = (String) proxy.getDataProtectionService()
                    .executePluginService(sessionToken1, pluginId,
                            new Object[] {key},
                            new HashMap());
        }catch (Exception e) {
            log.error("获取密管密码异常", e);
        }
        return msg;
    }

    /**
     * 检出
     * @param result 对象
     * @param key 密钥
     * @param hour 使用时长
     * @return 是否成功
     */
    public String checkout(AuthResultTO result,String key,int hour) {
        proxy = factory.createProxy();
        Map<String, String> params = new HashMap<String, String>();
        params.put("realmId", realmid); // ntlm
        UCMCredential template = new UCMCredential();
        UCMCheckoutEntry[] entries = new UCMCheckoutEntry[]{};
        template.setId("");
        template.setCheckoutEntries(entries);
        template.setPassword("");
        template.setUserId("");
        template.setRootCredential();
        try{
            proxy.getUCMService().checkout(result.getSession().getSessionToken(), "",
                    key, "req1", hour, template, params); // 3 hours
        }catch(XmlRpcException e){
            String msg = e.getMessage();
            if(msg.indexOf("code=60052")>0){
                return "0001";//该账号已检出
            }
            return "error";
        }
        return "success";
    }
}   
