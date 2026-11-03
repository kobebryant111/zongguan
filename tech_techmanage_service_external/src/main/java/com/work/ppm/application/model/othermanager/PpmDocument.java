package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;

import java.util.Date;


/**
 * 文档
 *
 */
@Data
public class PpmDocument {
    /**
     * ??PPM_DOCUMENT_ID  ??EOS?????????
     **/
    @Id
    private Long id;
    /**
     * 字段名
     **/
    private String catalogno;
    /**
     * 字段名
     **/
    private String docname;
    /**
     * A1:??????
     * A2:???????
     * A3:??????
     * A4:????
     * A5:????
     **/
    private String objecttype;
    /**
     * 字段名
     **/
    private String objectno;
    /**
     * 字段名
     **/
    private String stockpath;
    /**
     * ????????GLOBAL_YESNO
     **/
    private String iswrite;
    /**
     * ????????GLOBAL_YESNO
     **/
    private String isread;
    /**
     * 字段名
     **/
    private String docdomain;
    /**
     * 字段名
     **/
    private String tag1;
    /**
     * 字段名
     **/
    private String tag2;
    /**
     * 字段名
     **/
    private String tag3;
    /**
     * 1-????0-???
     **/
    private Long isdel;
    /**
     * 字段名
     **/
    private Date createtime;
    /**
     * 字段名
     **/
    private String creators;
    /**
     * 字段名
     **/
    private Date lastmodtime;
    /**
     * 字段名
     **/
    private String lastmoduser;
    /**
     * 字段名
     **/
    private String documentno;
    /**
     * 字段名
     **/
    private String currentversion;
    /**
     * 字段名
     **/
    private String iparea;
    /**
     * 字段名
     **/
    private String createorid;
    /**
     * 字段名
     **/
    private String lastmoduserid;
    /**
     * 上传阶段
     **/
    private String stage;

    /**
     * 文档枚举
     *
     */
    public enum DocmentEnum {
        /**
         * 采购
         */
        DOC_CG("采购", "A9"),
        /**
         * 可行性
         */
        DOC_KXX("可行性", "A5"),
        /**
         * 电子印章
         */
        DOC_DZYZ("电子印章", "A10"),
        /**
         * 需求
         */
        DOC_XQ("需求", "A3"),
        /**
         * 预审材料
         */
        DOC_YSCL("预审材料", "A11");

        /**
         * 构造函数
         */
        DocmentEnum(String type, CharSequence name) {
            this.name = name;
            this.type = type;
        }

        /**
         * 类型
         */
        private String type;
        /**
         * 名称
         */
        private CharSequence name;

        /**
         * 获取名称
         * @param type 类型
         * @return CharSequence
         */
        public static CharSequence getName(String type) {
            for (int i = DocmentEnum.values().length - 1; i >= 0; i--) {
                DocmentEnum item = DocmentEnum.values()[i];
                if(type.equals(item.getType())) {
                    return item.getName();
                }
            }
            return "";
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CharSequence getName() {
            return name;
        }

        public void setName(CharSequence name) {
            this.name = name;
        }
    }
}
