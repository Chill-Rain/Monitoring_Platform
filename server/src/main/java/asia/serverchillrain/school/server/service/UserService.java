package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

//@Service
public interface UserService {
    String register(String email, String password, String emailCode) throws MonitoringPlatformException;

    void registerAdmin(User adminUser);

    String login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException;

    String logout(HttpServletRequest request) throws MonitoringPlatformException;
}
