package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * dto
 */
@Data
public class PpmOutsourcingInfoPriceDto {
    /**
     *	主键
     **/
    private Long id;
    /**
     *	供应商编号
     **/
    private String orgcode;
    /**
     *	岗位
     **/
    private String position;
    /**
     *	1档开始价格
     **/
    private Long firststartprie;
    /**
     *	1档结束价格
     **/
    private Long firstendprie;
    /**
     *	2档开始价格
     **/
    private Long secondstartprie;
    /**
     *	2档结束价格
     **/
    private Long secondendprie;
    /**
     *	3档开始价格
     **/
    private Long thirdstartprie;
    /**
     *	3档结束价格
     **/
    private Long thirdendprie;
    /**
     *	4档开始价格
     **/
    private Long fourthstartprie;
    /**
     *	4档结束价格
     **/
    private Long fourthendprie;
    /**
     *	备注
     **/
    private String remark;
    /**
     *	创建时间
     **/
    private Date createtime;
    /**
     *	创建人
     **/
    private String createusrid;
    /**
     * 外包商名称
     */
    @Excel(name = "供应商", orderNum = "1",width = 30)
    private String outsourcname;
    /**
     * 人员类型
     *
     */
    @Excel(name = "人员类型", orderNum = "2",width = 30)
    private String positionName;
    @Excel(name = "一档（1-3年）", orderNum = "3",width = 30)
    private String firstPrice;
    @Excel(name = "二档（3-5年）", orderNum = "4",width = 30)
    private String secondPrice;
    @Excel(name = "三档（5-7年）", orderNum = "5",width = 30)
    private String thirdPrice;
    @Excel(name = "四档（7年以上）", orderNum = "6",width = 30)
    private String fourthPrice;
    /**
     * 是否删除
     */
    private String isDel;
    /**
     * 删除变量
     */
    private String delVal;
}
