package com.tharun.Food.Controller;

import com.tharun.Food.Service.InterfaceService.FoodService;
import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.Service.InterfaceService.UserService;
import com.tharun.Food.model.Food;
import com.tharun.Food.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian, @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg, @RequestParam(required = false) String foodCategory,
                                                        @PathVariable Long restaurantId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonveg, seasonal, foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
