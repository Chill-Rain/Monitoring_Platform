package asia.serverchillrain.school.server.entity.enums;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import lombok.Getter;

/**
 * Email设置绑定
 */
@Getter
public enum EmailSettingClass {
    EMAIL_CONTENT("email_content", "asia.serverchillrain.school.server.settings.email.EmailContent"),
    EMAIL_SYSTEM_USER("system_email", "asia.serverchillrain.school.server.settings.email.EmailSystemUser"),
    EMAIL_TIME("email_time", "asia.serverchillrain.school.server.settings.email.EmailTime"),
    EMAIL_TITLE("email_title", "asia.serverchillrain.school.server.settings.email.EmailTitle")
    ;
    private final String classPath;
    private final String name;
    public static EmailSettingClass getByName(String name) throws MonitoringPlatformException {
        for(EmailSettingClass item : EmailSettingClass.values()){
            if(item.getName().equals(name)){
                return item;
            }
        }
        throw new MonitoringPlatformException("错误的设置名--->" + name, ResponseCodeEnum.CODE_700);
    }

    EmailSettingClass(String name, String path) {
        this.classPath = path;
        this.name = name;
    }

}
