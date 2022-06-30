package minishop.project.e.api_eom;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Order;
import minishop.project.e.domain_eom.OrderItem;
import minishop.project.e.service_eom.OrderJpaRepository;
import minishop.project.e.service_eom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final OrderJpaRepository orderJpaRepository;

    //주문 생성
    @PostMapping("/api/v1/orders")
    public void createOrder(@RequestBody List<Item> list){
        orderService.createOrder(list);
    }

    //주문 전체 조회
    @GetMapping("/api/v1/orders")
    public List<OrderDto> getOrders(){
        List<Order> orders = orderJpaRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }


    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private List<OrderItemDto> orderItems; //List<OrdetItem> 이라고 하면 안됨. -> 이거 entity 노출임 !!!!

        public OrderDto(Order order) {
            orderId = order.getId();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getItemName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }


}
