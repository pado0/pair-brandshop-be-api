package minishop.project.e.api_eom;


import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.service_eom.ItemService;
import minishop.project.e.service_eom.ItemServiceImpl;
import minishop.project.e.service_eom.LikeService;
import minishop.project.e.service_eom.LikeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;
    private final LikeService likeService;


    @PostMapping("/api/v1/items")
    public void createItem(@RequestBody ItemDto itemDto){
        itemService.createItem(itemDto);
    }
    @GetMapping("/api/v1/items")
    public List<ItemDto> findAllItems(){
        return itemService.getAllItems();
    }
    @PostMapping("/api/v1/items/{itemId}/like")
    public void upLike(@PathVariable("itemId") Long itemId){
        likeService.upLike(itemId);
    }
    @DeleteMapping("/api/v1/items/{itemId}/like")
    public void downLike(@PathVariable("itemId") Long itemId){
        likeService.downLike(itemId);
    }

}
