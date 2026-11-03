package com.work.ppm.commons.utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel添加水印
 * @date 2021-18-16 09:17
 */
@Slf4j
public class ExcelWaterRemarkUtils {

    /**
     * 对象
     */
    private ExcelWaterRemarkUtils() {
    throw new IllegalStateException("ExcelWaterRemarkUtils class");
}

    /**
     * OOXML规范的excel的sheet.xml中的drawing标签及以下的标签的字节数组
     */
    private static final byte[][] OOXML_TAG_WORKSHEET_DRAWING_FOLLOWING = {
            "drawing".getBytes(),
            "legacyDrawing".getBytes(),
            "legacyDrawingHF".getBytes(),
            "picture".getBytes(),
            "oleObjects".getBytes(),
            "controls".getBytes(),
            "webPublishItems".getBytes(),
            "tableParts".getBytes(),
            "extLst".getBytes()
    };

    /**
     * 获取OOXML规范的excel的sheet.xml中的drawing标签中id属性的正则表达式
     */
    private static final Pattern OOXML_TAG_WORKSHEET_DRAWING_ID_PATTERN =
            Pattern.compile(".*r:id=\"(.+?)\".*", Pattern.DOTALL);


    /**
     * 往Excel中添加图片水印
     * @param wb wb
     * @param sheet sheet
     * @param waterMarkByte waterMarkByte
     * @param startXcol startXcol
     * @param startYRow startYRow
     * @param betweenXCol betweenXCol
     * @param betweenYRow betweenYRow
     */
    public static  void putWaterMarkToExcel(Workbook wb, Sheet sheet, byte[] waterMarkByte,int startXcol,
                                            int startYRow,int betweenXCol,int betweenYRow) {

        int xcount=1;
        int ycount=1;
        int waterRemarkWidth=0;
        int waterRemarkHeight=0;
        // 如果文本水印图片的字节数组为空则报错
        if (ArrayUtils.isEmpty(waterMarkByte)) {
            log.info("文本水印图片为空");
        }
        Drawing drawing = sheet.createDrawingPatriarch();
        for (int yCount = 0; yCount < ycount; yCount++) {
            for (int xCount = 0; xCount < xcount; xCount++) {
                int xIndexInteger = startXcol + (4 * waterRemarkWidth) + (xCount * betweenXCol);
                int yIndexInteger = startYRow + (4 * waterRemarkHeight) + (yCount * betweenYRow);
                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger,
                        yIndexInteger, xIndexInteger + waterRemarkWidth, yIndexInteger + waterRemarkHeight);

                Picture picture = drawing.createPicture(anchor,
                        wb.addPicture(waterMarkByte, Workbook.PICTURE_TYPE_PNG));
                picture.resize();
            }
        }
    }

    /**
     * 水印图片生成
     * @param content  内容
     * @param path   路径
     * @throws IOException 异常
     */
    public  static  void createWaterMark(String content,String path) throws IOException {
        Integer width = 900;
        Integer height = 200;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        String fontType = "宋体";
        Integer fontStyle= Font.PLAIN;
        Integer fontSize = 50;
        Font font = new Font(fontType,fontStyle,fontSize);
        Graphics2D g2d = image.createGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(width,height,Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        g2d.setColor(new Color(0,0,0,20));
        g2d.setStroke(new BasicStroke(1));
        g2d.setFont(font);
        g2d.rotate(Math.toRadians(-5),(double) image.getWidth()/2,(double)(image.getHeight())/2);
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content,context);
        double x = (width - bounds.getWidth())/2;
        double y = height / 2;
        g2d.drawString(content,(int)x,(int)y);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2d.dispose();
        ImageIO.write(image,"png",new File(path));
    }

    /**
     * 生成文本水印图片
     * @param content 文本水印内容
     * @return java.awt.image.BufferedImage 文本水印图片
     * @date 2020/7/1 9:54
     */
    public static BufferedImage createWaterMark(String content) {
        int width = 900; // 水印图片宽度
        int height = 200; // 水印图片高度
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // 创建指定宽度和高度且带透明度的图片

        Graphics2D g2d = image.createGraphics(); // 获取绘图对象
        g2d.setColor(new Color(0, 0, 0, 20)); // 设置颜色
        g2d.setStroke(new BasicStroke(1)); // 设置描边

        Font font = new Font("宋体", Font.PLAIN, 50); // 水印字体
        g2d.setFont(font); // 设置字体
        g2d.rotate(Math.toRadians(-5), image.getWidth() / 2D, image.getHeight() / 2D); // 设置图片倾斜
        Rectangle2D bounds = font.getStringBounds(content, g2d.getFontRenderContext()); // 获取根据指定字体和指定水印内容的呈现时的逻辑边界
        double x = (width - bounds.getWidth()) / 2; // 计算水印内容呈现位置的x轴起始点
        double y = height / 2; // 计算水印内容呈现位置的y轴起始点
        g2d.drawString(content, (int)x, (int)y); // 在指定坐标呈现水印内容
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); // 将水印呈现在上面
        g2d.dispose(); // 释放绘图对象

        return image;
    }

    /**
     * 生成文本水印图片(png类型)的字节数组
     * @param content 文本水印内容
     * @throws IOException 异常
     * @return byte[] 文本水印图片(png类型)的字节数组
     * @date 2020/7/1 9:54
     */
    public static byte[] createWaterMarkToByte(String content) throws IOException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(createWaterMark(content), "png", outputStream); // 将文本水印图片按png格式写出到字节流中
            return outputStream.toByteArray(); // 返回文本水印图片的字节数组
        }
    }

    /**
     * 添加水印
     * @param abpath  路径
     * @param content 内容
     * @param fileName 文件名称
     * @throws IOException  异常
     */
    public static void printExcelXlsxAb(String abpath,String content,String fileName) throws IOException {
        addWaterMarkToExcel(abpath, content, fileName, false);

    }

    /**
     * 添加水印
     * @param abpath abpath
     * @param content  content
     * @param fileName  fileName
     * @throws IOException 异常
     */
    public  static  void printExcelXlsAb(String abpath,String content,String fileName) throws IOException {
        addWaterMarkToExcel(abpath, content, fileName, true);
    }

    /**
     * excel加水印
     * @param abpath excel文件路径
     * @param content 水印内容
     * @param fileName 加水印后的excel保存路径
     * @param xlsType 是否是xls老版本
     * @throws IOException 异常
     */
    public static void addWaterMarkToExcel(String abpath, String content, String fileName,
                                           boolean xlsType) throws IOException {
        // 判断输出文件的路径是否存在，如果不存在则创建
        File file = new File(fileName);
        if ((!file.getParentFile().exists() || !file.getParentFile().isDirectory()) && !file.getParentFile().mkdirs()) {
            //文件夹创建失败的情况
            log.error("文件夹创建失败");
        }
        try (FileInputStream fileInputStream = new FileInputStream(abpath);
             Workbook wb = xlsType ? new HSSFWorkbook(fileInputStream) :
                     new XSSFWorkbook(fileInputStream); // 打开对应类型的excel
             OutputStream fos = new FileOutputStream(file)) {

            ZipSecureFile.setMinInflateRatio(-1.0D);
            byte[] waterMarkByte = createWaterMarkToByte(content);// 文本水印图片的字节数组
            // 循环为每个sheet添加水印
            for (int i=0; i< wb.getNumberOfSheets(); i++){
                Sheet sheet = wb.getSheetAt(i);
                putWaterMarkToExcel(wb,sheet,waterMarkByte,1,1,5,5);
            }
            wb.write(fos); // 保存添加水印后的excel
        }

    }

    /**
     * 2007及以上版的excel加水印
     * @param excelOriginFilePath excel源文件路径
     * @param content 水印内容
     * @throws IOException  异常
     * @throws InvalidFormatException  异常
     */
    public static void addWaterMarkToXlsx(String excelOriginFilePath, String content)
            throws IOException, InvalidFormatException {
        // 使用OPCPackage通过流的方式修改excel
        try (OPCPackage opcPackage = OPCPackage.open(new File(excelOriginFilePath))) {
            // 创建文本水印图片并将该图片添加excel中并返回图片资源/xl/media/image#.png文件的名称
            PackagePartName imagePartName = addImagePartToXlsx(opcPackage, createWaterMarkToByte(content));
            if (imagePartName == null) {   // 如果图片资源/xl/media/image#.png文件的名称为null则直接返回
                return;
            }

            opcPackage.getPartsByContentType(XSSFRelation.WORKSHEET.getContentType()).forEach(sheetPart ->
                    addWaterMarkToXlsxSheet(
                            sheetPart,
                            // sheet已经存在图片时添加文本水印图片
                            workSheetDrawingId -> {

                                // 根据sheet#.xml中的drawing标签的id的值找到对应的drawing#.xml和sheet#.xml的关系
                                Optional<PackageRelationship> drawingWithSheetRelationship =
                                        Optional.ofNullable(workSheetDrawingId).map(sheetPart::getRelationship);

                                // 如果找不到对应的关系则暂时先直接返回，理论上没有这种情况
                                if (!drawingWithSheetRelationship.isPresent()) {
                                    return;
                                }
                                PackagePart drawingPart = null; // drawing#.xml部分
                                try {
                                    // 根据关系对象找到对应的drawing#.xml
                                    drawingPart = sheetPart.getRelatedPart(drawingWithSheetRelationship.get());
                                } catch (InvalidFormatException e) {
                                    e.printStackTrace();
                                }

                                methodOne(drawingPart,imagePartName);

                            },
                            // sheet不存在图片时添加文本水印图片
                            currByteOffset -> {
                                // 如果偏移量小于等于0则直接返回
                                if (currByteOffset <= 0) {
                                    return;
                                }
                                methodTwo(currByteOffset,imagePartName,opcPackage,sheetPart);
                            }
                    )
            );

            opcPackage.flush();
        }
    }

    /**
     * 方法一
     * @param drawingPart   画图
     * @param imagePartName 图片名
     */
    public static void methodOne(PackagePart drawingPart,PackagePartName imagePartName ){
        // 如果没有drawing#.xml暂时先直接返回，理论上没有这种情况
        if (drawingPart == null) {
            return;
        }

        // 使用/xl/media/image#.png文件的名称为/xl/drawings/drawing#.xml文件添加一个image/png类型的关联关系
        PackageRelationship drawingRelationship = drawingPart.addRelationship(imagePartName, TargetMode.INTERNAL,
                XSSFRelation.IMAGE_PNG.getRelation());
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(drawingPart.getInputStream()); // 输入流
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(drawingPart.getOutputStream()) // 输出流
        ) {
            int byteSize = 1024; // 一次读取的字节数组大小，1KB
            byte[] buffer = new byte[byteSize]; // 读取的字节数组
            int len; // 实际读取的字节数

            // 只读取到/xl/drawings/drawing#.xml文件的结束标签</xdr:wsDr>前面作为第一部分，这样就可以再插入新增的图片的信息了
            long ln = drawingPart.getSize();
            int size = (int)ln-11;
            // 如果第一部分需要读入写出的部分的字节数大于0则循环读取并写出内容
            while (size > 0) {
                // 比较剩余需要读取的字节数和一次读取的字节数组大小，哪个小就读取对应的字节数
                len = bufferedInputStream.read(buffer, 0, Math.min(size, byteSize));
                bufferedOutputStream.write(buffer, 0, len); // 像输出流写出读入的字节数据
                size -= len; // 剩余需要读取的字节数减掉实际已经读取的字节数
            }

            // 写出新增的图片的信息和结束标签</xdr:wsDr>
            bufferedOutputStream.write((getImageInfoInDrawing(drawingRelationship.getId()) + "</xdr:wsDr>").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法二
     * @param currByteOffset currByteOffset
     * @param imagePartName imagePartName
     * @param opcPackage opcPackage
     * @param sheetPart sheetPart
     */
    public static void methodTwo(int currByteOffset,PackagePartName imagePartName,
                                 OPCPackage opcPackage,PackagePart sheetPart ){
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(sheetPart.getInputStream()); // 输入流
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(sheetPart.getOutputStream()) // 输出流
        ) {
            // 将图片资源添加到新增的绘画资源中并返回绘画资源/xl/drawings/drawing#.xml文件的名称
            PackagePartName drawingPartName = addDrawingPartToXlsxByImage(opcPackage, imagePartName);

            // 如果绘画资源/xl/drawings/drawing#.xml文件的名称为null则直接返回
            if (drawingPartName == null) {
                return;
            }

            // 使用/xl/drawings/drawing#.xml文件的名称为/xl/worksheets/sheet#.xml文件添加一个drawing类型的关联关系
            PackageRelationship sheetRelationship = sheetPart.addRelationship(drawingPartName,
                    TargetMode.INTERNAL, XSSFRelation.DRAWINGS.getRelation());

            int byteSize = 1024; // 一次读取的字节数组大小，1KB
            byte[] buffer = new byte[byteSize]; // 读取的字节数组
            int len; // 实际读取的字节数

            // /xl/worksheets/sheet#.xml文件从开始位置到需要插入<drawing />标签的字节偏移量，即第一部分需要读入写出的部分
            int size = currByteOffset;
            // 如果第一部分需要读入写出的部分的字节数大于0则循环读取并写出内容
            while (size > 0) {
                // 比较剩余需要读取的字节数和一次读取的字节数组大小，哪个小就读取对应的字节数
                len = bufferedInputStream.read(buffer, 0, Math.min(size, byteSize));
                bufferedOutputStream.write(buffer, 0, len); // 像输出流写出读入的字节数据
                size -= len; // 剩余需要读取的字节数减掉实际已经读取的字节数
            }

            // 写出<drawing />标签，<drawing />标签的id为上面加的drawing类型的关联关系的id，这样就可以对应上了
            bufferedOutputStream.write(("<drawing r:id=\"" + sheetRelationship.getId() + "\"/>").getBytes());

            // 原sheet#.xml剩余需要读入写出的部分的字节数
            size = Long.valueOf(sheetPart.getSize() - currByteOffset).intValue();
            while (size > 0) {
                // 比较剩余需要读取的字节数和一次读取的字节数组大小，哪个小就读取对应的字节数
                len = bufferedInputStream.read(buffer, 0, Math.min(size, byteSize));
                bufferedOutputStream.write(buffer, 0, len); // 像输出流写出读入的字节数据
                size -= len; // 剩余需要读取的字节数减掉实际已经读取的字节数
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加图片资源到OOXML规范的excel
     * @param opcPackage OOXML规范的excel的内容容器，可通过流的方式修改excel内容
     * @param waterMarkByte 图片资源的字节数组，图片的类型必须是png的
     * @return PackagePartName 图片资源在excel中名称，即/xl/media/image#.png文件的名称
     * @date 2020/7/1 13:43
     */
    public static PackagePartName addImagePartToXlsx(OPCPackage opcPackage, byte[] waterMarkByte) {
        // 如果OPCPackage为空则直接返回null
        if (Objects.isNull(opcPackage)) {
            return null;
        }

        // 如果图片资源的字节数组为空则直接返回null
        if (ArrayUtils.isEmpty(waterMarkByte)) {
            return null;
        }

        // /xl/media/image#.png文件的名称
        PackagePartName imagePartName;
        try {
            // 已经存在的/xl/media/image#.png文件的个数
            int imagePartSize = opcPackage.getPartsByContentType(XSSFRelation.IMAGE_PNG.getContentType()).size();
            // 将要创建的/xl/media/image#.png文件的名称，#为已存在文件个数加1，防止重名
            String imagePartNameStr = XSSFRelation.IMAGE_PNG.getFileName( imagePartSize + 1);
            imagePartName = PackagingURIHelper.createPartName(imagePartNameStr); // /xl/media/image#.png文件的名称
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            return null; // 生成/xl/media/image#.png文件的名称异常则直接返回null
        }

        // 创建/xl/media/image#.png文件
        PackagePart imagePart = opcPackage.createPart(imagePartName, XSSFRelation.IMAGE_PNG.getContentType());
        // 为/xl/media/image#.png文件写入图片数据
        try (OutputStream out = imagePart.getOutputStream()) {
            out.write(waterMarkByte);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 为/xl/media/image#.png文件写入图片数据异常则直接返回null
        }

        return imagePartName;
    }

    /**
     * 添加绘画资源到OOXML规范的excel，新增绘画资源和图片资源的关系并在绘画资源中设置图片资源
     * @param opcPackage OOXML规范的excel的内容容器，可通过流的方式修改excel内容
     * @param imagePartName 图片资源的名称，即/xl/media/image#.png文件的名称
     * @return PackagePartName 绘画资源在excel中的名称，/xl/drawings/drawing#.xml文件
     * @date 2020/7/1 13:43
     */
    public static PackagePartName addDrawingPartToXlsxByImage(OPCPackage opcPackage, PackagePartName imagePartName) {
        // 如果OPCPackage为空则直接返回null
        if (Objects.isNull(opcPackage)) {
            return null;
        }

        // 如果图片资源的名称为空则直接返回null
        if (Objects.isNull(imagePartName)) {
            return null;
        }

        PackagePartName drawingPartName; // /xl/drawings/drawing#.xml文件的名称

        try {
            // 已经存在的/xl/drawings/drawing#.xml文件的个数
            int drawingPartSize = opcPackage.getPartsByContentType(XSSFRelation.DRAWINGS.getContentType()).size();
            // 将要创建的/xl/drawings/drawing#.xml文件的名称，#为已存在文件个数加1，防止重名
            String drawingPartNameStr = XSSFRelation.DRAWINGS.getFileName(drawingPartSize + 1);
            // /xl/drawings/drawing#.xml文件的名称
            drawingPartName = PackagingURIHelper.createPartName(drawingPartNameStr);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            return null; // 生成/xl/drawings/drawing#.xml文件的名称异常则直接返回null
        }

        // 创建/xl/drawings/drawing#.xml文件
        PackagePart drawingPart = opcPackage.createPart(drawingPartName, XSSFRelation.DRAWINGS.getContentType());
        // 使用/xl/media/image#.png文件的名称为/xl/drawings/drawing#.xml文件添加一个image/png类型的关联关系
        PackageRelationship drawingRelationship = drawingPart.addRelationship(imagePartName, TargetMode.INTERNAL,
                XSSFRelation.IMAGE_PNG.getRelation());

        // 为/xl/drawings/drawing#.xml文件写入数据
        try (OutputStream out = drawingPart.getOutputStream()) {
            out.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
                    "<xdr:wsDr xmlns:xdr=\"http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing\" " +
                    "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" " +
                    "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"> " +
                    getImageInfoInDrawing(drawingRelationship.getId()) +
                    "</xdr:wsDr>").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null; // /xl/drawings/drawing#.xml文件写入数据异常则直接返回null
        }

        return drawingPartName;

    }

    /**
     * 为OOXML规范的excel的sheet添加文本水印图片
     * @param sheetPart OOXML规范的excel的sheet
     * @param existImageHandler sheet已经存在图片时添加文本水印图片的回调函数，参数为sheet#.xml中的drawing标签的属性id的值
     * @param notExistImageHandler sheet不存在图片时添加文本水印图片的回调函数，参数为sheet#.xml中可以添加drawing标签的位置的字节偏移量
     * @date 2020/7/1 13:40
     */
    public static void addWaterMarkToXlsxSheet(
            PackagePart sheetPart, Consumer<String> existImageHandler,
                                               Consumer<Integer> notExistImageHandler) {
        if (Objects.isNull(sheetPart)) {
            return;
        }
        byte space = 0x20;
        byte leftAngleBrackets = 0x3C;
        byte rightAngleBrackets = 0x3E;
        try(BufferedInputStream inputStream = new BufferedInputStream(sheetPart.getInputStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int tagByteSize = ArrayUtil.getMaxLengthInArray(OOXML_TAG_WORKSHEET_DRAWING_FOLLOWING);
            byte[] tagByte = new byte[tagByteSize];
            byte[] readByte = new byte[1]; // 每次就读取一个字节
            boolean readTag = false; // 是否读取标签名
            int currByteOffset = 0; // 字节偏移量
            int tagReadByteSize = 0; // 在读标签名的字节个数
            int flag = -1; // 标志：0-找到了drawing标签，1...n-没找到drawing标签但找到了drawing标签下面的标签，-1-drawing标签及以下的标签都没有找到
            while (inputStream.read(readByte, 0, 1) != -1) {
                currByteOffset++; // 累计字节偏移量
                if (readByte[0] == leftAngleBrackets) {
                    readTag = true; // 开始读取标签名
                    tagReadByteSize = 0; // 在读标签名的字节个数重置为0
                    continue; // 跳过当前的尖括号
                }
                if (!readTag) {
                    continue;
                }
                if (readByte[0] == rightAngleBrackets || readByte[0] == space) {
                    readTag = false; // 结束读取标签名
                    flag = ArrayUtil.indexOf(OOXML_TAG_WORKSHEET_DRAWING_FOLLOWING,tagByte, tagReadByteSize);
                    if (flag >= 0) {
                        break;
                    }
                }else if (tagReadByteSize >= tagByteSize) {
                    readTag = false;
                }else {
                    tagByte[tagReadByteSize++] = readByte[0];
                }
            }
            if (flag == 0) {
                if (existImageHandler == null) {
                    return;
                }
                while (inputStream.read(readByte, 0, 1) != -1) {
                    byteArrayOutputStream.write(readByte[0]);
                    if (readByte[0] == rightAngleBrackets) {
                        break;
                    }
                }
                Matcher matcher = OOXML_TAG_WORKSHEET_DRAWING_ID_PATTERN.matcher(byteArrayOutputStream.toString());
                if (!matcher.find()) {
                    return;
                }
                existImageHandler.accept(matcher.group(1));
                return;
            }
            if (flag > 0) {
                currByteOffset = currByteOffset - tagReadByteSize - 2;
            }else {
                currByteOffset -= 12;
            }
            if (notExistImageHandler == null) {
                return;
            }
            notExistImageHandler.accept(currByteOffset);
        } catch (IOException e) {
           log.info(e.getMessage());
        }
    }

    /**
     * 获取文本水印图片image#.png在drawing#.xml中的描述内容
     * @param imageWithDrawingRelationshipId 文本水印图片image#.png和drawing#.xml的关系id
     * @return java.lang.String 文本水印图片image#.png在drawing#.xml中的描述内容
     * @date 2020/7/5 14:27
     */
    public static String getImageInfoInDrawing(String imageWithDrawingRelationshipId) {
        return
                "<xdr:twoCellAnchor editAs=\"twoCell\"> " +
                        "<xdr:from> <xdr:col>1</xdr:col> <xdr:colOff>0</xdr:colOff> <xdr:row>1</xdr:row> " +
                        "<xdr:rowOff>0</xdr:rowOff> </xdr:from> " +
                        "<xdr:to> <xdr:col>13</xdr:col> <xdr:colOff>341630</xdr:colOff> <xdr:row>12</xdr:row> " +
                        "<xdr:rowOff>29845</xdr:rowOff> </xdr:to> " +
                        "<xdr:pic> <xdr:nvPicPr> <xdr:cNvPr id=\"1\" name=\"Picture 1\" descr=\"Picture\"/> " +
                        "<xdr:cNvPicPr> <a:picLocks noChangeAspect=\"true\"/> </xdr:cNvPicPr> </xdr:nvPicPr> " +
                        "<xdr:blipFill> <a:blip xmlns:r=\"http://schemas.openxmlformats.org/" +
                        "officeDocument/2006/relationships\" r:embed=\""
                        + imageWithDrawingRelationshipId + "\"/> <a:stretch> " +
                        "<a:fillRect/> </a:stretch> </xdr:blipFill> " +
                        "<xdr:spPr> <a:xfrm> <a:off x=\"685800\" y=\"182245\"/> " +
                        "<a:ext cx=\"8571230\" cy=\"1905000\"/> </a:xfrm> " +
                        "<a:prstGeom prst=\"rect\"> <a:avLst/> </a:prstGeom> </xdr:spPr> " +
                        "</xdr:pic> <xdr:clientData/> </xdr:twoCellAnchor>";
    }


}