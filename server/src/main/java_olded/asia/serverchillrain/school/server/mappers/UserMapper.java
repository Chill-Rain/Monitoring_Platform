package asia.serverchillrain.school.server.mappers;

import asia.serverchillrain.school.server.entity.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
