package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.mappers.UserMapper;
import asia.serverchillrain.school.server.utils.DataBaseUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 2024 01 26
 * 测试Controller
 */
@RestController
public class TestController extends BaseController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MemoryManager dataManager;
    @RequestMapping("/test")
    public Response test(){
       return getSuccessResponse("Test Success!");
    }
    @RequestMapping("/exception")
    public Response exception() throws MonitoringPlatformException {
        throw new MonitoringPlatformException("成功触发异常", ResponseCodeEnum.CODE_700);
    }
    @RequestMapping("/add/{key}/{data}")
    public Response add(@PathVariable String key, @PathVariable String data) throws MonitoringPlatformException {
        String data1 = dataManager.put(key, data);
        return getSuccessResponse(data1);
    }

    @RequestMapping("/get/{key}")
    public Response get(@PathVariable String key) throws MonitoringPlatformException {
        String data1 = dataManager.get(key);
        return getSuccessResponse(data1);
    }

    @RequestMapping("/remove/{key}")
    public Response remove(@PathVariable String key) throws MonitoringPlatformException {
        String data1 = dataManager.remove(key);
        return getSuccessResponse(data1);
    }
    @RequestMapping("/selectUser/{id}")
    public Response selectUser(@PathVariable String id){
        User userInfo = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", id));
        return getSuccessResponse(userInfo);
    }
    @RequestMapping("/newMap")
    public Response newMap(){
        return getSuccessResponse(new ConcurrentHashMap<>().toString());
    }
    @RequestMapping("/readDatabase")
    public Response read() throws IOException, ClassNotFoundException {
        return getSuccessResponse(DataBaseUtil.getDataBase());
    }
}
