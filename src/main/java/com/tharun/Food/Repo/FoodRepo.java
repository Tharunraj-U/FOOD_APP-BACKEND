package com.tharun.Food.Repo;

import com.tharun.Food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {

    // Method to find foods by restaurant ID
    List<Food> findByRestaurantId(Long restaurantId);

    // Custom query to search for food items based on a keyword
    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Food> searchFood(@Param("keyword") String keyword);

    List<Food> findByNameContainingIgnoreCase(String keyword);
}
