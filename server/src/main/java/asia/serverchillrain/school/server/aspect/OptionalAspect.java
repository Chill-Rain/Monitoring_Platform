//package asia.serverchillrain.school.server.aspect;
//
//import asia.serverchillrain.school.server.annotation.NeedLogin;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * @auther 2024 01 27
// * 操作切面
// */
//@Component
//@Aspect
//public class OptionalAspect {
//    private static final Logger logger = LoggerFactory.getLogger(OptionalAspect.class);
//    /**
//     * 需要登录切点
//     */
//    @Pointcut("@annotation(asia.serverchillrain.school.server.annotation.NeedLogin)")
//    private void needLogin(){}
//
//    /**
//     *
//     */
//    @Pointcut("@annotation(asia.serverchillrain.school.server.annotation.Admin)")
//    private void Admin(){}
//    @Around("needLogin()")
//    public Object interceptNeedLogin(ProceedingJoinPoint point){
//        try {
//            //切点目标
//            Object target = point.getTarget();
//            //切点参数
//            Object[] args = point.getArgs();
//            //切入方法名
//            String methodName = point.getSignature().getName();
//            logger.info("正在环绕方法---> " + methodName);
//            //获取对应的参数类型列表
//            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
//            Method method;
//            method = target.getClass().getMethod(methodName, parameterTypes);
//            NeedLogin needLogin = method.getAnnotation(NeedLogin.class);
//            boolean sign;
//            if(needLogin != null){
//                sign = doIntercept();
//                logger.info("执行增强方法---> " + needLogin.getClass());
//            }else{
//                logger.info("直接执行原方法---> " + methodName);
//                sign = true;
//            }
//            if(sign){
//                return point.proceed();
//            }else{
//                return null;
//            }
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private boolean doIntercept(){
//        return false;
//    }
//
//}
