package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.resourceplat.IaasResourceDto;
import com.work.ppm.application.dto.resourceplat.ResAddedExtDto;
import com.work.ppm.application.model.resourceplat.AddedItemExtVo;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedExt;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.application.model.resourceplat.ResAddedItemParts;
import com.work.ppm.application.model.resourceplat.ResAddedItemVersion;
import com.work.ppm.application.services.resourceplat.IResAddedExtService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * IResAddedExtService
 * @date 2021/10/19
 */
@Service("ResAddedExtService")
@Slf4j
public class ResAddedExtService extends BaseDaoSupper implements IResAddedExtService {
    /**
     * ResAddedExt实体
     */
    private static String XML_PATH = "com.work.ppm.application.model.resourceplat.ResAddedExt";

    /**
     * myBatisDao
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 分页查询
     *
     * @param modelData 对象
     * @param pager       分页对象
     */
    @Override
    public void queryPager(ResAddedExtDto modelData, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResAddedExtDto.class);
        myBatisDao.selectPager(XML_PATH, map, pager, "queryList");
    }

    /**
     * 获取主表数据
     * @param modelData 对象
     * @param pager       分页对象
     */
    @Override
    public void queryIaasResourcePager(IaasResourceDto modelData, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, IaasResourceDto.class);
        myBatisDao.selectPager(XML_PATH, map, pager, "queryIaasResourceList");
    }

    @Override
    public List<AddedItemExtVo> queryIaasResourceOther(IaasResourceDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, IaasResourceDto.class);
        return myBatisDao.selectListT(XML_PATH, map, "queryIaasResourceListOther");
    }

    /**
     * 获取数据
     * @param id
     * @return ResAdded
     */
    public List<ResAdded> queryResAddedConfig(String id) {
        List<ResAdded> form = myBatisDao.selectListT(XML_PATH, id, "queryResource");
        for (int i = 0; i < form.size(); i++) {
            ResAdded resAdded = form.get(i);
            List<ResAddedItem> dynamicForms = myBatisDao.selectListT(XML_PATH,
                    resAdded.getId(), "queryResItem");
            for (int j = 0; j < dynamicForms.size(); j++) {
                ResAddedItem resAddedItem = dynamicForms.get(j);
                List<ResAddedItemParts> hardWareData = myBatisDao.selectListT(XML_PATH,
                        resAddedItem.getId(), "queryResItemParts");
                List<ResAddedItemVersion> softWareData = myBatisDao.selectListT(XML_PATH,
                        resAddedItem.getId(), "queryResItemVersion");
                resAddedItem.setHardWareData(hardWareData);
                resAddedItem.setSoftWareData(softWareData);
            }
            resAdded.setDynamicForms(dynamicForms);
        }
        return form;
    }

    /**
     * 列表查询
     *
     * @param modelData 对象
     */
    @Override
    public List<ResAddedExt> queryList(ResAddedExtDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResAddedExtDto.class);
        return myBatisDao.selectListT(XML_PATH, map, "queryList");
    }

    /**
     * 删除
     *
     * @param modelData
     */
    @Override
    public void delete(ResAddedExtDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResAddedExtDto.class);
        myBatisDao.executionPojo(XML_PATH, map, IMyBatisDao.SQL.DELETE, "delete");
    }

    /**
     * 保存/更新
     * @param modelData
     */
    @Override
    public void save(ResAddedExtDto modelData) {
        ResAddedExt model = new ResAddedExt();
        geterateToResAdd(model, modelData);
        save(model);
    }

    /**
     * 保存/更新
     * @param model
     */
    @Override
    public void save(ResAddedExt model) {
        if (StringUtils.isNotBlank(model.getId())) {
            myBatisDao.executionPojo(XML_PATH, model, IMyBatisDao.SQL.INSTER, "update");
        } else {
            model.setId(UUIDUtils.generateUUID());
            myBatisDao.executionPojo(XML_PATH, model, IMyBatisDao.SQL.INSTER, "add");
        }
    }

    /**
     * 转换对象
     * @param model
     * @param modelData
     */
    private void geterateToResAdd(ResAddedExt model, ResAddedExtDto modelData) {
        model.setId(modelData.getId());
        model.setAddedItemId(modelData.getAddedItemId());
        model.setMachineRoom(modelData.getMachineRoom());
        model.setCabinet(modelData.getCabinet());
        model.setCabinetPos(modelData.getCabinetPos());
        model.setSn(modelData.getSn());
        model.setF5ip(modelData.getF5ip());
        model.setIp(modelData.getIp());
        model.setServerIp(modelData.getServerIp());
        model.setScanIp(modelData.getScanIp());
        model.setVcsIp(modelData.getVcsIp());
        model.setVip(modelData.getVip());
        model.setVlan(modelData.getVlan());
        model.setGateway(modelData.getGateway());
        model.setMask(modelData.getMask());
        if (Objects.nonNull(modelData.getCreateTime())) {
            model.setCreateTime(DateUtil.dataFormat(modelData.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        }
        if (Objects.nonNull(modelData.getUpdateTime())) {
            model.setUpdateTime(DateUtil.dataFormat(modelData.getUpdateTime(), "yyyy-MM-dd hh:mm:ss"));
        }
    }
    /**
     * 转义流转完成的数据进入iaas库
     * @param id
     */
    public void setIassInfoOrget(String id){

        myBatisDao.executionPojo(XML_PATH, id,  IMyBatisDao.SQL.INSTER,"setIaasRsource");

        myBatisDao.executionPojo(XML_PATH, id,  IMyBatisDao.SQL.INSTER,"setIaasAddedItem");

        myBatisDao.executionPojo(XML_PATH, id,  IMyBatisDao.SQL.INSTER,"setIaasItemParts");

        myBatisDao.executionPojo(XML_PATH, id,  IMyBatisDao.SQL.INSTER,"setIaasItemVersion");

    }

    /**
     * 通过接口更新iaas中的相关数据
     * @param resAddedExt
     */
    @Override
    public void updateByInterface(ResAddedExt resAddedExt){
        myBatisDao.executionPojo(XML_PATH,resAddedExt,IMyBatisDao.SQL.UPDATE,"updateByInterface");
    }

    /**
     * 根据系统查询未使用的资源新增的子表配置
     * @param id 系统ID
     * @return List<ResAddedItem> 未使用的资源数据
     */
    @Override
    public List<ResAddedItem> queryConfigListBySystem(String id){
        return myBatisDao.selectListT(XML_PATH,id,"queryConfigListBySystem");
    }
}
