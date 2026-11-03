package com.work.ppm.commons.utils;

import com.work.ppm.application.dto.integratedmanager.FileInfoDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@UtilityClass
public class CompressUtil {

    /**
     * 创建ZIP压缩文件
     *
     * @param sourceFiles
     * @param zipFilePath
     */
    public void compress(List<FileInfoDto> sourceFiles, String zipFilePath) throws IOException {
        byte[] buffer = new byte[1024];
        File zipFile = new File(zipFilePath);
        FileInputStream fis = null;
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            log.info("===========》创建压缩包开始《===========");
            for (FileInfoDto sourceFile : sourceFiles) {
                String fileName = sourceFile.getFileName();
                String filePath = sourceFile.getFilePath();
                log.info("docName:{}", fileName);
                log.info("filePath:{}", filePath);
                if (!StringUtil.isNotEmpty(fileName)) {
                    continue;
                }
                File realFile = new File(filePath);
                if (null == realFile || !realFile.exists()) {
                    continue;
                }
                fis = new FileInputStream(realFile);
                zos.putNextEntry(new ZipEntry(fileName));
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
        } catch (IOException e) {
            log.error("创建ZIP压缩包出错: {}", e.getMessage());
        } finally {
            fis.close();
        }
    }

//    public static void main(String[] args) throws IOException {
//        String zipFile = "D:\\test.zip";
//        List<Map<String, String>> sourceFiles = Lists.newArrayList();
//        Map<String, String> file = new HashMap<>();
//        file.put("docName", "考核日常要点梳理.xlsx");
//        file.put("filePath", "D:\\考核日常要点梳理.xlsx");
//        sourceFiles.add(file);
//        file = new HashMap<>();
//        file.put("docName", "科管打包命令.md");
//        file.put("filePath", "D:\\科管打包命令.md");
//        sourceFiles.add(file);
//        file = new HashMap<>();
//        file.put("docName", "永赢租赁金融科技部外包员工简历.xlsx");
//        file.put("filePath", "D:\\永赢租赁金融科技部外包员工简历.xlsx");
//        sourceFiles.add(file);
//        CompressUtil.compress(sourceFiles, zipFile);
//    }

}
