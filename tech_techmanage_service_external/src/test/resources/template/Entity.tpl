package com.work.ppm.application.model.#{model};

import lombok.Data;
#{importList}

 /**
  * #{tableComment} 实体对象
  * @author 作者
  */
@Data
public class #{className} {
#{fieldList}
}
