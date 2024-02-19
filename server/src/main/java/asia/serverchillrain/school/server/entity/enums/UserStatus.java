package asia.serverchillrain.school.server.entity.enums;


import lombok.Getter;
/**
 * 用户权限等级
 */
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
