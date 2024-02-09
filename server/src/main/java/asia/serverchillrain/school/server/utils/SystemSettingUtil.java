package asia.serverchillrain.school.server.utils;

import asia.serverchillrain.school.server.settings.Setting;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 2024 02 02
 * 系统设置工具
 */

public class SystemSettingUtil {
    public static final String KEY_APIS = "APIS";
    public static final String KEY_EMAILS = "EMAILS";
    private static final Map<String, Setting> SYSTEM_CACHE = new ConcurrentHashMap<>();
    public static Setting getSystemSetting(String key){
        return SYSTEM_CACHE.get(key);
    }

    public static void putSetting(String keyEmails, Setting setting) {
        SYSTEM_CACHE.put(keyEmails, setting);
    }


}
