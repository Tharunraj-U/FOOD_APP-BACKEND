package com.tharun.Food.Service.InterfaceService;

import com.tharun.Food.model.Category;
import com.tharun.Food.model.Food;
import com.tharun.Food.model.Restaurant;
import com.tharun.Food.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
    void deleteFood(Long foodId)throws  Exception;
    public List<Food> getRestaurantsFood(Long restaurantId,boolean isVegitarian,boolean isNonVeg,boolean isSeasonal,String foodCategory);

    public  List<Food> searchFood(String keyword);
    public  Food findFoodById(Long foodId)throws  Exception;
    public  Food updateAvailability(Long foodId)throws  Exception;
}
