package com.work.ppm.commons.artemis.request;

import com.work.ppm.commons.artemis.ArtemisPageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取人员列表请求参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonReqt extends ArtemisPageInfo {

    // 人员ID
    private String personId;
    // 姓名
    private String personName;
    // 拼音
    private String pinyin;
}
