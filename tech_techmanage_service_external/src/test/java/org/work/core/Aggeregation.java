package org.work.core;

import lombok.extern.slf4j.Slf4j;
import org.work.constant.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class Aggeregation {
    GenerateApi api = new GenerateApi();
    GenerateEntity entity = new GenerateEntity();
    GenerateDto dto = new GenerateDto();
    GenerateInterface interfaces = new GenerateInterface();
    GenerateService service = new GenerateService();

    /**
     * 生成代码
     * @param model 模块名称
     * @param tables 表名 可数组
     * @throws Exception
     */
    public void process(String model,String... tables) {
        try{
            // 将表名转换大写
            List<String> list = tableNmaeToUpper(tables);
            api.execApi(model, list.toArray(new String[0]));
            entity.execEntity(model, list.toArray(new String[0]));
            dto.execDto(model, list.toArray(new String[0]));
            interfaces.execIservice(model, list.toArray(new String[0]));
            service.execService(model, list.toArray(new String[0]));
            java.awt.Desktop.getDesktop().open(new File(Constant.CREATE_JAVA_DIR));
        }catch (Exception e) {
            log.error("生成代码异常：{}", e);
        }
    }

    /**
     * 表名转大写
     * @param tables
     * @return
     */
    private List<String> tableNmaeToUpper(String[] tables) {
        List<String > list = new ArrayList<>();
        for (String item: tables
             ) {
            list.add(item.toLowerCase());
        }
        return list;
    }
}
