package com.work.ppm.commons.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.work.cxf.client.util.StrUtils;
import com.work.ppm.commons.constant.FileProperties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Slf4j
@UtilityClass
public class PdfUtils {

    public static final String OS_NAME = "os.name";
    public static final FileProperties fileProperties = SpringUtil.getBean(FileProperties.class);

    /**
     * 创建卡牌文件
     *
     * @param pdfParams  文本参数
     * @param headImg    卡牌头像
     * @param targetFile 存储路径
     * @throws Exception
     */
    public void createCardPdf(Map<String, String> pdfParams, String headImg, String targetFile)
            throws Exception {
        // 取模板文件
        File pdfTemplate = getPdfTemplate();
        try (InputStream inputStream = new FileInputStream(pdfTemplate);
             OutputStream newPdfStream = new FileOutputStream(targetFile);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfReader pdfReader = new PdfReader(inputStream);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
            // 文本填充
            AcroFields acroFields = pdfStamper.getAcroFields();
            BaseFont baseFont;
            if (isWindows()) {
                baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\msyh.ttc,0",
                        BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            } else {
                baseFont = BaseFont.createFont("/nasfs/document/fonts/msyh.ttc,0",
                        BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
            acroFields.addSubstitutionFont(baseFont);
            for (String key : pdfParams.keySet()) {
                acroFields.setField(key, pdfParams.get(key));
            }
            // 图片填充
            if (StrUtils.isNotEmpty(headImg)) {
                //判断头像是否存在
                File headFile = new File(headImg);
                if (headFile.exists()) {
                    InputStream headImgStream = new FileInputStream(headImg);
                    byte[] imageByte = toByteArray(headImgStream);
                    Image headImage = Image.getInstance(imageByte);
                    // 卡一图片
                    headImage.setAbsolutePosition(205, 682);
                    PdfContentByte underContent = pdfStamper.getOverContent(1);
                    headImage.scaleAbsolute(72, 100);
                    underContent.addImage(headImage);
                    // 卡二图片
                    headImage.setAbsolutePosition(463, 682);
                    underContent.addImage(headImage);
                    headImgStream.close();
                }
            }
            // 是否禁用PDF编辑功能
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();
            Document document = new Document();
            PdfCopy pdfCopy = new PdfCopy(document, newPdfStream);
            document.open();
            PdfImportedPage importedPage = pdfCopy.getImportedPage(
                    new PdfReader(byteArrayOutputStream.toByteArray()), 1);
            pdfCopy.addPage(importedPage);
            document.close();
        } catch (Exception e) {
            log.error("创建卡牌文件失败:{}", e.getCause());
            throw new Exception(e);
        }
    }

    /**
     * 取出PDF模版
     *
     * @return
     * @throws FileNotFoundException
     */
    public File getPdfTemplate() throws FileNotFoundException {
        //return new File("C:\\Users\\ywwb436\\Downloads\\CardInfo.pdf");
        String root = fileProperties.getTemplatePath();
        return new File(root + "src/main/resources/CardInfo.pdf");
    }

    public boolean isLinux() {
        return System.getProperty(OS_NAME).toLowerCase().contains("linux");
    }

    public boolean isWindows() {
        return System.getProperty(OS_NAME).toLowerCase().contains("windows");
    }

    /**
     * 文件流转字节
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] toByteArray(InputStream inputStream) throws IOException {
        if (null == inputStream) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (true) {
            try {
                if (!(-1 != (n = inputStream.read(buffer)))) break;
            } catch (IOException e) {
                log.error("类型转换异常：{}", e.getCause());
                throw new IOException(e);
            }
            outputStream.write(buffer, 0, n);
        }
        return outputStream.toByteArray();
    }

}
