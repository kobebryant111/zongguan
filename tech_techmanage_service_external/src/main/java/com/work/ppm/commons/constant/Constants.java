package com.work.ppm.commons.constant;

/**
 * 全局常量类
 *
 */
public class Constants {

    /**
     * token
     */
    public static final String TOKEN = "X-Access-Token";


    /**
     * desc降序
     */
    public final static String ORDER_DESC = "desc";

    /**
     * asc升序
     */
    public final static String ORDER_ASC = "asc";

    /**
     * 用户信息头
     */
    public final static String HEAD_USER_INFO = "USER_HEAD";


    /**
     * 字典实体列表缓存Key
     */
    public static final String DICT_ENTRY_KEY = "dictEntries";

    /**
     * 字典缓存
     */
    public static final String DICT_CACHE = "dict";

    /**
     * 系统代码
     */
    public final static String SYS_CODE = "BUS";

    /**
     * 知识库solr code
     */
    public static final String SOLR_ZCRM_BUSINESS = "zcrm_business";

    /**
     * 收藏
     */
    public static final String COLLECT = "0";

    /**
     * 取消收藏
     */
    public static final String COLLECT_CANCEL = "1";



    /**
     * mybatis分页最大值
     */
    public static final long MYBATIS_MAX_PAGE_SIZE = 30000;


    /**
     * 微信pdf
     */
    public static final String TRANS_TYPE_WX = "WX";

    /**
     * 支付宝pdf
     */
    public static final String TRANS_TYPE_ZFB = "ZFB";

    /**
     * 招商银行pdf
     */
    public static final String TRANS_TYPE_ZS = "ZS";

    /**
     * 建设银行pdf
     */
    public static final String TRANS_TYPE_JS = "JS";

    /**
     * 建设银行pdf
     */
    public static final String TRANS_TYPE_GS = "GS";


    /**
     * PDF
     */
    public static final String FILE_TYPE_PDF = "PDF";

    /**
     * XLS
     */
    public static final String FILE_TYPE_XLS = "XLS";

    /**
     * XLSX
     */
    public static final String FILE_TYPE_XLSX = "XLSX";

    /**
     * CSV
     */
    public static final String FILE_TYPE_CSV = "CSV";

    /**
     * esb系统编号
     */
//    public static final String ESB_CONSUMER_ID = "30400901";
    public static final String ESB_CONSUMER_ID = "cfit";

    /**
     * esb交易成功代码
     */
    public static final String[] ESB_OK = {"0","000","0000","00000","000000"};

    /**
     * esb交易柜员
     */
    public static final String ESB_TRAN_TELLER_NO = "1101CM";

    /**
     * 上传影像 客户交易流水
     */
    public static final String UPLOAD_IMAGE_CUST_TRANS = "CUST_TRANS";
}
