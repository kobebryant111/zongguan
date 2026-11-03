package org.work.core;

import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.work.utils.FileUtil;
import org.work.utils.JdbcUtil;

import java.util.List;

@Slf4j
public class GenerateApi {


    /**
     * 执行生成action
     */
    public void execApi(Generate item) {
        try {
            // 读取文件
            String context = FileUtil.readertext("api");
            context = createAction(context, item);
            // 写入文件
            FileUtil.writeContext(context, item.getApiClassName());
            System.out.println(">>>>>>>" + item.getApiClassName() + "创建成功<<<<<<<<<<");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量
     *
     * @param model
     * @param tables
     */
    public void execApi(String model, String... tables) {
        Generate generate = null;
        for (String item : tables) {
            generate = new Generate(item);
            generate.setModel(model);
            this.execApi(generate);
        }
    }


    /**
     * 生成action.java
     *
     * @param context 内容
     * @param item
     * @return
     */
    private static String createAction(String context, Generate item) {
        context = FileUtil.common(context);
        context = context.replaceAll(FileUtil.getParam("apiClassName"), item.getApiClassName());
        context = context.replaceAll(FileUtil.getParam("tableComment"), item.getTableComment());
        context = context.replaceAll(FileUtil.getParam("className"), item.getClassNameLower());
        context = context.replaceAll(FileUtil.getParam("model"), item.getModel());
        context = context.replaceAll(FileUtil.getParam("classNameUpper"), item.getClassNameUpper());
        context = context.replaceAll(FileUtil.getParam("classNameLower"), item.getClassNameLower());
        context = context.replaceAll(FileUtil.getParam("classNameUpperDto"), item.getClassNameUpperDto());
        context = context.replaceAll(FileUtil.getParam("classNameLowerDto"), item.getClassNameLowerDto());
        context = context.replaceAll(FileUtil.getParam("interfaceName"), item.getInterfaceName());
        context = context.replaceAll(FileUtil.getParam("serviceLower"), item.getServiceLower());
        context = parseTemplateTag(context, item);
        return context;
    }

    /**
     * 解析模板标签内容
     *
     * @param context
     * @param gen
     * @return
     */
    private static String parseTemplateTag(String context, Generate gen) {
        try {
            GenerateEntity entity = new GenerateEntity();
            List<JdbcUtil.JdbcPo> list = entity.getEntity(gen.getTableName());
            // 解析jopo属性字段
            String propend = "";
            // 解析后的数据库字段内容，并自动拼接
            String cloumn = "";
            // 换行符
            String wrap = "\n\t\t\t";

            while (context.indexOf("<% for { %>") != -1) {
                cloumn = "";
                String tagStr = context.substring(context.indexOf("<% for { %>") + 11, context.indexOf("<% } %>")).trim();

                for (int j = 0; j < list.size(); j++) {
                    JdbcUtil.JdbcPo item = list.get(j);
                    propend = tagStr.replaceAll(FileUtil.getParam("filedLower"), StringUtil.processClassName(item.getColumnName().toLowerCase()));
                    propend = propend.replaceAll(FileUtil.getParam("javaType"), StringUtil.processClassName(item.getJavaType().toLowerCase()));
                    cloumn += propend.replaceAll(FileUtil.getParam("comments"), item.getComments() == null ? "" : item.getComments().toUpperCase()) + wrap;
                }

                StringBuffer sb = new StringBuffer(context);
                int index = sb.indexOf("<% for { %>");
                sb.delete(index, context.indexOf("<% } %>") + 7);
                sb.insert(index, cloumn);
                context = sb.toString();

            }
        } catch (Exception e) {
            log.error("解析模板异常：{}", e);
        }
        return context;
    }
}
