package com.example;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.job.MyDataflowJob;
import com.example.job.MySimpleJob;

/**
 * @Classname App
 * @Description
 * @Date 2021/1/13 20:16
 * @Created by hmh
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
//        new JobScheduler(zkCenter(), configuration()).init();
        new JobScheduler(zkCenter(), configuration()).init();

    }

    /**
     * zookeeper注册中心
     *
     * @return
     */
    public static CoordinatorRegistryCenter zkCenter() {
        ZookeeperConfiguration zc = new ZookeeperConfiguration("localhost:2181",
                "java-simple-job");

        ZookeeperRegistryCenter crc = new ZookeeperRegistryCenter(zc);
        //注册中心初始化
        crc.init();
        return crc;
    }

    public static LiteJobConfiguration configuration() {
        // job 核心配置
        JobCoreConfiguration jcc = JobCoreConfiguration
                .newBuilder("mySimpleJob", "0/5 * * * * ?", 2)
                .build();

        // job 类型配置
        JobTypeConfiguration jtc = new SimpleJobConfiguration(jcc, MySimpleJob.class.getCanonicalName());

        //job根的配置（LiteJobConfiguration）
        LiteJobConfiguration ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }

    public static LiteJobConfiguration configurationDataflow() {
        // job 核心配置
        JobCoreConfiguration jcc = JobCoreConfiguration
                .newBuilder("myDataFlowJob", "0/10 * * * * ?", 2)
                .build();

        // job 类型配置
        JobTypeConfiguration jtc = new DataflowJobConfiguration(jcc, MyDataflowJob.class.getCanonicalName(), true);

        //job根的配置（LiteJobConfiguration）
        LiteJobConfiguration ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }
}
