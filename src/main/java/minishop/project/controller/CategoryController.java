package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.CategoryGetDto;
import minishop.project.dto.CategoryPostDto;
import minishop.project.entity.Category;
import minishop.project.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public String joinCategory(@RequestBody CategoryPostDto categoryPostDto){

        Category category = new Category();
        category.setName(categoryPostDto.getName());

        // todo : null 처리 필요. 현재는 입력된 카테고리 id가 db에 없을 경우 null, 있을 경우 정상 동작
        Long parentCategoryId = categoryPostDto.getParentCategoryId();
        categoryService.saveCategory(category, parentCategoryId);

        return "ok";
    }

    @GetMapping("/category")
    public List<CategoryGetDto> findALlCategory() {

        List<CategoryGetDto> categoryGetDtos = new ArrayList<>();
        List<Category> categories = categoryService.findAllCategories();

        categories.forEach(x -> categoryGetDtos.add(new CategoryGetDto(
                x.getName(), x.getChild()
        )));

        // chile에서 parent 조회하지 않도록 별도의 dto 처리
        return categoryGetDtos;
    }
}
