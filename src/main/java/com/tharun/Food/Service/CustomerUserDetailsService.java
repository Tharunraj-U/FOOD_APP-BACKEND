package com.tharun.Food.Service;

import com.tharun.Food.Repo.UserRepository;
import com.tharun.Food.model.User;
import com.tharun.Food.model.User_Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        // Corrected condition
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        User_Role role = user.getRole();

        // Default role to ROLE_CUSTOMER if null
        if (role == null) {
            role = User_Role.ROLE_CUSTOMER;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        // Return UserDetails object
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
