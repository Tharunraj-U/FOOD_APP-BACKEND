package com.tharun.Food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tharun.Food.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullname;
    private  String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;
    private User_Role role=User_Role.ROLE_CUSTOMER;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders=new ArrayList<>();

    @ElementCollection

    private List<RestaurantDto> favorites=new ArrayList();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)//to delete all user address
    private List<Address> address=new ArrayList<>();
}
