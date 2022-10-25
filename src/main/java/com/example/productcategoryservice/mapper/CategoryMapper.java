package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CategoryRequestDto;
import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category map(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto map(Category category);

    List<CategoryResponseDto> map(List<Category> categories);

}
