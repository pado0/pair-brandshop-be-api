package minishop.project.api;


import lombok.RequiredArgsConstructor;
import minishop.project.api.response.Result;
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
    public String createItem(@RequestBody ItemDto itemDto){
        itemService.createItem(itemDto);
        return "Product creation completed";
    }

    //모든 Item 조회
    @GetMapping("/v1/items")
    public Result findAllItems() {

        List<ItemDto> allItems = itemService.getAllItems();
        return new Result<>(allItems, allItems.size());

    }

    //좋아요
    @PostMapping("/v1/items/{itemId}/like")
    public String upLike(@PathVariable("itemId") Long itemId){
        return likeService.pushLike(itemId);
    }

    //Item 삭제
    @DeleteMapping("/v1/items/{itemId}/delete")
    public void deleteItem(@PathVariable("itemId")Long itemId){
        itemService.deleteItem(itemId);
    }


}
