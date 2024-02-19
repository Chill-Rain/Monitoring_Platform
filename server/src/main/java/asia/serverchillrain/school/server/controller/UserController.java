package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.annotation.NeedLogin;
import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * &#064;auther  2024 01 27
 * Controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    /**
     * 注册
     * @param email 邮箱
     * @param password MD5加密后的密码
     * @param emailCode 邮箱验证码
     * @return 响应
     * @throws MonitoringPlatformException 监控平台异常
     */
    @RequestMapping("/register")
    public Response register(String email, String password, String emailCode) throws MonitoringPlatformException {
        return getSuccessResponse(userService.register(email, password, emailCode));
    }

    /**
     * 登录
     * @param request 本次请求
     * @param email 邮箱
     * @param password MD5加密后的密码
     * @return 响应
     * @throws MonitoringPlatformException 监控平台异常
     * @throws UnsupportedEncodingException 编码异常
     */
    @RequestMapping("/login")
    public Response login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(userService.login(request, email, password));
    }

    /**
     * 登出
     * @param request 本次请求
     * @return 响应
     * @throws MonitoringPlatformException 监控平台异常
     * @throws UnsupportedEncodingException 编码异常
     */
    @RequestMapping("/logout")
    @NeedLogin
    public Response logout(HttpServletRequest request) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(userService.logout(request));
    }
}
