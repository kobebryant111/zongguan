package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemPartsDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemVersionDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.application.model.resourceplat.ResAddedItemParts;
import com.work.ppm.application.model.resourceplat.ResAddedItemVersion;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResAddedShopService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.dao.impl.MyBatisDao;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * service
 *
 * @date 2021/09/03
 */
@Slf4j
@Service("ResAddedShopService")
public class ResAddedShopService extends BaseDaoSupper implements IResAddedShopService {

    /**
     * RESADDED
     */
    private static String RESADDED = "com.work.ppm.application.model.resourceplat.ResAdded";
    /**
     * EOSDICTENTRY
     */
    private static String EOSDICTENTRY = "com.work.ppm.application.model.systemmanager.EosDictEntry";
    /**
     * resAddedService
     */
    @Autowired
    private ResAddedService resAddedService;
    /**
     * myBatisDao
     */
    @Autowired
    private MyBatisDao myBatisDao;

    /**
     * 加入购物车保存数据
     *
     * @param resAddedDto     主表Dto
     * @param resAddedItemDto 子表Dto
     * @return 菜单列表
     */
    @Override
    public ApiResult<List> saveResGood(ResAddedDto resAddedDto, ResAddedItemDto resAddedItemDto) {
        ApiResult<List> result = new ApiResult<>();
        //判断是否重复，没有就新增，有就先删除再增加
        ResAdded resAdded = saveResMain(resAddedDto);

        //2.保存item表数据
        saveResItem(resAddedItemDto, resAdded);
        //3.查询item表菜单数据
        List<ResAddedItem> dynamicForms = getDynamicForms(resAdded.getId());

        result.setResults(dynamicForms);
        //4.无论保存是否返回主表ID
        result.setMessage(resAdded.getId());
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 保存主表信息
     *
     * @param resAddedDto
     * @return
     */
    @Override
    public ResAdded saveResMain(ResAddedDto resAddedDto) {
        resAddedDto = resAddedService.queryResourceRepeatShop(resAddedDto);
        ResAdded resAdded = new ResAdded();
        resAddedDto.setStatus("1");//状态设置为1 新建
        resAddedService.geterateToResAdd(resAdded, resAddedDto);
        myBatisDao.executionPojo(RESADDED, resAdded, IMyBatisDao.SQL.INSTER, "addResource");
        return resAdded;
    }

    /**
     * 保存单个子表的数据
     *
     * @param resAddedItemDto 子表Dto
     * @param resAdded        主表对象
     */
    private void saveResItem(ResAddedItemDto resAddedItemDto, ResAdded resAdded) {
        String addedItemId = resAddedItemDto.getId();

        if (StringUtils.isNotEmpty(resAddedItemDto.getId())) {
            //先删除addedItemId的关联数据
            deleteItemByItemId(addedItemId);
        } else {
            addedItemId = UUIDUtils.generateUUID();
            resAddedItemDto.setId(addedItemId);
            resAddedItemDto.setAddedId(resAdded.getId());
        }
        ResAddedItem resAddedItem = new ResAddedItem();
        //加入购物车前验算价格
        resAddedItemDto = resAddedService.getEveryCardPrice(resAdded.getSysOperLevel(),
                resAdded.getIsFundTrans(), resAddedItemDto);

        resAddedService.geterateToResAddItem(resAddedItem, resAddedItemDto);

        myBatisDao.executionPojo(RESADDED, resAddedItem, IMyBatisDao.SQL.INSTER, "addResItem");

        List<ResAddedItemPartsDto> resAddedItemPartsDtoList = resAddedItemDto.getHardWareData();
        resAddedService.addResItemParts(resAddedItemPartsDtoList, resAdded.getId(), addedItemId);

        List<ResAddedItemVersionDto> resAddedItemVersionDtoList = resAddedItemDto.getSoftWareData();
        resAddedService.addResItemVersion(resAddedItemVersionDtoList, resAdded.getId(), addedItemId);
    }

    /**
     * 根据Item表的ID查询Item表详细
     *
     * @param id item表ID
     * @return ResAddedItem对象
     */
    @Override
    public ResAddedItem showItemDetailById(String id) {
        ResAddedItem resAddedItem = myBatisDao.selectClass(RESADDED, id, "showItemDetailById");
        List<ResAddedItemParts> hardWareData = myBatisDao.selectListT
                (RESADDED, resAddedItem.getId(), "queryResItemParts");
        List<ResAddedItemVersion> softWareData = myBatisDao.selectListT
                (RESADDED, resAddedItem.getId(), "queryResItemVersion");
        resAddedItem.setHardWareData(hardWareData);
        resAddedItem.setSoftWareData(softWareData);
        return resAddedItem;
    }

    /**
     * 根据addedItemId删除Item表及parts,version表的中的数据
     *
     * @param addedItemId 子表ID
     */
    @Override
    public void deleteItemByItemId(String addedItemId) {
        myBatisDao.executionPojo(RESADDED, addedItemId, IMyBatisDao.SQL.DELETE, "deleteItemByItemId");
        myBatisDao.executionPojo(RESADDED, addedItemId, IMyBatisDao.SQL.DELETE, "deletePartsByItemId");
        myBatisDao.executionPojo(RESADDED, addedItemId, IMyBatisDao.SQL.DELETE, "deleteVersionByItemId");
    }

    /**
     * 根据addedItemId查询Item表及parts,version表的中的数据
     *
     * @param addedItemId 子表ID
     * @return
     */
    @Override
    public List<ResAddedItem> getDynamicForms(String addedItemId) {
        return myBatisDao.selectListT(RESADDED, addedItemId, "queryResItem");
    }

    /**
     * 根据id查询对应的字典项
     * @param dictid 字段ID
     * @param dicttypeid 字典名称
     * @return EosDictEntry 字典实体
     */
    private EosDictEntry getEosDictEntry(String dictid,String dicttypeid) {
        EosDictEntry eosDictEntry = new EosDictEntry();
        eosDictEntry.setDictid(dictid);
        eosDictEntry.setDicttypeid(dicttypeid);
        eosDictEntry = myBatisDao.selectClass(EOSDICTENTRY, eosDictEntry, "queryDetail");
        return eosDictEntry;
    }

    /**
     * 【资源新增重新提交用】根据页面数据校验数据中作废的配置信息
     * @param resAddedDto 主表Dto
     * @return ApiResult<String>
     */
    @Override
    public ApiResult<String> validOutofDateMainResubmit(ResAddedDto resAddedDto) {
        ApiResult<String> result = new ApiResult<>();
        StringBuilder validMessage = new StringBuilder("");
        List<ResAddedItemDto> list = resAddedDto.getDynamicForms();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                ResAddedItemDto resAddedItemDto = list.get(i);
                validOutofDateDataResubmit(validMessage, resAddedItemDto);
            }
        }
        if("".equals(validMessage.toString())){
            result.setStatus(ApiCode.SUCCESS);
        }else{
            result.setStatus(ApiCode.FAIL);
            result.setMessage(validMessage.toString());
        }
        return result;
    }

    /**
     * 【资源新增重新提交用】根据资源子表ID查询已经作废的资源描述
     * @param validMessage 校验信息
     * @param resAddedItemDto 参数实体
     * @return StringBuilder 错误 信息
     */
    private StringBuilder validOutofDateDataResubmit(StringBuilder validMessage, ResAddedItemDto resAddedItemDto) {
        EosDictEntry eosDictEntry = getEosDictEntry(resAddedItemDto.getNodeType(),"RES_NODETYPE");
        String nodeTypeName = eosDictEntry.getDictname();
        eosDictEntry = getEosDictEntry(resAddedItemDto.getModelType(),"RES_MODELTYPE");
        String modelTypeName = eosDictEntry.getDictname();
        String name = nodeTypeName+"-"+modelTypeName+"-"+resAddedItemDto.getNodeName();

        StringBuilder message =validMessage;
        validMessage = new StringBuilder();

        if ("1".equals(resAddedItemDto.getIsStorage())){
            //1.SAN
            validMessage.append(getOutofDateInfo(resAddedItemDto.getSanId()));
            //2.归档SAN
            validMessage.append(getOutofDateInfo(resAddedItemDto.getSanSpecialId()));
        }
        //套餐
        validMessage.append(getOutofDateInfo(resAddedItemDto.getModelConfig()));
        //操作系统
        validMessage.append(getOutofDateInfo(resAddedItemDto.getOs()));
        //NAS
        if("1".equals(resAddedItemDto.getIsNas())){
            validMessage.append(getOutofDateInfo(resAddedItemDto.getModelType()));
        }
        //软件
        List<ResAddedItemVersionDto> resAddedItemVersionDtoList = resAddedItemDto.getSoftWareData();
        for (int i = 0;i<resAddedItemVersionDtoList.size();i++){
            ResAddedItemVersionDto resAddedItemVersionDto = resAddedItemVersionDtoList.get(i);
            if("1".equals(resAddedItemVersionDto.getType())){
                validMessage.append(getOutofDateInfo(resAddedItemVersionDto.getSoftId()));
            }
        }
        //硬件
        List<ResAddedItemPartsDto> resAddedItemPartsDtoList = resAddedItemDto.getHardWareData();
        for (int j = 0;j<resAddedItemPartsDtoList.size();j++){
            ResAddedItemPartsDto resAddedItemPartsDto = resAddedItemPartsDtoList.get(j);
            validMessage.append(getOutofDateInfo(resAddedItemPartsDto.getPartId()));
        }

        if(!"".equals(validMessage.toString())){
            message.append(name+":\n");
            message.append(validMessage);
        }
        validMessage = message;
        return validMessage;
    }

    /**
     * 根据配置ID查询配置的作废信息
     * @param id
     * @return 错误信息
     */
    private String getOutofDateInfo(String id){
        String description = "";
        Map<String, Object> map = myBatisDao.selectClass(RESADDED,
                id, "validOutofDateDataResubmit");
        if(map!=null){
            description = "\t"+map.get("DESCRIPTION")+"--已作废\n";
        }
        return description;
    }

    /**
     * 【资源新增发起用】根据主表ID校验数据中作废的配置信息
     *
     * @param id 主表ID
     * @return ApiResult<String>
     */
    @Override
    public ApiResult<String> validOutofDateMain(String id) {
        ApiResult<String> result = new ApiResult<>();
        StringBuilder validMessage = new StringBuilder("");
        List<Map<String, Object>> list = myBatisDao.selectListT(RESADDED, id, "validOutofDateMain");
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                boolean ishead = false;
                Map<String,Object> map = list.get(i);
                validOutofDateData(validMessage, map, ishead);
            }
        }
        if("".equals(validMessage.toString())){
            result.setStatus(ApiCode.SUCCESS);
        }else{
            result.setStatus(ApiCode.FAIL);
            result.setMessage(validMessage.toString());
        }
        return result;
    }

    /**
     * 【资源新增发起用】根据资源子表ID查询已经作废的资源描述
     * @param validMessage 校验信息
     * @param map 参数map
     * @return validMessage 赋值后的检验信息
     */
    private StringBuilder validOutofDateData(StringBuilder validMessage, Map<String, Object> map
    ,boolean ishead) {
        List<Map<String, Object>> childList = myBatisDao.selectListT(RESADDED,
                map.get("ID"), "validOutofDateData");
        if(!childList.isEmpty()){
            for (int j = 0;j < childList.size(); j++){
                if(!ishead){
                    validMessage.append(map.get("NAME")+":\n");
                }
                Map<String,Object> childMap = childList.get(j);
                validMessage.append("\t"+childMap.get("DESCRIPTION")+"--已作废\n");
                ishead = true;
            }
        }
        return validMessage;
    }


}
