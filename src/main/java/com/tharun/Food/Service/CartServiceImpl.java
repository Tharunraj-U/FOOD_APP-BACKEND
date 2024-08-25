package com.tharun.Food.Service;

import com.tharun.Food.Repo.CartItemRepo;
import com.tharun.Food.Repo.CartRepo;
import com.tharun.Food.Service.InterfaceService.CartService;
import com.tharun.Food.Service.InterfaceService.FoodService;
import com.tharun.Food.Service.InterfaceService.UserService;
import com.tharun.Food.model.Cart;
import com.tharun.Food.model.CartItem;
import com.tharun.Food.model.Food;
import com.tharun.Food.model.User;
import com.tharun.Food.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Transactional
    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());

        Cart cart = cartRepo.findByCustomerId(user.getId());  // Updated method name
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(user);  // Updated to match field name
            cart = cartRepo.save(cart);
        }

        for (CartItem cartItem : cart.getItem()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity() * food.getPrice());
        CartItem savedCartItem = cartItemRepo.save(newCartItem);
        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Transactional
    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepo.save(item);
    }

    @Transactional
    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepo.findByCustomerId(user.getId());  // Updated method name
        if (cart == null) {
            throw new Exception("Cart not found for user ID: " + user.getId());
        }

        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItemOptional.get();
        cart.getItem().remove(item);
        return cartRepo.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getItem()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optional = cartRepo.findById(id);
        optional.get().setTotal(calculateCartTotals(optional.get()));
        if (optional.isEmpty()) {
            throw new Exception("Cart not found with ID: " + id);
        }
        return optional.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepo.findByCustomerId(user.getId());  // Updated method name
        if (cart == null) {
            throw new Exception("Cart not found for user ID: " + user.getId());
        }
        return cart;
    }

    @Transactional
    @Override
    public Cart clearCart(String jwt) throws Exception {
        Cart cart = findCartByUserId(jwt);
        cart.getItem().clear();
        return cartRepo.save(cart);
    }
}
