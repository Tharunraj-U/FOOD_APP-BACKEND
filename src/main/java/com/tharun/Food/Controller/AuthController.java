package com.tharun.Food.Controller;

import com.tharun.Food.Repo.CartRepo;
import com.tharun.Food.Repo.UserRepository;
import com.tharun.Food.Service.CustomerUserDetailsService;
import com.tharun.Food.config.JwtProvider;
import com.tharun.Food.model.Cart;
import com.tharun.Food.model.User;
import com.tharun.Food.model.User_Role;
import com.tharun.Food.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.tharun.Food.request.LoginRequest;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepo cartRepo;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        // Check if the email is already registered
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email is already used in another account");
        }

        // Create new user and set its attributes
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullname(user.getFullname());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(createdUser);

        // Create a cart for the newly registered user
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepo.save(cart);

        // Authenticate the user and generate a JWT token
        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        String jwt = jwtProvider.generateToken(authentication);

        // Prepare the response with JWT token and success message
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registration successful");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest request) {
        // Authenticate the user and generate a JWT token
        Authentication authentication = authenticate(request.getEmail(), request.getPassword());
        String jwt = jwtProvider.generateToken(authentication);

        // Extract user role from the authenticated user
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        // Prepare the response with JWT token and success message
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login successful");
        try {
            authResponse.setRole(User_Role.valueOf(role));
        } catch (IllegalArgumentException e) {
            authResponse.setRole(null); // Handle unexpected roles gracefully
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        // Load user details using the custom user details service
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username.");
        }

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        // Return authenticated user with authorities
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
