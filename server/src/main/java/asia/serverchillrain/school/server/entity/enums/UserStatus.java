package asia.serverchillrain.school.server.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ADMIN(5),
    NOMORL(3),
    BAN(-1);
    public int code;

    UserStatus(int code) {
        this.code = code;
    }

}
