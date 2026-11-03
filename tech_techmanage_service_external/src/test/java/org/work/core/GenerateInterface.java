package org.work.core;

import lombok.extern.slf4j.Slf4j;
import org.work.utils.FileUtil;

@Slf4j
public class GenerateInterface {
    /**
     * 生成文件
     *
     * @param item
     * @throws Exception
     */
    public void execIservice(Generate item) {
        try {
            // 读取文件
            String context = FileUtil.readertext("IService");
            context = createIservice(context, item);
            // 写入文件
            FileUtil.writeContext(context, item.getInterfaceName());
            System.out.println(">>>>>>>" + item.getInterfaceName() + "创建成功<<<<<<<<<<");
        } catch (Exception e) {
            log.error("生成接口service异常:{}", e);
        }
    }

    /**
     * 批量
     *
     * @param model
     * @param tables
     * @throws Exception
     */
    public void execIservice(String model, String... tables) {
        Generate entity = null;
        for (String item : tables) {
            entity = new Generate(item);
            entity.setModel(model);
            execIservice(entity);
        }
    }

    /**
     * 生成action.java
     *
     * @param context 内容
     * @param item
     * @return
     */
    private static String createIservice(String context, Generate item) {
        context = FileUtil.common(context);
        context = context.replaceAll(FileUtil.getParam("classNameUpper"), item.getClassNameUpper());
        context = context.replaceAll(FileUtil.getParam("classNameLower"), item.getClassNameLower());
        context = context.replaceAll(FileUtil.getParam("classNameUpperDto"), item.getClassNameUpperDto());
        context = context.replaceAll(FileUtil.getParam("classNameLowerDto"), item.getClassNameLowerDto());
        context = context.replaceAll(FileUtil.getParam("tableComment"), item.getTableComment());
        context = context.replaceAll(FileUtil.getParam("model"), item.getModel());
        context = context.replaceAll(FileUtil.getParam("interfaceName"), item.getInterfaceName());
        return context;
    }
}
