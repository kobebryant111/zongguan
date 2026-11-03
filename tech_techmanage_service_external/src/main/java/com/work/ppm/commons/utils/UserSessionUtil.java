package com.work.ppm.commons.utils;

import com.work.ppm.application.api.basismanager.PpmMenuLogApi;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.BaseApi;

/**
 * 获取登录对象
 *
 */
public class UserSessionUtil {
    /**
     * 1 我的  2 团队 3 辖内
     * @return boolean
     */
    public static int queryRange(){
        int result = 1;
        String teamRoles = DictUtil.getDictName("QUERY_RANGE","TEAM");

        String mgrRoles = DictUtil.getDictName("QUERY_RANGE","MGR");
        CommOperatorDoLoginDto dto = getCurrentUser();
        if(dto.getRoles().stream().anyMatch(role->teamRoles.contains(","+role.getRoleId()+","))){
            result = 2;
        }
        if(dto.getRoles().stream().anyMatch(role->mgrRoles.contains(","+role.getRoleId()+","))){
            result = 3;
        }
        return result;
    }

    /**
     * 是否查询辖内
     * @return boolean
     */
    public static boolean isQueryMgr(){
        boolean result = false;
        if(queryRange()==3){
            result = true;
        }
        return result;
    }

    /**
     * 是否查询团队
     * @return boolean
     */
    public static boolean isQueryTeam(){
        boolean result = false;
        if(queryRange()==2){
            result = true;
        }
        return result;
    }


    /**
     * 登录用户对象
     * @return CommOperatorDoLoginDto
     */
    public static CommOperatorDoLoginDto getCurrentUser (boolean isThrow){
//        Subject subject = SecurityUtils.getSubject();
//        if(subject==null){
//            if(isThrow){
//                AssertUtil.notNull(subject,"未登录");
//            }else{
//                return null;
//            }
//        }
//        CommOperatorDoLoginDto result = (CommOperatorDoLoginDto) subject.getPrincipal();
        CommOperatorDoLoginDto result = null;
        BaseApi baseApi = SpringUtil.getBean(PpmMenuLogApi.class);
        result = baseApi.getCurrentUser( RequestUtil.getHttpServletRequest());
        AssertUtil.notNull(result,"未登录");
        return result;
    }

    /**
     * 登录用户对象
     * @return CommOperatorDoLoginDto
     */
    public static CommOperatorDoLoginDto getCurrentUser (){
        return UserSessionUtil.getCurrentUser(false);
    }
}
