package com.work.ppm.commons.resourceplat;

/**
 * 资源申请常量类
 */
public class ResAddedConstant {

    /**
     * 构造方法
     */
    private ResAddedConstant(){}

    /** ------ 前端配置的套餐类型 ------ **/
    public static final String COMBOTYPE_CONFIG_1="1";//物理机套餐

    /**
     * 虚拟机套餐
     */
    public static final String COMBOTYPE_CONFIG_2="2";//虚拟机套餐

    /**
     * paas服务套餐
     */
    public static final String COMBOTYPE_CONFIG_3="3";//paas服务套餐

    /**
     * 专用物理机套餐
     */
    public static final String COMBOTYPE_CONFIG_4="4";//专用物理机套餐

    /**
     * 专用虚拟机套餐
     */
    public static final String COMBOTYPE_CONFIG_5="5";//专用虚拟机套餐

    /** ------ 数据库实际的大类 ------ **/

    public static final String COMBOTYPE_1="1";//物理机套餐

    /**
     * 虚拟机套餐
     */
    public static final String COMBOTYPE_3="3";//虚拟机套餐

    /**
     * paas服务套餐
     */
    public static final String COMBOTYPE_6="6";//paas服务套餐
    /**
     * 专用标识
     */
    public static final String ISSPECIAL="1";//专用标识

    /** ------ 数据库实际的小类 ------ **/

    public static final String SUBCOMBOTYPE_61="61";//PaaS服务套餐（Oracle）

    /**
     * PaaS服务套餐（Nginx）
     */
    public static final String SUBCOMBOTYPE_62="62";//PaaS服务套餐（Nginx）

    /**
     * PaaS服务套餐（容器云）
     */
    public static final String SUBCOMBOTYPE_63="63";//PaaS服务套餐（容器云）

    /** ------ 节点类型 ------ **/

    public static final  String NODE_2="2";//web

    /**
     * db
     */
    public static final String NODE_3="3";//db

    /**
     * 其他
     */
    public static final String NODE_5="5";//其他

    /** ------ key ------ **/

    public static final String CATEGORY="category";//大类

    /**
     * 小类
     */
    public static final String SUBCATEGORY="subcategory";//小类

    /**
     * 单价
     */
    public static final String HEAD_PRICE = "单价（元）";//

    /**
     * 总价（元）
     */
    public static final String TOTAL_PRICE = "总价（元）";//单价（元）

    /**
     * #,##0.0
     */
    public static final String SYMBOL = "#,##0.0";

    /**
     * 所属中心
     */
    public static final String OWNCENTERNAME = "所属中心";

    /**
     * 软件名称
     */
    public static final String NAME = "软件名称";

    /**
     * 0f 数值
     */
    public static final float O_F = 0F;

    /**
     * 资源扩容申请
     */
    public static final String RESOURCE_EXPAN = "资源扩容申请";

    /**
     * queryResInfo
     */
    public static final String QUERYRESINFO = "queryResInfo";

    /**
     *APPLYSCENE
     */
    public static final String APPLYSCENE = "APPLYSCENE";

    /**
     * PRICE
     */
    public static final String PRICE = "PRICE";

    /**
     * DISKCAPACITY
     */
    public static final String DISKCAPACITY = "DISKCAPACITY";

    /**
     * RES_OWNCENTER
     */
    public static final String RES_OWNCENTER = "RES_OWNCENTER";

    /**
     * 江北中心
     */
    public static final String JBZX = "江北中心";

    /**
     * 鄞州中心
     */
    public static final String YZZX = "鄞州中心";

}
