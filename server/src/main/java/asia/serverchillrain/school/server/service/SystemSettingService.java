package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * 系统配置服务
 */
public interface SystemSettingService {
    String readSettingsFormRedis2Memory() throws UnsupportedEncodingException, IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    String updateSetting();

    String readSettings2Redis() throws UnsupportedEncodingException;
}
