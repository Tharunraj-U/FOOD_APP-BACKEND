package com.tharun.Food.Service;

import com.tharun.Food.Repo.AddressRepo;
import com.tharun.Food.Repo.RestaurantRepo;
import com.tharun.Food.Repo.UserRepository;
import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.dto.RestaurantDto;
import com.tharun.Food.model.Address;
import com.tharun.Food.model.Restaurant;
import com.tharun.Food.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tharun.Food.request.CreateRestaurantRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepo.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (updatedRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (updatedRestaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }

        return restaurantRepo.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepo.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findByQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepo.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = (Restaurant) restaurantRepo.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner id " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        boolean isFavorite = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto fav : favorites) {
            if (fav.getId() == restaurantId) {
                isFavorite = true;
                break;
            }
        }

        if (!isFavorite) {
            favorites.add(restaurantDto);
            user.setFavorites(favorites);
            userRepository.save(user);
        }

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepo.save(restaurant);
    }
}
