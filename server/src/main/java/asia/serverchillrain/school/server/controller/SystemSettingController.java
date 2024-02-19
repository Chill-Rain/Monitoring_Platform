package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.SystemSettingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * &#064;auther  2024 01 28
 * 系统设置Controller
 */
@RestController("/system")
public class SystemSettingController extends BaseController {
    @Resource
    private SystemSettingService systemSettingService;

    /**
     * 从内存库中刷新缓存
     * @return 结果
     * @throws UnsupportedEncodingException 异常
     * @throws IntrospectionException 异常
     * @throws MonitoringPlatformException 异常
     * @throws ClassNotFoundException 异常
     * @throws InvocationTargetException 异常
     * @throws IllegalAccessException 异常
     * @throws InstantiationException 异常
     */
    @RequestMapping("/refreshFromRedis")
    public Response refresh() throws UnsupportedEncodingException, IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return getSuccessResponse(systemSettingService.readSettingsFormRedis2Memory());
    }
}
