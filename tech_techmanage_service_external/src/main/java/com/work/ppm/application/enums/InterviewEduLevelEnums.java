package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 学历枚举对象
 */
@Getter
public enum InterviewEduLevelEnums {

    A("A", "研究生", 2.0),
    B("B", "985、211本科", 2.0),
    C("C", "普通本科", 1.5),
    D("D", "专科", 1.0);

    InterviewEduLevelEnums(String code, String name, Double value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    private String code;

    private String name;

    private Double value;

    /**
     * 通过编码取枚举
     *
     * @param code
     * @return
     */
    public static InterviewEduLevelEnums fromCode(String code) {
        for (InterviewEduLevelEnums value : InterviewEduLevelEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
