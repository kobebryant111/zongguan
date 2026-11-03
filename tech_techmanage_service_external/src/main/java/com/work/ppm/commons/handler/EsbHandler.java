package com.work.ppm.commons.handler;


import com.work.ppm.application.dto.esb.EsbResultMassage;
import com.work.ppm.commons.annotaion.esb.EsbBody;
import com.work.ppm.commons.annotaion.esb.EsbConfig;
import com.work.ppm.commons.annotaion.esb.EsbProxy;
import com.work.ppm.commons.utils.EsbUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * esb服务实现类
 *
 */
public class EsbHandler implements InvocationHandler {
    /**
     * boolean
     **/
    public static final String BASE_BOOLEAN_STR = "boolean";
    /**
     * esb实现类型
     * @param proxy
     * @param method
     * @param args
     * @return T
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EsbConfig esbConfig = method.getAnnotation(EsbConfig.class);
        EsbProxy esbProxy = method.getAnnotation(EsbProxy.class);
        EsbBody esbBody = method.getAnnotation(EsbBody.class);

        Class resultType = method.getReturnType();
        Object result = null;
        Object arg = args!=null&&args.length>0?args[0]:null;
        //返回空类型
        if(Void.class.isAssignableFrom(resultType)||"void".equals(resultType.getSimpleName())){
            EsbUtil.sendCommEsbCheckResult(arg,esbConfig,esbProxy);
        }else if(EsbResultMassage.class.isAssignableFrom(resultType)){
            result = EsbUtil.sendCommEsbAllResult(arg, resultType,esbConfig,esbProxy);
        }else if(Boolean.class.isAssignableFrom(resultType.getClass())||
                BASE_BOOLEAN_STR.equals(resultType.getName())){
            result = EsbUtil.sendCommEsbBlResult(arg,esbConfig,esbProxy);
        }else{
            if(esbBody!=null){
                result = EsbUtil.sendCommEsbResultBody(arg,resultType,esbConfig,esbProxy);
            }else{
                result = EsbUtil.sendCommEsbAllResult(arg, resultType,esbConfig,esbProxy);
            }
        }
        return result;
    }

}

