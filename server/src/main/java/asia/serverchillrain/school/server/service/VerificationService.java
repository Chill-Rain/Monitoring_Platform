package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface VerificationService {
    String sendEmailCode(HttpServletRequest request, String email) throws MonitoringPlatformException, UnsupportedEncodingException;
}
