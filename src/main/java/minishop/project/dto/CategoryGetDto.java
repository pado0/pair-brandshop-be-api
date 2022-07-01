package minishop.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import minishop.project.entity.Category;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryGetDto {
    private String name;
    //private List<Item> items; // todo : 여기서 item list 조회시 오류가 난다. 일대다 양방향 매핑 실정 2 이슈
    // private Category parent;
    private List<Category> child;
}
