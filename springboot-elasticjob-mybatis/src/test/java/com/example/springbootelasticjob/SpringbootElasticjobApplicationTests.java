package com.example.springbootelasticjob;

import com.example.springbootelasticjob.dao.JdOrderMapper;
import com.example.springbootelasticjob.dao.TmallOrderMapper;
import com.example.springbootelasticjob.model.JdOrder;
import com.example.springbootelasticjob.model.Order;
import com.example.springbootelasticjob.model.TmallOrder;
import com.example.springbootelasticjob.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticjobApplicationTests {

    @Autowired
    private OrderService orderService;
    @Autowired
    private JdOrderMapper jdOrderMapper;
    @Autowired
    private TmallOrderMapper tmallOrderMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testOrder(){
        orderService.insertOrder();
    }

//
//    @Test
//    public void testGetOrder(){
//        Calendar now = Calendar.getInstance();
//        now.add(Calendar.SECOND,-30);
//
//        List<Order> orders = orderService.getOrder(now, 2, 1);
//        System.out.println(orders.stream().map(Order::getId).collect(toList()));
//    }
//
//    @Test
//    public void testThirdOrder(){
//        orderService.produceThirdOrder();
//    }
//
//    @Test
//    public void testGetJdOrder(){
//        List<JdOrder> notFetchedOrder = jdOrderMapper.getNotFetchedOrder(5);
//        System.out.println(notFetchedOrder.size());
//
//    }
//    @Test
//    public void testGetTmallOrder(){
//        List<TmallOrder> notFetchedOrder = tmallOrderMapper.getNotFetchedOrder(5);
//        System.out.println(notFetchedOrder.size());
//
//    }

}
