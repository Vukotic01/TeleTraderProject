package com.example.projectteletrader.Controller;
import com.example.projectteletrader.DTO.OrderDTO;
import com.example.projectteletrader.Model.Order;
import com.example.projectteletrader.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/app/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) throws Exception {
        String email = authentication.getName();
        Order createdOrder = orderService.createOrder(orderDTO, email).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdOrder);
    }

    @GetMapping("/app/top-buy")
    public ResponseEntity<?> getTop10BuyOrders(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Order> orders = orderService.getTop10BuyOrdersByUser(email).get();
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("No buy orders found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/app/top-sell")
    public ResponseEntity<?> getTop10SellOrders(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Order> orders = orderService.getTop10SellOrdersByUser(email).get();
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("No sell orders found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }








}