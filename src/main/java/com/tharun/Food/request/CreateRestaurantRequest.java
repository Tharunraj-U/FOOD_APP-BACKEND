package com.tharun.Food.request;


import com.tharun.Food.model.Address;
import com.tharun.Food.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

    private String cuisineType;
}
