package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.VerificationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.SimpleMailMessage;
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
    @RequestMapping("/sendEmailCode")
    public Response sendEmailCode(HttpServletRequest request, String email) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(verificationService.sendEmailCode(request, email));
    }



}
