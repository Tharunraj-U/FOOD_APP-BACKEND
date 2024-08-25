package com.tharun.Food.Controller;

import com.tharun.Food.Service.InterfaceService.CartService;
import com.tharun.Food.model.Cart;
import com.tharun.Food.model.CartItem;
import com.tharun.Food.request.AddCartItemRequest;
import com.tharun.Food.request.UpdateCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.findCartByUserId(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
