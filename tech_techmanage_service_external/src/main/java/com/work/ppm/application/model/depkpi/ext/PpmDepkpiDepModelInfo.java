package com.work.ppm.application.model.depkpi.ext;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepModel;
import lombok.Data;
/**
 * 模板映射表
 */
@Data
public class PpmDepkpiDepModelInfo extends PpmDepkpiDepModel {
    /**
     *	模板总数
     **/
    private int total;
    /**
     *	模板名称
     **/
    private String name;
}
