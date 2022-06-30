package minishop.project.e.api_eom;


import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.dto_eom.ItemDto;
import minishop.project.e.service_eom.ItemService;
import minishop.project.e.service_eom.ItemServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemServiceImpl itemService;

    @PostMapping("/api/v1/items")
    public void createItem(@RequestBody ItemDto itemDto){
        itemService.createItem(itemDto);
    }
    @GetMapping("/api/v1/items")
    public List<ItemDto> findAllItems(){
        return itemService.getAllItems();
    }

}
