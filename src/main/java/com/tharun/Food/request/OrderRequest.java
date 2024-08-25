package com.tharun.Food.request;

import com.tharun.Food.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address  deliveryAddress;
}
