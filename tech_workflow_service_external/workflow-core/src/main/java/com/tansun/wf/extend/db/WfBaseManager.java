package com.tansun.wf.extend.db;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 工作流数据操作基础类
 *
 * @param <D>
 * @param <T>
 */
public abstract class WfBaseManager<D extends WfBaseMapper<T>,   T> {

    @Autowired
    protected D mapper;

    @Autowired
    WfIdGeneratorService wfIdGeneratorService;


    public int insert(T entity) {
        if (entity != null && entity instanceof WfBaseEntity) {
            WfBaseEntity wfBaseEntity = (WfBaseEntity) entity;
            if (wfBaseEntity.getId() == null) {
                wfBaseEntity.setId(wfIdGeneratorService.getIdGenerator().getNextId());
            }
        }
        return this.mapper.insert(entity);

    }

    public int bulkInsert(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (Object entity : list) {
                if (entity != null && entity instanceof WfBaseEntity) {
                    WfBaseEntity wfBaseEntity = (WfBaseEntity) entity;
                    if (wfBaseEntity.getId() == null) {
                        wfBaseEntity.setId(wfIdGeneratorService.getIdGenerator().getNextId());
                    }
                }
            }
        }
        return this.mapper.bulkInsert(list);
    }


    public int delete(T entity) {
        return this.mapper.delete(entity);
    }


    public int deleteByIds(List<String> ids) {
        return this.mapper.deleteByIds(ids);
    }

    public int update(T entity) {
        return this.mapper.update(entity);

    }

    public void save(T entity){
        if (entity != null ) {
            WfBaseEntity wfBaseEntity = (WfBaseEntity) entity;
            if (wfBaseEntity.getId() == null) {
                this.insert(entity);
            }else {
                this.update(entity);
            }
        }else {
            throw new RuntimeException("entity is null");
        }
    }

    public  T selectById(String id) {
        return  this.mapper.selectById(id);
    }


    public List<T> findList() {
        return this.mapper.findList(null);
    }

    public List<T> findList(WfBaseQuery query) {
        return this.mapper.findList(query);
    }

    /**
     * mapper.xml 必须有<select id="findList" 查询语句
     *
     * @param query
     * @return
     */
    public T findOne(WfBaseQuery query) {
        List list = this.mapper.findList(query);
        if (list == null || list.size() == 0) {
            return null;
        } else if (list.size() == 1) {
            return (T) list.get(0);
        } else {
            throw new RuntimeException("return results greater than 1");
        }
    }

    /**
     * 分页查询，mapper.xml  必须有<select id="findList" 查询语句
     *
     * @param query
     * @param pageInfo
     * @return
     */
    public PageInfo<T> findPage(final WfBaseQuery query, PageInfo pageInfo) {
        pageInfo = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                mapper.findList(query);
            }
        });
        return pageInfo;
    }

    /**
     * 分页查询，mapper.xml  必须有<select id="findList" 查询语句
     *
     * @param query
     * @param pageInfo
     * @return
     */
    public PageInfo<List<Map>> findPageMap(final WfBaseQuery query, PageInfo pageInfo) {
        pageInfo = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                mapper.findList(query);
            }
        });
        return pageInfo;
    }

    /**
     * 查询总数，mapper.xml  必须有<select id="findList" 查询语句
     *
     * @param query
     * @return
     */
    public long findListCount(final WfBaseQuery query) {
        return PageHelper.count(new ISelect() {
            @Override
            public void doSelect() {
                mapper.findList(query);
            }
        });
    }

}
