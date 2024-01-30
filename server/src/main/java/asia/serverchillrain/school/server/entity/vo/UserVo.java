package asia.serverchillrain.school.server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -548869990949101874L;
    private Integer status;
    private String email;

}
