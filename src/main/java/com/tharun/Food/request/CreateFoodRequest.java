package com.tharun.Food.request;

import com.tharun.Food.model.Cart;
import com.tharun.Food.model.Category;
import com.tharun.Food.model.IngredientsItems;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private  String name;
    private  String  description;
    private  Long price;
    private Category category;
    private List<String > images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItems> ingredientsItems;



}
