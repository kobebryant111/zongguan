package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResReportInfo;
import com.work.ppm.application.services.resourceplat.IResReportService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 资产统计报表service
 * @date 2021/09/03
 */
@Service("resReportService")
@Slf4j
public class ResReportService extends BaseDaoSupper implements IResReportService {
    /**
     * 资源新增相关路径
     */
    private static final String RES_ADD = "com.work.ppm.application.model.resourceplat.ResAdded";
    /**
     * 资源报表实体类
     */
    private static final String RES_REPORT = "com.work.ppm.application.model.resourceplat.ResReportInfo";
    /**
     * myBatisDao
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 获取数据
     * @param resAddedDto 查询dto
     * @return list
     */
    @Override
    public List<ResAdded> queryResourceReportList(ResAddedDto resAddedDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(resAddedDto, ResAddedDto.class);
        return myBatisDao.selectListT(RES_ADD, map, "queryResAddList");
    }

    /**
     * 获取数据
     * @param list 查询出的id列表
     * @return list
     */
    @Override
    public List<ResReportInfo> queryStatisticReportInfo(List<String> list) {
        return myBatisDao.selectListT(RES_REPORT, list, "queryStatisticReportInfo");
    }

    /**
     * 获取数据
     * @return list
     */
    @Override
    public List<CommTreeDto> queryRepDeptTree() {
        List<CommTree> list = myBatisDao.selectListT(RES_REPORT, null, "queryRepDeptTree");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        return CommTreeDto.recursiveListToTree(dtoList);
    }

    /**
     * 操作数据
     * @param resAddedDto
     */
    public void setResAddedDto(ResAddedDto resAddedDto){

        // 对预期上线时间做特殊处理
        String expectedOnlineTimeRangDate = resAddedDto.getExpectedOnlineTimeRangDate();
        if (StringUtils.isNotEmpty(expectedOnlineTimeRangDate)) {
            String[] tmp = expectedOnlineTimeRangDate.split(",");
            resAddedDto.setExpectedOnlineStartTime(tmp[0]);
            resAddedDto.setExpectedOnlineEndTime(tmp[1]);
        }

        // 对申请时间做特殊处理
        String rangeDate = resAddedDto.getRangeDate();
        if (StringUtils.isNotEmpty(rangeDate)) {
            String[] tmp = rangeDate.split(",");
            resAddedDto.setStartTime(tmp[0]);
            resAddedDto.setEndTime(tmp[1]);
        }
        resAddedDto.setStatus("4");
    }

    /**
     * 操作数据
     * @param resAddedList
     * @param list
     * @return list
     */
    public List<Object>  setlist(List<ResAdded> resAddedList,List<Object> list){
        for (int i = 0; i < resAddedList.size(); i++) {
            ResAdded resAdded = resAddedList.get(i);
            List<Object> sheet = new LinkedList<>();
            sheet.add(resAdded.getAppName());// 应用名称
            sheet.add(resAdded.getProjectName());// 项目名称
            sheet.add(resAdded.getBusiDeptName());// 所属业务部门
            sheet.add(resAdded.getTechDeptName());// 所属科技部门
            sheet.add(resAdded.getBudgetNo());// 预算编号
            sheet.add(resAdded.getCreator());// 申请人
            sheet.add(resAdded.getCreateTime());// 申请时间
            sheet.add(resAdded.getExpectedOnlineTime());// 预计上线时间
            list.add(sheet);
        }
        return list;
    }

    /**
     * 操作数据
     * @param resReportInfoList
     * @param listSub
     * @return list
     */
    public List<Object>  setlistSub(List<ResReportInfo> resReportInfoList,List<Object> listSub){
        for (int i = 0; i < resReportInfoList.size(); i++) {
            ResReportInfo resReportInfo = resReportInfoList.get(i);
            List<Object> sheet = new LinkedList<>();
            sheet.add(resReportInfo.getOwnCenter());// 所属中心
            sheet.add(resReportInfo.getResType());// 资源类型
            sheet.add(resReportInfo.getDescribe());// 配置
            sheet.add(resReportInfo.getNum());// 数量
            sheet.add(resReportInfo.getPrice());// 单价(元)
            sheet.add(resReportInfo.getTotalPrice());// 总价(元)
            listSub.add(sheet);
        }
        return listSub;
    }

    /**
     * 操作数据
     * @param emptyLine
     */
    public void setEmptyLine(List<Object> emptyLine){
        emptyLine.add(" ");// 所属中心
        emptyLine.add(" ");// 资源类型
        emptyLine.add(" ");// 配置
        emptyLine.add(" ");// 数量
        emptyLine.add(" ");// 单价(元)
        emptyLine.add(" ");// 总价(元)
    }

    /**
     * 操作数据
     * @param lists
     * @return String[][]
     */
    public String[][]  setDataListMain(List<Object> lists){
        String[][] dataListMain = new String[lists.size()][];
        for(int i = 0; i < lists.size(); i++){
            List<Object> list = (List<Object>) lists.get(i);
            dataListMain[i] = new String[list.size()];
            for(int j = 0; j < list.size(); j++){
                if(list.get(j) != null){
                    dataListMain[i][j] = list.get(j).toString();
                }
            }
        }
        return dataListMain;
    }

    /**
     * 操作数据
     * @param listSub
     * @return String[][]
     */
    public String[][]  setDataListSub(List<Object> listSub){
        String[][] dataListSub = new String[listSub.size()][];
        for(int i = 0; i < listSub.size(); i++){
            List<Object> list = (List<Object>) listSub.get(i);
            dataListSub[i] = new String[list.size()];
            for(int j = 0; j < list.size(); j++){
                if(list.get(j) != null){
                    dataListSub[i][j] = list.get(j).toString();
                }
            }
        }
        return dataListSub;
    }

    /**
     * 操作数据
     * @param style
     * @param wb
     */
    public void setStyle(HSSFCellStyle style,HSSFWorkbook wb){
        style.setWrapText(true);// 自动换行
        style.setAlignment(HorizontalAlignment.CENTER);

        // 设置背景颜色
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());

        // solid填充foreground 前景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 设置边框及颜色[要设置 要不然表头没有边框]
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont headerFont = wb.createFont();// 创建字体样式
        headerFont.setBold(true);
        headerFont.setFontName("宋体");
        headerFont.setFontHeightInPoints((short) 10);
        style.setFont(headerFont);
    }

    /**
     * 操作数据
     * @param autoLineFeed
     * @param dataListMain
     * @param sheet
     * @param columnNumberMain
     */
    public void setAutoLineFeed(HSSFCellStyle autoLineFeed,String[][] dataListMain,
                                HSSFSheet sheet,int columnNumberMain){
        autoLineFeed.setWrapText(true);
        autoLineFeed.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = 0; i < dataListMain.length; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            // 设置边框及颜色
            autoLineFeed.setBorderBottom(BorderStyle.THIN);
            autoLineFeed.setBorderLeft(BorderStyle.THIN);
            autoLineFeed.setBorderTop(BorderStyle.THIN);
            autoLineFeed.setBorderRight(BorderStyle.THIN);
            autoLineFeed.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            autoLineFeed.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            autoLineFeed.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            autoLineFeed.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

            HSSFCell datacell = null;
            for (int j = 0; j < columnNumberMain; j++) {
                datacell = row.createCell((short) j);
                datacell.setCellValue(dataListMain[i][j]);
                datacell.setCellStyle(autoLineFeed);
            }
        }
    }

    /**
     * 操作数据
     * @param dataListSub
     * @param index
     * @param sheet
     * @param autoLineFeed
     * @param columnNumberSub
     * @param borderSpecial
     */
    public void setResTypeOriginal(String[][] dataListSub,int index,HSSFSheet sheet,
                                   HSSFCellStyle autoLineFeed,int columnNumberSub,HSSFCellStyle borderSpecial){
        String currentCenterValue = "";
        String currentResTypeValue = "";
        int beginIndexCenter = index;
        int endIndexCenter = beginIndexCenter - 1;
        String centerOriginal = dataListSub[0][0];

        int beginIndexResType = index;
        int endIndexResType = beginIndexResType - 1;
        String resTypeOriginal = dataListSub[0][1];
        for (int i = 0; i < dataListSub.length; i++) {

            currentCenterValue = dataListSub[i][0];
            currentResTypeValue = dataListSub[i][1];
            HSSFRow row = sheet.createRow(index + i);
            autoLineFeed.setVerticalAlignment(VerticalAlignment.CENTER);
            autoLineFeed.setWrapText(true);
            HSSFCell datacell = null;
            for (int j = 0; j < columnNumberSub; j++) {
                datacell =setDatacell(datacell,row,i,j,dataListSub,borderSpecial,autoLineFeed);
            }

            // 如果本次的数据跟上次的结果一致
            // 注意这边是从数据第一行就开始比较 因为endIndexCenter = beginIndexCenter - 1
            if(centerOriginal != null && currentCenterValue != null){
                if(centerOriginal.equals(currentCenterValue)) {
                    endIndexCenter++;
                } else {

                    // 先合并之前相同的 将原始值赋值为当前不同值 将beginIndexCenter赋值为endIndexCenter
                    // 如果只有一行相同的 就不要合并了
                    setCenterOriginal(beginIndexCenter,endIndexCenter,sheet);
                    centerOriginal = currentCenterValue;
                    endIndexCenter++;
                    beginIndexCenter = endIndexCenter;
                }
            }

            // 资源类型的合并同上
            if(resTypeOriginal.equals(currentResTypeValue)) {
                endIndexResType++;
            } else {
                setResTypeOriginal(beginIndexResType,endIndexResType,sheet);
                resTypeOriginal = currentResTypeValue;
                endIndexResType++;
                beginIndexResType = endIndexResType;
            }
        }

    }

    /**
     * 操作数据
     * @param beginIndexCenter
     * @param endIndexCenter
     * @param sheet
     */
    public void setCenterOriginal(int beginIndexCenter,int endIndexCenter,HSSFSheet sheet){
        if(beginIndexCenter != endIndexCenter) {
            CellRangeAddress cra = new CellRangeAddress(beginIndexCenter, endIndexCenter, 0
                    , 0);
            sheet.addMergedRegion(cra);
        }

    }

    /**
     * 操作数据
     * @param beginIndexResType
     * @param endIndexResType
     * @param sheet
     */
    public void setResTypeOriginal(int beginIndexResType,int endIndexResType,HSSFSheet sheet){
        if(beginIndexResType != endIndexResType) {
            CellRangeAddress cra = new CellRangeAddress(beginIndexResType, endIndexResType,1
                    , 1);
            sheet.addMergedRegion(cra);
        }

    }

    /**
     * 操作数据
     * @param datacell
     * @param row
     * @param i
     * @param j
     * @param dataListSub
     * @param borderSpecial
     * @param autoLineFeed
     * @return HSSFCell
     */
    public HSSFCell setDatacell(HSSFCell datacell,HSSFRow row,int i,int j,String[][] dataListSub,
                            HSSFCellStyle borderSpecial,HSSFCellStyle autoLineFeed){
        datacell = row.createCell((short) j);
        datacell.setCellValue(dataListSub[i][j]);
        if( i == dataListSub.length - 1) {
            datacell.setCellStyle(borderSpecial);
        } else {
            datacell.setCellStyle(autoLineFeed);
        }
        return datacell;
    }


    /**
     * 操作数据
     * @param resAddedDto 查询resAddedDto
     * @param response
     */
    @Override
    public void exportResReport(ResAddedDto resAddedDto, HttpServletResponse response) {

        // 设置参数
        setResAddedDto(resAddedDto);

        // 获取流转完成数据
        List<ResAdded> resAddedList = this.queryResourceReportList(resAddedDto);

        // 根据查询出的list进一步查询出统计信息
        // 查询出的resAddedList可能为空 所有的导出必须以上面的主查询查询出的结果不为空为基础
        List<String> resAddIdList = new LinkedList<>();
        if (resAddedList != null && !resAddedList.isEmpty()) {
            for (int i = 0; i < resAddedList.size(); i++) {
                resAddIdList.add(resAddedList.get(i).getId());
            }

            // 查询出统计信息
            List<ResReportInfo> resReportInfoList = this.queryStatisticReportInfo(resAddIdList);
            List<Object> linkedList = new LinkedList<>();
            List<Object> listSub = new LinkedList<>();

            // 上面主表的数据
            linkedList =setlist(resAddedList,linkedList);

            // 下面统计信息的数据
            listSub =setlistSub(resReportInfoList,listSub);

            // 追加一个空行(空数据行）// 特殊处理!!!!!!! --> 最后一行的空行是用来提交之前相同项的合并-_-||
            List<Object> emptyLine = new LinkedList<>();
            setEmptyLine(emptyLine);
            listSub.add(resReportInfoList.size(), emptyLine);// 注意要加到数据列表最后！！！


            int recordTotalMain = linkedList.size();// 上面列表的条数
            String sheetName = "sheet0";
            String fileName = "资产统计报表";// 前端已经有了下载名称 这边定义不定义意义不大
            int columnNumberMain = 8; // 上面列表的列数
            int columnNumberSub = 6; // 下面统计信息的列数
            int[] columnWidthMain = {1,2,3,4,5,6,7,8};
            int[] columnWidthSub = {1,2,3,4,5,6};

            String[][] dataListMain =setDataListMain(linkedList);

            String[][] dataListSub =setDataListSub(listSub);

            String[] columnNameMain = {"应用名称", "项目名称", "所属业务部门", "所属科技部门", "预算编号", "申请人", "申请时间"
                    , "预计上线时间"};
            String[] columnNameSub = {"所属中心", "资源类型", "配置", "数量", "单价(元)", "总价(元)"};
            // 第一步 创建一个WEBBOOK，对应一个Excel文件
            try (HSSFWorkbook wb = new HSSFWorkbook();){
                if (columnNumberMain == columnWidthMain.length && columnWidthMain.length == columnNameMain.length) {

                    // 第二步 在WEBBOOK中添加一个sheet，对应excel文件中的sheet
                    HSSFSheet sheet = wb.createSheet(sheetName);

                    // 第三步 创建标题的单元格样式style2以及字体样式headerFont1(此步骤跳过)

                    // 创建第一行 也就是表头
                    HSSFRow row = sheet.createRow(0);
                    row.setHeightInPoints(25);// 设置表头高度

                    // 第四步 创建表头单元格样式 以及表头的字体样式
                    HSSFCellStyle style = wb.createCellStyle();

                    // 渲染报表
                    setStyle(style,wb);

                    // 创建表头的列

                    setCellMain(columnNumberMain,row,columnNameMain,style);

                    // 为数据内容设置特点新单元格样式并自动换行 上下居中
                    HSSFCellStyle autoLineFeed = wb.createCellStyle();

                    setAutoLineFeed(autoLineFeed,dataListMain,sheet,columnNumberMain);

                    // 拼接下面的统计信息--------------------------------- begin -------------------------------------
                    // 创建第N行 也就是下面统计信息的表头

                    // 所属中心行
                    if (columnNumberSub == columnWidthSub.length && columnWidthSub.length == columnNameSub.length) {
                        HSSFRow rowSub = sheet.createRow(recordTotalMain + 2);
                        rowSub.setHeightInPoints(25);// 设置表头高度

                        // 创建表头的列
                        setCellSub(columnNumberSub,rowSub,columnNameSub,style);

                        // 注意：这边的创建行 目前是间隔了一行！！！！！！！
                        // 统计数据起始行数(recordTotalMain + 3)
                        int index = recordTotalMain + 3;

                        // 此borderSpecial样式是为了隐藏最终的空行-_-||
                        HSSFCellStyle borderSpecial =setBorderSpecial(wb);

                        setResTypeOriginal(dataListSub,index,sheet,autoLineFeed,columnNumberSub,borderSpecial);

                    }
                    // 拼接下面的统计信息---------------------------------- end ------------------------------------------
                    fileName = fileName + ".xls";
                    response.setContentType("application/ms-excel;charset=UTF-8");
                    response.setHeader("Content-Disposition","attachment;filename="
                            .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
                    wb.write(response.getOutputStream());
                }
            } catch (IOException e) {
                log.error("文件IO异常,异常原因："+e.getMessage()+",异常打印："+e.getStackTrace() ,e);
            }
        }
    }

    /**
     * 操作数据
     * @param wb
     * @return HSSFCellStyle
     */
    public HSSFCellStyle setBorderSpecial(HSSFWorkbook wb){
        HSSFCellStyle borderSpecial = wb.createCellStyle();
        borderSpecial.setBorderBottom(BorderStyle.NONE);
        borderSpecial.setBorderLeft(BorderStyle.NONE);
        borderSpecial.setBorderTop(BorderStyle.NONE);
        borderSpecial.setBorderRight(BorderStyle.NONE);
        return borderSpecial;
    }

    /**
     * 操作数据
     * @param columnNumberMain
     * @param row
     * @param columnNameMain
     * @param style
     */
    public void setCellMain (int columnNumberMain,HSSFRow row,String[] columnNameMain,HSSFCellStyle style){
        for (int i = 0; i < columnNumberMain; i++) {
            HSSFCell cellMain = row.createCell((short) i);
            cellMain.setCellValue(columnNameMain[i]);
            cellMain.setCellStyle(style);
        }
    }

    /**
     * 操作数据
     * @param columnNumberSub
     * @param rowSub
     * @param columnNameSub
     * @param style
     */
    public void setCellSub(int columnNumberSub,HSSFRow rowSub,String[] columnNameSub,HSSFCellStyle style){
        for (int i = 0; i < columnNumberSub; i++) {
            HSSFCell cellSub = rowSub.createCell((short) i);
            cellSub.setCellValue(columnNameSub[i]);
            cellSub.setCellStyle(style);// 这边的style同上面的style
        }
    }

    /**
     * 根据人员id查询所属部门id
     * @param userId
     * @return 1
     */
    public String getDeptIdLogin (String userId){
        // 初期化
        String deptFlag ="0";
        // 获取当前登陆人所属部门id
        String deptId = myBatisDao.selectClass(RES_REPORT, userId, "getDeptId");
        // 如果属于系统管理部人员
        if("141".equals(deptId)){
            deptFlag ="1";
        }
        return deptFlag;
    }
}
