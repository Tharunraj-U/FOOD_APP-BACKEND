package com.tharun.Food.Service;

import com.tharun.Food.Repo.UserRepository;
import com.tharun.Food.Service.InterfaceService.UserService;
import com.tharun.Food.config.JwtProvider;
import com.tharun.Food.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
     private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String  email=jwtProvider.getEmailFromJwtToken(jwt);
        User user=findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User Not Found");
        }
        return user;
    }
}
