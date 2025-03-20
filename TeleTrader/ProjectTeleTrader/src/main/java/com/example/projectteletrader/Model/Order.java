package com.example.projectteletrader.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double price;
    private Double amount;
    private String orderType; // "BUY" or "SELL"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;
}