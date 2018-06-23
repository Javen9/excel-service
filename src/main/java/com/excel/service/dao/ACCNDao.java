package com.excel.service.dao;

import com.excel.service.dao.po.AccnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by javen on 2018/5/17.
 */
@Repository
public class ACCNDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object getAccNoByName(String name) {
        String sql = "select acc_no from accn where name=?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, name);
        if (list.size() == 1) {
            return list.get(0).get("acc_no");
        }
        return null;
    }

    public List<AccnNode> getAccnList(String filter) {
        String sql = "select acc_no,name,acc_no_up from accn";
        List<Map<String, Object>> list = null;
        if (!StringUtils.isEmpty(filter)) {
            sql += " where name like ?";
            list = jdbcTemplate.queryForList(sql, "%" + filter + "%");
        } else {
            list = jdbcTemplate.queryForList(sql);
        }
        return getSubAccnList(list, "");
    }

    private List<AccnNode> getSubAccnList(List<Map<String, Object>> list, String acc_no_up) {
        List<AccnNode> nodeList = new ArrayList<>();
        for (Map<String, Object> item : list) {
            if (acc_no_up.equals(item.get("acc_no_up"))) {
                AccnNode node = new AccnNode();
                node.setId(item.get("acc_no"));
                node.setText(item.get("acc_no") + " " + item.get("name"));
                node.setChildren(getSubAccnList(list, item.get("acc_no").toString()));
                nodeList.add(node);
            }
        }
        return nodeList;
    }
}
