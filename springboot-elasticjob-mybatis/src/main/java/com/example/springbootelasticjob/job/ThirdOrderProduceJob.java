package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.autoconfig.ElasticSimpleJob;
import com.example.springbootelasticjob.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname ThirdOrderProduceJob
 * @Description
 * @Date 2021/1/16 9:28
 * @Created by hmh
 */
//@ElasticSimpleJob(
//        jobName = "thirdOrderProduceJob",
//        cron = "0/5 * * * * ?",
//        shardingTotalCount = 1,
//        overwrite = true
//)
public class ThirdOrderProduceJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        orderService.produceThirdOrder();
    }
}
