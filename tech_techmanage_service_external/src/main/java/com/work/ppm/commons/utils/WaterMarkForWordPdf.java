package com.work.ppm.commons.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.microsoft.schemas.office.office.CTLock;
import com.microsoft.schemas.office.office.STConnectType;
import com.microsoft.schemas.vml.CTFormulas;
import com.microsoft.schemas.vml.CTGroup;
import com.microsoft.schemas.vml.CTH;
import com.microsoft.schemas.vml.CTHandles;
import com.microsoft.schemas.vml.CTPath;
import com.microsoft.schemas.vml.CTShape;
import com.microsoft.schemas.vml.CTShapetype;
import com.microsoft.schemas.vml.CTTextPath;
import com.microsoft.schemas.vml.STExt;
import com.microsoft.schemas.vml.STTrueFalse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import javax.xml.namespace.QName;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtrRef;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;

/**
 * aadads
 * @date 2021-18-16 09:17
 */
public class WaterMarkForWordPdf {

    /**
     * 构造函数
     */
    private WaterMarkForWordPdf() {
        throw new IllegalStateException("WaterMarkForWordPdf class");
    }

    /**
     * 往Docx中添加文字水印
     * @param srcFile srcFile
     * @param outPdfFile srcFile
     * @param textWatermark textWatermark
     * @return a
     */
    public static String addWordWaterMark(String srcFile, String outPdfFile , String textWatermark) {
        ZipSecureFile.setMinInflateRatio(-1.0D);
        try (FileInputStream fileInputStream = new FileInputStream(srcFile);
             XWPFDocument doc = new XWPFDocument(fileInputStream);
             FileOutputStream out = new FileOutputStream(outPdfFile)){
            // 添加默认的（奇数页的）页眉的水印
            addTextWaterMarkToHeader(doc, XWPFHeaderFooterPolicy.DEFAULT, textWatermark);
            // 添加偶数页的页眉的水印
            addTextWaterMarkToHeader(doc, XWPFHeaderFooterPolicy.EVEN, textWatermark);
            // 添加首页的页眉的水印
            addTextWaterMarkToHeader(doc, XWPFHeaderFooterPolicy.FIRST, textWatermark);

            doc.write(out);
        } catch (Exception e){
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    /**
     * 添加文本水印到页眉
     * @param doc 文档
     * @param type 页眉类型
     * @param text 文本
     * @date 2020/5/15 8:45
     */
    public static void addTextWaterMarkToHeader(XWPFDocument doc, STHdrFtr.Enum type, String text) {
        // 获取页眉页脚规则，如果没有则创建
        XWPFHeaderFooterPolicy headerFooterPolicy = Optional.ofNullable(doc.getHeaderFooterPolicy())
                .orElse(doc.createHeaderFooterPolicy());

        // 获取指定类型的页眉，如果没有则创建
        XWPFHeader header = Optional.ofNullable(headerFooterPolicy.getHeader(type)).orElseGet(() -> {
            // 创建页眉的段落
            CTP p = CTP.Factory.newInstance();
            byte[] rsidr = doc.getDocument().getBody().getPArray(0).getRsidR();
            byte[] rsidrdefault = doc.getDocument().getBody().getPArray(0).getRsidRDefault();
            p.setRsidP(rsidr);
            p.setRsidRDefault(rsidrdefault);
            CTPPr pPr = p.addNewPPr();
            pPr.addNewPStyle().setVal("Header");

            // 根据页眉的段落创建指定类型的页眉
            XWPFParagraph[] pars = new XWPFParagraph[1];
            pars[0] = new XWPFParagraph(p, doc);
            return headerFooterPolicy.createHeader(type, pars);
        });

        /*
         * 基本上word的document的body标签下都有一个sectPr标签，用来描述当前章节的属性，
         * 但如果对word进行了“插入-分页-下一页分节符”操作（wps）则会在指定位置的段落标签b中添加一个sectPr标签，
         * 由于这个标签导致这个标签以上的页的水印都无法正常显示，测试一下wps加水印的功能，当出现这种情况时，他们是在
         * 第一个sectPr标签中加入了水印的页眉映射就可以为所有页添加水印，所以就加一下呗
         */
        setHeaderReferenceToFirstSectPrOfPArray(doc, type);

        // 添加文本水印到段落
        Optional.ofNullable(header.getParagraphArray(0))
                .ifPresent(xwpfParagraph -> {
                    removeTextWaterMarkFromParagraph(xwpfParagraph); // 删水印
                    addTextWaterMarkToParagraph(xwpfParagraph, text); // 加水印
                });
    }

    /**
     * 为word的所有段落中的第一个章节属性添加页眉信息
     * @param doc 文档
     * @param type 页眉类型
     * @date 2020/6/24 17:02
     */
    public static void setHeaderReferenceToFirstSectPrOfPArray(XWPFDocument doc, STHdrFtr.Enum type) {
        Optional.ofNullable(doc.getHeaderFooterPolicy()) // 获取页眉页脚规则
                .map(headerFooterPolicy -> headerFooterPolicy.getHeader(type)) // 获取指定类型的页眉
                .ifPresent(header -> {
                    // 如果页眉存在则为word的所有段落中的第一个章节属性添加页眉信息
                    CTBody body = doc.getDocument().getBody(); // 获取document.xml中的body内容
                    // 循环body中的所有段落p，找到第一个章节添加页眉信息，然后就可以结束循环了
                    for (int i = 0; i < body.sizeOfPArray(); i++) {
                        // 获取章节属性
                        Optional<CTSectPr> ctSectPr = Optional.ofNullable(body.getPArray(i)) // 获取指定段落
                                .map(CTP::getPPr) // 获取段落中的段落属性
                                .map(CTPPr::getSectPr); // 获取段落属性中的章节属性
                        // 如果当前段落没有章节属性则直接下一循环
                        if (!ctSectPr.isPresent()) {
                            continue;
                        }

                        String relationId = doc.getRelationId(header); // 获取页眉在document中的关系id

                        // 获取章节属性中指定页眉类型的页眉映射
                        Optional<CTHdrFtrRef> headerReference =
                                ctSectPr.get()
                                        .getHeaderReferenceList() // 获取章节属性中所有页眉映射
                                        .stream()
                                        .filter(ctHdrFtrRef -> ctHdrFtrRef.getType().equals(type)) // 筛选出指定页眉类型的页眉映射
                                        .findFirst();

                        // 如果指定页眉类型的页眉映射存在则修改一下页眉在document中的关系id否则就新增一个页眉映射
                        if(headerReference.isPresent()) {
                            headerReference.get().setId(relationId); // 修改页眉在document中的关系id
                        }
                        else {
                            CTHdrFtrRef ref = ctSectPr.get().addNewHeaderReference(); // 新增一个页眉映射
                            ref.setId(relationId); // 设置页眉在document中的关系id
                            ref.setType(type); // 设置页眉类型
                        }

                        break; // 退出循环
                    }
                });
    }

    /**
     * 删除段落中的水印
     * @param paragraph 段落
     * @date 2020/5/18 14:07
     */
    public static void removeTextWaterMarkFromParagraph(XWPFParagraph paragraph) {
        // 水印图片标签
        QName shape = new QName("urn:schemas-microsoft-com:vml", "shape");
        CTP ctp = paragraph.getCTP(); // 段落内容
        for (int i = ctp.sizeOfRArray() - 1; i >= 0; i--) {
            // 删除内容包含文本水印的行
            if (ctp.getRArray(i).getPictList().stream().map(ctPicture -> ctPicture.selectChildren(shape))
                    .flatMap(Arrays::stream).anyMatch(xmlObject ->
                            ((CTShape) xmlObject).getId().startsWith("PowerPlusWaterMarkObject"))) {
                ctp.removeR(i);
            }
        }
    }

    /**
     * 添加文本水印到段落
     * @param paragraph paragraph
     * @param text text
     */
    public static void addTextWaterMarkToParagraph(XWPFParagraph paragraph, String text) {
        CTP p = paragraph.getCTP();
        CTR r = p.addNewR();
        CTRPr rPr = r.addNewRPr();
        rPr.addNewNoProof();
        CTPicture pict = r.addNewPict();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        shapetype.setId("_x0000_t136");
        shapetype.setCoordsize("1600,21600");
        shapetype.setSpt(136);
        shapetype.setAdj("10800");
        shapetype.setPath2("m@7,0l@8,0m@5,21600l@6,21600e");
        CTFormulas formulas = shapetype.addNewFormulas();
        setCTFormulas(formulas);
        CTPath path = shapetype.addNewPath();
        path.setTextpathok(STTrueFalse.T);
        path.setConnecttype(STConnectType.CUSTOM);
        path.setConnectlocs("@9,0;@10,10800;@11,21600;@12,10800");
        path.setConnectangles("270,180,90,0");
        CTTextPath shapeTypeTextPath = shapetype.addNewTextpath();
        shapeTypeTextPath.setOn(STTrueFalse.T);
        shapeTypeTextPath.setFitshape(STTrueFalse.T);
        CTHandles handles = shapetype.addNewHandles();
        CTH h = handles.addNewH();
        h.setPosition("#0,bottomRight");
        h.setXrange("6629,14971");
        CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.EDIT);
        CTShape shape = group.addNewShape();
        shape.setId("PowerPlusWaterMarkObjectPas");
        shape.setSpid("_x0000_s1025");
        shape.setType("#_x0000_t136");
        // 设置一些样式
        shape.setStyle("position:absolute;margin-left:0;margin-top:0;width:415pt;height:67.5pt;z-index:-251654144;\n" +
                "mso-wrap-edited:f;mso-position-horizontal:center;mso-position-horizontal-relative:" +
                "margin;mso-position-vertical:center;mso-position-vertical-relative:margin;rotation:345");
        shape.setWrapcoords("616 5068 390 16297 39 16921 -39 17155 7265 17545 7186 17467 -39 17467 " +
                "18904 17467 10507 17467 8710 17545 18904 17077 18787 16843 18358 16297 18279 12554 " +
                "19178 12476 20701 11774 20779 11228 21131 10059 21248 8811 21248 7563 20975 6316 " +
                "20935 5380 19490 5146 14022 5068 2616 5068");
        // 设置文本颜色
        shape.setFillcolor("#d8d8d8");
        shape.setStroked(STTrueFalse.FALSE);
        CTTextPath shapeTextPath = shape.addNewTextpath();
        shapeTextPath.setStyle("font-family:&quot;Cambria&quot;;font-size:1pt");
        shapeTextPath.setString(text);
        pict.set(group);
        // end watermark paragraph
    }

    /**
     * 设置属性
     * @param formulas formulas
     * @return formulas
     */
    public static CTFormulas setCTFormulas(CTFormulas formulas){
        formulas.addNewF().setEqn("sum #0 0 10800");
        formulas.addNewF().setEqn("prod #0 2 1");
        formulas.addNewF().setEqn("sum 21600 0 @1");
        formulas.addNewF().setEqn("sum 0 0 @2");
        formulas.addNewF().setEqn("sum 21600 0 @3");
        formulas.addNewF().setEqn("if @0 @3 0");
        formulas.addNewF().setEqn("if @0 21600 @1");
        formulas.addNewF().setEqn("if @0 0 @2");
        formulas.addNewF().setEqn("if @0 @4 21600");
        formulas.addNewF().setEqn("mid @5 @6");
        formulas.addNewF().setEqn("mid @8 @5");
        formulas.addNewF().setEqn("mid @7 @8");
        formulas.addNewF().setEqn("mid @6 @7");
        formulas.addNewF().setEqn("sum @6 0 @5");
        return formulas;
    }

    /**
     * 往Pdf中添加文字水印
     * @param inputStream inputStream
     * @param destFile destFile
     * @param text text
     * @throws IOException 异常
     * @throws DocumentException 异常
     */
    public static void addWaterMark(InputStream inputStream, String destFile,
                                    String text) throws IOException, DocumentException {
        // 待加水印的文件
        PdfReader reader = new PdfReader(inputStream);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destFile));
        Document document = new Document(reader.getPageSize(1));
        float width = document.getPageSize().getWidth();
        float height = document.getPageSize().getHeight();
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;

        // 设置透明度
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.1F);
        // 设置字体
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        // 循环对每页插入水印
        for (int i = 1; i < total; i++)
        {
            // 水印的起始
            content = stamper.getOverContent(i);
            content.setGState(gs);
            content.setFontAndSize(base, width/16);
            // 开始
            content.beginText();
            // 设置颜色 默认为黑色
            content.setColorFill(BaseColor.BLACK);
            // 开始写入水印

            content.showTextAligned(Element.ALIGN_MIDDLE, text, width/8,
                    height/4, 15);

            content.endText();
        }
        stamper.close();
    }
}