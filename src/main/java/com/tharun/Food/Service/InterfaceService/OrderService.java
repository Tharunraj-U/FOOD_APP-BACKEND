package com.tharun.Food.Service.InterfaceService;

import com.tharun.Food.model.Order;
import com.tharun.Food.model.User;
import com.tharun.Food.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest req, User user) throws Exception;
    public  Order updateOrder(Long orderId,String orderStatus)throws  Exception;
    public void cancelOrder(Long orderId)throws  Exception;
    public List<Order> getUsersOrder(Long userId)throws  Exception;
    public  List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus)throws  Exception;
    public  Order findOrderById(Long orderId)throws  Exception;
}
