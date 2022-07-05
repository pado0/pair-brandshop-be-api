package minishop.project.domain.order.service;

import minishop.project.domain.order.Order;
import minishop.project.domain.item.dto.ItemDto;

import java.util.List;

public interface OrderService {

    void createOrder(List<ItemDto> items);

    List<Order> getAllOrders();

    void cancelOrder(Long orderId);

    long findToTalPrice();
}
