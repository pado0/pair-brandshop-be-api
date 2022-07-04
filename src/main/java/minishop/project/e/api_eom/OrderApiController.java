package minishop.project.e.api_eom;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Order;
import minishop.project.e.domain_eom.OrderItem;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.service_eom.OrderJpaRepository;
import minishop.project.e.service_eom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void createOrder(@RequestBody List<ItemDto> list){
        orderService.createOrder(list);
    }

    //주문 전체 조회
    //v1 : 컬렉션 관계 - fetch join 활용, Query 1개로 해결
    //페이징 안됨
    @GetMapping("/api/v1/orders")
    public List<OrderDto> getOrdersV1(){
        //주문 전체 조회 (fetch join)
        List<Order> orders = orderJpaRepository.findAllWithItemV1();
        //Dto로 변환
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    //주문 전체 조회
    //v2 : 컬렉셕 관계 - LAZY + hirbernate_defualt_batch_size 로 IN 쿼리 활용
    //toOne - fetch join 활용
    //페이징 됨
    @GetMapping("/api/v2/orders")
    public List<OrderDto> getOrdersV2(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "2") int limit){
        //주문 전체 조회 (fetch join)
        List<Order> orders = orderJpaRepository.findAllWithItemV2(offset,limit);
        //Dto로 변환
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }




    /*
    * 하단부,
    * Order 전체 조회시 사용 하는 DTO
    *
    * */
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
            itemName = orderItem.getItem().getItemName();  //Lazy 초기화
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }


}
