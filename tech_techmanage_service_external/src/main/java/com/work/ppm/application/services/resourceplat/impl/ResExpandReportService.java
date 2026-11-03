package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.services.resourceplat.IResExpandReportService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.resourceplat.ResAddedConstant;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
/**
 * 资源扩容
 *
 */
@Service("ResExpandReportService")
public class ResExpandReportService extends BaseDaoSupper implements IResExpandReportService {

    /**
     * 资源新增实体类
     */
    private static String RESADDED = "com.work.ppm.application.model.resourceplat.ResAdded";
    /**
     * 资源扩容项实体类
     */
    private static String RES_EXPAND_ITEM = "com.work.ppm.application.model.resourceplat.ResExpandItem";
    /**
     * 总价
     */
    private static String TOTALPRICE = "TOTALPRICE";
    /**
     * 价格
     */
    private static String PRICE = "PRICE";
    /**
     * 数量
     */
    private static String NUM = "NUM";
    /**
     * 配置
     */
    private static final String DESCRIBE = "DESCRIBE";
    /**
     * 所属中心
     */
    private static String OWNCENTER = "OWNCENTER";
    /**
     * 资源类型
     */
    private static String RESTYPE = "RESTYPE";
    /**
     * 节点名称
     */
    private static String CINAME = "CINAME";
    /**
     * 套餐大类
     */
    private static String CATEGORY = "CATEGORY";
    /**
     * 选型类型
     */
    private static String MODELTYPE = "MODELTYPE";
    /**
     * 套餐小类
     */
    private static String SUBCATEGORY = "SUBCATEGORY";
    /**
     *选型配置
     */
    private static String PZ = "PZ";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 资源新增接口
     */
    @Autowired
    private ResAddedService resAddedService;
    /**
     * 资源新增-扩展信息接口
     */
    @Autowired
    private ResAddedExtService resAddedExtService;
    /**
     * 字典表
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 导出资源申请详情
     *
     * @param id       申请ID
     * @param response response
     */
    public void exportResAuditReport(String id, HttpServletResponse response) {
        try(HSSFWorkbook wb = new HSSFWorkbook();) {
            //获取申请单信息
            ResAdded resAdd = myBatisDao.selectClass(RESADDED, id, ResAddedConstant.QUERYRESINFO);
            //创建工作簿，生成两个SHEET
            // 第一步 创建一个WEBBOOK，对应一个Excel文件

            // 第二步 在WEBBOOK中添加一个sheet，对应excel文件中的sheet
            HSSFSheet sheet = wb.createSheet("技评统计维度");

            //第三步添加第一行，合并单元格6列，背景色黄色，居中展示
            HSSFCellStyle style = getStyleOne(wb);
            HSSFCellStyle styleTwo = getStyleTwo(wb);
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
            forColumn(style, columnNumberMain, rowTwo);
            int seondTableRowNum = 0;
            //获取第一个表格内容
            List<Map<String, Object>> list = myBatisDao.selectListT(RES_EXPAND_ITEM, id, "queryResourceListForJP_New");
            seondTableRowNum = getSeondTableRowNum(sheet, styleTwo, list);
            HSSFRow rowFirst;
            //设置存储成本
            HSSFRow rowT = sheet.createRow(seondTableRowNum);
            CellRangeAddress reginT = new CellRangeAddress(seondTableRowNum, seondTableRowNum, 0, 5);
            for (int m = 0; m < 6; m++) {
                HSSFCell cellT = rowT.createCell((short) m);
                if (m == 0) {
                    cellT.setCellValue(resAdd.getAppName() + "存储资源成本");
                } else {
                    cellT.setCellValue("");
                }
                cellT.setCellStyle(style);
            }
            sheet.addMergedRegion(reginT);


            //设置存储成本表头
            String[] columnMem = {"所属中心", "节点名称", "节点类型", "选型类型", "选型配置", "存储容量"};
            //创建第二行
            HSSFRow rowThree = sheet.createRow(seondTableRowNum + 1);
            forColumn(style, columnMem, rowThree);
            List<Map<String, Object>> listTwo = myBatisDao.selectListT(RES_EXPAND_ITEM, id,
                    "queryResourceListMenoryForJP_New");
            for (int i = 0; i < listTwo.size(); i++) {
                getCellmain(sheet, styleTwo, listTwo, i,seondTableRowNum + 2);
            }
            rowFirst = sheet.createRow(seondTableRowNum + 2 + listTwo.size());
            //第一列
            HSSFCell cellMain = rowFirst.createCell((short) 0);
            cellMain.setCellValue("总计");
            cellMain.setCellStyle(styleTwo);
            //第二列
            cellMain = rowFirst.createCell((short) 1);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleTwo);
            //第三列
            cellMain = rowFirst.createCell((short) 2);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleTwo);
            //第四列
            cellMain = rowFirst.createCell((short) 3);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleTwo);
            //五列
            cellMain = rowFirst.createCell((short) 4);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleTwo);
            //第六列
            cellMain = rowFirst.createCell((short) 5);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleTwo);
            //合并单元格
            CellRangeAddress reginTT = new CellRangeAddress(
                    seondTableRowNum + 2 + listTwo.size(),
                    seondTableRowNum + 2 + listTwo.size(), 1, 5);
            sheet.addMergedRegion(reginTT);
            //导出总办技评数据
            this.exportZb(wb, resAdd, id);

            // 拼接下面的统计信息---------------------------------- end ------------------------------------------
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode("测试.xls", "UTF-8"))));
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 表格设置
     * @param sheet
     * @param styleTwo
     * @param list
     * @return seondTableRowNum
     */
    private int getSeondTableRowNum(HSSFSheet sheet, HSSFCellStyle styleTwo, List<Map<String, Object>> list) {
        int seondTableRowNum;
        if (list.isEmpty()) {
            //第四行
            seondTableRowNum = 3;
        } else {
            seondTableRowNum = 4 + list.size();
        }
        for (int i = 0; i < list.size(); i++) {
            getCellmain(sheet, styleTwo, list, i,2);
        }
        //添加共计
        if (!list.isEmpty()) {
            getCellmains(sheet, styleTwo, list);
        }
        return seondTableRowNum;
    }

    /**
     * 设置第二行
     * @param style
     * @param columnNumberMain
     * @param rowTwo
     */
    private void forColumn(HSSFCellStyle style, String[] columnNumberMain, HSSFRow rowTwo) {
        for (int i = 0; i < columnNumberMain.length; i++) {
            HSSFCell cellMain = rowTwo.createCell((short) i);
            cellMain.setCellValue(columnNumberMain[i]);
            cellMain.setCellStyle(style);
        }
    }

    /**
     * 添加总计
     * @param sheet
     * @param styleTwo
     * @param list
     */
    private void getCellmains(HSSFSheet sheet, HSSFCellStyle styleTwo, List<Map<String, Object>> list) {
        HSSFRow rowFirst;
        rowFirst = sheet.createRow(2 + list.size());
        //第一列
        HSSFCell cellMain = rowFirst.createCell((short) 0);
        cellMain.setCellValue("总计");
        cellMain.setCellStyle(styleTwo);
        //第二列
        cellMain = rowFirst.createCell((short) 1);
        cellMain.setCellValue("");
        cellMain.setCellStyle(styleTwo);
        //第三列
        cellMain = rowFirst.createCell((short) 2);
        cellMain.setCellValue("");
        cellMain.setCellStyle(styleTwo);
        //第四列
        cellMain = rowFirst.createCell((short) 3);
        cellMain.setCellValue("");
        cellMain.setCellStyle(styleTwo);
        //五列
        cellMain = rowFirst.createCell((short) 4);
        cellMain.setCellValue("");
        cellMain.setCellStyle(styleTwo);
        //第六列
        cellMain = rowFirst.createCell((short) 5);
        cellMain.setCellValue("");
        cellMain.setCellStyle(styleTwo);
        //合并单元格
        CellRangeAddress reginT = new CellRangeAddress(2 + list.size(), 2 + list.size(), 1, 5);
        sheet.addMergedRegion(reginT);
    }

    /**
     * 设置表格
     * @param sheet
     * @param styleTwo
     * @param list
     * @param i
     */
    private void getCellmain(HSSFSheet sheet, HSSFCellStyle styleTwo,
                             List<Map<String, Object>> list, int i,int rownum) {
        HSSFRow rowFirst;
        Map<String, Object> map = list.get(i);
        rowFirst = sheet.createRow(rownum + i);
        //第一列
        HSSFCell cellMain = rowFirst.createCell((short) 0);
        cellMain.setCellValue(null==map.get(OWNCENTER)? "" : map.get(OWNCENTER).toString());
        cellMain.setCellStyle(styleTwo);
        //第二列
        cellMain = rowFirst.createCell((short) 1);
        cellMain.setCellValue(null==map.get(CINAME)?"":map.get(CINAME).toString());
        cellMain.setCellStyle(styleTwo);
        //第三列
        cellMain = rowFirst.createCell((short) 2);
        cellMain.setCellValue(null==map.get(RESTYPE)?"":map.get(RESTYPE).toString());
        cellMain.setCellStyle(styleTwo);
        //第四列
        cellMain = rowFirst.createCell((short) 3);
        cellMain.setCellValue(null==map.get(MODELTYPE)?"":map.get(MODELTYPE).toString());
        cellMain.setCellStyle(styleTwo);
        //五列
        cellMain = rowFirst.createCell((short) 4);
        cellMain.setCellValue(null == map.get(PZ)? "" : map.get(PZ).toString());
        cellMain.setCellStyle(styleTwo);
        //第六列
        cellMain = rowFirst.createCell((short) 5);
        cellMain.setCellValue(null == map.get(DESCRIBE) ? "" : map.get(DESCRIBE).toString());
        cellMain.setCellStyle(styleTwo);
    }

    /**
     * 导出总办技评数据
     *
     * @param wb
     * @param resAdd
     * @param id
     */
    private void exportZb(HSSFWorkbook wb, ResAdded resAdd, String id) {
        HSSFCellStyle style = getStyleOne(wb);
        HSSFCellStyle styleTwo = getStyleTwo(wb);
        HSSFCellStyle styleThree = getStyleThree(wb);
        HSSFCellStyle styleFour = getStyleTwo(wb);
        CellRangeAddress regin = new CellRangeAddress(0, 0, 0, 5);
        //-----------------------------------拼接第二个SHEET-----------------------------
        HSSFSheet sheet = wb.createSheet("总办统计维度");

        //设置列宽
        setColumnstyle(sheet);
        //第一个表格-硬件资源成本
        HSSFRow sheetTwoRow = sheet.createRow(0);
        HSSFCell sheetTwoCell = sheetTwoRow.createCell((short) 0);
        sheetTwoCell.setCellValue(resAdd.getAppName() + "硬件资源成本");
        sheetTwoCell.setCellStyle(style);
        sheet.addMergedRegion(regin);


        //设置存储成本表头
        String[] columnZbReMem = {"所属中心", "选型类型", "选型配置", "数量", ResAddedConstant.HEAD_PRICE,
                ResAddedConstant.TOTAL_PRICE};
        //创建第二行
        HSSFRow rowZbRe = sheet.createRow(1);
        forColumn(style, columnZbReMem, rowZbRe);
        //查询硬件资源成本数据（资源扩充，主要是获取物理机配件）
        int seondTableRowNum = 0;
        List<Map<String, Object>> list = myBatisDao.selectListT(RES_EXPAND_ITEM, id, "queryResourceListResourceForZb");
        if (list.isEmpty()) {
            //第四行
            seondTableRowNum = 3;
        } else {
            seondTableRowNum = 4 + list.size();
        }
        HSSFRow rowFirst = null;
        BigDecimal totalNum = BigDecimal.valueOf(ResAddedConstant.O_F);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            rowFirst = sheet.createRow(2 + i);
            //第一列
            HSSFCell cellMain = rowFirst.createCell((short) 0);
            cellMain.setCellValue(null == map.get(OWNCENTER) ? "" : map.get(OWNCENTER).toString());
            cellMain.setCellStyle(styleTwo);
            //第二列
            cellMain = rowFirst.createCell((short) 1);
            cellMain.setCellValue(map.get(CATEGORY).toString());
            cellMain.setCellStyle(styleTwo);
            //第三列
            cellMain = rowFirst.createCell((short) 2);
            cellMain.setCellValue(map.get(SUBCATEGORY).toString()+","+map.get(DESCRIBE).toString());
            cellMain.setCellStyle(styleTwo);
            //第四列
            cellMain = rowFirst.createCell((short) 3);
            cellMain.setCellValue(map.get(NUM).toString());
            cellMain.setCellStyle(styleTwo);
            //五列
            cellMain = rowFirst.createCell((short) 4);
            cellMain.setCellValue(map.get(PRICE).toString());
            cellMain.setCellStyle(styleTwo);
            //第六列
            cellMain = rowFirst.createCell((short) 5);
            cellMain.setCellValue(map.get(TOTALPRICE).toString());
            cellMain.setCellStyle(styleTwo);
            //累加总和
            BigDecimal sanRelateRatioBig = new BigDecimal(map.get(TOTALPRICE).toString());
            totalNum = totalNum.add(sanRelateRatioBig);
        }
        //添加共计
        if (!list.isEmpty()) {
            rowFirst = sheet.createRow(2 + list.size());
            //第一列
            HSSFCell cellMain = rowFirst.createCell((short) 0);
            cellMain.setCellValue("总计");
            cellMain.setCellStyle(styleThree);
            //第二列
            cellMain = rowFirst.createCell((short) 1);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第三列
            cellMain = rowFirst.createCell((short) 2);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第四列
            cellMain = rowFirst.createCell((short) 3);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //五列
            cellMain = rowFirst.createCell((short) 4);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第六列
            cellMain = rowFirst.createCell((short) 5);
            if (list.isEmpty()) {
                cellMain.setCellValue("");
            } else {
                cellMain.setCellValue(totalNum.setScale(1, BigDecimal.ROUND_UP).floatValue());
            }
            HSSFDataFormat df = wb.createDataFormat();
            styleFour.setDataFormat(df.getFormat(ResAddedConstant.SYMBOL));
            cellMain.setCellStyle(styleFour);
            //合并单元格
            CellRangeAddress reginT = new CellRangeAddress(2 + list.size(), 2 + list.size(), 0, 4);
            sheet.addMergedRegion(reginT);
        }
        //导出存储资源成本
        int thirdTableRowNum = exportZbMenory(wb, resAdd, id, seondTableRowNum, sheet);
        //导出软件资源
        exportZbSoft(wb, resAdd, id, thirdTableRowNum, sheet);
    }

    /**
     * 导出总办技评存储资源成本
     *
     * @param wb
     * @param resAdd
     * @param id
     * @return thirdTableRowNum
     */
    private int exportZbMenory(HSSFWorkbook wb, ResAdded resAdd, String id, int startIndex, HSSFSheet sheet) {
        HSSFCellStyle style = getStyleOne(wb);
        HSSFCellStyle styleTwo = getStyleTwo(wb);
        HSSFCellStyle styleThree = getStyleThree(wb);
        HSSFCellStyle styleFour = getStyleTwo(wb);
        //第一个表格-硬件资源成本
        HSSFRow rowT = sheet.createRow(startIndex);
        CellRangeAddress reginT = new CellRangeAddress(startIndex, startIndex, 0, 5);
        for (int m = 0; m < 6; m++) {
            HSSFCell cellT = rowT.createCell((short) m);
            if (m == 0) {
                cellT.setCellValue(resAdd.getAppName() + "存储资源成本");
            } else {
                cellT.setCellValue("");
            }
            cellT.setCellStyle(style);
        }
        sheet.addMergedRegion(reginT);
        //设置存储成本表头
        String[] columnZbReMem = {"所属中心", "选型类型", "选型配置", "容量(GB)", "单价（元）", "总价（元）"};
        //创建第二行
        HSSFRow rowZbRe = sheet.createRow(startIndex + 1);
        forColumn(style, columnZbReMem, rowZbRe);
        int thirdTableRowNum = 0;
        List<Map<String, Object>> list = myBatisDao.selectListT(RES_EXPAND_ITEM, id, "queryResourceListMemoryForZb");
        if (list.isEmpty()) {
            //第四行
            thirdTableRowNum = startIndex + 3;
        } else {
            thirdTableRowNum = startIndex + 3 + list.size();
        }
        HSSFRow rowFirst = null;
        BigDecimal totalNum = BigDecimal.valueOf(ResAddedConstant.O_F);
        for (int i = 0; i < list.size(); i++) {
            totalNum = getBigDecimal(startIndex, sheet, styleTwo, list, totalNum, i);
        }
        //添加共计
        if (!list.isEmpty()) {
            rowFirst = sheet.createRow(startIndex + 2 + list.size());
            //第一列
            HSSFCell cellMain = rowFirst.createCell((short) 0);
            cellMain.setCellValue("总计");
            cellMain.setCellStyle(styleThree);
            //第二列
            cellMain = rowFirst.createCell((short) 1);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第三列
            cellMain = rowFirst.createCell((short) 2);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第四列
            cellMain = rowFirst.createCell((short) 3);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //五列
            cellMain = rowFirst.createCell((short) 4);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第六列
            cellMain = rowFirst.createCell((short) 5);
            if (list.isEmpty()) {
                cellMain.setCellValue("");
            } else {
                cellMain.setCellValue(totalNum.setScale(1, BigDecimal.ROUND_UP).floatValue());
            }
            HSSFDataFormat df = wb.createDataFormat();
            styleFour.setDataFormat(df.getFormat("#,##0.0"));
            cellMain.setCellStyle(styleFour);
            //合并单元格
            CellRangeAddress reginTT = new CellRangeAddress(
                    startIndex + 2 + list.size(),
                    startIndex + 2 + list.size(), 0, 4);
            sheet.addMergedRegion(reginTT);
        }
        return thirdTableRowNum + 1;
    }

    /**
     * 添加列
     *
     * @param startIndex
     * @param sheet
     * @param styleTwo
     * @param list
     * @param totalNum
     * @param i
     * @return totalNum
     */
    private BigDecimal getBigDecimal(int startIndex, HSSFSheet sheet, HSSFCellStyle styleTwo,
                                     List<Map<String, Object>> list, BigDecimal totalNum, int i) {
        HSSFRow rowFirst;
        Map<String, Object> map = list.get(i);
        rowFirst = sheet.createRow(startIndex + 2 + i);
        //第一列
        HSSFCell cellMain = rowFirst.createCell((short) 0);
        cellMain.setCellValue(null == map.get(OWNCENTER) ? "" : map.get(OWNCENTER).toString());
        cellMain.setCellStyle(styleTwo);
        //第二列
        cellMain = rowFirst.createCell((short) 1);
        cellMain.setCellValue(map.get(CATEGORY).toString());
        cellMain.setCellStyle(styleTwo);
        //第三列
        cellMain = rowFirst.createCell((short) 2);
        cellMain.setCellValue(map.get(DESCRIBE).toString());
        cellMain.setCellStyle(styleTwo);
        //第四列
        cellMain = rowFirst.createCell((short) 3);
        cellMain.setCellValue(map.get(NUM).toString());
        cellMain.setCellStyle(styleTwo);
        //五列
        cellMain = rowFirst.createCell((short) 4);
        cellMain.setCellValue(map.get(PRICE).toString());
        cellMain.setCellStyle(styleTwo);
        //第六列
        cellMain = rowFirst.createCell((short) 5);
        cellMain.setCellValue(map.get(TOTALPRICE).toString());
        cellMain.setCellStyle(styleTwo);
        //累加总和
        BigDecimal sanRelateRatioBig = new BigDecimal(map.get(TOTALPRICE).toString());
        totalNum = totalNum.add(sanRelateRatioBig);
        return totalNum;
    }

    /**
     * 导出总办技评软件资源成本
     *
     * @param wb
     * @param resAdd
     * @param id
     */
    private void exportZbSoft(HSSFWorkbook wb, ResAdded resAdd, String id, int startIndex, HSSFSheet sheet) {
        HSSFCellStyle style = getStyleOne(wb);
        HSSFCellStyle styleTwo = getStyleTwo(wb);
        HSSFCellStyle styleThree = getStyleThree(wb);
        HSSFCellStyle styleFour = getStyleTwo(wb);
        //第一个表格-硬件资源成本
        HSSFRow rowT = sheet.createRow(startIndex);
        CellRangeAddress reginT = new CellRangeAddress(startIndex, startIndex, 0, 5);
        for (int m = 0; m < 6; m++) {
            HSSFCell cellT = rowT.createCell((short) m);
            if (m == 0) {
                cellT.setCellValue(resAdd.getAppName() + "软件资源成本");
            } else {
                cellT.setCellValue("");
            }
            cellT.setCellStyle(style);
        }
        sheet.addMergedRegion(reginT);
        //设置存储成本表头
        String[] columnZbReMem = {ResAddedConstant.OWNCENTERNAME, ResAddedConstant.NAME,
                "软件版本", "数量", "单价（元）", "总价（元）"};
        //创建第二行
        HSSFRow rowZbRe = sheet.createRow(startIndex + 1);
        forColumn(style, columnZbReMem, rowZbRe);
        List<Map<String, Object>> list = myBatisDao.selectListT(RES_EXPAND_ITEM, id, "queryResourceListSoftForZb");
        HSSFRow rowFirst = null;
        BigDecimal totalNum = BigDecimal.valueOf(ResAddedConstant.O_F);
        for (int i = 0; i < list.size(); i++) {
            totalNum = getBigDecimal(startIndex, sheet, styleTwo, list, totalNum, i);
        }
        //添加共计
        if (!list.isEmpty()) {
            rowFirst = sheet.createRow(startIndex + 2 + list.size());
            //第一列
            HSSFCell cellMain = rowFirst.createCell((short) 0);
            cellMain.setCellValue("总计");
            cellMain.setCellStyle(styleThree);
            //第二列
            cellMain = rowFirst.createCell((short) 1);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第三列
            cellMain = rowFirst.createCell((short) 2);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第四列
            cellMain = rowFirst.createCell((short) 3);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //五列
            cellMain = rowFirst.createCell((short) 4);
            cellMain.setCellValue("");
            cellMain.setCellStyle(styleThree);
            //第六列
            cellMain = rowFirst.createCell((short) 5);
            if (list.isEmpty()) {
                cellMain.setCellValue("");
            } else {
                cellMain.setCellValue(totalNum.setScale(1, BigDecimal.ROUND_UP).floatValue());
            }
            HSSFDataFormat df = wb.createDataFormat();
            styleFour.setDataFormat(df.getFormat("#,##0.0"));
            cellMain.setCellStyle(styleFour);
            //合并单元格
            CellRangeAddress reginTT = new CellRangeAddress(
                    startIndex + 2 + list.size(),
                    startIndex + 2 + list.size(), 0, 4);
            sheet.addMergedRegion(reginTT);
        }
    }

    /**
     * 设置第一个表头样式
     *
     * @param wb
     * @return style
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
     * @return styleTwo
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
     * 设置数据样式
     *
     * @param wb
     * @return styleTwo
     */
    private HSSFCellStyle getStyleThree(HSSFWorkbook wb) {
        HSSFCellStyle styleTwo = wb.createCellStyle();
        styleTwo.setAlignment(HorizontalAlignment.RIGHT);
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

}
