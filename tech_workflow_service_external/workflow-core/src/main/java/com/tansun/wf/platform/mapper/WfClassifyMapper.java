package com.tansun.wf.platform.mapper;

import com.tansun.wf.extend.db.WfBaseMapper;
import com.tansun.wf.platform.model.WfClassifyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/4/27
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Repository
public interface WfClassifyMapper extends WfBaseMapper<WfClassifyEntity> {

    /**
     * 批量删除流程分类
     * @param ids 分类实体id
     */
     void deleteClassifyList(@Param("ids") List<String> ids);

    /**
     * 根据父节点尾模糊查询流程分类集合
     * @param parentIds 父节点id
     * @return 分类实体集合
     */
     List<WfClassifyEntity> findByParentIds(@Param("parentIds") String parentIds);

    /**
     * 通过分类id查询
     * @param id id
     * @return 分类实体
     */
     WfClassifyEntity findClassifyById(@Param("id") String id);

    Map<String, Object> selectClassfyCodeById(String id);

    /**
     * 通过分类编号查询
     * @param code code
     * @return 分类实体
     */
    WfClassifyEntity findClassifyByCode(@Param("code")String code);
}
