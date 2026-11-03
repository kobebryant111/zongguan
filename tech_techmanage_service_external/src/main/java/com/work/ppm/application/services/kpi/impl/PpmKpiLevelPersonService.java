package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.PpmKpiDepRoleInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonPageShowDto;
import com.work.ppm.application.model.kpi.ext.PpmKpiLevelPersonInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiDepRoleService;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchService;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiLevelPerson;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonDto;
import com.work.ppm.application.services.kpi.IPpmKpiLevelPersonService;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
  * 描述
  */
@Slf4j
@Service("ppmKpiLevelPersonService")
public class PpmKpiLevelPersonService extends BaseDaoSupper implements IPpmKpiLevelPersonService {
    /**
      * 实体类路径
      */
    private static String PPMKPILEVELPERSON = "com.work.ppm.application.model.kpi.PpmKpiLevelPerson";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     *  中心表
     */
    @Autowired
    private IPpmDaCenterService PpmDaCenterService;

    /**
     *  人员角色
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;


    /**
     * 考核发起
     */
    @Autowired
    private IPpmKpiItemSwitchService ppmKpiItemSwitchService;



    /**
     * 部门组织树接口
     */
    @Autowired
    private IPpmKpiDepRoleService ppmKpiDepRoleService;

    @Override
    public void queryPager(PpmKpiLevelPersonDto ppmKpiLevelPersonDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiLevelPersonDto,PpmKpiLevelPersonDto.class);
        myBatisDao.selectPager(PPMKPILEVELPERSON, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        Long id = getNextSequence(PpmKpiLevelPerson.class);

        // 如果需要请set 主键id
        ppmKpiLevelPerson.setId(id);
        myBatisDao.executionPojo(PPMKPILEVELPERSON, ppmKpiLevelPerson, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiLevelPersonDto queryDetail(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        PpmKpiLevelPerson data = myBatisDao.selectClass(PPMKPILEVELPERSON, ppmKpiLevelPerson,
        "queryDetail");
        PpmKpiLevelPersonDto item = JsonUtil.jsonToClass(data, PpmKpiLevelPersonDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        return myBatisDao.executionPojo(PPMKPILEVELPERSON, ppmKpiLevelPerson, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        return myBatisDao.executionPojo(PPMKPILEVELPERSON, ppmKpiLevelPerson, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    @Override
    public List<PpmKpiLevelPersonDto> getPpmKpiLevelPersons(PpmKpiLevelPersonDto ppmKpiLevelPersonDto){
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        List<PpmKpiLevelPerson> persons= myBatisDao.selectListT(PPMKPILEVELPERSON, ppmKpiLevelPerson, "getList");
        List<PpmKpiLevelPersonDto> personDtos=JsonUtil.jsonToList(persons,PpmKpiLevelPersonDto.class);
        return  personDtos;
    }

    @Override
    public void queryShowPager(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiLevelPersonInfoDto,PpmKpiLevelPersonInfoDto.class);
        if("1".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            myBatisDao.selectPager(PPMKPILEVELPERSON, map, pager,"querySecondDepGroup");
        }else if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            myBatisDao.selectPager(PPMKPILEVELPERSON, map, pager,"queryCenterGroup");
        }else{
            myBatisDao.selectPager(PPMKPILEVELPERSON, map, pager,"queryFirstDepGroup");
        }
        if(pager.getList().size()!=0){
            List<PpmKpiLevelPersonPageShowDto> item = JsonUtil.jsonToList(pager.getList(),
                    PpmKpiLevelPersonPageShowDto.class);
            for(int i=0;i<item.size();i++){
                if("1".equals(ppmKpiLevelPersonInfoDto.getOtype())){
                    ppmKpiLevelPersonInfoDto.setDepid(item.get(i).getDepid());
                }else if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
                    ppmKpiLevelPersonInfoDto.setCenterid(item.get(i).getCenterid());
                }else{
                    ppmKpiLevelPersonInfoDto.setDepid(item.get(i).getDepid());
                }
                PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(
                        ppmKpiLevelPersonInfoDto, PpmKpiLevelPerson.class);
                List<PpmKpiLevelPerson> persons=myBatisDao.selectListT(
                        PPMKPILEVELPERSON, ppmKpiLevelPerson,"queryPagerList");
                if(persons.size()!=0){
                    List<PpmKpiLevelPersonDto> personDtos=JsonUtil.jsonToList(
                            persons,PpmKpiLevelPersonDto.class);
                    item.get(i).setPersons(personDtos);
                }
            }

            pager.setList(item);
        }
    }

    @Override
    public List<PpmKpiLevelPersonPageShowDto> queryShowList(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiLevelPersonInfoDto,PpmKpiLevelPersonInfoDto.class);
        List<PpmKpiLevelPersonPageShowDto> item =null;
        List<PpmKpiLevelPersonInfo>  ppmKpiLevelPersonInfos=null;
        if("1".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            ppmKpiLevelPersonInfos=myBatisDao.selectListT(PPMKPILEVELPERSON, map,"querySecondDepGroup");
        }else if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            ppmKpiLevelPersonInfos=myBatisDao.selectListT(PPMKPILEVELPERSON, map,"queryCenterGroup");
        }else{
            ppmKpiLevelPersonInfos=myBatisDao.selectListT(PPMKPILEVELPERSON, map,"queryFirstDepGroup");
        }
        if(ppmKpiLevelPersonInfos.size()!=0){
            item = JsonUtil.jsonToList(ppmKpiLevelPersonInfos, PpmKpiLevelPersonPageShowDto.class);
            for(int i=0;i<item.size();i++){
                if("1".equals(ppmKpiLevelPersonInfoDto.getOtype())){
                    ppmKpiLevelPersonInfoDto.setDepid(item.get(i).getDepid());
                }else if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
                    ppmKpiLevelPersonInfoDto.setCenterid(item.get(i).getCenterid());
                }else{
                    ppmKpiLevelPersonInfoDto.setDepid(item.get(i).getDepid());
                }
                PpmKpiLevelPerson ppmKpiLevelPersonInfo = JsonUtil.jsonToClass(
                        ppmKpiLevelPersonInfoDto, PpmKpiLevelPersonInfo.class);
                List<PpmKpiLevelPerson> persons=myBatisDao.selectListT(
                        PPMKPILEVELPERSON, ppmKpiLevelPersonInfo,"queryPagerList");
                if(persons.size()!=0){
                    List<PpmKpiLevelPersonDto> personDtos=JsonUtil.jsonToList(
                            persons,PpmKpiLevelPersonDto.class);
                    item.get(i).setPersons(personDtos);
                }
            }

        }
        return item;
    }

    @Override
    public void exportData(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, HttpServletResponse response){
        String perFileName="";
        List<String>headers=new ArrayList<String>();
        if("1".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            perFileName="二级部员工比例人数";
            headers.add("中心");
            headers.add("二级部");
        }else if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            if("COMM_USER".equals(ppmKpiLevelPersonInfoDto.getRole())){
                perFileName="中心员工比例人数";
            }else{
                perFileName="中心经理层比例人数";
            }
            headers.add("中心");
        }else{
            perFileName="一级部比例人数";
            headers.add("一级部");
        }
        //获取导出数据
        List<PpmKpiLevelPersonPageShowDto> lists=new ArrayList<PpmKpiLevelPersonPageShowDto>();
        lists= queryShowList(ppmKpiLevelPersonInfoDto);
        //获取表头数据
        List<EosDictEntry> eoss=eosDictEntryService.getTypeidForList("KPI_LEVEL");
        for (int i=0;i<eoss.size();i++){
            headers.add(eoss.get(i).getDictname());
        }

        //新建工作簿
        Workbook workbook = new XSSFWorkbook();
        //设置文件名极其后缀
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String fileName=perFileName+"_"+sdf.format(new Date())+".xlsx";
        //新建第一个sheet页
        Sheet sheet= workbook.createSheet("第一页");
        //开始添加行数添加excel表头
        Row row=sheet.createRow(0);
        //设置excel宽度和高度
        sheet.setDefaultRowHeight((short)(3*256));
        sheet.setDefaultColumnWidth(25);
        //添加第一行表头数据
        for(int i=0;i<headers.size();i++){
            Cell cell=row.createCell(i);
            cell.setCellStyle(getRow1CellStyle(workbook));
            XSSFRichTextString text=new XSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }
        if(StringUtil.isNotEmpty(lists)){
            createRow(lists,headers,sheet);
        }
        ExcelUtil.publicDownloadExcel(fileName,response,workbook);
    }

    /**
     * 创建行数据
     * @param lists
     * @param headers
     * @param sheet
     */
    private void createRow(List<PpmKpiLevelPersonPageShowDto> lists,List<String>headers,Sheet sheet){
        //开始创建excel单元格数据从第二行开始
        int rownum=1;
        XSSFRichTextString text=null;
        for(int i=0;i<lists.size();i++){
            Row row1=sheet.createRow(rownum+i);
            row1.setHeightInPoints(20);
            for(int j=0;j<headers.size();j++){
                if("中心".equals(headers.get(j))){
                    text=new XSSFRichTextString(lists.get(i).getCentername());
                    row1.createCell(j).setCellValue(text);
                }else if("二级部".equals(headers.get(j))||"一级部".equals(headers.get(j))){
                    text=new XSSFRichTextString(lists.get(i).getDepname());
                    row1.createCell(j).setCellValue(text);
                }else{
                    String header=headers.get(j);
                    List list=lists.get(i).getPersons().stream().filter(
                            e->e.getTlevel().equals(header)).map(PpmKpiLevelPersonDto::getNums).
                            collect(Collectors.toList());
                    text=new XSSFRichTextString(list==null?"":list.get(0).toString());
                    row1.createCell(j).setCellValue(text);
                }
            }

        }
    }


    /**
     * 设置表头样式
     * @param workbook
     * @return CellStyle
     */
    private CellStyle getRow1CellStyle(Workbook workbook){
        CellStyle cellstyle=workbook.createCellStyle();
        cellstyle.setAlignment(HorizontalAlignment.CENTER);
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellstyle.setBorderBottom(BorderStyle.HAIR);
        cellstyle.setBorderTop(BorderStyle.HAIR);
        cellstyle.setBorderLeft(BorderStyle.HAIR);
        cellstyle.setBorderRight(BorderStyle.HAIR);
        Font font =workbook.createFont();
        font.setFontHeightInPoints((short)14);
        font.setItalic(false);
        font.setBold(false);
        font.setColor(IndexedColors.BLACK.index);
        cellstyle.setFont(font);
        cellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        return cellstyle;
    }

    /**
     *  获得Workbook
     * @param inStr
     * @param fileName
     * @return Workbook
     */
    private Workbook getWorkbook(InputStream inStr,String fileName){
        Workbook wb=null;
        try {
            String fileType=fileName.substring(fileName.lastIndexOf("."));
            if(".xls".equals(fileType)){
                wb=new HSSFWorkbook(inStr);
            }else if(".xlsx".equals(fileType)){
                wb=new XSSFWorkbook(inStr);
            }else{
                throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"解析文件格式有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"解析文件格式有误");
        }
        return wb;

    }

    /**
     * 获取cell中的值
     * @param cell
     * @return Object
     */
    private Object getCellValue(Cell cell){
        Object value=null;
        DecimalFormat df=new DecimalFormat("0");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df2=new DecimalFormat("0.00");
        if(cell==null){
            return null;
        }
        switch(cell.getCellType()){
            case BOOLEAN:
                value=cell.getBooleanCellValue();
                break;
            case NUMERIC:
                value=cell.getNumericCellValue();
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value=df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case STRING:
                value=String.valueOf(cell.getStringCellValue());
                break;
            case BLANK:
                value="";
                break;
            default:
                break;

        }
        return value;
    }

    @Override
    @Transactional("defaultTx")
    public boolean importExcel(MultipartFile file,PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, String userid) {
        Workbook worbook=null;
        try {
            worbook=this.getWorkbook(file.getInputStream(),file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"解析文件格式有误");
        }
        Sheet sheet=null;
        Row row=null;

        if(worbook.getNumberOfSheets()<0){
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"解析的文件中没有sheet");
        }
        sheet=worbook.getSheetAt(0);
        if(sheet==null){
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,"解析的sheet页中没有数据");
        }
        PpmKpiLevelPersonDto dto=new PpmKpiLevelPersonDto();
        dto.setCreatetime(new Date());
        dto.setCreateuserid(userid);
        dto.setOtype(ppmKpiLevelPersonInfoDto.getOtype());
        dto.setRole(ppmKpiLevelPersonInfoDto.getRole());
        for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            imporeInsertData(dto ,row,userid);
        }

        return true;
    }

    /**
     * 导入时插入行数据
     * @param ppmKpiLevelPersonDto
     * @param row
     * @return int
     */
    private int imporeInsertData(PpmKpiLevelPersonDto ppmKpiLevelPersonDto,Row row,String userid){

        log.info(userid);
        //获取表头数据
        List<EosDictEntry> eoss=eosDictEntryService.getTypeidForList("KPI_LEVEL");
        int first=0;
        if("1".equals(ppmKpiLevelPersonDto.getOtype())){
            first=2;
            String centerid=ppmKpiRoleService.retCenterid(getCellValue(row.getCell(0)).toString().trim());
            ppmKpiLevelPersonDto.setCenterid(centerid);
            String depid=commDepartmentService.getDepNameForId(getCellValue(row.getCell(1)).toString());
            ppmKpiLevelPersonDto.setDepid(depid);
            ppmKpiLevelPersonDto.setRole("COMM_USER");
        }else if("2".equals(ppmKpiLevelPersonDto.getOtype())){
            first=1;
            String centerid=ppmKpiRoleService.retCenterid(getCellValue(row.getCell(0)).toString());
            ppmKpiLevelPersonDto.setCenterid(centerid);
            if("COMM_USER".equals(ppmKpiLevelPersonDto.getRole())){
                ppmKpiLevelPersonDto.setRole("COMM_USER");
            }else{
                ppmKpiLevelPersonDto.setRole("DEP_MANAGERS");
            }

        }else{
            first=1;
            String depid="";
            if("总行金融科技部".equals(getCellValue(row.getCell(0)).toString())){
                depid="130";
            }else{
                depid=commDepartmentService.getDepNameForId(getCellValue(row.getCell(0)).toString());
            }

            ppmKpiLevelPersonDto.setDepid(depid);
        }
        for(int i=0;i<eoss.size();i++){
            ppmKpiLevelPersonDto.setTlevel(eoss.get(i).getDictname());
            if(getCellValue(row.getCell(i+first))!=null&&!"".equals(getCellValue(row.getCell(i+first)))){
                ppmKpiLevelPersonDto.setNums(Long.valueOf(String.valueOf(getCellValue(row.getCell(i+first)))));
            }else{
                ppmKpiLevelPersonDto.setNums(0L);
            }
            PpmKpiLevelPersonDto param=new PpmKpiLevelPersonDto();
            param.setOtype(ppmKpiLevelPersonDto.getOtype());
            param.setRole(ppmKpiLevelPersonDto.getRole());
            param.setTlevel(ppmKpiLevelPersonDto.getTlevel());
            param.setDepid(ppmKpiLevelPersonDto.getDepid());
            param.setCenterid(ppmKpiLevelPersonDto.getCenterid());
            PpmKpiLevelPersonDto item=queryDetailByCondition(param);
            if(StringUtil.isNotEmpty(item)){
                item.setNums(ppmKpiLevelPersonDto.getNums());
                updatePpmKpiLevelPerson(item);
            }else{
                addPpmKpiLevelPerson(ppmKpiLevelPersonDto);
            }
        }

        return 1;
    }

    @Override
    public PpmKpiLevelPersonDto queryDetailByCondition(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        PpmKpiLevelPerson data = myBatisDao.selectClass(PPMKPILEVELPERSON, ppmKpiLevelPerson,
                "getList");
        PpmKpiLevelPersonDto item = JsonUtil.jsonToClass(data, PpmKpiLevelPersonDto
                .class);
        return item;
    }
    @Override
    public int updateOrAdd(PpmKpiLevelPersonDto ppmKpiLevelPersonDto ,String userid){
        PpmKpiLevelPersonDto param=new PpmKpiLevelPersonDto();
        param.setOtype(ppmKpiLevelPersonDto.getOtype());
        param.setRole(ppmKpiLevelPersonDto.getRole());
        param.setTlevel(ppmKpiLevelPersonDto.getTlevel());
        param.setDepid(ppmKpiLevelPersonDto.getDepid());
        param.setCenterid(ppmKpiLevelPersonDto.getCenterid());
        PpmKpiLevelPersonDto item=queryDetailByCondition(param);
        if(StringUtil.isNotEmpty(item)){
            item.setNums(ppmKpiLevelPersonDto.getNums());
            updatePpmKpiLevelPerson(item);
        }else{
            ppmKpiLevelPersonDto.setNums(ppmKpiLevelPersonDto.getNums());
            ppmKpiLevelPersonDto.setCreatetime(new Date());
            ppmKpiLevelPersonDto.setCreateuserid(userid);
            addPpmKpiLevelPerson(ppmKpiLevelPersonDto);
        }
        return 1;
    }


    @Override
    public List<PpmKpiLevelPersonDto> getNoSetLevel(PpmKpiLevelPersonDto ppmKpiLevelPersonDto){
        PpmKpiLevelPerson ppmKpiLevelPerson = JsonUtil.jsonToClass(ppmKpiLevelPersonDto, PpmKpiLevelPerson.class);
        return myBatisDao.selectListT(PPMKPILEVELPERSON, ppmKpiLevelPerson, "queryNoSetLevel");
    }


    @Override
    public List<PpmKpiLevelPersonInfoDto> getPpmKpiLevelPersonsByCondition(
            PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto){
        PpmKpiLevelPersonInfo ppmKpiLevelPersonInfo = JsonUtil.jsonToClass(
                ppmKpiLevelPersonInfoDto, PpmKpiLevelPersonInfo.class);
        List<PpmKpiLevelPersonInfo> persons= myBatisDao.selectListT(
                PPMKPILEVELPERSON, ppmKpiLevelPersonInfo, "getListByCondition");
        List<PpmKpiLevelPersonInfoDto> personDtos=JsonUtil.jsonToList(
                persons,PpmKpiLevelPersonInfoDto.class);
        return  personDtos;
    }

    /**
     * 一级部数据插入
     * @param eosDictEntry 对象
     * @param userid 对象
     * @return int
     */
    private int firstDepLevelPerson(EosDictEntry eosDictEntry,String userid){
        //一级部数据
        String depid="130";
        PpmKpiLevelPersonDto ppmKpiLevelPersonDto=new PpmKpiLevelPersonDto();
        ppmKpiLevelPersonDto.setDepid(depid);
        ppmKpiLevelPersonDto.setOtype("3");
        ppmKpiLevelPersonDto.setNums(0L);
        ppmKpiLevelPersonDto.setTlevel(eosDictEntry.getDictid());
        updateOrAdd(ppmKpiLevelPersonDto,userid);
        return 1;
    }
    /**
     * 中心数据插入
     * @param eosDictEntry 对象
     * @param userid 对象
     * @return int
     */
    private int centerLevelPerson(EosDictEntry eosDictEntry,String userid){
        log.info(userid);
        //获得当前人所分管中心
        PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto =new PpmKpiDepRoleInfoDto();
        ppmKpiDepRoleInfoDto.setSign(2L);
        List<PpmKpiDepRoleInfoDto> centers=ppmKpiDepRoleService.getPpmKpiDepRoleView(ppmKpiDepRoleInfoDto);
        if(StringUtil.isNotEmpty(centers)){
            for(int i=0;i<centers.size();i++){
                PpmKpiLevelPersonDto param=new PpmKpiLevelPersonDto();
                param.setOtype("2");
                param.setRole("COMM_USER");
                param.setTlevel(eosDictEntry.getDictid());
                param.setCenterid(centers.get(i).getCenterid());
                //员工
                PpmKpiLevelPersonDto item=queryDetailByCondition(param);
                if(!StringUtil.isNotEmpty(item)){
                    param.setNums(0L);
                    addPpmKpiLevelPerson(param);
                }
                param.setRole("DEP_MANAGERS");
                //经理层
                PpmKpiLevelPersonDto item1=queryDetailByCondition(param);
                if(!StringUtil.isNotEmpty(item1)){
                    param.setNums(0L);
                    addPpmKpiLevelPerson(param);
                }
            }
        }
        return 1;
    }
    /**
     * 二级部数据插入
     * @param eosDictEntry 对象
     * @param userid 对象
     * @return int
     */
    private int depLevelPerson(EosDictEntry eosDictEntry,String userid){
        log.info(userid);
        //获得所有部门
        PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto =new PpmKpiDepRoleInfoDto();
        ppmKpiDepRoleInfoDto.setSign(1L);
        List<PpmKpiDepRoleInfoDto> deps=ppmKpiDepRoleService.getPpmKpiDepRoleView(ppmKpiDepRoleInfoDto);
        if(StringUtil.isNotEmpty(deps)){
            for(int i=0;i<deps.size();i++){
                if("131".equals(deps.get(i).getDepid())){
                    continue;
                }
                PpmKpiLevelPersonDto param=new PpmKpiLevelPersonDto();
                param.setOtype("1");
                param.setRole("COMM_USER");
                param.setTlevel(eosDictEntry.getDictid());
                param.setDepid(deps.get(i).getDepid());
                param.setCenterid(deps.get(i).getCenterid());
                //员工
                PpmKpiLevelPersonDto item=queryDetailByCondition(param);
                if(!StringUtil.isNotEmpty(item)){
                    param.setNums(0L);
                    addPpmKpiLevelPerson(param);
                }
            }
        }
        return 1;
    }
    @Override
    public int syncLevelPersonData(String userid){
        //删除部门不在考核组织树终的部门
         myBatisDao.executionPojo(PPMKPILEVELPERSON, null, IMyBatisDao.SQL.DELETE,"deleteDepLevelPerson");
        //删除中心不在考核组织树终的中心
         myBatisDao.executionPojo(PPMKPILEVELPERSON, null, IMyBatisDao.SQL.DELETE,"deleteCenterLevelPerson");
        //删除等级不在评级设置中的数据
         myBatisDao.executionPojo(PPMKPILEVELPERSON, null, IMyBatisDao.SQL.DELETE,"deleteLevelPerson");
         //插入数据
        List<EosDictEntry> eosDictEntrys=eosDictEntryService.getTypeidForList("KPI_LEVEL");
        if(StringUtil.isNotEmpty(eosDictEntrys)){
            for(int i=0;i<eosDictEntrys.size();i++){
                //一级部数据
                firstDepLevelPerson(eosDictEntrys.get(i),userid);
                //中心
                centerLevelPerson(eosDictEntrys.get(i),userid);
                //二级部
                depLevelPerson(eosDictEntrys.get(i),userid);
            }
        }
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int addKpiLevelPerson(EosDictEntry eosDictEntry,String userid){
        int id = ppmKpiItemSwitchService.addEvalSet(eosDictEntry);
        if(id>0){
            //一级部数据
            firstDepLevelPerson(eosDictEntry,userid);
            //中心
            centerLevelPerson(eosDictEntry,userid);
            //二级部
            depLevelPerson(eosDictEntry,userid);
        }
        return id;
    }


    @Override
    @Transactional("defaultTx")
    public int deleteKpiLevelPerson(EosDictEntry eosDictEntry,String userid){
        int id = ppmKpiItemSwitchService.delEvalSet(eosDictEntry);
        if(id>0){
            //删除等级不在评级设置中的数据
             myBatisDao.executionPojo(PPMKPILEVELPERSON, null, IMyBatisDao.SQL.DELETE,"deleteLevelPerson");
        }
        return 1;
    }
}
