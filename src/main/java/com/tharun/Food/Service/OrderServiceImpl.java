package com.tharun.Food.Service;

import com.tharun.Food.Repo.AddressRepo;
import com.tharun.Food.Repo.OrderItemRepo;
import com.tharun.Food.Repo.OrderRepo;
import com.tharun.Food.Repo.UserRepository;
import com.tharun.Food.Service.InterfaceService.CartService;
import com.tharun.Food.Service.InterfaceService.OrderService;
import com.tharun.Food.Service.InterfaceService.RestaurantService;
import com.tharun.Food.model.*;
import com.tharun.Food.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {
        Address shippingAddress = req.getDeliveryAddress();
        Address savedAddress = addressRepo.save(shippingAddress);
        if (!user.getAddress().contains(savedAddress)) {
            user.getAddress().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartById(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItem()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            OrderItem savedOrderItem = orderItemRepo.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);
        Order savedOrder = orderRepo.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepo.save(order);
        }
        throw new Exception("PLEASE SELECT A VALID ORDER STATUS");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepo.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepo.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepo.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new Exception("Order not found");
        }
        return orderOptional.get();
    }
}
