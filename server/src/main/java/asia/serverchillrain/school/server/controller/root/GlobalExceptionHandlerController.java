package asia.serverchillrain.school.server.controller.root;

import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @auther 2024 01 26
 * 异常控制器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController extends BaseController {
    private static  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);
//    @ExceptionHandler(value = Exception.class)
    public Response handler(Exception e){
        logger.error(e.getMessage());
        if(e instanceof MonitoringPlatformException) {
            return Monitorhandler((MonitoringPlatformException) e);
        }else {
            return getErrorResponse(e.getClass().getName()
                    + ": "
                    + e.getMessage(),
                    ResponseCodeEnum.CODE_700);
        }

    }
    private Response Monitorhandler(MonitoringPlatformException e){
       return getErrorResponse(e.getMessage(), e.getCodeEnum());
    }
}
