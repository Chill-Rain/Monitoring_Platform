package asia.serverchillrain.school.server.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @auther 2024 01 29
 * 编码类
 */

public class CodingUtil {
    /**
     * md5加密
     * @param str 加密内容
     * @return 加密结果
     */
    public static final String encodeMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * ISO-8859-1转UTF-8
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final String ISOtoUTF8(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, "UTF-8");
    }

    /**
     * UTF-8转GBK
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final String UTF8toGBK(String str) throws UnsupportedEncodingException {
//        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = str.getBytes();
        return new String(bytes, "GBK");
    }

    /**
     * ISO-8859-1转GBK
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final String ISOtoGBK(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, "GBK");
    }

    public static String GKBtoUTF8(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("GBK");
        return new String(bytes, "UTF-8");
    }
}
