package com.work.ppm.application.dto.othermanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.application.model.othermanager.PpmQuestionOption;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * dto
 */
@Data
@ExcelTarget("ppmQuestionListDto")
public class PpmQuestionListDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 序号
     */
    @Excel(name = "序号", orderNum = "0", width = 20)
    private String questionno;
    /**
     * 题干
     */
    @Excel(name = "题干" , orderNum = "1",width = 100)
    private String content;
    /**
     * 描述
     */
    private String description;
    /**
     * 题目类型
     */
    @Excel(name = "题目类型", orderNum = "2")
    private String questiontype;
    /**
     * 是否删除
     */
    private Integer isdel;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 最后更新时间
     */
    private Date lastmodtime;
    /**
     * 最后操作者
     */
    private String lastmoduser;
    /**
     * 正确答案
     */
    @Excel(name = "正确答案", orderNum = "4")
    private String answer;
    /**
     * 答案解析
     */
    @Excel(name = "答案解析", orderNum = "5")
    private String answerexplain;
    /**
     * 注
     */
    @Excel(name = "注" , orderNum = "6", width = 20)
    private String annotation;
    /**
     * 选项集合
     */
    private List<PpmQuestionOption> options;
    /**
     * 选项
     */
    @Excel(name = "选项")
    private String itemOption;
}
