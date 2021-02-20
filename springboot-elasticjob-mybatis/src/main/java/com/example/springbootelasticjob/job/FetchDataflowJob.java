package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.example.autoconfig.ElasticDataflowJob;
import com.example.springbootelasticjob.dao.JdOrderMapper;
import com.example.springbootelasticjob.dao.TmallOrderMapper;
import com.example.springbootelasticjob.model.AllOrder;
import com.example.springbootelasticjob.model.JdOrder;
import com.example.springbootelasticjob.model.TmallOrder;
import com.example.springbootelasticjob.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Classname FetchDataflowJob
 * @Description
 * @Date 2021/1/16 9:34
 * @Created by hmh
 */
//@ElasticDataflowJob(
//        jobName = "fetchThirdOrderJob",
//        cron = "0/10 * * * * ?",
//        shardingTotalCount = 2,
//        overwrite = true,
//        streamingProcess = false
//)
@Slf4j
public class FetchDataflowJob implements DataflowJob<Object> {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdOrderMapper jdOrderMapper;

    @Autowired
    private TmallOrderMapper tmallOrderMapper;

    @Override
    public List fetchData(ShardingContext shardingContext) {
        log.info("{},执行订单抓取任务", new Date());
        //京东订单
        if (shardingContext.getShardingItem() == 0) {
            List<JdOrder> jdOrders = jdOrderMapper.getNotFetchedOrder(1);
            if (jdOrders != null && jdOrders.size() > 0) {
                List<Object> jdOrderList = jdOrders.stream().map(jdOrder -> (Object) jdOrder).collect(toList());
                return jdOrderList;
            }
        } else {//天猫订单
            List<TmallOrder> tmallOrders = tmallOrderMapper.getNotFetchedOrder(1);
            if (tmallOrders != null && tmallOrders.size() > 0) {
                List<Object> tmallOrderList = tmallOrders.stream().map(tmallOrder -> (Object) tmallOrder).collect(toList());
                return tmallOrderList;
            }
        }
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Object> data) {
        log.info("{},执行订单处理任务", new Date());

        //京东订单
        if (shardingContext.getShardingItem() == 0) {
            if (data != null && data.size() > 0) {
                List<JdOrder> jdOrders = data.stream().map(d -> (JdOrder) d).collect(toList());
                for (JdOrder jdOrder : jdOrders) {
                    AllOrder allOrder = new AllOrder();
                    allOrder.setThirdOrderId(jdOrder.getId());
                    allOrder.setType(0);//京东订单
                    allOrder.setTotalAmount(jdOrder.getAmount());
                    allOrder.setCreateUser("system");
                    allOrder.setCreateTime(new Date());
                    allOrder.setUpdateUser("system");
                    allOrder.setUpdateTime(new Date());
                    orderService.processJdOrder(allOrder);
                }
            }
        } else {//天猫订单
            if (data != null && data.size() > 0) {
                List<TmallOrder> tmallOrders = data.stream().map(d -> (TmallOrder) d).collect(toList());
                for (TmallOrder tmallOrder : tmallOrders) {
                    AllOrder allOrder = new AllOrder();
                    allOrder.setThirdOrderId(tmallOrder.getId());
                    allOrder.setType(1);//天猫订单
                    allOrder.setTotalAmount(tmallOrder.getMoney());
                    allOrder.setCreateUser("system");
                    allOrder.setCreateTime(new Date());
                    allOrder.setUpdateUser("system");
                    allOrder.setUpdateTime(new Date());
                    orderService.processTmallOrder(allOrder);
                }
            }
        }

    }
}
