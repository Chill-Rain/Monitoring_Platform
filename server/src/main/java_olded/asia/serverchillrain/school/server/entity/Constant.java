package asia.serverchillrain.school.server.entity;

import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.utils.CodingUtil;

/**
 * @auther 2024 01 26
 * 常量类
 */

public class Constant {
    /**
     * 最高管理员
     */
    public static User adminUser = new User(CodingUtil.encodeMD5("admin"), CodingUtil.encodeMD5("123456"), 5);
}
