package asia.serverchillrain.school.server.database;


import asia.serverchillrain.school.server.utils.CodingUtil;
import asia.serverchillrain.school.server.utils.DataBaseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @auther 2024 01 27
 * 内存管理器(单例) 该部分用于实现一个类似于redis的简单存储库 只提供添加，删除，获取方法
 */
@Component
public class MemoryManager {

    private final Map<String, MemoryData> memoryDataBase = DataBaseUtil.getDataBase();
    private final Logger logger = LoggerFactory.getLogger(MemoryManager.class);
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    private final boolean flag = true;

    public MemoryManager() throws IOException, ClassNotFoundException {
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
    public String get(String key) throws UnsupportedEncodingException {
        MemoryData data = memoryDataBase.get(key);
        if(data == null || data.getIsDelete()){
            return null;
        }
        logger.info("获取了数据---> " + key + "-" + data.getData());
//        return CodingUtil.UTF8toGBK(data.getData());
        return data.getData();
//        return new String(data.getData().getBytes("UTF-8"));
    }
    public void put(String key, String data) throws UnsupportedEncodingException {
//        data = CodingUtil.UTF8toGBK(data);
        memoryDataBase.put(key, new MemoryData(data));
        logger.info("添加了数据---> " + key + "-" + data);
        DataBaseUtil.saveDataBase(memoryDataBase);
    }
    public MemoryData put(String key, MemoryData data) throws UnsupportedEncodingException {
//        data.setData(CodingUtil.UTF8toGBK(data.getData()));
        if(memoryDataBase.get(key) != null){
            memoryDataBase.put(key, data);
            logger.info("修改了数据---> " + key + "-" + data);
            return data;
        }
        memoryDataBase.put(key, data);
        logger.info("添加了数据---> " + key + "-" + data);
        DataBaseUtil.saveDataBase(memoryDataBase);
        return data;
    }
    public String remove(String key) {
        MemoryData data = memoryDataBase.get(key);
        if(data == null || data.getIsDelete()){
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
            int checkCount = 25;
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
            for (String key : queue) {
                long now = System.currentTimeMillis();
                MemoryData data = memoryDataBase.get(key);
                if (data != null && data.getExpiredTime() != 0L && data.getExpiredTime() - now <= 0) {
                    data.setIsDelete(true);
                }
            }
            Thread.sleep(1000);
        }
    }
    private void delete() throws InterruptedException {
        while(flag){
            Iterator<Map.Entry<String, MemoryData>> iterator = memoryDataBase.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, MemoryData> next = iterator.next();
                if(next.getValue().getIsDelete()){
                    iterator.remove();
                    logger.info("删除了数据--->" + next.getValue());
                }
            }
            Thread.sleep(10 * 1000);
        }
    }

}
