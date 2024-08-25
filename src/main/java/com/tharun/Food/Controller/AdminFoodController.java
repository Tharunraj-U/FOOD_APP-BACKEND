package com.tharun.Food.Controller;

import com.tharun.Food.Service.InterfaceService.FoodService;
import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.Service.InterfaceService.UserService;
import com.tharun.Food.model.Food;
import com.tharun.Food.model.Restaurant;
import com.tharun.Food.model.User;
import com.tharun.Food.request.CreateFoodRequest;
import com.tharun.Food.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse("Food deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailability(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
