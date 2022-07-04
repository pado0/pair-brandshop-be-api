package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.CategoryGetDto;
import minishop.project.dto.CategoryPostDto;
import minishop.project.entity.Category;
import minishop.project.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        return categories.stream().map(c -> new CategoryGetDto(c)).collect(Collectors.toList());


        // 이 코드를 넣으면 그제서야 들어감.
        // 지연로딩이라서 조회가 안되는 중이었음.
        //        categories.forEach(x -> categoryGetDtos.add(new CategoryGetDto(
//                x.getName(), x.getChild()
//        )));
//        for (CategoryGetDto categoryGetDto : categoryGetDtos) {
//            categoryGetDto.getChild().stream().forEach(c -> System.out.println("뭐지?: " + c));
//        }

        // chile에서 parent 조회하지 않도록 별도의 dto 처리
        //return categoryGetDtos;
    }
}
