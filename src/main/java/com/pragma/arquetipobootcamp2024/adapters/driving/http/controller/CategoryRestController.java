package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddCategoryRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CategoryResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ICategoryServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.CategoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryRestController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryResponseMapper categoryResponseMapper;
    private final ICategoryRequestMapper categoryRequestMapper;

    public CategoryRestController(ICategoryServicePort categoryServicePort,
                                  ICategoryEntityMapper categoryEntityMapper,
                                  ICategoryResponseMapper categoryResponseMapper,
                                  ICategoryRequestMapper categoryRequestMapper){
        this.categoryServicePort = categoryServicePort;
        this.categoryEntityMapper= categoryEntityMapper;
        this.categoryResponseMapper = categoryResponseMapper;
        this.categoryRequestMapper = categoryRequestMapper;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        try {
            List<CategoryModel> categoryModels = categoryServicePort.getAllCategories();
            List<CategoryResponse> categoryResponses = categoryResponseMapper.toCategoryResponseList(categoryModels);
            return ResponseEntity.ok(categoryResponses);
        }catch (Exception e){
            logger.error("Error retrieving categories", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/categorynew")
    public ResponseEntity<CategoryResponse> createCategory(@Validated @RequestBody AddCategoryRequest addCategoryRequest){
            CategoryModel categoryModel = categoryRequestMapper.addRequestToCategoryModel(addCategoryRequest);
            CategoryModel createdCategory = categoryServicePort.createCategory(categoryModel);
            CategoryResponse categoryResponse = categoryResponseMapper.toResponse(createdCategory);
            return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

}
