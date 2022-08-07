package org.classview.utils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author gjt
 * @version 1.0
 * @message File tools
 */
public class FileUtils {

    /**
     * 文件读取方法，返回的是字节码的16进制形式
     * @param filepath 读取路径
     */
    public static List<String> readClassFile(String filepath) throws IOException {

        FileInputStream fin = new FileInputStream(new File(filepath));
        StringWriter sw = new StringWriter();
        int len = 1;
        byte[] temp = new byte[len];
        /*16进制转化模块*/
        for (; (fin.read(temp, 0, len)) != -1;) {
            if (temp[0] > 0xf && temp[0] <= 0xff) {
                sw.write(Integer.toHexString(temp[0]));
            } else if (temp[0] >= 0x0 && temp[0] <= 0xf) {
                //对于只有1位的16进制数前边补“0”
                sw.write("0" + Integer.toHexString(temp[0]));
            } else {
                //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
                sw.write(Integer.toHexString(temp[0]).substring(6));
            }
        }
        // 分节处理，32个字符一节
        char[] bytesCharArrys = sw.toString().toUpperCase().toCharArray();
        // 一行的字符
        StringBuffer byteReaderOneLine = new StringBuffer();
        List<String> bytes = new LinkedList<String>();
        for (int i = 0; i < sw.toString().length(); i++) {
            if(i %32 == 0 && i != 0) {
                bytes.add(byteReaderOneLine.toString());
                // 删除当前缓冲
                byteReaderOneLine.delete(0, byteReaderOneLine.length());
                byteReaderOneLine.append(bytesCharArrys[i]);
            } else {
                byteReaderOneLine.append(bytesCharArrys[i]);
            }
        }
        return bytes;
    }

    /**
     * 读取指定字节长度的字符串
     * @param bytes  字符串
     * @param startIndex  开始位置
     * @param endIndex  结束位置 (默认*2 一个字节8位，这里是16进制，4*8 = 32 位，32/16 = 2)
     */
    public static String readBytesByIndex(String bytes, int startIndex,int endIndex) {
        Integer start = (startIndex-1) * 2;
        Integer end = endIndex * 2;
        String byteString = bytes.substring(start, end);
        return byteString;
    }

    /**
     * json格式输出文件（常量池）
     * @param jsonString
     * @param file
     */
    public static void writeConstantInfoByJson(String jsonString,File file) {
        // 将格式化后的字符串写入文件
        Writer write = null;
        try {
            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
