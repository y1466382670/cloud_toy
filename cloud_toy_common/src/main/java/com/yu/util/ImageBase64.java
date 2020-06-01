package com.yu.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * 图片转base64
 */
public class ImageBase64 {

    /**
     * 网络图片转换Base64的方法
     *
     * @param netImagePath     
     */
    public static String NetImageToBase64(String netImagePath) throws Exception {
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(netImagePath);
            final byte[] by = new byte[1024];
            // 创建链接
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            try {
                InputStream is = conn.getInputStream();
                // 将内容读取内存中
                int len = -1;
                while ((len = is.read(by)) != -1) {
                    data.write(by, 0, len);
                }
                // 对字节数组Base64编码
//                BASE64Encoder encoder = new BASE64Encoder();
//                String strNetImageToBase64 = encoder.encode(data.toByteArray());
                Base64.Encoder encoder = Base64.getEncoder();
                String strNetImageToBase64 = encoder.encodeToString(data.toByteArray());

                // 关闭流
                is.close();
                return strNetImageToBase64;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 图片转化成base64字符串
     *
     * @param imgPath
     * @return
     */
    public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL

            final byte[] by = new byte[1024];

            InputStream is = new FileInputStream(imgPath);
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 对字节数组Base64编码
//            BASE64Encoder encoder = new BASE64Encoder();
//            String strNetImageToBase64 = encoder.encode(data.toByteArray());
            Base64.Encoder encoder = Base64.getEncoder();
            String strNetImageToBase64 = encoder.encodeToString(data.toByteArray());
            // 关闭流
            is.close();
            return strNetImageToBase64;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




//    inal Base64.Decoder decoder = Base64.getDecoder();
//    final Base64.Encoder encoder = Base64.getEncoder();
//    final String text = "字串文字";
//    final byte[] textByte = text.getBytes("UTF-8");
//    //编码
//    final String encodedText = encoder.encodeToString(textByte);
//System.out.println(encodedText);
////解码
//System.out.println(new String(decoder.decode(encodedText), "UTF-8"));



    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
     */

    public static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        // 返回Base64编码过的字节数组字符串
//        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
//        return encoder.encode(Objects.requireNonNull(data));
        Base64.Encoder encoder = Base64.getEncoder();
        String strNetImageToBase64 = encoder.encodeToString(data);
        return strNetImageToBase64;
    }


}
