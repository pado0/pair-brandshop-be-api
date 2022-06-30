package minishop.project.e.service_eom;

import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Order;

import java.util.List;

public interface OrderService {

    void createOrder(List<Item> items);
    Order getOrderByOrderId(Long orderId);

    List<Order> getOrdersByUserId(String userId);

    List<Order> getAllOrders();
}
