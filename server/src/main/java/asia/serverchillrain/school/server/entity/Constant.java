package asia.serverchillrain.school.server.entity;

import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.utils.CodingUtil;

import java.util.Random;

/**
 * @auther 2024 01 26
 * 常量类
 */

public class Constant {
    public static final String EMAILCODE = "email-";
    public static final String EMAIL_TITLE = "email_title";
    public static final String EMAIL_MESSAGE = "email_content";
    public static final String EMAIL_TIME = "email_time";
    public static final String SYSTEM_EMAIL = "system_email";
    /**
     * 最高管理员
     */
    public static final User adminUser = new User(new Random().nextInt( 999999), "admin", CodingUtil.encodeMD5("123456"), 5);
    public static final String LOG_USER = "LOG_USER-";
    public static final String APIS = "APIS";
    public static final String EMAILS = "EMAILS";

    public static String EMAIL_EXPIRE_TIME = "email_expire_time";
}
