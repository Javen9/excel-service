package com.excel.service.controller.ots.template;

import com.excel.service.beans.TemplateColumnDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javen on 2018/5/15.
 */
public class Customer {

    private List<TemplateColumnDefinition> columnDefinitions = new ArrayList<>();

    public Customer() {
        init();
    }

    public List<TemplateColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void init() {
        TemplateColumnDefinition xc = new TemplateColumnDefinition("项次", "xc");
        columnDefinitions.add(xc);

        TemplateColumnDefinition khdh = new TemplateColumnDefinition("客户代号", "khdh");
        columnDefinitions.add(khdh);

        TemplateColumnDefinition qc = new TemplateColumnDefinition("全称", "qc");
        qc.addTitlePattern("会计科目");
        columnDefinitions.add(qc);

        TemplateColumnDefinition dxb = new TemplateColumnDefinition("对象别", "dxb", "3");
        columnDefinitions.add(dxb);

        TemplateColumnDefinition xygz = new TemplateColumnDefinition("信用管制", "xygz", "1");
        columnDefinitions.add(xygz);

        TemplateColumnDefinition kslb = new TemplateColumnDefinition("扣税类别", "kslb", "1");
        columnDefinitions.add(kslb);

        TemplateColumnDefinition lzfs = new TemplateColumnDefinition("立帐方式", "lzfs", "1");
        columnDefinitions.add(lzfs);

        TemplateColumnDefinition zljsfs = new TemplateColumnDefinition("帐龄计算方式", "zljsfs", "1");
        columnDefinitions.add(zljsfs);

        TemplateColumnDefinition jygzfse = new TemplateColumnDefinition("交易管制方式二", "jygzfse", "F");
        columnDefinitions.add(jygzfse);

        TemplateColumnDefinition jygzfss = new TemplateColumnDefinition("交易管制方式三", "jygzfss", "F");
        columnDefinitions.add(jygzfss);

        TemplateColumnDefinition qkkz = new TemplateColumnDefinition("欠款控制", "qkkz", "1");
        columnDefinitions.add(qkkz);

        TemplateColumnDefinition lry = new TemplateColumnDefinition("录入员", "lry", "A002");
        columnDefinitions.add(lry);

        TemplateColumnDefinition skhy = new TemplateColumnDefinition("收款含预/暂收款否", "skhy", "F");
        columnDefinitions.add(skhy);

        TemplateColumnDefinition yszkkm = new TemplateColumnDefinition("预收账款科目", "yszkkm", "2203");
        columnDefinitions.add(yszkkm);

        TemplateColumnDefinition yfzkkm = new TemplateColumnDefinition("预付账款科目", "yfzkkm", "1123");
        columnDefinitions.add(yfzkkm);

        TemplateColumnDefinition txmkz = new TemplateColumnDefinition("条形码控制", "txmkz", "1");
        columnDefinitions.add(txmkz);

        TemplateColumnDefinition zgyfzkkm = new TemplateColumnDefinition("暂估应付账款科目", "zgyfzkkm", "220201");
        columnDefinitions.add(zgyfzkkm);

        TemplateColumnDefinition bmqzdh = new TemplateColumnDefinition("部门群组代号", "bmqzdh", "123");
        columnDefinitions.add(bmqzdh);

        TemplateColumnDefinition sfrqfs = new TemplateColumnDefinition("首付日期方式", "sfrqfs", "1");
        columnDefinitions.add(sfrqfs);

        TemplateColumnDefinition fptt2 = new TemplateColumnDefinition("Etry E5发票抬头2", "fptt2");
        fptt2.addTitlePattern("会计科目");
        columnDefinitions.add(fptt2);

        TemplateColumnDefinition zhmc = new TemplateColumnDefinition("帐户名称", "zhmc");
        zhmc.addTitlePattern("会计科目");
        columnDefinitions.add(zhmc);

        TemplateColumnDefinition kdf = new TemplateColumnDefinition("快递否", "kdf", "F");
        columnDefinitions.add(kdf);

        TemplateColumnDefinition ysqtzk = new TemplateColumnDefinition("应收其它账款", "ysqtzk", "1221");
        columnDefinitions.add(ysqtzk);

        TemplateColumnDefinition yffy = new TemplateColumnDefinition("应付费用", "yffy", "2241");
        columnDefinitions.add(yffy);

        TemplateColumnDefinition xygzhblz = new TemplateColumnDefinition("信用管制含不立帐销/退/折单", "xygzhblz", "T");
        columnDefinitions.add(xygzhblz);

        TemplateColumnDefinition jc = new TemplateColumnDefinition("简称", "jc");
        jc.addTitlePattern("会计科目");
        columnDefinitions.add(jc);

        TemplateColumnDefinition jzlb = new TemplateColumnDefinition("结帐类别", "jzlb", "结帐期间");
        columnDefinitions.add(jzlb);

        TemplateColumnDefinition qsr = new TemplateColumnDefinition("起算日", "qsr", "1");
        columnDefinitions.add(qsr);

        TemplateColumnDefinition jgts = new TemplateColumnDefinition("间隔天数", "jgts", "30");
        columnDefinitions.add(jgts);

        TemplateColumnDefinition pjdqr = new TemplateColumnDefinition("票据到期日", "pjdqr", "30");
        columnDefinitions.add(pjdqr);

        TemplateColumnDefinition awbcxf = new TemplateColumnDefinition("按外币冲销否", "awbcxf", "F");
        columnDefinitions.add(awbcxf);

        TemplateColumnDefinition khdj = new TemplateColumnDefinition("客户等级", "khdj", "1");
        columnDefinitions.add(khdj);

        TemplateColumnDefinition xyhpjf = new TemplateColumnDefinition("信用含票据否", "xyhpjf", "F");
        columnDefinitions.add(xyhpjf);

        TemplateColumnDefinition yszkkm2 = new TemplateColumnDefinition("应收账款科目", "yszkkm2", "1122");
        columnDefinitions.add(yszkkm2);

        TemplateColumnDefinition yfzkkm2 = new TemplateColumnDefinition("应付账款科目", "yfzkkm2", "2202");
        columnDefinitions.add(yfzkkm2);

        TemplateColumnDefinition yspjkm = new TemplateColumnDefinition("应收票据科目", "yspjkm", "1121");
        columnDefinitions.add(yspjkm);

        TemplateColumnDefinition yfpjkm = new TemplateColumnDefinition("应付票据科目", "yfpjkm", "2201");
        columnDefinitions.add(yfpjkm);

        TemplateColumnDefinition xyedhddf = new TemplateColumnDefinition("信用额度含订单否", "xyedhddf", "F");
        columnDefinitions.add(xyedhddf);

        TemplateColumnDefinition fpmc = new TemplateColumnDefinition("发票名称", "fpmc", "");
        columnDefinitions.add(fpmc);

        TemplateColumnDefinition xyedhczhkf = new TemplateColumnDefinition("信用额度含传真汇款否", "xyedhczhkf", "F");
        columnDefinitions.add(xyedhczhkf);

        TemplateColumnDefinition khsl = new TemplateColumnDefinition("客户税率", "khsl", "17");
        columnDefinitions.add(khsl);

        TemplateColumnDefinition xyjc = new TemplateColumnDefinition("信用检测", "xyjc", "1");
        columnDefinitions.add(xyjc);

        TemplateColumnDefinition cydwf = new TemplateColumnDefinition("承运单位否", "cydwf", "F");
        columnDefinitions.add(cydwf);

        TemplateColumnDefinition xyedhxs = new TemplateColumnDefinition("信用额度含下属", "xyedhxs", "F");
        columnDefinitions.add(xyedhxs);

        TemplateColumnDefinition xyedhyskf = new TemplateColumnDefinition("信用额度含预收款否", "xyedhyskf", "F");
        columnDefinitions.add(xyedhyskf);

        TemplateColumnDefinition xyedhzskf = new TemplateColumnDefinition("信用额度含暂收款否", "xyedhzskf", "F");
        columnDefinitions.add(xyedhzskf);

        TemplateColumnDefinition shr = new TemplateColumnDefinition("审核人", "shr", "A002");
        columnDefinitions.add(shr);

        TemplateColumnDefinition jygzfsy = new TemplateColumnDefinition("交易管制方式一", "jygzfsy", "F");
        columnDefinitions.add(jygzfsy);

    }
}
