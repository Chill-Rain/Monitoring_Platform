package asia.serverchillrain.school.server.entity;

import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.utils.CodingUtil;

import java.util.Random;

/**
 * @auther 2024 01 26
 * 常量类
 */

public class Constant {
    /**
     * 最高管理员
     */
    public static User adminUser = new User(new Random().nextInt( 999999), "admin", CodingUtil.encodeMD5("123456"), 5);
    public static String LOG_USER = "LOG_USER-";
}
