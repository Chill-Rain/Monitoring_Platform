package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.annotation.NeedLogin;
import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.ApiService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 2024 02 01
 * Api请求Controlelr
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Resource
    private ApiService apiService;

    /**
     * 请求远程Api
     * @param request 一次请求
     * @param type Api类型
     * @return 请求信息
     * @throws MonitoringPlatformException 监控平台异常
     */
    @RequestMapping("/connectApi/{type}")
    @NeedLogin
    public Response connectApi(HttpServletRequest request, @PathVariable String type) throws MonitoringPlatformException {
        return getSuccessResponse(apiService.action(type));
    }
}
