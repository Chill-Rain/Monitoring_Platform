package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.ApiService;
import asia.serverchillrain.school.server.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @auther 2024 02 01
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Value("${application.api.camera.site}")
    private String cameraSite;
    @Value("${application.api.rtmp.site}")
    private String rtmp;
    @Override
    public String action(String dst) throws MonitoringPlatformException {
//        String response = HttpUtil.HTTPGet(dst);
        String result = null;
        switch (dst){
            case "camera":
                result = doFunction(() -> {
                    String site = cameraSite + "?dst=" + rtmp;
//                        HttpUtil.HTTPGet(cameraSite + "?dst=" + rtmp);
                    HttpUtil.HttpGet(site);
                    return true;
                });
        }
        return String.valueOf(result);
    }
    private String doFunction(BooleanSupplier func){
        return String.valueOf(func.getAsBoolean());
    }
}
