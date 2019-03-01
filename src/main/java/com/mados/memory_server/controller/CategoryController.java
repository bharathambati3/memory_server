package com.mados.memory_server.controller;

import com.mados.memory_server.constants.ErrorResultStatus;
import com.mados.memory_server.db.entity.Category;
import com.mados.memory_server.db.repository.CategoryRepo;
import com.mados.memory_server.request.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryRepo categoryRepo;

    // Get All Notes
    @GetMapping("/categories")
    public BaseResponse<List<Category>> getAllNotes() {
        return new BaseResponse<>(categoryRepo.findAll());
    }

    @PostMapping("/category")
    public BaseResponse<Category> createCategory(@Valid @RequestBody Category category) {
        category.setCreatedOn(LocalDateTime.now());
        category.setLastUpdated(LocalDateTime.now());
        return new BaseResponse<>(categoryRepo.save(category));
    }

    @PutMapping("/category/{id}")
    public BaseResponse<Category> updateCategory(@Valid @RequestBody Category category, @PathVariable("id") Integer id) {
        Optional<Category> byId = categoryRepo.findById(id);
        if (byId.isPresent()) {
            Category prevCategory = byId.get();
            prevCategory.setName(category.getName());
            prevCategory.setDescription(category.getDescription());
            prevCategory.setLastUpdated(LocalDateTime.now());
            return new BaseResponse<>(categoryRepo.save(prevCategory));
        }
        return new BaseResponse<>(ErrorResultStatus.INVALID_CATEGORY_ID);
    }

    @DeleteMapping("/category/{id}")
    public BaseResponse<Boolean> deleteCategory(@PathVariable("id") Integer id) {
        Optional<Category> byId = categoryRepo.findById(id);
        if (byId.isPresent()) {
            categoryRepo.delete(byId.get());
            return new BaseResponse<>(true);
        }
        return new BaseResponse<>(false);
    }
}
