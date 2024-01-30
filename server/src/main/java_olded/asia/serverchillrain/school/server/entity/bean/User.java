package asia.serverchillrain.school.server.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 2024 01 27
 *用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private Integer status;
}
