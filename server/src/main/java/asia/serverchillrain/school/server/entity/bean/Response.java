package asia.serverchillrain.school.server.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * &#064;auther  2024 01 26
 * 响应载体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String status;
    private Integer code;
    private String info;
    private String data;
}
