package com.dangqp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dangqp.order.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 */
public interface OrderService extends IService<Order> {

    //1 生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
