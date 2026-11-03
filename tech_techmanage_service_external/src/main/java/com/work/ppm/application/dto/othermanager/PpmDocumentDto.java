package com.work.ppm.application.dto.othermanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * dto
 */
@Data
public class PpmDocumentDto {
    /**
     *	??PPM_DOCUMENT_ID  ??EOS?????????
     **/
    private Long id;
    /**
     *	字段名
     **/
    private String catalogno;
    /**
     *	字段名
     **/
    private String docname;
    /**
     *	A1:??????
     A2:???????
     A3:??????
     A4:????
     A5:????
     **/
    private String objecttype;
    /**
     *	字段名
     **/
    private String objectno;
    /**
     *	字段名
     **/
    private String stockpath;
    /**
     *	????????GLOBAL_YESNO
     **/
    private String iswrite;
    /**
     *	????????GLOBAL_YESNO
     **/
    private String isread;
    /**
     *	字段名
     **/
    private String docdomain;
    /**
     *	字段名
     **/
    private String tag1;
    /**
     *	字段名
     **/
    private String tag2;
    /**
     *	字段名
     **/
    private String tag3;
    /**
     *	1-????0-???
     **/
    private Long isdel;
    /**
     *	字段名
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    /**
     *	字段名
     **/
    private String creators;
    /**
     *	字段名
     **/
    private Date lastmodtime;
    /**
     *	字段名
     **/
    private String lastmoduser;
    /**
     *	字段名
     **/
    private String documentno;
    /**
     *	字段名
     **/
    private String currentversion;
    /**
     *	字段名
     **/
    private String iparea;
    /**
     *	字段名
     **/
    private String createorid;
    /**
     *	字段名
     **/
    private String lastmoduserid;
    /**
     *	上传阶段
     **/
    private String stage;
    /**
     * 通用参数配置key
     */
    private String key;
    /**
     * 文档唯一编号，多个逗号分开
     */
    private String documentnos;
    // ============ 手机端 使用的参数 -======
    /**
     * 手机存放附件的路径
     */
    private String mobilePath;
    /**
     * 资源文件路径
     */
    private String sourceFilePath;
}
