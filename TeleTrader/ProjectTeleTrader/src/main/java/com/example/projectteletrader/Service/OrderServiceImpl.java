package com.example.projectteletrader.Service;
import com.example.projectteletrader.DTO.OrderDTO;
import com.example.projectteletrader.Model.Order;
import com.example.projectteletrader.Model.UserInfo;
import com.example.projectteletrader.Repository.OrderRepository;
import com.example.projectteletrader.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Order> createOrder(OrderDTO orderDTO, String email) throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        logger.info("SecurityContext in async task: " + securityContext.getAuthentication());


        UserInfo user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        Order order = new Order();
        order.setPrice(orderDTO.getPrice());
        order.setAmount(orderDTO.getAmount());
        order.setOrderType(orderDTO.getOrderType());
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        logger.info("Order saved successfully: " + savedOrder.getId());

        return CompletableFuture.completedFuture(savedOrder);
    }


    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Order>> getTop10BuyOrdersByUser(String email) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = orderRepository.findByOrderTypeAndUserEmailOrderByPriceDesc("BUY", email, pageable);
        return CompletableFuture.completedFuture(orders);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Order>> getTop10SellOrdersByUser(String email) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = orderRepository.findByOrderTypeAndUserEmailOrderByPriceAsc("SELL", email, pageable);
        return CompletableFuture.completedFuture(orders);
    }
}