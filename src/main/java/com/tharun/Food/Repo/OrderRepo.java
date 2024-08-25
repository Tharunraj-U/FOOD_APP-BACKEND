package com.tharun.Food.Repo;

import com.tharun.Food.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    public List<Order> findByCustomerId(Long userId);

    public  List<Order> findByRestaurantId(Long restaurantId);
}
