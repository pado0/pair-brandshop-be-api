package minishop.project.e.domain_eom;

import lombok.Data;
import lombok.NoArgsConstructor;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.exception_eom.NotEnoughStockException;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Item extends JpaBaseEntity{

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private int price;
    private int stockQuantity;

    //좋아요
    @OneToMany(mappedBy = "item")
    private List<Like> like;


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

    //아이템 생성,
    public static Item createItem(ItemDto itemDto){
        Item item = new Item();
        item.setPrice(itemDto.getPrice());
        item.setItemName(itemDto.getItemName());
        item.setStockQuantity(itemDto.getCount());
        return item;
    }
}
