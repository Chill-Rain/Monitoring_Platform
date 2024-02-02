package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.config.ServerConfig;
import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.enums.EmailSettingClass;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.SystemSettingService;
import asia.serverchillrain.school.server.settings.RedisConfigLine;
import asia.serverchillrain.school.server.settings.email.root.EmailSetting;
import asia.serverchillrain.school.server.utils.JsonUtil;
import asia.serverchillrain.school.server.utils.SystemSettingUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static asia.serverchillrain.school.server.utils.CodingUtil.*;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * @auther 2024 01 28
 * 系统设置服务
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    @Resource
    private ServerConfig serverConfig;
    @Value("${application.api.camera.site}")
    private String cameraSite;
    @Value("${application.api.rtmp.site}")
    private String camera;
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
    @Resource
    private MemoryManager redis;
    @Override
    public String readSettingsFormRedis2Memory() throws UnsupportedEncodingException, IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        readApis2Memory();
        readEmailConfig2Memory();
        return "Success!";
    }

    private void readEmailConfig2Memory() throws MonitoringPlatformException, IntrospectionException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String emailJson = redis.get(Constant.EMAILS);
        EmailSetting emailsetting = new EmailSetting();
        Map<String, String> emailSettingMap = JSON.parseObject(emailJson, new TypeReference<Map<String, String>>() {});
        Iterator<Map.Entry<String, String>> iterator = emailSettingMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            EmailSettingClass setting = EmailSettingClass.getByName(next.getKey());
            PropertyDescriptor pd = new PropertyDescriptor(setting.getName(), EmailSetting.class);
            Method writeMethod = pd.getWriteMethod();
            Class clazz = Class.forName(setting.getClassPath());
            RedisConfigLine redisConfigLine = (RedisConfigLine) clazz.newInstance();
            redisConfigLine.setSettingLine(next.getValue());
            writeMethod.invoke(emailsetting, redisConfigLine);
        }
        SystemSettingUtil.putSetting(SystemSettingUtil.KEY_EMAILS, emailsetting);
    }

    private void readApis2Memory() {
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
        Map<String, String> emailConfig = null;
        String emailJsons = redis.get(Constant.EMAILS);
        if(emailJsons != null){
            emailConfig = JSON.parseObject(emailJsons, new TypeReference<Map<String, String>>() {});
        }else{
            //为空 写入emailConfig
            emailConfig = Map.of(
                    Constant.EMAIL_TITLE, ISOtoUTF8(title),
                    Constant.EMAIL_MESSAGE, ISOtoUTF8(message),
                    Constant.EMAIL_TIME, ISOtoUTF8(time),
                    Constant.SYSTEM_EMAIL, ISOtoUTF8(systemEmail)
            );
            redis.put(Constant.EMAILS, JsonUtil.object2Json(emailConfig));
        }
    }

    private void readApis() throws UnsupportedEncodingException {
        Map<String, String> apis = null;
        String apiJsons = redis.get(Constant.APIS);
        if(apiJsons != null){
            apis = JSON.parseObject(apiJsons, new TypeReference<Map<String, String>>() {});
        }else{
            //为空 写入apis
            apis = Map.of(
                    "camera", ISOtoUTF8(cameraSite + camera),
                    "smock", ISOtoUTF8(model + smock),
                    "sleep", ISOtoUTF8(model + sleep),
                    "fire", ISOtoUTF8(model + fire),
                    "phone", ISOtoUTF8(model + phone)
            );
            redis.put(Constant.APIS, JsonUtil.object2Json(apis));
        }
    }
    private void readMysqlSetting(){

    }
}
