package com.tharun.Food.Repo;

import com.tharun.Food.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long id);  // Use 'CustomerId' to match the field in Cart
}
