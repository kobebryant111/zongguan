package com.work.ppm.application.model.othermanager.ext;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.application.model.othermanager.PpmQuestionList;
import com.work.ppm.application.model.othermanager.PpmQuestionOption;
import lombok.Data;

import java.util.List;

/**
 * 扩展
 */
@Data
@ExcelTarget("ppmQuestionListInfo")
public class PpmQuestionListInfo extends PpmQuestionList {
    /**
     * 选项
     */
    @Excel(name = "选项", orderNum = "3")
    private String options;
    /**
     * 集合
     */
    private List<PpmQuestionOption> result;
}
