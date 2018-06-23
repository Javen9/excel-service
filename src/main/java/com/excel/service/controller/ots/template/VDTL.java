package com.excel.service.controller.ots.template;

import com.excel.service.beans.TemplateColumnDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javen on 2018/5/15.
 */
public class VDTL {

    private List<TemplateColumnDefinition> columnDefinitions = new ArrayList<>();

    public VDTL() {
        init();
    }

    public List<TemplateColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void init() {
        TemplateColumnDefinition zphm = new TemplateColumnDefinition("制票号码", "zphm");
        columnDefinitions.add(zphm);

        TemplateColumnDefinition xz = new TemplateColumnDefinition("项资", "xz");
        columnDefinitions.add(xz);

        TemplateColumnDefinition pzrq = new TemplateColumnDefinition("凭证日期", "pzrq");
        columnDefinitions.add(pzrq);

        TemplateColumnDefinition pzhm = new TemplateColumnDefinition("凭证号码", "pzhm");
        pzhm.addTitlePattern("凭证号");
        columnDefinitions.add(pzhm);

        TemplateColumnDefinition jd = new TemplateColumnDefinition("借/贷", "jd");
        columnDefinitions.add(jd);

        TemplateColumnDefinition kjkmdm = new TemplateColumnDefinition("会计科目代码", "kjkmdm");
        columnDefinitions.add(kjkmdm);

        TemplateColumnDefinition kjkm = new TemplateColumnDefinition("会计科目", "kjkm");
        columnDefinitions.add(kjkm);

        TemplateColumnDefinition bmdh = new TemplateColumnDefinition("部门代号", "bmdh");
        columnDefinitions.add(bmdh);

        TemplateColumnDefinition zy = new TemplateColumnDefinition("摘  要", "zy");
        columnDefinitions.add(zy);

        TemplateColumnDefinition wbje = new TemplateColumnDefinition("外币金额", "wbje");
        columnDefinitions.add(wbje);

        TemplateColumnDefinition bbje = new TemplateColumnDefinition("本币金额", "bbje");
        columnDefinitions.add(bbje);

        TemplateColumnDefinition hl = new TemplateColumnDefinition("汇率", "hl", "1");
        columnDefinitions.add(hl);

        TemplateColumnDefinition dxb = new TemplateColumnDefinition("对象别", "dxb");
        columnDefinitions.add(dxb);

        TemplateColumnDefinition lydh = new TemplateColumnDefinition("来源单号", "lydh");
        columnDefinitions.add(lydh);

        TemplateColumnDefinition pzzl = new TemplateColumnDefinition("凭证种类", "pzzl", "3");
        columnDefinitions.add(pzzl);

        TemplateColumnDefinition bb = new TemplateColumnDefinition("币别", "bb", "RMB");
        columnDefinitions.add(bb);
    }
}
