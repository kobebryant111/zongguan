package com.work.ppm.application.services.resourceplat;


import com.work.ppm.application.dto.resourceplat.ResExpandDto;
import com.work.ppm.application.model.resourceplat.ResExpandItem;
import com.work.ppm.application.model.resourceplat.ResExpandItemParts;
import com.work.ppm.commons.ApiResult;

import java.util.List;
import java.util.Map;

/**
 * 资源扩容业务处理
 */
public interface IResExpandService {

    /**
     * 新资源扩容项
     *
     * @param resExpandItem
     */
    void insertResourceExpandItem(ResExpandItem resExpandItem);
    /**
     * 新资源扩容项配置
     *
     * @param resExpandItemParts
     */
    void insertResourceExpandItemParts(ResExpandItemParts resExpandItemParts);

    /**
     * 根据资源申请ID，删除资源配置项数据
     *
     * @param expandId
     */
    void deleteResourceExpandItem(String expandId);
    /**
     * 根据资源申请ID，删除资源配置项配置数据
     *
     * @param expandId
     */
    void deleteResourceExpandParts(String expandId);

    /**
     * 根据资源申请ID，查询资源配置项数据
     * @param expandId
     * @return List<ResExpandItem>
     */
    List<ResExpandItem> queryResourceExpandItem(String expandId);

    /**
     * 根据资源配置项ID，查询资源配置项配置数据
     * @param expandItemId
     * @return List<ResExpandItemParts>
     */
    List<ResExpandItemParts> queryResourceExpandItemParts(String expandItemId);

    /**
     * 资源申请新增
     * @param resExpandDto
     * @return true/false
     */
    boolean insertResourceExpand(ResExpandDto resExpandDto);

    /**
     * 资源申请删除
     * @param id
     */
    void deleteResourceExpand(String id);
    /**
     * 资源申请提交
     * @param resExpandDto
     * @return String
     */
    String submitResourceExpand(ResExpandDto resExpandDto);

    /**
     * 资源扩容编辑初始化
     * @param id
     * @return ResExpandDto
     */
    ResExpandDto queryResourceExpand(String id);

    /**
     * 配置资源查询
     * @param selectType 1物理机资源2虚拟机资源
     * @param sysOperLevel 系统运维等级
     * @param isFundTrans 是否涉及资金交易
     * @param ids 已选配件
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryConfgiItemList(String selectType,String sysOperLevel,String isFundTrans,String ids);

    /**
     * 配置应用系统ID，获取资源列表
     * @param ciid 应用系统标识
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryCmdbResourceList(String ciid);
    /**
     * 根据虚拟机的CIID，获取对应物理机信息
     * @param ciid 虚拟机的CIID
     * @return Map<String,Object>
     */
    Map<String,Object> queryCmdbRelateResourceList(String ciid);
    /**
     * 根据SAN资源，获取总的价格信息
     * @param id SAN资源ID
     * @param  space 总的空间GB
     * @return Map<String,Object>
     */
    Map<String,Object> querySanInfo(String id,String space);

    /**
     * 根据主表ID查询已作废的数据
     * @param resExpandDto 主表
     * @return ApiResult<String> 作废信息
     */
    ApiResult<String> validOutOfDateMain(ResExpandDto resExpandDto);
}
