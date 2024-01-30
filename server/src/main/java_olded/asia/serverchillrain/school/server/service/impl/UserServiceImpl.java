package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.mappers.UserMapper;
import asia.serverchillrain.school.server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @auther 2024 01 28
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public String register(User user) {

        return null;
    }
}
