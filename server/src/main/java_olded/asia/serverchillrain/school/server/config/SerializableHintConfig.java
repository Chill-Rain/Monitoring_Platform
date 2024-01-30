package asia.serverchillrain.school.server.config;

import asia.serverchillrain.school.server.database.MemoryData;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 2024 01 29
 * 序列化注册器
 */
@Configuration
@ImportRuntimeHints(SerializableHintConfig.PropertyNamingStrategyRegistrar.class)
public class SerializableHintConfig {
    static class PropertyNamingStrategyRegistrar implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.serialization()
                    .registerType(String.class)
                    .registerType(ConcurrentHashMap.class)
                    .registerType(MemoryData.class);

        }
    }
}
