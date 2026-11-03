package com.tansun.wf.activiti.util.dbtool.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectRes {

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, Object>> results) {
        this.results = results;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private int total;
    private String tableName;
    private List<String> columnNames = new ArrayList<>();
    private List<Map<String, Object>> results = new ArrayList<>();
}
