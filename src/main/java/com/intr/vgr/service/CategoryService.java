package com.intr.vgr.service;

import com.intr.vgr.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();

    public Category create(String categoryName);
}
