package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 2024 01 27
 * 登录注销注册Controller
 */
@RestController("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public Response register(String email, String password, String emailCode) throws MonitoringPlatformException {
        return getSuccessResponse(userService.register(email, password, emailCode));
    }

    @RequestMapping("/login")
    public Response login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException {
        return getSuccessResponse(userService.login(request, email, password));
    }
}
