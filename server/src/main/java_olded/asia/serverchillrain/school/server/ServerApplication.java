package asia.serverchillrain.school.server;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.nativex.MyBatisResourcesScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统入口
 */

@SpringBootApplication
//@MapperScan(basePackages ="asia.serverchillrain.school.server.mappers")
//@MyBatisResourcesScan(typeAliasesPackages = "asia.serverchillrain.school.server.entity.bean", mapperLocationPatterns = "/static/mappers/*.xml")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
