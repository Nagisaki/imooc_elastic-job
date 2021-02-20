package com.example.autoconfig;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname ElasticSimpleJob
 * @Description
 * @Date 2021/1/14 16:18
 * @Created by hmh
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticSimpleJob {

    String jobName() default "";

    String cron() default "";

    int shardingTotalCount() default 1;

    boolean overwrite() default false;

    Class<? extends JobShardingStrategy> jobStrategy() default AverageAllocationJobShardingStrategy.class;

    boolean jobEvent() default false;

    Class<? extends ElasticJobListener>[] jobListener() default {};
}