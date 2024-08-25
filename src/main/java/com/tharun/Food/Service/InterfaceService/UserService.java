package com.tharun.Food.Service.InterfaceService;

import com.tharun.Food.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt)throws Exception;

    public User findUserByEmail(String email)throws  Exception;
}
