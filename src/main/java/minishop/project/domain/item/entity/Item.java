package minishop.project.domain.item.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import minishop.project.domain.common.JpaBaseEntity;
import minishop.project.domain.like.entity.Like;
import minishop.project.domain.item.dto.ItemDto;
import minishop.project.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Item extends JpaBaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private int price;
    private int stockQuantity;

    //좋아요
    //Item - 부모 @OneTOMany
    //Like - 자식 @ManyToOne (FK 위치 - 주인)
    //Item 삭제시 -> Like 전파 삭제 -> Cascade

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE) // Item 삭제시 좋아요 전파 삭제됨
    private List<Like> likes = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    //카테고리

    //하트 카운트

    //주문 생성시, 재고 다운
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    //주문 취소시, 재고 업
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // todo : mapper 적용해보기
    //아이템 생성,
    public static Item createItem(ItemDto itemDto){
        Item item = new Item();
        item.setItemStatus(ItemStatus.SELL);
        item.setPrice(itemDto.getPrice());
        item.setItemName(itemDto.getItemName());
        item.setStockQuantity(itemDto.getCount());
        return item;
    }
}
