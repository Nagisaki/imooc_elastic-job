package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.autoconfig.ElasticSimpleJob;
import com.example.springbootelasticjob.model.Order;
import com.example.springbootelasticjob.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.runtime.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@ElasticSimpleJob(
//        jobName = "orderCancelJob",
//        cron = "0/15 * * * * ?",
//        shardingTotalCount = 2,
//        overwrite = true
//)
@Slf4j
public class OrderCancelJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("{},执行取消订单任务", new Date());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, -10);
        List<Order> orders = orderService.getOrder(now, shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());

        // 多线程异步 取消订单
        if (orders != null && orders.size() > 0) {
            ExecutorService es = Executors.newFixedThreadPool(4);
            for (Order order : orders) {
                es.execute(() -> {
                    //更新条件
                    Integer orderId = order.getId();
                    Date updateTime = order.getUpdateTime();
                    //更新内容
                    int status = 3;//已取消
                    String updateUser = "system";
                    Date updateNow = new Date();

                    orderService.cancelOrder(orderId, updateTime, status, updateUser, updateNow);
                });
            }
            es.shutdown();
        }
    }
}
