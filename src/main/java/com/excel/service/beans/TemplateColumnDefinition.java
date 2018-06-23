package com.excel.service.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javen on 2018/5/15.
 */
public class TemplateColumnDefinition {

    private String title;

    private String field;

    private List<String> titlePatternList = new ArrayList<>();

    private String defaultValue;

    private boolean have;

    public TemplateColumnDefinition(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public TemplateColumnDefinition(String title, String field, String defaultValue) {
        this.title = title;
        this.field = field;
        this.defaultValue = defaultValue;
    }

    public void addTitlePattern(String titlePattern) {
        titlePatternList.add(titlePattern);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTitlePatternList() {
        return titlePatternList;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }
}
