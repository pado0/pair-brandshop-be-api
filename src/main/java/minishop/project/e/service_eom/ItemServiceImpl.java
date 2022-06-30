package minishop.project.e.service_eom;

import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.repository_eom.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
//public class ItemServiceImpl implements ItemService{
public class ItemServiceImpl {

    private final ItemRepository itemRepository;

    public void createItem(ItemDto itemDto){
        Item item = Item.createItem(itemDto);
        itemRepository.save(item);
    }

    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> allItems = items.stream()
                                        .map(s ->
                                                new ItemDto(s.getId(), s.getItemName(), s.getPrice(), s.getStockQuantity()))
                                        .collect(Collectors.toList());
        return allItems;
    }

}
