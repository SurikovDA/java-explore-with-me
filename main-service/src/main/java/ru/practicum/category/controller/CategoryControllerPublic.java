package ru.practicum.category.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/categories")
public class CategoryControllerPublic {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAll(@RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                 @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        log.info("Получен запрос GET /categories?from={}&size={}", from, size);
        return categoryService.findAllCategory(from, size);
    }

    @GetMapping("/{catId}")
    public Category getCategoryById(@PathVariable Long catId) {
        return categoryService.getById(catId);
    }
}
