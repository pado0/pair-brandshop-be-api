package minishop.project.e.service_eom;

import minishop.project.e.dto_eom.ItemDto;

import java.util.List;

public interface ItemService {

    public void createItem(ItemDto itemDto);
    List<ItemDto> getAllItems();

    void deleteItem(Long itemId);
//    Long createItem
}
