package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.autoconfig.ElasticSimpleJob;
import com.example.springbootelasticjob.listener.MyNormalListener;
import com.example.springbootelasticjob.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Classname MySimpleJob
 * @Description
 * @Date 2021/1/14 17:23
 * @Created by hmh
 */
@ElasticSimpleJob(
        jobName = "mySimpleJob",
        cron = "0/5 * * * * ?",
        shardingTotalCount = 10,
        overwrite = true,
//        jobEvent = true
        jobListener = MyNormalListener.class
)
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("{},执行批量插入订单任务。", new Date());
        for (int i = 0; i < 10; i++)
            orderService.insertOrder();
    }
}