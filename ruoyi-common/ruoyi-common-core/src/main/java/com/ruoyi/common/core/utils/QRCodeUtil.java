package com.ruoyi.common.core.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * 二维码生成工具类
 * Created by chenzan on 2022/09/19
 */
public class QRCodeUtil {
    private static final int QRCODE_SIZE = 430; // 二维码尺寸，宽度和高度均是320
    private static final String FORMAT_TYPE = "PNG"; // 二维码图片类型









    /**
     * 获取二维码图片
     *
     * @param dataStr    二维码内容
     * @param needLogo   是否需要添加logo
     * @param bottomText 底部文字       为空则不显示
     * @return
     */
    @SneakyThrows
    public static BufferedImage getQRCodeImage(String dataStr, boolean needLogo, String bottomText,InputStream logoInputStream) {
        if (dataStr == null) {
            throw new RuntimeException("未包含任何信息");
        }
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    //定义内容字符集的编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);        //定义纠错等级
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(dataStr, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int tempHeight = height;
        if (StringUtils.hasText(bottomText)) {
            tempHeight = tempHeight + 12;
        }
        BufferedImage image = new BufferedImage(width, tempHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 判断是否添加logo
        if (needLogo) {
            insertLogoImage(image,logoInputStream);
        }
        // 判断是否添加底部文字
        if (StringUtils.hasText(bottomText)) {
            addFontImage(image, bottomText);
        }
        return image;
    }


    /**
     * url 转 InputStream
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public static InputStream url2InputStream(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(30 * 1000);  // 连接超时:30s
        conn.setReadTimeout(1 * 1000 * 1000); // IO超时:1min
        conn.connect();

        // 创建输入流读取文件
        InputStream in = conn.getInputStream();
        return in;
    }


    /**
     * 插入logo图片
     *
     * @param source 二维码图片
     * @throws Exception
     */
    private static void insertLogoImage(BufferedImage source,InputStream logoInputStream) throws Exception {
        // 默认logo放于resource/static/image目录下

        if (logoInputStream == null || logoInputStream.available() == 0) {
            return;
        }
        Image src = ImageIO.read(logoInputStream);
        int width = 100;
        int height = 100;

        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        src = image;

        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    private static void addFontImage(BufferedImage source, String declareText) {
        //生成image
        int defineWidth = QRCODE_SIZE;
        int defineHeight = 20;
        BufferedImage textImage = new BufferedImage(defineWidth, defineHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,   RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, defineWidth, defineHeight);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        //部署linux需要注意 linux无此字体会显示方块
        Font font = new Font("宋体", Font.BOLD, 15);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(declareText, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (defineWidth - fontMetrics.stringWidth(declareText)) / 2;
        float y = (defineHeight + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;
        g2.drawString(declareText, (int) offset, (int) y);

        Graphics2D graph = source.createGraphics();
        //开启文字抗锯齿
        graph.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,   RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //添加image
        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src = textImage;
        graph.drawImage(src, 0, QRCODE_SIZE - 8, width, height, Color.WHITE, null);
        graph.dispose();
    }

}

