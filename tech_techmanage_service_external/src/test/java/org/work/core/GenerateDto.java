package org.work.core;

import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.work.constant.Constant;
import org.work.utils.FileUtil;
import org.work.utils.JdbcUtil;

import java.util.List;

@Slf4j
public class GenerateDto {
    public void execDto(Generate item) {
        try {
            List<JdbcUtil.JdbcPo> list = JdbcUtil.getDataBaseFiled(item.getTableName());
            // 读取模板内容
            String context = FileUtil.readertext("Dto");
            // 解析模板内容
            context = this.parseTemplate(context, list, item);
            // 写入模板
            FileUtil.writeContext(context, item.getClassNameUpper() + Constant.ENTITY_DTO);

            System.out.println(">>>>>>>" + item.getClassNameUpper() + "Dto创建成功<<<<<<<<<<");
        } catch (Exception e) {
            log.error("生成实体类异常：{}", e);
        }
    }

    /**
     * 批量
     *
     * @param
     */
    public void execDto(String model, String... tables) {
        Generate generate = null;
        for (String item : tables) {
            generate = new Generate(item);
            generate.setModel(model);
            this.execDto(generate);
        }
    }

    /**
     * 解析模板内容 并返回解析后的内容
     *
     * @param context
     * @param item
     * @return
     */
    private String parseTemplate(String context, List<JdbcUtil.JdbcPo> list, Generate item) {
        this.getEntityFiled(list, item);
        context = FileUtil.common(context);
        context = context.replaceAll(FileUtil.getParam("model"), item.getModel());
        context = context.replaceAll(FileUtil.getParam("importList"), item.getImportList());
        context = context.replaceAll(FileUtil.getParam("className"), item.getClassNameUpper());
        context = context.replaceAll(FileUtil.getParam("tableComment"), item.getTableComment());
        context = context.replaceAll(FileUtil.getParam("fieldList"), item.getField());
        return context;
    }

    /**
     * 获取实体类字段属性
     *
     * @param list
     * @param pojo 实体类
     * @return
     */
    private void getEntityFiled(List<JdbcUtil.JdbcPo> list, Generate pojo) {
        StringBuffer filedSb = new StringBuffer();

        // 空格
        String space = "\t";
        GenerateEntity entity = new GenerateEntity();
        // 字段属性
        for (int j = 0; j < list.size(); j++) {
            JdbcUtil.JdbcPo item = list.get(j);
            // java  字段
            String javaFiled = StringUtil.processClassName(item.getColumnName().toLowerCase());
            // 数据类型
            String javaType = entity.getDataType(item.getDataType(),item.getDataPrecision(), item.getDataScale());
            item.setJavaType(javaType);
//            if (j != 0) {
//                space = "\t";
//            }
            // 新增注释 -- 按照波行命名规范 2020-12-28
            filedSb.append(space).append("/**").append("\n")
                    .append(space).append("*\t")
                    .append(null == item.getComments() ? "字段名" : item.getComments() )
                    .append("\n")
                    .append(space).append("**/").append("\n");
            // 新增注解 -- ApiImplicitParam
            filedSb.append(space)
                    .append("@ApiModelProperty(name = \"")
                    .append(javaFiled).append("\", value = \"")
                    .append(null == item.getComments() ? "" : item.getComments())
                    .append("\", dataType = \"")
                    .append(javaType).append("\")\n");
            // 新增导出注解 -- Excel
            filedSb.append(space)
                    .append("@Excel(name = \"")
                    .append(null == item.getComments() ? "" : item.getComments())
                    .append("\", orderNum = \"")
                    .append(j+1)
                    .append("\")\n");
            // 字段
            filedSb.append(space).append("private ").append(javaType);
            filedSb.append(" ").append(javaFiled).append(";");
            if(j < list.size() -1) {
                filedSb.append("\n");
            }
        }
        pojo.setField(filedSb.toString());
        // 导入主键注解包
        StringBuilder importsb = new StringBuilder();

        // 判断是否存在date数据类型
        if (pojo.getField().indexOf("Date") != -1) {
            importsb.append("import java.util.Date;\n\n");
        }
        pojo.setImportList(importsb.toString());
    }

    /**
     * 获取数据
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public List<JdbcUtil.JdbcPo> getEntity(String tableName) throws Exception {
        List<JdbcUtil.JdbcPo> list = JdbcUtil.getDataBaseFiled(tableName);
        String type = "";
        GenerateEntity entity = new GenerateEntity();
        for (JdbcUtil.JdbcPo item : list) {
            type = entity.getDataType(item.getDataType(),item.getDataPrecision(), item.getDataScale());
            item.setJavaType(type);
        }
        return list;
    }
}
