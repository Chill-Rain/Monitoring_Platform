package asia.serverchillrain.school.server.controller;

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
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Resource
    private ApiService apiService;
    @RequestMapping("/connectCamera/{dst}")
    public Response connectCamera(HttpServletRequest request, @PathVariable String dst) throws MonitoringPlatformException {

        return getSuccessResponse(apiService.action(dst));
    }
}
