package asia.serverchillrain.school.server.utils;

import java.util.Random;

/**
 * @auther 2024 02 02
 * 随机数生成工具
 */

public class RandomUtil {
    public static String getEmailCode(Integer len){
        int range = (int) (Math.pow(10, len - 1) * 9);
        return String.valueOf(new Random().nextInt(range));
    }
}
