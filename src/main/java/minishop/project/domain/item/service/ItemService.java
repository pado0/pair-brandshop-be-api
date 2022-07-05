package minishop.project.domain.item.service;

import minishop.project.domain.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    public void createItem(ItemDto itemDto);
    List<ItemDto> getAllItems();

    void deleteItem(Long itemId);
//    Long createItem
}
