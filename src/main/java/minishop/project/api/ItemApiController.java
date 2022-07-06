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


    //Item 생성
    @PostMapping("/v1/items")
    public void createItem(@RequestBody ItemDto itemDto){
        itemService.createItem(itemDto);
    }

    //모든 Item 조회
    @GetMapping("/v1/items")
    public List<ItemDto> findAllItems(){
        return itemService.getAllItems();
    }

    //좋아요
    @PostMapping("/v1/items/{itemId}/like")
    public void upLike(@PathVariable("itemId") Long itemId){
        likeService.pushLike(itemId);
    }

    //Item 삭제
    @DeleteMapping("/v1/items/{itemId}/delete")
    public void deleteItem(@PathVariable("itemId")Long itemId){
        itemService.deleteItem(itemId);
    }


}
