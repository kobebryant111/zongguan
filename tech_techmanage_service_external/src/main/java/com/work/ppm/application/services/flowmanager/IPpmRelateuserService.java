package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmRelateuserDto;
import com.work.ppm.commons.pager.Pager;

/**
 * 外包人员账户关联
 */
public interface IPpmRelateuserService {

    /**
     * 分页
     * @param ppmRelateuser 对象
     * @param pager 分页对象
     */
    void queryPager(PpmRelateuserDto ppmRelateuser, Pager pager) ;

    /**
     * 添加
     * @param ppmRelateuser 对象
     * @return int
     */
    int addPpmRelateuser(PpmRelateuserDto ppmRelateuser) ;

    /**
     * 查询详情
     * @param ppmRelateuser 对象
     * @return PpmRelateuser
     */
    PpmRelateuserDto queryDetail(PpmRelateuserDto ppmRelateuser) ;

    /**
     * 更新
     * @param ppmRelateuser 对象
     * @return Integer 1:更新成功，2：添加成功
     */
    Integer updatePpmRelateuser(PpmRelateuserDto ppmRelateuser) ;

    /**
     * 删除
     * @param ppmRelateuser 对象
     * @return Integer
     */
    Integer deletePpmRelateuser(PpmRelateuserDto ppmRelateuser) ;

    /**
     * 查询信息
     * @param username 登录人工号
     * @return PpmRelateuserDto
     */
    PpmRelateuserDto queryUserInfoByUserId(String username);
}
