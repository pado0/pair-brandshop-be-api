package minishop.project.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import minishop.project.api.response.FindOrderByMemberResult;
import minishop.project.api.response.Result;
import minishop.project.api.response.SingleResult;
import minishop.project.domain.order.entity.Order;
import minishop.project.domain.order.entity.OrderItem;
import minishop.project.domain.item.dto.ItemDto;
import minishop.project.domain.order.repository.OrderJpaRepository;
import minishop.project.domain.order.repository.OrderRepository;
import minishop.project.domain.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final OrderJpaRepository orderJpaRepository;


    //주문 CREATE
    @PostMapping("/v1/orders")
    public String createOrder(@RequestBody List<ItemDto> list){
        orderService.createOrder(list);
        return "Order Creation Completed";
    }

    //주문 SELECT1
    //v1 : 컬렉션 관계 - fetch join 활용, Query 1개로 해결
    //페이징 안됨
    @GetMapping("/v1/orders")
    public Result getOrdersV1(){
        //주문 전체 조회 (fetch join)
        List<Order> orders = orderJpaRepository.findAllWithItemV1();
        //Dto로 변환
        List<OrderDto> orderDtos = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return new Result(orderDtos,orderDtos.size());
    }

    //주문 SELECT2
    //v2 : 컬렉셕 관계 - LAZY + hirbernate_defualt_batch_size 로 IN 쿼리 활용
    //toOne - fetch join 활용
    //페이징 됨
    @GetMapping("/v2/orders")
    public Result getOrdersV2(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "2") int limit){
        //주문 전체 조회 (fetch join)
        List<Order> orders = orderJpaRepository.findAllWithItemV2(offset,limit);

        // todo : 리턴 바로 치고 메소드 레퍼런스로 리팩토링 ->? what 메소드 레퍼런스?
        //Dto로 변환
        List<OrderDto> orderDtos = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return new Result(orderDtos,orderDtos.size());
    }

    //사용자별 조회 해보기
    @GetMapping("/v3/orders")
    public FindOrderByMemberResult getOrdersV3(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "2") int limit){

       return  orderService.findAllOrderByMember();

    }

    //주문 DELETE --> 상태 변경 cancel
    @DeleteMapping("/v1/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
    }

    //매출 총합
    @GetMapping("/v1/orders/totalprice")
    public long getTotalPrice(){
        return orderService.findToTalPrice();
    }


    /*
    * 하단부,
    * Order 전체 조회시 사용 하는 DTO
    * */
    @Data
    public static class OrderDto {
        private Long orderId;
        private List<OrderItemDto> orderItems; //List<OrdetItem> 이라고 하면 안됨. -> 이거 entity 노출임 !!!!

        public OrderDto(Order order) {
            orderId = order.getId();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }
    }

    @Data
    public static class OrderItemDto {
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
