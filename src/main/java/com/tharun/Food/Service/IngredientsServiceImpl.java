package com.tharun.Food.Service;

import com.tharun.Food.Repo.IngredientCategoryRepo;
import com.tharun.Food.Repo.IngredientItemRepo;
import com.tharun.Food.Service.InterfaceService.IngredientsService;
import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.model.IngredientCategory;
import com.tharun.Food.model.IngredientsItems;
import com.tharun.Food.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl  implements IngredientsService {
    @Autowired
    private IngredientItemRepo ingredientItemRepo;


    @Autowired
    private IngredientCategoryRepo ingredientCategoryRepo;


    @Autowired
    private RestaurantService restaurantService;


    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category=new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);
        return ingredientCategoryRepo.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optional=ingredientCategoryRepo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Ingredient category not found");
        }

        return optional.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantID(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepo.findByRestaurantId(id);
    }

    @Override
    public IngredientsItems createIngredientsItems(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

         Restaurant restaurant=restaurantService.getRestaurantByUserId(restaurantId);
         IngredientCategory ingredient=findIngredientCategoryById(categoryId);
         IngredientsItems items=new IngredientsItems();
         items.setName(ingredientName);
         items.setRestaurant(restaurant);
         items.setCategory(ingredient);

         IngredientsItems ingredientsItems=ingredientItemRepo.save(items);
         ingredient.getIngredients().add(ingredientsItems);
        return ingredientsItems;
    }

    @Override
    public List<IngredientsItems> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItems updateStock(Long id) throws Exception {


        Optional<IngredientsItems> ingredientsItems=ingredientItemRepo.findById(id);
        if(ingredientsItems.isEmpty()){
            throw  new Exception("Ingredients Not Found");
        }

        IngredientsItems items=ingredientsItems.get();
        items.setInStoke(!items.isInStoke());
        return ingredientItemRepo.save(items);
    }
}
