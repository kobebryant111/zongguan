package com.work.ppm.application.dto.esb.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
/**
 * 通用信息数组
 */
@Data
@XStreamAlias("list")
public class CommInfArray {
    /**
     * 员工工号
     */
    @XStreamAlias("EmpNo")
    private String EmpNo;
}
