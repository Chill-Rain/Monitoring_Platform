package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.database.MemoryData;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.mappers.UserMapper;
import asia.serverchillrain.school.server.service.UserService;
import asia.serverchillrain.school.server.settings.Setting;
import asia.serverchillrain.school.server.settings.api.root.ApiSetting;
import asia.serverchillrain.school.server.settings.email.root.EmailSetting;
import asia.serverchillrain.school.server.utils.DataBaseUtil;
import asia.serverchillrain.school.server.utils.SystemSettingUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static asia.serverchillrain.school.server.utils.SystemSettingUtil.KEY_EMAILS;
import static asia.serverchillrain.school.server.utils.SystemSettingUtil.getSystemSetting;

/**
 * @auther 2024 01 26
 * 测试Controller
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MemoryManager dataManager;
    @Resource
    private JavaMailSender sender;
    @Resource
    private MemoryManager redis;

    @RequestMapping("/test")
    public Response test(){
       return getSuccessResponse("Test Success!");
    }
    @RequestMapping("/exception")
    public Response exception() throws MonitoringPlatformException {
        throw new MonitoringPlatformException("成功触发异常", ResponseCodeEnum.CODE_700);
    }
    @RequestMapping("/add/{key}/{data}")
    public Response add(@PathVariable String key, @PathVariable String data) throws MonitoringPlatformException, UnsupportedEncodingException {
        MemoryData data1 = dataManager.put(key, new MemoryData(data));
        return getSuccessResponse(data1);
    }

    @RequestMapping("/get/{key}")
    public Response get(@PathVariable String key) throws MonitoringPlatformException, UnsupportedEncodingException {
        String data1 = dataManager.get(key);
        return getSuccessResponse(data1);
    }

    @RequestMapping("/remove/{key}")
    public Response remove(@PathVariable String key) throws MonitoringPlatformException, UnsupportedEncodingException {
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
    @RequestMapping("/readMemory")
    public Response readMemory() throws IOException, ClassNotFoundException {
        return getSuccessResponse(redis.get(KEY_EMAILS));
    }
    @RequestMapping("/email/{target}/{content}")
    public Response email(@PathVariable String target, @PathVariable String content) throws MessagingException {
        try {
            MimeMessage message = sender.createMimeMessage();//发送器
            MimeMessageHelper helper = new MimeMessageHelper(message);//编辑器
            //邮件编辑
            EmailSetting emailSetting = (EmailSetting)getSystemSetting(KEY_EMAILS);
            helper.setSubject(emailSetting.getEmail_title().getLine());
            helper.setText(content);
            helper.setSentDate(new Date());
            helper.setTo(target);
            helper.setFrom(emailSetting.getSystem_email().getLine());
            sender.send(message);
//            logger.info("邮件发送成功！收件人：" + email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return getSuccessResponse("success!");
    }
    @RequestMapping("/invokeEmail")
    public Response invokeEmail(){
        EmailSetting emailSetting = (EmailSetting)SystemSettingUtil.getSystemSetting(SystemSettingUtil.KEY_EMAILS);
        return getSuccessResponse(emailSetting.getSystem_email());
    }
    @RequestMapping("/invokeApi")
    public Response invokeApi(){
        ApiSetting apiSetting = (ApiSetting)SystemSettingUtil.getSystemSetting(SystemSettingUtil.KEY_APIS);
        return getSuccessResponse(apiSetting.getCamera());
    }
}
