package asia.serverchillrain.school.server.entity.enums;
/**
 * 用户权限等级
 */

import lombok.Getter;

@Getter
public enum UserStatus {
    ADMIN(5),
    NORMAL(3),
    BAN(-1);
    public final int code;

    UserStatus(int code) {
        this.code = code;
    }

}
