package com.example.springbootelasticjob.sharding;

import com.dangdang.ddframe.job.lite.api.strategy.JobInstance;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

/**
 * @Classname MyShardingStrategy
 * @Description
 * @Date 2021/1/16 14:17
 * @Created by hmh
 */
public class MyShardingStrategy implements JobShardingStrategy {

    /**
     * 作业分片.
     *
     * @param jobInstances       所有参与分片的单元列表
     * @param jobName            作业名称
     * @param shardingTotalCount 分片总数
     * @return 分片结果
     */
    @Override
    public Map<JobInstance, List<Integer>> sharding(List<JobInstance> jobInstances,
                                                    String jobName,
                                                    int shardingTotalCount) {
        Map<JobInstance, List<Integer>> map = new HashMap<>();
        ArrayDeque<Integer> deque = new ArrayDeque<>(shardingTotalCount);

        for (int i = 0; i < shardingTotalCount; i++) {
            deque.add(i);
        }

        while (deque.size() > 0) {
            for (JobInstance jobInstance : jobInstances) {
                if (deque.size() > 0) {
                    List<Integer> integers = map.get(jobInstance);
                    if (integers != null && integers.size() > 0) {
                        integers.add(deque.pop());
                    } else {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(deque.pop());
                        map.put(jobInstance, list);
                    }
                }
            }
        }
        return map;
    }
}
