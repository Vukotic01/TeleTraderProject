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
import java.util.Map;
import java.util.Set;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Set<String> VALID_ORDER_TYPES = Set.of("BUY", "SELL");


    @PostMapping("/app/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) {
        String email = authentication.getName();


        if (!VALID_ORDER_TYPES.contains(orderDTO.getOrderType())) {
            return ResponseEntity.badRequest().body("Invalid orderType. Must be BUY or SELL.");
        }


        if (orderDTO.getPrice() == null || orderDTO.getPrice() <= 0) {
            return ResponseEntity.badRequest().body("Price must be greater than 0.");
        }

        if (orderDTO.getAmount() == null || orderDTO.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than 0.");
        }

        try {
            Order createdOrder = orderService.createOrder(orderDTO, email).get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }
    }
    @GetMapping("/app/top-buy")
    public ResponseEntity<?> getTop10BuyOrders(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Order> orders = orderService.getTop10BuyOrdersByUser(email).get();
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("No buy orders found.");
            }
            if (orders.size() < 10) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Less than 10 buy orders found", "orders", orders));
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
            if (orders.size() < 10) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Less than 10 sell orders found", "orders", orders));
            }
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }








}