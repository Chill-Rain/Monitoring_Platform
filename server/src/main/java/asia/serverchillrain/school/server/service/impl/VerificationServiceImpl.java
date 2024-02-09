package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.database.MemoryData;
import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.mappers.UserMapper;
import asia.serverchillrain.school.server.service.VerificationService;
import asia.serverchillrain.school.server.settings.email.root.EmailSetting;
import asia.serverchillrain.school.server.utils.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static asia.serverchillrain.school.server.utils.SystemSettingUtil.KEY_EMAILS;
import static asia.serverchillrain.school.server.utils.SystemSettingUtil.getSystemSetting;

/**
 * @auther 2024 01 27
 */
@Service
public class VerificationServiceImpl implements VerificationService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationService.class);
    @Resource
    private MemoryManager redis;
    @Resource
    private JavaMailSender sender;
    @Resource
    private UserMapper userMapper;
    @Override
    public String sendEmailCode(HttpServletRequest request, String email) throws MonitoringPlatformException, UnsupportedEncodingException {
        EmailSetting emailSetting = (EmailSetting)getSystemSetting(KEY_EMAILS);
        //首先查询用户是否存在，存在则抛出异常不存在则继续
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if(user != null){
            throw new MonitoringPlatformException("用户已存在", ResponseCodeEnum.CODE_600);
        }
        //用户不存在，检验验证码是否存在，否则生成验证码并存入redis并设置过期，是则删除
        String emailCode = redis.get(Constant.EMAILCODE + email);
        if(emailCode != null && emailCode.trim() != ""){
            redis.remove(Constant.EMAILCODE + email);
        }
        String code = RandomUtil.getEmailCode(5);
        MemoryData data = new MemoryData(code);
        data.expired(Long.getLong(emailSetting.getEmail_time().getLine()));
        redis.put(Constant.EMAILCODE + email, data);
        this.sendCode(email, code);
        return "send email code success";
    }

    /**
     * 邮件生成器
     * @param email 目标邮箱
     * @param code 验证码
     */
    private void sendCode(String email, String code) {
        try {
            MimeMessage message = sender.createMimeMessage();//发送器
            MimeMessageHelper helper = new MimeMessageHelper(message);//编辑器
            //邮件编辑
            EmailSetting emailSetting = (EmailSetting)getSystemSetting(KEY_EMAILS);
            helper.setSubject(emailSetting.getEmail_title().getLine());
            helper.setText(emailSetting.getEmail_content().getLine()
                    .replace("{code}", code)
                    .replace("{time}", Long.getLong(emailSetting.getEmail_time().getLine()) / 1000 / 60 + "分钟"));
            helper.setSentDate(new Date());
            helper.setTo(email);
            helper.setFrom(emailSetting.getSystem_email().getLine());
            sender.send(message);
            logger.info("邮件发送成功！收件人：" + email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
