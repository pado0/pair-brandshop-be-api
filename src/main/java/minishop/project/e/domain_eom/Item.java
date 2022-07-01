package minishop.project.e.domain_eom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.exception_eom.NotEnoughStockException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    //카테고리

    //하트 카운트?

    //주문 생성 재고 다운
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    //주문 취소 재고 업
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public static Item createItem(ItemDto itemDto){
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setPrice(itemDto.getPrice());
        item.setItemName(itemDto.getItemName());
        item.setStockQuantity(itemDto.getStockQuantity());
        return item;
    }
}