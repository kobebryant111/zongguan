package com.work.ppm.commons.artemis;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

@Data
public class ArtemisPageInfo<E> {

    /**
     * 记录总数
     */
    private Integer total;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     * 资源数据列表
     */
    private JSONArray list;
    /**
     * 结果集
     */
    private List<E> result;

}
