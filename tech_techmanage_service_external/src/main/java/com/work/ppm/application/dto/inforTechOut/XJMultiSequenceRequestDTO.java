package com.work.ppm.application.dto.inforTechOut;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
/**
 * 自动增加请求实体
 *
 */
@ApiModel(value = "自动增加请求实体")
@Data
public class XJMultiSequenceRequestDTO {

    /**
     * 类型
     */
    @ApiModelProperty(name="seqtype",value="类型")
    @Size(max=38, message="类型长度不能超过38！")
    private String seqtype;

    /**
     * 数字
     */
    @ApiModelProperty(name="seqid",value="数字")
    @Size(max=38, message="数字长度不能超过38！")
    private String seqid;

    /**
     * 前缀
     */
    @ApiModelProperty(name="seqprefix",value="前缀")
    @Size(max=38, message="前缀长度不能超过38！")
    private String seqprefix;
    /**
     * 长度
     */
    private String formatLength;
}
