package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.exception.WorkflowException;
import com.tansun.wf.platform.mapper.WfDefinedButtonMapper;
import com.tansun.wf.platform.model.WfDefinedButtonEntity;
import com.tansun.wf.platform.query.WfDefinedButtonQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/7
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfDefinedButtonService  extends WfBaseManager<WfDefinedButtonMapper, WfDefinedButtonEntity> {

    @Autowired
    WfDefinedButtonMapper wfDefinedButtonMapper;

    public PageInfo<WfDefinedButtonEntity> getPageList(WfDefinedButtonQuery query, PageInfo pageInfo) {
        return this.findPage(query, pageInfo);
    }

    public int delete(String id){
        WfDefinedButtonEntity entity = new WfDefinedButtonEntity();
        entity.setId(id);
        return this.delete(entity);
    }

    public WfDefinedButtonEntity getById(String id){
        return this.selectById(id);
    }

    public int save(Map<String, Object> reqMap){
        String alias =  MapUtils.getString(reqMap,"alias");
        if (StringUtils.isBlank(alias)) {
            throw new WorkflowException("意见别名不能为空");
        }
        boolean isAlias = isSpecialChar(alias);
        if(isAlias){
            throw new WorkflowException("不能有特殊符号");
        }
        String id = MapUtils.getString(reqMap, "id");
        String key = MapUtils.getString(reqMap, "key");
        if (StringUtils.isBlank(key)) {
            throw new WorkflowException("意见key不能为空");
        }
        String name = MapUtils.getString(reqMap, "name");
        if (StringUtils.isBlank(name)) {
            throw new WorkflowException("意见名称不能为空");
        }
        String status = MapUtils.getString(reqMap, "status");
        if (StringUtils.isBlank(status)) {
            throw new WorkflowException("意见生效未生效状态不能为空");
        }
        Integer buttonSort = MapUtils.getInteger(reqMap, "buttonSort");
        if (null==buttonSort) {
            throw new WorkflowException("排序不能为空");
        }
        String tenantId = MapUtils.getString(reqMap, "tenantId");
        WfDefinedButtonEntity wfDefinedButtonEntity = new WfDefinedButtonEntity();
        wfDefinedButtonEntity.setKey(key);
        wfDefinedButtonEntity.setName(name);
        wfDefinedButtonEntity.setTenantId(tenantId);
        wfDefinedButtonEntity.setButtonSort(buttonSort);
        wfDefinedButtonEntity.setStatus(status);
        wfDefinedButtonEntity.setAlias(alias);
        //保存操作
        if(id == ""|| id == null){
            WfDefinedButtonQuery wfDefinedButtonQuery = new WfDefinedButtonQuery();
            wfDefinedButtonQuery.setKey(MapUtils.getString(reqMap, "key"));;
            if(wfDefinedButtonMapper.findByKey(wfDefinedButtonQuery) != null){
                throw new WorkflowException("保存失败,该按钮key已存在");
            }
            this.save(wfDefinedButtonEntity);
            return 1;
        } else {
            //修改
            wfDefinedButtonEntity.setId(id);
            return this.update(wfDefinedButtonEntity);
        }
    }

    private boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

}
