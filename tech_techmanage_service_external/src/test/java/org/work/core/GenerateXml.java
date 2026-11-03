package org.work.core;

import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.work.utils.FileUtil;
import org.work.utils.JdbcUtil;

import java.io.File;
import java.util.List;

@Slf4j
public class GenerateXml {
    public void execSqlXml(Generate item, List<JdbcUtil.JdbcPo> list) {
       try{
           // 读取文件
           String context = FileUtil.readertext("sqlx");
           // 解析模板内容
//        list = JdbcUtil.getDataBaseFiled(item.getTableName());
           context = parseTemplate(context,item,list);
           // 写入文件
           FileUtil.writeContext(context, item.getClassNameUpper(),".xml");
           System.out.println(">>>>>>>>>>>" + item.getClassNameUpper() + ".xml 创建成功<<<<<<<<<<<");
       }catch (Exception e){
           log.error("生成sql-xml异常：{}", e);
       }
    }

    /**
     *
     * @param context
     * @param item
     * @param list
     * @return
     */
    public String parseTemplate(String context,Generate item,List<JdbcUtil.JdbcPo> list) {
        context = FileUtil.common(context);
        context = context.replaceAll(FileUtil.getParam("model"), item.getModel());
        context = context.replaceAll(FileUtil.getParam("tableComment"), item.getTableComment());
        context = context.replaceAll(FileUtil.getParam("classNameUpper"), item.getClassNameUpper());
        context = context.replaceAll(FileUtil.getParam("classNameLower"), item.getClassNameLower());
        context = context.replaceAll(FileUtil.getParam("tableName"), item.getTableName());
        context = context.replaceAll(FileUtil.getParam("classNameUpperAll"), item.getClassNameUpperAll());
        // 处理模板中for标签内容
        context = parseTemplateTag(context, item,list);
        return context;
    }

    /**
     * 解析模板标签内容
     * @param context
     * @param list
     * @return
     */
    private String parseTemplateTag(String context, Generate gen, List<JdbcUtil.JdbcPo> list) {
        // 解析jopo属性字段
        String propend = "";
        // 解析后的数据库字段内容，并自动拼接
        String cloumn = "";
        // 换行符
        String wrap = "";
        int j = 0;
        while(context.indexOf("<% for { %>") != -1) {
            cloumn = "";
            String tagStr = context.substring(context.indexOf("<% for { %>") + 11, context.indexOf("<% } %>")).trim();
            // 判断 模板内容标签
            if(j != 0) {
                wrap = "\n\t\t\t\t";
            } else {
                wrap = "\n\t\t";
            }
            for (int i = 0; i < list.size(); i++) {
                JdbcUtil.JdbcPo item = list.get(i);
                propend = tagStr.replaceAll(FileUtil.getParamXml("filedLower"), StringUtil.processClassName(item.getColumnName().toLowerCase()));
                // 是否为空字符串
                String nullStr = "";
                if("String".equals(item.getJavaType())) {
                    nullStr = " and " + StringUtil.processClassName(item.getColumnName().toLowerCase()) + " != '' ";
                }
                propend = propend.replaceAll(FileUtil.getParamXml("Null"), nullStr);

                cloumn += propend.replaceAll(FileUtil.getParamXml("filedUpper"), item.getColumnName().toUpperCase()) + wrap;
                if (i == 0) {
                    String fileId = StringUtil.processClassName(item.getColumnName().toLowerCase());
                    gen.setFiledIdLower(fileId);
                    gen.setFiledIdUpper(item.getColumnName());
                }
            }

            StringBuffer sb = new StringBuffer(context);
            int index = sb.indexOf("<% for { %>");
            sb.delete(index, context.indexOf("<% } %>") + 7);
            sb.insert(index, cloumn);
            context = sb.toString();
            context = context.replaceAll(FileUtil.getParamXml("filedIdUpper"), gen.getFiledIdUpper());
            context = context.replaceAll(FileUtil.getParamXml("filedIdLower"), gen.getFiledIdLower());
            // 处理数据库字段和实体类字段
            proccessFileds(list,gen);
            context = context.replaceAll(FileUtil.getParamXml("filedListUpper"), gen.getFiledListUpper());
            context = context.replaceAll(FileUtil.getParamXml("filedListLower"), gen.getFiledListLower());
            ++j;
        }
        return context;
    }
    /**
     * 处理数据库字段和实体类属性
     * @param list
     * @param gen
     */
    private void proccessFileds(List<JdbcUtil.JdbcPo> list, Generate gen) {
        StringBuffer dataBaseFiled = new StringBuffer();
        StringBuffer entityFiled = new StringBuffer();
        String space = "";
        for (int i = 0; i < list.size(); i++) {
            JdbcUtil.JdbcPo item = list.get(i);
            if(i == 0) {
                space = "\n\t\t\t";
            } else {
                space = "\n\t\t\t";
            }
            if(i == list.size()-1) {
                dataBaseFiled.append(item.getColumnName());
                entityFiled.append("#{").append(StringUtil.processClassName(item.getColumnName().toLowerCase())).append("}");
            } else {
                dataBaseFiled.append(item.getColumnName()).append(",").append(space);
                entityFiled.append("#{").append(StringUtil.processClassName(item.getColumnName().toLowerCase())).append("},").append(space);
            }
        }

        gen.setFiledListLower(entityFiled.toString());
        gen.setFiledListUpper(dataBaseFiled.toString());

    }
}
