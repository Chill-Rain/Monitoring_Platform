package asia.serverchillrain.school.server.config;

import asia.serverchillrain.school.server.settings.api.*;
import asia.serverchillrain.school.server.settings.api.root.ApiSetting;
import asia.serverchillrain.school.server.settings.email.EmailContent;
import asia.serverchillrain.school.server.settings.email.EmailSystemUser;
import asia.serverchillrain.school.server.settings.email.EmailTime;
import asia.serverchillrain.school.server.settings.email.EmailTitle;
import asia.serverchillrain.school.server.settings.email.root.EmailSetting;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther 2024 02 02
 */
@Configuration
@ImportRuntimeHints(ReflectHintConfig.class)
public class ReflectHintConfig implements RuntimeHintsRegistrar {
    private static List<Class> list = Stream.of(
            EmailTime.class,
            EmailTitle.class,
            EmailContent.class,
            EmailSystemUser.class,
            CameraHardWare.class,
            FireModel.class,
            PhoneModel.class,
            SleepModel.class,
            SmockModel.class,
            ApiSetting.class,
            EmailSetting.class
    ).collect(Collectors.toList());
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        list.forEach(x -> hints.reflection().registerType(x, MemberCategory.values()));
    }
}
