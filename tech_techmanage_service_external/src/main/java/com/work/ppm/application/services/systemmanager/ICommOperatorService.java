package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.LoginDto;
import com.work.ppm.application.model.systemmanager.CommOperator;

import java.util.List;

/**
 * 员工登录
 */
public interface ICommOperatorService {

    /**
     * 更新用户密码
     * @param userId /
     * @param oldPwd /
     * @param newPwd /
     * @param confirmPwd /
     */
    void updateUserPassword(String userId, String oldPwd, String newPwd, String confirmPwd);
    /**
     * 查询记录清单
     * @param commOperator 对象
     */
    List<CommOperator> getList(CommOperator commOperator);
    /**
     * 分页
     * @param commOperator 对象
     * @param pager
     */
    void queryPager(CommOperator commOperator, Pager pager) ;

    /**
     * 添加
     * @param commOperator 对象
     * @return int
     */
    int addCommOperator(CommOperator commOperator) ;
    /**
     * 查询详情
     * @param commOperator 对象
     * @return CommOperator
     */
    @Deprecated
    CommOperator queryDetail(CommOperator commOperator) ;

    /**
     * 更新
     * @param commOperator 对象
     * @return Integer
     */
    @Deprecated
    Integer updateCommOperator(CommOperator commOperator) ;

    /**
     * 更新
     * @param commOperator 对象
     * @return Integer
     */
    Integer updateCommOperatorCus(CommOperator commOperator);

    /**
     * 登录
     * @param commOperator 对象
     * @return CommOperatorDoLoginDto
     */
    CommOperatorDoLoginDto doLogin(CommOperator commOperator) ;

    /**
     * 单点登录
     * @param loginDto 对象
     * @return ApiResult
     */
    ApiResult singleLogin(LoginDto loginDto) ;

    /**
     * 获取下一个节点用户id
     * @param roleid 角色id
     * @return String
     */
    String getNextRoleUser(String roleid) ;

    /**
     * 获取下一个节点用户id
     * @param roleid 角色id
     * @param type 数据返回类型 1：名称 + 工号，2：工号
     * @return String
     */
    String getNextRoleUser(String roleid,int type) ;

    /**
     * 角色用户格式化
     * @param roleId 角色id
     * @return 182420|182430@洪晨阳|孙嘉蕾
     */
    String getRoleUserFormat(String roleId);

    /**
     * 根据用户id 查询 用户名称
     * @param userId 用户id
     * @return String
     */
    String getUserNameByUserId(String userId) ;

    /**
     *  角色id查询工号
     * @param roleId 角色id
     * @return 工号
     */
    String getUserIdByRoleId(String roleId);

    /**
     *  角色id查询名称
     * @param roleId 角色id
     * @return 工号
     */
    String getUserNameByRoleId(String roleId);

    /**
     * 工号MD5加密
     * @param userid
     * @param ip
     * @return String
     */
    String getSecretKey(String userid,String ip);

    /**
     * 记录登陆时间
     */
    void updateLastlogin(String username);
}
