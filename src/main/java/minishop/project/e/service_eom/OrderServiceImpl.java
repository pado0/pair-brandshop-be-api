package minishop.project.e.service_eom;

import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Order;
import minishop.project.e.domain_eom.OrderItem;
import minishop.project.e.domain_eom.OrderStatus;
import minishop.project.e.repository_eom.ItemRepository;
import minishop.project.e.repository_eom.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements  OrderService{

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    public void createOrder(List<Item> items) {

        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        for (Item item : items) {

            Item findItem = itemRepository.findById(item.getId()).get();
            findItem.removeStock(item.getStockQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setCount(item.getStockQuantity());
            orderItem.setItem(findItem);
            orderItem.setOrder(order);
            orderItem.setOrderPrice(findItem.getPrice());
            order.addOrderItem(orderItem);
        }
        orderRepository.save(order);
    }

    public Order getOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId).get();
//        return orderRepository.findById(orderId).orElse(Order::new); --> todo 널처리
    }

    public List<Order> getOrdersByUserId(String userId) {
        return  null;
    }

    public List<Order> getAllOrders() {


        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            System.out.println("order.getId() = " + order.getId());
        }
        for (Order order : orders) {
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(a -> a.getItem().getItemName()); //getItem()이 아니라, getName()에서 초기화됨 ㅎㅎ

        }
        return orders;
    }
}
