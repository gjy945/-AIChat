package com.example.admin.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);


    // 每分钟执行
//    @Scheduled(cron = "0 */1 * * * ?")
    // 每天凌晨0点执行
//     @Scheduled(cron = "0 0 0 * * ?")
    // 每秒执行
    @Scheduled(cron = "0 * * * * ?")
    public void test() {

         logger.info("测试定时任务");
    }
}
