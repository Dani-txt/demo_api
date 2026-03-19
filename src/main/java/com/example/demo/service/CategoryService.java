package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //Quizá se podría optimizar con @Awtowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

    public Optional<Category> partialUpdate(Long id, Category categoryUpdates) {
        return categoryRepository.findById(id).map(existingCategory -> {
            if (categoryUpdates.getName() != null) {
                existingCategory.setName(categoryUpdates.getName());
            }
            return categoryRepository.save(existingCategory);
        });
    }
}

