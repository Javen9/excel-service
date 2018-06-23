package com.excel.service.controller.ots.template;

import com.excel.service.beans.TemplateColumnDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javen on 2018/5/15.
 */
public class VDEH {

    private List<TemplateColumnDefinition> columnDefinitions = new ArrayList<>();

    public VDEH() {
        init();
    }

    public List<TemplateColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void init() {
        TemplateColumnDefinition cphm = new TemplateColumnDefinition("传票号码", "cphm");
        columnDefinitions.add(cphm);

        TemplateColumnDefinition zprq = new TemplateColumnDefinition("制票日期", "zprq");
        zprq.addTitlePattern("凭证日期");
        columnDefinitions.add(zprq);

        TemplateColumnDefinition pzhm = new TemplateColumnDefinition("凭证号码", "pzhm");
        pzhm.addTitlePattern("凭证号");
        columnDefinitions.add(pzhm);

        TemplateColumnDefinition pzrq = new TemplateColumnDefinition("凭证日期", "pzrq");
        columnDefinitions.add(pzrq);

        TemplateColumnDefinition pzlb = new TemplateColumnDefinition("凭证类别", "pzlb", "3");
        columnDefinitions.add(pzlb);

        TemplateColumnDefinition gzrq = new TemplateColumnDefinition("过账日期", "gzrq");
        gzrq.addTitlePattern("凭证日期");
        columnDefinitions.add(gzrq);

        TemplateColumnDefinition shrq = new TemplateColumnDefinition("审核日期", "shrq");
        shrq.addTitlePattern("凭证日期");
        columnDefinitions.add(shrq);

        TemplateColumnDefinition bm = new TemplateColumnDefinition("部门", "bm");
        columnDefinitions.add(bm);

        TemplateColumnDefinition shzj = new TemplateColumnDefinition("审核注记", "shzj");
        columnDefinitions.add(shzj);

        TemplateColumnDefinition bzr = new TemplateColumnDefinition("编制人", "bzr");
        columnDefinitions.add(bzr);

        TemplateColumnDefinition gzr = new TemplateColumnDefinition("过账人", "gzr");
        columnDefinitions.add(gzr);

        TemplateColumnDefinition je = new TemplateColumnDefinition("金额", "je");
        columnDefinitions.add(je);

        TemplateColumnDefinition lrrq = new TemplateColumnDefinition("录入日期", "lrrq");
        lrrq.addTitlePattern("凭证日期");
        columnDefinitions.add(lrrq);
    }
}
