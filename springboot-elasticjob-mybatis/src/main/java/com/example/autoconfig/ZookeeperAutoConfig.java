package com.example.autoconfig;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname ZookeeperAutoConfig
 * @Description
 * @Date 2021/1/13 21:10
 * @Created by hmh
 */
@Configuration
@ConditionalOnProperty("elasticjob.zookeeper.server-list")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfig {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    /**
     * zookeeper注册中心
     *
     * @return
     */
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter zkCenter() {
        String serverLists = zookeeperProperties.getServerList();
        String namespace = zookeeperProperties.getNamespace();
        ZookeeperConfiguration zc = new ZookeeperConfiguration(serverLists, namespace);
        ZookeeperRegistryCenter crc = new ZookeeperRegistryCenter(zc);
        return crc;
    }
}
