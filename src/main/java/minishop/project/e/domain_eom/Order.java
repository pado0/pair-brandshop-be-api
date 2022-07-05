package minishop.project.e.domain_eom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import minishop.project.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch=LAZY, mappedBy = "order", cascade=CascadeType.ALL) //Order 저장시, OrderItem 자동 저장
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //상태 3가지 체크


    /*
    * 주문 생성시 필요한 연관 메서드
    */

    // Order -> OrderItem 생성
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); //Order의 OrderItem에 추가
        orderItem.setOrder(this);  //OrderItem의 Order에 Order 저장
    }

    //멤버 셋팅
    public void setMember(Member member){
        this.member= member;
        member.getOrders().add(this);
    }

    //주문 생성을 --> OrderItem에서 처리


    //비즈니스 로직
    //주문취소
    public void cancel() {

        if (getStatus() == OrderStatus.COMPLETION) { // 배송완료
            //RunTimeException
            throw new IllegalStateException("이미 배송완료된 상품입니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
