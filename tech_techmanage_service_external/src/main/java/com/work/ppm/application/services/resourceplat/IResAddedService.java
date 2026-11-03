package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemPartsDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemVersionDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
/**
 * service
 * @date 2021/09/03
 */
public interface IResAddedService {

    /**
     * 分页
     *
     * @param resAddedDto 对象
     * @param pager       分页对象
     */
    void queryPager(ResAddedDto resAddedDto, Pager pager);

    /**
     * 删除资源新增配置
     *
     * @param resAddedDto
     */
    void deleteResourceAdded(ResAddedDto resAddedDto);

    /**
     * 根据资源新增删除所有Item表 parts表 version表数据
     * @param id 资源新增Id
     */
    void deleteConfigItem(String id);

    /**
     * 查询关联系统树
     *
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryRelaSysTree();

    /**
     * 查询关联系统信息
     *
     * @param pcode
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> queryRelaSysData(String pcode);

    /**
     * 查询网络申请的网络位置
     *
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> queryIpApplyNetAddress();

    /**
     * 查询关联项目树
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryRelaProgramTree();

    /**
     * 获取预先配置好的选择情况
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> querySelectionConfig();

    /**
     * 获取配件关联关系表
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryHardWareRela();

    /**
     * 获取软件关联关系表
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> querySoftWareRela();

    /**
     * 根据条件获取套餐列表
     * @param comboType
     * @param nodeType
     * @return ApiResult<List>
     */
    ApiResult<List> queryRelaComboList(String comboType, String nodeType);

    /**
     * 发起资源申请
     * @param resAddedDto
     * @return processInfo
     */
    String submitResourceAdded(ResAddedDto resAddedDto);

    /**
     * 保存资源申请草稿
     * @param resAddedDto
     * @return true/false
     */
    boolean addResource(ResAddedDto resAddedDto);

    /**
     * 查询已经存在的资源
     * @param resAddedDto
     * @return ResAddedDto
     */
    ResAddedDto queryResourceRepeat(ResAddedDto resAddedDto);

    /**
     * 查询已经存在的资源
     * @param resAddedDto
     * @return ResAddedDto
     */
    ResAddedDto queryResourceRepeatShop(ResAddedDto resAddedDto);

    /**
     * 重新提交流程
     * @param flowDto
     * @param resAddedDto
     * @return true/false
     */
    boolean reSubmitWorkFlow(FlowDto flowDto,ResAddedDto resAddedDto);

    /**
     * 获取存储套餐
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryStorageComboData();

    /**
     * 获取需要通知的资源列表
     * @param noticeDateStr -- 当天需要通知的预计上线日期，格式：yyyy-MM-dd
     * @return List<ResAdded>
     */
    List<ResAdded> findListByNotice(String noticeDateStr);

    /**
     * 根据资源配置ID查询资源的详细配置
     * @param id
     * @return ResAdded
     */
    ResAdded queryResAddedConfig(String id);

    /**
     * 删除之后重新入库
     * @param id
     */
    void delConfigBeforeEdit(String id);

    /**
     * 获取补录数据
     *
     * @param resAddedDto 对象
     * @param pager  分页对象
     */
    void queryAdditionalList(ResAddedDto resAddedDto, Pager pager);

    /**
     * 流转完成的数据迁移到报表
     * @param id
     */
    void addDataTransToReport(String id);

    /**
     * 导出报表
     * @param id
     * @param response
     */
    void exportZb(String id,HttpServletResponse response);

    /**
     * 终止流程且删除业务数据
     * @param resAddedDto
     * @param userId
     */
    void  deleteProcessInstance(ResAddedDto resAddedDto,String userId);

    /**
     * resAddedDto 转 resAdded
     * @param resAdded
     * @param resAddedDto
     */
    void geterateToResAdd(ResAdded resAdded, ResAddedDto resAddedDto);

    /**
     * ResAddedItemDto 转 resAddedItem
     * @param resAddedItem
     * @param resAddedItemDto
     */
    void geterateToResAddItem(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto);

    /**
     * 保存资源新增配件信息数据
     * @param resAddedItemPartsDtoList 资源新增配件列表
     * @param addedId                  资源新增主表ID
     * @param addedItemId              资源新增主表对象
     */
    public void addResItemParts(
            List<ResAddedItemPartsDto> resAddedItemPartsDtoList,
            String addedId,
            String addedItemId);

    /**
     * 保存资源新增软件版本数据
     * @param resAddedItemVersionDtoList 资源新增软件列表
     * @param addedId                    资源新增主表ID
     * @param addedItemId                资源新增主表对象
     */
    public void addResItemVersion(
            List<ResAddedItemVersionDto> resAddedItemVersionDtoList,
            String addedId,
            String addedItemId);

    /**
     * 计算每一个配置卡片的总价
     *
     * @param sysOperLevel    系统运维等级
     * @param isFundTrans     是否涉及资金交易
     * @param resAddedItemDto 资源申请子表Dto
     * @return resAddedItemDto 验算过没一个配置卡片价格的dto
     */
    public ResAddedItemDto getEveryCardPrice(
            String sysOperLevel,
            String isFundTrans,
            ResAddedItemDto resAddedItemDto);
}
