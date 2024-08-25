package com.tharun.Food.Controller;

import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.Service.InterfaceService.UserService;
import com.tharun.Food.model.Restaurant;
import com.tharun.Food.model.User;
import com.tharun.Food.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tharun.Food.request.CreateRestaurantRequest;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.createRestaurant(request,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurant(id,request);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant( @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
      restaurantService.deleteRestaurant(id);
        MessageResponse response=new MessageResponse();
        response.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus( @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId( @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
