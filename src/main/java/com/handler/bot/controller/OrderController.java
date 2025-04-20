package com.handler.bot.controller;

import com.handler.bot.model.OrderModifyReq;
import com.handler.bot.model.OrderReq;
import com.handler.bot.model.OrderRes;
import com.handler.bot.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/punch")
    public void placeOrder(@RequestBody OrderReq orderReq) {
        OrderRes orderRes = orderService.placeOrder(orderReq);
        logger.info("Order Placed {}", orderRes);
    }

    @PostMapping("/mod")
    public void modifyOrder(@RequestBody OrderModifyReq orderModifyReq) {
        OrderRes orderRes = orderService.modifyOrder(orderModifyReq);
        logger.info("Order Modified {}", orderRes);
    }
}
