package minishop.project.domain.order.service;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.item.entity.Item;
import minishop.project.domain.item.entity.ItemStatus;
import minishop.project.domain.item.service.ItemService;
import minishop.project.domain.order.Order;
import minishop.project.domain.order.OrderItem;
import minishop.project.domain.order.OrderStatus;
import minishop.project.domain.item.dto.ItemDto;
import minishop.project.domain.item.repository.ItemRepository;
import minishop.project.domain.order.repository.OrderRepository;
import minishop.project.domain.member.entity.Member;
import minishop.project.exception.CUserNotFoundException;
import minishop.project.domain.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements  OrderService{

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ItemService itemService;

    //CREATE
    @Override
    public void createOrder(List<ItemDto> items){

        //회원 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Member member = memberRepository.findByLoginEmail(id).orElseThrow(CUserNotFoundException::new);

        //주문 생성 및 배송상태 설정
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.ORDER);
        newOrder.setMember(member);

        //선택한 상품, OrderItem생성 및 연관 관계 셋팅
        for (ItemDto itemDto : items) {
            //일단, 정확한 값만을 넘어온다고 가정(잘못된 Item_id 처리해줘야함 )
            Item findItem = itemRepository.findById(itemDto.getId()).get();

            //아래 메서드에서 Order와 OrderItem 관게 전부해줌
            //해당 Item이 판매중이면 실행
            if(findItem.getItemStatus()== ItemStatus.SELL){
                OrderItem.createOrderItem(findItem, newOrder, itemDto.getCount());
            }
        }
        orderRepository.save(newOrder);
    }

    //SELECT
    //존재하는 모든 주문 조회 (회원 구분X)
    @Override
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

    //로그인 회원 주문 조회

    //주문 취소
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }

    @Override
    public long findToTalPrice() {
        long sum = 0 ;
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            if(order.getStatus()!=OrderStatus.CANCEL){
                sum+=order.getTotalPrice();
            }
        }
        return sum ;
    }
}
