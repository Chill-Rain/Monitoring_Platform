package asia.serverchillrain.school.server.annotation;

import java.lang.annotation.*;

/**
 * 需要注解
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {


}
