package com.khdz.patrol.netpatrolapp.Device;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/9/21.
 */

public class CodeTrans {
    public StringBuffer Msg = new StringBuffer();

    public static String GetGbkChar(byte[] codes) {
        if ((byte) 0xAA == codes[0]) {
            int offset = 0xB0 - (int) '0';
            char enChar = (char) (codes[1] & 0xFF - offset);//在Java中，codes[1]有符号位，codes[1]&0xFF将有符号位改为无符号位
            return new String(new char[]{enChar});
        } else {
            try {
                return new String(codes, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }//获得单个GBK编码字符（4个字节转换成一个中文或英文）

    public static String Byte2Gbk(byte[] codes) {
        if (null == codes || 0 == codes.length || 0 != codes.length % 2) {
            return null;
        }
        int retLength = codes.length / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < retLength; i++) {
            byte b1 = codes[i * 2];
            byte b2 = codes[i * 2 + 1];
            if ((byte) 0xFF == b1 && (byte) 0xFF == b2) {
                break;
            }
            String str = GetGbkChar(new byte[]{b1, b2});
            sb.append(str);
        }
        return sb.toString();
    }//字节数组转为GBK编码

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]).toUpperCase();
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }//字节转为16进制的字符串
}
