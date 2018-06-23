package com.excel.service.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javen on 2018/5/14.
 */
public class DatagridColumns {

    private List<Column> columns = new ArrayList<>();

    public void addColumn(String title, String field) {
        columns.add(new Column(title, field));
    }

    class Column {
        private String field;
        private String title;

        public Column(String title, String field) {
            this.field = field;
            this.title = title;
        }

        public String getField() {
            return field;
        }

        public String getTitle() {
            return title;
        }
    }

    public List<Column> getColumns() {
        return columns;
    }
}
