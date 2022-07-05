package minishop.project.domain.category.service;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.category.entity.Category;
import minishop.project.domain.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveCategory(Category category, Long parentCategoryId) {
        Optional<Category> parentCategory = categoryRepository.findById(parentCategoryId);

        // parent 세팅해주기, parent의 child 세팅해주기.
        if (parentCategory.isPresent()) {
            category.setParent(parentCategory.get());
            parentCategory.get().addChildCategory(category);
        }

        categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
