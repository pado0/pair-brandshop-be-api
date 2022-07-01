package minishop.project.e.service_eom;

import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Order;
import minishop.project.e.domain_eom.OrderItem;
import minishop.project.e.domain_eom.OrderStatus;
import minishop.project.e.dto_eom.ItemDto;
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
    public void createOrder(List<ItemDto> items){

        //주문 생성 및 배송상태 설정
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.ORDER);

        //선택한 상품, OrderItem생성 및 연관 관계 셋팅
        for (ItemDto itemDto : items) {
            //일단, 정확한 값만을 넘어온다고 가정(잘못된 Item_id 처리해줘야함 )
            Item findItem = itemRepository.findById(itemDto.getId()).get();

            //아래 메서드에서 Order와 OrderItem 관게 전부해줌
            OrderItem.createOrderItem(findItem, newOrder, itemDto.getCount());

        }
        orderRepository.save(newOrder);
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
