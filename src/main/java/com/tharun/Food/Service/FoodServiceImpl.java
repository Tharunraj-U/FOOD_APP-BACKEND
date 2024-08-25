package com.tharun.Food.Service;

import com.tharun.Food.Repo.FoodRepo;
import com.tharun.Food.Service.InterfaceService.FoodService;
import com.tharun.Food.model.Category;
import com.tharun.Food.model.Food;
import com.tharun.Food.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tharun.Food.request.CreateFoodRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepo foodRepo;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setName(req.getName());
        food.setFoodCategory(category);
        food.setDescription(req.getDescription());
        food.setSeasonal(req.isSeasonal());
        food.setRestaurant(restaurant);
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        food.setVegetarian(req.isVegetarian());
        Food savedFood = foodRepo.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        if (!foodRepo.existsById(foodId)) {
            throw new Exception("Food item not found");
        }
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepo.delete(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        // Retrieve all food items for the given restaurant ID
        List<Food> foods = foodRepo.findByRestaurantId(restaurantId);

        // Filter by vegetarian
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }

        // Filter by non-vegetarian
        if (isNonVeg) {
            foods = filterByNonVeg(foods, isNonVeg);
        }

        // Filter by seasonal
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        // Filter by food category
        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepo.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        return foodRepo.findById(foodId)
                .orElseThrow(() -> new Exception("Food item not found"));
    }

    @Override
    public Food updateAvailability(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable()); // Toggle availability
        return foodRepo.save(food);
    }
}
