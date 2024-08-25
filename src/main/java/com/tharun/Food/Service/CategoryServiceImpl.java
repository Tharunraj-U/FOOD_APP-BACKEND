package com.tharun.Food.Service;

import com.tharun.Food.Repo.CategoryRepo;
import com.tharun.Food.Service.InterfaceService.CategoryService;
import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.model.Category;
import com.tharun.Food.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;


    @Autowired
    private RestaurantService restaurantService;




    @Override
    public Category createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);

        Category category=new Category();
        category.setName(name);
        category.setId(restaurant.getId());
        category.setRestaurant(restaurant);
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantID(Long id) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(id);
        return categoryRepo.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory=categoryRepo.findById(id);
        if(optionalCategory.isEmpty()){
            throw new Exception(("category Not found"));
        }
        return optionalCategory.get();
    }
}
