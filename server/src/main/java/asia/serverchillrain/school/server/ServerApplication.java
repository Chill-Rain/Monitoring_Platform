package asia.serverchillrain.school.server;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.nativex.MyBatisResourcesScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;

/**
 * 系统入口
 */

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
