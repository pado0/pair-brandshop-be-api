package minishop.project.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import minishop.project.domain.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryGetDto {
    private String name;
    //private List<Item> items;
    // private Category parent;
    private List<CategoryGetDto> child;

    public CategoryGetDto(Category category) {
        name = category.getName();
        child = category.getChild().stream().map(c -> new CategoryGetDto(c)).collect(Collectors.toList());
    }
}
