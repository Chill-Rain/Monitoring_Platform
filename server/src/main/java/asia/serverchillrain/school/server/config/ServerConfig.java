package asia.serverchillrain.school.server.config;

import asia.serverchillrain.school.server.config.root.AppConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther 2024 01 28
 */
@Component
@Getter
public class ServerConfig extends AppConfig {
    @Value("${application.server.path}")
    private String serverPath;
}
