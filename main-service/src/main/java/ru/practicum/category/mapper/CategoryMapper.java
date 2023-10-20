package ru.practicum.category.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CategoryMapper {
    public static Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .build();
    }

    public static List<Category> toCategoriesPageDto(Page<Category> categories) {
        return categories.stream().collect(Collectors.toList());
    }
}
