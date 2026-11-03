package com.tansun.wf.platform.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/12
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Repository
public interface WfReportMapper {
    /**
     * @param tenantId 租户id
     *  @return key :processNumber,processName
     */
    List<Map<String,Object>> queryProcessNumber (@Param("tenantId") String tenantId);

    /**
     *
     * @param tenantId 租户id
     * @return key :processNum,processName,runProcessNumber
     */
    List<Map<String,Object>> queryRunProcessNumber (@Param("tenantId") String tenantId);

    /**
     *
     * @param tenantId 租户id
     * @return
     */
    List<Map<String, Object>> queryProcessState(String tenantId);

    /**
     *
     * @param tenantId 租户id
     * @param userId 用户id
     * @return
     */
    List<Map<String, Object>> queryUserProcessNumber(@Param("tenantId") String tenantId, @Param("userId") String userId);
}
