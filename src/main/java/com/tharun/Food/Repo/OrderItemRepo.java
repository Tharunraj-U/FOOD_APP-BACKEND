package com.tharun.Food.Repo;

import com.tharun.Food.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    // Additional query methods (if any) can be defined here
}
