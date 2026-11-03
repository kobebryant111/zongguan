package com.work.ppm.application.dto.flowmanager;

import lombok.Data;

import java.util.List;

/**
 * 实体类
 */
@Data
public class PpmFlowTypeDto {
    /**
     *	字段名
     **/
    private Long id;
    /**
     *	字段名
     **/
    private String flowname;
    /**
     *	字段名
     **/
    private String flowdraftno;
    /**
     *	字段名
     **/
    private String parentid;
    /**
     *	字段名
     **/
    private Long isdel;

    /**
     *	字段名
     **/
    private String creators;

    /**
     *	字段名
     **/
    private String lastmoduser;
    /**
     *	字段名
     **/
    private String userid;
    /**
     *	字段名
     **/
    private String username;
    /**
     * 子节点集合
     */
    private List<PpmFlowTypeDto> children;
    /**
     * 状态 add 添加，update：更新
     */
    private String sqlState;

}
