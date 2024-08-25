package com.tharun.Food.Repo;

import com.tharun.Food.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_CustomerId(Long customerId);  // Use 'Cart_CustomerId' to reference the correct fields
}
