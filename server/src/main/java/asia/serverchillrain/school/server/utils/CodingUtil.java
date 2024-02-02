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
    public static final String ISOtoUTF8(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, "UTF-8");
    }
}
