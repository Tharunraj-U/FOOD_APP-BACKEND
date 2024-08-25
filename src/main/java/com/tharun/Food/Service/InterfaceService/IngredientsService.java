package com.tharun.Food.Service.InterfaceService;

import com.tharun.Food.model.IngredientCategory;
import com.tharun.Food.model.IngredientsItems;

import java.util.List;

public interface IngredientsService {




    public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws  Exception;
    public  IngredientCategory findIngredientCategoryById(Long id)throws  Exception;
    public List<IngredientCategory> findIngredientCategoryByRestaurantID(Long id)throws  Exception;
    public  IngredientsItems createIngredientsItems(Long restaurantId,String ingredientName,Long categoryId) throws  Exception;
    public  List<IngredientsItems> findRestaurantsIngredients(Long restaurantId);
    public IngredientsItems updateStock(Long id) throws  Exception;
}
