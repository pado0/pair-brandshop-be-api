package minishop.project.api;

import lombok.RequiredArgsConstructor;
import minishop.project.api.response.CommonResult;
import minishop.project.domain.category.dto.CategoryGetDto;
import minishop.project.domain.category.dto.CategoryPostDto;
import minishop.project.domain.category.entity.Category;
import minishop.project.domain.category.service.CategoryService;
import minishop.project.domain.common.ResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CategoryController {
    private final CategoryService categoryService;
    private final ResponseService responseService;

    @PostMapping("/category")
    public CommonResult joinCategory(@RequestBody CategoryPostDto categoryPostDto){

        Category category = new Category();
        category.setName(categoryPostDto.getName());
        Long parentCategoryId = categoryPostDto.getParentCategoryId();

        if(categoryService.saveCategory(category, parentCategoryId) == null){
            return responseService.getFailResult(9998, "존재하지 않는 부모 카테고리 id입니다.");
        }

        return responseService.getSuccessResult();
    }

    @GetMapping("/category")
    public List<CategoryGetDto> findALlCategory() {
        List<Category> categories = categoryService.findAllCategories();
        return categories.stream().map(CategoryGetDto::new).collect(Collectors.toList());
    }

}
