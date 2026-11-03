package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目分配导出
 */
@Data
@ExcelTarget("PpmRdHrProjectInfoExportDto")
public class   PpmRdHrProjectInfoExportDto  {
    //----
    /**
     * 用户名称
     */
    @Excel(name = "姓名", orderNum = "1")
    private String username;
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "2")
    private String badge;
    /**
     * 0.全职 1.兼职研发1（[85%以上)、2兼职研发2 [70%-85%)、3兼职研发3 [50%-70%)
     */
    @Excel(name = "研发人员性质", width = 20 ,orderNum = "4",replace =
            {"全职100%_0","兼职研发1[85%以上)_1","兼职研发2[70%-85%)_2","兼职研发3[50%-70%)_3"})
    private String nature;

    /**
     * 项目编码
     */
    @Excel(name = "项目编码", orderNum = "4")
    private String projectCode;

    /**
     * 合同金额
     */
    @Excel(name = "项目合同预算金额(万元)", orderNum = "5")
    private String contractAmount;
    /**
     * 项目名称
     */
    @Excel(name = "项目名称", orderNum = "6")
    private String projectName;

    /**
     * 项目状态  0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止
     */
    @Excel(name = "项目状态", orderNum = "7" ,replace = {"立项中_0","进行中_1","收尾中_2","已完结_9","暂停_3","废止_4"})
    private String projectState;
    /**
     * 占比
     */
    @Excel(name = "占比",numFormat = "0.00%",orderNum = "8" )
    private double proportion;

    /**
     * 考勤月份
     */
    @Excel(name = "考勤月份", orderNum = "9" )
    private String attendanceDate;
    /**
     *	1号考勤时长
     **/
    @Excel(name="1号", orderNum = "10")
    private BigDecimal firstOfMontn;

    /**
     *	2号考勤时长
     **/
    @Excel(name="2号", orderNum = "11")
    private BigDecimal twoOfMontn;

    /**
     *	3号考勤时长
     **/
    @Excel(name="3号", orderNum = "12")
    private BigDecimal threeOfMontn;
    /**
     *	4号考勤时长
     **/
    @Excel(name="4号", orderNum = "13")
    private BigDecimal fourOfMontn;
    /**
     *	5号考勤时长
     **/
    @Excel(name="5号", orderNum = "14")
    private BigDecimal fiveOfMontn;
    /**
     *	6号考勤时长
     **/
    @Excel(name="6号", orderNum = "15")
    private BigDecimal sixOfMontn;
    /**
     *	7号考勤时长
     **/
    @Excel(name="7号", orderNum = "16")
    private BigDecimal sevenOfMontn;
    /**
     *	8号考勤时长
     **/
    @Excel(name="8号", orderNum = "17")
    private BigDecimal eightOfMontn;
    /**
     *	9号考勤时长
     **/
    @Excel(name="9号", orderNum = "18")
    private BigDecimal nineOfMontn;
    /**
     *	10号考勤时长
     **/
    @Excel(name="10号", orderNum = "19")
    private BigDecimal tenOfMontn;
    /**
     *	11号考勤时长
     **/
    @Excel(name="11号", orderNum = "20")
    private BigDecimal elevenOfMontn;
    /**
     *	12号考勤时长
     **/
    @Excel(name="12号", orderNum = "21")
    private BigDecimal twelveOfMontn;
    /**
     * 13号考勤时长
     */
    @Excel(name="13号", orderNum = "22")
    private BigDecimal thirteenOfMontn;
    /**
     * 14号考勤时长
     */
    @Excel(name="14号", orderNum = "23")
    private BigDecimal fourteenOfMontn;
    /**
     * 15号考勤时长
     */
    @Excel(name="15号", orderNum = "24")
    private BigDecimal fifteenOfMontn;
    /**
     * 16号考勤时长
     */
    @Excel(name="16号", orderNum = "25")
    private BigDecimal sixteenOfMontn;
    /**
     * 17号考勤时长
     */
    @Excel(name="17号", orderNum = "26")
    private BigDecimal seventeenOfMontn;
    /**
     * 18号考勤时长
     */
    @Excel(name="18号", orderNum = "27")
    private BigDecimal eighteenOfMontn;
    /**
     * 19号考勤时长
     */
    @Excel(name="19号", orderNum = "28")
    private BigDecimal nineteenOfMontn;
    /**
     * 20号考勤时长
     */
    @Excel(name="20号", orderNum = "29")
    private BigDecimal twentyOfMontn;
    /**
     * 21号考勤时长
     */
    @Excel(name="21号", orderNum = "30")
    private BigDecimal twentyOneOfMontn;
    /**
     * 22号考勤时长
     */
    @Excel(name="22号", orderNum = "31")
    private BigDecimal twentyTwoOfMontn;
    /**
     * 23号考勤时长
     */
    @Excel(name="23号", orderNum = "32")
    private BigDecimal twentyThreeOfMontn;
    /**
     * 24号考勤时长
     */
    @Excel(name="24号", orderNum = "33")
    private BigDecimal twentyFourOfMontn;
    /**
     * 25号考勤时长
     */
    @Excel(name="25号", orderNum = "34")
    private BigDecimal twentyFiveOfMontn;
    /**
     * 26号考勤时长
     */
    @Excel(name="26号", orderNum = "35")
    private BigDecimal twentySixOfMontn;
    /**
     * 27号考勤时长
     */
    @Excel(name="27号", orderNum = "36")
    private BigDecimal twentySevenOfMontn;
    /**
     * 28号考勤时长
     */
    @Excel(name="28号", orderNum = "37")
    private BigDecimal twentyEightOfMontn;
    /**
     * 29号考勤时长
     */
    @Excel(name="29号", orderNum = "38")
    private BigDecimal twentyNineOfMontn;
    /**
     * 30号考勤时长
     */
    @Excel(name="30号", orderNum = "39")
    private BigDecimal thirtyOfMontn;
    /**
     * 31号考勤时长
     */
    @Excel(name="31号", orderNum = "40")
    private BigDecimal thirtyOneOfMontn;


}
