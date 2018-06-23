package com.excel.service.beans;

import java.util.List;
import java.util.Map;

/**
 * Created by javen on 2018/5/14.
 */
public class DatagridDatas {

    private int total;

    private List<Map<String, Object>> rows;

    public int getTotal() {
        return total;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
        this.total = rows.size();
    }
}
