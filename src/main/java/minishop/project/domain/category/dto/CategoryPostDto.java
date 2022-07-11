package minishop.project.domain.category.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CategoryPostDto {

    private String name;

    @Nullable
    private Long parentCategoryId;
}
