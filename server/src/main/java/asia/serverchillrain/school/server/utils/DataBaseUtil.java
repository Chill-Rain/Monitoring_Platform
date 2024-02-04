package asia.serverchillrain.school.server.utils;

import asia.serverchillrain.school.server.database.MemoryData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 2024 01 27
 * Jar下的内存处理器
 */

public class DataBaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseUtil.class);
    private static final String path = "data.mp";
    public static Map<String, MemoryData> getDataBase() throws IOException, ClassNotFoundException {
        if(new File(path).exists()){
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
            Object o = ois.readObject();
            ois.close();
//            if(o instanceof Map){
//                return (Map<String, MemoryData>) o;
            if(o instanceof String){
                logger.info("数据载入于--->" + path);
                return readJsonDataBase((String) o);
            }else {
                logger.info("空内存数据库--->已创建新数据源");
                return new ConcurrentHashMap<>();
            }
        }else {
            logger.info("空内存数据库--->已创建新数据源");
            return new ConcurrentHashMap<>();
        }
    }
    public static void saveDataBase(Map<String, MemoryData> database){
        try {
            String databaseJson = JsonUtil.object2Json(database);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(databaseJson);
            oos.close();
            logger.info("数据已保存于--->" + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static ConcurrentHashMap<String, MemoryData> readJsonDataBase(String json){
        return JSON.parseObject(json, new TypeReference<ConcurrentHashMap<String, MemoryData>>() {});

    }

}
