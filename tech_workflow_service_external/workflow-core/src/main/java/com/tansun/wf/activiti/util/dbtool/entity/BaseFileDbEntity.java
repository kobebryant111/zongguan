package com.tansun.wf.activiti.util.dbtool.entity;

import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;
import com.tansun.wf.activiti.util.dbtool.tool.Tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 表格实体对外接口
 * @author 罗道宏
 */
public abstract class BaseFileDbEntity {

    public static final String YES = "1";
    public static final String NO = "0";
    public static final String NULL_VAL = "null";
    public static final Integer NULL_NUM = -1;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public abstract SelectRes selectAll();

    /**
     * 删除
     * @return 删除条数
     */
    public abstract int delete();

    /**
     * 不检测插入，请确保所有字段不为空
     * @return 插入结果
     */
    public abstract boolean insertNotVerify();

    /**
     * 生成插入语句
     * @return 插入语句
     */
    public abstract String insertMineStr();

    /**
     * 修改
     * @param origin 原记录
     * @return 修改条数
     */
    public abstract int update(BaseFileDbEntity origin);

    /**
     * 建表
     * @return 建表结果
     */
    public abstract boolean create();

    /**
     * 删表
     * @return 删表结果
     */
    public abstract boolean drop();

    /**
     * 获取默认属性实例
     * @return 默认属性实例
     */
    public abstract BaseFileDbEntity defaultAttribute();

    /**
     * 建表执行
     * @param sql 建表sql
     * @return 建表结果
     */
    protected boolean createTable(String sql){
        Operater ch = new Operater(sql);
        Table tb = ch.checkCreate();
        Tables t= Tools.readTables();
        if(t==null) {
            if(tb!=null) {
                Tables tbs = new Tables();
                tbs.getTables().add(tb);
                Tools.writeTables(tbs);
                return true;
            }
        } else {
            if (t.getTables().contains(tb)) {
                throw new DbToolException(tb.getName() + ":表已经存在！创建失败~");
            } else {
                t.getTables().add(tb);
                Tools.writeTables(t);
                return true;
            }
        }
        return false;
    }

    protected boolean dropTable(String tableName){
        // 移除表内容
        if(!Tools.removeFile(tableName + ".tb")){
            return false;
        }
        // 移除表结构
        Tables tables = Tools.readTables();
        if(tables == null){
            return true;
        }
        List<Table> tbs = tables.getTables();
        tbs.removeIf(table -> table.getName().equals(tableName));
        Tools.writeTables(tables);
        return true;
    }

    // key、val
    protected Map<String, List<String>> getValuableAtt(){
        Class<?> clz = this.getClass();
        Field[] fields = clz.getDeclaredFields();
        List<String> keys = new ArrayList<>();
        List<String> vals = new ArrayList<>();
        try {
            for(Field field : fields) {
                String getName = "get" + upperFirst(field.getName());
                Method m = this.getClass().getMethod(getName);
                Object val = m.invoke(this);
                if (val != null) {
                    keys.add(field.getName());
                    vals.add(val.toString());
                }
            }
        } catch (Exception e) {
            throw new DbToolException("获取有值属性表失败了");
        }
        Map<String, List<String>> res = new HashMap<>();
        res.put("keys", keys);
        res.put("vals", vals);
        return res;
    }

     private static String upperFirst(String fildeName){
         byte[] items = fildeName.getBytes();
         items[0] = (byte) ((char) items[0] - 'a' + 'A');
         return new String(items);
     }
}
