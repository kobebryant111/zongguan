package com.work.ppm.application.dto.integratedmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 *  加班开始时间配置表 传输对象
 */
@Data
public class PpmKqOvertimeConfigDto {
   /**
   *	号数
   **/
   @ApiModelProperty(name = "id", value = "编号", dataType = "Long")
   private Long id;
   /**
   *	加班开始时间
   **/
   @ApiModelProperty(name = "startTime", value = "加班开始时间", dataType = "String")
   private String startTime;
   /**
   *	生效日期
   **/
   @JsonFormat(pattern = "yyyy-MM-dd")
   @ApiModelProperty(name = "effectDate", value = "生效日期", dataType = "Date")
   private Date effectDate;
   /**
   *	是否生效
   **/
   @ApiModelProperty(name = "status", value = "是否生效", dataType = "String")
   private String status;
   /**
   *	创建时间
   **/
   @ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
   private Date createdTime;
   /**
    *	是否生效 where条件
    **/
   @ApiModelProperty(name = "statusWhere", value = "是否生效 where条件", dataType = "String")
   private String statusWhere;

}
