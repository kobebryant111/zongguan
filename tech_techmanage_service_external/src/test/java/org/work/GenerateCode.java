package org.work;

import lombok.extern.slf4j.Slf4j;
import org.work.constant.Constant;
import org.work.core.Aggeregation;


/**
 * 程序入口
 */
@Slf4j
public class GenerateCode {
    public static void main(String[] args) {
        try {
            Aggeregation aggeregation = new Aggeregation();
            // 表名
            String[] tables = {"PPM_ZJ_PERSON"};
            /**
             * model : 模块名称
             * tables :表名
             */
            System.out.println(">>>>>>>>>>> 目标地址 " + Constant.CREATE_JAVA_DIR + " <<<<<<<<<<");
            aggeregation.process("integratedmanager", tables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
