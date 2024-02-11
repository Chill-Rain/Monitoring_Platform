//package asia.serverchillrain.school.server.config;
//
////import asia.serverchillrain.school.server.aspect.OptionalAspect;
//import org.springframework.aot.hint.MemberCategory;
//import org.springframework.aot.hint.RuntimeHints;
//import org.springframework.aot.hint.RuntimeHintsRegistrar;
//import org.springframework.beans.factory.BeanClassLoaderAware;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.boot.availability.ApplicationAvailability;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportRuntimeHints;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * @auther 2024 02 09
// * 代理指示器
// */
//@Configuration
//@ImportRuntimeHints(ProxyHintConfig.class)
//public class ProxyHintConfig implements RuntimeHintsRegistrar {
////    private static List<Class<?>> aspect = Stream.of(
//////            OptionalAspect.class
////    ).collect(Collectors.toList());
//    @Override
//    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
//        aspect.forEach(x -> hints.reflection()
//                .registerType(x, builder -> builder.withMembers(MemberCategory.INTROSPECT_DECLARED_METHODS)));
//        hints.proxies().registerJdkProxy(FactoryBean.class, BeanClassLoaderAware.class, ApplicationListener.class);
//        hints.proxies().registerJdkProxy(ApplicationAvailability.class, ApplicationListener.class);
//    }
//}
