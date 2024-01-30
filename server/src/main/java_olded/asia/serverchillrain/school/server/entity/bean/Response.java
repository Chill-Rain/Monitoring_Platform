package asia.serverchillrain.school.server.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 2024 01 26
 * 响应载体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String status;
    private Integer code;
    private String info;
    private T data;
}
