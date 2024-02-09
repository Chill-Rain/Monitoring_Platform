package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.database.MemoryData;
import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.enums.UserStatus;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.entity.vo.UserVo;
import asia.serverchillrain.school.server.mappers.UserMapper;
import asia.serverchillrain.school.server.service.UserService;
import asia.serverchillrain.school.server.table.UserTable;
import asia.serverchillrain.school.server.utils.CodingUtil;
import asia.serverchillrain.school.server.utils.IPUtil;
import asia.serverchillrain.school.server.utils.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @auther 2024 01 28
 */
@Service
//@Scope("singleton")
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private  MemoryManager redis;

//    static {
//        try {
//            redis = new MemoryManager();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Resource
    private UserMapper userMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(String email, String password, String emailCode) throws MonitoringPlatformException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("email", user.getEmail());
        User tryRegisterUser = userMapper.selectOne(query);
        if(tryRegisterUser != null){
            throw new MonitoringPlatformException("注册名已存在！", ResponseCodeEnum.CODE_200);
        }
        user.setId(new Random().nextInt(999999));
        user.setEmail(user.getEmail());
        user.setPassword(CodingUtil.encodeMD5(user.getPassword()));
        userMapper.insert(user);
        logger.info("注册信息---> " + user);
        return "注册成功！";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerAdmin(User adminUser) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("email", adminUser.getEmail());
        User tryRegisterUser = userMapper.selectOne(query);
        if(tryRegisterUser != null){
            logger.info("最高管理员账户已存在！");
        }else{
            adminUser.setId(new Random().nextInt(999999));
            userMapper.insert(adminUser);
            logger.info("管理员未注册，已自动注册。 管理员注册信息---> " + adminUser);
        }
    }

    @Override
    public String login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException, UnsupportedEncodingException {
        String ip = IPUtil.getIp(request);
        String userJson = redis.get(ip + "-" + email);
        UserVo user = null;
        //redis中有，直接登陆并重置自动登录时间
        if(userJson != null){
            //重新设置过期和过期时间
            logger.info("------>从内存数据库中登录");
//            MemoryData data = JsonUtil.json2Object(userJson, MemoryData.class);
            user = JsonUtil.json2Object(userJson, UserVo.class);
            if(user.getEmail() == null || user.getStatus() == null){
                redis.remove(ip + "-" + email);
                throw new MonitoringPlatformException("错误的缓存信息，已刷新，请重新登陆！", ResponseCodeEnum.CODE_200);
            }
        }else{//没有，走正常登录逻辑
            logger.info("------>从MySQL中登录");
            QueryWrapper<User> query = new QueryWrapper<>();
            query.eq(UserTable.EMAIL, email)
                    .eq(UserTable.PASSWORD, CodingUtil.encodeMD5(password));
            User one = userMapper.selectOne(query);
            if(one == null){
                throw new MonitoringPlatformException("账号或密码错误！", ResponseCodeEnum.CODE_200);
            }
            if(one.getStatus() == UserStatus.BAN.getCode()){
                throw new MonitoringPlatformException("用户已封禁！", ResponseCodeEnum.CODE_200);
            }
            user = new UserVo(one.getStatus(), one.getEmail());
        }
        MemoryData data = new MemoryData(JsonUtil.object2Json(user));
        data.setNeedExpired(true);
        data.expired(7 * 24 * 60 * 60);
        request.getSession().setAttribute(Constant.LOG_USER + ip, user);
        redis.put(ip + "-" + email, data);
        return "已登录！";
    }

    @Override
    public String logout(HttpServletRequest request) throws MonitoringPlatformException, UnsupportedEncodingException {
        String ip = IPUtil.getIp(request);
        UserVo uservo = (UserVo) request.getSession().getAttribute(Constant.LOG_USER + IPUtil.getIp(request));
        if(uservo == null){
            throw new MonitoringPlatformException("你没有登陆！", ResponseCodeEnum.CODE_600);
        }
        redis.remove(ip + "-" + uservo.getEmail());
        logger.info("用户登出---> " + uservo.getEmail());
        return "Success!";
    }
}
