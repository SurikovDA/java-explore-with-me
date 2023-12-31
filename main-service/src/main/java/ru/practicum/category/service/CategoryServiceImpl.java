package ru.practicum.category.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.EntityNotFoundException;

import static ru.practicum.category.mapper.CategoryMapper.toCategoriesPage;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createNewCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDto.getName());
        if (optionalCategory.isPresent()) {
            throw new ConflictException("Имя уже занято");
        }
        log.info("Категория {}, передана на сохранение", categoryDto.getName());
        return categoryRepository.save(CategoryMapper.toCategory(categoryDto));
    }

    @Override
    @Transactional
    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
        log.info("Категория id = {} удалена.", id);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDto.getName());
        if (optionalCategory.isPresent() && !Objects.equals(optionalCategory.get().getId(), category.getId())) {
            throw new ConflictException("Имя уже занято");
        }
        category.setName(categoryDto.getName());
        log.info("Данные категории c id = {} переданы на обновление.", id);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategory(int from, int size) {
        int page = 0;
        if (from != 0) {
            page = from / size;
        }
        log.info("Получен список всех категорий.");
        return toCategoriesPage(categoryRepository.findAll(PageRequest.of(page, size)));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
    }
}