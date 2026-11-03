package com.work.ppm.application.dto.#{model};

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.annotation.Excel;
#{importList}

 /**
  *  #{tableComment} 传输对象
  * @author 作者
  */
@Data
@ExcelTarget("#{className}Dto")
public class #{className}Dto {
#{fieldList}
}
