package com.work.ppm.application.services.rdmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.rdmanager.SystemListDto;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public interface IPpmOperatorKeyService {
    /**
     * 分页查询
     *
     * @param pager
     * @param user
     */
    void queryPager(Pager pager, CommOperatorDoLoginDto user);

    /**
     * 判断是否首次进入密码管理模块
     *
     * @param user
     * @return Long
     */
    CommOperator firstEnter(CommOperatorDoLoginDto user);

    /**
     * 设置二次密码
     *
     * @param request
     * @param verificationCode
     * @param confirmPassWord
     */
    void setPassword(HttpServletRequest request, String verificationCode, String confirmPassWord);

    /**
     * 二次密码、验证码校验
     *
     * @param request
     * @param verificationCode
     * @param passWord
     */
    void verifyCodeAndPassword(HttpServletRequest request, String verificationCode, String passWord);

    /**
     * 修改二次密码
     *
     * @param verificationCode
     * @param confirmPassword
     * @param newPassword
     * @param password
     * @param request
     */

    void changePassword(String verificationCode, String confirmPassword, String newPassword,
                        String password, HttpServletRequest request);

    /**
     * 第一次ecdhe
     *
     * @param body
     * @param request
     */
    Map<String, Object> ecdhefirst(Map<String, String> body, HttpServletRequest request) throws Exception;

    /**
     * 删除系统密码
     *
     * @param body
     */
    void deleteSysList(ArrayList<String> body);

    /**
     * 第一次添加ecdhe
     *
     * @param body
     * @param request
     * @return
     */
    Object ecdheAddfirst(Map<String, String> body, HttpServletRequest request);

    /**
     * 添加系统密码
     *
     * @param body
     * @param request
     */
    void addSysPsd(Map<String, String> body, HttpServletRequest request) throws Exception;

    /**
     * 修改系统密码
     *
     * @param body
     * @param request
     */
    void updataSysPsd(Map<String, String> body, HttpServletRequest request) throws Exception;

    /**
     * 获取验证码
     * @param request
     * @return
     */
    ApiResult<Object> getCode(HttpServletRequest request);

    /**
     * 系统分页查询
     * @param pager
     * @param systemListDto
     */
    void queryAllPager(Pager pager, SystemListDto systemListDto);

    /**
     * 重置密码
     * @param systemListDto
     * @param request
     */
    void resetUserScodPwd(SystemListDto systemListDto, HttpServletRequest request);

    /**
     * 关闭验证码
     * @param request
     */
    void clossverifyCode(HttpServletRequest request);

    /**
     * 验证码信息
     * @param request
     * @return
     */
    Object selectverifyCode(HttpServletRequest request);

}
