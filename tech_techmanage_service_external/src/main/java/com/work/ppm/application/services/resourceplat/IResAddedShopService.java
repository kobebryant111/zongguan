package com.work.ppm.application.services.resourceplat;


import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.commons.ApiResult;

import java.util.List;

/**
 * service
 * @date 2021/09/03
 */
public interface IResAddedShopService {

    /**
     * 加入购物车保存数据
     * @param resAddedDto 主表Dto
     * @param resAddedItemDto 子表Dto
     * @return 菜单列表
     */
    ApiResult<List> saveResGood(ResAddedDto resAddedDto, ResAddedItemDto resAddedItemDto);

    /**
     * 根据Item表的ID查询Item表详细
     * @param id ite表ID
     * @return ResAddedItem对象
     */
    ResAddedItem showItemDetailById(String id);

    /**
     * 根据addedItemId删除Item表及parts,version表的中的数据
     * @param addedItemId Item表Id
     */
    void deleteItemByItemId(String addedItemId);

    /**
     * 根据addedItemId查询Item表及parts,version表的中的数据
     * @param addedItemId Item表Id
     * @return List<ResAddedItem> 购物车列表
     */
    List<ResAddedItem> getDynamicForms(String addedItemId);

    /**
     * 保存主表数据
     * @param resAddedDto
     * @return ResAdded 返回对象
     */
    ResAdded saveResMain(ResAddedDto resAddedDto);

    /**
     * 根据主表ID校验数据中作废的配置信息
     * @param id 主表ID
     * @return String 错误信息
     */
    ApiResult<String> validOutofDateMain(String id);

    /**
     * 【资源新增重新提交用】根据页面数据校验数据中作废的配置信息
     * @param resAddedDto 主表Dto
     * @return ApiResult<String> 错误信息
     */
    ApiResult<String> validOutofDateMainResubmit(ResAddedDto resAddedDto);
}
