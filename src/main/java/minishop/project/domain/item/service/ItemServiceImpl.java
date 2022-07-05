package minishop.project.domain.item.service;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.item.entity.Item;
import minishop.project.domain.item.entity.ItemStatus;
import minishop.project.domain.item.dto.ItemDto;
import minishop.project.domain.item.repository.ItemRepository;
import minishop.project.domain.like.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
//public class ItemServiceImpl implements ItemService{
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final LikeRepository likeRepository;

    @Override
    public void createItem(ItemDto itemDto){
        Item item = Item.createItem(itemDto);
        itemRepository.save(item);
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();

        List<ItemDto> itemList = items.stream().map(
                s -> new ItemDto(s.getId(), s.getItemName(), s.getPrice(), s.getStockQuantity(), likeRepository.countByItem(s))
        ).collect(Collectors.toList());

//        List<ItemDto> allItems =
//                items.stream().map(s ->
//                        new ItemDto(s.getId(), s.getItemName(), s.getPrice(), s.getStockQuantity(),))
//                                        .collect(Collectors.toList());
        return itemList;
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        item.setItemStatus(ItemStatus.discontinued);

    }
}
