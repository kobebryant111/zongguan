package com.work.ppm.application.services.datamanager;

import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
  * 密管设备
  */
public interface IPpmPwdShebeiService {

    /**
     * 分页
     * @param ppmPwdShebeiDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmPwdShebeiDto ppmPwdShebeiDto, Pager pager) ;

    /**
     * 根据流程id 查询
     * @param instanceid 流程id
     * @return list
     */
    List<PpmPwdShebeiDto> getList(String instanceid);

    /**
     * 添加设备
     * @param pwdList  集合
     * @param instanceid 流程id
     * @return int 音响行数
     *
     */
    int addSheBei(List<PpmPwdShebeiDto> pwdList, String instanceid);
    /**
     * 添加设备
     * @param ppmPwdShebeiDto  集合
     * @return int 音响行数
     *
     */
    int addSheBei(PpmPwdShebeiDto ppmPwdShebeiDto);

    /**
     * 删除设备
     * @param id 自增编号
     * @return int
     *
     */
    int deleteShebei(Long id);

    /**
     * 删除设备
     * @param instanceid 流程编号
     * @return int
     *
     */
    int deleteShebei(String instanceid);

    /**
     * 获取详情
     * @param ii 对象
     * @return PpmPwdShebeiDto
     */
    PpmPwdShebeiDto getSheBei(PpmPwdShebeiDto ii);

    /**
     * 更新设备
     * @param ppmPwdShebeiDto 对象
     * @return 影响行数
     */
    int updatePwdShebei(PpmPwdShebeiDto ppmPwdShebeiDto);

    /**
     * 获取设备的结束时间
     * @param ppmPwdShebeiDto 对象
     * @return PpmPwdShebeiDto
     */
    PpmPwdShebeiDto querySheBeiEndTime(PpmPwdShebeiDto ppmPwdShebeiDto);

    /**
     * 查询设备密码列表
     * @param ppmPwdShebeiDto 对象
     * @return list
     */
    List<PpmPwdShebeiDto> getPwdList(PpmPwdShebeiDto ppmPwdShebeiDto);
}
