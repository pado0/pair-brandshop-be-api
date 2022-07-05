package minishop.project.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import minishop.project.domain.item.entity.Item;

import javax.persistence.*;

@Entity
@Data
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    @JsonIgnore //연쇄적인 호출을 피하기 위해
    private Order order;

    private int orderPrice; // 주문 가격 ( 수량 포함된 계산 가격임)
    private int count;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    /** Order에서  주문 취소시 실행  */
    public void cancel() {
        //제고 수량 업시킴
        this.status=OrderStatus.CANCEL;
        getItem().addStock(count);
    }

    //==생성 메서드==//
    public static void createOrderItem(Item item, Order order, int count) {

        //1 생성
        OrderItem orderItem = new OrderItem();

        //2 기본값 셋팅
        orderItem.setStatus(OrderStatus.ORDER);
        orderItem.setItem(item);
        orderItem.setCount(count);
        item.removeStock(count);
        orderItem.setOrderPrice(item.getPrice()*count);

        //3 연관 관계 셋팅
        order.addOrderItem(orderItem);

    }

    public int getTotalPrice() {
        return orderPrice;
    }


    // 조회 로직


}
