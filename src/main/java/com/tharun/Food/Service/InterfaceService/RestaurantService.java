package com.tharun.Food.Service.InterfaceService;

import com.tharun.Food.dto.RestaurantDto;
import com.tharun.Food.model.Restaurant;
import com.tharun.Food.model.User;
import com.tharun.Food.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant  createRestaurant(CreateRestaurantRequest req , User user);

    public  Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws  Exception;

    public  void deleteRestaurant(Long restaurantId)throws  Exception;

    public List<Restaurant>  getAllRestaurant();

    public  List<Restaurant> searchRestaurant(String keyword);

    public  Restaurant findRestaurantById(Long id)throws  Exception;
    public  Restaurant getRestaurantByUserId(Long userId)throws  Exception;

    public RestaurantDto addToFavorites(long restaurantId,User user)throws  Exception;

    public  Restaurant updateRestaurantStatus(Long id)throws  Exception;
}
