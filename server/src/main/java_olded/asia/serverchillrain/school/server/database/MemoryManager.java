package asia.serverchillrain.school.server.database;

import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.utils.DataBaseUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther 2024 01 27
 * 内存管理器(单例) 该部分用于实现一个类似于redis的简单存储库 只提供添加，删除，获取方法
 */
@Component
public class MemoryManager {

    private Map<String, MemoryData> memoryDataBase = DataBaseUtil.getDataBase();
    private final Logger logger = LoggerFactory.getLogger(MemoryManager.class);
    private BlockingQueue<String> queue = new ArrayBlockingQueue(100);
    private boolean flag = true;
    private int checkCount = 25;
    private MemoryManager() throws IOException, ClassNotFoundException {
        logger.info("内存数据管理器已创建！");
        new Thread(() -> {
            try {
                logger.info("检查线程已启动！");
                produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                logger.info("标记删除线程已启动！");
                consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                logger.info("删除线程已启动！");
                delete();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DataBaseUtil.saveDataBase(memoryDataBase);
        }));
    }
    public String get(String key){
        MemoryData data = memoryDataBase.get(key);
        if(data.getIsDelete()){
            return null;
        }
        logger.info("获取了数据---> " + key + "-" + data.getData());
        return data.getData();
    }
    public String put(String key, String data){
        memoryDataBase.put(key, new MemoryData(data));
        logger.info("添加了数据---> " + key + "-" + data);
        DataBaseUtil.saveDataBase(memoryDataBase);
        return data;
    }
    public String remove(String key){
        MemoryData data = memoryDataBase.get(key);
        if(data.getIsDelete()){
            return null;
        }
        data.setIsDelete(true);
        logger.info("移除了数据---> " + key + "-" + data.getData());
        DataBaseUtil.saveDataBase(memoryDataBase);
        return data.getData();
    }

    private void produce() throws InterruptedException {
        while(flag){
            //读取库容量大小，并动态确定要标记的数量
            int size = memoryDataBase.size();
            //动态确定淘汰数量
            int count = (size / checkCount) < checkCount ? 0 : (size / checkCount);
            Random random = new Random();
            memoryDataBase.forEach((key, data) ->{
                int index = random.nextInt(size);
                Set<String> keys = memoryDataBase.keySet();
                String[] array = keys.toArray(new String[0]);
                key = array[index];
                try {
                    if(!queue.contains(key) && memoryDataBase.get(key).getNeedExpired()){
                        queue.put(key);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Thread.sleep(1000);
        }
    }

    /**
     * 标记删除
     */
    private void consume() throws InterruptedException {
        while(flag){
            queue.forEach(data ->{
                long now = System.currentTimeMillis();
                MemoryData memoryData = memoryDataBase.get(data);
                //应当过期
                if(memoryData.getExpiredTime() != 0L && memoryData.getExpiredTime() - now >= 0){
                    memoryData.setIsDelete(true);
                }
            });
            Thread.sleep(1000);
        }
    }
    private void delete() throws InterruptedException {
        while(flag){
            memoryDataBase.forEach((key, data) -> {
                if(data.getIsDelete()){
                    memoryDataBase.remove(key);
                    logger.info("删除了数据--->" + key);
                }
            });
            Thread.sleep(60 * 100);
        }
    }

}
