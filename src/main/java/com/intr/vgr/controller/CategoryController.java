package com.intr.vgr.controller;

import com.google.common.base.Optional;
import com.intr.vgr.ga_responses.GaResponse;
import com.intr.vgr.model.Category;
import com.intr.vgr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity categoryList() {
        return new GaResponse().successResponse(categoryService.getAllCategories());
    }

    @PostMapping("/create")
    public ResponseEntity createCategory(@RequestParam("categoryName") String categoryName,
            @RequestAttribute("unauthorized") boolean unauthorized) {
        System.out.println("UNAUTH_" + unauthorized);
        if (unauthorized) {
            return new GaResponse().unautorizedResponse();
        } else {
            var category = categoryService.create(categoryName);
            return new GaResponse().successResponse(category);
        }
    }
}
