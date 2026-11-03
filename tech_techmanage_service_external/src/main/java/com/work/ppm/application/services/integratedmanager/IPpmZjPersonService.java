package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmZjPersonDto;
import com.work.ppm.commons.artemis.response.PersonResp;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 闸机人员信息 接口描述
 *
 */
public interface IPpmZjPersonService {
    /**
     * 过滤筛选
     *
     * @param ppmZjPersonDto
     * @return
     */
    List<PpmZjPersonDto> queryList(PpmZjPersonDto ppmZjPersonDto);

    /**
     * 分页
     *
     * @param ppmZjPersonDto 对象
     * @param pager          分页对象
     */
    void queryPager(PpmZjPersonDto ppmZjPersonDto, Pager pager);

    /**
     * 添加
     *
     * @param ppmZjPersonDto 对象
     * @return int
     */
    int addPpmZjPerson(PpmZjPersonDto ppmZjPersonDto);

    /**
     * 查询详情
     *
     * @param ppmZjPersonDto 对象
     * @return PpmZjPersonDto
     */
    PpmZjPersonDto queryDetail(PpmZjPersonDto ppmZjPersonDto);

    /**
     * 更新
     *
     * @param ppmZjPersonDto 对象
     * @return int
     */
    int updatePpmZjPerson(PpmZjPersonDto ppmZjPersonDto);

    /**
     * 删除
     *
     * @param ppmZjPersonDto 对象
     * @return int
     */
    int deletePpmZjPerson(PpmZjPersonDto ppmZjPersonDto);

    /**
     * 检测闸机人员是否存在
     *
     * @param certificateNo
     * @return
     */
    boolean checkExitsPerson(String certificateNo);

    /**
     * 根据身份证号取人员编号
     *
     * @param certificateNo
     * @return
     */
    String getPersonId(String certificateNo);

    /**
     * 人员信息同步
     *
     * @param result
     */
    void syncPerson(List<PersonResp> result);
}
