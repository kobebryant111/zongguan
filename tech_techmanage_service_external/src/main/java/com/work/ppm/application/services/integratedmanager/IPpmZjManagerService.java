package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmZjManagerDto;
import com.work.ppm.commons.artemis.response.DoorResp;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 闸机管理 接口描述
 *
 */
public interface IPpmZjManagerService {

    /**
     * 分页
     *
     * @param ppmZjManagerDto 对象
     * @param pager           分页对象
     */
    void queryPager(PpmZjManagerDto ppmZjManagerDto, Pager pager);

    /**
     * 添加
     *
     * @param ppmZjManagerDto 对象
     * @return int
     */
    int addPpmZjManager(PpmZjManagerDto ppmZjManagerDto);

    /**
     * 查询详情
     *
     * @param ppmZjManagerDto 对象
     * @return PpmZjManagerDto
     */
    PpmZjManagerDto queryDetail(PpmZjManagerDto ppmZjManagerDto);

    /**
     * 更新
     *
     * @param ppmZjManagerDto 对象
     * @return int
     */
    int updatePpmZjManager(PpmZjManagerDto ppmZjManagerDto);

    /**
     * 删除
     *
     * @param ppmZjManagerDto 对象
     * @return int
     */
    int deletePpmZjManager(PpmZjManagerDto ppmZjManagerDto);

    /**
     * 条件查询
     *
     * @param ppmZjManagerDto
     * @return
     */
    List<PpmZjManagerDto> queryList(PpmZjManagerDto ppmZjManagerDto);

    /**
     * 同步闸机数据
     *
     * @param result
     */
    void syncDoors(List<DoorResp> result);
}
