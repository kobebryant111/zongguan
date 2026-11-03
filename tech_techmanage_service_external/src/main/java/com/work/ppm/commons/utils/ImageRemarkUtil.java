package com.work.ppm.commons.utils;

import lombok.extern.slf4j.Slf4j;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图片添加水印
 * @date 2021-18-16 09:17
 */
@Slf4j
public class ImageRemarkUtil {

    /**
     * 构造函数
     */
    private ImageRemarkUtil(){
       throw new IllegalStateException("ImageRemarkUtil class");
    }

    /**
     * 水印透明度
     */
    private static  float alpha = 0.15F;

    /**
     * 水印文字颜色
     */
    private static Color color = Color.BLACK;


    /**
     * 往图片中添加文字水印
     * @param logoText logoText
     * @param srcFile srcFile
     * @param targetPath targetPath
     * @param degree degree
     */
    public static void markImageByTextAb(String logoText,String srcFile,String targetPath,Integer degree){
        OutputStream os  = null;
        try{
            //源图片
            File file = new File(srcFile);
            Image srcImg = ImageIO.read(file);
            BufferedImage bufferedImage = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null),BufferedImage.TYPE_INT_RGB);
            //水印字体
            Font font = new Font("宋体",Font.BOLD,bufferedImage.getWidth() /20);
            //获取画笔对象
            Graphics2D g = bufferedImage.createGraphics();
            //
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),srcImg.getHeight(null)
                    ,Image.SCALE_SMOOTH),0,0,null);
            //设置水印旋转
            if(null != degree){
                g.rotate(Math.toRadians(degree),
                        (double)bufferedImage.getWidth() /2,
                        (double)bufferedImage.getHeight() /2
                );
            }
            //设置水印颜色,字体,透明度
            g.setColor(color);
            g.setFont(font);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            //指定水印在图片上的位置
            FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
            g.drawString(logoText,
                    3*(bufferedImage.getWidth()-metrics.stringWidth(logoText))/5,
                    (1*(bufferedImage.getHeight()-metrics.getHeight()))/5+metrics.getAscent());
            //释放资源
            g.dispose();
            File desfile = new File(targetPath);
            os = new FileOutputStream(desfile);
            ImageIO.write(bufferedImage,"JPG",os);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}