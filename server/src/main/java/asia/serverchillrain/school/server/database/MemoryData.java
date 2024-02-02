package asia.serverchillrain.school.server.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @auther 2024 01 27
 * 内存数据
 */
@Data
@NoArgsConstructor
public class MemoryData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4262229809827907727L;
    private String data;
    private Integer length;
    private Long createTime = System.currentTimeMillis();
    private Boolean needExpired = false;
    private Boolean isDelete = false;
    private Long expiredTime = 0L;

    public MemoryData(String data) {
//        this.data = data;
        this.data = new String(data.getBytes(StandardCharsets.UTF_8));
        length = data.length();
    }
    public void expired(long time) {
        expiredTime = createTime + time;
    }
}
