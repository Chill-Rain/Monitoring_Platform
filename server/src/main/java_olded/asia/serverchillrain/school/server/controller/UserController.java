package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Response register(@RequestBody User user){
        return getSuccessResponse(userService.register(user));
    }


}
