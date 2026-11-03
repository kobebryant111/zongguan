package com.work.ppm.commons.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.HrProject;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import edu.emory.mathcs.backport.java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * excel工具类
 */
@Slf4j
public class ExcelUtil {
    /**
     * 导出
     * @param list 集合
     * @param pojoClass 类
     * @param fileName 文件名称
     * @param response response
     */
    public static void exportExcel(List<?>list , Class<?> pojoClass, String fileName,  HttpServletResponse response) {
        exportExcel(list,null,null,pojoClass,fileName,true,response);
    }

    /**
     * 导出
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportToExcel(List<List<HrProject>> list , Class<?> pojoClass, String fileName, HttpServletResponse response) {
        exportToExcel(list,null,null,pojoClass,fileName,true,response);
    }

    /**
     * 年度考勤导出
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void mapToExcel(List<Map<Object, Object>> list , Class<?> pojoClass, String fileName, HttpServletResponse response) {
        mapToExcel(list,null,null,pojoClass,fileName,true,response);
    }
    /**
     * 导出
     * @param list 集合
     * @param title 标题
     * @param sheetName sheet名
     * @param pojoClass 类
     * @param fileName 文件名称
     * @param isCreateHeader 是否创建表头
     * @param response response
     */
    public static void exportExcel(List<?>list , String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaltExport(list,pojoClass,fileName,response, exportParams);
    }

    public static void exportToExcel(List<List<HrProject>>list , String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet(fileName);
        sheet.setColumnWidth(4, 45 * 256); // 第一列宽度45个字符
        final Row headerRow = sheet.createRow(0);
        headerRow.setHeight((short) (25 * 20)); // 表头行高 25 榜
        // 创建表头
        Cell depidCell = headerRow.createCell(0);
        depidCell.setCellValue("研发部门");
        Cell nameCell = headerRow.createCell(1);
        nameCell.setCellValue("姓名");
        Cell badgeCell = headerRow.createCell(2);
        badgeCell.setCellValue("工号");
        Cell natureCell = headerRow.createCell(3);
        natureCell.setCellValue("研发人员性质");
        Cell pjcCell = headerRow.createCell(4);
        pjcCell.setCellValue("研发项目");
        Cell proCell = headerRow.createCell(5);
        proCell.setCellValue("占比");
        final List<PpmKqRdCalendar> rdCalendarList2 = list.get(0).get(0).getRdCalendarList();
        for (int i = 0; i < rdCalendarList2.size(); i++) {
            final Cell cell1 = headerRow.createCell(i+6);
            cell1.setCellValue(rdCalendarList2.get(i).getDayOfMonth() + "号");
        }
        sheet.createFreezePane(0,1); // 冻结首行
        // 当前行号
        int rowNum = 1;

        // 遍历数据
        for (int i = 0; i < list.size(); i++) {
            final List<HrProject> hrProjects = list.get(i);
            final Row attendanceRow = sheet.createRow(rowNum++);
            attendanceRow.setHeight((short) (25 * 20));// 表头行高 25 榜
            String nature = "";
            switch (hrProjects.get(0).getNature()) {
                case "0": {
                    nature = "全职100%";
                    break;
                }
                case "1": {
                    nature = "兼职研发1[85%-100%)";
                    break;
                }
                case "2": {
                    nature = "兼职研发2[70%-85%)";
                    break;
                }
                case "3": {
                    nature = "兼职研发3[50%-70%)";
                    break;
                }
                default:
                    break;
            }
//            attendanceRow.createCell(0).setCellValue(hrProjects.get(0).getUsername()
//                    + "(" + hrProjects.get(0).getBadge() + ")" + "["+ nature +"]" ); // 人员信息
            final List<PpmKqRdCalendar> rdCalendarList = hrProjects.get(0).getRdCalendarList();
            for (int j = 0; j < rdCalendarList.size(); j++) {
                String AttendanceOrno = "";
                switch (rdCalendarList.get(j).getAttendanceOrno()) {
                    case "0": {
                        AttendanceOrno = "未出勤";
                        break;
                    }
                    case "1": {
                        AttendanceOrno = "出勤";
                        break;
                    }
                    case "2": {
                        AttendanceOrno = "非工作日";
                        break;
                    }
                    default:
                        break;
                }
                attendanceRow.createCell(j+6).setCellValue(AttendanceOrno);
            }
            for (int l = 0; l < hrProjects.size(); l++) {
                final Row projectRow = sheet.createRow(rowNum++);
                projectRow.setHeight((short) (25 * 20));
/*                String projectState = "";
                switch (hrProjects.get(l).getProjectState()) {
                    case "0": {
                        projectState = "立项中";
                        break;
                    }
                    case "1": {
                        projectState = "进行中";
                        break;
                    }
                    case "2": {
                        projectState = "收尾中";
                        break;
                    }
                    case "3": {
                        projectState = "暂停";
                        break;
                    }
                    case "4": {
                        projectState = "废止";
                        break;
                    }
                    case "9": {
                        projectState = "已完结";
                        break;
                    }
                }*/
                projectRow.createCell(0).setCellValue(hrProjects.get(l).getChargeName());
                projectRow.createCell(1).setCellValue(hrProjects.get(l).getUsername());
                projectRow.createCell(2).setCellValue(hrProjects.get(l).getBadge());
                projectRow.createCell(3).setCellValue(nature);

/*                projectRow.createCell(4).setCellValue("["+ projectState + "]"
                        + hrProjects.get(l).getProjectName() + "(" + hrProjects.get(l).getProjectCode() + ")"
                + "项目合同预算总金额(万元):" + hrProjects.get(l).getBudgetAmount());*/
                projectRow.createCell(4).setCellValue(hrProjects.get(l).getProjectName()+ "(" + hrProjects.get(l).getProjectCode() + ")");

                final BigDecimal bigDecimal = new BigDecimal(String.valueOf(hrProjects.get(l).getProportion()));
                final BigDecimal multiply = bigDecimal.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
                projectRow.createCell(5).setCellValue(multiply+"%");
                final List<PpmKqRdCalendar> rdCalendarList1 = hrProjects.get(l).getRdCalendarList();
                for (int j = 0; j < rdCalendarList1.size(); j++) {
                    projectRow.createCell(j+6).setCellValue(rdCalendarList1.get(j).getAttendanceTime().toString());
                }
            }

        }
        if(null != workbook) {
            downloadExcel(fileName,response, workbook);
        }
    }
    public static void mapToExcel(List<Map<Object, Object>> dataList , String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(fileName);

        // 确认列名
        Set<Integer> monthKeys = new TreeSet<>();
        for (Map<Object, Object> data : dataList) {
            for (Object key : data.keySet()) {
                try {
                    int month = Integer.parseInt((String) key);
                    if (month >= 1 && month <= 12) {
                        monthKeys.add(month);
                    }
                } catch (NumberFormatException e) {
//                    log.info("非数值类型");
                }
            }
        }
        // 固定表头
        List<String> fixedHeaders = java.util.Arrays.asList("研发部门", "工号","姓名");
        // 完整表头
        List<Object> finalHeaders = new ArrayList<>(fixedHeaders);
        List<String> monthHeaders = monthKeys.stream().map(m -> m + "月天数").collect(Collectors.toList());
        finalHeaders.addAll(monthHeaders);

        // 查询 如 1月天数 -> key：1
        Map<String, Object> headerToKeyMap =monthKeys.stream().collect(Collectors.toMap(
                m -> m + "月天数",
                m -> String.valueOf(m)
        ));

        // 写入表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < finalHeaders.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue((String) finalHeaders.get(i));
        }

        // 写入数据
        int rowNum = 1;
        for (Map<Object, Object> data : dataList) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < finalHeaders.size(); i++) {
                String header = (String) finalHeaders.get(i);
                Cell cell = row.createCell(i);
                // 根据表头写入值
                if ("研发部门".equals(header)) {
                    cell.setCellValue(data.getOrDefault("chargename","").toString());
                } else if ("工号".equals(header)) {
                    cell.setCellValue(data.getOrDefault("badge","").toString());
                } else if ("姓名".equals(header)) {
                    cell.setCellValue(data.getOrDefault("username", "").toString());
                } else {
                    // 月份值写入
                    String mapKey =(String) headerToKeyMap.get(header);
                    Object value = data.get(mapKey);

                    if (value instanceof Number) {
                        cell.setCellValue(((Number)value).doubleValue());
                    } else if (value != null) {
                        cell.setCellValue(value.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
//                sheet.autoSizeColumn(i);
            }
        }
        downloadExcel(fileName, response, workbook);

    }
    /**
     * 导入
     * @param filePath 文件路径
     * @param pojoClass 类
     * @param <T> 泛型
     * @return List
     */
    public static<T> List<T> importExcel(String filePath,Class<T> pojoClass) {
        return importExcel(filePath,0,1,pojoClass);
    }
    /**
     * 导入
     * @param filePath 文件路径
     * @param titleRows 标题行
     * @param headerRows 表头行
     * @param pojoClass 类
     * @param <T> 泛型
     * @return List
     */
    public static<T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows,Class<T> pojoClass) {
        if(!StringUtil.isStringNotEmpty(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try{
            list = ExcelImportUtil.importExcel(new File(filePath),pojoClass,params);
        }catch (Exception e ) {
            log.error("ExcelUtil", e);
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"Excel导入失败");
        }
        return list;
    }

    /**
     * 导入
     * @param file 文件
     * @param pojoClass 类
     * @param <T> 泛型
     * @return list
     */
    public static<T>  List<T> importExcel(MultipartFile file, Class<T> pojoClass) {
        return importExcel(file,0,1,pojoClass);
    }
    /**
     * 导入
     * @param file 对象
     * @param titleRows 标题行
     * @param headerRows 表头行
     * @param pojoClass 类
     * @param <T> 泛型
     * @return list
     */
    public static<T>  List<T> importExcel(MultipartFile file, Integer titleRows,Integer headerRows,Class<T> pojoClass) {
        if(!StringUtil.isNotEmpty(file)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try{
            list = ExcelImportUtil.importExcel(file.getInputStream(),pojoClass,params);
        }catch (Exception e ) {
            log.error("ExcelUtil", e);
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"Excel导入失败");
        }
        return list;
    }
    /**
     * 创建workbook
     * @param list 集合
     * @param pojoClass 类
     * @param fileName 文件名称
     * @param response  response
     * @param exportParams 对象
     */
    private static void defaltExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if(null != workbook) {
            downloadExcel(fileName,response, workbook);
        }
    }

    /**
     * 下载文件
     * @param fileName 文件名
     * @param res  Response
     * @param workbook 对象
     */
    private static void downloadExcel(String fileName, HttpServletResponse res, Workbook workbook) {
        OutputStream out = null;
        try{
            res.setCharacterEncoding("UTF-8");
            res.setHeader("content-type", "application/ms-excel;charset=UTF-8");
            res.setContentType("application/ms-excel;charset=UTF-8");
            res.setHeader("Content-Disposition","attachment:filename=" + URLEncoder.encode(fileName,"UTF-8"));
            res.flushBuffer();
            out = res.getOutputStream();
            workbook.write(out);
        }catch (IOException ie) {
            log.error("下载文件异常：{}",ie);
        }finally {
            try {
               if(null != out) {
                   out.flush();
                   out.close();
               }
            } catch (IOException e) {
                log.error("下载文件关闭资源异常：{}",e);
            }
        }
    }

    /**
     * 下载文件
     * @param fileName
     * @param res
     * @param workbook
     */
    public static void publicDownloadExcel(String fileName, HttpServletResponse res, Workbook workbook) {
        downloadExcel(fileName,res,workbook);
    }
}
