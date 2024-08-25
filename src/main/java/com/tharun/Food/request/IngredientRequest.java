package com.tharun.Food.request;

import lombok.Data;

@Data
public class IngredientRequest {


    private  String name;
   private  Long categoryid;
    private  Long restaurantId;


}
