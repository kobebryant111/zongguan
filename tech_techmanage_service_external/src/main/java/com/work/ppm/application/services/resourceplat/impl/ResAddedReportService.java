package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.model.resourceplat.ReportJp;
import com.work.ppm.application.model.resourceplat.ReportZb;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResAddedReportService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.resourceplat.ResAddedConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源新增总办技评报表导出
 * service
 * @date 2021/11/04
 */
@Service("ResAddedReportService")
public class ResAddedReportService extends BaseDaoSupper implements IResAddedReportService {

    /**
     * RESADDED
     */
    private static String RESADDED = "com.work.ppm.application.model.resourceplat.ResAdded";

    /**
     * EOSDICTENTRY
     */
    private static String EOSDICTENTRY = "com.work.ppm.application.model.systemmanager.EosDictEntry";

    /**
     * myBatisDao
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 生成报表
     * @param wb excel工作簿
     * @param resAdd 实体
     */
    public void generateReport(HSSFWorkbook wb, ResAdded resAdd) {
        HSSFCellStyle style = getStyleOne(wb);
        HSSFCellStyle styleTwo = getStyleTwo(wb);

        //技评维度sheet
        sheetOne(resAdd, wb, style, styleTwo);

        //总办维度sheet
        sheetTwo(resAdd, wb, style, styleTwo);
    }

    /**
     * 设置第一个表头样式
     *
     * @param wb
     * @return HSSFCellStyle
     */
    private HSSFCellStyle getStyleOne(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);

        HSSFFont headerFont = wb.createFont();// 创建字体样式
        headerFont.setFontName("宋体");// 设置字体类型
        headerFont.setFontHeight((short) 220);// 设置字体大小
        style.setFont(headerFont);// 为标题样式设置字体样式
        return style;
    }

    /**
     * 设置数据样式
     *
     * @param wb
     * @return HSSFCellStyle
     */
    private HSSFCellStyle getStyleTwo(HSSFWorkbook wb) {
        HSSFCellStyle styleTwo = wb.createCellStyle();
        styleTwo.setAlignment(HorizontalAlignment.CENTER);
        styleTwo.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTwo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        styleTwo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTwo.setBorderLeft(BorderStyle.THIN);
        styleTwo.setBorderRight(BorderStyle.THIN);
        styleTwo.setBorderTop(BorderStyle.THIN);
        styleTwo.setBorderBottom(BorderStyle.THIN);

        HSSFFont headerFont = wb.createFont();// 创建字体样式
        headerFont.setFontName("宋体");// 设置字体类型
        headerFont.setFontHeight((short) 220);// 设置字体大小
        styleTwo.setFont(headerFont);// 为标题样式设置字体样式
        return styleTwo;
    }

    /**
     * 设置列样式
     *
     * @param sheet
     */
    private void setColumnstyle(HSSFSheet sheet) {
        //设置列宽
        sheet.setColumnWidth(0, 1024 * 3);
        sheet.setColumnWidth(1, 1024 * 5);
        sheet.setColumnWidth(2, 1024 * 5);
        sheet.setColumnWidth(3, 1024 * 5);
        sheet.setColumnWidth(4, 1024 * 10);
        sheet.setColumnWidth(5, 1024 * 10);
    }

    /**
     * 技评维度sheet
     *
     * @param resAdded
     * @param wb
     */
    private void sheetOne(ResAdded resAdded, HSSFWorkbook wb, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        HSSFSheet sheet = wb.createSheet("技评统计维度");
        int tableIndex;
        tableIndex = sheetOneTableOne(resAdded, sheet, style, styleTwo);

        sheetOneTableTwo(resAdded, sheet, tableIndex, style, styleTwo);

    }

    /**
     * 技评报表-软/硬资源成本
     * @param resAdded
     * @param sheet
     * @param style
     * @param styleTwo
     * @return beginIndex
     */
    private int sheetOneTableOne(ResAdded resAdded, HSSFSheet sheet, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        sheetOneTableOneHead(resAdded, sheet, style);
        int beginIndex = 2;

        List<ReportJp> resCoonfigList = new ArrayList<>();
        List<ReportJp> list = myBatisDao.selectListT(RESADDED, resAdded.getId(), "resConfigMain");
        ReportJp report;
        for (int i = 0; i < list.size(); i++) {
            report = list.get(i);
            String soft = myBatisDao.selectClass(RESADDED, report.getId(), "resConfigSoft");
            String part = myBatisDao.selectClass(RESADDED, report.getId(), "resConfigPart");
            report.setModelconfig(part);
            report.setStorageConfig(soft);
            resCoonfigList.add(report);
        }
        beginIndex = drawColumnJp(styleTwo, sheet, resCoonfigList, beginIndex);
        return beginIndex;
    }

    /**
     * 生成技评统计维度报表-系统计算资源表表头
     *
     * @param resAdd
     * @param sheet
     * @param style
     */
    private void sheetOneTableOneHead(ResAdded resAdd, HSSFSheet sheet, HSSFCellStyle style) {
        //第三步添加第一行，合并单元格6列，背景色黄色，居中展示
        //设置列宽
        setColumnstyle(sheet);
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        CellRangeAddress regin = new CellRangeAddress(0, 0, 0, 5);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue(resAdd.getAppName() + "计算资源配置");
        cell.setCellStyle(style);
        sheet.addMergedRegion(regin);
        String[] columnNumberMain = {"所属中心", "节点名称", "节点类型", "选型类型", "选型配置", "软件配置"};
        //创建第二行
        HSSFRow rowTwo = sheet.createRow(1);
        for (int i = 0; i < columnNumberMain.length; i++) {
            HSSFCell cellMain = rowTwo.createCell((short) i);
            cellMain.setCellValue(columnNumberMain[i]);
            cellMain.setCellStyle(style);
        }
    }

    /**
     * 技评报表-存储资源成本
     *
     * @param resAdded
     * @param sheet
     * @param beginIndex
     * @param style
     * @param styleTwo
     */
    private void sheetOneTableTwo(ResAdded resAdded, HSSFSheet sheet, int beginIndex, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        beginIndex = sheetOneTableTwoHead(resAdded, sheet, beginIndex, style);

        List<ReportJp> storageResList = myBatisDao.selectListT(RESADDED, resAdded.getId(), "storageRepoetJp");
        List<ReportJp> storageList = reConfigStorageList(storageResList);
        drawColumnJp(styleTwo, sheet, storageList, beginIndex);
    }

    /**
     * 生成技评统计维度报表-系统计算资源表表头
     *
     * @param resAdd
     * @param sheet
     * @param beginIndex
     * @param style
     * @return beginIndex
     */
    private int sheetOneTableTwoHead(ResAdded resAdd, HSSFSheet sheet, int beginIndex, HSSFCellStyle style) {
        //和上一个表格空一行
        beginIndex = beginIndex + 2;

        CellRangeAddress regin = new CellRangeAddress(beginIndex, beginIndex, 0, 5);

        //设置列宽
        setColumnstyle(sheet);
        //创建第一行
        HSSFRow row = sheet.createRow(beginIndex);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue(resAdd.getAppName() + "存储资源成本");
        cell.setCellStyle(style);
        sheet.addMergedRegion(regin);
        String[] columnNumberMain = {"所属中心", "节点名称", "节点类型", "选型类型", "选型配置", "存储容量"};
        //创建第二行
        HSSFRow rowTwo = sheet.createRow(beginIndex + 1);
        for (int i = 0; i < columnNumberMain.length; i++) {
            HSSFCell cellMain = rowTwo.createCell((short) i);
            cellMain.setCellValue(columnNumberMain[i]);
            cellMain.setCellStyle(style);
        }
        //计算值开始行
        beginIndex = beginIndex + 2;
        return beginIndex;
    }

    /**
     * 重新计算存储成本列表
     *
     * @param list
     * @return List<ReportJp>
     */
    private List<ReportJp> reConfigStorageList(List<ReportJp> list) {
        List<ReportJp> newList = new ArrayList<>();
        ReportJp reportJp;
        for (int i = 0; i < list.size(); i++) {
            reportJp = list.get(i);
            String storageConfig = "";
            storageConfig = stringBufferConfig(reportJp, storageConfig);
            reportJp.setStorageConfig(storageConfig);
            newList.add(reportJp);
        }
        return newList;
    }

    /**
     * 拼接容量配置
     * @param reportJp
     * @param storageConfig
     * @return storageConfig
     */
    private String stringBufferConfig(ReportJp reportJp, String storageConfig) {
        if (reportJp.getNasSize() != 0) {
            storageConfig = "NAS存储：" + reportJp.getNasSize() + "G";
            if (reportJp.getSanSize() != 0) {
                storageConfig += ",SAN存储：" + reportJp.getSanSize() + "G,归档SAN：" + reportJp.getSanSpecialSize() + "G";
            } else {
                if (reportJp.getDbSize() != 0) {
                    storageConfig += ",数据库容量：" + reportJp.getDbSize() + "G";
                }
            }
        } else {
            storageConfig = stringBufferWitnNasEmpty(reportJp, storageConfig);
        }
        return storageConfig;
    }

    /**
     * nas为空时拼接容量
     * @param reportJp
     * @param storageConfig
     * @return storageConfig
     */
    private String stringBufferWitnNasEmpty(ReportJp reportJp, String storageConfig) {
        if (reportJp.getSanSize() != 0) {
            storageConfig = "SAN存储：" + reportJp.getSanSize() + "G,归档SAN：" + reportJp.getSanSpecialSize() + "G";
            if (reportJp.getDbSize() != 0) {
                storageConfig += ",数据库容量：" + reportJp.getDbSize() + "G";
            }
        } else {
            if (reportJp.getDbSize() != 0) {
                storageConfig = "数据库容量：" + reportJp.getDbSize() + "G";
            }
        }
        return storageConfig;
    }

    /**
     * 技评报表-绘制数值行
     *
     * @param styleTwo
     * @param sheet
     * @param list
     * @param beginIndex
     * @return beginIndex
     */
    private int drawColumnJp(HSSFCellStyle styleTwo, HSSFSheet sheet, List<ReportJp> list, int beginIndex) {
        ReportJp report;
        HSSFRow row;
        for (int i = 0; i < list.size(); i++) {
            report = list.get(i);
            if (StringUtils.isNotEmpty(report.getNodeName())) {
                row = sheet.createRow(beginIndex);
                //第一列
                HSSFCell cellMain = row.createCell((short) 0);
                cellMain.setCellValue(report.getOwnCenterName());
                cellMain.setCellStyle(styleTwo);
                //第二列
                cellMain = row.createCell((short) 1);
                cellMain.setCellValue(report.getNodeName());
                cellMain.setCellStyle(styleTwo);
                //第三列
                cellMain = row.createCell((short) 2);
                cellMain.setCellValue(report.getNodeTypeName());
                cellMain.setCellStyle(styleTwo);
                //第四列
                cellMain = row.createCell((short) 3);
                cellMain.setCellValue(report.getModelTypeName());
                cellMain.setCellStyle(styleTwo);
                //五列
                cellMain = row.createCell((short) 4);
                cellMain.setCellValue(report.getModelconfig());
                cellMain.setCellStyle(styleTwo);
                //第六列
                cellMain = row.createCell((short) 5);
                cellMain.setCellValue(report.getStorageConfig());
                cellMain.setCellStyle(styleTwo);

                beginIndex += 1;
            }
        }
        return beginIndex;
    }

    /**
     * 总办维度sheet
     *
     * @param resAdd
     * @param wb
     * @param style
     * @param styleTwo
     */
    private void sheetTwo(ResAdded resAdd, HSSFWorkbook wb, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        HSSFSheet sheet = wb.createSheet("总办统计维度");
        int tableIndex = 0;
        tableIndex = sheetTwoTableOne(resAdd, sheet, style, styleTwo);

        tableIndex = sheetTwoTableTwo(resAdd, sheet, tableIndex, style, styleTwo);

        sheetTwoTableThree(resAdd, sheet, tableIndex, style, styleTwo);

    }

    /**
     * 总办报表-系统硬件资源成本
     *
     * @param resAdded
     * @param sheet
     * @param style
     * @param styleTwo
     * @return beginIndex
     */
    private int sheetTwoTableOne(ResAdded resAdded, HSSFSheet sheet, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        //-----------------------------------拼接第二个SHEET-----------------------------
        //设置硬件资源成本表头
        sheetTwoTableOneHead(resAdded, sheet, style);
        //设置硬件资源成本表值
        List<EosDictEntry> dictEntries = getOwnCenterList();

        int beginIndex = 2;
        int firstIndex = beginIndex;

        EosDictEntry eosDictEntry;
        for (int i = 0; i < dictEntries.size(); i++) {
            eosDictEntry = dictEntries.get(i);

            Map<String, Object> param = getReportParam(resAdded, eosDictEntry);

            //物理机套餐
            List<ReportZb> physicalResList = myBatisDao.selectListT(RESADDED, param, "physicalResReportZb");
            beginIndex = drawColumn(styleTwo, sheet, physicalResList, beginIndex);

            //虚拟机资源
            List<ReportZb> virtualResList = myBatisDao.selectListT(RESADDED, param, "virtualResReportZb");
            beginIndex = drawColumn(styleTwo, sheet, virtualResList, beginIndex);

            //Paas套餐
            List<ReportZb> paasResList = myBatisDao.selectListT(RESADDED, param, "paasResReportZb");
            beginIndex = drawColumn(styleTwo, sheet, paasResList, beginIndex);

            //专用设备套餐
            List<ReportZb> specailResList = myBatisDao.selectListT(RESADDED, param, "specialResReport");
            beginIndex = drawColumn(styleTwo, sheet, specailResList, beginIndex);

            //物理机配件
            List<ReportZb> physicalPartList = myBatisDao.selectListT(RESADDED, param, "physicalPartReportZb");
            beginIndex = drawColumn(styleTwo, sheet, physicalPartList, beginIndex);
        }
        if (firstIndex != beginIndex) {
            drawCountRow(styleTwo, sheet, beginIndex, 2 + 1);
        }
        return beginIndex;
    }

    /**
     * 生成总办统计维度报表-硬件资源成本表头
     *
     * @param resAdd
     * @param sheet
     * @param style
     */
    private void sheetTwoTableOneHead(ResAdded resAdd, HSSFSheet sheet, HSSFCellStyle style) {
        CellRangeAddress regin = new CellRangeAddress(0, 0, 0, 5);

        //设置列宽
        setColumnstyle(sheet);
        //第一个表格-硬件资源成本
        HSSFRow sheetTwoRow = sheet.createRow(0);
        HSSFCell sheetTwoCell = sheetTwoRow.createCell((short) 0);
        sheetTwoCell.setCellValue(resAdd.getAppName() + "硬件资源成本");
        sheetTwoCell.setCellStyle(style);
        sheet.addMergedRegion(regin);

        //设置存储成本表头
        String[] columnZbReMem = {"所属中心", "选型类型", "选型配置",
                "数量", ResAddedConstant.HEAD_PRICE, ResAddedConstant.TOTAL_PRICE};
        //创建第二行
        HSSFRow rowZbRe = sheet.createRow(1);
        for (int i = 0; i < columnZbReMem.length; i++) {
            HSSFCell cellMain = rowZbRe.createCell((short) i);
            cellMain.setCellValue(columnZbReMem[i]);
            cellMain.setCellStyle(style);
        }
    }

    /**
     * 获取所属中心列表
     * @return List<EosDictEntry>
     */
    private List<EosDictEntry> getOwnCenterList() {
        Map<String, Object> dictMap = new HashMap<>();
        dictMap.put("dicttypeid", ResAddedConstant.RES_OWNCENTER);
        return myBatisDao.selectListT(EOSDICTENTRY, dictMap, "getList");
    }

    /**
     * 设置总办报表查询条件
     *
     * @param resAdded
     * @param eosDictEntry
     * @return param
     */
    private Map<String, Object> getReportParam(ResAdded resAdded, EosDictEntry eosDictEntry) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", resAdded.getId());
        param.put("ownCenter", eosDictEntry.getDictid());
        param.put("ownCenterName", eosDictEntry.getDictname());
        return param;
    }

    /**
     * 总办报表-绘制数值行
     *
     * @param styleTwo
     * @param sheet
     * @param list
     * @param beginIndex
     * @return beginIndex
     */
    private int drawColumn(HSSFCellStyle styleTwo, HSSFSheet sheet, List<ReportZb> list, int beginIndex) {
        ReportZb report;
        HSSFRow row = null;
        for (int i = 0; i < list.size(); i++) {
            report = list.get(i);
            if (StringUtils.isNotEmpty(report.getDescription()) && !"0C0G0G".equals(report.getDescription())
                    && !"0".equals(report.getCount())) {
                row = sheet.createRow(beginIndex);
                //第一列
                HSSFCell cellMain = row.createCell((short) 0);
                cellMain.setCellValue(report.getOwnCenterName());
                cellMain.setCellStyle(styleTwo);
                //第二列
                cellMain = row.createCell((short) 1);
                cellMain.setCellValue(report.getType());
                cellMain.setCellStyle(styleTwo);
                //第三列
                cellMain = row.createCell((short) 2);
                cellMain.setCellValue(report.getDescription());
                cellMain.setCellStyle(styleTwo);
                //第四列
                cellMain = row.createCell((short) 3);
                cellMain.setCellValue(report.getCount());
                cellMain.setCellStyle(styleTwo);
                //五列
                cellMain = row.createCell((short) 4);
                cellMain.setCellValue(report.getPrice());
                cellMain.setCellStyle(styleTwo);
                //第六列
                cellMain = row.createCell((short) 5);
                cellMain.setCellValue(report.getTotalPrice());
                cellMain.setCellStyle(styleTwo);

                beginIndex += 1;
            }
        }
        return beginIndex;
    }

    /**
     * 绘制合计行，利用公式计算总价列的和
     *
     * @param styleTwo
     * @param sheet
     * @param beginIndex
     * @param firstIndex
     */
    private void drawCountRow(HSSFCellStyle styleTwo, HSSFSheet sheet, int beginIndex, int firstIndex) {
        //设置总计行
        HSSFRow row = sheet.createRow(beginIndex);

        HSSFCell cellMain = row.createCell((short) 0);
        cellMain.setCellValue("合计");
        cellMain.setCellStyle(styleTwo);

        cellMain = row.createCell((short) 1);
        cellMain.setCellValue("合计");
        cellMain.setCellStyle(styleTwo);

        cellMain = row.createCell((short) 2);
        cellMain.setCellValue("合计");
        cellMain.setCellStyle(styleTwo);

        cellMain = row.createCell((short) 3);
        cellMain.setCellValue("合计");
        cellMain.setCellStyle(styleTwo);

        cellMain = row.createCell((short) 4);
        cellMain.setCellValue("合计");
        cellMain.setCellStyle(styleTwo);

        cellMain = row.createCell((short) 5);
        String function = "SUM(F" + firstIndex + ":" + "F" + beginIndex + ")";
        cellMain.setCellFormula(function);
        cellMain.setCellStyle(styleTwo);

        //合并单元格
        CellRangeAddress reginTT = new CellRangeAddress(beginIndex, beginIndex, 0, 4);
        sheet.addMergedRegion(reginTT);
    }

    /**
     * 总办报表-存储资源成本
     * @param resAdded
     * @param sheet
     * @param beginIndex
     * @param style
     * @param styleTwo
     * @return beginIndex
     */
    private int sheetTwoTableTwo(ResAdded resAdded, HSSFSheet sheet, int beginIndex, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        //设置硬件资源成本表头
        beginIndex = sheetTwoTableTwoHead(resAdded, sheet, beginIndex, style);
        //设置硬件资源成本表值

        //所属中心列表
        List<EosDictEntry> dictEntries = getOwnCenterList();
        int firstIndex = beginIndex;

        EosDictEntry eosDictEntry;
        for (int i = 0; i < dictEntries.size(); i++) {
            eosDictEntry = dictEntries.get(i);

            Map<String, Object> param = getReportParam(resAdded, eosDictEntry);

            //san
            List<ReportZb> sanList = getSanList(param, "sanReportZb");
            beginIndex = drawColumn(styleTwo, sheet, sanList, beginIndex);

            //归档san
            List<ReportZb> specialSanList = getSanList(param, "specailSanReportZb");
            beginIndex = drawColumn(styleTwo, sheet, specialSanList, beginIndex);

            //nas
            List<ReportZb> nasList = myBatisDao.selectListT(RESADDED, param, "nasReportZb");
            beginIndex = drawColumn(styleTwo, sheet, nasList, beginIndex);

            //数据库容量
            List<ReportZb> dbList = myBatisDao.selectListT(RESADDED, param, "dbReportZb");
            beginIndex = drawColumn(styleTwo, sheet, dbList, beginIndex);
        }
        if (firstIndex != beginIndex) {
            drawCountRow(styleTwo, sheet, beginIndex, firstIndex + 1);
        }
        return beginIndex;
    }

    /**
     * 生成"总办统计维度报表-软件资源成本表头
     *
     * @param resAdd
     * @param sheet
     * @param beginIndex
     * @param style
     * @return beginIndex
     */
    private int sheetTwoTableTwoHead(ResAdded resAdd, HSSFSheet sheet, int beginIndex, HSSFCellStyle style) {
        //和上一个表格空一行
        beginIndex = beginIndex + 2;

        CellRangeAddress regin = new CellRangeAddress(beginIndex, beginIndex, 0, 5);

        //设置列宽
        setColumnstyle(sheet);
        //第一个表格-存储资源成本
        HSSFRow sheetTwoRow = sheet.createRow(beginIndex);
        HSSFCell sheetTwoCell = sheetTwoRow.createCell((short) 0);
        sheetTwoCell.setCellValue(resAdd.getAppName() + "存储资源成本");
        sheetTwoCell.setCellStyle(style);
        sheet.addMergedRegion(regin);

        //设置存储成本表头
        String[] columnZbReMem = {"所属中心", "选型类型", "选型配置", "容量(GB)", "单价（元）", "总价（元）"};
        //创建第二行
        HSSFRow rowZbRe = sheet.createRow(beginIndex + 1);
        for (int i = 0; i < columnZbReMem.length; i++) {
            HSSFCell cellMain = rowZbRe.createCell((short) i);
            cellMain.setCellValue(columnZbReMem[i]);
            cellMain.setCellStyle(style);
        }
        //计算值开始行
        beginIndex = beginIndex + 2;
        return beginIndex;
    }

    /**
     * 计算SAN/归档SAN总价之后的SAN列表
     *
     * @param param
     * @param sqlName
     * @return List<ReportZb>
     */
    private List<ReportZb> getSanList(Map<String, Object> param, String sqlName) {
        List<ReportZb> newSanList = new ArrayList<>();
        List<ReportZb> sanList = myBatisDao.selectListT(RESADDED, param, sqlName);
        ReportZb sanReport;
        for (int j = 0; j < sanList.size(); j++) {
            sanReport = sanList.get(j);
            if (StringUtils.isNotEmpty(sanReport.getDescription())) {
                BigDecimal increase = BigDecimal.valueOf(1000);
                BigDecimal count = new BigDecimal(sanReport.getCount());
                BigDecimal price = new BigDecimal(sanReport.getPrice());

                float totalPrice = count.divide(increase).multiply(price).setScale(1,BigDecimal.ROUND_UP).floatValue();
                sanReport.setTotalPrice(totalPrice);
                newSanList.add(sanReport);
            }
        }
        return newSanList;
    }

    /**
     * 总办报表-软件资源成本
     *
     * @param resAdded
     * @param sheet
     * @param beginIndex
     * @param style
     * @param styleTwo
     */
    private void sheetTwoTableThree(ResAdded resAdded, HSSFSheet sheet, int beginIndex, HSSFCellStyle style
            , HSSFCellStyle styleTwo) {
        //设置硬件资源成本表头
        beginIndex = sheetTwoTableThreeHead(resAdded, sheet, beginIndex, style);
        //设置硬件资源成本表值

        //所属中心列表
        Map<String, Object> dictMap = new HashMap<>();
        dictMap.put("dicttypeid", ResAddedConstant.RES_OWNCENTER);
        List<EosDictEntry> dictEntries = myBatisDao.selectListT(EOSDICTENTRY, dictMap, "getList");
        int firstIndex = beginIndex;

        EosDictEntry eosDictEntry;
        for (int i = 0; i < dictEntries.size(); i++) {
            eosDictEntry = dictEntries.get(i);

            Map<String, Object> param = getReportParam(resAdded, eosDictEntry);

            //软件
            List<ReportZb> softList = myBatisDao.selectListT(RESADDED, param, "softWareReportZb");
            beginIndex = drawColumn(styleTwo, sheet, softList, beginIndex);

        }
        if (firstIndex != beginIndex) {
            drawCountRow(styleTwo, sheet, beginIndex, firstIndex + 1);
        }
    }

    /**
     * 生成总办统计维度报表-存储资源成本表头
     *
     * @param resAdd
     * @param sheet
     * @param beginIndex
     * @param style
     * @return beginIndex
     */
    private int sheetTwoTableThreeHead(ResAdded resAdd, HSSFSheet sheet, int beginIndex, HSSFCellStyle style) {
        //和上一个表格空一行
        beginIndex = beginIndex + 2;

        CellRangeAddress regin = new CellRangeAddress(beginIndex, beginIndex, 0, 5);

        //设置列宽
        setColumnstyle(sheet);
        //第一个表格-存储资源成本
        HSSFRow sheetTwoRow = sheet.createRow(beginIndex);
        HSSFCell sheetTwoCell = sheetTwoRow.createCell((short) 0);
        sheetTwoCell.setCellValue(resAdd.getAppName() + "软件资源成本");
        sheetTwoCell.setCellStyle(style);
        sheet.addMergedRegion(regin);

        //设置存储成本表头
        String[] columnZbReMem = {"所属中心", "软件名称", "软件版本", "数量", "单价（元）", "总价（元）"};
        //创建第二行
        HSSFRow rowZbRe = sheet.createRow(beginIndex + 1);
        for (int i = 0; i < columnZbReMem.length; i++) {
            HSSFCell cellMain = rowZbRe.createCell((short) i);
            cellMain.setCellValue(columnZbReMem[i]);
            cellMain.setCellStyle(style);
        }
        //计算值开始行
        beginIndex = beginIndex + 2;
        return beginIndex;
    }
}
