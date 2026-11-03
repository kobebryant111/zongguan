package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiTitle;
import lombok.Data;

import java.util.List;

/**
 * 考核标题表
 *
 */
@Data
public class PpmDepkpiTitleInfo extends PpmDepkpiTitle {
    /**
     * 所属考核项id
     **/
    private Long targetid;

    /**
     * 部门指标明细id
     **/
    private Long detailid;
    /**
     * 标题数据
     **/
    private List<PpmDepkpiTitle> titlelist;
    /**
     * 内容分求和
     **/
    private Long sumContentPoint;
}
