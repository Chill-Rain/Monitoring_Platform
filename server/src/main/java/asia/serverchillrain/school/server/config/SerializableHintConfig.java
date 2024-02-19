package asia.serverchillrain.school.server.config;

import asia.serverchillrain.school.server.database.MemoryData;
import asia.serverchillrain.school.server.entity.vo.UserVo;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * &#064;auther  2024 01 29
 * 序列化指示器
 */
@Configuration
@ImportRuntimeHints(SerializableHintConfig.class)
public class SerializableHintConfig implements RuntimeHintsRegistrar{
    private static final List<Class> clazzs = Stream.of(
            String.class,
            ConcurrentHashMap.class,
            MemoryData.class,
            UserVo.class,
            ReentrantLock.class
    ).collect(Collectors.toList());
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        clazzs.forEach(x -> hints.serialization().registerType(x));
    }

}
