package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.UserService;
import asia.serverchillrain.school.server.service.VerificationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @auther 2024 01 27
 * Controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private VerificationService verificationService;

    @RequestMapping("/register")
    public Response register(String email, String password, String emailCode) throws MonitoringPlatformException {
        return getSuccessResponse(userService.register(email, password, emailCode));
    }

    @RequestMapping("/login")
    public Response login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(userService.login(request, email, password));
    }
    @RequestMapping("/logout")
    public Response logout(HttpServletRequest request) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(userService.logout(request));
    }
}
