package minishop.project.e.domain_eom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="orders") //order 예약어라
@Data
@NoArgsConstructor
public class Order extends JpaBaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

//    @JsonIgnore
    @OneToMany(fetch=LAZY, mappedBy = "order", cascade=CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //상태 3가지 체크

    //연관 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    //1 멤버 셋팅

    //2 orderItem 저장

    //주문생성메서드

    //주문취소
    //==생성 메서드==//
//    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
//        OrderItem orderItem = new OrderItem();
//        orderItem.setItem(item);
//        orderItem.setOrderPrice(orderPrice);
//        orderItem.setCount(count);
//        item.removeStock(count);
//        return orderItem;
//    }

}
