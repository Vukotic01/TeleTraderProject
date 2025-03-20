package com.example.projectteletrader.Service;


import com.example.projectteletrader.DTO.OrderDTO;
import com.example.projectteletrader.Model.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    @Async("threadPoolTaskExecutor")
    CompletableFuture<Order> createOrder(OrderDTO orderDTO, String email) throws Exception;

    @Async("threadPoolTaskExecutor")
    CompletableFuture<List<Order>> getTop10BuyOrdersByUser(String email);


    @Async("threadPoolTaskExecutor")
    CompletableFuture<List<Order>> getTop10SellOrdersByUser(String email);
}