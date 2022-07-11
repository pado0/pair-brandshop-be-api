package minishop.project.domain.item.entity;


import minishop.project.domain.item.dto.ItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // 스프링 빈으로 등록됨
public interface ItemMapper {

    Item itemDtoToItem(ItemDto itemDto);
}
