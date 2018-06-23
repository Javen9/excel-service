package com.excel.service.dao.po;

import java.util.List;

/**
 * Created by javen on 2018/5/31.
 */
public class AccnNode {

    private Object id;
    private Object text;
    private List<AccnNode> children;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public List<AccnNode> getChildren() {
        return children;
    }

    public void setChildren(List<AccnNode> children) {
        this.children = children;
    }
}
