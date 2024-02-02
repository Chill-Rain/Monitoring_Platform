package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import jakarta.servlet.http.HttpServletRequest;

public interface VerificationService {
    String sendEmailCode(HttpServletRequest request, String email) throws MonitoringPlatformException;
}
