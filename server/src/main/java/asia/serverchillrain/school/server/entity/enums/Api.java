package asia.serverchillrain.school.server.entity.enums;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import lombok.Getter;

/**
 * Api设置绑定
 */
@Getter
public enum Api {
    CAMERA_SITE("cameraSite", "asia.serverchillrain.school.server.settings.api.CameraHardWare"),
    PHONE("phone", "asia.serverchillrain.school.server.settings.api.PhoneModel"),
    SLEEP("sleep", "asia.serverchillrain.school.server.settings.api.SleepModel"),
    FIRE("fire", "asia.serverchillrain.school.server.settings.api.FireModel"),
    SMOCK("smock", "asia.serverchillrain.school.server.settings.api.SmockModel"),
    CAMERA_CLOSE("cameraClose", "asia.serverchillrain.school.server.settings.api.CameraCloseSite"),
    CAMERA_OPEN("cameraOpen", "asia.serverchillrain.school.server.settings.api.CameraOpenSite")
    ;
    private final String name;
    private final String classPath;
    public static Api getByName(String name) throws MonitoringPlatformException {
        for(Api api : Api.values()){
            if(api.getName().equals(name)){
                return api;
            }
        }
        throw new MonitoringPlatformException("错误的API名称！", ResponseCodeEnum.CODE_700);
    }
    Api(String name, String classPath) {
        this.name = name;
        this.classPath = classPath;
    }

}
