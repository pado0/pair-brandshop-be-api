package minishop.project.e.service_eom;

import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.repository_eom.ItemRepository;
import minishop.project.e.repository_eom.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


}
