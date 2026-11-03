package com.work.ppm.commons.pager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页对象
 */
@Data
public class Pager {
    /**
     * 构造函数
     */
    public Pager(int index ,int size) {
        this.index = index;
        this.size = size;
    }
    /**
     * 构造函数
     */
    public Pager() {
        this.index = 1;
        this.size = 50;
    }
    /**
     * 每页显示的条数
     */
    @ApiModelProperty(name = "size", value = "每页显示的条数", dataType = "int")
    private int size;
    /**
     * 页数下标，当前在第几页
     */
    @ApiModelProperty(name = "index", value = "页数下标，当前在第几页", dataType = "int")
    private int index;
    /**
     * 总共有多少条数
     */
    @ApiModelProperty(name = "total", value = "总共有多少条数", dataType = "int")
    private int total;
    /**
     * 分页总数
     */
    @ApiModelProperty(name = "pagerCount", value = "分页总数", dataType = "int", hidden = true)
    private int pagerCount;
    /**
     * 开始分页数
     */
    @ApiModelProperty(name = "start", value = "开始分页数", dataType = "int", hidden = true)
    private int start;
    /**
     * 结束页
     */
    @ApiModelProperty(name = "end", value = "结束页", dataType = "int", hidden = true)
    private int end;

    public int getEnd() {
        return this.getSize() * this.getIndex();
    }

    /**
     * 数据集合
     */
    @ApiModelProperty(name = "list", value = "数据集合", dataType = "int", hidden = true)
    private List<?> list;

    public int getStart() {
        return (this.getIndex() -1) * (this.getSize());
    }
    /**
     * 计算分页数
     * @return int
     */
    @ApiModelProperty(name = "getComputePageCount", value = "计算分页数", dataType = "int", hidden = true)
    public int getComputePageCount() {
        Double pageCount = new Double(Math.ceil(this.total + 0.0D)) / this.getSize();
        this.pagerCount = Integer.valueOf(Double.valueOf(Math.ceil(pageCount)).intValue());
        return this.pagerCount;
    }
    /**
     * 排序列
     */
    @ApiModelProperty(name = "sortField", value = "排序列", dataType = "string", hidden = true)
    private String  sortField;
    /**
     * 排序倒叙，正序
     */
    @ApiModelProperty(name = "sortOrder", value = "排序倒叙，正序", dataType = "string", hidden = true)
    private String sortOrder;

    /**
     * oracle查询因为从块中取数据，分页分数不确定，导致重复等问题，需要唯一列确定顺序
     * 多个的话已，分割
     */
    @ApiModelProperty(name = "sortRequirdField", value = "oracle查询因为从块中取数据", dataType = "string", hidden = true)
    private String sortRequirdField ;
}
