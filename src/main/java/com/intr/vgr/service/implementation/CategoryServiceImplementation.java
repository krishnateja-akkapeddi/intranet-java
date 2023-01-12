package com.intr.vgr.service.implementation;

import com.intr.vgr.model.Category;
import com.intr.vgr.model.CategoryType;
import com.intr.vgr.repository.CategoryRepository;
import com.intr.vgr.service.CategoryService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
    public Category create(String categoryName){
        Category category = new Category();
        category.setCategory(CategoryType.valueOf(categoryName));
var saved =categoryRepository.save(category);
return saved;
    }
}
