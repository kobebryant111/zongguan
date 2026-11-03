package org.work.core;

import lombok.extern.slf4j.Slf4j;
import org.work.utils.FileUtil;
@Slf4j
public class GenerateService {
    public void execService(Generate item) {
      try{
          // 读取文件
          String context = FileUtil.readertext("Service");
          context = createService(context, item);
          // 写入文件
          FileUtil.writeContext(context, item.getServiceUpper());
          System.out.println(">>>>>>>" +item.getServiceUpper() + "创建成功<<<<<<<<<<");
      }catch (Exception E){
          log.error("生成模板service异常:{}", E);
      }
    }

    /**
     * 批量
     * @param model
     * @param tables
     * @throws Exception
     */
    public void execService(String model, String...tables) {
        Generate entity = null;
        for (String item : tables) {
            entity = new Generate(item);
            entity.setModel(model);
            execService(entity);
        }
    }

    /**
     * 生成action.java
     * @param context 内容
     * @param item
     * @return
     */
    private static String createService(String context, Generate item) {
        context = FileUtil.common(context);
        context = context.replaceAll(FileUtil.getParam("model"), item.getModel());
        context = context.replaceAll(FileUtil.getParam("tableComment"), item.getTableComment());
        context = context.replaceAll(FileUtil.getParam("interfaceName"), item.getInterfaceName());
        context = context.replaceAll(FileUtil.getParam("serviceUpper"), item.getServiceUpper());
        context = context.replaceAll(FileUtil.getParam("serviceLower"), item.getServiceLower());
        context = context.replaceAll(FileUtil.getParam("classNameLower"), item.getClassNameLower());
        context = context.replaceAll(FileUtil.getParam("classNameUpper"), item.getClassNameUpper());
        context = context.replaceAll(FileUtil.getParam("classNameLowerDto"), item.getClassNameLowerDto());
        context = context.replaceAll(FileUtil.getParam("classNameUpperDto"), item.getClassNameUpperDto());
        context = context.replaceAll(FileUtil.getParam("classNameUpperAll"), item.getClassNameUpperAll());
        return context;
    }
}
