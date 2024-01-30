package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.config.ServerConfig;
import asia.serverchillrain.school.server.entity.bean.SystemSettingLine;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.SystemSettingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @auther 2024 01 28
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    @Resource
    private ServerConfig serverConfig;
    @Override
    public boolean refreshCache() {
        return false;
    }

    @Override
    public String writeSettingDB(SystemSettingLine settingJson) {
        if(!serverConfig.getStatus().equals("dev")){
            new MonitoringPlatformException("权限不足！", ResponseCodeEnum.CODE_200);
        }
        return "Success!";
    }

    @Override
    public String updateSetting() {
        return null;
    }
}
