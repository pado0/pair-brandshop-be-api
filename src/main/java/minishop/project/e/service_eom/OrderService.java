package minishop.project.e.service_eom;

import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Order;
import minishop.project.e.dto_eom.ItemDto;

import java.util.List;

public interface OrderService {

    void createOrder(List<ItemDto> items);

    Order getOrderByOrderId(Long orderId);

    List<Order> getOrdersByUserId(String userId);

    List<Order> getAllOrders();

}
