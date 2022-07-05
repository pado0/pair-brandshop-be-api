package minishop.project.api;


import lombok.RequiredArgsConstructor;
import minishop.project.domain.item.dto.ItemDto;
import minishop.project.domain.item.service.ItemService;
import minishop.project.domain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;
    private final LikeService likeService;


    @PostMapping("/v1/items")
    public void createItem(@RequestBody ItemDto itemDto){
        itemService.createItem(itemDto);
    }
    @GetMapping("/v1/items")
    public List<ItemDto> findAllItems(){
        return itemService.getAllItems();
    }
    @PostMapping("/v1/items/{itemId}/like")
    public void upLike(@PathVariable("itemId") Long itemId){
        likeService.pushLike(itemId);
    }

    @PostMapping("/v1/items/{itemId}/delete")
    public void deleteItem(@PathVariable("itemId")Long itemId){
        itemService.deleteItem(itemId);
    }


}
