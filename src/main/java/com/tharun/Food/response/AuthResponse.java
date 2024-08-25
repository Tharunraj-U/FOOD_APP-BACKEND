package com.tharun.Food.response;

import com.tharun.Food.model.User_Role;
import lombok.Data;

import java.net.UnknownServiceException;
import java.security.PrivateKey;
@Data
public class AuthResponse {
    private String jwt;
    private  String  message;
    private User_Role role;
}
