package asia.serverchillrain.school.server.config;

import asia.serverchillrain.school.server.database.MemoryData;
import asia.serverchillrain.school.server.entity.vo.UserVo;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther 2024 01 29
 * 序列化注册器
 */
@Configuration
@ImportRuntimeHints(SerializableHintConfig.PropertyNamingStrategyRegistrar.class)
public class SerializableHintConfig {
    static class PropertyNamingStrategyRegistrar implements RuntimeHintsRegistrar {
        private static List<Class> clazzs = Stream.of(
                String.class,
                ConcurrentHashMap.class,
                MemoryData.class,
                UserVo.class
        ).collect(Collectors.toList());
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            for (Class clazz : clazzs) {
                hints.serialization().registerType(clazz);
            }
        }
    }
}
