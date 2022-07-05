package minishop.project.domain.category.dto;

import lombok.Data;

@Data
public class CategoryPostDto {

    private String name;
    private Long parentCategoryId;

}
