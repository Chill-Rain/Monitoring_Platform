package asia.serverchillrain.school.server.entity.enums;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;

public enum ApiSettingClass {
    CAMERA("camera", "asia.serverchillrain.school.server.settings.api.CameraHardWare"),
    PHONE("phone", "asia.serverchillrain.school.server.settings.api.PhoneModel"),
    SLEEP("sleep", "asia.serverchillrain.school.server.settings.api.SleepModel"),
    FIRE("fire", "asia.serverchillrain.school.server.settings.api.FireModel"),
    SMOCK("smock", "asia.serverchillrain.school.server.settings.api.SmockModel")
    ;
    private String name;
    private String classPath;
    public static ApiSettingClass getByName(String name) throws MonitoringPlatformException {
        for(ApiSettingClass api : ApiSettingClass.values()){
            if(api.getName().equals(name)){
                return api;
            }
        }
        throw new MonitoringPlatformException("错误的API名称！", ResponseCodeEnum.CODE_700);
    }
    ApiSettingClass(String name, String classPath) {
        this.name = name;
        this.classPath = classPath;
    }

    public String getName() {
        return name;
    }

    public String getClassPath() {
        return classPath;
    }
}
