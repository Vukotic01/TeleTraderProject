package com.example.projectteletrader.Repository;
import com.example.projectteletrader.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderTypeAndUserEmailOrderByPriceDesc(String orderType, String email, Pageable pageable);

    List<Order> findByOrderTypeAndUserEmailOrderByPriceAsc(String orderType, String email, Pageable pageable);



}