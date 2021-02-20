package com.example.springelasticJob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.springelasticJob.autoconfig.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname MySimpleJob
 * @Description
 * @Date 2021/1/14 17:23
 * @Created by hmh
 */
@ElasticSimpleJob(
        jobName = "mySimpleJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true
)
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("我是分分片项" + shardingContext.getShardingItem() +
                ",分片总数是" + shardingContext.getShardingTotalCount());
    }
}