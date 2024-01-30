package asia.serverchillrain.school.server.config.root;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther 2024 01 28
 */
@Component
@Getter
public class AppConfig {
    @Value("${application.app.path}")
    private String appPath;
    @Value("${application.status}")
    private String status;

}
