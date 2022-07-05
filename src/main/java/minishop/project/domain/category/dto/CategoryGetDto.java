package minishop.project.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import minishop.project.domain.category.Category;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryGetDto {
    private String name;
    //private List<Item> items; // todo : 여기서 item list 조회시 오류가 난다. 일대다 양방향 매핑 실정 2 이슈
    // private Category parent;
    private List<CategoryGetDto> child;

    public CategoryGetDto(Category category) {
        name = category.getName();
        child = category.getChild().stream().map(c -> new CategoryGetDto(c)).collect(Collectors.toList());
    }
}
