package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.bean.SystemSettingLine;

/**
 * 系统配置服务
 */
public interface SystemSettingService {
    boolean refreshCache();

    String writeSettingDB(SystemSettingLine settingJson);
    String updateSetting();
}
