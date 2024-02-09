package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.VerificationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @auther 2024 01 27
 * 验证Controller
 */

@RestController
@RequestMapping("/checkCode")
public class VerificationController extends BaseController {
    @Resource
    private VerificationService verificationService;

    /**
     * 发送验证码
     * @param request 本次请求
     * @param email 邮箱
     * @return 返回
     * @throws MonitoringPlatformException 监控平台异常
     * @throws UnsupportedEncodingException 编码异常
     */
    @RequestMapping("/sendEmailCode")
    public Response sendEmailCode(HttpServletRequest request, String email) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(verificationService.sendEmailCode(request, email));
    }



}
