package com.work.ppm.commons.artemis.request;

import com.alibaba.fastjson.JSONArray;
import com.work.ppm.commons.artemis.ArtemisPageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询门禁点列表v2 请求参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoorReqt extends ArtemisPageInfo {

    /**
     * 名称，模糊搜索，最大长度32，若包含中文，最大长度指不超过按照指定编码的字节长度，即getBytes("utf-8").length
     */
    private String name;

    /**
     * 区域编号,可以为空; String[]，支持根据区域批量查询； 区域编号个数 <=1000个； 单个长度<=64Byte；，
     * 可从[查询区域列表v2]@[软件产品-综合安防管理平台-API列表-资源目录-区域信息接口#查询区域列表v2]接口获取返回参数indexCode
     */
    private List<String> regionIndexCodes;

    @Override
    public Integer getTotal() {
        return super.getTotal();
    }

    @Override
    public Integer getPageNo() {
        return super.getPageNo();
    }

    @Override
    public Integer getPageSize() {
        return super.getPageSize();
    }

    @Override
    public JSONArray getList() {
        return super.getList();
    }

    @Override
    public List getResult() {
        return super.getResult();
    }

    @Override
    public void setTotal(Integer total) {
        super.setTotal(total);
    }

    @Override
    public void setPageNo(Integer pageNo) {
        super.setPageNo(pageNo);
    }

    @Override
    public void setPageSize(Integer pageSize) {
        super.setPageSize(pageSize);
    }

    @Override
    public void setList(JSONArray list) {
        super.setList(list);
    }

    @Override
    public void setResult(List result) {
        super.setResult(result);
    }
}
