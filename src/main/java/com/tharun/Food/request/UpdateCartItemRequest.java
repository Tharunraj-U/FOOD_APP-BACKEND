package com.tharun.Food.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {


    private  Long cartItemId;
    private int quantity;

}
