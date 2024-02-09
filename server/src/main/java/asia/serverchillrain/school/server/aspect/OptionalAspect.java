package asia.serverchillrain.school.server.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @auther 2024 01 27
 * 操作切面
 */
@Component
@Aspect
public class OptionalAspect {
    /**
     * 需要登录切点
     */
    @Pointcut("@annotation(asia.serverchillrain.school.server.annotation.NeedLogin)")
    private void needLogin(){}

    /**
     *
     */
    @Pointcut("@annotation(asia.serverchillrain.school.server.annotation.Admin)")
    private void Admin(){}
}
