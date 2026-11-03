package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityExcelDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 闸机权限管理 接口描述
 *
 */
public interface IPpmZjAuthorityService {

    /**
     * 分页
     *
     * @param ppmZjAuthorityDto 对象
     * @param pager             分页对象
     */
    void queryPager(PpmZjAuthorityDto ppmZjAuthorityDto, Pager pager,CommOperatorDoLoginDto user);

    /**
     * 添加
     *
     * @param ppmZjAuthorityDto 对象
     * @return int
     */
    int addPpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * 查询列表
     *
     * @param ppmZjAuthorityDto 对象
     * @return PpmZjAuthorityDto
     */
    List<PpmZjAuthorityDto> queryList(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * 查询列表
     *
     * @param ppmZjAuthorityDto 对象
     * @return PpmZjAuthorityDto
     */
    List<PpmZjAuthorityDto> queryListOther(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * 查询详情
     *
     * @param ppmZjAuthorityDto 对象
     * @return PpmZjAuthorityDto
     */
    PpmZjAuthorityDto queryDetail(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * 查询详情
     *
     * @param ppmZjAuthorityDto 对象
     * @return PpmZjAuthorityDto
     */
    PpmZjAuthorityDto queryByZjAuth(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * 更新
     *
     * @param ppmZjAuthorityDto 对象
     * @return int
     */
    int updatePpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * 删除
     *
     * @param ppmZjAuthorityDto 对象
     * @return int
     */
    int deletePpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto);

    /**
     * Excel数据导出
     *
     * @param ppmZjAuthorityDto
     * @param user
     * @return
     */
    List<PpmZjAuthorityExcelDto> exportPpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto, CommOperatorDoLoginDto user);

    /**
     * 检测重复权限
     * <p>
     * 重复返回true,反之false
     * </p>
     *
     * @param certificateNumber 证件号
     * @param ppmZjId           闸机号
     */
    boolean checkRepeatAuth(String certificateNumber, String ppmZjId);

    /**
     * 清理权限过期数据
     */
    void cleanExpireData();

    /**
     * 根据身份证清理权限过期数据
     *
     * @param certificateNumbers
     */
    void cleanExpireData(List<String> certificateNumbers);

    /**
     * 批量添加闸机权限
     *
     * @param applyDto
     */
    void addPpmZjAuthorityInfo(PpmZjAuthorityApplyDto applyDto);

    /**
     * 同步人员权限到闸机系统
     *
     * @param authorities
     */
    void addAuthToZjServer(List<PpmZjAuthorityDto> authorities);

    /**
     * 同步人员权限到闸机系统
     *
     * @param authorities
     */
    void delAuthToZjServer(List<PpmZjAuthorityDto> authorities);
}