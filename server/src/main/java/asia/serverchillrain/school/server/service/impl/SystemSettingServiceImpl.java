package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.enums.Api;
import asia.serverchillrain.school.server.entity.enums.EmailSettingClass;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.SystemSettingService;
import asia.serverchillrain.school.server.settings.RedisConfigLine;
import asia.serverchillrain.school.server.settings.api.root.ApiSetting;
import asia.serverchillrain.school.server.settings.email.root.EmailSetting;
import asia.serverchillrain.school.server.utils.JsonUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static asia.serverchillrain.school.server.utils.SystemSettingUtil.*;

/**
 * @auther 2024 01 28
 * 系统设置服务
 * 如果要能单独写，则需要读txt
 */
@Service
@PropertySource(value = "classpath:setting.properties", encoding = "UTF-8")
public class SystemSettingServiceImpl implements SystemSettingService {

    @Value("${application.api.camera.site}")
    private String cameraSite;
    @Value("${application.api.camera.open}")
    private String open;
    @Value("${application.api.rtmp.site}")
    private String rtmp;
    @Value("${application.api.model.site}")
    private String model;
    @Value("${application.api.smock.site}")
    private String smock;
    @Value("${application.api.sleep.site}")
    private String sleep;
    @Value("${application.api.fire.site}")
    private String fire;
    @Value("${application.api.phone.site}")
    private String phone;
    @Value("${application.system.register.message}")
    private String message;
    @Value("${application.system.register.time}")
    private String time;
    @Value("${application.system.email.email}")
    private String systemEmail;
    @Value("${application.system.email.title}")
    private String title;
    @Value("${application.api.camera.close}")
    private String close;
    @Resource
    private MemoryManager redis;
    @Override
    public String readSettingsFormRedis2Memory() throws UnsupportedEncodingException, IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        readEmailConfig2Memory();
        readApis2Memory();
        return "Success!";
    }

    private void readEmailConfig2Memory() throws MonitoringPlatformException, IntrospectionException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        String emailJson = redis.get(KEY_EMAILS);
        EmailSetting emailsetting = new EmailSetting();
        Map<String, String> emailSettingMap = JSON.parseObject(emailJson, new TypeReference<>() {
        });
        for (Map.Entry<String, String> next : emailSettingMap.entrySet()) {
            EmailSettingClass setting = EmailSettingClass.getByName(next.getKey());
            PropertyDescriptor pd = new PropertyDescriptor(setting.getName(), EmailSetting.class);
            Method writeMethod = pd.getWriteMethod();
            Class<?> clazz = Class.forName(setting.getClassPath());
            RedisConfigLine redisConfigLine = (RedisConfigLine) clazz.newInstance();
            redisConfigLine.setLine(next.getValue());
            writeMethod.invoke(emailsetting, redisConfigLine);
        }
        putSetting(KEY_EMAILS, emailsetting);
    }

    private void readApis2Memory() throws IntrospectionException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, MonitoringPlatformException, UnsupportedEncodingException {
        String apiJson = redis.get(KEY_APIS);
        ApiSetting apiSetting = new ApiSetting();
        Map<String, String> emailSettingMap = JSON.parseObject(apiJson, new TypeReference<>() {
        });
        for (Map.Entry<String, String> next : emailSettingMap.entrySet()) {
            Api setting = Api.getByName(next.getKey());
            PropertyDescriptor pd = new PropertyDescriptor(setting.getName(), ApiSetting.class);
            Method writeMethod = pd.getWriteMethod();
            Class<?> clazz = Class.forName(setting.getClassPath());
            RedisConfigLine redisConfigLine = (RedisConfigLine) clazz.newInstance();
            redisConfigLine.setLine(next.getValue());
            writeMethod.invoke(apiSetting, redisConfigLine);
        }
        putSetting(KEY_APIS, apiSetting);
    }

    @Override
    public String updateSetting() {
        return null;
    }

    @Override
    public String readSettings2Redis() throws UnsupportedEncodingException {
        readRedis();
        readMysqlSetting();
        return null;
    }

    private void readRedis() throws UnsupportedEncodingException {
        readApis();
        readEmailConfig();
    }

    private void readEmailConfig() throws UnsupportedEncodingException {
        Map<String, String> emailConfig;
        String emailJsons = redis.get(KEY_EMAILS);
        if(emailJsons != null){
            emailConfig = JSON.parseObject(emailJsons, new TypeReference<>() {
            });
        }else{
            //为空 写入emailConfig
            emailConfig = Map.of(
                    Constant.EMAIL_TITLE, title,
                    Constant.EMAIL_MESSAGE, message,
                    Constant.EMAIL_TIME, time,
                    Constant.SYSTEM_EMAIL, systemEmail
            );
            redis.put(KEY_EMAILS, JsonUtil.object2Json(emailConfig));
        }
    }

    private void readApis() throws UnsupportedEncodingException {
        Map<String, String> apis = null;
        String apiJsons = redis.get(KEY_APIS);
        if(apiJsons != null){
            apis = JSON.parseObject(apiJsons, new TypeReference<>() {
            });
        }else{
            //为空 写入apis
            apis = Map.of(
                    "cameraSite", cameraSite,
                    "cameraClose", cameraSite + close,
                    "cameraOpen", cameraSite + open + rtmp,
                    "smock", model + smock,
                    "sleep", model + sleep,
                    "fire", model + fire,
                    "phone", model + phone
            );
            redis.put(KEY_APIS, JsonUtil.object2Json(apis));
        }
    }
    private void readMysqlSetting(){

    }
}
