package com.tharun.Food.Service.InterfaceService;

import com.tharun.Food.model.Category;

import java.util.List;

public interface CategoryService {
    public Category  createCategory (String name ,Long userId) throws Exception;
    public List<Category> findCategoryByRestaurantID(Long id) throws  Exception;
    public Category findCategoryById(Long id)throws  Exception;

}
