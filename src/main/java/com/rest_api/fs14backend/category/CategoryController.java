package com.rest_api.fs14backend.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }
    @PostMapping("/")
    public Category addOne(@RequestBody Category category) {
        return categoryService.addOne(category);
    }

    @GetMapping(path="/{id}")
    public Optional<Category> getCategoryById(@PathVariable("id") UUID id) {
        return Optional.ofNullable(categoryService.findById(id));
    }
    @DeleteMapping(path="/{id}")
    public void deleteCategoryById(@PathVariable("id") UUID id){
        categoryService.deleteById(id);
    }
}
