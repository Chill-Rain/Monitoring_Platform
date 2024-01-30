package asia.serverchillrain.school.server.utils;

import org.springframework.util.DigestUtils;

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
}
