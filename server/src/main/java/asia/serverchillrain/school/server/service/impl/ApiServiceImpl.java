package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.school.server.database.MemoryManager;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.ApiService;
import asia.serverchillrain.school.server.utils.HttpUtil;
import asia.serverchillrain.school.server.utils.JsonUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther 2024 02 01
 * API服务
 */
@Service
public class ApiServiceImpl implements ApiService {
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
    @Resource
    private MemoryManager redis;

    @Override
    public String action(String type) throws MonitoringPlatformException {
        Map<String, String> apis = JSON.parseObject(redis.get(Constant.APIS), new TypeReference<Map<String, String>>() {});
        if(apis == null){
            throw new MonitoringPlatformException("APIS设置异常！请删除data.mp!", ResponseCodeEnum.CODE_700);
        }
        String site = apis.get(type);
        if(site == null){
            throw new MonitoringPlatformException("请求了错误的API！", ResponseCodeEnum.CODE_404);
        }
        String result = doFunction(() -> {
            HttpUtil.HttpGet(site);
            logger.info("请求的api为---> " + type);
            return true;
        });
        return result;
    }
    private String doFunction(BooleanSupplier func){
        return String.valueOf(func.getAsBoolean());
    }
}
