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
import java.util.Optional;
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
                s -> new ItemDto(s.getId(),
                                    s.getItemName(),
                                    s.getPrice(),
                                    s.getStockQuantity(),
                                    likeRepository.countByItem(s))
        ).collect(Collectors.toList());

        return itemList;
    }

    @Override
    public void deleteItem(Long itemId) {

        //영속상태가됨
        Optional<Item> findItem = itemRepository.findById(itemId);
        if(!findItem.isPresent()){
            throw new IllegalStateException("존재하는 상품이 없습니다");
        }
        //영속상태라 변경감지로 실행됨
        findItem.get().setItemStatus(ItemStatus.discontinued);

    }
}
