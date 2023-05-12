package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    public Category addOne(Category category) {
        return categoryRepo.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category findById(UUID categoryId) {
        return categoryRepo.findById(categoryId).orElse(null);
    }

    public Optional<Category> findByName(String categoryName) {
        return categoryRepo.findByName(categoryName);
    }

    public void deleteById(UUID categoryId) {
        if (categoryRepo.findById(categoryId).isPresent()) {
            categoryRepo.deleteById(categoryId);
        } else {
            throw new NotFoundException("Category not found");
        }
    }
}
