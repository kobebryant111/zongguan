package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.resourceplat.IaasResourceDto;
import com.work.ppm.application.dto.resourceplat.ResAddedExtDto;
import com.work.ppm.application.model.resourceplat.AddedItemExtVo;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedExt;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * iaas库
 *
 */
public interface IResAddedExtService {

    /**
     * 分页查询
     * @param modelData 对象
     * @param pager       分页对象
     */
    void queryPager(ResAddedExtDto modelData, Pager pager);

    /**
     * 分页
     *
     * @param modelData 对象
     * @param pager       分页对象
     */
    void queryIaasResourcePager(IaasResourceDto modelData, Pager pager);

    /**
     * 分页
     *
     * @param modelData 对象
     * @return List<AddedItemExtVo>
     */
    List<AddedItemExtVo> queryIaasResourceOther(IaasResourceDto modelData);

    /**
     * 列表查询
     * @param modelData
     * @return List<ResAddedExt>
     */
    List<ResAddedExt> queryList(ResAddedExtDto modelData);

    /**
     * 保存/更新
     * @param model 对象
     */
    void save(ResAddedExt model);

    /**
     * 保存/更新
     * @param modelData
     */
    void save(ResAddedExtDto modelData);

    /**
     * 删除
     * @param modelData
     */
    void delete(ResAddedExtDto modelData);

    /**
     * 获取资源新增：详细信息
     * @param id
     * @return List<ResAdded>
     */
    List<ResAdded> queryResAddedConfig(String id);

    /**
     * 通过接口更新iaas中的相关数据
     * @param resAddedExt
     */
    void updateByInterface(ResAddedExt resAddedExt);

    /**
     * 根据系统查询未使用的资源新增的子表配置
     * @param id 系统ID
     * @return List<ResAddedItem> 未使用的资源数据
     */
    List<ResAddedItem> queryConfigListBySystem(String id);
}
