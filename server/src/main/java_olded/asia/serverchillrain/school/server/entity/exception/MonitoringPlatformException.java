package asia.serverchillrain.school.server.entity.exception;

import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 2024 01 26
 * 监控平台异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MonitoringPlatformException extends Exception{
    String message;
    ResponseCodeEnum codeEnum;
}
