package com.excel.service.controller.ots;

import com.excel.service.beans.DatagridColumns;
import com.excel.service.beans.DatagridDatas;
import com.excel.service.beans.JsonObject;
import com.excel.service.beans.TemplateColumnDefinition;
import com.excel.service.controller.ots.template.Customer;
import com.excel.service.controller.ots.template.VDEH;
import com.excel.service.controller.ots.template.VDTL;
import com.excel.service.dao.ACCNDao;
import com.excel.service.util.CustomerCodeUtil;
import com.excel.service.util.ExcelUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by javen on 2018/5/11.
 */
@Controller
@RequestMapping("/ots")
public class OTSController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ACCNDao accnDao;

    private final static String OTS_DatagridData_Token = "OTS_DatagridData_Token";
    private final static String OTS_ExcelData_Token = "OTS_ExcelData_Token";
    private final static String OTS_Download_FileName = "OTS_Download_FileName";

    @GetMapping("/page")
    public String index() {
        return "ots";
    }

    @ResponseBody
    @PostMapping("upload")
    public JsonObject upload(HttpServletRequest req, MultipartHttpServletRequest request) {
        JsonObject jsonObject = new JsonObject();
        Map<String, MultipartFile> fileMap = request.getFileMap();
        Iterator<String> keys = fileMap.keySet().iterator();
        if (keys.hasNext()) {
            MultipartFile multipartFile = fileMap.get(keys.next());
            try {
                request.getSession().setAttribute(OTS_Download_FileName, multipartFile.getOriginalFilename());
                InputStream inputStream = multipartFile.getInputStream();
                LinkedHashMap<String, ArrayList<String>> excelData = ExcelUtil.readExcel(inputStream);

                DatagridColumns columns = new DatagridColumns();
                List<Map<String, Object>> rows = new ArrayList<>();
                int keyIndex = 1;
                for (String key : excelData.keySet()) {
                    String field = "key" + keyIndex++;
                    columns.addColumn(key, field);

                    for (int index = 0; index < excelData.get(key).size(); index++) {
                        Map<String, Object> row = null;
                        if (rows.size() > index) {
                            row = rows.get(index);
                        } else {
                            row = new HashMap<>();
                            rows.add(row);
                        }
                        row.put(field, excelData.get(key).get(index));
                    }
                }
                //标准模板
                Map<String, Object> data = new HashMap<>();
                data.put("st", columns);
                //厂商客户模板
                DatagridColumns columns_cs = new DatagridColumns();
                for (TemplateColumnDefinition column : new Customer().getColumnDefinitions()) {
                    columns_cs.addColumn(column.getTitle(), column.getField());
                }
                data.put("cs", columns_cs);
                //VDTL模板
                DatagridColumns columns_vdtl = new DatagridColumns();
                for (TemplateColumnDefinition column : new VDTL().getColumnDefinitions()) {
                    columns_vdtl.addColumn(column.getTitle(), column.getField());
                }
                data.put("vdtl", columns_vdtl);
                //VDEH模板
                DatagridColumns columns_vdeh = new DatagridColumns();
                for (TemplateColumnDefinition column : new VDEH().getColumnDefinitions()) {
                    columns_vdeh.addColumn(column.getTitle(), column.getField());
                }
                data.put("vdeh", columns_vdeh);
                jsonObject.setData(data);

                DatagridDatas datas = new DatagridDatas();
                datas.setRows(rows);
                req.getSession().setAttribute(OTS_DatagridData_Token, datas);
                req.getSession().setAttribute(OTS_ExcelData_Token, excelData);
            } catch (Exception e) {
                jsonObject.setCode("1");
                jsonObject.setMsg("Excel文件读取失败");
                logger.error("Excel文件读取失败", e);
            }
        }
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping("getOriginalDatas")
    public Object getOriginalDatas(HttpServletRequest req) {
        return req.getSession().getAttribute(OTS_DatagridData_Token);
    }

    @ResponseBody
    @RequestMapping("getCustomerDatas")
    public Object getCustomerDatas(HttpServletRequest req) {
        LinkedHashMap<String, ArrayList<String>> excelData = (LinkedHashMap<String, ArrayList<String>>) req.getSession().getAttribute(OTS_ExcelData_Token);
        List<Map<String, Object>> rows = new ArrayList<>();
        List<Integer> remove_rows_index = new ArrayList<>();
        Set<String> customerList = new HashSet<>();
        if (excelData != null) {
            Customer customer = new Customer();
            for (String key : excelData.keySet()) {
                for (int index = 0; index < excelData.get(key).size(); index++) {
                    Map<String, Object> row = null;
                    if (rows.size() > index) {
                        row = rows.get(index);
                        if (remove_rows_index.contains(index)) {
                            continue;
                        }
                    } else {
                        row = new HashMap<>();
                        rows.add(row);
                    }
                    String value = excelData.get(key).get(index);
                    for (TemplateColumnDefinition column : customer.getColumnDefinitions()) {
                        boolean have = false;
                        if (equalsByTrim(column.getTitle(), key)) {
                            have = true;
                        } else {
                            for (String titlePattern : column.getTitlePatternList()) {
                                if (Pattern.matches(titlePattern, key)) {
                                    have = true;
                                }
                            }
                        }
                        if (have) {
                            column.setHave(have);
                            if ("qc".equals(column.getField())) {
                                value = getCustomerName(excelData, value, index);
                                if (isEmpty(value) || customerList.contains(value)) {
                                    remove_rows_index.add(index);
                                }
                                customerList.add(value);
                            }
                            row.put(column.getField(), value);
                        }
                    }
                }
            }
            for (int i = remove_rows_index.size() - 1; i >= 0; i--) {
                rows.remove(remove_rows_index.get(i).intValue());
            }
            int count = 0;
            for (TemplateColumnDefinition column : customer.getColumnDefinitions()) {
                if (!column.isHave()) {
                    for (Map<String, Object> row : rows) {
                        if (column.getDefaultValue() != null) {
                            row.put(column.getField(), column.getDefaultValue());
                        } else {
                            if ("项次".equals(column.getTitle())) {
                                row.put(column.getField(), ++count + "");
                            } else if ("客户代号".equals(column.getTitle())) {
                                row.put(column.getField(), CustomerCodeUtil.getCode((String) row.get("qc")));
                            }
                        }
                    }
                }
            }
        }
        DatagridDatas datas = new DatagridDatas();
        datas.setRows(rows);
        return datas;
    }

    @ResponseBody
    @RequestMapping("getVDTLDatas")
    public Object getVDTLDatas(HttpServletRequest req) {
        LinkedHashMap<String, ArrayList<String>> excelData = (LinkedHashMap<String, ArrayList<String>>) req.getSession().getAttribute(OTS_ExcelData_Token);
        List<Map<String, Object>> rows = new ArrayList<>();
        if (excelData != null) {
            VDTL vdtl = new VDTL();
            for (String key : excelData.keySet()) {
                for (int index = 0; index < excelData.get(key).size(); index++) {
                    Map<String, Object> row = null;
                    if (rows.size() > index) {
                        row = rows.get(index);
                    } else {
                        row = new HashMap<>();
                        rows.add(row);
                    }
                    String value = excelData.get(key).get(index);
                    if ("借方金额".equals(key)) {
                        if (!isEmpty(value) && new BigDecimal(value).compareTo(new BigDecimal("0")) != 0) {
                            row.put("jd", "D");
                            row.put("bbje", value);
                        }
                        continue;
                    } else if ("贷方金额".equals(key)) {
                        if (!isEmpty(value) && new BigDecimal(value).compareTo(new BigDecimal("0")) != 0) {
                            row.put("jd", "C");
                            row.put("bbje", value);
                        }
                        continue;
                    }
                    for (TemplateColumnDefinition column : vdtl.getColumnDefinitions()) {
                        boolean have = false;
                        if (equalsByTrim(column.getTitle(), key)) {
                            have = true;
                        } else {
                            for (String titlePattern : column.getTitlePatternList()) {
                                if (Pattern.matches(titlePattern, key)) {
                                    have = true;
                                }
                            }
                        }
                        if (have) {
                            column.setHave(have);
                            if ("pzrq".equals(column.getField())) {
                                if (value.indexOf(".") > 0) {
                                    value = value.replace(".", "/");
                                }
                            } else if ("kjkm".equals(column.getField())) {

                                String v = getCustomerName(excelData, value, index);
                                if (!isEmpty(v)) {
                                    row.put("dxb", CustomerCodeUtil.getCode(v));
                                }
                                if (value.indexOf(" ") > 0) {
                                    String v2 = null;
//                                    if (value.lastIndexOf("_") > 0) {
//                                        v2 = value.substring(value.indexOf(" ") + 1, value.lastIndexOf("_"));
//                                    } else if (value.lastIndexOf(" ") > 0) {
//                                        v2 = value.substring(value.indexOf(" ") + 1);
//                                    } else {
                                    v2 = value;
//                                    }
                                    if (v2 != null) {
                                        v2 = v2.replaceAll(" ", "");
                                    }
                                    Object no = accnDao.getAccNoByName(v2);
                                    if (no != null) {
                                        row.put("kjkmdm", no.toString());
                                    }
                                }

                            }
                            row.put(column.getField(), value);
                        }
                    }
                }
            }
            for (TemplateColumnDefinition column : vdtl.getColumnDefinitions()) {
                if (!column.isHave() || "凭证号码".equals(column.getTitle())) {
                    String last_pzhm = null;
                    int xz = 1;
                    for (Map<String, Object> row : rows) {
                        if (column.getDefaultValue() != null) {
                            row.put(column.getField(), column.getDefaultValue());
                        } else {
                            if ("项资".equals(column.getTitle())) {
                                if (!row.get("pzhm").equals(last_pzhm)) {
                                    xz = 1;
                                    last_pzhm = (String) row.get("pzhm");
                                }
                                row.put(column.getField(), xz++ + "");
                            } else if ("凭证号码".equals(column.getTitle())) {
                                String pzrq = (String) row.get("pzrq");
                                String pzhm = (String) row.get(column.getField());
                                pzrq = pzrq.substring(0, pzrq.lastIndexOf("/")).replace("/", "");
                                row.put(column.getField(), pzrq + pzhm);
                            }
                        }
                    }
                }
            }
        }
        DatagridDatas datas = new DatagridDatas();
        datas.setRows(rows);
        return datas;
    }

    @ResponseBody
    @RequestMapping("getVDEHDatas")
    public Object getVDEHDatas(HttpServletRequest req) {
        LinkedHashMap<String, ArrayList<String>> excelData = (LinkedHashMap<String, ArrayList<String>>) req.getSession().getAttribute(OTS_ExcelData_Token);
        List<Map<String, Object>> rows = new ArrayList<>();
        if (excelData != null) {
            VDEH vdeh = new VDEH();
            for (String key : excelData.keySet()) {
                for (int index = 0; index < excelData.get(key).size(); index++) {
                    Map<String, Object> row = null;
                    if (rows.size() > index) {
                        row = rows.get(index);
                    } else {
                        row = new HashMap<>();
                        rows.add(row);
                    }
                    String value = excelData.get(key).get(index);
                    //下面统计总金额用，以借方金额合计为准
                    if ("借方金额".equals(key)) {
                        if (!isEmpty(value) && new BigDecimal(value).compareTo(new BigDecimal("0")) != 0) {
                            row.put("je", value);
                        }
                        continue;
                    } else if ("贷方金额".equals(key)) {
                        if (!isEmpty(value) && new BigDecimal(value).compareTo(new BigDecimal("0")) != 0) {
                            row.put("je", "0");
                        }
                        continue;
                    }
                    for (TemplateColumnDefinition column : vdeh.getColumnDefinitions()) {
                        boolean have = false;
                        if (equalsByTrim(column.getTitle(), key)) {
                            have = true;
                        } else {
                            for (String titlePattern : column.getTitlePatternList()) {
                                if (Pattern.matches(titlePattern, key)) {
                                    have = true;
                                }
                            }
                        }
                        if (have) {
                            column.setHave(have);
                            if ("zprq".equals(column.getField()) || "pzrq".equals(column.getField()) || "gzrq".equals(column.getField()) || "shrq".equals(column.getField())
                                    || "lrrq".equals(column.getField())) {
                                if (value.indexOf(".") > 0) {
                                    value = value.replace(".", "/");
                                }
                            }
                            row.put(column.getField(), value);
                        }
                    }
                }
            }
            List<Integer> remove_rows_index = new ArrayList<>();
            for (TemplateColumnDefinition column : vdeh.getColumnDefinitions()) {
                if (!column.isHave() || "凭证号码".equals(column.getTitle())) {
                    String last_pzhm = null;
                    StringBuilder total = null;
                    int index = 0;
                    for (Map<String, Object> row : rows) {
                        if (column.getDefaultValue() != null) {
                            row.put(column.getField(), column.getDefaultValue());
                        } else {
                            if ("凭证号码".equals(column.getTitle())) {
                                String pzrq = (String) row.get("pzrq");
                                String pzhm = (String) row.get(column.getField());
                                pzrq = pzrq.substring(0, pzrq.lastIndexOf("/")).replace("/", "");
                                pzhm = pzrq + pzhm;
                                row.put(column.getField(), pzhm);
                                //合计金额
                                if (!pzhm.equals(last_pzhm)) {
                                    total = new StringBuilder((String) row.get("je"));
                                    last_pzhm = pzhm;
                                    row.put("je", total);
                                } else {
                                    BigDecimal v = new BigDecimal(total.toString()).add(new BigDecimal((String) row.get("je")));
                                    total.replace(0, total.length(), v.toString());
                                    remove_rows_index.add(index);
                                }
                            }
                        }
                        index++;
                    }
                }
            }
            for (int i = remove_rows_index.size() - 1; i >= 0; i--) {
                rows.remove(remove_rows_index.get(i).intValue());
            }
        }
        DatagridDatas datas = new DatagridDatas();
        datas.setRows(rows);
        return datas;
    }

    @ResponseBody
    @RequestMapping("getAccnList")
    public Object getAccnList(String filter) {
        return accnDao.getAccnList(filter);
    }

//    @ResponseBody
//    @PostMapping("saveBMDH")
//    public JsonObject saveBMDH(String bmdh) {
//        JsonObject jsonObject = new JsonObject();
//        if (!BMDHUtil.saveBHDM(bmdh)) {
//            jsonObject.setCode("1");
//            jsonObject.setMsg(bmdh + " 部门代号已存在");
//        }
//        return jsonObject;
//    }

    @PostMapping("downLoad")
    public void downLoad(HttpServletRequest request, HttpServletResponse response, String jsonData, String type) {
        LinkedHashMap<String, ArrayList<String>> mapData = new Gson().fromJson(jsonData, new TypeToken<LinkedHashMap<String, ArrayList<String>>>() {
        }.getType());
        XSSFWorkbook xssfWorkbook = ExcelUtil.buildExcel(mapData);
        String fileName = type + "_" + request.getSession().getAttribute(OTS_Download_FileName);
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            xssfWorkbook.write(response.getOutputStream());
        } catch (IOException e) {
            logger.error("下载生成标准Excel【{}】异常", fileName, e);
        } finally {
            try {
                xssfWorkbook.close();
            } catch (IOException e) {
            }
        }
    }

    private boolean isEmpty(String v) {
        return v == null || v.trim().length() == 0;
    }

    private boolean equalsByTrim(String v1, String v2) {
        if (v1 == null || v2 == null) {
            return false;
        }
        v1 = v1.replaceAll(" ", "");
        v2 = v2.replaceAll(" ", "");
        return v1.equals(v2);
    }

    /**
     * 获取厂商客户名称
     */
    private String getCustomerName(LinkedHashMap<String, ArrayList<String>> excelData, String value, int index) {
        if (value != null && (value.indexOf("预收账款_") >= 0 || value.indexOf("预付账款_") >= 0
                || value.indexOf("应收账款_") >= 0 || value.indexOf("预付账款_") >= 0
                || value.indexOf("应收票据_") >= 0 || value.indexOf("预付票据_") >= 0)) {
            return value.substring(value.lastIndexOf("_") + 1);
        }
        if (excelData.get("往来单位") != null) {
            return excelData.get("往来单位").get(index);
        }
        return null;
    }
}
